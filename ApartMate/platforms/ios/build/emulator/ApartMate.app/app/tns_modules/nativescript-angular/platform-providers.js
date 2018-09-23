Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var frame_1 = require("tns-core-modules/ui/frame");
var page_1 = require("tns-core-modules/ui/page");
var platform_1 = require("tns-core-modules/platform");
exports.APP_ROOT_VIEW = new core_1.InjectionToken("App Root View");
exports.DEVICE = new core_1.InjectionToken("platform device");
exports.PAGE_FACTORY = new core_1.InjectionToken("page factory");
var _rootPageRef;
function setRootPage(page) {
    _rootPageRef = new WeakRef(page);
}
exports.setRootPage = setRootPage;
function getRootPage() {
    return _rootPageRef && _rootPageRef.get();
}
exports.getRootPage = getRootPage;
// Use an exported function to make the AoT compiler happy.
function getDefaultPage() {
    var rootPage = getRootPage();
    if (rootPage instanceof page_1.Page) {
        return rootPage;
    }
    var frame = frame_1.topmost();
    if (frame && frame.currentPage) {
        return frame.currentPage;
    }
    return null;
}
exports.getDefaultPage = getDefaultPage;
exports.defaultPageProvider = { provide: page_1.Page, useFactory: getDefaultPage };
// Use an exported function to make the AoT compiler happy.
function getDefaultFrame() {
    return frame_1.topmost();
}
exports.getDefaultFrame = getDefaultFrame;
exports.defaultFrameProvider = { provide: frame_1.Frame, useFactory: getDefaultFrame };
// Use an exported function to make the AoT compiler happy.
function getDefaultDevice() {
    return platform_1.device;
}
exports.getDefaultDevice = getDefaultDevice;
exports.defaultDeviceProvider = { provide: exports.DEVICE, useFactory: getDefaultDevice };
exports.defaultPageFactory = function (_opts) {
    return new page_1.Page();
};
exports.defaultPageFactoryProvider = { provide: exports.PAGE_FACTORY, useValue: exports.defaultPageFactory };
var FrameService = /** @class */ (function () {
    function FrameService() {
        // TODO: Add any methods that are needed to handle frame/page navigation
        this.frames = [];
    }
    // Return the topmost frame.
    // TabView with frames scenario: topmost() will return the root TabViewItem frame,
    // which could be the wrong topmost frame (modal with nested frame e.g.):
    // TabViewItem -> Frame -> Modal -> Frame2 -> Frame2-Navigation
    FrameService.prototype.getFrame = function () {
        var topmostFrame = frame_1.topmost();
        var _a = this.findFrame(topmostFrame), cachedFrame = _a.cachedFrame, cachedFrameRootOutlet = _a.cachedFrameRootOutlet;
        if (cachedFrame && cachedFrameRootOutlet) {
            var topmostFrameByOutlet = this.getTopmostFrameByOutlet(cachedFrameRootOutlet);
            if (topmostFrameByOutlet && topmostFrameByOutlet !== cachedFrame) {
                topmostFrame = topmostFrameByOutlet;
            }
        }
        return topmostFrame;
    };
    FrameService.prototype.addFrame = function (frame, name, rootOutlet) {
        this.frames.push({ frame: frame, name: name, rootOutlet: rootOutlet });
    };
    FrameService.prototype.removeFrame = function (frame) {
        this.frames = this.frames.filter(function (currentFrame) { return currentFrame.frame !== frame; });
    };
    FrameService.prototype.containsOutlet = function (name) {
        var nameFound = false;
        for (var i = 0; i < this.frames.length; i++) {
            var currentFrame = this.frames[i];
            if (name && currentFrame.name === name) {
                nameFound = true;
                break;
            }
        }
        return nameFound;
    };
    FrameService.prototype.findFrame = function (frame) {
        var cachedFrame;
        var cachedFrameRootOutlet;
        for (var i = 0; i < this.frames.length; i++) {
            var currentFrame = this.frames[i];
            if (currentFrame.frame === frame) {
                cachedFrame = currentFrame;
                cachedFrameRootOutlet = currentFrame.rootOutlet;
                break;
            }
        }
        return { cachedFrame: cachedFrame, cachedFrameRootOutlet: cachedFrameRootOutlet };
    };
    // Return the latest navigated frame from the given outlet branch.
    FrameService.prototype.getTopmostFrameByOutlet = function (rootOutlet) {
        var frame;
        for (var i = this.frames.length - 1; i > 0; i--) {
            var currentFrame = this.frames[i];
            if (currentFrame.rootOutlet === rootOutlet) {
                frame = currentFrame.frame;
                break;
            }
        }
        return frame;
    };
    FrameService.decorators = [
        { type: core_1.Injectable },
    ];
    return FrameService;
}());
exports.FrameService = FrameService;
//# sourceMappingURL=platform-providers.js.map