var capacitorNavbar = (function (exports, core) {
    'use strict';

    const Navbar = core.registerPlugin('Navbar', {
        web: () => Promise.resolve().then(function () { return web; }).then((m) => new m.NavbarWeb()),
    });

    class NavbarWeb extends core.WebPlugin {
        async setup() {
            return true;
        }
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
        async exitApp() {
            return true;
        }
        async setVisibility() {
            return true;
        }
    }

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        NavbarWeb: NavbarWeb
    });

    exports.Navbar = Navbar;

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
