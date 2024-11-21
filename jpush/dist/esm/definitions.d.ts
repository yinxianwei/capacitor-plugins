export interface JPushPlugin {
    init(): Promise<{
        data: 'notDetermined' | 'denied' | 'authorized' | 'provisional';
    }>;
    getRegistrationID(): Promise<{
        data: {
            code?: string;
            registrationID: string;
        };
    }>;
    setTags(options: {
        sequence: number;
        tags: string[];
    }): Promise<{
        data: {
            iResCode: number;
            iTags: string[];
            seq: number;
        };
    }>;
    setAlias(options: {
        sequence: number;
        alias: string;
    }): Promise<{
        data: {
            iResCode: number;
            iAlias: string;
            seq: number;
        };
    }>;
    addListener(name: 'openNotification', callback: {
        (): {
            badge: number;
            body: string;
            subtitle: string;
            title: string;
            extras: string;
        };
    }): void;
}
