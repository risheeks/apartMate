/**
 * @fileoverview added by tsickle
 * @suppress {checkTypes,extraRequire,uselessCode} checked by tsc
 */
/**
 * @license
 * Copyright Google Inc. All Rights Reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be
 * found in the LICENSE file at https://angular.io/license
 */
import { EmptyOutletComponent } from './components/empty_outlet';
import { PRIMARY_OUTLET } from './shared';
/** @typedef {?} */
var Routes;
export { Routes };
/** @typedef {?} */
var UrlMatchResult;
export { UrlMatchResult };
/** @typedef {?} */
var UrlMatcher;
export { UrlMatcher };
/** @typedef {?} */
var Data;
export { Data };
/** @typedef {?} */
var ResolveData;
export { ResolveData };
/** @typedef {?} */
var LoadChildrenCallback;
export { LoadChildrenCallback };
/** @typedef {?} */
var LoadChildren;
export { LoadChildren };
/** @typedef {?} */
var QueryParamsHandling;
export { QueryParamsHandling };
/** @typedef {?} */
var RunGuardsAndResolvers;
export { RunGuardsAndResolvers };
/**
 * See `Routes` for more details.
 *
 * @record
 */
export function Route() { }
/** @type {?|undefined} */
Route.prototype.path;
/** @type {?|undefined} */
Route.prototype.pathMatch;
/** @type {?|undefined} */
Route.prototype.matcher;
/** @type {?|undefined} */
Route.prototype.component;
/** @type {?|undefined} */
Route.prototype.redirectTo;
/** @type {?|undefined} */
Route.prototype.outlet;
/** @type {?|undefined} */
Route.prototype.canActivate;
/** @type {?|undefined} */
Route.prototype.canActivateChild;
/** @type {?|undefined} */
Route.prototype.canDeactivate;
/** @type {?|undefined} */
Route.prototype.canLoad;
/** @type {?|undefined} */
Route.prototype.data;
/** @type {?|undefined} */
Route.prototype.resolve;
/** @type {?|undefined} */
Route.prototype.children;
/** @type {?|undefined} */
Route.prototype.loadChildren;
/** @type {?|undefined} */
Route.prototype.runGuardsAndResolvers;
/**
 * Filled for routes with `loadChildren` once the module has been loaded
 * \@internal
 * @type {?|undefined}
 */
Route.prototype._loadedConfig;
export class LoadedRouterConfig {
    /**
     * @param {?} routes
     * @param {?} module
     */
    constructor(routes, module) {
        this.routes = routes;
        this.module = module;
    }
}
if (false) {
    /** @type {?} */
    LoadedRouterConfig.prototype.routes;
    /** @type {?} */
    LoadedRouterConfig.prototype.module;
}
/**
 * @param {?} config
 * @param {?=} parentPath
 * @return {?}
 */
export function validateConfig(config, parentPath = '') {
    // forEach doesn't iterate undefined values
    for (let i = 0; i < config.length; i++) {
        /** @type {?} */
        const route = config[i];
        /** @type {?} */
        const fullPath = getFullPath(parentPath, route);
        validateNode(route, fullPath);
    }
}
/**
 * @param {?} route
 * @param {?} fullPath
 * @return {?}
 */
function validateNode(route, fullPath) {
    if (!route) {
        throw new Error(`
      Invalid configuration of route '${fullPath}': Encountered undefined route.
      The reason might be an extra comma.

      Example:
      const routes: Routes = [
        { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
        { path: 'dashboard',  component: DashboardComponent },, << two commas
        { path: 'detail/:id', component: HeroDetailComponent }
      ];
    `);
    }
    if (Array.isArray(route)) {
        throw new Error(`Invalid configuration of route '${fullPath}': Array cannot be specified`);
    }
    if (!route.component && !route.children && !route.loadChildren &&
        (route.outlet && route.outlet !== PRIMARY_OUTLET)) {
        throw new Error(`Invalid configuration of route '${fullPath}': a componentless route without children or loadChildren cannot have a named outlet set`);
    }
    if (route.redirectTo && route.children) {
        throw new Error(`Invalid configuration of route '${fullPath}': redirectTo and children cannot be used together`);
    }
    if (route.redirectTo && route.loadChildren) {
        throw new Error(`Invalid configuration of route '${fullPath}': redirectTo and loadChildren cannot be used together`);
    }
    if (route.children && route.loadChildren) {
        throw new Error(`Invalid configuration of route '${fullPath}': children and loadChildren cannot be used together`);
    }
    if (route.redirectTo && route.component) {
        throw new Error(`Invalid configuration of route '${fullPath}': redirectTo and component cannot be used together`);
    }
    if (route.path && route.matcher) {
        throw new Error(`Invalid configuration of route '${fullPath}': path and matcher cannot be used together`);
    }
    if (route.redirectTo === void 0 && !route.component && !route.children && !route.loadChildren) {
        throw new Error(`Invalid configuration of route '${fullPath}'. One of the following must be provided: component, redirectTo, children or loadChildren`);
    }
    if (route.path === void 0 && route.matcher === void 0) {
        throw new Error(`Invalid configuration of route '${fullPath}': routes must have either a path or a matcher specified`);
    }
    if (typeof route.path === 'string' && route.path.charAt(0) === '/') {
        throw new Error(`Invalid configuration of route '${fullPath}': path cannot start with a slash`);
    }
    if (route.path === '' && route.redirectTo !== void 0 && route.pathMatch === void 0) {
        /** @type {?} */
        const exp = `The default value of 'pathMatch' is 'prefix', but often the intent is to use 'full'.`;
        throw new Error(`Invalid configuration of route '{path: "${fullPath}", redirectTo: "${route.redirectTo}"}': please provide 'pathMatch'. ${exp}`);
    }
    if (route.pathMatch !== void 0 && route.pathMatch !== 'full' && route.pathMatch !== 'prefix') {
        throw new Error(`Invalid configuration of route '${fullPath}': pathMatch can only be set to 'prefix' or 'full'`);
    }
    if (route.children) {
        validateConfig(route.children, fullPath);
    }
}
/**
 * @param {?} parentPath
 * @param {?} currentRoute
 * @return {?}
 */
function getFullPath(parentPath, currentRoute) {
    if (!currentRoute) {
        return parentPath;
    }
    if (!parentPath && !currentRoute.path) {
        return '';
    }
    else if (parentPath && !currentRoute.path) {
        return `${parentPath}/`;
    }
    else if (!parentPath && currentRoute.path) {
        return currentRoute.path;
    }
    else {
        return `${parentPath}/${currentRoute.path}`;
    }
}
/**
 * Makes a copy of the config and adds any default required properties.
 * @param {?} r
 * @return {?}
 */
export function standardizeConfig(r) {
    /** @type {?} */
    const children = r.children && r.children.map(standardizeConfig);
    /** @type {?} */
    const c = children ? Object.assign({}, r, { children }) : Object.assign({}, r);
    if (!c.component && (children || c.loadChildren) && (c.outlet && c.outlet !== PRIMARY_OUTLET)) {
        c.component = EmptyOutletComponent;
    }
    return c;
}

