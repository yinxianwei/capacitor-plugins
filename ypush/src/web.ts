import { WebPlugin } from '@capacitor/core';

import type { YPushPlugin } from './definitions';

export class YPushWeb extends WebPlugin implements YPushPlugin {
  async getNotificationSettings(): Promise<any> {
    return;
  }
  async requestAuthorization(): Promise<any> {
    return;
  }
  async getDeviceToken(): Promise<any> {
    return;
  }
  async openSettings(): Promise<any> {
    return;
  }
}
