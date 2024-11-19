package com.capacitorjs.plugins.jpush;

import android.content.Context;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class JPushReceiver extends JPushMessageReceiver {
    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        JPushPlugin.onTagOperatorResult(jPushMessage);
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        JPushPlugin.onAliasOperatorResult(jPushMessage);
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        JPushInterface.reportNotificationOpened(context, notificationMessage.msgId);
        JPushPlugin.onNotifyMessageOpened(notificationMessage);
    }
}
