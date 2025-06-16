export interface YPushPlugin {
    getNotificationSettings(): Promise<{
        data: number;
    }>;
    requestAuthorization(): Promise<{
        data: boolean;
    }>;
    getDeviceToken(): Promise<{
        data: string;
    }>;
    openSettings(): Promise<{
        data: boolean;
    }>;
}
