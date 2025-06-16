var capacitorYPush = (function (exports, core) {
    'use strict';

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
    }

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        YPushWeb: YPushWeb
    });

    exports.YPush = YPush;

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
