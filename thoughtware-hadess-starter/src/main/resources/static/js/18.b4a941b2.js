(window.webpackJsonp=window.webpackJsonp||[]).push([[18],{1171:function(t,e,r){"use strict";var n=r(3),o=r(0),a={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"path",attrs:{d:"M516 673c0 4.4 3.4 8 7.5 8h185c4.1 0 7.5-3.6 7.5-8v-48c0-4.4-3.4-8-7.5-8h-185c-4.1 0-7.5 3.6-7.5 8v48zm-194.9 6.1l192-161c3.8-3.2 3.8-9.1 0-12.3l-192-160.9A7.95 7.95 0 00308 351v62.7c0 2.4 1 4.6 2.9 6.1L420.7 512l-109.8 92.2a8.1 8.1 0 00-2.9 6.1V673c0 6.8 7.9 10.5 13.1 6.1zM880 112H144c-17.7 0-32 14.3-32 32v736c0 17.7 14.3 32 32 32h736c17.7 0 32-14.3 32-32V144c0-17.7-14.3-32-32-32zm-40 728H184V184h656v656z"}}]},name:"code",theme:"outlined"},i=r(12),c=function(t,e){return o.createElement(i.a,Object(n.a)(Object(n.a)({},t),{},{ref:e,icon:a}))};c.displayName="CodeOutlined";e.a=o.forwardRef(c)},346:function(t,e,r){"use strict";r.r(e),function(t){r(255);var n,o=r(180),a=(r(161),r(80)),i=r(0),c=r.n(i),u=r(128),l=(r(734),r(1171)),s=r(1159),f=r(342),h=r(28),d=r(90),p=r(735),y=r(443),v=r(7);function m(t){return(m="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t})(t)}function g(){return(g=Object.assign?Object.assign.bind():function(t){for(var e=1;e<arguments.length;e++){var r=arguments[e];for(var n in r)Object.prototype.hasOwnProperty.call(r,n)&&(t[n]=r[n])}return t}).apply(this,arguments)}function b(){/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */b=function(){return t};var t={},e=Object.prototype,r=e.hasOwnProperty,n=Object.defineProperty||function(t,e,r){t[e]=r.value},o="function"==typeof Symbol?Symbol:{},a=o.iterator||"@@iterator",i=o.asyncIterator||"@@asyncIterator",c=o.toStringTag||"@@toStringTag";function u(t,e,r){return Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}),t[e]}try{u({},"")}catch(t){u=function(t,e,r){return t[e]=r}}function l(t,e,r,o){var a=e&&e.prototype instanceof h?e:h,i=Object.create(a.prototype),c=new G(o||[]);return n(i,"_invoke",{value:x(t,r,c)}),i}function s(t,e,r){try{return{type:"normal",arg:t.call(e,r)}}catch(t){return{type:"throw",arg:t}}}t.wrap=l;var f={};function h(){}function d(){}function p(){}var y={};u(y,a,(function(){return this}));var v=Object.getPrototypeOf,g=v&&v(v(k([])));g&&g!==e&&r.call(g,a)&&(y=g);var w=p.prototype=h.prototype=Object.create(y);function L(t){["next","throw","return"].forEach((function(e){u(t,e,(function(t){return this._invoke(e,t)}))}))}function E(t,e){var o;n(this,"_invoke",{value:function(n,a){function i(){return new e((function(o,i){!function n(o,a,i,c){var u=s(t[o],t,a);if("throw"!==u.type){var l=u.arg,f=l.value;return f&&"object"==m(f)&&r.call(f,"__await")?e.resolve(f.__await).then((function(t){n("next",t,i,c)}),(function(t){n("throw",t,i,c)})):e.resolve(f).then((function(t){l.value=t,i(l)}),(function(t){return n("throw",t,i,c)}))}c(u.arg)}(n,a,o,i)}))}return o=o?o.then(i,i):i()}})}function x(t,e,r){var n="suspendedStart";return function(o,a){if("executing"===n)throw new Error("Generator is already running");if("completed"===n){if("throw"===o)throw a;return N()}for(r.method=o,r.arg=a;;){var i=r.delegate;if(i){var c=j(i,r);if(c){if(c===f)continue;return c}}if("next"===r.method)r.sent=r._sent=r.arg;else if("throw"===r.method){if("suspendedStart"===n)throw n="completed",r.arg;r.dispatchException(r.arg)}else"return"===r.method&&r.abrupt("return",r.arg);n="executing";var u=s(t,e,r);if("normal"===u.type){if(n=r.done?"completed":"suspendedYield",u.arg===f)continue;return{value:u.arg,done:r.done}}"throw"===u.type&&(n="completed",r.method="throw",r.arg=u.arg)}}}function j(t,e){var r=e.method,n=t.iterator[r];if(void 0===n)return e.delegate=null,"throw"===r&&t.iterator.return&&(e.method="return",e.arg=void 0,j(t,e),"throw"===e.method)||"return"!==r&&(e.method="throw",e.arg=new TypeError("The iterator does not provide a '"+r+"' method")),f;var o=s(n,t.iterator,e.arg);if("throw"===o.type)return e.method="throw",e.arg=o.arg,e.delegate=null,f;var a=o.arg;return a?a.done?(e[t.resultName]=a.value,e.next=t.nextLoc,"return"!==e.method&&(e.method="next",e.arg=void 0),e.delegate=null,f):a:(e.method="throw",e.arg=new TypeError("iterator result is not an object"),e.delegate=null,f)}function O(t){var e={tryLoc:t[0]};1 in t&&(e.catchLoc=t[1]),2 in t&&(e.finallyLoc=t[2],e.afterLoc=t[3]),this.tryEntries.push(e)}function S(t){var e=t.completion||{};e.type="normal",delete e.arg,t.completion=e}function G(t){this.tryEntries=[{tryLoc:"root"}],t.forEach(O,this),this.reset(!0)}function k(t){if(t){var e=t[a];if(e)return e.call(t);if("function"==typeof t.next)return t;if(!isNaN(t.length)){var n=-1,o=function e(){for(;++n<t.length;)if(r.call(t,n))return e.value=t[n],e.done=!1,e;return e.value=void 0,e.done=!0,e};return o.next=o}}return{next:N}}function N(){return{value:void 0,done:!0}}return d.prototype=p,n(w,"constructor",{value:p,configurable:!0}),n(p,"constructor",{value:d,configurable:!0}),d.displayName=u(p,c,"GeneratorFunction"),t.isGeneratorFunction=function(t){var e="function"==typeof t&&t.constructor;return!!e&&(e===d||"GeneratorFunction"===(e.displayName||e.name))},t.mark=function(t){return Object.setPrototypeOf?Object.setPrototypeOf(t,p):(t.__proto__=p,u(t,c,"GeneratorFunction")),t.prototype=Object.create(w),t},t.awrap=function(t){return{__await:t}},L(E.prototype),u(E.prototype,i,(function(){return this})),t.AsyncIterator=E,t.async=function(e,r,n,o,a){void 0===a&&(a=Promise);var i=new E(l(e,r,n,o),a);return t.isGeneratorFunction(r)?i:i.next().then((function(t){return t.done?t.value:i.next()}))},L(w),u(w,c,"Generator"),u(w,a,(function(){return this})),u(w,"toString",(function(){return"[object Generator]"})),t.keys=function(t){var e=Object(t),r=[];for(var n in e)r.push(n);return r.reverse(),function t(){for(;r.length;){var n=r.pop();if(n in e)return t.value=n,t.done=!1,t}return t.done=!0,t}},t.values=k,G.prototype={constructor:G,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=void 0,this.done=!1,this.delegate=null,this.method="next",this.arg=void 0,this.tryEntries.forEach(S),!t)for(var e in this)"t"===e.charAt(0)&&r.call(this,e)&&!isNaN(+e.slice(1))&&(this[e]=void 0)},stop:function(){this.done=!0;var t=this.tryEntries[0].completion;if("throw"===t.type)throw t.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var e=this;function n(r,n){return i.type="throw",i.arg=t,e.next=r,n&&(e.method="next",e.arg=void 0),!!n}for(var o=this.tryEntries.length-1;o>=0;--o){var a=this.tryEntries[o],i=a.completion;if("root"===a.tryLoc)return n("end");if(a.tryLoc<=this.prev){var c=r.call(a,"catchLoc"),u=r.call(a,"finallyLoc");if(c&&u){if(this.prev<a.catchLoc)return n(a.catchLoc,!0);if(this.prev<a.finallyLoc)return n(a.finallyLoc)}else if(c){if(this.prev<a.catchLoc)return n(a.catchLoc,!0)}else{if(!u)throw new Error("try statement without catch or finally");if(this.prev<a.finallyLoc)return n(a.finallyLoc)}}}},abrupt:function(t,e){for(var n=this.tryEntries.length-1;n>=0;--n){var o=this.tryEntries[n];if(o.tryLoc<=this.prev&&r.call(o,"finallyLoc")&&this.prev<o.finallyLoc){var a=o;break}}a&&("break"===t||"continue"===t)&&a.tryLoc<=e&&e<=a.finallyLoc&&(a=null);var i=a?a.completion:{};return i.type=t,i.arg=e,a?(this.method="next",this.next=a.finallyLoc,f):this.complete(i)},complete:function(t,e){if("throw"===t.type)throw t.arg;return"break"===t.type||"continue"===t.type?this.next=t.arg:"return"===t.type?(this.rval=this.arg=t.arg,this.method="return",this.next="end"):"normal"===t.type&&e&&(this.next=e),f},finish:function(t){for(var e=this.tryEntries.length-1;e>=0;--e){var r=this.tryEntries[e];if(r.finallyLoc===t)return this.complete(r.completion,r.afterLoc),S(r),f}},catch:function(t){for(var e=this.tryEntries.length-1;e>=0;--e){var r=this.tryEntries[e];if(r.tryLoc===t){var n=r.completion;if("throw"===n.type){var o=n.arg;S(r)}return o}}throw new Error("illegal catch attempt")},delegateYield:function(t,e,r){return this.delegate={iterator:k(t),resultName:e,nextLoc:r},"next"===this.method&&(this.arg=void 0),f}},t}function w(t,e,r,n,o,a,i){try{var c=t[a](i),u=c.value}catch(t){return void r(t)}c.done?e(u):Promise.resolve(u).then(n,o)}function L(t){return function(){var e=this,r=arguments;return new Promise((function(n,o){var a=t.apply(e,r);function i(t){w(a,n,o,i,c,"next",t)}function c(t){w(a,n,o,i,c,"throw",t)}i(void 0)}))}}function E(t,e){return function(t){if(Array.isArray(t))return t}(t)||function(t,e){var r=null==t?null:"undefined"!=typeof Symbol&&t[Symbol.iterator]||t["@@iterator"];if(null!=r){var n,o,a,i,c=[],u=!0,l=!1;try{if(a=(r=r.call(t)).next,0===e){if(Object(r)!==r)return;u=!1}else for(;!(u=(n=a.call(r)).done)&&(c.push(n.value),c.length!==e);u=!0);}catch(t){l=!0,o=t}finally{try{if(!u&&null!=r.return&&(i=r.return(),Object(i)!==i))return}finally{if(l)throw o}}return c}}(t,e)||function(t,e){if(!t)return;if("string"==typeof t)return x(t,e);var r=Object.prototype.toString.call(t).slice(8,-1);"Object"===r&&t.constructor&&(r=t.constructor.name);if("Map"===r||"Set"===r)return Array.from(t);if("Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r))return x(t,e)}(t,e)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function x(t,e){(null==e||e>t.length)&&(e=t.length);for(var r=0,n=new Array(e);r<e;r++)n[r]=t[r];return n}(n="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.enterModule:void 0)&&n(t);var j="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.default.signature:function(t){return t},O=function(t){var e=t.location,r=t.match.params,n=t.repositoryStore,h=t.systemRoleStore,d=n.repositoryData,m=n.findRepository,w=n.findAllRepository,x=n.repositoryAllList,j=h.getInitProjectPermissions,O=r.id,S=e.pathname,G=E(Object(i.useState)(""),2),k=G[0],N=G[1],_=E(Object(i.useState)(!1),2),H=_[0],P=_[1],A=[{key:"2",title:"制品",id:"/repository/".concat(O,"/libraryList"),icon:c.a.createElement(l.a,{className:"icon-nav"})},{key:"3",title:"制品扫描",id:"/repository/".concat(O,"/scanPlay"),icon:c.a.createElement(l.a,{className:"icon-nav"})}];Object(i.useEffect)(L(b().mark((function e(){return b().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:w(),m(O).then((function(e){0===e.code&&(e.data||t.history.push("/repository"))})),j(Object(v.getUser)().userId,O,!0);case 3:case"end":return e.stop()}}),e)}))),[]),Object(i.useEffect)(L(b().mark((function t(){return b().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:S.includes("scan")?N("/repository/".concat(O,"/scanPlay")):N(S);case 1:case"end":return t.stop()}}),t)}))),[S]);var I=function(){var e=L(b().mark((function e(r){return b().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:N(r.router),t.history.push(r.router);case 2:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}(),T=function(){var e=L(b().mark((function e(){return b().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:N(null),t.history.push("/repository/".concat(O,"/setting/repositoryInfo"));case 2:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}();return c.a.createElement("div",{className:"setting "},d&&c.a.createElement("div",{className:"left-nav left-nav-setting-width"},c.a.createElement("div",null,c.a.createElement("div",{className:"nav-repository"},c.a.createElement(o.default,{overlay:c.a.createElement(p.a,g({},t,{repositoryAllList:x,setTriggerVisible:P,repositoryId:O})),trigger:["click"],visible:H,onVisibleChange:function(t){return P(t)},overlayClassName:"aside-dropdown-normal aside-dropdown"},c.a.createElement("div",{className:"repository-nav"},c.a.createElement(a.default,{placement:"right",title:null==d?void 0:d.name},c.a.createElement("div",null,c.a.createElement(y.a,{text:d.name,colors:d.color}))),c.a.createElement(s.a,{className:"repository-nav-icon"})))),null==A?void 0:A.map((function(t){return c.a.createElement("div",{key:t.key,className:"".concat(k===t.router&&" choice-table-nav"," table-nav "),onClick:function(){return I(t)}},c.a.createElement("div",{className:"setting-nav"},c.a.createElement("div",null,t.icon),c.a.createElement("div",null,t.title)))}))),c.a.createElement("div",{className:"setting-nav-color setting-nav-style",onClick:T},c.a.createElement("div",{className:"setting-nav "},c.a.createElement(f.a,{className:"icon-nav"}),c.a.createElement("div",null,"设置")))),c.a.createElement("div",{className:"setting_right_height"},Object(u.a)(t.route.routes)))};j(O,"useState{[navPath,setNavPath]('')}\nuseState{[triggerVisible,setTriggerVisible](false)}\nuseEffect{}\nuseEffect{}");var S,G,k=Object(h.g)(Object(d.inject)("systemRoleStore","repositoryStore")(Object(d.observer)(O)));e.default=k,(S="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.default:void 0)&&(S.register(O,"RepositoryAside","/Users/limingliang/xcode-xpack/web/thoughtware-hadess-ui/src/repository/navigator/RepositoryAside.js"),S.register(k,"default","/Users/limingliang/xcode-xpack/web/thoughtware-hadess-ui/src/repository/navigator/RepositoryAside.js")),(G="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.leaveModule:void 0)&&G(t)}.call(this,r(51)(t))},443:function(t,e,r){"use strict";(function(t){var n,o=r(0),a=r.n(o);r(469);(n="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.enterModule:void 0)&&n(t);"undefined"!=typeof reactHotLoaderGlobal&&reactHotLoaderGlobal.default.signature;var i,c,u=function(t){var e=t.text,r=t.colors;return a.a.createElement("span",{className:"xcode-listname-icon ".concat(r?"xcode-icon-".concat(r):"xcode-icon-0")},e&&e.substring(0,1).toUpperCase())},l=u;e.a=l,(i="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.default:void 0)&&(i.register(u,"ListIcon","/Users/limingliang/xcode-xpack/web/thoughtware-hadess-ui/src/common/repositoryIcon/Listicon.js"),i.register(l,"default","/Users/limingliang/xcode-xpack/web/thoughtware-hadess-ui/src/common/repositoryIcon/Listicon.js")),(c="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.leaveModule:void 0)&&c(t)}).call(this,r(51)(t))},469:function(t,e,r){},734:function(t,e,r){},735:function(t,e,r){"use strict";(function(t){var n,o=r(0),a=r.n(o),i=(r(736),r(443));function c(t){return(c="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t})(t)}function u(){/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */u=function(){return t};var t={},e=Object.prototype,r=e.hasOwnProperty,n=Object.defineProperty||function(t,e,r){t[e]=r.value},o="function"==typeof Symbol?Symbol:{},a=o.iterator||"@@iterator",i=o.asyncIterator||"@@asyncIterator",l=o.toStringTag||"@@toStringTag";function s(t,e,r){return Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}),t[e]}try{s({},"")}catch(t){s=function(t,e,r){return t[e]=r}}function f(t,e,r,o){var a=e&&e.prototype instanceof p?e:p,i=Object.create(a.prototype),c=new G(o||[]);return n(i,"_invoke",{value:x(t,r,c)}),i}function h(t,e,r){try{return{type:"normal",arg:t.call(e,r)}}catch(t){return{type:"throw",arg:t}}}t.wrap=f;var d={};function p(){}function y(){}function v(){}var m={};s(m,a,(function(){return this}));var g=Object.getPrototypeOf,b=g&&g(g(k([])));b&&b!==e&&r.call(b,a)&&(m=b);var w=v.prototype=p.prototype=Object.create(m);function L(t){["next","throw","return"].forEach((function(e){s(t,e,(function(t){return this._invoke(e,t)}))}))}function E(t,e){var o;n(this,"_invoke",{value:function(n,a){function i(){return new e((function(o,i){!function n(o,a,i,u){var l=h(t[o],t,a);if("throw"!==l.type){var s=l.arg,f=s.value;return f&&"object"==c(f)&&r.call(f,"__await")?e.resolve(f.__await).then((function(t){n("next",t,i,u)}),(function(t){n("throw",t,i,u)})):e.resolve(f).then((function(t){s.value=t,i(s)}),(function(t){return n("throw",t,i,u)}))}u(l.arg)}(n,a,o,i)}))}return o=o?o.then(i,i):i()}})}function x(t,e,r){var n="suspendedStart";return function(o,a){if("executing"===n)throw new Error("Generator is already running");if("completed"===n){if("throw"===o)throw a;return N()}for(r.method=o,r.arg=a;;){var i=r.delegate;if(i){var c=j(i,r);if(c){if(c===d)continue;return c}}if("next"===r.method)r.sent=r._sent=r.arg;else if("throw"===r.method){if("suspendedStart"===n)throw n="completed",r.arg;r.dispatchException(r.arg)}else"return"===r.method&&r.abrupt("return",r.arg);n="executing";var u=h(t,e,r);if("normal"===u.type){if(n=r.done?"completed":"suspendedYield",u.arg===d)continue;return{value:u.arg,done:r.done}}"throw"===u.type&&(n="completed",r.method="throw",r.arg=u.arg)}}}function j(t,e){var r=e.method,n=t.iterator[r];if(void 0===n)return e.delegate=null,"throw"===r&&t.iterator.return&&(e.method="return",e.arg=void 0,j(t,e),"throw"===e.method)||"return"!==r&&(e.method="throw",e.arg=new TypeError("The iterator does not provide a '"+r+"' method")),d;var o=h(n,t.iterator,e.arg);if("throw"===o.type)return e.method="throw",e.arg=o.arg,e.delegate=null,d;var a=o.arg;return a?a.done?(e[t.resultName]=a.value,e.next=t.nextLoc,"return"!==e.method&&(e.method="next",e.arg=void 0),e.delegate=null,d):a:(e.method="throw",e.arg=new TypeError("iterator result is not an object"),e.delegate=null,d)}function O(t){var e={tryLoc:t[0]};1 in t&&(e.catchLoc=t[1]),2 in t&&(e.finallyLoc=t[2],e.afterLoc=t[3]),this.tryEntries.push(e)}function S(t){var e=t.completion||{};e.type="normal",delete e.arg,t.completion=e}function G(t){this.tryEntries=[{tryLoc:"root"}],t.forEach(O,this),this.reset(!0)}function k(t){if(t){var e=t[a];if(e)return e.call(t);if("function"==typeof t.next)return t;if(!isNaN(t.length)){var n=-1,o=function e(){for(;++n<t.length;)if(r.call(t,n))return e.value=t[n],e.done=!1,e;return e.value=void 0,e.done=!0,e};return o.next=o}}return{next:N}}function N(){return{value:void 0,done:!0}}return y.prototype=v,n(w,"constructor",{value:v,configurable:!0}),n(v,"constructor",{value:y,configurable:!0}),y.displayName=s(v,l,"GeneratorFunction"),t.isGeneratorFunction=function(t){var e="function"==typeof t&&t.constructor;return!!e&&(e===y||"GeneratorFunction"===(e.displayName||e.name))},t.mark=function(t){return Object.setPrototypeOf?Object.setPrototypeOf(t,v):(t.__proto__=v,s(t,l,"GeneratorFunction")),t.prototype=Object.create(w),t},t.awrap=function(t){return{__await:t}},L(E.prototype),s(E.prototype,i,(function(){return this})),t.AsyncIterator=E,t.async=function(e,r,n,o,a){void 0===a&&(a=Promise);var i=new E(f(e,r,n,o),a);return t.isGeneratorFunction(r)?i:i.next().then((function(t){return t.done?t.value:i.next()}))},L(w),s(w,l,"Generator"),s(w,a,(function(){return this})),s(w,"toString",(function(){return"[object Generator]"})),t.keys=function(t){var e=Object(t),r=[];for(var n in e)r.push(n);return r.reverse(),function t(){for(;r.length;){var n=r.pop();if(n in e)return t.value=n,t.done=!1,t}return t.done=!0,t}},t.values=k,G.prototype={constructor:G,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=void 0,this.done=!1,this.delegate=null,this.method="next",this.arg=void 0,this.tryEntries.forEach(S),!t)for(var e in this)"t"===e.charAt(0)&&r.call(this,e)&&!isNaN(+e.slice(1))&&(this[e]=void 0)},stop:function(){this.done=!0;var t=this.tryEntries[0].completion;if("throw"===t.type)throw t.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var e=this;function n(r,n){return i.type="throw",i.arg=t,e.next=r,n&&(e.method="next",e.arg=void 0),!!n}for(var o=this.tryEntries.length-1;o>=0;--o){var a=this.tryEntries[o],i=a.completion;if("root"===a.tryLoc)return n("end");if(a.tryLoc<=this.prev){var c=r.call(a,"catchLoc"),u=r.call(a,"finallyLoc");if(c&&u){if(this.prev<a.catchLoc)return n(a.catchLoc,!0);if(this.prev<a.finallyLoc)return n(a.finallyLoc)}else if(c){if(this.prev<a.catchLoc)return n(a.catchLoc,!0)}else{if(!u)throw new Error("try statement without catch or finally");if(this.prev<a.finallyLoc)return n(a.finallyLoc)}}}},abrupt:function(t,e){for(var n=this.tryEntries.length-1;n>=0;--n){var o=this.tryEntries[n];if(o.tryLoc<=this.prev&&r.call(o,"finallyLoc")&&this.prev<o.finallyLoc){var a=o;break}}a&&("break"===t||"continue"===t)&&a.tryLoc<=e&&e<=a.finallyLoc&&(a=null);var i=a?a.completion:{};return i.type=t,i.arg=e,a?(this.method="next",this.next=a.finallyLoc,d):this.complete(i)},complete:function(t,e){if("throw"===t.type)throw t.arg;return"break"===t.type||"continue"===t.type?this.next=t.arg:"return"===t.type?(this.rval=this.arg=t.arg,this.method="return",this.next="end"):"normal"===t.type&&e&&(this.next=e),d},finish:function(t){for(var e=this.tryEntries.length-1;e>=0;--e){var r=this.tryEntries[e];if(r.finallyLoc===t)return this.complete(r.completion,r.afterLoc),S(r),d}},catch:function(t){for(var e=this.tryEntries.length-1;e>=0;--e){var r=this.tryEntries[e];if(r.tryLoc===t){var n=r.completion;if("throw"===n.type){var o=n.arg;S(r)}return o}}throw new Error("illegal catch attempt")},delegateYield:function(t,e,r){return this.delegate={iterator:k(t),resultName:e,nextLoc:r},"next"===this.method&&(this.arg=void 0),d}},t}function l(t,e,r,n,o,a,i){try{var c=t[a](i),u=c.value}catch(t){return void r(t)}c.done?e(u):Promise.resolve(u).then(n,o)}function s(t,e){return function(t){if(Array.isArray(t))return t}(t)||function(t,e){var r=null==t?null:"undefined"!=typeof Symbol&&t[Symbol.iterator]||t["@@iterator"];if(null!=r){var n,o,a,i,c=[],u=!0,l=!1;try{if(a=(r=r.call(t)).next,0===e){if(Object(r)!==r)return;u=!1}else for(;!(u=(n=a.call(r)).done)&&(c.push(n.value),c.length!==e);u=!0);}catch(t){l=!0,o=t}finally{try{if(!u&&null!=r.return&&(i=r.return(),Object(i)!==i))return}finally{if(l)throw o}}return c}}(t,e)||function(t,e){if(!t)return;if("string"==typeof t)return f(t,e);var r=Object.prototype.toString.call(t).slice(8,-1);"Object"===r&&t.constructor&&(r=t.constructor.name);if("Map"===r||"Set"===r)return Array.from(t);if("Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r))return f(t,e)}(t,e)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function f(t,e){(null==e||e>t.length)&&(e=t.length);for(var r=0,n=new Array(e);r<e;r++)n[r]=t[r];return n}(n="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.enterModule:void 0)&&n(t);var h="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.default.signature:function(t){return t},d=function(t){var e=t.setTriggerVisible,r=t.setType,n=t.repositoryAllList,c=t.repositoryId,f=s(Object(o.useState)([]),2),h=f[0],d=f[1];Object(o.useEffect)((function(){if(n){var t=n.filter((function(t){return t.id!==c})),e=n.filter((function(t){return t.id===c}));t.unshift(e[0]);var r=t.length>5?t.slice(0,5):t;d(r)}}),[c]);var p=function(){var n,o=(n=u().mark((function n(o){return u().wrap((function(n){for(;;)switch(n.prev=n.next){case 0:t.history.push("/repository/".concat(o.id,"/libraryList")),e(!1),r("2");case 3:case"end":return n.stop()}}),n)})),function(){var t=this,e=arguments;return new Promise((function(r,o){var a=n.apply(t,e);function i(t){l(a,r,o,i,c,"next",t)}function c(t){l(a,r,o,i,c,"throw",t)}i(void 0)}))});return function(t){return o.apply(this,arguments)}}();return a.a.createElement("div",{className:"menu-toggle"},a.a.createElement("div",{className:"menu-toggle-title"},"切换仓库"),a.a.createElement("div",{className:"menu-toggle-group"},h.map((function(t){return a.a.createElement("div",{className:"menu-toggle-item ".concat(c&&c===t.id?"menu-toggle-active":""),key:t.id,onClick:function(){return p(t)}},a.a.createElement(i.a,{text:t.name,colors:t.color}),a.a.createElement("div",{className:"right-page-nav-text"},t.name))}))),n.length>5&&a.a.createElement("div",{className:"menu-toggle-tail cursor",onClick:function(){t.history.push("/repository")}},"查看更多"))};h(d,"useState{[repositoryList,setRepositoryList]([])}\nuseEffect{}");var p,y,v=d;e.a=v,(p="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.default:void 0)&&(p.register(d,"ChangeRepository","/Users/limingliang/xcode-xpack/web/thoughtware-hadess-ui/src/repository/navigator/ChangeRepository.js"),p.register(v,"default","/Users/limingliang/xcode-xpack/web/thoughtware-hadess-ui/src/repository/navigator/ChangeRepository.js")),(y="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.leaveModule:void 0)&&y(t)}).call(this,r(51)(t))},736:function(t,e,r){}}]);