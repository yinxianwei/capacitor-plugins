import { WebPlugin } from '@capacitor/core';
export class WechatWeb extends WebPlugin {
    async isInstalled() {
        return true;
    }
    async registerApp() {
        return true;
    }
    async shareImageMessage() {
        return true;
    }
    async shareMiniProgramMessage() {
        return true;
    }
    async shareTextMessage() {
        return true;
    }
    async shareWebPageMessage() {
        return true;
    }
    async shareVideoMessage() {
        return true;
    }
    async shareMusicVideoMessage() {
        return true;
    }
    async auth() {
        return true;
    }
    async sendPaymentRequest() {
        return true;
    }
    async openMiniProgram() {
        return true;
    }
    async openCustomerServiceResp() {
        return true;
    }
}
//# sourceMappingURL=web.js.map