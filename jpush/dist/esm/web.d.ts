import { WebPlugin } from '@capacitor/core';
import type { JPushPlugin } from './definitions';
export declare class JPushWeb extends WebPlugin implements JPushPlugin {
    init(): Promise<any>;
    getRegistrationID(): Promise<any>;
    setTags(): Promise<any>;
    setAlias(): Promise<any>;
    addListener(): Promise<any>;
    getRemoteNotification(): Promise<any>;
    setBadge(): Promise<any>;
    clearAllNotifications(): Promise<any>;
    clearNotificationById(): Promise<any>;
}