//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiY29uZmlnLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vLi4vLi4vLi4vLi4vcGFja2FnZXMvcm91dGVyL3NyYy9jb25maWcudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7Ozs7Ozs7Ozs7QUFVQSxPQUFPLEVBQUMsb0JBQW9CLEVBQUMsTUFBTSwyQkFBMkIsQ0FBQztBQUMvRCxPQUFPLEVBQUMsY0FBYyxFQUFDLE1BQU0sVUFBVSxDQUFDOzs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7O0FBcVh4QyxNQUFNOzs7OztJQUNKLFlBQW1CLE1BQWUsRUFBUyxNQUF3QjtRQUFoRCxXQUFNLEdBQU4sTUFBTSxDQUFTO1FBQVMsV0FBTSxHQUFOLE1BQU0sQ0FBa0I7S0FBSTtDQUN4RTs7Ozs7Ozs7Ozs7O0FBRUQsTUFBTSx5QkFBeUIsTUFBYyxFQUFFLGFBQXFCLEVBQUU7O0lBRXBFLEtBQUssSUFBSSxDQUFDLEdBQUcsQ0FBQyxFQUFFLENBQUMsR0FBRyxNQUFNLENBQUMsTUFBTSxFQUFFLENBQUMsRUFBRSxFQUFFOztRQUN0QyxNQUFNLEtBQUssR0FBVSxNQUFNLENBQUMsQ0FBQyxDQUFDLENBQUM7O1FBQy9CLE1BQU0sUUFBUSxHQUFXLFdBQVcsQ0FBQyxVQUFVLEVBQUUsS0FBSyxDQUFDLENBQUM7UUFDeEQsWUFBWSxDQUFDLEtBQUssRUFBRSxRQUFRLENBQUMsQ0FBQztLQUMvQjtDQUNGOzs7Ozs7QUFFRCxzQkFBc0IsS0FBWSxFQUFFLFFBQWdCO0lBQ2xELElBQUksQ0FBQyxLQUFLLEVBQUU7UUFDVixNQUFNLElBQUksS0FBSyxDQUFDO3dDQUNvQixRQUFROzs7Ozs7Ozs7S0FTM0MsQ0FBQyxDQUFDO0tBQ0o7SUFDRCxJQUFJLEtBQUssQ0FBQyxPQUFPLENBQUMsS0FBSyxDQUFDLEVBQUU7UUFDeEIsTUFBTSxJQUFJLEtBQUssQ0FBQyxtQ0FBbUMsUUFBUSw4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RCxJQUFJLENBQUMsWUFBWSxFQUFFO1FBQ2pCLE9BQU8sVUFBVSxDQUFDO0tBQ25CO0lBQ0QsSUFBSSxDQUFDLFVBQVUsSUFBSSxDQUFDLFlBQVksQ0FBQyxJQUFJLEVBQUU7UUFDckMsT0FBTyxFQUFFLENBQUM7S0FDWDtTQUFNLElBQUksVUFBVSxJQUFJLENBQUMsWUFBWSxDQUFDLElBQUksRUFBRTtRQUMzQyxPQUFPLEdBQUcsVUFBVSxHQUFHLENBQUM7S0FDekI7U0FBTSxJQUFJLENBQUMsVUFBVSxJQUFJLFlBQVksQ0FBQyxJQUFJLEVBQUU7UUFDM0MsT0FBTyxZQUFZLENBQUMsSUFBSSxDQUFDO0tBQzFCO1NBQU07UUFDTCxPQUFPLEdBQUcsVUFBVSxJQUFJLFlBQVksQ0FBQyxJQUFJLEVBQUUsQ0FBQztLQUM3QztDQUNGOzs7Ozs7QUFLRCxNQUFNLDRCQUE0QixDQUFROztJQUN4QyxNQUFNLFFBQVEsR0FBRyxDQUFDLENBQUMsUUFBUSxJQUFJLENBQUMsQ0FBQyxRQUFRLENBQUMsR0FBRyxDQUFDLGlCQUFpQixDQUFDLENBQUM7O0lBQ2pFLE1BQU0sQ0FBQyxHQUFHLFFBQVEsQ0FBQyxDQUFDLG1CQUFLLENBQUMsSUFBRSxRQUFRLElBQUUsQ0FBQyxtQkFBSyxDQUFDLENBQUMsQ0FBQztJQUMvQyxJQUFJLENBQUMsQ0FBQyxDQUFDLFNBQVMsSUFBSSxDQUFDLFFBQVEsSUFBSSxDQUFDLENBQUMsWUFBWSxDQUFDLElBQUksQ0FBQyxDQUFDLENBQUMsTUFBTSxJQUFJLENBQUMsQ0FBQyxNQUFNLEtBQUssY0FBYyxDQUFDLEVBQUU7UUFDN0YsQ0FBQyxDQUFDLFNBQVMsR0FBRyxvQkFBb0IsQ0FBQztLQUNwQztJQUNELE9BQU8sQ0FBQyxDQUFDO0NBQ1YiLCJzb3VyY2VzQ29udGVudCI6WyIvKipcbiAqIEBsaWNlbnNlXG4gKiBDb3B5cmlnaHQgR29vZ2xlIEluYy4gQWxsIFJpZ2h0cyBSZXNlcnZlZC5cbiAqXG4gKiBVc2Ugb2YgdGhpcyBzb3VyY2UgY29kZSBpcyBnb3Zlcm5lZCBieSBhbiBNSVQtc3R5bGUgbGljZW5zZSB0aGF0IGNhbiBiZVxuICogZm91bmQgaW4gdGhlIExJQ0VOU0UgZmlsZSBhdCBodHRwczovL2FuZ3VsYXIuaW8vbGljZW5zZVxuICovXG5cbmltcG9ydCB7TmdNb2R1bGVGYWN0b3J5LCBOZ01vZHVsZVJlZiwgVHlwZX0gZnJvbSAnQGFuZ3VsYXIvY29yZSc7XG5pbXBvcnQge09ic2VydmFibGV9IGZyb20gJ3J4anMnO1xuaW1wb3J0IHtFbXB0eU91dGxldENvbXBvbmVudH0gZnJvbSAnLi9jb21wb25lbnRzL2VtcHR5X291dGxldCc7XG5pbXBvcnQge1BSSU1BUllfT1VUTEVUfSBmcm9tICcuL3NoYXJlZCc7XG5pbXBvcnQge1VybFNlZ21lbnQsIFVybFNlZ21lbnRHcm91cH0gZnJvbSAnLi91cmxfdHJlZSc7XG5cbi8qKlxuICogQGRlc2NyaXB0aW9uXG4gKlxuICogUmVwcmVzZW50cyByb3V0ZXIgY29uZmlndXJhdGlvbi5cbiAqXG4gKiBgUm91dGVzYCBpcyBhbiBhcnJheSBvZiByb3V0ZSBjb25maWd1cmF0aW9ucy4gRWFjaCBvbmUgaGFzIHRoZSBmb2xsb3dpbmcgcHJvcGVydGllczpcbiAqXG4gKiAtIGBwYXRoYCBpcyBhIHN0cmluZyB0aGF0IHVzZXMgdGhlIHJvdXRlIG1hdGNoZXIgRFNMLlxuICogLSBgcGF0aE1hdGNoYCBpcyBhIHN0cmluZyB0aGF0IHNwZWNpZmllcyB0aGUgbWF0Y2hpbmcgc3RyYXRlZ3kuXG4gKiAtIGBtYXRjaGVyYCBkZWZpbmVzIGEgY3VzdG9tIHN0cmF0ZWd5IGZvciBwYXRoIG1hdGNoaW5nIGFuZCBzdXBlcnNlZGVzIGBwYXRoYCBhbmQgYHBhdGhNYXRjaGAuXG4gKiAtIGBjb21wb25lbnRgIGlzIGEgY29tcG9uZW50IHR5cGUuXG4gKiAtIGByZWRpcmVjdFRvYCBpcyB0aGUgdXJsIGZyYWdtZW50IHdoaWNoIHdpbGwgcmVwbGFjZSB0aGUgY3VycmVudCBtYXRjaGVkIHNlZ21lbnQuXG4gKiAtIGBvdXRsZXRgIGlzIHRoZSBuYW1lIG9mIHRoZSBvdXRsZXQgdGhlIGNvbXBvbmVudCBzaG91bGQgYmUgcGxhY2VkIGludG8uXG4gKiAtIGBjYW5BY3RpdmF0ZWAgaXMgYW4gYXJyYXkgb2YgREkgdG9rZW5zIHVzZWQgdG8gbG9vayB1cCBDYW5BY3RpdmF0ZSBoYW5kbGVycy4gU2VlXG4gKiAgIGBDYW5BY3RpdmF0ZWAgZm9yIG1vcmUgaW5mby5cbiAqIC0gYGNhbkFjdGl2YXRlQ2hpbGRgIGlzIGFuIGFycmF5IG9mIERJIHRva2VucyB1c2VkIHRvIGxvb2sgdXAgQ2FuQWN0aXZhdGVDaGlsZCBoYW5kbGVycy4gU2VlXG4gKiAgIGBDYW5BY3RpdmF0ZUNoaWxkYCBmb3IgbW9yZSBpbmZvLlxuICogLSBgY2FuRGVhY3RpdmF0ZWAgaXMgYW4gYXJyYXkgb2YgREkgdG9rZW5zIHVzZWQgdG8gbG9vayB1cCBDYW5EZWFjdGl2YXRlIGhhbmRsZXJzLiBTZWVcbiAqICAgYENhbkRlYWN0aXZhdGVgIGZvciBtb3JlIGluZm8uXG4gKiAtIGBjYW5Mb2FkYCBpcyBhbiBhcnJheSBvZiBESSB0b2tlbnMgdXNlZCB0byBsb29rIHVwIENhbkxvYWQgaGFuZGxlcnMuIFNlZVxuICogICBgQ2FuTG9hZGAgZm9yIG1vcmUgaW5mby5cbiAqIC0gYGRhdGFgIGlzIGFkZGl0aW9uYWwgZGF0YSBwcm92aWRlZCB0byB0aGUgY29tcG9uZW50IHZpYSBgQWN0aXZhdGVkUm91dGVgLlxuICogLSBgcmVzb2x2ZWAgaXMgYSBtYXAgb2YgREkgdG9rZW5zIHVzZWQgdG8gbG9vayB1cCBkYXRhIHJlc29sdmVycy4gU2VlIGBSZXNvbHZlYCBmb3IgbW9yZVxuICogICBpbmZvLlxuICogLSBgcnVuR3VhcmRzQW5kUmVzb2x2ZXJzYCBkZWZpbmVzIHdoZW4gZ3VhcmRzIGFuZCByZXNvbHZlcnMgd2lsbCBiZSBydW4uIEJ5IGRlZmF1bHQgdGhleSBydW4gb25seVxuICogICAgd2hlbiB0aGUgbWF0cml4IHBhcmFtZXRlcnMgb2YgdGhlIHJvdXRlIGNoYW5nZS4gV2hlbiBzZXQgdG8gYHBhcmFtc09yUXVlcnlQYXJhbXNDaGFuZ2VgIHRoZXlcbiAqICAgIHdpbGwgYWxzbyBydW4gd2hlbiBxdWVyeSBwYXJhbXMgY2hhbmdlLiBBbmQgd2hlbiBzZXQgdG8gYGFsd2F5c2AsIHRoZXkgd2lsbCBydW4gZXZlcnkgdGltZS5cbiAqIC0gYGNoaWxkcmVuYCBpcyBhbiBhcnJheSBvZiBjaGlsZCByb3V0ZSBkZWZpbml0aW9ucy5cbiAqIC0gYGxvYWRDaGlsZHJlbmAgaXMgYSByZWZlcmVuY2UgdG8gbGF6eSBsb2FkZWQgY2hpbGQgcm91dGVzLiBTZWUgYExvYWRDaGlsZHJlbmAgZm9yIG1vcmVcbiAqICAgaW5mby5cbiAqXG4gKiAjIyMgU2ltcGxlIENvbmZpZ3VyYXRpb25cbiAqXG4gKiBgYGBcbiAqIFt7XG4gKiAgIHBhdGg6ICd0ZWFtLzppZCcsXG4gICogIGNvbXBvbmVudDogVGVhbSxcbiAqICAgY2hpbGRyZW46IFt7XG4gKiAgICAgcGF0aDogJ3VzZXIvOm5hbWUnLFxuICogICAgIGNvbXBvbmVudDogVXNlclxuICogICB9XVxuICogfV1cbiAqIGBgYFxuICpcbiAqIFdoZW4gbmF2aWdhdGluZyB0byBgL3RlYW0vMTEvdXNlci9ib2JgLCB0aGUgcm91dGVyIHdpbGwgY3JlYXRlIHRoZSB0ZWFtIGNvbXBvbmVudCB3aXRoIHRoZSB1c2VyXG4gKiBjb21wb25lbnQgaW4gaXQuXG4gKlxuICogIyMjIE11bHRpcGxlIE91dGxldHNcbiAqXG4gKiBgYGBcbiAqIFt7XG4gKiAgIHBhdGg6ICd0ZWFtLzppZCcsXG4gKiAgIGNvbXBvbmVudDogVGVhbVxuICogfSwge1xuICogICBwYXRoOiAnY2hhdC86dXNlcicsXG4gKiAgIGNvbXBvbmVudDogQ2hhdFxuICogICBvdXRsZXQ6ICdhdXgnXG4gKiB9XVxuICogYGBgXG4gKlxuICogV2hlbiBuYXZpZ2F0aW5nIHRvIGAvdGVhbS8xMShhdXg6Y2hhdC9qaW0pYCwgdGhlIHJvdXRlciB3aWxsIGNyZWF0ZSB0aGUgdGVhbSBjb21wb25lbnQgbmV4dCB0b1xuICogdGhlIGNoYXQgY29tcG9uZW50LiBUaGUgY2hhdCBjb21wb25lbnQgd2lsbCBiZSBwbGFjZWQgaW50byB0aGUgYXV4IG91dGxldC5cbiAqXG4gKiAjIyMgV2lsZCBDYXJkc1xuICpcbiAqIGBgYFxuICogW3tcbiAqICAgcGF0aDogJyoqJyxcbiAqICAgY29tcG9uZW50OiBTaW5rXG4gKiB9XVxuICogYGBgXG4gKlxuICogUmVnYXJkbGVzcyBvZiB3aGVyZSB5b3UgbmF2aWdhdGUgdG8sIHRoZSByb3V0ZXIgd2lsbCBpbnN0YW50aWF0ZSB0aGUgc2luayBjb21wb25lbnQuXG4gKlxuICogIyMjIFJlZGlyZWN0c1xuICpcbiAqIGBgYFxuICogW3tcbiAqICAgcGF0aDogJ3RlYW0vOmlkJyxcbiAqICAgY29tcG9uZW50OiBUZWFtLFxuICogICBjaGlsZHJlbjogW3tcbiAqICAgICBwYXRoOiAnbGVnYWN5L3VzZXIvOm5hbWUnLFxuICogICAgIHJlZGlyZWN0VG86ICd1c2VyLzpuYW1lJ1xuICogICB9LCB7XG4gKiAgICAgcGF0aDogJ3VzZXIvOm5hbWUnLFxuICogICAgIGNvbXBvbmVudDogVXNlclxuICogICB9XVxuICogfV1cbiAqIGBgYFxuICpcbiAqIFdoZW4gbmF2aWdhdGluZyB0byAnL3RlYW0vMTEvbGVnYWN5L3VzZXIvamltJywgdGhlIHJvdXRlciB3aWxsIGNoYW5nZSB0aGUgdXJsIHRvXG4gKiAnL3RlYW0vMTEvdXNlci9qaW0nLCBhbmQgdGhlbiB3aWxsIGluc3RhbnRpYXRlIHRoZSB0ZWFtIGNvbXBvbmVudCB3aXRoIHRoZSB1c2VyIGNvbXBvbmVudFxuICogaW4gaXQuXG4gKlxuICogSWYgdGhlIGByZWRpcmVjdFRvYCB2YWx1ZSBzdGFydHMgd2l0aCBhICcvJywgdGhlbiBpdCBpcyBhbiBhYnNvbHV0ZSByZWRpcmVjdC4gRS5nLiwgaWYgaW4gdGhlXG4gKiBleGFtcGxlIGFib3ZlIHdlIGNoYW5nZSB0aGUgYHJlZGlyZWN0VG9gIHRvIGAvdXNlci86bmFtZWAsIHRoZSByZXN1bHQgdXJsIHdpbGwgYmUgJy91c2VyL2ppbScuXG4gKlxuICogIyMjIEVtcHR5IFBhdGhcbiAqXG4gKiBFbXB0eS1wYXRoIHJvdXRlIGNvbmZpZ3VyYXRpb25zIGNhbiBiZSB1c2VkIHRvIGluc3RhbnRpYXRlIGNvbXBvbmVudHMgdGhhdCBkbyBub3QgJ2NvbnN1bWUnXG4gKiBhbnkgdXJsIHNlZ21lbnRzLiBMZXQncyBsb29rIGF0IHRoZSBmb2xsb3dpbmcgY29uZmlndXJhdGlvbjpcbiAqXG4gKiBgYGBcbiAqIFt7XG4gKiAgIHBhdGg6ICd0ZWFtLzppZCcsXG4gKiAgIGNvbXBvbmVudDogVGVhbSxcbiAqICAgY2hpbGRyZW46IFt7XG4gKiAgICAgcGF0aDogJycsXG4gKiAgICAgY29tcG9uZW50OiBBbGxVc2Vyc1xuICogICB9LCB7XG4gKiAgICAgcGF0aDogJ3VzZXIvOm5hbWUnLFxuICogICAgIGNvbXBvbmVudDogVXNlclxuICogICB9XVxuICogfV1cbiAqIGBgYFxuICpcbiAqIFdoZW4gbmF2aWdhdGluZyB0byBgL3RlYW0vMTFgLCB0aGUgcm91dGVyIHdpbGwgaW5zdGFudGlhdGUgdGhlIEFsbFVzZXJzIGNvbXBvbmVudC5cbiAqXG4gKiBFbXB0eS1wYXRoIHJvdXRlcyBjYW4gaGF2ZSBjaGlsZHJlbi5cbiAqXG4gKiBgYGBcbiAqIFt7XG4gKiAgIHBhdGg6ICd0ZWFtLzppZCcsXG4gKiAgIGNvbXBvbmVudDogVGVhbSxcbiAqICAgY2hpbGRyZW46IFt7XG4gKiAgICAgcGF0aDogJycsXG4gKiAgICAgY29tcG9uZW50OiBXcmFwcGVyQ21wLFxuICogICAgIGNoaWxkcmVuOiBbe1xuICogICAgICAgcGF0aDogJ3VzZXIvOm5hbWUnLFxuICogICAgICAgY29tcG9uZW50OiBVc2VyXG4gKiAgICAgfV1cbiAqICAgfV1cbiAqIH1dXG4gKiBgYGBcbiAqXG4gKiBXaGVuIG5hdmlnYXRpbmcgdG8gYC90ZWFtLzExL3VzZXIvamltYCwgdGhlIHJvdXRlciB3aWxsIGluc3RhbnRpYXRlIHRoZSB3cmFwcGVyIGNvbXBvbmVudCB3aXRoXG4gKiB0aGUgdXNlciBjb21wb25lbnQgaW4gaXQuXG4gKlxuICogQW4gZW1wdHkgcGF0aCByb3V0ZSBpbmhlcml0cyBpdHMgcGFyZW50J3MgcGFyYW1zIGFuZCBkYXRhLiBUaGlzIGlzIGJlY2F1c2UgaXQgY2Fubm90IGhhdmUgaXRzXG4gKiBvd24gcGFyYW1zLCBhbmQsIGFzIGEgcmVzdWx0LCBpdCBvZnRlbiB1c2VzIGl0cyBwYXJlbnQncyBwYXJhbXMgYW5kIGRhdGEgYXMgaXRzIG93bi5cbiAqXG4gKiAjIyMgTWF0Y2hpbmcgU3RyYXRlZ3lcbiAqXG4gKiBCeSBkZWZhdWx0IHRoZSByb3V0ZXIgd2lsbCBsb29rIGF0IHdoYXQgaXMgbGVmdCBpbiB0aGUgdXJsLCBhbmQgY2hlY2sgaWYgaXQgc3RhcnRzIHdpdGhcbiAqIHRoZSBzcGVjaWZpZWQgcGF0aCAoZS5nLiwgYC90ZWFtLzExL3VzZXJgIHN0YXJ0cyB3aXRoIGB0ZWFtLzppZGApLlxuICpcbiAqIFdlIGNhbiBjaGFuZ2UgdGhlIG1hdGNoaW5nIHN0cmF0ZWd5IHRvIG1ha2Ugc3VyZSB0aGF0IHRoZSBwYXRoIGNvdmVycyB0aGUgd2hvbGUgdW5jb25zdW1lZCB1cmwsXG4gKiB3aGljaCBpcyBha2luIHRvIGB1bmNvbnN1bWVkVXJsID09PSBwYXRoYCBvciBgJGAgcmVndWxhciBleHByZXNzaW9ucy5cbiAqXG4gKiBUaGlzIGlzIHBhcnRpY3VsYXJseSBpbXBvcnRhbnQgd2hlbiByZWRpcmVjdGluZyBlbXB0eS1wYXRoIHJvdXRlcy5cbiAqXG4gKiBgYGBcbiAqIFt7XG4gKiAgIHBhdGg6ICcnLFxuICogICBwYXRoTWF0Y2g6ICdwcmVmaXgnLCAvL2RlZmF1bHRcbiAqICAgcmVkaXJlY3RUbzogJ21haW4nXG4gKiB9LCB7XG4gKiAgIHBhdGg6ICdtYWluJyxcbiAqICAgY29tcG9uZW50OiBNYWluXG4gKiB9XVxuICogYGBgXG4gKlxuICogU2luY2UgYW4gZW1wdHkgcGF0aCBpcyBhIHByZWZpeCBvZiBhbnkgdXJsLCBldmVuIHdoZW4gbmF2aWdhdGluZyB0byAnL21haW4nLCB0aGUgcm91dGVyIHdpbGxcbiAqIHN0aWxsIGFwcGx5IHRoZSByZWRpcmVjdC5cbiAqXG4gKiBJZiBgcGF0aE1hdGNoOiBmdWxsYCBpcyBwcm92aWRlZCwgdGhlIHJvdXRlciB3aWxsIGFwcGx5IHRoZSByZWRpcmVjdCBpZiBhbmQgb25seSBpZiBuYXZpZ2F0aW5nIHRvXG4gKiAnLycuXG4gKlxuICogYGBgXG4gKiBbe1xuICogICBwYXRoOiAnJyxcbiAqICAgcGF0aE1hdGNoOiAnZnVsbCcsXG4gKiAgIHJlZGlyZWN0VG86ICdtYWluJ1xuICogfSwge1xuICogICBwYXRoOiAnbWFpbicsXG4gKiAgIGNvbXBvbmVudDogTWFpblxuICogfV1cbiAqIGBgYFxuICpcbiAqICMjIyBDb21wb25lbnRsZXNzIFJvdXRlc1xuICpcbiAqIEl0IGlzIHVzZWZ1bCBhdCB0aW1lcyB0byBoYXZlIHRoZSBhYmlsaXR5IHRvIHNoYXJlIHBhcmFtZXRlcnMgYmV0d2VlbiBzaWJsaW5nIGNvbXBvbmVudHMuXG4gKlxuICogU2F5IHdlIGhhdmUgdHdvIGNvbXBvbmVudHMtLUNoaWxkQ21wIGFuZCBBdXhDbXAtLXRoYXQgd2Ugd2FudCB0byBwdXQgbmV4dCB0byBlYWNoIG90aGVyIGFuZCBib3RoXG4gKiBvZiB0aGVtIHJlcXVpcmUgc29tZSBpZCBwYXJhbWV0ZXIuXG4gKlxuICogT25lIHdheSB0byBkbyB0aGF0IHdvdWxkIGJlIHRvIGhhdmUgYSBib2d1cyBwYXJlbnQgY29tcG9uZW50LCBzbyBib3RoIHRoZSBzaWJsaW5ncyBjYW4gZ2V0IHRoZSBpZFxuICogcGFyYW1ldGVyIGZyb20gaXQuIFRoaXMgaXMgbm90IGlkZWFsLiBJbnN0ZWFkLCB5b3UgY2FuIHVzZSBhIGNvbXBvbmVudGxlc3Mgcm91dGUuXG4gKlxuICogYGBgXG4gKiBbe1xuICogICAgcGF0aDogJ3BhcmVudC86aWQnLFxuICogICAgY2hpbGRyZW46IFtcbiAqICAgICAgeyBwYXRoOiAnYScsIGNvbXBvbmVudDogTWFpbkNoaWxkIH0sXG4gKiAgICAgIHsgcGF0aDogJ2InLCBjb21wb25lbnQ6IEF1eENoaWxkLCBvdXRsZXQ6ICdhdXgnIH1cbiAqICAgIF1cbiAqIH1dXG4gKiBgYGBcbiAqXG4gKiBTbyB3aGVuIG5hdmlnYXRpbmcgdG8gYHBhcmVudC8xMC8oYS8vYXV4OmIpYCwgdGhlIHJvdXRlIHdpbGwgaW5zdGFudGlhdGUgdGhlIG1haW4gY2hpbGQgYW5kIGF1eFxuICogY2hpbGQgY29tcG9uZW50cyBuZXh0IHRvIGVhY2ggb3RoZXIuIEluIHRoaXMgZXhhbXBsZSwgdGhlIGFwcGxpY2F0aW9uIGNvbXBvbmVudFxuICogaGFzIHRvIGhhdmUgdGhlIHByaW1hcnkgYW5kIGF1eCBvdXRsZXRzIGRlZmluZWQuXG4gKlxuICogVGhlIHJvdXRlciB3aWxsIGFsc28gbWVyZ2UgdGhlIGBwYXJhbXNgLCBgZGF0YWAsIGFuZCBgcmVzb2x2ZWAgb2YgdGhlIGNvbXBvbmVudGxlc3MgcGFyZW50IGludG9cbiAqIHRoZSBgcGFyYW1zYCwgYGRhdGFgLCBhbmQgYHJlc29sdmVgIG9mIHRoZSBjaGlsZHJlbi4gVGhpcyBpcyBkb25lIGJlY2F1c2UgdGhlcmUgaXMgbm8gY29tcG9uZW50XG4gKiB0aGF0IGNhbiBpbmplY3QgdGhlIGFjdGl2YXRlZCByb3V0ZSBvZiB0aGUgY29tcG9uZW50bGVzcyBwYXJlbnQuXG4gKlxuICogVGhpcyBpcyBlc3BlY2lhbGx5IHVzZWZ1bCB3aGVuIGNoaWxkIGNvbXBvbmVudHMgYXJlIGRlZmluZWQgYXMgZm9sbG93czpcbiAqXG4gKiBgYGBcbiAqIFt7XG4gKiAgICBwYXRoOiAncGFyZW50LzppZCcsXG4gKiAgICBjaGlsZHJlbjogW1xuICogICAgICB7IHBhdGg6ICcnLCBjb21wb25lbnQ6IE1haW5DaGlsZCB9LFxuICogICAgICB7IHBhdGg6ICcnLCBjb21wb25lbnQ6IEF1eENoaWxkLCBvdXRsZXQ6ICdhdXgnIH1cbiAqICAgIF1cbiAqIH1dXG4gKiBgYGBcbiAqXG4gKiBXaXRoIHRoaXMgY29uZmlndXJhdGlvbiBpbiBwbGFjZSwgbmF2aWdhdGluZyB0byAnL3BhcmVudC8xMCcgd2lsbCBjcmVhdGUgdGhlIG1haW4gY2hpbGQgYW5kIGF1eFxuICogY29tcG9uZW50cy5cbiAqXG4gKiAjIyMgTGF6eSBMb2FkaW5nXG4gKlxuICogTGF6eSBsb2FkaW5nIHNwZWVkcyB1cCBvdXIgYXBwbGljYXRpb24gbG9hZCB0aW1lIGJ5IHNwbGl0dGluZyBpdCBpbnRvIG11bHRpcGxlIGJ1bmRsZXMsIGFuZFxuICogbG9hZGluZyB0aGVtIG9uIGRlbWFuZC4gVGhlIHJvdXRlciBpcyBkZXNpZ25lZCB0byBtYWtlIGxhenkgbG9hZGluZyBzaW1wbGUgYW5kIGVhc3kuIEluc3RlYWQgb2ZcbiAqIHByb3ZpZGluZyB0aGUgY2hpbGRyZW4gcHJvcGVydHksIHlvdSBjYW4gcHJvdmlkZSB0aGUgYGxvYWRDaGlsZHJlbmAgcHJvcGVydHksIGFzIGZvbGxvd3M6XG4gKlxuICogYGBgXG4gKiBbe1xuICogICBwYXRoOiAndGVhbS86aWQnLFxuICogICBjb21wb25lbnQ6IFRlYW0sXG4gKiAgIGxvYWRDaGlsZHJlbjogJ3RlYW0nXG4gKiB9XVxuICogYGBgXG4gKlxuICogVGhlIHJvdXRlciB3aWxsIHVzZSByZWdpc3RlcmVkIE5nTW9kdWxlRmFjdG9yeUxvYWRlciB0byBmZXRjaCBhbiBOZ01vZHVsZSBhc3NvY2lhdGVkIHdpdGggJ3RlYW0nLlxuICogVGhlbiBpdCB3aWxsIGV4dHJhY3QgdGhlIHNldCBvZiByb3V0ZXMgZGVmaW5lZCBpbiB0aGF0IE5nTW9kdWxlLCBhbmQgd2lsbCB0cmFuc3BhcmVudGx5IGFkZFxuICogdGhvc2Ugcm91dGVzIHRvIHRoZSBtYWluIGNvbmZpZ3VyYXRpb24uXG4gKlxuICovXG5leHBvcnQgdHlwZSBSb3V0ZXMgPSBSb3V0ZVtdO1xuXG4vKipcbiAqIEBkZXNjcmlwdGlvbiBSZXByZXNlbnRzIHRoZSByZXN1bHRzIG9mIHRoZSBVUkwgbWF0Y2hpbmcuXG4gKlxuICogKiBgY29uc3VtZWRgIGlzIGFuIGFycmF5IG9mIHRoZSBjb25zdW1lZCBVUkwgc2VnbWVudHMuXG4gKiAqIGBwb3NQYXJhbXNgIGlzIGEgbWFwIG9mIHBvc2l0aW9uYWwgcGFyYW1ldGVycy5cbiAqXG4gKiBAZXhwZXJpbWVudGFsXG4gKi9cbmV4cG9ydCB0eXBlIFVybE1hdGNoUmVzdWx0ID0ge1xuICBjb25zdW1lZDogVXJsU2VnbWVudFtdOyBwb3NQYXJhbXM/OiB7W25hbWU6IHN0cmluZ106IFVybFNlZ21lbnR9O1xufTtcblxuLyoqXG4gKiBAZGVzY3JpcHRpb25cbiAqXG4gKiBBIGZ1bmN0aW9uIG1hdGNoaW5nIFVSTHNcbiAqXG4gKiBBIGN1c3RvbSBVUkwgbWF0Y2hlciBjYW4gYmUgcHJvdmlkZWQgd2hlbiBhIGNvbWJpbmF0aW9uIG9mIGBwYXRoYCBhbmQgYHBhdGhNYXRjaGAgaXNuJ3RcbiAqIGV4cHJlc3NpdmUgZW5vdWdoLlxuICpcbiAqIEZvciBpbnN0YW5jZSwgdGhlIGZvbGxvd2luZyBtYXRjaGVyIG1hdGNoZXMgaHRtbCBmaWxlcy5cbiAqXG4gKiBgYGBcbiAqIGV4cG9ydCBmdW5jdGlvbiBodG1sRmlsZXModXJsOiBVcmxTZWdtZW50W10pIHtcbiAqICAgcmV0dXJuIHVybC5sZW5ndGggPT09IDEgJiYgdXJsWzBdLnBhdGguZW5kc1dpdGgoJy5odG1sJykgPyAoe2NvbnN1bWVkOiB1cmx9KSA6IG51bGw7XG4gKiB9XG4gKlxuICogZXhwb3J0IGNvbnN0IHJvdXRlcyA9IFt7IG1hdGNoZXI6IGh0bWxGaWxlcywgY29tcG9uZW50OiBBbnlDb21wb25lbnQgfV07XG4gKiBgYGBcbiAqXG4gKiBAZXhwZXJpbWVudGFsXG4gKi9cbmV4cG9ydCB0eXBlIFVybE1hdGNoZXIgPSAoc2VnbWVudHM6IFVybFNlZ21lbnRbXSwgZ3JvdXA6IFVybFNlZ21lbnRHcm91cCwgcm91dGU6IFJvdXRlKSA9PlxuICAgIFVybE1hdGNoUmVzdWx0O1xuXG4vKipcbiAqIEBkZXNjcmlwdGlvblxuICpcbiAqIFJlcHJlc2VudHMgdGhlIHN0YXRpYyBkYXRhIGFzc29jaWF0ZWQgd2l0aCBhIHBhcnRpY3VsYXIgcm91dGUuXG4gKlxuICogU2VlIGBSb3V0ZXNgIGZvciBtb3JlIGRldGFpbHMuXG4gKlxuICovXG5leHBvcnQgdHlwZSBEYXRhID0ge1xuICBbbmFtZTogc3RyaW5nXTogYW55XG59O1xuXG4vKipcbiAqIEBkZXNjcmlwdGlvblxuICpcbiAqIFJlcHJlc2VudHMgdGhlIHJlc29sdmVkIGRhdGEgYXNzb2NpYXRlZCB3aXRoIGEgcGFydGljdWxhciByb3V0ZS5cbiAqXG4gKiBTZWUgYFJvdXRlc2AgZm9yIG1vcmUgZGV0YWlscy5cbiAqXG4gKi9cbmV4cG9ydCB0eXBlIFJlc29sdmVEYXRhID0ge1xuICBbbmFtZTogc3RyaW5nXTogYW55XG59O1xuXG4vKipcbiAqIEBkZXNjcmlwdGlvblxuICpcbiAqIFRoZSB0eXBlIG9mIGBsb2FkQ2hpbGRyZW5gLlxuICpcbiAqIFNlZSBgUm91dGVzYCBmb3IgbW9yZSBkZXRhaWxzLlxuICpcbiAqL1xuZXhwb3J0IHR5cGUgTG9hZENoaWxkcmVuQ2FsbGJhY2sgPSAoKSA9PlxuICAgIFR5cGU8YW55PnwgTmdNb2R1bGVGYWN0b3J5PGFueT58IFByb21pc2U8VHlwZTxhbnk+PnwgT2JzZXJ2YWJsZTxUeXBlPGFueT4+O1xuXG4vKipcbiAqIEBkZXNjcmlwdGlvblxuICpcbiAqIFRoZSB0eXBlIG9mIGBsb2FkQ2hpbGRyZW5gLlxuICpcbiAqIFNlZSBgUm91dGVzYCBmb3IgbW9yZSBkZXRhaWxzLlxuICpcbiAqL1xuZXhwb3J0IHR5cGUgTG9hZENoaWxkcmVuID0gc3RyaW5nIHwgTG9hZENoaWxkcmVuQ2FsbGJhY2s7XG5cbi8qKlxuICogQGRlc2NyaXB0aW9uXG4gKlxuICogVGhlIHR5cGUgb2YgYHF1ZXJ5UGFyYW1zSGFuZGxpbmdgLlxuICpcbiAqIFNlZSBgUm91dGVyTGlua2AgZm9yIG1vcmUgZGV0YWlscy5cbiAqXG4gKi9cbmV4cG9ydCB0eXBlIFF1ZXJ5UGFyYW1zSGFuZGxpbmcgPSAnbWVyZ2UnIHwgJ3ByZXNlcnZlJyB8ICcnO1xuXG4vKipcbiAqIEBkZXNjcmlwdGlvblxuICpcbiAqIFRoZSB0eXBlIG9mIGBydW5HdWFyZHNBbmRSZXNvbHZlcnNgLlxuICpcbiAqIFNlZSBgUm91dGVzYCBmb3IgbW9yZSBkZXRhaWxzLlxuICogQGV4cGVyaW1lbnRhbFxuICovXG5leHBvcnQgdHlwZSBSdW5HdWFyZHNBbmRSZXNvbHZlcnMgPSAncGFyYW1zQ2hhbmdlJyB8ICdwYXJhbXNPclF1ZXJ5UGFyYW1zQ2hhbmdlJyB8ICdhbHdheXMnO1xuXG4vKipcbiAqIFNlZSBgUm91dGVzYCBmb3IgbW9yZSBkZXRhaWxzLlxuICpcbiAqL1xuZXhwb3J0IGludGVyZmFjZSBSb3V0ZSB7XG4gIHBhdGg/OiBzdHJpbmc7XG4gIHBhdGhNYXRjaD86IHN0cmluZztcbiAgbWF0Y2hlcj86IFVybE1hdGNoZXI7XG4gIGNvbXBvbmVudD86IFR5cGU8YW55PjtcbiAgcmVkaXJlY3RUbz86IHN0cmluZztcbiAgb3V0bGV0Pzogc3RyaW5nO1xuICBjYW5BY3RpdmF0ZT86IGFueVtdO1xuICBjYW5BY3RpdmF0ZUNoaWxkPzogYW55W107XG4gIGNhbkRlYWN0aXZhdGU/OiBhbnlbXTtcbiAgY2FuTG9hZD86IGFueVtdO1xuICBkYXRhPzogRGF0YTtcbiAgcmVzb2x2ZT86IFJlc29sdmVEYXRhO1xuICBjaGlsZHJlbj86IFJvdXRlcztcbiAgbG9hZENoaWxkcmVuPzogTG9hZENoaWxkcmVuO1xuICBydW5HdWFyZHNBbmRSZXNvbHZlcnM/OiBSdW5HdWFyZHNBbmRSZXNvbHZlcnM7XG4gIC8qKlxuICAgKiBGaWxsZWQgZm9yIHJvdXRlcyB3aXRoIGBsb2FkQ2hpbGRyZW5gIG9uY2UgdGhlIG1vZHVsZSBoYXMgYmVlbiBsb2FkZWRcbiAgICogQGludGVybmFsXG4gICAqL1xuICBfbG9hZGVkQ29uZmlnPzogTG9hZGVkUm91dGVyQ29uZmlnO1xufVxuXG5leHBvcnQgY2xhc3MgTG9hZGVkUm91dGVyQ29uZmlnIHtcbiAgY29uc3RydWN0b3IocHVibGljIHJvdXRlczogUm91dGVbXSwgcHVibGljIG1vZHVsZTogTmdNb2R1bGVSZWY8YW55Pikge31cbn1cblxuZXhwb3J0IGZ1bmN0aW9uIHZhbGlkYXRlQ29uZmlnKGNvbmZpZzogUm91dGVzLCBwYXJlbnRQYXRoOiBzdHJpbmcgPSAnJyk6IHZvaWQge1xuICAvLyBmb3JFYWNoIGRvZXNuJ3QgaXRlcmF0ZSB1bmRlZmluZWQgdmFsdWVzXG4gIGZvciAobGV0IGkgPSAwOyBpIDwgY29uZmlnLmxlbmd0aDsgaSsrKSB7XG4gICAgY29uc3Qgcm91dGU6IFJvdXRlID0gY29uZmlnW2ldO1xuICAgIGNvbnN0IGZ1bGxQYXRoOiBzdHJpbmcgPSBnZXRGdWxsUGF0aChwYXJlbnRQYXRoLCByb3V0ZSk7XG4gICAgdmFsaWRhdGVOb2RlKHJvdXRlLCBmdWxsUGF0aCk7XG4gIH1cbn1cblxuZnVuY3Rpb24gdmFsaWRhdGVOb2RlKHJvdXRlOiBSb3V0ZSwgZnVsbFBhdGg6IHN0cmluZyk6IHZvaWQge1xuICBpZiAoIXJvdXRlKSB7XG4gICAgdGhyb3cgbmV3IEVycm9yKGBcbiAgICAgIEludmFsaWQgY29uZmlndXJhdGlvbiBvZiByb3V0ZSAnJHtmdWxsUGF0aH0nOiBFbmNvdW50ZXJlZCB1bmRlZmluZWQgcm91dGUuXG4gICAgICBUaGUgcmVhc29uIG1pZ2h0IGJlIGFuIGV4dHJhIGNvbW1hLlxuXG4gICAgICBFeGFtcGxlOlxuICAgICAgY29uc3Qgcm91dGVzOiBSb3V0ZXMgPSBbXG4gICAgICAgIHsgcGF0aDogJycsIHJlZGlyZWN0VG86ICcvZGFzaGJvYXJkJywgcGF0aE1hdGNoOiAnZnVsbCcgfSxcbiAgICAgICAgeyBwYXRoOiAnZGFzaGJvYXJkJywgIGNvbXBvbmVudDogRGFzaGJvYXJkQ29tcG9uZW50IH0sLCA8PCB0d28gY29tbWFzXG4gICAgICAgIHsgcGF0aDogJ2RldGFpbC86aWQnLCBjb21wb25lbnQ6IEhlcm9EZXRhaWxDb21wb25lbnQgfVxuICAgICAgXTtcbiAgICBgKTtcbiAgfVxuICBpZiAoQXJyYXkuaXNBcnJheShyb3V0ZSkpIHtcbiAgICB0aHJvdyBuZXcgRXJyb3IoYEludmFsaWQgY29uZmlndXJhdGlvbiBvZiByb3V0ZSAnJHtmdWxsUGF0aH0nOiBBcnJheSBjYW5ub3QgYmUgc3BlY2lmaWVkYCk7XG4gIH1cbiAgaWYgKCFyb3V0ZS5jb21wb25lbnQgJiYgIXJvdXRlLmNoaWxkcmVuICYmICFyb3V0ZS5sb2FkQ2hpbGRyZW4gJiZcbiAgICAgIChyb3V0ZS5vdXRsZXQgJiYgcm91dGUub3V0bGV0ICE9PSBQUklNQVJZX09VVExFVCkpIHtcbiAgICB0aHJvdyBuZXcgRXJyb3IoXG4gICAgICAgIGBJbnZhbGlkIGNvbmZpZ3VyYXRpb24gb2Ygcm91dGUgJyR7ZnVsbFBhdGh9JzogYSBjb21wb25lbnRsZXNzIHJvdXRlIHdpdGhvdXQgY2hpbGRyZW4gb3IgbG9hZENoaWxkcmVuIGNhbm5vdCBoYXZlIGEgbmFtZWQgb3V0bGV0IHNldGApO1xuICB9XG4gIGlmIChyb3V0ZS5yZWRpcmVjdFRvICYmIHJvdXRlLmNoaWxkcmVuKSB7XG4gICAgdGhyb3cgbmV3IEVycm9yKFxuICAgICAgICBgSW52YWxpZCBjb25maWd1cmF0aW9uIG9mIHJvdXRlICcke2Z1bGxQYXRofSc6IHJlZGlyZWN0VG8gYW5kIGNoaWxkcmVuIGNhbm5vdCBiZSB1c2VkIHRvZ2V0aGVyYCk7XG4gIH1cbiAgaWYgKHJvdXRlLnJlZGlyZWN0VG8gJiYgcm91dGUubG9hZENoaWxkcmVuKSB7XG4gICAgdGhyb3cgbmV3IEVycm9yKFxuICAgICAgICBgSW52YWxpZCBjb25maWd1cmF0aW9uIG9mIHJvdXRlICcke2Z1bGxQYXRofSc6IHJlZGlyZWN0VG8gYW5kIGxvYWRDaGlsZHJlbiBjYW5ub3QgYmUgdXNlZCB0b2dldGhlcmApO1xuICB9XG4gIGlmIChyb3V0ZS5jaGlsZHJlbiAmJiByb3V0ZS5sb2FkQ2hpbGRyZW4pIHtcbiAgICB0aHJvdyBuZXcgRXJyb3IoXG4gICAgICAgIGBJbnZhbGlkIGNvbmZpZ3VyYXRpb24gb2Ygcm91dGUgJyR7ZnVsbFBhdGh9JzogY2hpbGRyZW4gYW5kIGxvYWRDaGlsZHJlbiBjYW5ub3QgYmUgdXNlZCB0b2dldGhlcmApO1xuICB9XG4gIGlmIChyb3V0ZS5yZWRpcmVjdFRvICYmIHJvdXRlLmNvbXBvbmVudCkge1xuICAgIHRocm93IG5ldyBFcnJvcihcbiAgICAgICAgYEludmFsaWQgY29uZmlndXJhdGlvbiBvZiByb3V0ZSAnJHtmdWxsUGF0aH0nOiByZWRpcmVjdFRvIGFuZCBjb21wb25lbnQgY2Fubm90IGJlIHVzZWQgdG9nZXRoZXJgKTtcbiAgfVxuICBpZiAocm91dGUucGF0aCAmJiByb3V0ZS5tYXRjaGVyKSB7XG4gICAgdGhyb3cgbmV3IEVycm9yKFxuICAgICAgICBgSW52YWxpZCBjb25maWd1cmF0aW9uIG9mIHJvdXRlICcke2Z1bGxQYXRofSc6IHBhdGggYW5kIG1hdGNoZXIgY2Fubm90IGJlIHVzZWQgdG9nZXRoZXJgKTtcbiAgfVxuICBpZiAocm91dGUucmVkaXJlY3RUbyA9PT0gdm9pZCAwICYmICFyb3V0ZS5jb21wb25lbnQgJiYgIXJvdXRlLmNoaWxkcmVuICYmICFyb3V0ZS5sb2FkQ2hpbGRyZW4pIHtcbiAgICB0aHJvdyBuZXcgRXJyb3IoXG4gICAgICAgIGBJbnZhbGlkIGNvbmZpZ3VyYXRpb24gb2Ygcm91dGUgJyR7ZnVsbFBhdGh9Jy4gT25lIG9mIHRoZSBmb2xsb3dpbmcgbXVzdCBiZSBwcm92aWRlZDogY29tcG9uZW50LCByZWRpcmVjdFRvLCBjaGlsZHJlbiBvciBsb2FkQ2hpbGRyZW5gKTtcbiAgfVxuICBpZiAocm91dGUucGF0aCA9PT0gdm9pZCAwICYmIHJvdXRlLm1hdGNoZXIgPT09IHZvaWQgMCkge1xuICAgIHRocm93IG5ldyBFcnJvcihcbiAgICAgICAgYEludmFsaWQgY29uZmlndXJhdGlvbiBvZiByb3V0ZSAnJHtmdWxsUGF0aH0nOiByb3V0ZXMgbXVzdCBoYXZlIGVpdGhlciBhIHBhdGggb3IgYSBtYXRjaGVyIHNwZWNpZmllZGApO1xuICB9XG4gIGlmICh0eXBlb2Ygcm91dGUucGF0aCA9PT0gJ3N0cmluZycgJiYgcm91dGUucGF0aC5jaGFyQXQoMCkgPT09ICcvJykge1xuICAgIHRocm93IG5ldyBFcnJvcihgSW52YWxpZCBjb25maWd1cmF0aW9uIG9mIHJvdXRlICcke2Z1bGxQYXRofSc6IHBhdGggY2Fubm90IHN0YXJ0IHdpdGggYSBzbGFzaGApO1xuICB9XG4gIGlmIChyb3V0ZS5wYXRoID09PSAnJyAmJiByb3V0ZS5yZWRpcmVjdFRvICE9PSB2b2lkIDAgJiYgcm91dGUucGF0aE1hdGNoID09PSB2b2lkIDApIHtcbiAgICBjb25zdCBleHAgPVxuICAgICAgICBgVGhlIGRlZmF1bHQgdmFsdWUgb2YgJ3BhdGhNYXRjaCcgaXMgJ3ByZWZpeCcsIGJ1dCBvZnRlbiB0aGUgaW50ZW50IGlzIHRvIHVzZSAnZnVsbCcuYDtcbiAgICB0aHJvdyBuZXcgRXJyb3IoXG4gICAgICAgIGBJbnZhbGlkIGNvbmZpZ3VyYXRpb24gb2Ygcm91dGUgJ3twYXRoOiBcIiR7ZnVsbFBhdGh9XCIsIHJlZGlyZWN0VG86IFwiJHtyb3V0ZS5yZWRpcmVjdFRvfVwifSc6IHBsZWFzZSBwcm92aWRlICdwYXRoTWF0Y2gnLiAke2V4cH1gKTtcbiAgfVxuICBpZiAocm91dGUucGF0aE1hdGNoICE9PSB2b2lkIDAgJiYgcm91dGUucGF0aE1hdGNoICE9PSAnZnVsbCcgJiYgcm91dGUucGF0aE1hdGNoICE9PSAncHJlZml4Jykge1xuICAgIHRocm93IG5ldyBFcnJvcihcbiAgICAgICAgYEludmFsaWQgY29uZmlndXJhdGlvbiBvZiByb3V0ZSAnJHtmdWxsUGF0aH0nOiBwYXRoTWF0Y2ggY2FuIG9ubHkgYmUgc2V0IHRvICdwcmVmaXgnIG9yICdmdWxsJ2ApO1xuICB9XG4gIGlmIChyb3V0ZS5jaGlsZHJlbikge1xuICAgIHZhbGlkYXRlQ29uZmlnKHJvdXRlLmNoaWxkcmVuLCBmdWxsUGF0aCk7XG4gIH1cbn1cblxuZnVuY3Rpb24gZ2V0RnVsbFBhdGgocGFyZW50UGF0aDogc3RyaW5nLCBjdXJyZW50Um91dGU6IFJvdXRlKTogc3RyaW5nIHtcbiAgaWYgKCFjdXJyZW50Um91dGUpIHtcbiAgICByZXR1cm4gcGFyZW50UGF0aDtcbiAgfVxuICBpZiAoIXBhcmVudFBhdGggJiYgIWN1cnJlbnRSb3V0ZS5wYXRoKSB7XG4gICAgcmV0dXJuICcnO1xuICB9IGVsc2UgaWYgKHBhcmVudFBhdGggJiYgIWN1cnJlbnRSb3V0ZS5wYXRoKSB7XG4gICAgcmV0dXJuIGAke3BhcmVudFBhdGh9L2A7XG4gIH0gZWxzZSBpZiAoIXBhcmVudFBhdGggJiYgY3VycmVudFJvdXRlLnBhdGgpIHtcbiAgICByZXR1cm4gY3VycmVudFJvdXRlLnBhdGg7XG4gIH0gZWxzZSB7XG4gICAgcmV0dXJuIGAke3BhcmVudFBhdGh9LyR7Y3VycmVudFJvdXRlLnBhdGh9YDtcbiAgfVxufVxuXG4vKipcbiAqIE1ha2VzIGEgY29weSBvZiB0aGUgY29uZmlnIGFuZCBhZGRzIGFueSBkZWZhdWx0IHJlcXVpcmVkIHByb3BlcnRpZXMuXG4gKi9cbmV4cG9ydCBmdW5jdGlvbiBzdGFuZGFyZGl6ZUNvbmZpZyhyOiBSb3V0ZSk6IFJvdXRlIHtcbiAgY29uc3QgY2hpbGRyZW4gPSByLmNoaWxkcmVuICYmIHIuY2hpbGRyZW4ubWFwKHN0YW5kYXJkaXplQ29uZmlnKTtcbiAgY29uc3QgYyA9IGNoaWxkcmVuID8gey4uLnIsIGNoaWxkcmVufSA6IHsuLi5yfTtcbiAgaWYgKCFjLmNvbXBvbmVudCAmJiAoY2hpbGRyZW4gfHwgYy5sb2FkQ2hpbGRyZW4pICYmIChjLm91dGxldCAmJiBjLm91dGxldCAhPT0gUFJJTUFSWV9PVVRMRVQpKSB7XG4gICAgYy5jb21wb25lbnQgPSBFbXB0eU91dGxldENvbXBvbmVudDtcbiAgfVxuICByZXR1cm4gYztcbn1cbiJdfQ==