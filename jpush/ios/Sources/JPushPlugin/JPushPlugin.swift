import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(JPushPlugin)
public class JPushPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "JPushPlugin"
    public let jsName = "JPush"
    var userinfo: [AnyHashable : Any]?
    var authCallbackId: String?

    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "init", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getRegistrationID", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setTags", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setAlias", returnType: CAPPluginReturnPromise),
    ]
    private let implementation = JPush()

    override public func load() {   
        NotificationCenter.default.addObserver(self, selector: #selector(applicationDidLaunch), name: UIApplication.didFinishLaunchingNotification, object: nil)
    }
    @objc func `init`(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            #if targetEnvironment(simulator)
            call.resolve(["data": ""])
            #else
            self.authCallbackId = call.callbackId
            self.bridge?.saveCall(call)
            #endif
            self.startJPushSDK()
        }
    }
    @objc func getRegistrationID(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            JPUSHService.registrationIDCompletionHandler { resCode, registrationID in
                call.resolve(["data": ["code": resCode,
                                       "registrationID": registrationID ?? ""]])
            }
        }
    }
    @objc func setTags(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            let tags = call.getArray("tags") as! [String]
            JPUSHService.setTags(Set(tags), completion: { iResCode, iTags, seq in
                let data = [
                    "iResCode": iResCode,
                    "iTags": iTags?.compactMap{$0 as? String} ?? [],
                    "seq": seq
                ]
                call.resolve(["data": data])
            }, seq: call.getInt("sequence") ?? 0)
        }
    }
    @objc func setAlias(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            JPUSHService.setAlias(call.getString("alias")!, completion: { iResCode, iAlias, seq in
                call.resolve(["data": [
                    "iResCode": iResCode,
                    "iAlias": iAlias! as String,
                    "seq": seq
                ]])
            }, seq: call.getInt("sequence") ?? 0)
        }
    }
    @objc func applicationDidLaunch (notification: Notification) {
        self.userinfo = notification.userInfo
        JPUSHService.setDebugMode()
    }
    @objc func startJPushSDK() {
        let entity = JPUSHRegisterEntity()
        entity.types = NSInteger(UNAuthorizationOptions.alert.rawValue) |
        NSInteger(UNAuthorizationOptions.sound.rawValue) |
        NSInteger(UNAuthorizationOptions.badge.rawValue)

        JPUSHService.register(forRemoteNotificationConfig: entity, delegate: self)
        JPUSHService.setup(withOption: self.userinfo, appKey: self.getConfig().getString("appKey") ?? "", channel: self.getConfig().getString("channel") ?? "", apsForProduction: self.getConfig().getBoolean("isProduction", false), advertisingIdentifier: nil)
    }
}

extension JPushPlugin: JPUSHRegisterDelegate {
    
    private func openNotification(request: UNNotificationRequest) {
        let extras = request.content.userInfo
        let content = request.content

        let badge = content.badge
        let body = content.body
        let subtitle = content.subtitle
        let title = content.title
        self.notifyListeners("openNotification", data: ["badge": badge ?? 0, "body": body, "subtitle": subtitle, "title": title, "extras": extras])
    }
    //MARK - JPUSHRegisterDelegate
    @available(iOS 10.0, *)
    public func jpushNotificationCenter(_ center: UNUserNotificationCenter, didReceive response: UNNotificationResponse, withCompletionHandler completionHandler: (() -> Void)) {
        let userInfo = response.notification.request.content.userInfo
        if (response.notification.request.trigger?.isKind(of: UNPushNotificationTrigger.self) == true) {
            JPUSHService.handleRemoteNotification(userInfo)
        }
        self.openNotification(request: response.notification.request)
        completionHandler()
    }
    
    @available(iOS 10.0, *)
    public func jpushNotificationCenter(_ center: UNUserNotificationCenter, willPresent notification: UNNotification,
                                 withCompletionHandler completionHandler: ((Int) -> Void)) {
        completionHandler(Int(UNNotificationPresentationOptions.badge.rawValue | UNNotificationPresentationOptions.sound.rawValue | UNNotificationPresentationOptions.alert.rawValue))
    }
    
    public func jpushNotificationCenter(_ center: UNUserNotificationCenter, openSettingsFor notification: UNNotification) {
    }
    public func jpushNotificationAuthorization(_ status: JPAuthorizationStatus, withInfo info: [AnyHashable : Any]?) {
        var data = "notDetermined"
        if status == .statusDenied {
            data = "denied"
        } else if status == .statusAuthorized {
            data = "authorized"
        } else if status == .statusProvisional {
            data = "provisional"
        }
        if let callId = self.authCallbackId {
            let call = self.bridge?.savedCall(withID: callId);
            call?.resolve(["data": data])
            self.bridge?.releaseCall(withID: callId)
            self.authCallbackId = nil;
        }
    }
}
