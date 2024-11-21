import { registerPlugin } from '@capacitor/core';
const JPush = registerPlugin('JPush', {
    web: () => import('./web').then((m) => new m.JPushWeb()),
});
export * from './definitions';
export { JPush };
//# sourceMappingURL=index.js.map