# @yinxianwei/jpush

capacitor jpush

## Install

```bash
npm install @yinxianwei/jpush
npx cap sync
```

### Config

```json
# capacitor.config.json
{
    "plugins": {
        ...
        "JPush": {
            "appKey": "",
            "channel": "default",
            "isProduction": false
        }
    }
}
```

### iOS

```c
# xx-Bridging-Header.h

#import "JPUSHService.h"
```

```swift
# AppDelegate.swift

func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
    JPUSHService.registerDeviceToken(deviceToken)
}
```

```
Capabilities -> add Push Notifications
```


### Android

> https://docs.jiguang.cn/jpush/client/Android/android_guide

```xml
# android/app/src/main/AndroidManifest.xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android" xmlns:tools="http://schemas.android.com/tools">
    <application>
    ...
        <service android:name="com.capacitorjs.plugins.jpush.JPushService"
            android:enabled="true"
            android:exported="false"
            android:process=":pushcore">
            <intent-filter>
                <action android:name="cn.jiguang.user.service.action" />
            </intent-filter>
        </service>

        <receiver android:name="com.capacitorjs.plugins.jpush.JPushReceiver"
            android:exported="true">
            <intent-filter>
                <action android:name="cn.jpush.android.intent.SERVICE_MESSAGE" />
                <category android:name="${applicationId}"></category>
            </intent-filter>
        </receiver>

        <receiver
            android:name="cn.jpush.android.service.PushReceiver"
            android:enabled="true"
            android:exported="false">
            <intent-filter android:priority="1000">
                <action android:name="cn.jpush.android.intent.NOTIFICATION_RECEIVED_PROXY" />
                <category android:name="${applicationId}" />
            </intent-filter>
        </receiver>

        <provider
            android:name="cn.jpush.android.service.DataProvider"
            android:authorities="${applicationId}.DataProvider"
            android:exported="false"
            android:process=":pushcore"
            tools:replace="android:authorities" />
        <activity
            android:name="cn.jpush.android.service.JNotifyActivity"
            android:exported="true"
            android:taskAffinity=""
            android:theme="@style/JPushTheme" >
            <intent-filter>
                <action android:name="cn.jpush.android.intent.JNotifyActivity" />
                <category android:name="android.intent.category.DEFAULT"/>
                <category android:name="${applicationId}" />
            </intent-filter>
        </activity>
        <activity
            android:name="cn.android.service.JTransitActivity"
            android:exported="true"
            android:taskAffinity=""
            android:theme="@style/JPushTheme" >
            <intent-filter>
                <action android:name="cn.android.service.JTransitActivity" />
                <category android:name="android.intent.category.DEFAULT"/>

                <category android:name="${applicationId}" />
            </intent-filter>
        </activity>
        <provider
            android:name="cn.jpush.android.service.InitProvider"
            android:authorities="${applicationId}.jiguang.InitProvider"
            android:exported="false" />
        <meta-data android:name="JPUSH_CHANNEL"
            android:value="developer-default"
            tools:replace="android:value" />
        <meta-data android:name="JPUSH_APPKEY"
            android:value="输入APPKEY"
            tools:replace="android:value" />
    </application>

    <permission
        android:name="${applicationId}.permission.JPUSH_MESSAGE"
        android:protectionLevel="signature" />
    <uses-permission android:name="${applicationId}.permission.JPUSH_MESSAGE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <!-- 适配Android13，弹出通知必须权限-->
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>

    <uses-permission android:name="com.huawei.android.launcher.permission.CHANGE_BADGE" /><!-- 华为角标 -->
    <uses-permission android:name="com.vivo.notification.permission.BADGE_ICON" /><!-- VIVO角标权限 -->
    <uses-permission android:name="com.hihonor.android.launcher.permission.CHANGE_BADGE" /><!--honor 角标-->
    <uses-permission android:name="android.permission.VIBRATE" /><!--振动器权限，JPUSH支持通知开启振动功能，小米推送必须-->

</manifest>

```

