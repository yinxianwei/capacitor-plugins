import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(WechatPlugin)
public class WechatPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "WechatPlugin"
    public let jsName = "Wechat"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "isInstalled", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "registerApp", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "shareImageMessage", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "shareMiniProgramMessage", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "shareTextMessage", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "shareWebPageMessage", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "shareVideoMessage", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "shareMusicVideoMessage", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "auth", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "sendPaymentRequest", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "openMiniProgram", returnType: CAPPluginReturnPromise)
    ]
    public static var shared:WechatPlugin? = nil;
    private let implementation = Wechat()
    public var callbackId = "";
    
    public override func load() {
        super.load()
        WechatPlugin.shared = self;
    }
    
    @objc func isInstalled(_ call: CAPPluginCall) {
        let data = WXApi.isWXAppInstalled()
        call.resolve(["data": data]);
    }
    
    @objc func registerApp(_ call: CAPPluginCall) {
        let data = WXApi.registerApp(getConfig().getString("appId")!, universalLink: getConfig().getString("universalLink")!);
        call.resolve(["data": data]);
    }
    @objc func shareImageMessage(_ call: CAPPluginCall) {
        let msg = WXMediaMessage()
        msg.title = call.getString("title", "")
        msg.description = call.getString("description", "")
        let imgObj = WXImageObject()
        imgObj.imageData = self.getImageData(call.getString("imagePath", ""), maxSize: 1024*10)
        msg.mediaObject = imgObj;
        let req = SendMessageToWXReq()
        req.bText = false;
        req.message = msg;
        req.scene = Int32(call.getInt("scene", 0))
        DispatchQueue.main.async {
            WXApi.send(req) { data in
                call.resolve(["data": data]);
            }
        }
    }

    @objc func shareMiniProgramMessage(_ call: CAPPluginCall) {
        let msg = WXMediaMessage()
        msg.title = call.getString("title", "")
        msg.description = call.getString("description", "")

        let object = WXMiniProgramObject()
        object.webpageUrl = call.getString("webpageUrl", "");
        object.userName = call.getString("userName", "");
        object.path = call.getString("path", "");
        object.hdImageData = getImageData(call.getString("hdImageData", ""), maxSize: 128);
        object.withShareTicket = call.getBool("withShareTicket", false);
        object.miniProgramType = WXMiniProgramType(rawValue: UInt(call.getInt("miniProgramType", 0)))!
        msg.mediaObject = object;
        
        let req = SendMessageToWXReq()
        req.bText = false;
        req.message = msg;
        req.scene = Int32(call.getInt("scene", 0))

        DispatchQueue.main.async {
            WXApi.send(req) { data in
                call.resolve(["data": data]);
            }
        }
    }

    @objc func shareTextMessage(_ call: CAPPluginCall) {
        let req = SendMessageToWXReq()
        req.bText = true;
        req.text = call.getString("text", "");
        req.scene = Int32(call.getInt("scene", 0))
        DispatchQueue.main.async {
            WXApi.send(req) { data in
                call.resolve(["data": data]);
            }
        }
    }

    @objc func shareWebPageMessage(_ call: CAPPluginCall) {
        let msg = WXMediaMessage()
        msg.title = call.getString("title", "")
        msg.description = call.getString("description", "")
        
        let webpageObject = WXWebpageObject()
        webpageObject.webpageUrl = call.getString("webpageUrl", "")
        msg.mediaObject = webpageObject
        let req = SendMessageToWXReq()
        req.bText = false;
        req.message = msg;
        req.scene = Int32(call.getInt("scene", 0))

        DispatchQueue.main.async {
            WXApi.send(req) { data in
                call.resolve(["data": data]);
            }
        }
    }

    @objc func shareVideoMessage(_ call: CAPPluginCall) {

        let msg = WXMediaMessage()
        msg.title = call.getString("title", "")
        msg.description = call.getString("description", "")

        let videoObject = WXVideoObject()
        videoObject.videoUrl = call.getString("videoUrl", "")
        msg.mediaObject = videoObject
        
        let req = SendMessageToWXReq()
        req.bText = false;
        req.message = msg;
        req.scene = Int32(call.getInt("scene", 0))

        DispatchQueue.main.async {
            WXApi.send(req) { data in
                call.resolve(["data": data]);
            }
        }
    }

    @objc func shareMusicVideoMessage(_ call: CAPPluginCall) {
        let msg = WXMediaMessage()
        msg.title = call.getString("title", "")
        msg.description = call.getString("description", "")
        
        let mvObject = WXMusicVideoObject()
        mvObject.musicUrl = call.getString("musicUrl", "");
        mvObject.musicDataUrl = call.getString("musicDataUrl", "");
        mvObject.songLyric = call.getString("songLyric", "");
        mvObject.hdAlbumThumbData = getImageData(call.getString("hdAlbumThumbFilePath", ""), maxSize: 1024);
        mvObject.singerName = call.getString("singerName", "");
        mvObject.albumName = call.getString("albumName", "");
        mvObject.musicGenre = call.getString("musicGenre", "");
        mvObject.issueDate = UInt64(call.getInt("issueDate", 0));
        mvObject.identification = call.getString("identification", "");
        mvObject.duration = UInt32(call.getInt("duration", 0));
        msg.mediaObject = mvObject;
        
        let req = SendMessageToWXReq()
        req.bText = false;
        req.message = msg;
        req.scene = Int32(call.getInt("scene", 0))

        DispatchQueue.main.async {
            WXApi.send(req) { data in
                call.resolve(["data": data]);
            }
        }
    }

    @objc func auth(_ call: CAPPluginCall) {
        let req = SendAuthReq()
        req.scope = call.getString("scope", "snsapi_userinfo");
        req.state = call.getString("state", "");
        DispatchQueue.main.async {
            self.callbackId = call.callbackId
            self.bridge?.saveCall(call)
            WXApi.send(req)
        }
    }

    @objc func sendPaymentRequest(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            call.resolve()
        }
    }

    @objc func openMiniProgram(_ call: CAPPluginCall) {
        let req = WXLaunchMiniProgramReq()
        req.userName = call.getString("userName", "")
        req.path = call.getString("path", "")
        req.miniProgramType = WXMiniProgramType(rawValue: UInt(call.getInt("miniProgramType", 0)))!
        DispatchQueue.main.async {
            WXApi.send(req) { data in
                call.resolve(["data": data]);
            }
        }
    }
    
    @objc func getImageData(_ input: String, maxSize: Int) -> Data {
        if let url = URL(string: input), input.hasPrefix("http") {
            if let imageData = try? Data(contentsOf: url) {
                return UIImage(data: imageData)!.pngData()!
            }
        }
        return Data(base64Encoded: input)!
    }
    
    
    public static func onResp(_ resp: Any) {
        if let callbackId = shared?.callbackId, !callbackId.isEmpty {
            let call = shared?.bridge?.savedCall(withID: callbackId)
            let baseResp = resp as! BaseResp;
            if baseResp.errCode == 0 {
                if resp is SendAuthResp {
                    let res = baseResp as! SendAuthResp
                    call?.resolve(["data": ["code": res.code, "state": res.state, "lang": res.lang, "country": res.country]])
                }
            } else {
                call?.reject(String(baseResp.errCode))
            }
            shared?.bridge?.releaseCall(withID: callbackId);
            shared?.callbackId = "";
        }
    }
}
