import { WebPlugin } from '@capacitor/core';

import type { JPushPlugin } from './definitions';

export class JPushWeb extends WebPlugin implements JPushPlugin {
  async init(): Promise<any> {
    return undefined;
  }
  async getRegistrationID(): Promise<any> {
    return undefined;
  }
  async setTags(): Promise<any> {
    return undefined;
  }
  async setAlias(): Promise<any> {
    return undefined;
  }
  async addListener(): Promise<any> {
    return undefined;
  }
}
