import { WebPlugin } from '@capacitor/core';
import type { WechatPlugin } from './definitions';
export declare class WechatWeb extends WebPlugin implements WechatPlugin {
    isInstalled(): Promise<any>;
    registerApp(): Promise<any>;
    shareImageMessage(): Promise<any>;
    shareMiniProgramMessage(): Promise<any>;
    shareTextMessage(): Promise<any>;
    shareWebPageMessage(): Promise<any>;
    shareVideoMessage(): Promise<any>;
    shareMusicVideoMessage(): Promise<any>;
    auth(): Promise<any>;
    sendPaymentRequest(): Promise<any>;
    openMiniProgram(): Promise<any>;
}
