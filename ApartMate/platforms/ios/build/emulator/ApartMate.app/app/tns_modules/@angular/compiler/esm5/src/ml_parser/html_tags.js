/**
 * @license
 * Copyright Google Inc. All Rights Reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be
 * found in the LICENSE file at https://angular.io/license
 */
import { TagContentType } from './tags';
var HtmlTagDefinition = /** @class */ (function () {
    function HtmlTagDefinition(_a) {
        var _b = _a === void 0 ? {} : _a, closedByChildren = _b.closedByChildren, requiredParents = _b.requiredParents, implicitNamespacePrefix = _b.implicitNamespacePrefix, _c = _b.contentType, contentType = _c === void 0 ? TagContentType.PARSABLE_DATA : _c, _d = _b.closedByParent, closedByParent = _d === void 0 ? false : _d, _e = _b.isVoid, isVoid = _e === void 0 ? false : _e, _f = _b.ignoreFirstLf, ignoreFirstLf = _f === void 0 ? false : _f;
        var _this = this;
        this.closedByChildren = {};
        this.closedByParent = false;
        this.canSelfClose = false;
        if (closedByChildren && closedByChildren.length > 0) {
            closedByChildren.forEach(function (tagName) { return _this.closedByChildren[tagName] = true; });
        }
        this.isVoid = isVoid;
        this.closedByParent = closedByParent || isVoid;
        if (requiredParents && requiredParents.length > 0) {
            this.requiredParents = {};
            // The first parent is the list is automatically when none of the listed parents are present
            this.parentToAdd = requiredParents[0];
            requiredParents.forEach(function (tagName) { return _this.requiredParents[tagName] = true; });
        }
        this.implicitNamespacePrefix = implicitNamespacePrefix || null;
        this.contentType = contentType;
        this.ignoreFirstLf = ignoreFirstLf;
    }
    HtmlTagDefinition.prototype.requireExtraParent = function (currentParent) {
        if (!this.requiredParents) {
            return false;
        }
        if (!currentParent) {
            return true;
        }
        var lcParent = currentParent.toLowerCase();
        var isParentTemplate = lcParent === 'template' || currentParent === 'ng-template';
        return !isParentTemplate && this.requiredParents[lcParent] != true;
    };
    HtmlTagDefinition.prototype.isClosedByChild = function (name) {
        return this.isVoid || name.toLowerCase() in this.closedByChildren;
    };
    return HtmlTagDefinition;
}());
export { HtmlTagDefinition };
var _DEFAULT_TAG_DEFINITION;
// see http://www.w3.org/TR/html51/syntax.html#optional-tags
// This implementation does not fully conform to the HTML5 spec.
var TAG_DEFINITIONS;
export function getHtmlTagDefinition(tagName) {
    if (!TAG_DEFINITIONS) {
        _DEFAULT_TAG_DEFINITION = new HtmlTagDefinition();
        TAG_DEFINITIONS = {
            'base': new HtmlTagDefinition({ isVoid: true }),
            'meta': new HtmlTagDefinition({ isVoid: true }),
            'area': new HtmlTagDefinition({ isVoid: true }),
            'embed': new HtmlTagDefinition({ isVoid: true }),
            'link': new HtmlTagDefinition({ isVoid: true }),
            'img': new HtmlTagDefinition({ isVoid: true }),
            'input': new HtmlTagDefinition({ isVoid: true }),
            'param': new HtmlTagDefinition({ isVoid: true }),
            'hr': new HtmlTagDefinition({ isVoid: true }),
            'br': new HtmlTagDefinition({ isVoid: true }),
            'source': new HtmlTagDefinition({ isVoid: true }),
            'track': new HtmlTagDefinition({ isVoid: true }),
            'wbr': new HtmlTagDefinition({ isVoid: true }),
            'p': new HtmlTagDefinition({
                closedByChildren: [
                    'address', 'article', 'aside', 'blockquote', 'div', 'dl', 'fieldset',
                    'footer', 'form', 'h1', 'h2', 'h3', 'h4', 'h5',
                    'h6', 'header', 'hgroup', 'hr', 'main', 'nav', 'ol',
                    'p', 'pre', 'section', 'table', 'ul'
                ],
                closedByParent: true
            }),
            'thead': new HtmlTagDefinition({ closedByChildren: ['tbody', 'tfoot'] }),
            'tbody': new HtmlTagDefinition({ closedByChildren: ['tbody', 'tfoot'], closedByParent: true }),
            'tfoot': new HtmlTagDefinition({ closedByChildren: ['tbody'], closedByParent: true }),
            'tr': new HtmlTagDefinition({
                closedByChildren: ['tr'],
                requiredParents: ['tbody', 'tfoot', 'thead'],
                closedByParent: true
            }),
            'td': new HtmlTagDefinition({ closedByChildren: ['td', 'th'], closedByParent: true }),
            'th': new HtmlTagDefinition({ closedByChildren: ['td', 'th'], closedByParent: true }),
            'col': new HtmlTagDefinition({ requiredParents: ['colgroup'], isVoid: true }),
            'svg': new HtmlTagDefinition({ implicitNamespacePrefix: 'svg' }),
            'math': new HtmlTagDefinition({ implicitNamespacePrefix: 'math' }),
            'li': new HtmlTagDefinition({ closedByChildren: ['li'], closedByParent: true }),
            'dt': new HtmlTagDefinition({ closedByChildren: ['dt', 'dd'] }),
            'dd': new HtmlTagDefinition({ closedByChildren: ['dt', 'dd'], closedByParent: true }),
            'rb': new HtmlTagDefinition({ closedByChildren: ['rb', 'rt', 'rtc', 'rp'], closedByParent: true }),
            'rt': new HtmlTagDefinition({ closedByChildren: ['rb', 'rt', 'rtc', 'rp'], closedByParent: true }),
            'rtc': new HtmlTagDefinition({ closedByChildren: ['rb', 'rtc', 'rp'], closedByParent: true }),
            'rp': new HtmlTagDefinition({ closedByChildren: ['rb', 'rt', 'rtc', 'rp'], closedByParent: true }),
            'optgroup': new HtmlTagDefinition({ closedByChildren: ['optgroup'], closedByParent: true }),
            'option': new HtmlTagDefinition({ closedByChildren: ['option', 'optgroup'], closedByParent: true }),
            'pre': new HtmlTagDefinition({ ignoreFirstLf: true }),
            'listing': new HtmlTagDefinition({ ignoreFirstLf: true }),
            'style': new HtmlTagDefinition({ contentType: TagContentType.RAW_TEXT }),
            'script': new HtmlTagDefinition({ contentType: TagContentType.RAW_TEXT }),
            'title': new HtmlTagDefinition({ contentType: TagContentType.ESCAPABLE_RAW_TEXT }),
            'textarea': new HtmlTagDefinition({ contentType: TagContentType.ESCAPABLE_RAW_TEXT, ignoreFirstLf: true }),
        };
    }
    return TAG_DEFINITIONS[tagName.toLowerCase()] || _DEFAULT_TAG_DEFINITION;
}
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiaHRtbF90YWdzLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vLi4vLi4vLi4vLi4vLi4vLi4vLi4vLi4vcGFja2FnZXMvY29tcGlsZXIvc3JjL21sX3BhcnNlci9odG1sX3RhZ3MudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7Ozs7OztHQU1HO0FBRUgsT0FBTyxFQUFDLGNBQWMsRUFBZ0IsTUFBTSxRQUFRLENBQUM7QUFFckQ7SUFjRSwyQkFDSSxFQVVNO1lBVk4sNEJBVU0sRUFWTCxzQ0FBZ0IsRUFBRSxvQ0FBZSxFQUFFLG9EQUF1QixFQUMxRCxtQkFBMEMsRUFBMUMsK0RBQTBDLEVBQUUsc0JBQXNCLEVBQXRCLDJDQUFzQixFQUFFLGNBQWMsRUFBZCxtQ0FBYyxFQUNsRixxQkFBcUIsRUFBckIsMENBQXFCO1FBSDFCLGlCQTBCQztRQXZDTyxxQkFBZ0IsR0FBNkIsRUFBRSxDQUFDO1FBRXhELG1CQUFjLEdBQVksS0FBSyxDQUFDO1FBU2hDLGlCQUFZLEdBQVksS0FBSyxDQUFDO1FBYzVCLElBQUksZ0JBQWdCLElBQUksZ0JBQWdCLENBQUMsTUFBTSxHQUFHLENBQUMsRUFBRTtZQUNuRCxnQkFBZ0IsQ0FBQyxPQUFPLENBQUMsVUFBQSxPQUFPLElBQUksT0FBQSxLQUFJLENBQUMsZ0JBQWdCLENBQUMsT0FBTyxDQUFDLEdBQUcsSUFBSSxFQUFyQyxDQUFxQyxDQUFDLENBQUM7U0FDNUU7UUFDRCxJQUFJLENBQUMsTUFBTSxHQUFHLE1BQU0sQ0FBQztRQUNyQixJQUFJLENBQUMsY0FBYyxHQUFHLGNBQWMsSUFBSSxNQUFNLENBQUM7UUFDL0MsSUFBSSxlQUFlLElBQUksZUFBZSxDQUFDLE1BQU0sR0FBRyxDQUFDLEVBQUU7WUFDakQsSUFBSSxDQUFDLGVBQWUsR0FBRyxFQUFFLENBQUM7WUFDMUIsNEZBQTRGO1lBQzVGLElBQUksQ0FBQyxXQUFXLEdBQUcsZUFBZSxDQUFDLENBQUMsQ0FBQyxDQUFDO1lBQ3RDLGVBQWUsQ0FB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b29nbGUgSW5jLiBBbGwgUmlnaHRzIFJlc2VydmVkLlxuICpcbiAqIFVzZSBvZiB0aGlzIHNvdXJjZSBjb2RlIGlzIGdvdmVybmVkIGJ5IGFuIE1JVC1zdHlsZSBsaWNlbnNlIHRoYXQgY2FuIGJlXG4gKiBmb3VuZCBpbiB0aGUgTElDRU5TRSBmaWxlIGF0IGh0dHBzOi8vYW5ndWxhci5pby9saWNlbnNlXG4gKi9cblxuaW1wb3J0IHtUYWdDb250ZW50VHlwZSwgVGFnRGVmaW5pdGlvbn0gZnJvbSAnLi90YWdzJztcblxuZXhwb3J0IGNsYXNzIEh0bWxUYWdEZWZpbml0aW9uIGltcGxlbWVudHMgVGFnRGVmaW5pdGlvbiB7XG4gIHByaXZhdGUgY2xvc2VkQnlDaGlsZHJlbjoge1trZXk6IHN0cmluZ106IGJvb2xlYW59ID0ge307XG5cbiAgY2xvc2VkQnlQYXJlbnQ6IGJvb2xlYW4gPSBmYWxzZTtcbiAgLy8gVE9ETyhpc3N1ZS8yNDU3MSk6IHJlbW92ZSAnIScuXG4gIHJlcXVpcmVkUGFyZW50cyAhOiB7W2tleTogc3RyaW5nXTogYm9vbGVhbn07XG4gIC8vIFRPRE8oaXNzdWUvMjQ1NzEpOiByZW1vdmUgJyEnLlxuICBwYXJlbnRUb0FkZCAhOiBzdHJpbmc7XG4gIGltcGxpY2l0TmFtZXNwYWNlUHJlZml4OiBzdHJpbmd8bnVsbDtcbiAgY29udGVudFR5cGU6IFRhZ0NvbnRlbnRUeXBlO1xuICBpc1ZvaWQ6IGJvb2xlYW47XG4gIGlnbm9yZUZpcnN0TGY6IGJvb2xlYW47XG4gIGNhblNlbGZDbG9zZTogYm9vbGVhbiA9IGZhbHNlO1xuXG4gIGNvbnN0cnVjdG9yKFxuICAgICAge2Nsb3NlZEJ5Q2hpbGRyZW4sIHJlcXVpcmVkUGFyZW50cywgaW1wbGljaXROYW1lc3BhY2VQcmVmaXgsXG4gICAgICAgY29udGVudFR5cGUgPSBUYWdDb250ZW50VHlwZS5QQVJTQUJMRV9EQVRBLCBjbG9zZWRCeVBhcmVudCA9IGZhbHNlLCBpc1ZvaWQgPSBmYWxzZSxcbiAgICAgICBpZ25vcmVGaXJzdExmID0gZmFsc2V9OiB7XG4gICAgICAgIGNsb3NlZEJ5Q2hpbGRyZW4/OiBzdHJpbmdbXSxcbiAgICAgICAgY2xvc2VkQnlQYXJlbnQ/OiBib29sZWFuLFxuICAgICAgICByZXF1aXJlZFBhcmVudHM/OiBzdHJpbmdbXSxcbiAgICAgICAgaW1wbGljaXROYW1lc3BhY2VQcmVmaXg/OiBzdHJpbmcsXG4gICAgICAgIGNvbnRlbnRUeXBlPzogVGFnQ29udGVudFR5cGUsXG4gICAgICAgIGlzVm9pZD86IGJvb2xlYW4sXG4gICAgICAgIGlnbm9yZUZpcnN0TGY/OiBib29sZWFuXG4gICAgICB9ID0ge30pIHtcbiAgICBpZiAoY2xvc2VkQnlDaGlsZHJlbiAmJiBjbG9zZWRCeUNoaWxkcmVuLmxlbmd0aCA+IDApIHtcbiAgICAgIGNsb3NlZEJ5Q2hpbGRyZW4uZm9yRWFjaCh0YWdOYW1lID0+IHRoaXMuY2xvc2VkQnlDaGlsZHJlblt0YWdOYW1lXSA9IHRydWUpO1xuICAgIH1cbiAgICB0aGlzLmlzVm9pZCA9IGlzVm9pZDtcbiAgICB0aGlzLmNsb3NlZEJ5UGFyZW50ID0gY2xvc2VkQnlQYXJlbnQgfHwgaXNWb2lkO1xuICAgIGlmIChyZXF1aXJlZFBhcmVudHMgJiYgcmVxdWlyZWRQYXJlbnRzLmxlbmd0aCA+IDApIHtcbiAgICAgIHRoaXMucmVxdWlyZWRQYXJlbnRzID0ge307XG4gICAgICAvLyBUaGUgZmlyc3QgcGFyZW50IGlzIHRoZSBsaXN0IGlzIGF1dG9tYXRpY2FsbHkgd2hlbiBub25lIG9mIHRoZSBsaXN0ZWQgcGFyZW50cyBhcmUgcHJlc2VudFxuICAgICAgdGhpcy5wYXJlbnRUb0FkZCA9IHJlcXVpcmVkUGFyZW50c1swXTtcbiAgICAgIHJlcXVpcmVkUGFyZW50cy5mb3JFYWNoKHRhZ05hbWUgPT4gdGhpcy5yZXF1aXJlZFBhcmVudHNbdGFnTmFtZV0gPSB0cnVlKTtcbiAgICB9XG4gICAgdGhpcy5pbXBsaWNpdE5hbWVzcGFjZVByZWZpeCA9IGltcGxpY2l0TmFtZXNwYWNlUHJlZml4IHx8IG51bGw7XG4gICAgdGhpcy5jb250ZW50VHlwZSA9IGNvbnRlbnRUeXBlO1xuICAgIHRoaXMuaWdub3JlRmlyc3RMZiA9IGlnbm9yZUZpcnN0TGY7XG4gIH1cblxuICByZXF1aXJlRXh0cmFQYXJlbnQoY3VycmVudFBhcmVudDogc3RyaW5nKTogYm9vbGVhbiB7XG4gICAgaWYgKCF0aGlzLnJlcXVpcmVkUGFyZW50cykge1xuICAgICAgcmV0dXJuIGZhbHNlO1xuICAgIH1cblxuICAgIGlmICghY3VycmVudFBhcmVudCkge1xuICAgICAgcmV0dXJuIHRydWU7XG4gICAgfVxuXG4gICAgY29uc3QgbGNQYXJlbnQgPSBjdXJyZW50UGFyZW50LnRvTG93ZXJDYXNlKCk7XG4gICAgY29uc3QgaXNQYXJlbnRUZW1wbGF0ZSA9IGxjUGFyZW50ID09PSAndGVtcGxhdGUnIHx8IGN1cnJlbnRQYXJlbnQgPT09ICduZy10ZW1wbGF0ZSc7XG4gICAgcmV0dXJuICFpc1BhcmVudFRlbXBsYXRlICYmIHRoaXMucmVxdWlyZWRQYXJlbnRzW2xjUGFyZW50XSAhPSB0cnVlO1xuICB9XG5cbiAgaXNDbG9zZWRCeUNoaWxkKG5hbWU6IHN0cmluZyk6IGJvb2xlYW4ge1xuICAgIHJldHVybiB0aGlzLmlzVm9pZCB8fCBuYW1lLnRvTG93ZXJDYXNlKCkgaW4gdGhpcy5jbG9zZWRCeUNoaWxkcmVuO1xuICB9XG59XG5cbmxldCBfREVGQVVMVF9UQUdfREVGSU5JVElPTiAhOiBIdG1sVGFnRGVmaW5pdGlvbjtcblxuLy8gc2VlIGh0dHA6Ly93d3cudzMub3JnL1RSL2h0bWw1MS9zeW50YXguaHRtbCNvcHRpb25hbC10YWdzXG4vLyBUaGlzIGltcGxlbWVudGF0aW9uIGRvZXMgbm90IGZ1bGx5IGNvbmZvcm0gdG8gdGhlIEhUTUw1IHNwZWMuXG5sZXQgVEFHX0RFRklOSVRJT05TICE6IHtba2V5OiBzdHJpbmddOiBIdG1sVGFnRGVmaW5pdGlvbn07XG5cbmV4cG9ydCBmdW5jdGlvbiBnZXRIdG1sVGFnRGVmaW5pdGlvbih0YWdOYW1lOiBzdHJpbmcpOiBIdG1sVGFnRGVmaW5pdGlvbiB7XG4gIGlmICghVEFHX0RFRklOSVRJT05TKSB7XG4gICAgX0RFRkFVTFRfVEFHX0RFRklOSVRJT04gPSBuZXcgSHRtbFRhZ0RlZmluaXRpb24oKTtcbiAgICBUQUdfREVGSU5JVElPTlMgPSB7XG4gICAgICAnYmFzZSc6IG5ldyBIdG1sVGFnRGVmaW5pdGlvbih7aXNWb2lkOiB0cnVlfSksXG4gICAgICAnbWV0YSc6IG5ldyBIdG1sVGFnRGVmaW5pdGlvbih7aXNWb2lkOiB0cnVlfSksXG4gICAgICAnYXJlYSc6IG5ldyBIdG1sVGFnRGVmaW5pdGlvbih7aXNWb2lkOiB0cnVlfSksXG4gICAgICAnZW1iZWQnOiBuZXcgSHRtbFRhZ0RlZmluaXRpb24oe2lzVm9pZDogdHJ1ZX0pLFxuICAgICAgJ2xpbmsnOiBuZXcgSHRtbFRhZ0RlZmluaXRpb24oe2lzVm9pZDogdHJ1ZX0pLFxuICAgICAgJ2ltZyc6IG5ldyBIdG1sVGFnRGVmaW5pdGlvbih7aXNWb2lkOiB0cnVlfSksXG4gICAgICAnaW5wdXQnOiBuZXcgSHRtbFRhZ0RlZmluaXRpb24oe2lzVm9pZDogdHJ1ZX0pLFxuICAgICAgJ3BhcmFtJzogbmV3IEh0bWxUYWdEZWZpbml0aW9uKHtpc1ZvaWQ6IHRydWV9KSxcbiAgICAgICdocic6IG5ldyBIdG1sVGFnRGVmaW5pdGlvbih7aXNWb2lkOiB0cnVlfSksXG4gICAgICAnYnInOiBuZXcgSHRtbFRhZ0RlZmluaXRpb24oe2lzVm9pZDogdHJ1ZX0pLFxuICAgICAgJ3NvdXJjZSc6IG5ldyBIdG1sVGFnRGVmaW5pdGlvbih7aXNWb2lkOiB0cnVlfSksXG4gICAgICAndHJhY2snOiBuZXcgSHRtbFRhZ0RlZmluaXRpb24oe2lzVm9pZDogdHJ1ZX0pLFxuICAgICAgJ3dicic6IG5ldyBIdG1sVGFnRGVmaW5pdGlvbih7aXNWb2lkOiB0cnVlfSksXG4gICAgICAncCc6IG5ldyBIdG1sVGFnRGVmaW5pdGlvbih7XG4gICAgICAgIGNsb3NlZEJ5Q2hpbGRyZW46IFtcbiAgICAgICAgICAnYWRkcmVzcycsICdhcnRpY2xlJywgJ2FzaWRlJywgICAnYmxvY2txdW90ZScsICdkaXYnLCAgJ2RsJywgICdmaWVsZHNldCcsXG4gICAgICAgICAgJ2Zvb3RlcicsICAnZm9ybScsICAgICdoMScsICAgICAgJ2gyJywgICAgICAgICAnaDMnLCAgICdoNCcsICAnaDUnLFxuICAgICAgICAgICdoNicsICAgICAgJ2hlYWRlcicsICAnaGdyb3VwJywgICdocicsICAgICAgICAgJ21haW4nLCAnbmF2JywgJ29sJyxcbiAgICAgICAgICAncCcsICAgICAgICdwcmUnLCAgICAgJ3NlY3Rpb24nLCAndGFibGUnLCAgICAgICd1bCdcbiAgICAgICAgXSxcbiAgICAgICAgY2xvc2VkQnlQYXJlbnQ6IHRydWVcbiAgICAgIH0pLFxuICAgICAgJ3RoZWFkJzogbmV3IEh0bWxUYWdEZWZpbml0aW9uKHtjbG9zZWRCeUNoaWxkcmVuOiBbJ3Rib2R5JywgJ3Rmb290J119KSxcbiAgICAgICd0Ym9keSc6IG5ldyBIdG1sVGFnRGVmaW5pdGlvbih7Y2xvc2VkQnlDaGlsZHJlbjogWyd0Ym9keScsICd0Zm9vdCddLCBjbG9zZWRCeVBhcmVudDogdHJ1ZX0pLFxuICAgICAgJ3Rmb290JzogbmV3IEh0bWxUYWdEZWZpbml0aW9uKHtjbG9zZWRCeUNoaWxkcmVuOiBbJ3Rib2R5J10sIGNsb3NlZEJ5UGFyZW50OiB0cnVlfSksXG4gICAgICAndHInOiBuZXcgSHRtbFRhZ0RlZmluaXRpb24oe1xuICAgICAgICBjbG9zZWRCeUNoaWxkcmVuOiBbJ3RyJ10sXG4gICAgICAgIHJlcXVpcmVkUGFyZW50czogWyd0Ym9keScsICd0Zm9vdCcsICd0aGVhZCddLFxuICAgICAgICBjbG9zZWRCeVBhcmVudDogdHJ1ZVxuICAgICAgfSksXG4gICAgICAndGQnOiBuZXcgSHRtbFRhZ0RlZmluaXRpb24oe2Nsb3NlZEJ5Q2hpbGRyZW46IFsndGQnLCAndGgnXSwgY2xvc2VkQnlQYXJlbnQ6IHRydWV9KSxcbiAgICAgICd0aCc6IG5ldyBIdG1sVGFnRGVmaW5pdGlvbih7Y2xvc2VkQnlDaGlsZHJlbjogWyd0ZCcsICd0aCddLCBjbG9zZWRCeVBhcmVudDogdHJ1ZX0pLFxuICAgICAgJ2NvbCc6IG5ldyBIdG1sVGFnRGVmaW5pdGlvbih7cmVxdWlyZWRQYXJlbnRzOiBbJ2NvbGdyb3VwJ10sIGlzVm9pZDogdHJ1ZX0pLFxuICAgICAgJ3N2Zyc6IG5ldyBIdG1sVGFnRGVmaW5pdGlvbih7aW1wbGljaXROYW1lc3BhY2VQcmVmaXg6ICdzdmcnfSksXG4gICAgICAnbWF0aCc6IG5ldyBIdG1sVGFnRGVmaW5pdGlvbih7aW1wbGljaXROYW1lc3BhY2VQcmVmaXg6ICdtYXRoJ30pLFxuICAgICAgJ2xpJzogbmV3IEh0bWxUYWdEZWZpbml0aW9uKHtjbG9zZWRCeUNoaWxkcmVuOiBbJ2xpJ10sIGNsb3NlZEJ5UGFyZW50OiB0cnVlfSksXG4gICAgICAnZHQnOiBuZXcgSHRtbFRhZ0RlZmluaXRpb24oe2Nsb3NlZEJ5Q2hpbGRyZW46IFsnZHQnLCAnZGQnXX0pLFxuICAgICAgJ2RkJzogbmV3IEh0bWxUYWdEZWZpbml0aW9uKHtjbG9zZWRCeUNoaWxkcmVuOiBbJ2R0JywgJ2RkJ10sIGNsb3NlZEJ5UGFyZW50OiB0cnVlfSksXG4gICAgICAncmInOiBuZXcgSHRtbFRhZ0RlZmluaXRpb24oXG4gICAgICAgICAge2Nsb3NlZEJ5Q2hpbGRyZW46IFsncmInLCAncnQnLCAncnRjJywgJ3JwJ10sIGNsb3NlZEJ5UGFyZW50OiB0cnVlfSksXG4gICAgICAncnQnOiBuZXcgSHRtbFRhZ0RlZmluaXRpb24oXG4gICAgICAgICAge2Nsb3NlZEJ5Q2hpbGRyZW46IFsncmInLCAncnQnLCAncnRjJywgJ3JwJ10sIGNsb3NlZEJ5UGFyZW50OiB0cnVlfSksXG4gICAgICAncnRjJzogbmV3IEh0bWxUYWdEZWZpbml0aW9uKHtjbG9zZWRCeUNoaWxkcmVuOiBbJ3JiJywgJ3J0YycsICdycCddLCBjbG9zZWRCeVBhcmVudDogdHJ1ZX0pLFxuICAgICAgJ3JwJzogbmV3IEh0bWxUYWdEZWZpbml0aW9uKFxuICAgICAgICAgIHtjbG9zZWRCeUNoaWxkcmVuOiBbJ3JiJywgJ3J0JywgJ3J0YycsICdycCddLCBjbG9zZWRCeVBhcmVudDogdHJ1ZX0pLFxuICAgICAgJ29wdGdyb3VwJzogbmV3IEh0bWxUYWdEZWZpbml0aW9uKHtjbG9zZWRCeUNoaWxkcmVuOiBbJ29wdGdyb3VwJ10sIGNsb3NlZEJ5UGFyZW50OiB0cnVlfSksXG4gICAgICAnb3B0aW9uJzpcbiAgICAgICAgICBuZXcgSHRtbFRhZ0RlZmluaXRpb24oe2Nsb3NlZEJ5Q2hpbGRyZW46IFsnb3B0aW9uJywgJ29wdGdyb3VwJ10sIGNsb3NlZEJ5UGFyZW50OiB0cnVlfSksXG4gICAgICAncHJlJzogbmV3IEh0bWxUYWdEZWZpbml0aW9uKHtpZ25vcmVGaXJzdExmOiB0cnVlfSksXG4gICAgICAnbGlzdGluZyc6IG5ldyBIdG1sVGFnRGVmaW5pdGlvbih7aWdub3JlRmlyc3RMZjogdHJ1ZX0pLFxuICAgICAgJ3N0eWxlJzogbmV3IEh0bWxUYWdEZWZpbml0aW9uKHtjb250ZW50VHlwZTogVGFnQ29udGVudFR5cGUuUkFXX1RFWFR9KSxcbiAgICAgICdzY3JpcHQnOiBuZXcgSHRtbFRhZ0RlZmluaXRpb24oe2NvbnRlbnRUeXBlOiBUYWdDb250ZW50VHlwZS5SQVdfVEVYVH0pLFxuICAgICAgJ3RpdGxlJzogbmV3IEh0bWxUYWdEZWZpbml0aW9uKHtjb250ZW50VHlwZTogVGFnQ29udGVudFR5cGUuRVNDQVBBQkxFX1JBV19URVhUfSksXG4gICAgICAndGV4dGFyZWEnOiBuZXcgSHRtbFRhZ0RlZmluaXRpb24oXG4gICAgICAgICAge2NvbnRlbnRUeXBlOiBUYWdDb250ZW50VHlwZS5FU0NBUEFCTEVfUkFXX1RFWFQsIGlnbm9yZUZpcnN0TGY6IHRydWV9KSxcbiAgICB9O1xuICB9XG4gIHJldHVybiBUQUdfREVGSU5JVElPTlNbdGFnTmFtZS50b0xvd2VyQ2FzZSgpXSB8fCBfREVGQVVMVF9UQUdfREVGSU5JVElPTjtcbn1cbiJdfQ==