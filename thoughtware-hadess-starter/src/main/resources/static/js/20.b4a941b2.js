(window.webpackJsonp=window.webpackJsonp||[]).push([[20],{1035:function(e,t,r){},1036:function(e,t,r){},1037:function(e,t,r){"use strict";(function(e){r(251);var n,o=r(176),a=(r(178),r(58)),i=(r(253),r(109)),c=r(0),l=r.n(c),u=r(398),s=r(392);r(1038);function f(e){return(f="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function d(){/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */d=function(){return e};var e={},t=Object.prototype,r=t.hasOwnProperty,n=Object.defineProperty||function(e,t,r){e[t]=r.value},o="function"==typeof Symbol?Symbol:{},a=o.iterator||"@@iterator",i=o.asyncIterator||"@@asyncIterator",c=o.toStringTag||"@@toStringTag";function l(e,t,r){return Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}),e[t]}try{l({},"")}catch(e){l=function(e,t,r){return e[t]=r}}function u(e,t,r,o){var a=t&&t.prototype instanceof y?t:y,i=Object.create(a.prototype),c=new k(o||[]);return n(i,"_invoke",{value:O(e,r,c)}),i}function s(e,t,r){try{return{type:"normal",arg:e.call(t,r)}}catch(e){return{type:"throw",arg:e}}}e.wrap=u;var p={};function y(){}function h(){}function m(){}var v={};l(v,a,(function(){return this}));var b=Object.getPrototypeOf,g=b&&b(b(N([])));g&&g!==t&&r.call(g,a)&&(v=g);var w=m.prototype=y.prototype=Object.create(v);function E(e){["next","throw","return"].forEach((function(t){l(e,t,(function(e){return this._invoke(t,e)}))}))}function x(e,t){var o;n(this,"_invoke",{value:function(n,a){function i(){return new t((function(o,i){!function n(o,a,i,c){var l=s(e[o],e,a);if("throw"!==l.type){var u=l.arg,d=u.value;return d&&"object"==f(d)&&r.call(d,"__await")?t.resolve(d.__await).then((function(e){n("next",e,i,c)}),(function(e){n("throw",e,i,c)})):t.resolve(d).then((function(e){u.value=e,i(u)}),(function(e){return n("throw",e,i,c)}))}c(l.arg)}(n,a,o,i)}))}return o=o?o.then(i,i):i()}})}function O(e,t,r){var n="suspendedStart";return function(o,a){if("executing"===n)throw new Error("Generator is already running");if("completed"===n){if("throw"===o)throw a;return I()}for(r.method=o,r.arg=a;;){var i=r.delegate;if(i){var c=L(i,r);if(c){if(c===p)continue;return c}}if("next"===r.method)r.sent=r._sent=r.arg;else if("throw"===r.method){if("suspendedStart"===n)throw n="completed",r.arg;r.dispatchException(r.arg)}else"return"===r.method&&r.abrupt("return",r.arg);n="executing";var l=s(e,t,r);if("normal"===l.type){if(n=r.done?"completed":"suspendedYield",l.arg===p)continue;return{value:l.arg,done:r.done}}"throw"===l.type&&(n="completed",r.method="throw",r.arg=l.arg)}}}function L(e,t){var r=t.method,n=e.iterator[r];if(void 0===n)return t.delegate=null,"throw"===r&&e.iterator.return&&(t.method="return",t.arg=void 0,L(e,t),"throw"===t.method)||"return"!==r&&(t.method="throw",t.arg=new TypeError("The iterator does not provide a '"+r+"' method")),p;var o=s(n,e.iterator,t.arg);if("throw"===o.type)return t.method="throw",t.arg=o.arg,t.delegate=null,p;var a=o.arg;return a?a.done?(t[e.resultName]=a.value,t.next=e.nextLoc,"return"!==t.method&&(t.method="next",t.arg=void 0),t.delegate=null,p):a:(t.method="throw",t.arg=new TypeError("iterator result is not an object"),t.delegate=null,p)}function j(e){var t={tryLoc:e[0]};1 in e&&(t.catchLoc=e[1]),2 in e&&(t.finallyLoc=e[2],t.afterLoc=e[3]),this.tryEntries.push(t)}function S(e){var t=e.completion||{};t.type="normal",delete t.arg,e.completion=t}function k(e){this.tryEntries=[{tryLoc:"root"}],e.forEach(j,this),this.reset(!0)}function N(e){if(e){var t=e[a];if(t)return t.call(e);if("function"==typeof e.next)return e;if(!isNaN(e.length)){var n=-1,o=function t(){for(;++n<e.length;)if(r.call(e,n))return t.value=e[n],t.done=!1,t;return t.value=void 0,t.done=!0,t};return o.next=o}}return{next:I}}function I(){return{value:void 0,done:!0}}return h.prototype=m,n(w,"constructor",{value:m,configurable:!0}),n(m,"constructor",{value:h,configurable:!0}),h.displayName=l(m,c,"GeneratorFunction"),e.isGeneratorFunction=function(e){var t="function"==typeof e&&e.constructor;return!!t&&(t===h||"GeneratorFunction"===(t.displayName||t.name))},e.mark=function(e){return Object.setPrototypeOf?Object.setPrototypeOf(e,m):(e.__proto__=m,l(e,c,"GeneratorFunction")),e.prototype=Object.create(w),e},e.awrap=function(e){return{__await:e}},E(x.prototype),l(x.prototype,i,(function(){return this})),e.AsyncIterator=x,e.async=function(t,r,n,o,a){void 0===a&&(a=Promise);var i=new x(u(t,r,n,o),a);return e.isGeneratorFunction(r)?i:i.next().then((function(e){return e.done?e.value:i.next()}))},E(w),l(w,c,"Generator"),l(w,a,(function(){return this})),l(w,"toString",(function(){return"[object Generator]"})),e.keys=function(e){var t=Object(e),r=[];for(var n in t)r.push(n);return r.reverse(),function e(){for(;r.length;){var n=r.pop();if(n in t)return e.value=n,e.done=!1,e}return e.done=!0,e}},e.values=N,k.prototype={constructor:k,reset:function(e){if(this.prev=0,this.next=0,this.sent=this._sent=void 0,this.done=!1,this.delegate=null,this.method="next",this.arg=void 0,this.tryEntries.forEach(S),!e)for(var t in this)"t"===t.charAt(0)&&r.call(this,t)&&!isNaN(+t.slice(1))&&(this[t]=void 0)},stop:function(){this.done=!0;var e=this.tryEntries[0].completion;if("throw"===e.type)throw e.arg;return this.rval},dispatchException:function(e){if(this.done)throw e;var t=this;function n(r,n){return i.type="throw",i.arg=e,t.next=r,n&&(t.method="next",t.arg=void 0),!!n}for(var o=this.tryEntries.length-1;o>=0;--o){var a=this.tryEntries[o],i=a.completion;if("root"===a.tryLoc)return n("end");if(a.tryLoc<=this.prev){var c=r.call(a,"catchLoc"),l=r.call(a,"finallyLoc");if(c&&l){if(this.prev<a.catchLoc)return n(a.catchLoc,!0);if(this.prev<a.finallyLoc)return n(a.finallyLoc)}else if(c){if(this.prev<a.catchLoc)return n(a.catchLoc,!0)}else{if(!l)throw new Error("try statement without catch or finally");if(this.prev<a.finallyLoc)return n(a.finallyLoc)}}}},abrupt:function(e,t){for(var n=this.tryEntries.length-1;n>=0;--n){var o=this.tryEntries[n];if(o.tryLoc<=this.prev&&r.call(o,"finallyLoc")&&this.prev<o.finallyLoc){var a=o;break}}a&&("break"===e||"continue"===e)&&a.tryLoc<=t&&t<=a.finallyLoc&&(a=null);var i=a?a.completion:{};return i.type=e,i.arg=t,a?(this.method="next",this.next=a.finallyLoc,p):this.complete(i)},complete:function(e,t){if("throw"===e.type)throw e.arg;return"break"===e.type||"continue"===e.type?this.next=e.arg:"return"===e.type?(this.rval=this.arg=e.arg,this.method="return",this.next="end"):"normal"===e.type&&t&&(this.next=t),p},finish:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.finallyLoc===e)return this.complete(r.completion,r.afterLoc),S(r),p}},catch:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.tryLoc===e){var n=r.completion;if("throw"===n.type){var o=n.arg;S(r)}return o}}throw new Error("illegal catch attempt")},delegateYield:function(e,t,r){return this.delegate={iterator:N(e),resultName:t,nextLoc:r},"next"===this.method&&(this.arg=void 0),p}},e}function p(e,t,r,n,o,a,i){try{var c=e[a](i),l=c.value}catch(e){return void r(e)}c.done?t(l):Promise.resolve(l).then(n,o)}function y(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,o,a,i,c=[],l=!0,u=!1;try{if(a=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;l=!1}else for(;!(l=(n=a.call(r)).done)&&(c.push(n.value),c.length!==t);l=!0);}catch(e){u=!0,o=e}finally{try{if(!l&&null!=r.return&&(i=r.return(),Object(i)!==i))return}finally{if(u)throw o}}return c}}(e,t)||function(e,t){if(!e)return;if("string"==typeof e)return h(e,t);var r=Object.prototype.toString.call(e).slice(8,-1);"Object"===r&&e.constructor&&(r=e.constructor.name);if("Map"===r||"Set"===r)return Array.from(e);if("Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r))return h(e,t)}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function h(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=new Array(t);r<t;r++)n[r]=e[r];return n}(n="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.enterModule:void 0)&&n(e);var m="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.default.signature:function(e){return e},v=function(e){var t=y(i.default.useForm(),1)[0],r=e.deleteVisible,n=e.setDeleteVisible,f=e.repository,h=e.deleteRepository,m=y(Object(c.useState)(!1),2),v=m[0],b=m[1],g=function(){n(!1)},w=l.a.createElement(l.a.Fragment,null,l.a.createElement(s.a,{onClick:g,title:"取消",isMar:!0}),v?l.a.createElement(s.a,{title:"删除中",type:"dangerous"}):l.a.createElement(s.a,{onClick:function(){t.validateFields().then(function(){var t,r=(t=d().mark((function t(r){return d().wrap((function(t){for(;;)switch(t.prev=t.next){case 0:b(!0),h(f.id).then((function(t){b(!1),0===t.code?(n(!1),e.history.push("/repository"),a.default.info("删除成功")):a.default.error(t.msg)}));case 2:case"end":return t.stop()}}),t)})),function(){var e=this,r=arguments;return new Promise((function(n,o){var a=t.apply(e,r);function i(e){p(a,n,o,i,c,"next",e)}function c(e){p(a,n,o,i,c,"throw",e)}i(void 0)}))});return function(e){return r.apply(this,arguments)}}())},title:"确定",type:"dangerous"}));return l.a.createElement(u.a,{open:r,onCancel:g,closable:!1,footer:w,destroyOnClose:!0,width:500,title:"删除制品库"},l.a.createElement("div",{className:"repository-delete"},l.a.createElement("div",{className:"desc-border"},l.a.createElement("div",null,"您正在删除仓库",l.a.createElement("span",{className:"desc-text"},null==f?void 0:f.name)),"删除该制品库后 里面所有制品将会清理，客户端配置对应制品的路径将失效"),l.a.createElement("div",{className:"data-table"},l.a.createElement(i.default,{form:t,layout:"vertical",autoComplete:"off"},l.a.createElement(i.default.Item,{label:"制品库名称",name:"repositoryName",rules:[{required:!0,message:"请输入制品库名称"},function(e){e.getFieldValue;return{validator:function(e,t,r){if(t)return t===f.name?Promise.resolve():Promise.reject("请输入".concat(f.name));r()}}}]},l.a.createElement(o.default,{placeholder:"计划名称"}))))))};m(v,"useForm{[form]}\nuseState{[deleteState,setDeleteState](false)}");var b,g,w=v;t.a=w,(b="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.default:void 0)&&(b.register(v,"RepositoryDelete","/Users/limingliang/xcode-xpack/web/thoughtware-hadess-ui/src/repository/setting/basicInfo/RepositoryDelete.js"),b.register(w,"default","/Users/limingliang/xcode-xpack/web/thoughtware-hadess-ui/src/repository/setting/basicInfo/RepositoryDelete.js")),(g="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.leaveModule:void 0)&&g(e)}).call(this,r(51)(e))},1038:function(e,t,r){},359:function(e,t,r){"use strict";r.r(t),function(e){r(252);var n,o=r(177),a=(r(725),r(726)),i=(r(1035),r(692)),c=r.n(i),l=(r(390),r(389)),u=(r(255),r(180)),s=(r(178),r(58)),f=(r(253),r(109)),d=(r(251),r(176)),p=r(0),y=r.n(p),h=(r(1036),r(393)),m=r(261),v=r(426),b=r(1184),g=r(1185),w=r(217),E=r(113),x=r(90),O=r(392),L=r(460),j=r(461),S=r(689),k=r(1037);function N(e){return(N="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function I(){return(I=Object.assign?Object.assign.bind():function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var n in r)Object.prototype.hasOwnProperty.call(r,n)&&(e[n]=r[n])}return e}).apply(this,arguments)}function P(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function G(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?P(Object(r),!0).forEach((function(t){C(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):P(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function C(e,t,r){return(t=function(e){var t=function(e,t){if("object"!==N(e)||null===e)return e;var r=e[Symbol.toPrimitive];if(void 0!==r){var n=r.call(e,t||"default");if("object"!==N(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(e,"string");return"symbol"===N(t)?t:String(t)}(t))in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function _(){/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */_=function(){return e};var e={},t=Object.prototype,r=t.hasOwnProperty,n=Object.defineProperty||function(e,t,r){e[t]=r.value},o="function"==typeof Symbol?Symbol:{},a=o.iterator||"@@iterator",i=o.asyncIterator||"@@asyncIterator",c=o.toStringTag||"@@toStringTag";function l(e,t,r){return Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}),e[t]}try{l({},"")}catch(e){l=function(e,t,r){return e[t]=r}}function u(e,t,r,o){var a=t&&t.prototype instanceof d?t:d,i=Object.create(a.prototype),c=new j(o||[]);return n(i,"_invoke",{value:E(e,r,c)}),i}function s(e,t,r){try{return{type:"normal",arg:e.call(t,r)}}catch(e){return{type:"throw",arg:e}}}e.wrap=u;var f={};function d(){}function p(){}function y(){}var h={};l(h,a,(function(){return this}));var m=Object.getPrototypeOf,v=m&&m(m(S([])));v&&v!==t&&r.call(v,a)&&(h=v);var b=y.prototype=d.prototype=Object.create(h);function g(e){["next","throw","return"].forEach((function(t){l(e,t,(function(e){return this._invoke(t,e)}))}))}function w(e,t){var o;n(this,"_invoke",{value:function(n,a){function i(){return new t((function(o,i){!function n(o,a,i,c){var l=s(e[o],e,a);if("throw"!==l.type){var u=l.arg,f=u.value;return f&&"object"==N(f)&&r.call(f,"__await")?t.resolve(f.__await).then((function(e){n("next",e,i,c)}),(function(e){n("throw",e,i,c)})):t.resolve(f).then((function(e){u.value=e,i(u)}),(function(e){return n("throw",e,i,c)}))}c(l.arg)}(n,a,o,i)}))}return o=o?o.then(i,i):i()}})}function E(e,t,r){var n="suspendedStart";return function(o,a){if("executing"===n)throw new Error("Generator is already running");if("completed"===n){if("throw"===o)throw a;return k()}for(r.method=o,r.arg=a;;){var i=r.delegate;if(i){var c=x(i,r);if(c){if(c===f)continue;return c}}if("next"===r.method)r.sent=r._sent=r.arg;else if("throw"===r.method){if("suspendedStart"===n)throw n="completed",r.arg;r.dispatchException(r.arg)}else"return"===r.method&&r.abrupt("return",r.arg);n="executing";var l=s(e,t,r);if("normal"===l.type){if(n=r.done?"completed":"suspendedYield",l.arg===f)continue;return{value:l.arg,done:r.done}}"throw"===l.type&&(n="completed",r.method="throw",r.arg=l.arg)}}}function x(e,t){var r=t.method,n=e.iterator[r];if(void 0===n)return t.delegate=null,"throw"===r&&e.iterator.return&&(t.method="return",t.arg=void 0,x(e,t),"throw"===t.method)||"return"!==r&&(t.method="throw",t.arg=new TypeError("The iterator does not provide a '"+r+"' method")),f;var o=s(n,e.iterator,t.arg);if("throw"===o.type)return t.method="throw",t.arg=o.arg,t.delegate=null,f;var a=o.arg;return a?a.done?(t[e.resultName]=a.value,t.next=e.nextLoc,"return"!==t.method&&(t.method="next",t.arg=void 0),t.delegate=null,f):a:(t.method="throw",t.arg=new TypeError("iterator result is not an object"),t.delegate=null,f)}function O(e){var t={tryLoc:e[0]};1 in e&&(t.catchLoc=e[1]),2 in e&&(t.finallyLoc=e[2],t.afterLoc=e[3]),this.tryEntries.push(t)}function L(e){var t=e.completion||{};t.type="normal",delete t.arg,e.completion=t}function j(e){this.tryEntries=[{tryLoc:"root"}],e.forEach(O,this),this.reset(!0)}function S(e){if(e){var t=e[a];if(t)return t.call(e);if("function"==typeof e.next)return e;if(!isNaN(e.length)){var n=-1,o=function t(){for(;++n<e.length;)if(r.call(e,n))return t.value=e[n],t.done=!1,t;return t.value=void 0,t.done=!0,t};return o.next=o}}return{next:k}}function k(){return{value:void 0,done:!0}}return p.prototype=y,n(b,"constructor",{value:y,configurable:!0}),n(y,"constructor",{value:p,configurable:!0}),p.displayName=l(y,c,"GeneratorFunction"),e.isGeneratorFunction=function(e){var t="function"==typeof e&&e.constructor;return!!t&&(t===p||"GeneratorFunction"===(t.displayName||t.name))},e.mark=function(e){return Object.setPrototypeOf?Object.setPrototypeOf(e,y):(e.__proto__=y,l(e,c,"GeneratorFunction")),e.prototype=Object.create(b),e},e.awrap=function(e){return{__await:e}},g(w.prototype),l(w.prototype,i,(function(){return this})),e.AsyncIterator=w,e.async=function(t,r,n,o,a){void 0===a&&(a=Promise);var i=new w(u(t,r,n,o),a);return e.isGeneratorFunction(r)?i:i.next().then((function(e){return e.done?e.value:i.next()}))},g(b),l(b,c,"Generator"),l(b,a,(function(){return this})),l(b,"toString",(function(){return"[object Generator]"})),e.keys=function(e){var t=Object(e),r=[];for(var n in t)r.push(n);return r.reverse(),function e(){for(;r.length;){var n=r.pop();if(n in t)return e.value=n,e.done=!1,e}return e.done=!0,e}},e.values=S,j.prototype={constructor:j,reset:function(e){if(this.prev=0,this.next=0,this.sent=this._sent=void 0,this.done=!1,this.delegate=null,this.method="next",this.arg=void 0,this.tryEntries.forEach(L),!e)for(var t in this)"t"===t.charAt(0)&&r.call(this,t)&&!isNaN(+t.slice(1))&&(this[t]=void 0)},stop:function(){this.done=!0;var e=this.tryEntries[0].completion;if("throw"===e.type)throw e.arg;return this.rval},dispatchException:function(e){if(this.done)throw e;var t=this;function n(r,n){return i.type="throw",i.arg=e,t.next=r,n&&(t.method="next",t.arg=void 0),!!n}for(var o=this.tryEntries.length-1;o>=0;--o){var a=this.tryEntries[o],i=a.completion;if("root"===a.tryLoc)return n("end");if(a.tryLoc<=this.prev){var c=r.call(a,"catchLoc"),l=r.call(a,"finallyLoc");if(c&&l){if(this.prev<a.catchLoc)return n(a.catchLoc,!0);if(this.prev<a.finallyLoc)return n(a.finallyLoc)}else if(c){if(this.prev<a.catchLoc)return n(a.catchLoc,!0)}else{if(!l)throw new Error("try statement without catch or finally");if(this.prev<a.finallyLoc)return n(a.finallyLoc)}}}},abrupt:function(e,t){for(var n=this.tryEntries.length-1;n>=0;--n){var o=this.tryEntries[n];if(o.tryLoc<=this.prev&&r.call(o,"finallyLoc")&&this.prev<o.finallyLoc){var a=o;break}}a&&("break"===e||"continue"===e)&&a.tryLoc<=t&&t<=a.finallyLoc&&(a=null);var i=a?a.completion:{};return i.type=e,i.arg=t,a?(this.method="next",this.next=a.finallyLoc,f):this.complete(i)},complete:function(e,t){if("throw"===e.type)throw e.arg;return"break"===e.type||"continue"===e.type?this.next=e.arg:"return"===e.type?(this.rval=this.arg=e.arg,this.method="return",this.next="end"):"normal"===e.type&&t&&(this.next=t),f},finish:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.finallyLoc===e)return this.complete(r.completion,r.afterLoc),L(r),f}},catch:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.tryLoc===e){var n=r.completion;if("throw"===n.type){var o=n.arg;L(r)}return o}}throw new Error("illegal catch attempt")},delegateYield:function(e,t,r){return this.delegate={iterator:S(e),resultName:t,nextLoc:r},"next"===this.method&&(this.arg=void 0),f}},e}function R(e,t,r,n,o,a,i){try{var c=e[a](i),l=c.value}catch(e){return void r(e)}c.done?t(l):Promise.resolve(l).then(n,o)}function T(e){return function(){var t=this,r=arguments;return new Promise((function(n,o){var a=e.apply(t,r);function i(e){R(a,n,o,i,c,"next",e)}function c(e){R(a,n,o,i,c,"throw",e)}i(void 0)}))}}function H(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,o,a,i,c=[],l=!0,u=!1;try{if(a=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;l=!1}else for(;!(l=(n=a.call(r)).done)&&(c.push(n.value),c.length!==t);l=!0);}catch(e){u=!0,o=e}finally{try{if(!l&&null!=r.return&&(i=r.return(),Object(i)!==i))return}finally{if(u)throw o}}return c}}(e,t)||function(e,t){if(!e)return;if("string"==typeof e)return A(e,t);var r=Object.prototype.toString.call(e).slice(8,-1);"Object"===r&&e.constructor&&(r=e.constructor.name);if("Map"===r||"Set"===r)return Array.from(e);if("Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r))return A(e,t)}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function A(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=new Array(t);r<t;r++)n[r]=e[r];return n}(n="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.enterModule:void 0)&&n(e);var F="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.default.signature:function(e){return e},M=d.default.TextArea,U=function(e){var t=H(f.default.useForm(),1)[0],r=e.match.params,n=e.repositoryStore,i=n.compileRepositoryGroup,x=n.findRepository,N=n.updateRepository,P=n.deleteRepository,C=n.findRepositoryByGroup,R=n.findUnRelevanceRepository,A=n.updateRepositoryMaven,F=n.findRepositoryMavenByRpyId,U=j.a.findRemoteProxyList,D=j.a.findProxyListByRpyId,V=H(Object(p.useState)(null),2),B=V[0],Y=V[1],z=H(Object(p.useState)([]),2),$=z[0],q=z[1],J=n.repositoryData,K=H(Object(p.useState)(),2),W=K[0],Q=K[1],X=H(Object(p.useState)(null),2),Z=X[0],ee=X[1],te=H(Object(p.useState)([]),2),re=te[0],ne=te[1],oe=H(Object(p.useState)(""),2),ae=oe[0],ie=oe[1],ce=H(Object(p.useState)([]),2),le=ce[0],ue=ce[1],se=H(Object(p.useState)(!1),2),fe=se[0],de=se[1],pe=H(Object(p.useState)(),2),ye=pe[0],he=pe[1],me=H(Object(p.useState)(!1),2),ve=me[0],be=me[1],ge=H(Object(p.useState)([]),2),we=ge[0],Ee=ge[1],xe=H(Object(p.useState)([]),2),Oe=xe[0],Le=xe[1],je=H(Object(p.useState)(!1),2),Se=je[0],ke=je[1],Ne=H(Object(p.useState)(!1),2),Ie=Ne[0],Pe=Ne[1];Object(p.useEffect)(T(_().mark((function e(){return _().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,Ge();case 2:F({repositoryId:r.id}).then((function(e){0===e.code&&e.data.length>0&&(0===e.data[0].coverState?de(!1):de(!0),he(e.data[0]))})),D(r.id).then((function(e){0===e.code&&Ee(e.data)}));case 4:case"end":return e.stop()}}),e)}))),[r.id]);var Ge=function(){var n=T(_().mark((function n(){var o,a,i;return _().wrap((function(n){for(;;)switch(n.prev=n.next){case 0:return n.next=2,x(r.id);case 2:0===(o=n.sent).code&&(o.data?(t.setFieldsValue({type:o.agencyUrl,name:o.data.name,description:o.data.description}),Y(o.data),ie(null===(a=o.data)||void 0===a?void 0:a.name),R(o.data.type,r.id).then((function(e){0===e.code&&q(e.data)})),C(r.id).then((function(e){0===e.code&&ne(e.data)})),i=o.data.type.toLowerCase(),U({agencyType:i}).then((function(e){0===e.code&&Le(e.data)}))):e.history.push("/repository"));case 4:case"end":return n.stop()}}),n)})));return function(){return n.apply(this,arguments)}}(),Ce=function(){var e=T(_().mark((function e(t){return _().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return ke(!0),e.next=3,N(G(G({},t),{},{id:B.id,name:ae,repositoryUrl:ae,repositoryType:B.repositoryType,category:B.category,storage:{id:t.storage},type:null==B?void 0:B.type,proxyDataList:we}));case 3:if(0!==e.sent.code){e.next=12;break}if("group"!==(null==B?void 0:B.repositoryType)){e.next=9;break}i({repositoryGroupId:B.id,repositoryList:re}).then((function(e){0===e.code&&Ge()})),e.next=11;break;case 9:return e.next=11,Ge();case 11:s.default.success("修改成功",1);case 12:ke(!1);case 13:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}(),_e=function(e){return le.some((function(t){return t===e}))},Re=function(e){_e(e)?ue(le.filter((function(t){return t!==e}))):ue(le.concat(e))},Te=function(){var e=T(_().mark((function e(t){return _().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:Q(t);case 1:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}(),He=function(){var e=T(_().mark((function e(t){var r;return _().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:r=t.target.value,ie(r);case 2:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}(),Ae=function(){var e=T(_().mark((function e(){return _().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:B&&(q($.filter((function(e){return(null==W?void 0:W.id)!==e.id}))),W&&ne(re.concat(W)),Q(null));case 1:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}(),Fe=function(){var e=T(_().mark((function e(){return _().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:Z&&(q($.concat(Z)),ne(re.filter((function(e){return(null==Z?void 0:Z.id)!==e.id}))),ee(null));case 1:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}(),Me=function(){var e=T(_().mark((function e(t){return _().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:ee(t);case 1:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}(),Ue=[{title:"名称",dataIndex:"agencyName",key:"agencyName",width:"50%"},{title:"地址",dataIndex:"agencyUrl",key:"agencyUrl",width:"50%"},{title:"操作",dataIndex:"action",key:"action",width:"5%",ellipsis:!0,render:function(e,t){return we.length>1?y.a.createElement("span",null,y.a.createElement(m.default,{onClick:function(){return De(t)}})):y.a.createElement("span",{className:"user-table-ban"},y.a.createElement(m.default,null))}}],De=function(e){Ee(we.filter((function(t){return t.id!==e.id})))},Ve=[{key:1,title:"仓库信息",desc:"更新仓库信息",icon:y.a.createElement(v.default,null),enCode:"house_update",content:y.a.createElement("div",{className:"bottom-rename"},y.a.createElement(f.default,{form:t,autoComplete:"off",layout:"vertical",name:"name",initialValues:{name:null==J?void 0:J.name,remarks:null==J?void 0:J.remarks}},y.a.createElement(f.default.Item,{label:"制品仓库",name:"type"},y.a.createElement("div",{className:"repository-type-table"},y.a.createElement(L.a,{type:null==B?void 0:B.type,width:35,height:35}),y.a.createElement("div",{className:"type-text"},null==J?void 0:J.type))),y.a.createElement(f.default.Item,{label:"仓库名称",name:"name",rules:[{required:!0,message:"仓库名称必填"}]},y.a.createElement(d.default,{addonBefore:"".concat(null==B?void 0:B.prefixPath),style:{width:"70%"},onChange:He})),"remote"===(null==B?void 0:B.repositoryType)&&y.a.createElement("div",{className:"proxy-style"},y.a.createElement("div",{className:"add-table-proxy"},y.a.createElement("div",{className:"proxy-head-nav"},y.a.createElement("div",{className:"proxy-head-text"},"代理地址")),y.a.createElement(u.default,{getPopupContainer:function(e){return e.parentElement},overlay:y.a.createElement(S.a,{visible:ve,setVisible:be,proxyPathList:we,setProxyPathList:Ee,remoteProxyList:Oe,type:null==B?void 0:B.type}),visible:ve,onVisibleChange:function(e){be(e)},trigger:["click"],placement:"topRight",overlayStyle:{width:540}},y.a.createElement("div",{className:"add-proxy-text"},"+添加代理地址"))),y.a.createElement(l.default,{rowKey:function(e){return e.id},columns:Ue,dataSource:we,pagination:!1,showHeader:!1})),"local"===(null==B?void 0:B.repositoryType)&&"maven"===B.type&&y.a.createElement(f.default.Item,{label:"版本控制",name:"version"},y.a.createElement(d.default,{defaultValue:null==B?void 0:B.versionType,style:{width:"70%"},disabled:!0})),"group"===(null==B?void 0:B.repositoryType)&&y.a.createElement(f.default.Item,{label:"组合选择",name:"name"},y.a.createElement("div",{className:"repository-group"},y.a.createElement("div",{className:"group-bord"},$.length?null==$?void 0:$.map((function(e){return y.a.createElement("div",{className:"".concat((null==W?void 0:W.id)===e.id&&" opt-color"," cut-repository click-cursor"),onClick:function(){return Te(e)}},y.a.createElement("div",{className:"opt-text "},e.name+" ("+e.repositoryType+")"))})):null),y.a.createElement("div",null,y.a.createElement("div",null,y.a.createElement(b.a,{className:"icon-style",onClick:Ae})),y.a.createElement(g.a,{className:"icon-style ",onClick:Fe})),y.a.createElement("div",{className:"group-bord"},null==re?void 0:re.map((function(e){return y.a.createElement("div",{className:"".concat((null==Z?void 0:Z.id)===e.id&&" opt-color","  cut-repository click-cursor"),onClick:function(){return Me(e)}},y.a.createElement("div",{className:"opt-text "},e.name+" ("+e.repositoryType+")"))}))))),y.a.createElement(f.default.Item,{label:"描述",name:"description"},y.a.createElement(M,{rows:4,style:{width:"70%"}}))),y.a.createElement("div",{className:"bottom-rename-btn"},y.a.createElement(O.a,{title:"取消",isMar:!0,onClick:function(){return Re(1)}}),Se?y.a.createElement(O.a,{title:"加载中",type:"primary"}):y.a.createElement(c.a,{code:"xpack_update",domainId:B&&B.id},y.a.createElement(O.a,{type:"primary",title:"确定",onClick:function(){t.validateFields().then((function(e){Ce(e)}))}}))))},{key:2,title:"仓库策略",desc:"仓库策略",icon:y.a.createElement(m.default,null),enCode:"house_delete",content:y.a.createElement("div",null,y.a.createElement("div",null,"制品是否允许覆盖"),y.a.createElement("div",{className:"strategy-style"},y.a.createElement("div",{className:"strategy-text-desc"},"开启后，制品允许覆盖"),y.a.createElement(a.a,{checkedChildren:"允许",unCheckedChildren:"不允许",defaultChecked:fe,onChange:function(e){var t=e?1:0;A(G(G({},ye),{},{coverState:t})),de(e)}})))},{key:3,title:"仓库删除",desc:"删除仓库",icon:y.a.createElement(m.default,null),enCode:"house_delete",content:y.a.createElement("div",{className:"bottom-delete"},y.a.createElement("div",{style:{color:"#ff0000",paddingBottom:5,fontSize:13}},"此操作无法恢复！请慎重操作！"),y.a.createElement(O.a,{title:"取消",isMar:!0,onClick:function(){return Re(3)}}),y.a.createElement(c.a,{code:"xpack_delete",domainId:B&&B.id},y.a.createElement(O.a,{onClick:function(){return Pe(!0)},type:"dangerous",title:"删除"})))}];return y.a.createElement("div",{className:"basicInfo hadess-data-width"},y.a.createElement(o.default,{sm:{span:"24"},md:{span:"24"},lg:{span:"24"},xl:{span:"18",offset:"3"},xxl:{span:"18",offset:"3"}},y.a.createElement("div",{className:"basicInfo-up"},y.a.createElement(h.a,{firstItem:"仓库信息"})),y.a.createElement("div",{className:"basicInfo-li"},Ve.map((function(e,t){return function(e,t){return"Release"!==(null==B?void 0:B.versionType)&&2==e.key?null:y.a.createElement("div",{key:e.key,className:"".concat(t>0?" border-top":"")},y.a.createElement("div",{className:"basicInfo-li-top ".concat(_e(e.key)?"basicInfo-li-select":""),onClick:function(){return Re(e.key)}},y.a.createElement("div",{className:"basicInfo-li-icon"},e.icon),y.a.createElement("div",{className:"basicInfo-li-center"},y.a.createElement("div",{className:"basicInfo-li-title"},e.title),!_e(e.key)&&y.a.createElement("div",{className:"basicInfo-li-desc"},e.desc)),y.a.createElement("div",{className:"basicInfo-li-down"},_e(e.key)?y.a.createElement(w.a,null):y.a.createElement(E.default,null))),y.a.createElement("div",{className:"".concat(_e(e.key)?"basicInfo-li-bottom":"basicInfo-li-none")},_e(e.key)&&e.content))}(e,t)}))),y.a.createElement(k.a,I({},e,{deleteVisible:Ie,repository:B,setDeleteVisible:Pe,deleteRepository:P}))))};F(U,"useForm{[form]}\nuseState{[repository,setRepository](null)}\nuseState{[repositoryList,setRepositoryList]([])}\nuseState{[underRepository,setUnderRepository]}\nuseState{[choiceRepository,setChoiceRepository](null)}\nuseState{[choiceRepositoryList,setChoiceRepositoryList]([])}\nuseState{[repositoryName,setRepositoryName]('')}\nuseState{[expandedTree,setExpandedTree]([])}\nuseState{[strategyState,setStrategyState](false)}\nuseState{[repositoryMaven,setRepositoryMaven]}\nuseState{[proxyVisible,setProxyVisible](false)}\nuseState{[proxyPathList,setProxyPathList]([])}\nuseState{[remoteProxyList,setRemoteProxyList]([])}\nuseState{[updateState,setUpdateState](false)}\nuseState{[deleteVisible,setDeleteVisible](false)}\nuseEffect{}");var D,V,B=Object(x.inject)("repositoryStore")(Object(x.observer)(U));t.default=B,(D="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.default:void 0)&&(D.register(M,"TextArea","/Users/limingliang/xcode-xpack/web/thoughtware-hadess-ui/src/repository/setting/basicInfo/RepositoryBasicInfo.js"),D.register(U,"RepositoryBasicInfo","/Users/limingliang/xcode-xpack/web/thoughtware-hadess-ui/src/repository/setting/basicInfo/RepositoryBasicInfo.js"),D.register(B,"default","/Users/limingliang/xcode-xpack/web/thoughtware-hadess-ui/src/repository/setting/basicInfo/RepositoryBasicInfo.js")),(V="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.leaveModule:void 0)&&V(e)}.call(this,r(51)(e))},398:function(e,t,r){"use strict";(function(e){r(254);var n,o=r(179),a=r(0),i=r.n(a),c=r(406),l=r(392),u=(r(409),["title","children","width","footer"]);function s(){return(s=Object.assign?Object.assign.bind():function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var n in r)Object.prototype.hasOwnProperty.call(r,n)&&(e[n]=r[n])}return e}).apply(this,arguments)}function f(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,o,a,i,c=[],l=!0,u=!1;try{if(a=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;l=!1}else for(;!(l=(n=a.call(r)).done)&&(c.push(n.value),c.length!==t);l=!0);}catch(e){u=!0,o=e}finally{try{if(!l&&null!=r.return&&(i=r.return(),Object(i)!==i))return}finally{if(u)throw o}}return c}}(e,t)||function(e,t){if(!e)return;if("string"==typeof e)return d(e,t);var r=Object.prototype.toString.call(e).slice(8,-1);"Object"===r&&e.constructor&&(r=e.constructor.name);if("Map"===r||"Set"===r)return Array.from(e);if("Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r))return d(e,t)}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function d(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=new Array(t);r<t;r++)n[r]=e[r];return n}function p(e,t){if(null==e)return{};var r,n,o=function(e,t){if(null==e)return{};var r,n,o={},a=Object.keys(e);for(n=0;n<a.length;n++)r=a[n],t.indexOf(r)>=0||(o[r]=e[r]);return o}(e,t);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);for(n=0;n<a.length;n++)r=a[n],t.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(e,r)&&(o[r]=e[r])}return o}(n="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.enterModule:void 0)&&n(e);var y="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.default.signature:function(e){return e},h=function(e){var t=e.title,r=e.children,n=e.width,d=e.footer,y=p(e,u),h=f(Object(a.useState)(0),2),m=h[0],v=h[1];Object(a.useEffect)((function(){return v(Object(c.b)()),function(){window.onresize=null}}),[m]);window.onresize=function(){v(Object(c.b)())};var b=i.a.createElement(i.a.Fragment,null,i.a.createElement(l.a,{onClick:y.onCancel,title:"取消",isMar:!0}),i.a.createElement(l.a,{onClick:y.onOk,title:"确定",type:"primary"}));return i.a.createElement(o.default,s({title:t,width:n,style:{maxWidth:"calc(100vw - 120px)",maxHeight:"calc(100vh - 120px)",marginRight:"auto",marginLeft:"auto",position:"absolute",top:60,right:0,left:0,height:"100%",display:"flex",flexDirection:"column"},wrapClassName:"tiklab_modal",closable:!1,footer:d||b},y),r)};y(h,"useState{[height,setHeight](0)}\nuseEffect{}");var m,v,b=h;t.a=b,(m="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.default:void 0)&&(m.register(h,"Modals","/Users/limingliang/xcode-xpack/web/thoughtware-hadess-ui/src/common/modal/Modal.js"),m.register(b,"default","/Users/limingliang/xcode-xpack/web/thoughtware-hadess-ui/src/common/modal/Modal.js")),(v="undefined"!=typeof reactHotLoaderGlobal?reactHotLoaderGlobal.leaveModule:void 0)&&v(e)}).call(this,r(51)(e))},409:function(e,t,r){}}]);