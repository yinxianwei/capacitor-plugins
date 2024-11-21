import { WebPlugin } from '@capacitor/core';
export class NavbarWeb extends WebPlugin {
    async setTitle(options) {
        document.title = options.value;
        return true;
    }
    async setLeftIcon() {
        return true;
    }
    async setLeftVisibility() {
        return true;
    }
    async setRightIcon() {
        return true;
    }
    async setRightVisibility() {
        return true;
    }
    async allowsBackForwardNavigationGestures() {
        return true;
    }
}
//# sourceMappingURL=web.js.map