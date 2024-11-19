import { registerPlugin } from '@capacitor/core';

import type { NavbarPlugin } from './definitions';

const Navbar = registerPlugin<NavbarPlugin>('Navbar', {
  web: () => import('./web').then((m) => new m.NavbarWeb()),
});

export * from './definitions';
export { Navbar };
