package com.capacitorjs.plugins.wechat;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXMusicVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


@CapacitorPlugin(name = "Wechat")
public class WechatPlugin extends Plugin {

    private Wechat implementation = new Wechat();
    public static IWXAPI wxapi;
    public static String APPID;
    public static String callbackId = null;
    public static Bridge bridge;

    @Override
    public void load() {
        super.load();
        APPID = this.getConfig().getString("AppId", "");
        bridge = this.getBridge();
    }

    public static IWXAPI getWxApi() {
        if (wxapi == null) {
            wxapi = WXAPIFactory.createWXAPI(bridge.getContext(), APPID, true);
        }
        return wxapi;
    }
    private String buildTransaction() {
        return String.valueOf(System.currentTimeMillis());
    }

    private String buildTransaction(final String type) {
        return type + System.currentTimeMillis();
    }
    @PluginMethod
    public void isInstalled(PluginCall call) {
        JSObject res = new JSObject();
        res.put("data", getWxApi().isWXAppInstalled());
        call.resolve(res);
    }
    @PluginMethod
    public void registerApp(PluginCall call) {
        boolean result = this.getWxApi().registerApp(APPID);
        JSObject res = new JSObject();
        res.put("data", result);
        call.resolve(res);
    }

    @PluginMethod
    public void shareImageMessage(PluginCall call) {
        Bitmap bmp = WechatPlugin.getBitmapFromInput(call.getString("imagePath"));
        if (bmp == null) {
            call.reject("Unknown bitmap");
            return;
        }
        WXMediaMessage msg = new WXMediaMessage();
        msg.title = call.getString("title");
        msg.description = call.getString("description");

        WXImageObject imgObj = new WXImageObject(WechatPlugin.compressBitmap(bmp, 1024));
        msg.mediaObject = imgObj;
        bmp.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.transaction = buildTransaction("img");
        req.scene = call.getInt("scene", SendMessageToWX.Req.WXSceneSession);
        boolean data = this.getWxApi().sendReq(req);
        JSObject res = new JSObject();
        res.put("data", data);
        call.resolve(res);
    }

    @PluginMethod
    public void shareTextMessage(PluginCall call) {
        String text = call.getString("text");
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = call.getInt("scene", SendMessageToWX.Req.WXSceneSession);
        boolean data = this.getWxApi().sendReq(req);
        JSObject res = new JSObject();
        res.put("data", data);
        call.resolve(res);
    }

    @PluginMethod
    public void shareMiniProgramMessage(PluginCall call) {
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = call.getString("webpageUrl");
        miniProgramObj.miniprogramType = call.getInt("miniprogramType", WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE);
        miniProgramObj.userName = call.getString("userName");
        miniProgramObj.path = call.getString("path");
        miniProgramObj.withShareTicket = call.getBoolean("withShareTicket", false);
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = call.getString("title");
        msg.description = call.getString("description");
        Bitmap bmp = WechatPlugin.getBitmapFromInput(call.getString("hdImageData"));
        msg.setThumbImage(WechatPlugin.compressBitmap(bmp, 128));
        bmp.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("miniProgram");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        boolean data = this.getWxApi().sendReq(req);
        JSObject res = new JSObject();
        res.put("data", data);
        call.resolve(res);
    }
    @PluginMethod
    public void shareWebPageMessage(PluginCall call) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = call.getString("webpageUrl");
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = call.getString("title");
        msg.description = call.getString("description");
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message =msg;
        req.scene = call.getInt("scene", SendMessageToWX.Req.WXSceneSession);
        boolean data = this.getWxApi().sendReq(req);
        JSObject res = new JSObject();
        res.put("data", data);
        call.resolve(res);
    }
    @PluginMethod
    public void shareVideoMessage(PluginCall call) {
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = call.getString("videoUrl");
        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = call.getString("title");
        msg.description = call.getString("description");
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        req.scene = call.getInt("scene", SendMessageToWX.Req.WXSceneSession);
        boolean data = this.getWxApi().sendReq(req);
        JSObject res = new JSObject();
        res.put("data", data);
        call.resolve(res);
    }
    @PluginMethod
    public void shareMusicVideoMessage(PluginCall call) {
        WXMusicVideoObject musicVideo = new WXMusicVideoObject();
        musicVideo.musicUrl = call.getString("musicUrl");
        musicVideo.musicDataUrl = call.getString("musicDataUrl");
        musicVideo.songLyric = call.getString("songLyric");
        musicVideo.hdAlbumThumbFilePath = call.getString("hdAlbumThumbFilePath");
        musicVideo.singerName = call.getString("singerName");
        musicVideo.albumName = call.getString("albumName");
        musicVideo.musicGenre = call.getString("musicGenre");
        musicVideo.issueDate = call.getLong("issueDate");
        musicVideo.identification = call.getString("identification");
        musicVideo.duration = call.getInt("duration");

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = musicVideo;
        msg.title = call.getString("title");
        msg.description = call.getString("description");
        msg.messageExt = call.getString("messageExt");
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("musicVideo");
        req.message = msg;
        req.scene = call.getInt("scene");
        boolean data = this.getWxApi().sendReq(req);
        JSObject res = new JSObject();
        res.put("data", data);
        call.resolve(res);
    }
    @PluginMethod
    public void openMiniProgram(PluginCall call) {
        IWXAPI api = WXAPIFactory.createWXAPI(this.getContext(), this.APPID);
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = call.getString("userName");
        req.path = call.getString("path");
        req.miniprogramType = call.getInt("miniprogramType", WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE);;
        boolean data = this.getWxApi().sendReq(req);
        JSObject res = new JSObject();
        res.put("data", data);
        call.resolve(res);
    }
    @PluginMethod
    public void auth(PluginCall call) {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = call.getString("scope", "snsapi_userinfo");
        req.state = call.getString("state");
        this.getWxApi().sendReq(req);
        callbackId = call.getCallbackId();
        bridge.saveCall(call);
    }

    public static Bitmap getBitmapFromInput(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        if (input.startsWith("http://") || input.startsWith("https://")) {
            return getBitmapFromURL(input);
        } else {
            return getBitmapFromBase64(input);
        }
    }

    public static Bitmap compressBitmap(Bitmap bitmap, int maxSize) {
        if (bitmap == null || maxSize <= 0) {
            return bitmap;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int quality = 100;
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            while (outputStream.toByteArray().length / 1024 > maxSize && quality > 10) {
                outputStream.reset();
                quality -= 5;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            }
            byte[] compressedBytes = outputStream.toByteArray();
            return BitmapFactory.decodeByteArray(compressedBytes, 0, compressedBytes.length);

        } catch (Exception e) {
            e.printStackTrace();
            return bitmap; // 压缩失败，返回原始 Bitmap
        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static Bitmap getBitmapFromURL(String urlString) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return bitmap;
    }

    private static Bitmap getBitmapFromBase64(String base64String) {
        Bitmap bitmap = null;
        try {
            String[] list = base64String.split(",", 2);
            base64String = list[list.length - 1];
            byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

}
