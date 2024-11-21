import { WebPlugin } from '@capacitor/core';
import type { NavbarPlugin } from './definitions';
export declare class NavbarWeb extends WebPlugin implements NavbarPlugin {
    setTitle(options: {
        value: string;
    }): Promise<boolean>;
    setLeftIcon(): Promise<boolean>;
    setLeftVisibility(): Promise<boolean>;
    setRightIcon(): Promise<boolean>;
    setRightVisibility(): Promise<boolean>;
    allowsBackForwardNavigationGestures(): Promise<boolean>;
}
