import { registerPlugin } from '@capacitor/core';
const Wechat = registerPlugin('Wechat', {
    web: () => import('./web').then((m) => new m.WechatWeb()),
});
export * from './definitions';
export { Wechat };
//# sourceMappingURL=index.js.map