import { registerPlugin } from '@capacitor/core';

import type { YPushPlugin } from './definitions';

const YPush = registerPlugin<YPushPlugin>('YPush', {
  web: () => import('./web').then((m) => new m.YPushWeb()),
});

export * from './definitions';
export { YPush };
