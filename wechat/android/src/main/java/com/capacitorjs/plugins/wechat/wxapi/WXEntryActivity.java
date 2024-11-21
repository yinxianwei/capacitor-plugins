package com.capacitorjs.plugins.wechat.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.capacitorjs.plugins.wechat.WechatPlugin;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private final String TAG = "WXEntryActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IWXAPI wxapi = WechatPlugin.getWxApi();
        wxapi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {}

    @Override
    public void onResp(BaseResp baseResp) {
        if (WechatPlugin.callbackId != null) {
            PluginCall call = WechatPlugin.bridge.getSavedCall(WechatPlugin.callbackId);
            WechatPlugin.callbackId = null;
            if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {
                if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
                    SendAuth.Resp resp = ((SendAuth.Resp) baseResp);
                    JSObject res = new JSObject();
                    JSObject data = new JSObject();
                    data.put("code", resp.code);
                    data.put("state", resp.state);
                    data.put("lang", resp.lang);
                    data.put("country", resp.country);
                    res.put("data", data);
                    call.resolve(res);
                }
            } else {
                JSObject res = new JSObject();
                res.put("code", baseResp.errCode);
                call.reject(baseResp.errStr, res);
            }
            WechatPlugin.bridge.releaseCall(call);
        }
        finish();
    }
}
