(window.webpackJsonp=window.webpackJsonp||[]).push([[20],{1033:function(e,t,r){},1046:function(e,t,r){"use strict";var n=r(0),a=r.n(n),i=r(188),o=r(98),u=r(1041);t.a=function(e){var t=e.currentPage,r=e.changPage,n=e.page,c=n.totalPage,l=void 0===c?1:c,s=n.totalRecord,f=void 0===s?1:s;return l>1&&a.a.createElement("div",{className:"licence-page"},a.a.createElement("div",{className:"licence-page-record"},"  共",f,"条 "),a.a.createElement("div",{className:"".concat(1===t?"licence-page-ban":"licence-page-allow"),onClick:function(){return 1===t?null:r(t-1)}},a.a.createElement(i.default,null)),a.a.createElement("div",{className:"licence-page-current"},t),a.a.createElement("div",{className:"licence-page-line"}," / "),a.a.createElement("div",null,l),a.a.createElement("div",{className:"".concat(t===l?"licence-page-ban":"licence-page-allow"),onClick:function(){return t===l?null:r(t+1)}},a.a.createElement(o.default,null)),a.a.createElement("div",{className:"licence-page-fresh",onClick:function(){return r(1)}},a.a.createElement(u.default,null)))}},108:function(e,t,r){"use strict";r.d(t,"a",(function(){return n}));var n=function(e,t,r){return e>=r*t?r:e<=(r-1)*t+1?1===r?1:r-1:r}},1218:function(e,t,r){"use strict";r(1033)},1240:function(e,t,r){"use strict";r(479);var n,a,i,o,u,c,l,s,f,p=r(222),d=(r(475),r(217)),h=(r(480),r(223)),m=(r(903),r(902)),y=(r(135),r(56)),v=(r(482),r(226)),b=(r(939),r(940)),g=(r(121),r(34)),w=(r(185),r(46)),A=r(0),E=r.n(A),x=r(935),O=r(108),j=r(489),S=r(1045),P=r(206),k=r(1046),_=r(859),N=r(1247),L=r(8),I=r(7);function U(e){return(U="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function T(){/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */T=function(){return t};var e,t={},r=Object.prototype,n=r.hasOwnProperty,a=Object.defineProperty||function(e,t,r){e[t]=r.value},i="function"==typeof Symbol?Symbol:{},o=i.iterator||"@@iterator",u=i.asyncIterator||"@@asyncIterator",c=i.toStringTag||"@@toStringTag";function l(e,t,r){return Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}),e[t]}try{l({},"")}catch(e){l=function(e,t,r){return e[t]=r}}function s(e,t,r,n){var i=t&&t.prototype instanceof y?t:y,o=Object.create(i.prototype),u=new _(n||[]);return a(o,"_invoke",{value:j(e,r,u)}),o}function f(e,t,r){try{return{type:"normal",arg:e.call(t,r)}}catch(e){return{type:"throw",arg:e}}}t.wrap=s;var p="suspendedStart",d="executing",h="completed",m={};function y(){}function v(){}function b(){}var g={};l(g,o,(function(){return this}));var w=Object.getPrototypeOf,A=w&&w(w(N([])));A&&A!==r&&n.call(A,o)&&(g=A);var E=b.prototype=y.prototype=Object.create(g);function x(e){["next","throw","return"].forEach((function(t){l(e,t,(function(e){return this._invoke(t,e)}))}))}function O(e,t){function r(a,i,o,u){var c=f(e[a],e,i);if("throw"!==c.type){var l=c.arg,s=l.value;return s&&"object"==U(s)&&n.call(s,"__await")?t.resolve(s.__await).then((function(e){r("next",e,o,u)}),(function(e){r("throw",e,o,u)})):t.resolve(s).then((function(e){l.value=e,o(l)}),(function(e){return r("throw",e,o,u)}))}u(c.arg)}var i;a(this,"_invoke",{value:function(e,n){function a(){return new t((function(t,a){r(e,n,t,a)}))}return i=i?i.then(a,a):a()}})}function j(t,r,n){var a=p;return function(i,o){if(a===d)throw Error("Generator is already running");if(a===h){if("throw"===i)throw o;return{value:e,done:!0}}for(n.method=i,n.arg=o;;){var u=n.delegate;if(u){var c=S(u,n);if(c){if(c===m)continue;return c}}if("next"===n.method)n.sent=n._sent=n.arg;else if("throw"===n.method){if(a===p)throw a=h,n.arg;n.dispatchException(n.arg)}else"return"===n.method&&n.abrupt("return",n.arg);a=d;var l=f(t,r,n);if("normal"===l.type){if(a=n.done?h:"suspendedYield",l.arg===m)continue;return{value:l.arg,done:n.done}}"throw"===l.type&&(a=h,n.method="throw",n.arg=l.arg)}}}function S(t,r){var n=r.method,a=t.iterator[n];if(a===e)return r.delegate=null,"throw"===n&&t.iterator.return&&(r.method="return",r.arg=e,S(t,r),"throw"===r.method)||"return"!==n&&(r.method="throw",r.arg=new TypeError("The iterator does not provide a '"+n+"' method")),m;var i=f(a,t.iterator,r.arg);if("throw"===i.type)return r.method="throw",r.arg=i.arg,r.delegate=null,m;var o=i.arg;return o?o.done?(r[t.resultName]=o.value,r.next=t.nextLoc,"return"!==r.method&&(r.method="next",r.arg=e),r.delegate=null,m):o:(r.method="throw",r.arg=new TypeError("iterator result is not an object"),r.delegate=null,m)}function P(e){var t={tryLoc:e[0]};1 in e&&(t.catchLoc=e[1]),2 in e&&(t.finallyLoc=e[2],t.afterLoc=e[3]),this.tryEntries.push(t)}function k(e){var t=e.completion||{};t.type="normal",delete t.arg,e.completion=t}function _(e){this.tryEntries=[{tryLoc:"root"}],e.forEach(P,this),this.reset(!0)}function N(t){if(t||""===t){var r=t[o];if(r)return r.call(t);if("function"==typeof t.next)return t;if(!isNaN(t.length)){var a=-1,i=function r(){for(;++a<t.length;)if(n.call(t,a))return r.value=t[a],r.done=!1,r;return r.value=e,r.done=!0,r};return i.next=i}}throw new TypeError(U(t)+" is not iterable")}return v.prototype=b,a(E,"constructor",{value:b,configurable:!0}),a(b,"constructor",{value:v,configurable:!0}),v.displayName=l(b,c,"GeneratorFunction"),t.isGeneratorFunction=function(e){var t="function"==typeof e&&e.constructor;return!!t&&(t===v||"GeneratorFunction"===(t.displayName||t.name))},t.mark=function(e){return Object.setPrototypeOf?Object.setPrototypeOf(e,b):(e.__proto__=b,l(e,c,"GeneratorFunction")),e.prototype=Object.create(E),e},t.awrap=function(e){return{__await:e}},x(O.prototype),l(O.prototype,u,(function(){return this})),t.AsyncIterator=O,t.async=function(e,r,n,a,i){void 0===i&&(i=Promise);var o=new O(s(e,r,n,a),i);return t.isGeneratorFunction(r)?o:o.next().then((function(e){return e.done?e.value:o.next()}))},x(E),l(E,c,"Generator"),l(E,o,(function(){return this})),l(E,"toString",(function(){return"[object Generator]"})),t.keys=function(e){var t=Object(e),r=[];for(var n in t)r.push(n);return r.reverse(),function e(){for(;r.length;){var n=r.pop();if(n in t)return e.value=n,e.done=!1,e}return e.done=!0,e}},t.values=N,_.prototype={constructor:_,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=e,this.done=!1,this.delegate=null,this.method="next",this.arg=e,this.tryEntries.forEach(k),!t)for(var r in this)"t"===r.charAt(0)&&n.call(this,r)&&!isNaN(+r.slice(1))&&(this[r]=e)},stop:function(){this.done=!0;var e=this.tryEntries[0].completion;if("throw"===e.type)throw e.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var r=this;function a(n,a){return u.type="throw",u.arg=t,r.next=n,a&&(r.method="next",r.arg=e),!!a}for(var i=this.tryEntries.length-1;i>=0;--i){var o=this.tryEntries[i],u=o.completion;if("root"===o.tryLoc)return a("end");if(o.tryLoc<=this.prev){var c=n.call(o,"catchLoc"),l=n.call(o,"finallyLoc");if(c&&l){if(this.prev<o.catchLoc)return a(o.catchLoc,!0);if(this.prev<o.finallyLoc)return a(o.finallyLoc)}else if(c){if(this.prev<o.catchLoc)return a(o.catchLoc,!0)}else{if(!l)throw Error("try statement without catch or finally");if(this.prev<o.finallyLoc)return a(o.finallyLoc)}}}},abrupt:function(e,t){for(var r=this.tryEntries.length-1;r>=0;--r){var a=this.tryEntries[r];if(a.tryLoc<=this.prev&&n.call(a,"finallyLoc")&&this.prev<a.finallyLoc){var i=a;break}}i&&("break"===e||"continue"===e)&&i.tryLoc<=t&&t<=i.finallyLoc&&(i=null);var o=i?i.completion:{};return o.type=e,o.arg=t,i?(this.method="next",this.next=i.finallyLoc,m):this.complete(o)},complete:function(e,t){if("throw"===e.type)throw e.arg;return"break"===e.type||"continue"===e.type?this.next=e.arg:"return"===e.type?(this.rval=this.arg=e.arg,this.method="return",this.next="end"):"normal"===e.type&&t&&(this.next=t),m},finish:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.finallyLoc===e)return this.complete(r.completion,r.afterLoc),k(r),m}},catch:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.tryLoc===e){var n=r.completion;if("throw"===n.type){var a=n.arg;k(r)}return a}}throw Error("illegal catch attempt")},delegateYield:function(t,r,n){return this.delegate={iterator:N(t),resultName:r,nextLoc:n},"next"===this.method&&(this.arg=e),m}},t}function C(e,t,r,n,a,i,o){try{var u=e[i](o),c=u.value}catch(e){return void r(e)}u.done?t(c):Promise.resolve(c).then(n,a)}function z(e){return function(){var t=this,r=arguments;return new Promise((function(n,a){var i=e.apply(t,r);function o(e){C(i,n,a,o,u,"next",e)}function u(e){C(i,n,a,o,u,"throw",e)}o(void 0)}))}}function R(e,t,r,n){r&&Object.defineProperty(e,t,{enumerable:r.enumerable,configurable:r.configurable,writable:r.writable,value:r.initializer?r.initializer.call(n):void 0})}function D(e,t){for(var r=0;r<t.length;r++){var n=t[r];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(e,K(n.key),n)}}function F(e,t,r){return t&&D(e.prototype,t),r&&D(e,r),Object.defineProperty(e,"prototype",{writable:!1}),e}function G(e,t,r){return(t=K(t))in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function K(e){var t=function(e,t){if("object"!=U(e)||!e)return e;var r=e[Symbol.toPrimitive];if(void 0!==r){var n=r.call(e,t||"default");if("object"!=U(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(e,"string");return"symbol"==U(t)?t:t+""}function M(e,t,r,n,a){var i={};return Object.keys(n).forEach((function(e){i[e]=n[e]})),i.enumerable=!!i.enumerable,i.configurable=!!i.configurable,("value"in i||i.initializer)&&(i.writable=!0),i=r.slice().reverse().reduce((function(r,n){return n(e,t,r)||r}),i),a&&void 0!==i.initializer&&(i.value=i.initializer?i.initializer.call(a):void 0,i.initializer=void 0),void 0===i.initializer?(Object.defineProperty(e,t,i),null):i}var B=new(a=M((n=F((function e(){!function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,e),R(this,"findApplyAuth",a,this),R(this,"findApplyAuthUserList",i,this),R(this,"findApplyAuthUserPage",o,this),R(this,"createUserApplyAuth",u,this),R(this,"removeUserApplyAuth",c,this),R(this,"updateApplyAuthType",l,this),R(this,"deleteUserApplyAuth",s,this),R(this,"findAdminRoleUser",f,this),G(this,"findAllList",z(T().mark((function e(){return T().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,I.Axios.post("/user/userdir/findAllList");case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)})))),G(this,"findUserPage",function(){var e=z(T().mark((function e(t){return T().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,I.Axios.post("/user/user/findUserPage",t);case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}())}))).prototype,"findApplyAuth",[L.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return z(T().mark((function e(){return T().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,I.Axios.post("/applyAuth/findApplyAuth");case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)})))}}),i=M(n.prototype,"findApplyAuthUserList",[L.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return z(T().mark((function e(){return T().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,I.Axios.post("/applyAuthUser/findApplyAuthUserList",{});case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)})))}}),o=M(n.prototype,"findApplyAuthUserPage",[L.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var e=z(T().mark((function e(t){return T().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,I.Axios.post("/applyAuthUser/findApplyAuthUserPage",t);case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()}}),u=M(n.prototype,"createUserApplyAuth",[L.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var e=z(T().mark((function e(t){var r;return T().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return(r=new FormData).append("userId",t),e.next=4,I.Axios.post("/applyAuth/createUserApplyAuth",r);case 4:return e.abrupt("return",e.sent);case 5:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()}}),c=M(n.prototype,"removeUserApplyAuth",[L.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var e=z(T().mark((function e(t){var r;return T().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return(r=new FormData).append("userId",t),e.next=4,I.Axios.post("/applyAuth/removeUserApplyAuth",r);case 4:return e.abrupt("return",e.sent);case 5:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()}}),l=M(n.prototype,"updateApplyAuthType",[L.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var e=z(T().mark((function e(t){return T().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,I.Axios.post("/applyAuthType/updateApplyAuthType",t);case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()}}),s=M(n.prototype,"deleteUserApplyAuth",[L.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return function(){var e=z(T().mark((function e(t){var r;return T().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return(r=new FormData).append("userId",t),e.next=4,I.Axios.post("/applyAuth/deleteUserApplyAuth",r);case 4:return e.abrupt("return",e.sent);case 5:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()}}),f=M(n.prototype,"findAdminRoleUser",[L.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return z(T().mark((function e(){return T().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,I.Axios.post("/roleUser/findAdminRoleUser");case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)})))}}),n);function $(e){return($="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function J(e){return function(e){if(Array.isArray(e))return W(e)}(e)||function(e){if("undefined"!=typeof Symbol&&null!=e[Symbol.iterator]||null!=e["@@iterator"])return Array.from(e)}(e)||Q(e)||function(){throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function V(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function Y(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?V(Object(r),!0).forEach((function(t){q(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):V(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function q(e,t,r){return(t=function(e){var t=function(e,t){if("object"!=$(e)||!e)return e;var r=e[Symbol.toPrimitive];if(void 0!==r){var n=r.call(e,t||"default");if("object"!=$(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(e,"string");return"symbol"==$(t)?t:t+""}(t))in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function H(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,a,i,o,u=[],c=!0,l=!1;try{if(i=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;c=!1}else for(;!(c=(n=i.call(r)).done)&&(u.push(n.value),u.length!==t);c=!0);}catch(e){l=!0,a=e}finally{try{if(!c&&null!=r.return&&(o=r.return(),Object(o)!==o))return}finally{if(l)throw a}}return u}}(e,t)||Q(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function Q(e,t){if(e){if("string"==typeof e)return W(e,t);var r={}.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?W(e,t):void 0}}function W(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=Array(t);r<t;r++)n[r]=e[r];return n}var X=function(e){var t=e.visible,r=e.handleOk,n=e.handleCancel,a=e.allSelectIds,i=e.selectedRowKeys,o=e.setSelectedRowKeys,u=e.userDir,c=e.getAuthInfo,l=B.findUserPage,s=B.findAllList,f=H(Object(A.useState)([]),2),p=f[0],d=f[1],h=H(Object(A.useState)({pageSize:6,currentPage:1}),1)[0],v=H(Object(A.useState)({}),2),b=v[0],w=v[1],x=H(Object(A.useState)({pageParam:h}),2),O=x[0],S=x[1],L=H(Object(A.useState)([]),2),I=L[0],U=L[1];Object(A.useEffect)((function(){u?U(u):s().then((function(e){e.code||U(e.data)}))}),[u]),Object(A.useEffect)((function(){T()}),[O]);var T=function(){l(O).then((function(e){e.code||(w({totalRecord:e.data.totalRecord,totalPage:e.data.totalPage}),d(e.data.dataList))}))},C=function(e){return a&&a.some((function(t){return t===e.id}))},z=function(e){if(!C(e)){var t=i.indexOf(e.id);if(t>=0){if(c(!1,1))return;i.splice(t,1)}else{if(c(!0,1))return;i.push(e.id)}o(J(i))}},R={selectedRowKeys:i,onSelectAll:function(e,t,r){var n=r.map((function(e){return e&&e.id})).filter((function(e){return void 0!==e}));if(e){var a=n.filter((function(e){return!t.includes(e)}));if(c(e,a.length))return;var u=Array.from(new Set([].concat(J(n),J(i))));o(u)}else{if(c(e,n.length))return;var l=i.filter((function(e){return!n.includes(e)}));o(l)}},onSelect:function(e){return z(e)},getCheckboxProps:function(e){return{disabled:C(e)}}},D=[{title:"姓名",dataIndex:"nickname",key:"nickname",width:"30%",ellipsis:!0,render:function(e,t){return E.a.createElement(g.default,null,E.a.createElement(j.a,{userInfo:t}),e)}},{title:"手机号",dataIndex:"phone",key:"phone",width:"25%",ellipsis:!0,render:function(e){return e||"--"}},{title:"邮箱",dataIndex:"email",key:"email",width:"25%",ellipsis:!0,render:function(e){return e||"--"}},{title:"用户目录",dataIndex:"dirId",key:"dirId",width:"15%",ellipsis:!0,render:function(e){var t=null==I?void 0:I.find((function(t){return t.id===e}));return t?t.name:"--"}}];return E.a.createElement(_.a,{title:"选择用户",visible:t,width:800,footer:E.a.createElement("div",{className:"tiklab_productauth_type",style:e.children?{justifyContent:"space-between"}:{justifyContent:"flex-end"}},e.children,E.a.createElement("div",null,E.a.createElement(P.a,{onClick:n,title:"取消",isMar:!0}),E.a.createElement(P.a,{onClick:r,title:"确定",type:"primary"})))},E.a.createElement("div",{className:"licence-user-add-modal"},E.a.createElement(N.a,{placeholder:"搜索姓名、手机号",onPressEnter:function(e){S(Y(Y({},O),{},{nickname:e.target.value,pageParam:h}))},style:{width:200}}),E.a.createElement("div",{style:{width:"100%",paddingTop:15}},E.a.createElement(m.default,{columns:D,dataSource:p,rowSelection:R,onRow:function(e){return{onClick:function(){return z(e)}}},rowKey:function(e){return e.id},pagination:!1,locale:{emptyText:E.a.createElement(y.default,{description:"没有查询到数据"})}}),E.a.createElement(k.a,{currentPage:O.pageParam.currentPage,changPage:function(e){S(Y(Y({},O),{},{pageParam:{pageSize:6,currentPage:e}}))},page:b}))))};function Z(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,a,i,o,u=[],c=!0,l=!1;try{if(i=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;c=!1}else for(;!(c=(n=i.call(r)).done)&&(u.push(n.value),u.length!==t);c=!0);}catch(e){l=!0,a=e}finally{try{if(!c&&null!=r.return&&(o=r.return(),Object(o)!==o))return}finally{if(l)throw a}}return u}}(e,t)||function(e,t){if(e){if("string"==typeof e)return ee(e,t);var r={}.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?ee(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function ee(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=Array(t);r<t;r++)n[r]=e[r];return n}var te=function(e){var t=e.visible,r=e.setVisible,n=e.auth,a=e.userDir,i=e.findAllProductAuth,o=B.createUserApplyAuth,u=B.findApplyAuthUserList,c=Z(Object(A.useState)(null),2),l=c[0],s=c[1],f=Z(Object(A.useState)([]),2),p=f[0],d=f[1],h=Z(Object(A.useState)([]),2),m=h[0],y=h[1];Object(A.useEffect)((function(){t&&(s(n),u().then((function(e){if(0===e.code){var t=(null==e?void 0:e.data)||[];y(null==t?void 0:t.map((function(e){var t;return null==e||null===(t=e.user)||void 0===t?void 0:t.id}))),d(null==t?void 0:t.map((function(e){var t;return null==e||null===(t=e.user)||void 0===t?void 0:t.id})))}})))}),[t]);var v=function(){r(!1),d([]),y([])};return E.a.createElement(X,{userDir:a,visible:t,allSelectIds:m,selectedRowKeys:p,setSelectedRowKeys:d,handleOk:function(){var e=p.filter((function(e){return!m.includes(e)})),t=e&&e.map((function(e){return o(e)}));Promise.all(t).then((function(e){setTimeout((function(){i(),v()}),200)}))},handleCancel:v,getAuthInfo:function(e,t){if(!l)return!0;var r=l.residueNumber,n=l.userNumber;return-1===r?(s({userNumber:e?n+t:n-t,residueNumber:-1}),!1):t>r?(w.default.error("超出授权人数"),!0):(s({userNumber:e?n+t:n-t,residueNumber:e?r-t:r+t}),!1)}},E.a.createElement("div",{className:"tiklab_productauth_type"},E.a.createElement("div",{className:"tiklab_productauth_type productauth_already"},E.a.createElement("div",{className:"productauth_type_title"},"已选择人数"),E.a.createElement("div",null,(null==l?void 0:l.userNumber)||0)),E.a.createElement("div",{className:"tiklab_productauth_type"},E.a.createElement("div",{className:"productauth_type_title"},"剩余授权人数"),E.a.createElement("div",null,-1===(null==l?void 0:l.residueNumber)?"不限制":null==l?void 0:l.residueNumber))))};r(1033);function re(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,a,i,o,u=[],c=!0,l=!1;try{if(i=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;c=!1}else for(;!(c=(n=i.call(r)).done)&&(u.push(n.value),u.length!==t);c=!0);}catch(e){l=!0,a=e}finally{try{if(!c&&null!=r.return&&(o=r.return(),Object(o)!==o))return}finally{if(l)throw a}}return u}}(e,t)||function(e,t){if(e){if("string"==typeof e)return ne(e,t);var r={}.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?ne(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function ne(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=Array(t);r<t;r++)n[r]=e[r];return n}var ae=function(e){var t=B.findApplyAuth,r=B.createUserApplyAuth,n=B.removeUserApplyAuth,a=B.deleteUserApplyAuth,i=B.findAdminRoleUser,o=B.findAllList,u=B.findApplyAuthUserPage,c=re(Object(A.useState)(!1),2),l=c[0],s=c[1],f=re(Object(A.useState)({}),2),_=f[0],N=f[1],L=re(Object(A.useState)({}),2),I=L[0],U=L[1],T=re(Object(A.useState)({pageParam:{pageSize:10,currentPage:1}}),2),C=T[0],z=T[1],R=re(Object(A.useState)(!1),2),D=R[0],F=R[1],G=re(Object(A.useState)(null),2),K=G[0],M=G[1],$=re(Object(A.useState)([]),2),J=$[0],V=$[1];Object(A.useEffect)((function(){q(),i().then((function(e){0===e.code&&M(e.data)})),o().then((function(e){0===e.code&&V(e.data)}))}),[]),Object(A.useEffect)((function(){Y()}),[C]);var Y=function(){u(C).then((function(e){0===e.code&&U(e.data)}))},q=function(){s(!0),t().then((function(e){s(!1),0===e.code&&N(e.data)}))},H=function(){q(),Y()},Q=function(e){var t=e.user.id;"2"===e.state?r(t).then((function(e){0===e.code?H():w.default.info(e.msg)})):n(t).then((function(e){0===e.code?H():w.default.info(e.msg)}))},W=function(e){z({pageParam:{currentPage:e,pageSize:10}})},X=[{title:"姓名",dataIndex:["user","nickname"],key:["user","nickname"],width:"19%",ellipsis:!0,render:function(e,t){return E.a.createElement(g.default,null,E.a.createElement(j.a,{userInfo:null==t?void 0:t.user}),e)}},{title:"手机号",dataIndex:["user","phone"],key:["user","phone"],width:"18%",ellipsis:!0,render:function(e){return e||"--"}},{title:"邮箱",dataIndex:["user","email"],key:["user","email"],width:"18%",ellipsis:!0,render:function(e){return e||"--"}},{title:"用户目录",dataIndex:["user","dirId"],key:["user","dirId"],width:"15%",ellipsis:!0,render:function(e){var t=null==J?void 0:J.find((function(t){return t.id===e}));return t?t.name:"--"}},{title:"状态",dataIndex:"state",key:"state",width:"13%",ellipsis:!0,render:function(e){return"1"===e?E.a.createElement(b.default,{color:"green"},"已激活"):E.a.createElement(b.default,{color:"red"},"未激活")}},{title:"操作",width:"17%",ellipsis:!0,render:function(e,t){var r,n;if((null==K||null===(r=K.user)||void 0===r?void 0:r.id)!==(null==t||null===(n=t.user)||void 0===n?void 0:n.id)){var i=t.state;return E.a.createElement(g.default,{size:"middle"},"2"===i?E.a.createElement(x.PrivilegeButton,{code:"version_open"},E.a.createElement("span",{className:"tiklab_productauth-action-ac",onClick:function(){return Q(t)}},"激活")):E.a.createElement(x.PrivilegeButton,{code:"version_open"},E.a.createElement("span",{className:"tiklab_productauth-action-ac",onClick:function(){return Q(t)}},"取消激活")),E.a.createElement(x.PrivilegeButton,{code:"version_delete"},E.a.createElement(v.default,{placement:"topRight",title:"你确定移除吗",onConfirm:function(){return function(e){var t=e.user.id;a(t).then((function(e){if(0===e.code){var t=Object(O.a)(I.totalRecord,10,C.pageParam.currentPage);q(),W(t)}else w.default.info(e.msg)}))}(t)},okText:"确定",cancelText:"取消"},E.a.createElement("span",{className:"tiklab_productauth-action-bc"},"删除"))))}}}];return E.a.createElement(p.default,{className:"tiklab_productauth"},E.a.createElement(d.default,{sm:{span:24},xs:{span:24},md:{span:24},lg:{span:"21",offset:"1"},xl:{span:"20",offset:"2"},xxl:{span:"18",offset:"3"},className:"tiklab_productauth_container"},E.a.createElement(h.default,{spinning:l,tip:"正在查询……"},E.a.createElement(S.a,{firstItem:"应用访问权限"},E.a.createElement(P.a,{title:"添加用户",type:"primary",onClick:function(){return F(!0)}}),E.a.createElement(te,{auth:_,visible:D,setVisible:F,userDir:J,findAllProductAuth:H})),E.a.createElement("div",{className:"tiklab_productauth_type"},E.a.createElement("div",{className:"tiklab_productauth_type productauth_already"},E.a.createElement("div",{className:"productauth_type_title"},"已授权"),E.a.createElement("div",null,(null==_?void 0:_.userNumber)||0)),E.a.createElement("div",{className:"tiklab_productauth_type"},E.a.createElement("div",{className:"productauth_type_title"},"剩余授权"),E.a.createElement("div",null,-1===(null==_?void 0:_.residueNumber)?"不限制":null==_?void 0:_.residueNumber))),E.a.createElement("div",{className:"tiklab_productauth_table"},E.a.createElement(m.default,{dataSource:(null==I?void 0:I.dataList)||[],columns:X,pagination:!1,rowKey:function(e){var t;return null==e||null===(t=e.user)||void 0===t?void 0:t.id},locale:{emptyText:E.a.createElement(y.default,{description:"没有查询到数据"})}}),E.a.createElement(k.a,{currentPage:C.pageParam.currentPage,page:I,changPage:W})))))};t.a=ae},1247:function(e,t,r){"use strict";r(474);var n=r(216),a=r(0),i=r.n(a),o=r(158);function u(){return(u=Object.assign?Object.assign.bind():function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var n in r)({}).hasOwnProperty.call(r,n)&&(e[n]=r[n])}return e}).apply(null,arguments)}t.a=function(e){var t=u({},(function(e){if(null==e)throw new TypeError("Cannot destructure "+e)}(e),e));return i.a.createElement(n.default,u({},t,{allowClear:!0,bordered:!1,autoComplete:"off",prefix:i.a.createElement(o.default,{style:{fontSize:16}}),className:"licence-search-input",onChange:function(e){"click"===e.type&&t.onPressEnter(e)}}))}}}]);