import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(UmengPlugin)
public class UmengPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "UmengPlugin"
    public let jsName = "Umeng"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "initWithOptions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setLogEnabled", returnType: CAPPluginReturnPromise),
    ]
    private let implementation = Umeng()

    @objc func initWithOptions(_ call: CAPPluginCall) {
        let appKey = self.getConfig().getString("iOSAppKey") ?? ""
        let channel = self.getConfig().getString("iOSChannel")
        UMConfigure.initWithAppkey(appKey, channel: channel)
        call.resolve(["data": true])
    }
    @objc func setLogEnabled(_ call: CAPPluginCall) {
        let value = call.getBool("value")
        UMConfigure.setLogEnabled(value ?? false);
    }
}
