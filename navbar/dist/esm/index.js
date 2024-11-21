import { registerPlugin } from '@capacitor/core';
const Navbar = registerPlugin('Navbar', {
    web: () => import('./web').then((m) => new m.NavbarWeb()),
});
export * from './definitions';
export { Navbar };
//# sourceMappingURL=index.js.map