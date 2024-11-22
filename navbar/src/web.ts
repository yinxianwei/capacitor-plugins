import { WebPlugin } from '@capacitor/core';

import type { NavbarPlugin } from './definitions';

export class NavbarWeb extends WebPlugin implements NavbarPlugin {
  async setup(): Promise<boolean>{
    return true;
  }
  async setTitle(options: { value: string }): Promise<boolean> {
    document.title = options.value;
    return true;
  }
  async setLeftIcon(): Promise<boolean> {
    return true;
  }
  async setLeftVisibility(): Promise<boolean> {
    return true;
  }
  async setRightIcon(): Promise<boolean> {
    return true;
  }
  async setRightVisibility(): Promise<boolean> {
    return true;
  }
  async allowsBackForwardNavigationGestures(): Promise<boolean> {
    return true;
  }
  async exitApp(): Promise<boolean> {
    return true;
  }
}
