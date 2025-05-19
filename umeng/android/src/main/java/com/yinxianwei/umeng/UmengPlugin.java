package com.yinxianwei.umeng;

import com.getcapacitor.Bridge;
import com.getcapacitor.CapConfig;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginConfig;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.umeng.commonsdk.UMConfigure;

@CapacitorPlugin(name = "Umeng")
public class UmengPlugin extends Plugin {

    private Umeng implementation = new Umeng();

    static public void preInit(Bridge bridge) {
        CapConfig config = bridge.getConfig();
        PluginConfig pluginConfig = config.getPluginConfiguration("Umeng");
        String appKey = pluginConfig.getString("androidAppKey");
        String channel = pluginConfig.getString("androidChannel");
        UMConfigure.preInit(bridge.getContext(), appKey, channel);
    }
    @PluginMethod
    public void initWithOptions(PluginCall call) {
        String appKey = this.getConfig().getString("androidAppKey");
        String channel = this.getConfig().getString("androidChannel");
        UMConfigure.init(this.getContext(), appKey, channel, UMConfigure.DEVICE_TYPE_PHONE, null);
        JSObject ret = new JSObject();
        ret.put("data", true);
        call.resolve(ret);
    }
    @PluginMethod
    public void setLogEnabled(PluginCall call) {
        Boolean value = call.getBoolean("value");
        UMConfigure.setLogEnabled(value);
        JSObject ret = new JSObject();
        ret.put("data", true);
        call.resolve(ret);
    }
}
