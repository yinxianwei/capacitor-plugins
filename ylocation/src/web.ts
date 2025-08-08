import { WebPlugin } from '@capacitor/core';

import type { YlocationPlugin, Position } from './definitions';

export class YlocationWeb extends WebPlugin implements YlocationPlugin {
  async requestPermissions(): Promise<boolean> {
    if (!navigator.permissions) {
      return false;
    }
    try {
      const status = await navigator.permissions.query({ name: 'geolocation' });
      return status.state == 'granted';
    } catch (e) {
      return false;
    }
  }
  async getCurrentPosition(options: { enableHighAccuracy: boolean; timeout: number }): Promise<Position> {
    return new Promise((resolve, reject) => {
      navigator.geolocation.getCurrentPosition(
        (pos) => {
          resolve(pos);
        },
        (err) => {
          reject(err.message);
        },
        Object.assign({ enableHighAccuracy: false, timeout: 5000, maximumAge: 0 }, options),
      );
    });
  }
}
