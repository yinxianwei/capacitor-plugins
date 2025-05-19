import { WebPlugin } from '@capacitor/core';

import type { WechatPlugin } from './definitions';

export class WechatWeb extends WebPlugin implements WechatPlugin {
  async isInstalled(): Promise<any> {
    return true;
  }
  async registerApp(): Promise<any> {
    return true;
  }
  async shareImageMessage(): Promise<any> {
    return true;
  }
  async shareMiniProgramMessage(): Promise<any> {
    return true;
  }
  async shareTextMessage(): Promise<any> {
    return true;
  }
  async shareWebPageMessage(): Promise<any> {
    return true;
  }
  async shareVideoMessage(): Promise<any> {
    return true;
  }
  async shareMusicVideoMessage(): Promise<any> {
    return true;
  }
  async auth(): Promise<any> {
    return true;
  }
  async sendPaymentRequest(): Promise<any> {
    return true;
  }
  async openMiniProgram(): Promise<any> {
    return true;
  }
  async openCustomerServiceResp(): Promise<any> {
    return true;
  }
}