```js
# android/app/build.gradle

android {
    defaultConfig {
        ...
        ndk {
            //选择要添加的对应 cpu 类型的 .so 库。
            abiFilters 'armeabi', 'armeabi-v7a', 'arm64-v8a'
            // 还可以添加 'x86', 'x86_64', 'mips', 'mips64'
        }
        manifestPlaceholders = [
            JPUSH_PKGNAME : applicationId,
            JPUSH_APPKEY : "", //JPush 上注册的包名对应的 Appkey.
            JPUSH_CHANNEL : "developer-default", //暂时填写默认值即可.
            // MEIZU_APPKEY : "MZ-魅族的APPKEY",
            // MEIZU_APPID : "MZ-魅族的APPID",
            // XIAOMI_APPID : "MI-小米的APPID",
            // XIAOMI_APPKEY : "MI-小米的APPKEY",
            // OPPO_APPKEY : "OP-oppo的APPKEY",
            // OPPO_APPID : "OP-oppo的APPID",
            // OPPO_APPSECRET : "OP-oppo的APPSECRET",
            // VIVO_APPKEY : "vivo的APPKEY",
            // VIVO_APPID : "vivo的APPID"
            // HONOR_APPID : "Honor的APP ID";
        ]
    }
}
dependencies {
    // 若不集成厂商通道，可直接跳过以下依赖
    // 极光厂商插件版本与接入 JPush 版本保持一致，下同
    // 接入华为厂商
    implementation 'com.huawei.hms:push:6.5.0.300'
    implementation 'cn.jiguang.sdk.plugin:huawei:5.0.0'
    // 接入 FCM 厂商
    implementation 'com.google.firebase:firebase-messaging:23.0.5'
    implementation 'cn.jiguang.sdk.plugin:fcm:5.0.0'
    // 接入魅族厂商
    implementation 'cn.jiguang.sdk.plugin:meizu:5.0.0'
    // 接入 VIVO 厂商
    implementation 'cn.jiguang.sdk.plugin:vivo:5.0.0'
    // 接入小米厂商
    implementation 'cn.jiguang.sdk.plugin:xiaomi:5.0.0'
    // 接入 OPPO 厂商
    implementation 'cn.jiguang.sdk.plugin:oppo:5.0.0'
    // JPush Android SDK v4.6.0 开始，需要单独引入 oppo 厂商 aar ，请下载官网 SDK 包并把 sh-android-xxx-release/third-push/oppo/libs 下的 aar 文件单独拷贝一份到应用 module/libs 下
    implementation(name: 'com.heytap.msp-push-3.1.0', ext: 'aar')
    //以下为 OPPO 3.1.0 aar需要依赖
    implementation 'com.google.code.gson:gson:2.6.2'
    implementation 'commons-codec:commons-codec:1.6'
    implementation 'androidx.annotation:annotation:1.1.0'
    // 接入荣耀厂商
    implementation 'cn.jiguang.sdk.plugin:honor:5.0.0' 
    //需要单独引入荣耀厂商 aar ，请下载官网 SDK 包并把 jpush-android-xxx-release/third-push/honor/libs 下的 aar 件单独拷贝一份到应用 module/libs 下
    implementation(name: 'HiPushSdk-v7.0.1.103', ext: 'aar')
}

apply plugin: 'com.huawei.agconnect'
```


## API

<docgen-index>

* [`init()`](#init)
* [`getRegistrationID()`](#getregistrationid)
* [`setTags(...)`](#settags)
* [`setAlias(...)`](#setalias)
* [`addListener('openNotification', ...)`](#addlisteneropennotification-)
* [`getRemoteNotification()`](#getremotenotification)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### init()

```typescript
init() => Promise<{ data: 'notDetermined' | 'denied' | 'authorized' | 'provisional'; }>
```

**Returns:** <code>Promise&lt;{ data: 'notDetermined' | 'denied' | 'authorized' | 'provisional'; }&gt;</code>

--------------------


### getRegistrationID()

```typescript
getRegistrationID() => Promise<{ data: { code?: string; registrationID: string; }; }>
```

**Returns:** <code>Promise&lt;{ data: { code?: string; registrationID: string; }; }&gt;</code>

--------------------


### setTags(...)

```typescript
setTags(options: { sequence: number; tags: string[]; }) => Promise<{ data: { iResCode: number; iTags: string[]; seq: number; }; }>
```

| Param         | Type                                               |
| ------------- | -------------------------------------------------- |
| **`options`** | <code>{ sequence: number; tags: string[]; }</code> |

**Returns:** <code>Promise&lt;{ data: { iResCode: number; iTags: string[]; seq: number; }; }&gt;</code>

--------------------


### setAlias(...)

```typescript
setAlias(options: { sequence: number; alias: string; }) => Promise<{ data: { iResCode: number; iAlias: string; seq: number; }; }>
```

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code>{ sequence: number; alias: string; }</code> |

**Returns:** <code>Promise&lt;{ data: { iResCode: number; iAlias: string; seq: number; }; }&gt;</code>

--------------------


### addListener('openNotification', ...)

```typescript
addListener(name: 'openNotification', callback: { (): { badge: number; body: string; subtitle: string; title: string; extras: string; }; }) => void
```

| Param          | Type                                                                                                    |
| -------------- | ------------------------------------------------------------------------------------------------------- |
| **`name`**     | <code>'openNotification'</code>                                                                         |
| **`callback`** | <code>() =&gt; { badge: number; body: string; subtitle: string; title: string; extras: string; }</code> |

--------------------


### getRemoteNotification()

```typescript
getRemoteNotification() => Promise<any>
```

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------

</docgen-api>
