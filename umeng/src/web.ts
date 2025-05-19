import { WebPlugin } from '@capacitor/core';

import type { UmengPlugin } from './definitions';

export class UmengWeb extends WebPlugin implements UmengPlugin {
  async initWithOptions(): Promise<any> {
    return;
  }
  async setLogEnabled(): Promise<any> {
    return;
  }
}
