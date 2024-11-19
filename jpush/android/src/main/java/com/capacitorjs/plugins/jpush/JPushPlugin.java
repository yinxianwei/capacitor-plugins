package com.capacitorjs.plugins.jpush;

import android.content.Intent;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.PermissionCallback;
import com.getcapacitor.util.PermissionHelper;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.ups.JPushUPSManager;

@CapacitorPlugin(name = "JPush")
public class JPushPlugin extends Plugin {

    private JPush implementation = new JPush();

    public static JPushPlugin instance;
    public String initCallId;
    public String tagsCallId;
    public String aliasCallId;
    @Override
    public void load() {
        super.load();
        instance = this;
    }

    @Override
    protected void handleOnResume() {
        super.handleOnResume();
        if (this.initCallId != null) {
            PluginCall call = this.bridge.getSavedCall(this.initCallId);
            int status = JPushInterface.isNotificationEnabled(getContext());
            JSObject res = new JSObject();
            if (status == 1) {
                JPushUPSManager.registerToken(this.getContext(),  this.getConfig().getString("appKey"), null, null, result -> {
                    if (result.getReturnCode() == 0) {
                        res.put("data", "authorized");
                    } else {
                        res.put("data", "denied");
                    }
                    call.resolve(res);
                    this.bridge.releaseCall(call);
                    this.initCallId = null;
                });
            } else {
                res.put("data", "denied");
                call.resolve(res);
                this.bridge.releaseCall(call);
                this.initCallId = null;
            }
        }
    }

    @PluginMethod
    public void init(PluginCall call) {
        JPushInterface.setDebugMode(!this.getConfig().getBoolean("isProduction", true));
        JPushInterface.setChannel(this.getContext(), this.getConfig().getString("channel", "default"));
        int status = JPushInterface.isNotificationEnabled(getContext());
        JSObject res = new JSObject();
        if (status == -1) {
            res.put("data", "denied");
            call.resolve(res);
        } else if (status == 0) {
            JPushInterface.requestRequiredPermission(this.getActivity());
            this.initCallId = call.getCallbackId();
            this.bridge.saveCall(call);
        } else if (status == 1) {
            JPushUPSManager.registerToken(this.getContext(),  this.getConfig().getString("appKey"), null, null, result -> {
                if (result.getReturnCode() == 0) {
                    res.put("data", "authorized");
                } else {
                    res.put("data", "denied");
                }
                call.resolve(res);
            });
        }
    }

    @PluginMethod
    public void getRegistrationID(PluginCall call) {
        String registrationID = JPushInterface.getRegistrationID(this.getContext());
        JSObject res = new JSObject();
        JSObject data = new JSObject();
        data.put("registrationID", registrationID);
        res.put("data", data);
        call.resolve(res);
    }

    @PluginMethod
    public void setTags(PluginCall call) {
        JSArray tags = call.getArray("tags");
        Set<String> resultSet = new HashSet<>();
        for (int i = 0; i < tags.length(); i++) {
            String tag = tags.optString(i);
            resultSet.add(tag);
        }
        Integer sequence = call.getInt("sequence");
        JPushInterface.setTags(getContext(), sequence, resultSet);
        this.tagsCallId = call.getCallbackId();
        this.bridge.saveCall(call);
    }

    @PluginMethod
    public void setAlias(PluginCall call) {
        JPushInterface.setAlias(getContext(), call.getInt("sequence"), call.getString("alias"));
        this.aliasCallId = call.getCallbackId();
        this.bridge.saveCall(call);
    }

    static void onTagOperatorResult(JPushMessage jPushMessage) {
        if (instance.tagsCallId != null) {
            JSObject res = new JSObject();
            JSObject data = new JSObject();
            int errorCode =  jPushMessage.getErrorCode();
            data.put("iResCode", errorCode == 0 ? 1 : 0);
            data.put("iTags", new JSArray(jPushMessage.getTags()));
            data.put("seq", jPushMessage.getSequence());
            res.put("data", data);
            PluginCall call = instance.bridge.getSavedCall(instance.tagsCallId);
            call.resolve(res);
            instance.bridge.releaseCall(call);
            instance.tagsCallId = null;
        }
    }

    static void onAliasOperatorResult(JPushMessage jPushMessage) {
        if (instance.aliasCallId != null) {
            JSObject res = new JSObject();
            JSObject data = new JSObject();
            int errorCode =  jPushMessage.getErrorCode();
            data.put("iResCode", errorCode == 0 ? 1 : 0);
            data.put("iAlias", jPushMessage.getAlias());
            data.put("seq", jPushMessage.getSequence());
            res.put("data", data);
            PluginCall call = instance.bridge.getSavedCall(instance.aliasCallId);
            call.resolve(res);
            instance.bridge.releaseCall(call);
            instance.aliasCallId = null;
        }
    }

    static void onNotifyMessageOpened(NotificationMessage notificationMessage) {
        Intent intent = new Intent(instance.getContext(), instance.getActivity().getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        instance.getActivity().startActivity(intent);
        JSObject res = new JSObject();
        JSObject data = new JSObject();
        data.put("badge", 0);
        data.put("body", notificationMessage.inAppMsgContentBody);
        data.put("subtitle", notificationMessage.notificationContent);
        data.put("title", notificationMessage.notificationTitle);
        data.put("extras", notificationMessage.notificationExtras);
        res.put("data", data);
        instance.notifyListeners("openNotification", res);
    }
}
