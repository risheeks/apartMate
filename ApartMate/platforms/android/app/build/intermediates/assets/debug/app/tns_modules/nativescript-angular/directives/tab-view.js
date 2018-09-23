Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var tab_view_1 = require("tns-core-modules/ui/tab-view");
var element_registry_1 = require("../element-registry");
var trace_1 = require("../trace");
var lang_facade_1 = require("../lang-facade");
var TabViewDirective = /** @class */ (function () {
    function TabViewDirective(element) {
        this.tabView = element.nativeElement;
    }
    Object.defineProperty(TabViewDirective.prototype, "selectedIndex", {
        get: function () {
            return this._selectedIndex;
        },
        set: function (value) {
            this._selectedIndex = value;
            if (this.viewInitialized) {
                this.tabView.selectedIndex = this._selectedIndex;
            }
        },
        enumerable: true,
        configurable: true
    });
    TabViewDirective.prototype.ngAfterViewInit = function () {
        this.viewInitialized = true;
        trace_1.rendererLog("this._selectedIndex: " + this._selectedIndex);
        if (!lang_facade_1.isBlank(this._selectedIndex)) {
            this.tabView.selectedIndex = this._selectedIndex;
        }
    };
    TabViewDirective.decorators = [
        { type: core_1.Directive, args: [{
                    selector: "TabView",
                },] },
    ];
    /** @nocollapse */
    TabViewDirective.ctorParameters = function () { return [
        { type: core_1.ElementRef }
    ]; };
    TabViewDirective.propDecorators = {
        selectedIndex: [{ type: core_1.Input }]
    };
    return TabViewDirective;
}());
exports.TabViewDirective = TabViewDirective;
var TabViewItemDirective = /** @class */ (function () {
    function TabViewItemDirective(owner, templateRef, viewContainer) {
        this.owner = owner;
        this.templateRef = templateRef;
        this.viewContainer = viewContainer;
    }
    Object.defineProperty(TabViewItemDirective.prototype, "config", {
        get: function () {
            return this._config || {};
        },
        set: function (config) {
            if (!this._config
                || this._config.iconSource !== config.iconSource
                || this._config.title !== config.title
                || this._config.textTransform !== config.textTransform) {
                this._config = config;
                this.applyConfig();
            }
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TabViewItemDirective.prototype, "title", {
        get: function () {
            return this.config.title;
        },
        set: function (title) {
            this.config = Object.assign(this.config, { title: title });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TabViewItemDirective.prototype, "iconSource", {
        get: function () {
            return this.config.iconSource;
        },
        set: function (iconSource) {
            this.config = Object.assign(this.config, { iconSource: iconSource });
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(TabViewItemDirective.prototype, "textTransform", {
        get: function () {
            return this.config.textTransform;
        },
        set: function (textTransform) {
            this.config = Object.assign(this.config, { textTransform: textTransform });
        },
        enumerable: true,
        configurable: true
    });
    TabViewItemDirective.prototype.ensureItem = function () {
        if (!this.item) {
            this.item = new tab_view_1.TabViewItem();
        }
    };
    TabViewItemDirective.prototype.applyConfig = function () {
        this.ensureItem();
        if (this.config.title) {
            this.item.title = this.config.title;
        }
        if (this.config.iconSource) {
            this.item.iconSource = this.config.iconSource;
        }
        //  TabViewItem textTransform has a default value for Android that kick in
        // only if no value (even a null value) is set.
        if (this.config.textTransform) {
            this.item.textTransform = this.config.textTransform;
        }
    };
    TabViewItemDirective.prototype.ngOnInit = function () {
        this.applyConfig();
        var viewRef = this.viewContainer.createEmbeddedView(this.templateRef);
        // Filter out text nodes and comments
        var realViews = viewRef.rootNodes.filter(function (node) {
            return !(node instanceof element_registry_1.InvisibleNode);
        });
        if (realViews.length > 0) {
            this.item.view = realViews[0];
            var newItems = (this.owner.tabView.items || []).concat([this.item]);
            this.owner.tabView.items = newItems;
        }
    };
    TabViewItemDirective.decorators = [
        { type: core_1.Directive, args: [{
                    selector: "[tabItem]" // tslint:disable-line:directive-selector
                },] },
    ];
    /** @nocollapse */
    TabViewItemDirective.ctorParameters = function () { return [
        { type: TabViewDirective },
        { type: core_1.TemplateRef },
        { type: core_1.ViewContainerRef }
    ]; };
    TabViewItemDirective.propDecorators = {
        config: [{ type: core_1.Input, args: ["tabItem",] }],
        title: [{ type: core_1.Input }],
        iconSource: [{ type: core_1.Input }],
        textTransform: [{ type: core_1.Input }]
    };
    return TabViewItemDirective;
}());
exports.TabViewItemDirective = TabViewItemDirective;
//# sourceMappingURL=tab-view.js.map