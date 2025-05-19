'use strict';

var core = require('@capacitor/core');

exports.Scene = void 0;
(function (Scene) {
    Scene[Scene["SESSION"] = 0] = "SESSION";
    Scene[Scene["TIMELINE"] = 1] = "TIMELINE";
    Scene[Scene["FAVORITE"] = 2] = "FAVORITE";
})(exports.Scene || (exports.Scene = {}));
exports.MiniprogramType = void 0;
(function (MiniprogramType) {
    MiniprogramType[MiniprogramType["RELEASE"] = 0] = "RELEASE";
    MiniprogramType[MiniprogramType["TEST"] = 1] = "TEST";
    MiniprogramType[MiniprogramType["PREVIEW"] = 2] = "PREVIEW";
})(exports.MiniprogramType || (exports.MiniprogramType = {}));

const Wechat = core.registerPlugin('Wechat', {
    web: () => Promise.resolve().then(function () { return web; }).then((m) => new m.WechatWeb()),
});

class WechatWeb extends core.WebPlugin {
    async isInstalled() {
        return true;
    }
    async registerApp() {
        return true;
    }
    async shareImageMessage() {
        return true;
    }
    async shareMiniProgramMessage() {
        return true;
    }
    async shareTextMessage() {
        return true;
    }
    async shareWebPageMessage() {
        return true;
    }
    async shareVideoMessage() {
        return true;
    }
    async shareMusicVideoMessage() {
        return true;
    }
    async auth() {
        return true;
    }
    async sendPaymentRequest() {
        return true;
    }
    async openMiniProgram() {
        return true;
    }
    async openCustomerServiceResp() {
        return true;
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    WechatWeb: WechatWeb
});

exports.Wechat = Wechat;
//# sourceMappingURL=plugin.cjs.js.map
