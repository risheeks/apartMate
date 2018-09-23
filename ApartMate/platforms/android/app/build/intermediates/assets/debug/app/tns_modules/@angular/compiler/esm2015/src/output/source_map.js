/**
 * @license
 * Copyright Google Inc. All Rights Reserved.
 *
 * Use of this source code is governed by an MIT-style license that can be
 * found in the LICENSE file at https://angular.io/license
 */
import { utf8Encode } from '../util';
// https://docs.google.com/document/d/1U1RGAehQwRypUTovF1KRlpiOFze0b-_2gc6fAH0KY0k/edit
const VERSION = 3;
const JS_B64_PREFIX = '# sourceMappingURL=data:application/json;base64,';
export class SourceMapGenerator {
    constructor(file = null) {
        this.file = file;
        this.sourcesContent = new Map();
        this.lines = [];
        this.lastCol0 = 0;
        this.hasMappings = false;
    }
    // The content is `null` when the content is expected to be loaded using the URL
    addSource(url, content = null) {
        if (!this.sourcesContent.has(url)) {
            this.sourcesContent.set(url, content);
        }
        return this;
    }
    addLine() {
        this.lines.push([]);
        this.lastCol0 = 0;
        return this;
    }
    addMapping(col0, sourceUrl, sourceLine0, sourceCol0) {
        if (!this.currentLine) {
            throw new Error(`A line must be added before mappings can be added`);
        }
        if (sourceUrl != null && !this.sourcesContent.has(sourceUrl)) {
            throw new Error(`Unknown source file "${sourceUrl}"`);
        }
        if (col0 == null) {
            throw new Error(`The column in the generated code must be provided`);
        }
        if (col0 < this.lastCol0) {
            throw new Error(`Mapping should be added in output order`);
        }
        if (sourceUrl && (sourceLine0 == null || sourceCol0 == null)) {
            throw new Error(`The source location must be provided when a source url is provided`);
        }
        this.hasMappings = true;
        this.lastCol0 = col0;
        this.currentLine.push({ col0, sourceUrl, sourceLine0, sourceCol0 });
        return this;
    }
    get currentLine() { return this.lines.slice(-1)[0]; }
    toJSON() {
        if (!this.hasMappings) {
            return null;
        }
        const sourcesIndex = new Map();
        const sources = [];
        const sourcesContent = [];
        Array.from(this.sourcesContent.keys()).forEach((url, i) => {
            sourcesIndex.set(url, i);
            sources.push(url);
            sourcesContent.push(this.sourcesContent.get(url) || null);
        });
        let mappings = '';
        let lastCol0 = 0;
        let lastSourceIndex = 0;
        let lastSourceLine0 = 0;
        let lastSourceCol0 = 0;
        this.lines.forEach(segments => {
            lastCol0 = 0;
            mappings += segments
                .map(segment => {
                // zero-based starting column of the line in the generated code
                let segAsStr = toBase64VLQ(segment.col0 - lastCol0);
                lastCol0 = segment.col0;
                if (segment.sourceUrl != null) {
                    // zero-based index into the “sources” list
                    segAsStr +=
                        toBase64VLQ(sourcesIndex.get(segment.sourceUrl) - lastSourceIndex);
                    lastSourceIndex = sourcesIndex.get(segment.sourceUrl);
                    // the zero-based starting line in the original source
                    segAsStr += toBase64VLQ(segment.sourceLine0 - lastSourceLine0);
                    lastSourceLine0 = segment.sourceLine0;
                    // the zero-based starting column in the original source
                    segAsStr += toBase64VLQ(segment.sourceCol0 - lastSourceCol0);
                    lastSourceCol0 = segment.sourceCol0;
                }
                return segAsStr;
            })
                .join(',');
            mappings += ';';
        });
        mappings = mappings.slice(0, -1);
        return {
            'file': this.file || '',
            'version': VERSION,
            'sourceRoot': '',
            'sources': sources,
            'sourcesContent': sourcesContent,
            'mappings': mappings,
        };
    }
    toJsComment() {
        return this.hasMappings ? '//' + JS_B64_PREFIX + toBase64String(JSON.stringify(this, null, 0)) :
            '';
    }
}
export function toBase64String(value) {
    let b64 = '';
    value = utf8Encode(value);
    for (let i = 0; i < value.length;) {
        const i1 = value.charCodeAt(i++);
        const i2 = value.charCodeAt(i++);
        const i3 = value.charCodeAt(i++);
        b64 += toBase64Digit(i1 >> 2);
        b64 += toBase64Digit(((i1 & 3) << 4) | (isNaN(i2) ? 0 : i2 >> 4));
        b64 += isNaN(i2) ? '=' : toBase64Digit(((i2 & 15) << 2) | (i3 >> 6));
        b64 += isNaN(i2) || isNaN(i3) ? '=' : toBase64Digit(i3 & 63);
    }
    return b64;
}
function toBase64VLQ(value) {
    value = value < 0 ? ((-value) << 1) + 1 : value << 1;
    let out = '';
    do {
        let digit = value & 31;
        value = value >> 5;
        if (value > 0) {
            digit = digit | 32;
        }
        out += toBase64Digit(digit);
    } while (value > 0);
    return out;
}
const B64_DIGITS = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/';
function toBase64Digit(value) {
    if (value < 0 || value >= 64) {
        throw new Error(`Can only encode value in the range [0, 63]`);
    }
    return B64_DIGITS[value];
}
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoic291cmNlX21hcC5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbIi4uLy4uLy4uLy4uLy4uLy4uLy4uL3BhY2thZ2VzL2NvbXBpbGVyL3NyYy9vdXRwdXQvc291cmNlX21hcC50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTs7Ozs7O0dBTUc7QUFFSCxPQUFPLEVBQUMsVUFBVSxFQUFDLE1BQU0sU0FBUyxDQUFDO0FBRW5DLHVGQUF1RjtBQUN2RixNQUFNLE9BQU8sR0FBRyxDQUFDLENBQUM7QUFFbEIsTUFBTSxhQUFhLEdBQUcsa0RBQWtELENBQUM7QUFrQnpFLE1BQU07SUFNSixZQUFvQixPQUFvQixJQUFJO1FBQXhCLFNBQUksR0FBSixJQUFJLENBQW9CO1FBTHBDLG1CQUFjLEdBQTZCLElBQUksR0FBRyxFQUFFLENBQUM7UUFDckQsVUFBSyxHQUFnQixFQUFFLENBQUM7UUFDeEIsYUFBUSxHQUFXLENBQUMsQ0FBQztRQUNyQixnQkFBVyxHQUFHLEtBQUssQ0FBQztJQUVtQixDQUFDO0lBRWhELGdGQUFnRjtJQUNoRixTQUFTLENBQUMsR0FBVyxFQUFFLFVBQXVCLElBQUk7UUFDaEQsSUFBSSxDQUFDLElBQUksQ0FBQyxjQUFjLENBQUMsR0FBRyxDQUFDLEdBQUcsQ0FBQyxFQUFFO1lBQ2pDLElBQUksQ0FBQyxjQUFjLENBQUMsR0FBRyxDQUFDLEdBQUcsRUFBRSxPQUFPLENBQUMsQ0FBQztTQUN2QztRQUNELE9BQU8sSUFBSSxDQUFDO0lBQ2QsQ0FBQztJQUVELE9BQU87UUFDTCxJQUFJLENBQUMsS0FBSyxDQUFDLElBQUksQ0FBQyxFQUFFLENBQUMsQ0FBQztRQUNwQixJQUFJLENBQUMsUUFBUSxHQUFHLENBQUMsQ0FBQztRQUNsQixPQUFPLElBQUksQ0FBQztJQUNkLENBQUM7SUFFRCxVQUFVLENBQUMsSUFBWSxFQUFFLFNBQWtCLEVBQUUsV0FBb0IsRUFBRSxVQUFtQjtRQUNwRixJQUFJLENBQUMsSUFBSSxDQUFDLFdBQVcsRUFBRTtZ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QUMsSUFBSSxDQUFDLENBQUMsR0FBRyxDQUFDLENBQUMsQ0FBQyxDQUFDLEtBQUssSUFBSSxDQUFDLENBQUM7SUFFckQsSUFBSSxHQUFHLEdBQUcsRUFBRSxDQUFDO0lBQ2IsR0FBRztRQUNELElBQUksS0FBSyxHQUFHLEtBQUssR0FBRyxFQUFFLENBQUM7UUFDdkIsS0FBSyxHQUFHLEtBQUssSUFBSSxDQUFDLENBQUM7UUFDbkIsSUFBSSxLQUFLLEdBQUcsQ0FBQyxFQUFFO1lBQ2IsS0FBSyxHQUFHLEtBQUssR0FBRyxFQUFFLENBQUM7U0FDcEI7UUFDRCxHQUFHLElBQUksYUFBYSxDQUFDLEtBQUssQ0FBQyxDQUFDO0tBQzdCLFFBQVEsS0FBSyxHQUFHLENBQUMsRUFBRTtJQUVwQixPQUFPLEdBQUcsQ0FBQztBQUNiLENBQUM7QUFFRCxNQUFNLFVBQVUsR0FBRyxrRUFBa0UsQ0FBQztBQUV0Rix1QkFBdUIsS0FBYTtJQUNsQyxJQUFJLEtBQUssR0FBRyxDQUFDLElBQUksS0FBSyxJQUFJLEVBQUUsRUFBRTtRQUM1QixNQUFNLElBQUksS0FBSyxDQUFDLDRDQUE0QyxDQUFDLENBQUM7S0FDL0Q7SUFFRCxPQUFPLFVBQVUsQ0FBQyxLQUFLLENBQUMsQ0FBQztBQUMzQixDQUFDIiwic291cmNlc0NvbnRlbnQiOlsiLyoqXG4gKiBAbGljZW5zZVxuICogQ29weXJpZ2h0IEdvb2dsZSBJbmMuIEFsbCBSaWdodHMgUmVzZXJ2ZWQuXG4gKlxuICogVXNlIG9mIHRoaXMgc291cmNlIGNvZGUgaXMgZ292ZXJuZWQgYnkgYW4gTUlULXN0eWxlIGxpY2Vuc2UgdGhhdCBjYW4gYmVcbiAqIGZvdW5kIGluIHRoZSBMSUNFTlNFIGZpbGUgYXQgaHR0cHM6Ly9hbmd1bGFyLmlvL2xpY2Vuc2VcbiAqL1xuXG5pbXBvcnQge3V0ZjhFbmNvZGV9IGZyb20gJy4uL3V0aWwnO1xuXG4vLyBodHRwczovL2RvY3MuZ29vZ2xlLmNvbS9kb2N1bWVudC9kLzFVMVJHQWVoUXdSeXBVVG92RjFLUmxwaU9GemUwYi1fMmdjNmZBSDBLWTBrL2VkaXRcbmNvbnN0IFZFUlNJT04gPSAzO1xuXG5jb25zdCBKU19CNjRfUFJFRklYID0gJyMgc291cmNlTWFwcGluZ1VSTD1kYXRhOmFwcGxpY2F0aW9uL2pzb247YmFzZTY0LCc7XG5cbnR5cGUgU2VnbWVudCA9IHtcbiAgY29sMDogbnVtYmVyLFxuICBzb3VyY2VVcmw/OiBzdHJpbmcsXG4gIHNvdXJjZUxpbmUwPzogbnVtYmVyLFxuICBzb3VyY2VDb2wwPzogbnVtYmVyLFxufTtcblxuZXhwb3J0IHR5cGUgU291cmNlTWFwID0ge1xuICB2ZXJzaW9uOiBudW1iZXIsXG4gIGZpbGU/OiBzdHJpbmcsXG4gIHNvdXJjZVJvb3Q6IHN0cmluZyxcbiAgc291cmNlczogc3RyaW5nW10sXG4gIHNvdXJjZXNDb250ZW50OiAoc3RyaW5nIHwgbnVsbClbXSxcbiAgbWFwcGluZ3M6IHN0cmluZyxcbn07XG5cbmV4cG9ydCBjbGFzcyBTb3VyY2VNYXBHZW5lcmF0b3Ige1xuICBwcml2YXRlIHNvdXJjZXNDb250ZW50OiBNYXA8c3RyaW5nLCBzdHJpbmd8bnVsbD4gPSBuZXcgTWFwKCk7XG4gIHByaXZhdGUgbGluZXM6IFNlZ21lbnRbXVtdID0gW107XG4gIHByaXZhdGUgbGFzdENvbDA6IG51bWJlciA9IDA7XG4gIHByaXZhdGUgaGFzTWFwcGluZ3MgPSBmYWxzZTtcblxuICBjb25zdHJ1Y3Rvcihwcml2YXRlIGZpbGU6IHN0cmluZ3xudWxsID0gbnVsbCkge31cblxuICAvLyBUaGUgY29udGVudCBpcyBgbnVsbGAgd2hlbiB0aGUgY29udGVudCBpcyBleHBlY3RlZCB0byBiZSBsb2FkZWQgdXNpbmcgdGhlIFVSTFxuICBhZGRTb3VyY2UodXJsOiBzdHJpbmcsIGNvbnRlbnQ6IHN0cmluZ3xudWxsID0gbnVsbCk6IHRoaXMge1xuICAgIGlmICghdGhpcy5zb3VyY2VzQ29udGVudC5oYXModXJsKSkge1xuICAgICAgdGhpcy5zb3VyY2VzQ29udGVudC5zZXQodXJsLCBjb250ZW50KTtcbiAgICB9XG4gICAgcmV0dXJuIHRoaXM7XG4gIH1cblxuICBhZGRMaW5lKCk6IHRoaXMge1xuICAgIHRoaXMubGluZXMucHVzaChbXSk7XG4gICAgdGhpcy5sYXN0Q29sMCA9IDA7XG4gICAgcmV0dXJuIHRoaXM7XG4gIH1cblxuICBhZGRNYXBwaW5nKGNvbDA6IG51bWJlciwgc291cmNlVXJsPzogc3RyaW5nLCBzb3VyY2VMaW5lMD86IG51bWJlciwgc291cmNlQ29sMD86IG51bWJlcik6IHRoaXMge1xuICAgIGlmICghdGhpcy5jdXJyZW50TGluZSkge1xuICAgICAgdGhyb3cgbmV3IEVycm9yKGBBIGxpbmUgbXVzdCBiZSBhZGRlZCBiZWZvcmUgbWFwcGluZ3MgY2FuIGJlIGFkZGVkYCk7XG4gICAgfVxuICAgIGlmIChzb3VyY2VVcmwgIT0gbnVsbCAmJiAhdGhpcy5zb3VyY2VzQ29udGVudC5oYXMoc291cmNlVXJsKSkge1xuICAgICAgdGhyb3cgbmV3IEVycm9yKGBVbmtub3duIHNvdXJjZSBmaWxlIFwiJHtzb3VyY2VVcmx9XCJgKTtcbiAgICB9XG4gICAgaWYgKGNvbDAgPT0gbnVsbCkge1xuICAgICAgdGhyb3cgbmV3IEVycm9yKGBUaGUgY29sdW1uIGluIHRoZSBnZW5lcmF0ZWQgY29kZSBtdXN0IGJlIHByb3ZpZGVkYCk7XG4gICAgfVxuICAgIGlmIChjb2wwIDwgdGhpcy5sYXN0Q29sMCkge1xuICAgICAgdGhyb3cgbmV3IEVycm9yKGBNYXBwaW5nIHNob3VsZCBiZSBhZGRlZCBpbiBvdXRwdXQgb3JkZXJgKTtcbiAgICB9XG4gICAgaWYgKHNvdXJjZVVybCAmJiAoc291cmNlTGluZTAgPT0gbnVsbCB8fCBzb3VyY2VDb2wwID09IG51bGwpKSB7XG4gICAgICB0aHJvdyBuZXcgRXJyb3IoYFRoZSBzb3VyY2UgbG9jYXRpb24gbXVzdCBiZSBwcm92aWRlZCB3aGVuIGEgc291cmNlIHVybCBpcyBwcm92aWRlZGApO1xuICAgIH1cblxuICAgIHRoaXMuaGFzTWFwcGluZ3MgPSB0cnVlO1xuICAgIHRoaXMubGFzdENvbDAgPSBjb2wwO1xuICAgIHRoaXMuY3VycmVudExpbmUucHVzaCh7Y29sMCwgc291cmNlVXJsLCBzb3VyY2VMaW5lMCwgc291cmNlQ29sMH0pO1xuICAgIHJldHVybiB0aGlzO1xuICB9XG5cbiAgcHJpdmF0ZSBnZXQgY3VycmVudExpbmUoKTogU2VnbWVudFtdfG51bGwgeyByZXR1cm4gdGhpcy5saW5lcy5zbGljZSgtMSlbMF07IH1cblxuICB0b0pTT04oKTogU291cmNlTWFwfG51bGwge1xuICAgIGlmICghdGhpcy5oYXNNYXBwaW5ncykge1xuICAgICAgcmV0dXJuIG51bGw7XG4gICAgfVxuXG4gICAgY29uc3Qgc291cmNlc0luZGV4ID0gbmV3IE1hcDxzdHJpbmcsIG51bWJlcj4oKTtcbiAgICBjb25zdCBzb3VyY2VzOiBzdHJpbmdbXSA9IFtdO1xuICAgIGNvbnN0IHNvdXJjZXNDb250ZW50OiAoc3RyaW5nIHwgbnVsbClbXSA9IFtdO1xuXG4gICAgQXJyYXkuZnJvbSh0aGlzLnNvdXJjZXNDb250ZW50LmtleXMoKSkuZm9yRWFjaCgodXJsOiBzdHJpbmcsIGk6IG51bWJlcikgPT4ge1xuICAgICAgc291cmNlc0luZGV4LnNldCh1cmwsIGkpO1xuICAgICAgc291cmNlcy5wdXNoKHVybCk7XG4gICAgICBzb3VyY2VzQ29udGVudC5wdXNoKHRoaXMuc291cmNlc0NvbnRlbnQuZ2V0KHVybCkgfHwgbnVsbCk7XG4gICAgfSk7XG5cbiAgICBsZXQgbWFwcGluZ3M6IHN0cmluZyA9ICcnO1xuICAgIGxldCBsYXN0Q29sMDogbnVtYmVyID0gMDtcbiAgICBsZXQgbGFzdFNvdXJjZUluZGV4OiBudW1iZXIgPSAwO1xuICAgIGxldCBsYXN0U291cmNlTGluZTA6IG51bWJlciA9IDA7XG4gICAgbGV0IGxhc3RTb3VyY2VDb2wwOiBudW1iZXIgPSAwO1xuXG4gICAgdGhpcy5saW5lcy5mb3JFYWNoKHNlZ21lbnRzID0+IHtcbiAgICAgIGxhc3RDb2wwID0gMDtcblxuICAgICAgbWFwcGluZ3MgKz0gc2VnbWVudHNcbiAgICAgICAgICAgICAgICAgICAgICAubWFwKHNlZ21lbnQgPT4ge1xuICAgICAgICAgICAgICAgICAgICAgICAgLy8gemVyby1iYXNlZCBzdGFydGluZyBjb2x1bW4gb2YgdGhlIGxpbmUgaW4gdGhlIGdlbmVyYXRlZCBjb2RlXG4gICAgICAgICAgICAgICAgICAgICAgICBsZXQgc2VnQXNTdHIgPSB0b0Jhc2U2NFZMUShzZWdtZW50LmNvbDAgLSBsYXN0Q29sMCk7XG4gICAgICAgICAgICAgICAgICAgICAgICBsYXN0Q29sMCA9IHNlZ21lbnQuY29sMDtcblxuICAgICAgICAgICAgICAgICAgICAgICAgaWYgKHNlZ21lbnQuc291cmNlVXJsICE9IG51bGwpIHtcbiAgICAgICAgICAgICAgICAgICAgICAgICAgLy8gemVyby1iYXNlZCBpbmRleCBpbnRvIHRoZSDigJxzb3VyY2Vz4oCdIGxpc3RcbiAgICAgICAgICAgICAgICAgICAgICAgICAgc2VnQXNTdHIgKz1cbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIHRvQmFzZTY0VkxRKHNvdXJjZXNJbmRleC5nZXQoc2VnbWVudC5zb3VyY2VVcmwpICEgLSBsYXN0U291cmNlSW5kZXgpO1xuICAgICAgICAgICAgICAgICAgICAgICAgICBsYXN0U291cmNlSW5kZXggPSBzb3VyY2VzSW5kZXguZ2V0KHNlZ21lbnQuc291cmNlVXJsKSAhO1xuICAgICAgICAgICAgICAgICAgICAgICAgICAvLyB0aGUgemVyby1iYXNlZCBzdGFydGluZyBsaW5lIGluIHRoZSBvcmlnaW5hbCBzb3VyY2VcbiAgICAgICAgICAgICAgICAgICAgICAgICAgc2VnQXNTdHIgKz0gdG9CYXNlNjRWTFEoc2VnbWVudC5zb3VyY2VMaW5lMCAhIC0gbGFzdFNvdXJjZUxpbmUwKTtcbiAgICAgICAgICAgICAgICAgICAgICAgICAgbGFzdFNvdXJjZUxpbmUwID0gc2VnbWVudC5zb3VyY2VMaW5lMCAhO1xuICAgICAgICAgICAgICAgICAgICAgICAgICAvLyB0aGUgemVyby1iYXNlZCBzdGFydGluZyBjb2x1bW4gaW4gdGhlIG9yaWdpbmFsIHNvdXJjZVxuICAgICAgICAgICAgICAgICAgICAgICAgICBzZWdBc1N0ciArPSB0b0Jhc2U2NFZMUShzZWdtZW50LnNvdXJjZUNvbDAgISAtIGxhc3RTb3VyY2VDb2wwKTtcbiAgICAgICAgICAgICAgICAgICAgICAgICAgbGFzdFNvdXJjZUNvbDAgPSBzZWdtZW50LnNvdXJjZUNvbDAgITtcbiAgICAgICAgICAgICAgICAgICAgICAgIH1cblxuICAgICAgICAgICAgICAgICAgICAgICAgcmV0dXJuIHNlZ0FzU3RyO1xuICAgICAgICAgICAgICAgICAgICAgIH0pXG4gICAgICAgICAgICAgICAgICAgICAgLmpvaW4oJywnKTtcbiAgICAgIG1hcHBpbmdzICs9ICc7JztcbiAgICB9KTtcblxuICAgIG1hcHBpbmdzID0gbWFwcGluZ3Muc2xpY2UoMCwgLTEpO1xuXG4gICAgcmV0dXJuIHtcbiAgICAgICdmaWxlJzogdGhpcy5maWxlIHx8ICcnLFxuICAgICAgJ3ZlcnNpb24nOiBWRVJTSU9OLFxuICAgICAgJ3NvdXJjZVJvb3QnOiAnJyxcbiAgICAgICdzb3VyY2VzJzogc291cmNlcyxcbiAgICAgICdzb3VyY2VzQ29udGVudCc6IHNvdXJjZXNDb250ZW50LFxuICAgICAgJ21hcHBpbmdzJzogbWFwcGluZ3MsXG4gICAgfTtcbiAgfVxuXG4gIHRvSnNDb21tZW50KCk6IHN0cmluZyB7XG4gICAgcmV0dXJuIHRoaXMuaGFzTWFwcGluZ3MgPyAnLy8nICsgSlNfQjY0X1BSRUZJWCArIHRvQmFzZTY0U3RyaW5nKEpTT04uc3RyaW5naWZ5KHRoaXMsIG51bGwsIDApKSA6XG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAnJztcbiAgfVxufVxuXG5leHBvcnQgZnVuY3Rpb24gdG9CYXNlNjRTdHJpbmcodmFsdWU6IHN0cmluZyk6IHN0cmluZyB7XG4gIGxldCBiNjQgPSAnJztcbiAgdmFsdWUgPSB1dGY4RW5jb2RlKHZhbHVlKTtcbiAgZm9yIChsZXQgaSA9IDA7IGkgPCB2YWx1ZS5sZW5ndGg7KSB7XG4gICAgY29uc3QgaTEgPSB2YWx1ZS5jaGFyQ29kZUF0KGkrKyk7XG4gICAgY29uc3QgaTIgPSB2YWx1ZS5jaGFyQ29kZUF0KGkrKyk7XG4gICAgY29uc3QgaTMgPSB2YWx1ZS5jaGFyQ29kZUF0KGkrKyk7XG4gICAgYjY0ICs9IHRvQmFzZTY0RGlnaXQoaTEgPj4gMik7XG4gICAgYjY0ICs9IHRvQmFzZTY0RGlnaXQoKChpMSAmIDMpIDw8IDQpIHwgKGlzTmFOKGkyKSA/IDAgOiBpMiA+PiA0KSk7XG4gICAgYjY0ICs9IGlzTmFOKGkyKSA/ICc9JyA6IHRvQmFzZTY0RGlnaXQoKChpMiAmIDE1KSA8PCAyKSB8IChpMyA+PiA2KSk7XG4gICAgYjY0ICs9IGlzTmFOKGkyKSB8fCBpc05hTihpMykgPyAnPScgOiB0b0Jhc2U2NERpZ2l0KGkzICYgNjMpO1xuICB9XG5cbiAgcmV0dXJuIGI2NDtcbn1cblxuZnVuY3Rpb24gdG9CYXNlNjRWTFEodmFsdWU6IG51bWJlcik6IHN0cmluZyB7XG4gIHZhbHVlID0gdmFsdWUgPCAwID8gKCgtdmFsdWUpIDw8IDEpICsgMSA6IHZhbHVlIDw8IDE7XG5cbiAgbGV0IG91dCA9ICcnO1xuICBkbyB7XG4gICAgbGV0IGRpZ2l0ID0gdmFsdWUgJiAzMTtcbiAgICB2YWx1ZSA9IHZhbHVlID4+IDU7XG4gICAgaWYgKHZhbHVlID4gMCkge1xuICAgICAgZGlnaXQgPSBkaWdpdCB8IDMyO1xuICAgIH1cbiAgICBvdXQgKz0gdG9CYXNlNjREaWdpdChkaWdpdCk7XG4gIH0gd2hpbGUgKHZhbHVlID4gMCk7XG5cbiAgcmV0dXJuIG91dDtcbn1cblxuY29uc3QgQjY0X0RJR0lUUyA9ICdBQkNERUZHSElKS0xNTk9QUVJTVFVWV1hZWmFiY2RlZmdoaWprbG1ub3BxcnN0dXZ3eHl6MDEyMzQ1Njc4OSsvJztcblxuZnVuY3Rpb24gdG9CYXNlNjREaWdpdCh2YWx1ZTogbnVtYmVyKTogc3RyaW5nIHtcbiAgaWYgKHZhbHVlIDwgMCB8fCB2YWx1ZSA+PSA2NCkge1xuICAgIHRocm93IG5ldyBFcnJvcihgQ2FuIG9ubHkgZW5jb2RlIHZhbHVlIGluIHRoZSByYW5nZSBbMCwgNjNdYCk7XG4gIH1cblxuICByZXR1cm4gQjY0X0RJR0lUU1t2YWx1ZV07XG59XG4iXX0=