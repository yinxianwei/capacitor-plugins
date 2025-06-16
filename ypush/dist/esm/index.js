import { registerPlugin } from '@capacitor/core';
const YPush = registerPlugin('YPush', {
    web: () => import('./web').then((m) => new m.YPushWeb()),
});
export * from './definitions';
export { YPush };
//# sourceMappingURL=index.js.map