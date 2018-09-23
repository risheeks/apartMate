/**
 * @fileoverview added by tsickle
 * @suppress {checkTypes,extraRequire,uselessCode} checked by tsc
 */
import { APP_ID, NgModule, NgZone, PLATFORM_INITIALIZER, createPlatformFactory, platformCore } from '@angular/core';
import { BrowserModule, ɵBrowserDomAdapter as BrowserDomAdapter, ɵELEMENT_PROBE_PROVIDERS as ELEMENT_PROBE_PROVIDERS } from '@angular/platform-browser';
import { BrowserDetection, createNgZone } from './browser_util';
/**
 * @return {?}
 */
function initBrowserTests() {
    BrowserDomAdapter.makeCurrent();
    BrowserDetection.setup();
}
/** @type {?} */
const _TEST_BROWSER_PLATFORM_PROVIDERS = [{ provide: PLATFORM_INITIALIZER, useValue: initBrowserTests, multi: true }];
/** *
 * Platform for testing
 *
 *
  @type {?} */
export const platformBrowserTesting = createPlatformFactory(platformCore, 'browserTesting', _TEST_BROWSER_PLATFORM_PROVIDERS);
const ɵ0 = createNgZone;
/**
 * NgModule for testing.
 *
 *
 */
export class BrowserTestingModule {
}
BrowserTestingModule.decorators = [
    { type: NgModule, args: [{
                exports: [BrowserModule],
                providers: [
                    { provide: APP_ID, useValue: 'a' },
                    ELEMENT_PROBE_PROVIDERS,
                    { provide: NgZone, useFactory: ɵ0 },
                ]
            },] }
];
export { ɵ0 };

