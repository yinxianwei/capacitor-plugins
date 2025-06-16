import { WebPlugin } from '@capacitor/core';
import type { YPushPlugin } from './definitions';
export declare class YPushWeb extends WebPlugin implements YPushPlugin {
    getNotificationSettings(): Promise<any>;
    requestAuthorization(): Promise<any>;
    getDeviceToken(): Promise<any>;
    openSettings(): Promise<any>;
}
