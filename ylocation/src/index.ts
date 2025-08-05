import { registerPlugin } from '@capacitor/core';

import type { YlocationPlugin } from './definitions';

const Ylocation = registerPlugin<YlocationPlugin>('Ylocation', {
  web: () => import('./web').then((m) => new m.YlocationWeb()),
});

export * from './definitions';
export { Ylocation };
