(window.webpackJsonp=window.webpackJsonp||[]).push([[12],{992:function(e,t,r){"use strict";r.r(t),r.d(t,"default",(function(){return V}));r(449);var n,i,a,o=r(221),l=(r(447),r(219)),c=(r(444),r(215)),u=(r(713),r(712)),s=(r(776),r(774)),f=(r(733),r(734)),m=(r(188),r(43)),p=r(0),d=r.n(p),h=r(6),y=r(86),v=r(833),b=r(207),g=r(698),w=r(166),E=r(167),x=r(7),O=r.n(x),k=r(8);function N(e){return(N="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function L(){/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */L=function(){return t};var e,t={},r=Object.prototype,n=r.hasOwnProperty,i=Object.defineProperty||function(e,t,r){e[t]=r.value},a="function"==typeof Symbol?Symbol:{},o=a.iterator||"@@iterator",l=a.asyncIterator||"@@asyncIterator",c=a.toStringTag||"@@toStringTag";function u(e,t,r){return Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}),e[t]}try{u({},"")}catch(e){u=function(e,t,r){return e[t]=r}}function s(e,t,r,n){var a=t&&t.prototype instanceof y?t:y,o=Object.create(a.prototype),l=new T(n||[]);return i(o,"_invoke",{value:S(e,r,l)}),o}function f(e,t,r){try{return{type:"normal",arg:e.call(t,r)}}catch(e){return{type:"throw",arg:e}}}t.wrap=s;var m="suspendedStart",p="executing",d="completed",h={};function y(){}function v(){}function b(){}var g={};u(g,o,(function(){return this}));var w=Object.getPrototypeOf,E=w&&w(w(I([])));E&&E!==r&&n.call(E,o)&&(g=E);var x=b.prototype=y.prototype=Object.create(g);function O(e){["next","throw","return"].forEach((function(t){u(e,t,(function(e){return this._invoke(t,e)}))}))}function k(e,t){function r(i,a,o,l){var c=f(e[i],e,a);if("throw"!==c.type){var u=c.arg,s=u.value;return s&&"object"==N(s)&&n.call(s,"__await")?t.resolve(s.__await).then((function(e){r("next",e,o,l)}),(function(e){r("throw",e,o,l)})):t.resolve(s).then((function(e){u.value=e,o(u)}),(function(e){return r("throw",e,o,l)}))}l(c.arg)}var a;i(this,"_invoke",{value:function(e,n){function i(){return new t((function(t,i){r(e,n,t,i)}))}return a=a?a.then(i,i):i()}})}function S(t,r,n){var i=m;return function(a,o){if(i===p)throw Error("Generator is already running");if(i===d){if("throw"===a)throw o;return{value:e,done:!0}}for(n.method=a,n.arg=o;;){var l=n.delegate;if(l){var c=j(l,n);if(c){if(c===h)continue;return c}}if("next"===n.method)n.sent=n._sent=n.arg;else if("throw"===n.method){if(i===m)throw i=d,n.arg;n.dispatchException(n.arg)}else"return"===n.method&&n.abrupt("return",n.arg);i=p;var u=f(t,r,n);if("normal"===u.type){if(i=n.done?d:"suspendedYield",u.arg===h)continue;return{value:u.arg,done:n.done}}"throw"===u.type&&(i=d,n.method="throw",n.arg=u.arg)}}}function j(t,r){var n=r.method,i=t.iterator[n];if(i===e)return r.delegate=null,"throw"===n&&t.iterator.return&&(r.method="return",r.arg=e,j(t,r),"throw"===r.method)||"return"!==n&&(r.method="throw",r.arg=new TypeError("The iterator does not provide a '"+n+"' method")),h;var a=f(i,t.iterator,r.arg);if("throw"===a.type)return r.method="throw",r.arg=a.arg,r.delegate=null,h;var o=a.arg;return o?o.done?(r[t.resultName]=o.value,r.next=t.nextLoc,"return"!==r.method&&(r.method="next",r.arg=e),r.delegate=null,h):o:(r.method="throw",r.arg=new TypeError("iterator result is not an object"),r.delegate=null,h)}function _(e){var t={tryLoc:e[0]};1 in e&&(t.catchLoc=e[1]),2 in e&&(t.finallyLoc=e[2],t.afterLoc=e[3]),this.tryEntries.push(t)}function A(e){var t=e.completion||{};t.type="normal",delete t.arg,e.completion=t}function T(e){this.tryEntries=[{tryLoc:"root"}],e.forEach(_,this),this.reset(!0)}function I(t){if(t||""===t){var r=t[o];if(r)return r.call(t);if("function"==typeof t.next)return t;if(!isNaN(t.length)){var i=-1,a=function r(){for(;++i<t.length;)if(n.call(t,i))return r.value=t[i],r.done=!1,r;return r.value=e,r.done=!0,r};return a.next=a}}throw new TypeError(N(t)+" is not iterable")}return v.prototype=b,i(x,"constructor",{value:b,configurable:!0}),i(b,"constructor",{value:v,configurable:!0}),v.displayName=u(b,c,"GeneratorFunction"),t.isGeneratorFunction=function(e){var t="function"==typeof e&&e.constructor;return!!t&&(t===v||"GeneratorFunction"===(t.displayName||t.name))},t.mark=function(e){return Object.setPrototypeOf?Object.setPrototypeOf(e,b):(e.__proto__=b,u(e,c,"GeneratorFunction")),e.prototype=Object.create(x),e},t.awrap=function(e){return{__await:e}},O(k.prototype),u(k.prototype,l,(function(){return this})),t.AsyncIterator=k,t.async=function(e,r,n,i,a){void 0===a&&(a=Promise);var o=new k(s(e,r,n,i),a);return t.isGeneratorFunction(r)?o:o.next().then((function(e){return e.done?e.value:o.next()}))},O(x),u(x,c,"Generator"),u(x,o,(function(){return this})),u(x,"toString",(function(){return"[object Generator]"})),t.keys=function(e){var t=Object(e),r=[];for(var n in t)r.push(n);return r.reverse(),function e(){for(;r.length;){var n=r.pop();if(n in t)return e.value=n,e.done=!1,e}return e.done=!0,e}},t.values=I,T.prototype={constructor:T,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=e,this.done=!1,this.delegate=null,this.method="next",this.arg=e,this.tryEntries.forEach(A),!t)for(var r in this)"t"===r.charAt(0)&&n.call(this,r)&&!isNaN(+r.slice(1))&&(this[r]=e)},stop:function(){this.done=!0;var e=this.tryEntries[0].completion;if("throw"===e.type)throw e.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var r=this;function i(n,i){return l.type="throw",l.arg=t,r.next=n,i&&(r.method="next",r.arg=e),!!i}for(var a=this.tryEntries.length-1;a>=0;--a){var o=this.tryEntries[a],l=o.completion;if("root"===o.tryLoc)return i("end");if(o.tryLoc<=this.prev){var c=n.call(o,"catchLoc"),u=n.call(o,"finallyLoc");if(c&&u){if(this.prev<o.catchLoc)return i(o.catchLoc,!0);if(this.prev<o.finallyLoc)return i(o.finallyLoc)}else if(c){if(this.prev<o.catchLoc)return i(o.catchLoc,!0)}else{if(!u)throw Error("try statement without catch or finally");if(this.prev<o.finallyLoc)return i(o.finallyLoc)}}}},abrupt:function(e,t){for(var r=this.tryEntries.length-1;r>=0;--r){var i=this.tryEntries[r];if(i.tryLoc<=this.prev&&n.call(i,"finallyLoc")&&this.prev<i.finallyLoc){var a=i;break}}a&&("break"===e||"continue"===e)&&a.tryLoc<=t&&t<=a.finallyLoc&&(a=null);var o=a?a.completion:{};return o.type=e,o.arg=t,a?(this.method="next",this.next=a.finallyLoc,h):this.complete(o)},complete:function(e,t){if("throw"===e.type)throw e.arg;return"break"===e.type||"continue"===e.type?this.next=e.arg:"return"===e.type?(this.rval=this.arg=e.arg,this.method="return",this.next="end"):"normal"===e.type&&t&&(this.next=t),h},finish:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.finallyLoc===e)return this.complete(r.completion,r.afterLoc),A(r),h}},catch:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.tryLoc===e){var n=r.completion;if("throw"===n.type){var i=n.arg;A(r)}return i}}throw Error("illegal catch attempt")},delegateYield:function(t,r,n){return this.delegate={iterator:I(t),resultName:r,nextLoc:n},"next"===this.method&&(this.arg=e),h}},t}function S(e,t,r,n,i,a,o){try{var l=e[a](o),c=l.value}catch(e){return void r(e)}l.done?t(c):Promise.resolve(c).then(n,i)}function j(e){return function(){var t=this,r=arguments;return new Promise((function(n,i){var a=e.apply(t,r);function o(e){S(a,n,i,o,l,"next",e)}function l(e){S(a,n,i,o,l,"throw",e)}o(void 0)}))}}function _(e,t,r,n){r&&Object.defineProperty(e,t,{enumerable:r.enumerable,configurable:r.configurable,writable:r.writable,value:r.initializer?r.initializer.call(n):void 0})}function A(e,t){for(var r=0;r<t.length;r++){var n=t[r];n.enumerable=n.enumerable||!1,n.configurable=!0,"value"in n&&(n.writable=!0),Object.defineProperty(e,I(n.key),n)}}function T(e,t,r){return t&&A(e.prototype,t),r&&A(e,r),Object.defineProperty(e,"prototype",{writable:!1}),e}function I(e){var t=function(e,t){if("object"!=N(e)||!e)return e;var r=e[Symbol.toPrimitive];if(void 0!==r){var n=r.call(e,t||"default");if("object"!=N(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(e,"string");return"symbol"==N(t)?t:t+""}function P(e,t,r,n,i){var a={};return Object.keys(n).forEach((function(e){a[e]=n[e]})),a.enumerable=!!a.enumerable,a.configurable=!!a.configurable,("value"in a||a.initializer)&&(a.writable=!0),a=r.slice().reverse().reduce((function(r,n){return n(e,t,r)||r}),a),i&&void 0!==a.initializer&&(a.value=a.initializer?a.initializer.call(i):void 0,a.initializer=void 0),void 0===a.initializer?(Object.defineProperty(e,t,a),null):a}var Y=new(i=P((n=T((function e(){var t,r,n;!function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,e),_(this,"getVersions",i,this),_(this,"findUseLicence",a,this),t=this,r="findAllLicence",n=j(L().mark((function e(){return L().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,h.Axios.post("/licence/findAllLicence");case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)}))),(r=I(r))in t?Object.defineProperty(t,r,{value:n,enumerable:!0,configurable:!0,writable:!0}):t[r]=n}))).prototype,"getVersions",[k.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return j(L().mark((function e(){return L().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,h.Axios.post("/version/getVersion");case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)})))}}),a=P(n.prototype,"findUseLicence",[k.action],{configurable:!0,enumerable:!0,writable:!0,initializer:function(){return j(L().mark((function e(){return L().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,h.Axios.post("/licence/findUseLicence");case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}}),e)})))}}),n);function F(){return(F=Object.assign?Object.assign.bind():function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var n in r)({}).hasOwnProperty.call(r,n)&&(e[n]=r[n])}return e}).apply(null,arguments)}function C(e){return function(e){if(Array.isArray(e))return D(e)}(e)||function(e){if("undefined"!=typeof Symbol&&null!=e[Symbol.iterator]||null!=e["@@iterator"])return Array.from(e)}(e)||M(e)||function(){throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function z(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,i,a,o,l=[],c=!0,u=!1;try{if(a=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;c=!1}else for(;!(c=(n=a.call(r)).done)&&(l.push(n.value),l.length!==t);c=!0);}catch(e){u=!0,i=e}finally{try{if(!c&&null!=r.return&&(o=r.return(),Object(o)!==o))return}finally{if(u)throw i}}return l}}(e,t)||M(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function M(e,t){if(e){if("string"==typeof e)return D(e,t);var r={}.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?D(e,t):void 0}}function D(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=Array(t);r<t;r++)n[r]=e[r];return n}var G=function(e){var t=e.bgroup,r=e.children,n=Y.getVersions,i=Y.findUseLicence,a=Y.findAllLicence,x=z(Object(p.useState)(null),2),k=x[0],N=x[1],L=z(Object(p.useState)(null),2),S=L[0],j=L[1],_=z(Object(p.useState)([]),2),A=_[0],T=_[1],I=z(Object(p.useState)({}),2),P=I[0],M=I[1],D=z(Object(p.useState)(!1),2),G=D[0],H=D[1],U=z(Object(p.useState)(!1),2),V=U[0],J=U[1],K=z(Object(p.useState)(!0),2),R=K[0],$=K[1],q=window.location.origin;Object(p.useEffect)((function(){B()}),[]);var B=function(){n().then((function(e){0===e.code&&(M(e.data),localStorage.setItem(h.LOCALSTORAGE_KEY.VERSION_INFO,JSON.stringify(e.data)),e.data.expired||(Q(),W())),$(!1)}))},Q=function(){i().then((function(e){0===e.code&&e.data&&j(e.data),6e3===e.code&&m.default.error(e.msg)}))},W=function(){a().then((function(e){0===e.code&&N(e.data)}))},X={name:"file",action:q+("/"===q.substring(q.length-1,q.length)?"licence/import":"/licence/import"),onChange:function(e){var t=C(e.fileList);if(t=(t=t.slice(-1)).map((function(e){return e.response&&(e.url=e.response.url),e})),T(t),"uploading"!==e.file.status&&console.log(e.file,e.fileList),"done"===e.file.status){var r=e.file;return 0===r.response.code?(setTimeout((function(){T([]),B()}),1e3),m.default.success("licence导入成功")):m.default.error("licence导入失败：".concat(r.response.msg))}if("error"===e.file.status)return m.default.error("licence导入失败，请重新上传")}},Z=[{title:"生效时间",dataIndex:"expiryTime",key:"expiryTime",width:"32%",ellipsis:!0,render:function(e){return O()(e).format("YYYY-MM-DD HH:mm:ss")}},{title:"过期时间",dataIndex:"issuedTime",key:"issuedTime",width:"30%",ellipsis:!0,render:function(e){return O()(e).format("YYYY-MM-DD HH:mm:ss")}},{title:"用户数",dataIndex:"userNum",key:"userNum",width:"32%",ellipsis:!0,render:function(e){return e>0?e:"无限制"}},{title:"状态",dataIndex:"state",key:"state",width:"16%",ellipsis:!0,render:function(e){return d.a.createElement(d.a.Fragment,null,1===e&&"已失效",2===e&&d.a.createElement("span",{className:"licence-history-modal-blue"},"生效中"),3===e&&"未生效")}}];return d.a.createElement(o.default,{spinning:R,tip:"导入licence中..."},d.a.createElement(l.default,{className:"tiklab_version"},d.a.createElement(c.default,{sm:{span:24},xs:{span:24},md:{span:24},lg:{span:"16",offset:"4"},xl:{span:"14",offset:"5"},xxl:{span:"14",offset:"5"}},d.a.createElement("div",{className:"tiklab_version_container"},d.a.createElement(v.a,{firstItem:"版本与许可证"},d.a.createElement("div",{className:"tiklab_version_up_btn"},2===(null==P?void 0:P.release)&&d.a.createElement(d.a.Fragment,null,d.a.createElement(s.a,F({},X,{fileList:A}),d.a.createElement(b.a,{isMar:!P.expired,type:"primary"},"导入licence")),!P.expired&&d.a.createElement(d.a.Fragment,null,d.a.createElement(b.a,{onClick:function(){return J(!0)}},"历史"),d.a.createElement(g.a,{title:d.a.createElement("div",{className:"licence-history-modal-title"},d.a.createElement("div",null,"历史"),d.a.createElement(b.a,{type:"text",onClick:function(){return J(!1)},title:d.a.createElement(y.a,null)})),width:800,visible:V,onCancel:function(){return J(!1)},footer:null},d.a.createElement("div",{className:"licence-history-modal"},d.a.createElement(u.default,{dataSource:k,columns:Z,pagination:!1,rowKey:function(e,t){return t},locale:{emptyText:d.a.createElement("div",{className:"licence-history-modal-empty"},"暂无历史")}}))))))),d.a.createElement("div",{className:"tiklab_version-info"},d.a.createElement("div",{className:"tiklab_version-info-item"},d.a.createElement("span",{className:"info-item-title"},"应用名称"),h.productTitle[t]),d.a.createElement("div",{className:"tiklab_version-info-item"},d.a.createElement("div",{className:"info-item-title"},"版本类型"),P.expired?d.a.createElement(d.a.Fragment,null,d.a.createElement("div",{className:"info-item-img"},d.a.createElement("img",{src:w.a,alt:"企业版特性",height:16,width:16})),d.a.createElement("div",{className:"info-item-info"},"社区版"),d.a.createElement("div",null,d.a.createElement("span",{className:"info-item-action",onClick:function(){return H(!0)}},"企业版特性"),d.a.createElement("span",{className:"info-item-action",onClick:function(){Object(h.applySubscription)(t)}},"订阅")),d.a.createElement(g.a,{width:800,visible:G,onCancel:function(){return H(!1)},footer:null,title:d.a.createElement("div",{className:"licence-version-feat-modal-title"},d.a.createElement("div",null,"企业版特性"),d.a.createElement(b.a,{type:"text",onClick:function(){return H(!1)},title:d.a.createElement(y.a,null)}))},d.a.createElement("div",{className:"licence-version-feat-modal"},r))):d.a.createElement(d.a.Fragment,null,d.a.createElement("div",{className:"info-item-img"},d.a.createElement("img",{src:E.a,alt:"企业版特性",height:16,width:16})),d.a.createElement("div",null,"企业版"),d.a.createElement("div",{className:"info-item-action"},P.expired?null!=S&&S.tryApply?d.a.createElement(f.default,{color:"#ff4d4f"},"试用已过期"):d.a.createElement(f.default,{color:"#ff4d4f"},"订阅已过期"):null!=S&&S.tryApply?d.a.createElement(f.default,{color:"#5d70ea"},"试用中"):d.a.createElement(f.default,{color:"#5d70ea"},"订阅中")))),d.a.createElement("div",{className:"tiklab_version-info-item"},d.a.createElement("span",{className:"info-item-title"},"用户数"),1===P.release||P.expired?"不限制":(null==S?void 0:S.userNum)>0?S.userNum+"人":"不限制"),d.a.createElement("div",{className:"tiklab_version-info-item"},d.a.createElement("span",{className:"info-item-title"},"生效时间"),null!=S&&S.expiryTime?O()(null==S?void 0:S.expiryTime).format("YYYY-MM-DD HH:mm:ss"):"--"),d.a.createElement("div",{className:"tiklab_version-info-item"},d.a.createElement("span",{className:"info-item-title"},"过期时间"),null!=S&&S.issuedTime?O()(null==S?void 0:S.issuedTime).format("YYYY-MM-DD HH:mm:ss"):"--"))))))},H=r(423);function U(){return(U=Object.assign?Object.assign.bind():function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var n in r)({}).hasOwnProperty.call(r,n)&&(e[n]=r[n])}return e}).apply(null,arguments)}var V=function(e){var t=function(e){return e?d.a.createElement(H.a,{style:{color:"#0063FF"}}):d.a.createElement(y.a,{style:{color:"#ff0000"}})},r=[{title:"功能",dataIndex:"title",key:"title",colSpan:2,render:function(e,t,r){var n={children:e,props:{}};return 0===r&&(n.props.rowSpan=3),r>0&&r<3&&(n.props.rowSpan=0),3===r&&(n.props.rowSpan=2),r>3&&(n.props.rowSpan=0),n}},{title:"功能",dataIndex:"feature",key:"feature",colSpan:0},{title:"社区版",dataIndex:"ce",key:"ce",render:function(e){return t(e)}},{title:"企业版",dataIndex:"ee",key:"ee",render:function(e){return t(e)}}];return d.a.createElement(G,U({},e,{bgroup:"hadess"}),d.a.createElement(u.default,{bordered:!0,columns:r,dataSource:[{key:"1",title:"基本功能",feature:"LDAP",ce:!1,ee:!0},{key:"2",title:"基本功能",feature:"在线客服",ce:!1,ee:!0},{key:"3",title:"基本功能",feature:"用户和部门",ce:!0,ee:!0},{key:"4",title:"升级功能",feature:"插件",ce:!1,ee:!0},{key:"5",title:"升级功能",feature:"权限",ce:!0,ee:!0}],pagination:!1}))}}}]);