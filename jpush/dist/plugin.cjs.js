'use strict';

var core = require('@capacitor/core');

const JPush = core.registerPlugin('JPush', {
    web: () => Promise.resolve().then(function () { return web; }).then((m) => new m.JPushWeb()),
});

class JPushWeb extends core.WebPlugin {
    async init() {
        return undefined;
    }
    async getRegistrationID() {
        return undefined;
    }
    async setTags() {
        return undefined;
    }
    async setAlias() {
        return undefined;
    }
    async addListener() {
        return undefined;
    }
    async getRemoteNotification() {
        return undefined;
    }
    async setBadge() {
        return;
    }
    async clearAllNotifications() {
        return;
    }
    async clearNotificationById() {
        return;
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    JPushWeb: JPushWeb
});

exports.JPush = JPush;
//# sourceMappingURL=plugin.cjs.js.map
