export interface NavbarPlugin {
    setTitle(options: {
        value: string;
    }): Promise<boolean>;
    setLeftIcon(options: {
        normal: string;
    }): Promise<boolean>;
    setLeftVisibility(options: {
        value: boolean;
    }): Promise<boolean>;
    setRightIcon(options: {
        normal: string;
    }): Promise<boolean>;
    setRightVisibility(options: {
        value: boolean;
    }): Promise<boolean>;
    allowsBackForwardNavigationGestures(options: {
        value: boolean;
    }): Promise<boolean>;
    exitApp(): Promise<boolean>;
}
