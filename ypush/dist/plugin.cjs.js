'use strict';

var core = require('@capacitor/core');

const YPush = core.registerPlugin('YPush', {
    web: () => Promise.resolve().then(function () { return web; }).then((m) => new m.YPushWeb()),
});

class YPushWeb extends core.WebPlugin {
    async getNotificationSettings() {
        return;
    }
    async requestAuthorization() {
        return;
    }
    async getDeviceToken() {
        return;
    }
    async openSettings() {
        return;
    }
    async setBadge() {
        return;
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    YPushWeb: YPushWeb
});

exports.YPush = YPush;
//# sourceMappingURL=plugin.cjs.js.map
