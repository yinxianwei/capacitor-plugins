import { registerPlugin } from '@capacitor/core';

import type { UmengPlugin } from './definitions';

const Umeng = registerPlugin<UmengPlugin>('Umeng', {
  web: () => import('./web').then((m) => new m.UmengWeb()),
});

export * from './definitions';
export { Umeng };