//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiYnJvd3Nlci5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbIi4uLy4uLy4uLy4uLy4uLy4uLy4uL3BhY2thZ2VzL3BsYXRmb3JtLWJyb3dzZXIvdGVzdGluZy9zcmMvYnJvd3Nlci50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOzs7O0FBT0EsT0FBTyxFQUFDLE1BQU0sRUFBRSxRQUFRLEVBQUUsTUFBTSxFQUFFLG9CQUFvQixFQUErQixxQkFBcUIsRUFBRSxZQUFZLEVBQUMsTUFBTSxlQUFlLENBQUM7QUFDL0ksT0FBTyxFQUFDLGFBQWEsRUFBRSxrQkFBa0IsSUFBSSxpQkFBaUIsRUFBRSx3QkFBd0IsSUFBSSx1QkFBdUIsRUFBQyxNQUFNLDJCQUEyQixDQUFDO0FBQ3RKLE9BQU8sRUFBQyxnQkFBZ0IsRUFBRSxZQUFZLEVBQUMsTUFBTSxnQkFBZ0IsQ0FBQzs7OztBQUU5RDtJQUNFLGlCQUFpQixDQUFDLFdBQVcsRUFBRSxDQUFDO0lBQ2hDLGdCQUFnQixDQUFDLEtBQUssRUFBRSxDQUFDO0NBQzFCOztBQUVELE1BQU0sZ0NBQWdDLEdBQ2xDLENBQUMsRUFBQyxPQUFPLEVBQUUsb0JBQW9CLEVBQUUsUUFBUSxFQUFFLGdCQUFnQixFQUFFLEtBQUssRUFBRSxJQUFJLEVBQUMsQ0FBQyxDQUFDOzs7Ozs7QUFPL0UsYUFBYSxzQkFBc0IsR0FDL0IscUJBQXFCLENBQUMsWUFBWSxFQUFFLGdCQUFnQixFQUFFLGdDQUFnQyxDQUFDLENBQUM7V0FZMUQsWUFBWTs7Ozs7O0FBRzlDLE1BQU07OztZQVJMLFFBQVEsU0FBQztnQkFDUixPQUFPLEVBQUUsQ0FBQyxhQUFhLENBQUM7Z0JBQ3hCLFNBQVMsRUFBRTtvQkFDVCxFQUFDLE9BQU8sRUFBRSxNQUFNLEVBQUUsUUFBUSxFQUFFLEdBQUcsRUFBQztvQkFDaEMsdUJBQXVCO29CQUN2QixFQUFDLE9BQU8sRUFBRSxNQUFNLEVBQUUsVUFBVSxJQUFjLEVBQUM7aUJBQzVDO2FBQ0YiLCJzb3VyY2VzQ29udGVudCI6WyIvKipcbiAqIEBsaWNlbnNlXG4gKiBDb3B5cmlnaHQgR29vZ2xlIEluYy4gQWxsIFJpZ2h0cyBSZXNlcnZlZC5cbiAqXG4gKiBVc2Ugb2YgdGhpcyBzb3VyY2UgY29kZSBpcyBnb3Zlcm5lZCBieSBhbiBNSVQtc3R5bGUgbGljZW5zZSB0aGF0IGNhbiBiZVxuICogZm91bmQgaW4gdGhlIExJQ0VOU0UgZmlsZSBhdCBodHRwczovL2FuZ3VsYXIuaW8vbGljZW5zZVxuICovXG5pbXBvcnQge0FQUF9JRCwgTmdNb2R1bGUsIE5nWm9uZSwgUExBVEZPUk1fSU5JVElBTElaRVIsIFBsYXRmb3JtUmVmLCBTdGF0aWNQcm92aWRlciwgY3JlYXRlUGxhdGZvcm1GYWN0b3J5LCBwbGF0Zm9ybUNvcmV9IGZyb20gJ0Bhbmd1bGFyL2NvcmUnO1xuaW1wb3J0IHtCcm93c2VyTW9kdWxlLCDJtUJyb3dzZXJEb21BZGFwdGVyIGFzIEJyb3dzZXJEb21BZGFwdGVyLCDJtUVMRU1FTlRfUFJPQkVfUFJPVklERVJTIGFzIEVMRU1FTlRfUFJPQkVfUFJPVklERVJTfSBmcm9tICdAYW5ndWxhci9wbGF0Zm9ybS1icm93c2VyJztcbmltcG9ydCB7QnJvd3NlckRldGVjdGlvbiwgY3JlYXRlTmdab25lfSBmcm9tICcuL2Jyb3dzZXJfdXRpbCc7XG5cbmZ1bmN0aW9uIGluaXRCcm93c2VyVGVzdHMoKSB7XG4gIEJyb3dzZXJEb21BZGFwdGVyLm1ha2VDdXJyZW50KCk7XG4gIEJyb3dzZXJEZXRlY3Rpb24uc2V0dXAoKTtcbn1cblxuY29uc3QgX1RFU1RfQlJPV1NFUl9QTEFURk9STV9QUk9WSURFUlM6IFN0YXRpY1Byb3ZpZGVyW10gPVxuICAgIFt7cHJvdmlkZTogUExBVEZPUk1fSU5JVElBTElaRVIsIHVzZVZhbHVlOiBpbml0QnJvd3NlclRlc3RzLCBtdWx0aTogdHJ1ZX1dO1xuXG4vKipcbiAqIFBsYXRmb3JtIGZvciB0ZXN0aW5nXG4gKlxuICpcbiAqL1xuZXhwb3J0IGNvbnN0IHBsYXRmb3JtQnJvd3NlclRlc3RpbmcgPVxuICAgIGNyZWF0ZVBsYXRmb3JtRmFjdG9yeShwbGF0Zm9ybUNvcmUsICdicm93c2VyVGVzdGluZycsIF9URVNUX0JST1dTRVJfUExBVEZPUk1fUFJPVklERVJTKTtcblxuLyoqXG4gKiBOZ01vZHVsZSBmb3IgdGVzdGluZy5cbiAqXG4gKlxuICovXG5ATmdNb2R1bGUoe1xuICBleHBvcnRzOiBbQnJvd3Nlck1vZHVsZV0sXG4gIHByb3ZpZGVyczogW1xuICAgIHtwcm92aWRlOiBBUFBfSUQsIHVzZVZhbHVlOiAnYSd9LFxuICAgIEVMRU1FTlRfUFJPQkVfUFJPVklERVJTLFxuICAgIHtwcm92aWRlOiBOZ1pvbmUsIHVzZUZhY3Rvcnk6IGNyZWF0ZU5nWm9uZX0sXG4gIF1cbn0pXG5leHBvcnQgY2xhc3MgQnJvd3NlclRlc3RpbmdNb2R1bGUge1xufVxuIl19