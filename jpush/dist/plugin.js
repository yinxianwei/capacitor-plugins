var capacitorJPush = (function (exports, core) {
    'use strict';

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
    }

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        JPushWeb: JPushWeb
    });

    exports.JPush = JPush;

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
