import Foundation
import Capacitor
import UserNotifications

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(YPushPlugin)
public class YPushPlugin: CAPPlugin, CAPBridgedPlugin, UNUserNotificationCenterDelegate {
    public let identifier = "YPushPlugin"
    public let jsName = "YPush"
    
    public static var shared:YPushPlugin? = nil;
    public var callbackId = "";
    var launchOptions: [AnyHashable : Any]?

    public override func load() {
        super.load()
        YPushPlugin.shared = self;
        UNUserNotificationCenter.current().delegate = self
        NotificationCenter.default.addObserver(self, selector: #selector(applicationDidLaunch), name: UIApplication.didFinishLaunchingNotification, object: nil)
    }
    
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getNotificationSettings", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "requestAuthorization", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getDeviceToken", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "openSettings", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setBadge", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = YPush()
    
    @objc func applicationDidLaunch (notification: Notification) {
        if let userInfo = notification.userInfo,
           let launchOptions = userInfo[UIApplication.LaunchOptionsKey.remoteNotification] as? [AnyHashable: Any] {
            self.launchOptions = launchOptions
        }
    }
    
    @objc func getNotificationSettings(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            UNUserNotificationCenter.current().getNotificationSettings { settings in
                call.resolve(["data": settings.authorizationStatus])
            }
        }
    }
    @objc func requestAuthorization(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .sound, .badge]) { granted, error in
                call.resolve(["data": granted])
            }
        }
    }
    @objc func getDeviceToken(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            #if targetEnvironment(simulator)
            call.resolve(["data": ""])
            #else
            self.callbackId = call.callbackId
            self.bridge?.saveCall(call)
            UIApplication.shared.registerForRemoteNotifications()
            #endif
        }
    }
    @objc func openSettings(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            if let settingsURL = URL(string: UIApplication.openSettingsURLString) {
                if UIApplication.shared.canOpenURL(settingsURL) {
                    UIApplication.shared.open(settingsURL, options: [:], completionHandler: nil)
                    return call.resolve(["data": true])
                }
            }
            call.resolve(["data": false])
        }
    }
    @objc func setBadge(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            UIApplication.shared.applicationIconBadgeNumber = call.getInt("value") ?? 0
            call.resolve(["data": true])
        }
    }
    
    @objc func notify(_ request: UNNotificationRequest) {
        let extras = request.content.userInfo
        let content = request.content
        let badge = content.badge
        let body = content.body
        let subtitle = content.subtitle
        let title = content.title
        self.notifyListeners("openNotification", data: ["badge": badge ?? 0, "body": body, "subtitle": subtitle, "title": title, "extras": extras])
    }
    
    public func userNotificationCenter(_ center: UNUserNotificationCenter, didReceive response: UNNotificationResponse, withCompletionHandler completionHandler: @escaping () -> Void) {
        self.notify(response.notification.request)
        completionHandler()
    }
    public override func addListener(_ call: CAPPluginCall) {
        super.addListener(call)
        if let launchOptions = self.launchOptions {
            var data = ["badge": 0, "body": "", "subtitle": "", "title": "", "extras": nil] as [String : Any?]
            if let aps = launchOptions["aps"] as? [String: Any] {
                if let alert = aps["alert"] as? [String: Any] {
                    data["body"] = alert["body"] as? String ?? ""
                    data["subtitle"] = alert["subtitle"] as? String ?? ""
                    data["title"] = alert["title"] as? String ?? ""
                }
                data["badge"] = aps["badge"] as? Int ?? 0
            }
            data["extras"] = launchOptions
            self.notifyListeners("openNotification", data: data as [String : Any])
            self.launchOptions = nil
        }
    }
    
    public static func registerDeviceToken(_ deviceToken: Data?) {
        if let callbackId = shared?.callbackId, !callbackId.isEmpty {
            let call = shared?.bridge?.savedCall(withID: callbackId)
            if let tempToken = deviceToken {
                let tokenParts = tempToken.map { data in String(format: "%02.2hhx", data) }
                let token = tokenParts.joined()
                call?.resolve(["data": token])
            } else {
                call?.resolve(["data": ""])
            }
            shared?.bridge?.releaseCall(withID: callbackId);
            shared?.callbackId = "";
        }
    }
}

