(window.webpackJsonp=window.webpackJsonp||[]).push([[33],{1046:function(e,t,r){"use strict";r.r(t),r.d(t,"default",(function(){return C}));r(265);var n=r(187),o=(r(471),r(470)),a=(r(506),r(502)),i=r.n(a),c=r(0),l=r.n(c),u=r(44);Object(u.a)(".proxy {\n  width: 100%;\n  height: 100%;\n  overflow: auto;\n}\n.proxy .proxy-head-up {\n  display: flex;\n  flex-direction: row;\n  justify-content: space-between;\n  align-items: center;\n}\n.proxy .proxy-table-style {\n  margin-top: 10px;\n}\n.proxy .proxy-icon-style {\n  display: flex;\n  gap: 10px;\n  cursor: pointer;\n}\n.proxy .proxy-icon-no {\n  color: #cccccc;\n}");var s=r(475),f=r(58),y=r(567),p=r(509),h=r(1055),d=r(472),m=(r(264),r(186)),v=(r(267),r(172)),g=(r(266),r(118)),b=r(485);function w(e){return(w="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function x(){return(x=Object.assign?Object.assign.bind():function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var n in r)({}).hasOwnProperty.call(r,n)&&(e[n]=r[n])}return e}).apply(null,arguments)}function E(){/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */E=function(){return t};var e,t={},r=Object.prototype,n=r.hasOwnProperty,o=Object.defineProperty||function(e,t,r){e[t]=r.value},a="function"==typeof Symbol?Symbol:{},i=a.iterator||"@@iterator",c=a.asyncIterator||"@@asyncIterator",l=a.toStringTag||"@@toStringTag";function u(e,t,r){return Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}),e[t]}try{u({},"")}catch(e){u=function(e,t,r){return e[t]=r}}function s(e,t,r,n){var a=t&&t.prototype instanceof m?t:m,i=Object.create(a.prototype),c=new I(n||[]);return o(i,"_invoke",{value:L(e,r,c)}),i}function f(e,t,r){try{return{type:"normal",arg:e.call(t,r)}}catch(e){return{type:"throw",arg:e}}}t.wrap=s;var y="suspendedStart",p="executing",h="completed",d={};function m(){}function v(){}function g(){}var b={};u(b,i,(function(){return this}));var x=Object.getPrototypeOf,O=x&&x(x(T([])));O&&O!==r&&n.call(O,i)&&(b=O);var j=g.prototype=m.prototype=Object.create(b);function P(e){["next","throw","return"].forEach((function(t){u(e,t,(function(e){return this._invoke(t,e)}))}))}function S(e,t){function r(o,a,i,c){var l=f(e[o],e,a);if("throw"!==l.type){var u=l.arg,s=u.value;return s&&"object"==w(s)&&n.call(s,"__await")?t.resolve(s.__await).then((function(e){r("next",e,i,c)}),(function(e){r("throw",e,i,c)})):t.resolve(s).then((function(e){u.value=e,i(u)}),(function(e){return r("throw",e,i,c)}))}c(l.arg)}var a;o(this,"_invoke",{value:function(e,n){function o(){return new t((function(t,o){r(e,n,t,o)}))}return a=a?a.then(o,o):o()}})}function L(t,r,n){var o=y;return function(a,i){if(o===p)throw Error("Generator is already running");if(o===h){if("throw"===a)throw i;return{value:e,done:!0}}for(n.method=a,n.arg=i;;){var c=n.delegate;if(c){var l=_(c,n);if(l){if(l===d)continue;return l}}if("next"===n.method)n.sent=n._sent=n.arg;else if("throw"===n.method){if(o===y)throw o=h,n.arg;n.dispatchException(n.arg)}else"return"===n.method&&n.abrupt("return",n.arg);o=p;var u=f(t,r,n);if("normal"===u.type){if(o=n.done?h:"suspendedYield",u.arg===d)continue;return{value:u.arg,done:n.done}}"throw"===u.type&&(o=h,n.method="throw",n.arg=u.arg)}}}function _(t,r){var n=r.method,o=t.iterator[n];if(o===e)return r.delegate=null,"throw"===n&&t.iterator.return&&(r.method="return",r.arg=e,_(t,r),"throw"===r.method)||"return"!==n&&(r.method="throw",r.arg=new TypeError("The iterator does not provide a '"+n+"' method")),d;var a=f(o,t.iterator,r.arg);if("throw"===a.type)return r.method="throw",r.arg=a.arg,r.delegate=null,d;var i=a.arg;return i?i.done?(r[t.resultName]=i.value,r.next=t.nextLoc,"return"!==r.method&&(r.method="next",r.arg=e),r.delegate=null,d):i:(r.method="throw",r.arg=new TypeError("iterator result is not an object"),r.delegate=null,d)}function k(e){var t={tryLoc:e[0]};1 in e&&(t.catchLoc=e[1]),2 in e&&(t.finallyLoc=e[2],t.afterLoc=e[3]),this.tryEntries.push(t)}function N(e){var t=e.completion||{};t.type="normal",delete t.arg,e.completion=t}function I(e){this.tryEntries=[{tryLoc:"root"}],e.forEach(k,this),this.reset(!0)}function T(t){if(t||""===t){var r=t[i];if(r)return r.call(t);if("function"==typeof t.next)return t;if(!isNaN(t.length)){var o=-1,a=function r(){for(;++o<t.length;)if(n.call(t,o))return r.value=t[o],r.done=!1,r;return r.value=e,r.done=!0,r};return a.next=a}}throw new TypeError(w(t)+" is not iterable")}return v.prototype=g,o(j,"constructor",{value:g,configurable:!0}),o(g,"constructor",{value:v,configurable:!0}),v.displayName=u(g,l,"GeneratorFunction"),t.isGeneratorFunction=function(e){var t="function"==typeof e&&e.constructor;return!!t&&(t===v||"GeneratorFunction"===(t.displayName||t.name))},t.mark=function(e){return Object.setPrototypeOf?Object.setPrototypeOf(e,g):(e.__proto__=g,u(e,l,"GeneratorFunction")),e.prototype=Object.create(j),e},t.awrap=function(e){return{__await:e}},P(S.prototype),u(S.prototype,c,(function(){return this})),t.AsyncIterator=S,t.async=function(e,r,n,o,a){void 0===a&&(a=Promise);var i=new S(s(e,r,n,o),a);return t.isGeneratorFunction(r)?i:i.next().then((function(e){return e.done?e.value:i.next()}))},P(j),u(j,l,"Generator"),u(j,i,(function(){return this})),u(j,"toString",(function(){return"[object Generator]"})),t.keys=function(e){var t=Object(e),r=[];for(var n in t)r.push(n);return r.reverse(),function e(){for(;r.length;){var n=r.pop();if(n in t)return e.value=n,e.done=!1,e}return e.done=!0,e}},t.values=T,I.prototype={constructor:I,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=e,this.done=!1,this.delegate=null,this.method="next",this.arg=e,this.tryEntries.forEach(N),!t)for(var r in this)"t"===r.charAt(0)&&n.call(this,r)&&!isNaN(+r.slice(1))&&(this[r]=e)},stop:function(){this.done=!0;var e=this.tryEntries[0].completion;if("throw"===e.type)throw e.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var r=this;function o(n,o){return c.type="throw",c.arg=t,r.next=n,o&&(r.method="next",r.arg=e),!!o}for(var a=this.tryEntries.length-1;a>=0;--a){var i=this.tryEntries[a],c=i.completion;if("root"===i.tryLoc)return o("end");if(i.tryLoc<=this.prev){var l=n.call(i,"catchLoc"),u=n.call(i,"finallyLoc");if(l&&u){if(this.prev<i.catchLoc)return o(i.catchLoc,!0);if(this.prev<i.finallyLoc)return o(i.finallyLoc)}else if(l){if(this.prev<i.catchLoc)return o(i.catchLoc,!0)}else{if(!u)throw Error("try statement without catch or finally");if(this.prev<i.finallyLoc)return o(i.finallyLoc)}}}},abrupt:function(e,t){for(var r=this.tryEntries.length-1;r>=0;--r){var o=this.tryEntries[r];if(o.tryLoc<=this.prev&&n.call(o,"finallyLoc")&&this.prev<o.finallyLoc){var a=o;break}}a&&("break"===e||"continue"===e)&&a.tryLoc<=t&&t<=a.finallyLoc&&(a=null);var i=a?a.completion:{};return i.type=e,i.arg=t,a?(this.method="next",this.next=a.finallyLoc,d):this.complete(i)},complete:function(e,t){if("throw"===e.type)throw e.arg;return"break"===e.type||"continue"===e.type?this.next=e.arg:"return"===e.type?(this.rval=this.arg=e.arg,this.method="return",this.next="end"):"normal"===e.type&&t&&(this.next=t),d},finish:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.finallyLoc===e)return this.complete(r.completion,r.afterLoc),N(r),d}},catch:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var r=this.tryEntries[t];if(r.tryLoc===e){var n=r.completion;if("throw"===n.type){var o=n.arg;N(r)}return o}}throw Error("illegal catch attempt")},delegateYield:function(t,r,n){return this.delegate={iterator:T(t),resultName:r,nextLoc:n},"next"===this.method&&(this.arg=e),d}},t}function O(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function j(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?O(Object(r),!0).forEach((function(t){P(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):O(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function P(e,t,r){return(t=function(e){var t=function(e,t){if("object"!=w(e)||!e)return e;var r=e[Symbol.toPrimitive];if(void 0!==r){var n=r.call(e,t||"default");if("object"!=w(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(e,"string");return"symbol"==w(t)?t:t+""}(t))in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function S(e,t,r,n,o,a,i){try{var c=e[a](i),l=c.value}catch(e){return void r(e)}c.done?t(l):Promise.resolve(l).then(n,o)}function L(e){return function(){var t=this,r=arguments;return new Promise((function(n,o){var a=e.apply(t,r);function i(e){S(a,n,o,i,c,"next",e)}function c(e){S(a,n,o,i,c,"throw",e)}i(void 0)}))}}function _(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,o,a,i,c=[],l=!0,u=!1;try{if(a=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;l=!1}else for(;!(l=(n=a.call(r)).done)&&(c.push(n.value),c.length!==t);l=!0);}catch(e){u=!0,o=e}finally{try{if(!l&&null!=r.return&&(i=r.return(),Object(i)!==i))return}finally{if(u)throw o}}return c}}(e,t)||function(e,t){if(e){if("string"==typeof e)return k(e,t);var r={}.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?k(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function k(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=Array(t);r<t;r++)n[r]=e[r];return n}var N={labelCol:{span:6},wrapperCol:{span:25}},I=function(e){var t=_(g.default.useForm(),1)[0],r=e.editVisible,n=e.setEditVisible,o=e.compileType,a=e.remoteProxy,i=e.createRemoteProxy,u=e.updateRemoteProxy,s=e.setRemoteProxy,f=_(Object(c.useState)("maven"),2),y=f[0],p=f[1];Object(c.useEffect)((function(){a&&t.setFieldsValue({agencyType:a.agencyType,agencyUrl:a.agencyUrl,agencyName:a.agencyName,userName:a.userName,password:a.password})}),[a,r]);var h=function(){var e=L(E().mark((function e(){return E().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:t.validateFields().then(function(){var e=L(E().mark((function e(t){return E().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:a?u(j(j({},a),{},{agencyType:t.agencyType,agencyName:t.agencyName,agencyUrl:t.agencyUrl})).then((function(e){0===e.code&&w()})):i(j(j({},t),{},{agencyType:y,type:1})).then((function(e){0===e.code&&w()}));case 1:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}());case 1:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}(),w=function(){t.resetFields(),s(null),n(!1)},O=l.a.createElement(l.a.Fragment,null,l.a.createElement(d.a,{onClick:w,title:"取消",isMar:!0}),l.a.createElement(d.a,{onClick:h,title:"确定",type:"primary"}));return l.a.createElement(b.a,{visible:r,closable:!1,footer:O,destroyOnClose:!0,width:500,title:"add"===o?"添加来源":"修改来源"},l.a.createElement(g.default,x({},N,{form:t,preserve:!1,layout:"vertical"}),l.a.createElement(g.default.Item,{name:"agencyType",label:"代理类型"},l.a.createElement(v.default,{defaultValue:"maven",options:[{value:"maven",label:"maven"},{value:"npm",label:"npm"},{value:"docker",label:"docker"}],onChange:function(e){p(e)}})),l.a.createElement(g.default.Item,{name:"agencyName",label:"代理来源",rules:[{required:!0,message:"名称必填"}]},l.a.createElement(m.default,{placeholder:"请输入来源名称"})),l.a.createElement(g.default.Item,{name:"agencyUrl",label:"代理地址",rules:[{required:!0,message:"地址必填"}]},l.a.createElement(m.default,{placeholder:"请输入来源地址"}))))},T=r(489);function A(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,o,a,i,c=[],l=!0,u=!1;try{if(a=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;l=!1}else for(;!(l=(n=a.call(r)).done)&&(c.push(n.value),c.length!==t);l=!0);}catch(e){u=!0,o=e}finally{try{if(!l&&null!=r.return&&(i=r.return(),Object(i)!==i))return}finally{if(u)throw o}}return c}}(e,t)||function(e,t){if(e){if("string"==typeof e)return R(e,t);var r={}.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?R(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function R(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=Array(t);r<t;r++)n[r]=e[r];return n}var C=Object(f.observer)((function(e){var t=y.a.createRemoteProxy,r=y.a.updateRemoteProxy,a=y.a.findRemoteProxyList,u=y.a.deleteRemoteProxy,f=y.a.findRepositoryByProxyId,m=y.a.fresh,v=A(Object(c.useState)([]),2),g=v[0],b=v[1],w=A(Object(c.useState)(!1),2),x=w[0],E=w[1],O=A(Object(c.useState)("add"),2),j=O[0],P=O[1],S=A(Object(c.useState)(null),2),L=S[0],_=S[1],k=A(Object(c.useState)("确认删除"),1)[0];Object(c.useEffect)((function(){a({}).then((function(e){0===e.code&&b(e.data)}))}),[m]);var N=[{title:"代理来源",dataIndex:"agencyName",key:"agencyName",width:"20%",ellipsis:!0},{title:"地址",dataIndex:"agencyUrl",key:"agencyUrl",width:"50%",ellipsis:!0},{title:"类型",dataIndex:"agencyType",key:"agencyType",width:"20%",ellipsis:!0},{title:"操作",dataIndex:"action",key:"action",width:"10%",ellipsis:!0,render:function(e,t){return 1===t.type?l.a.createElement("div",{className:"proxy-icon-style"},l.a.createElement(i.a,{code:"hadess_proxy_update",key:"hadess_proxy_update"},l.a.createElement(p.default,{className:"actions-se",onClick:function(){return R(t)}})),l.a.createElement(i.a,{code:"hadess_proxy_delete",key:"hadess_proxy_delete"},l.a.createElement(T.a,{value:t,deleteData:u,title:k,type:"agency",findRepositoryByProxyId:f}))):l.a.createElement(h.a,{className:"proxy-icon-no"})}}],R=function(e){P("update"),E(!0),_(e)};return l.a.createElement("div",{className:"proxy hadess-data-width"},l.a.createElement(n.default,{sm:{span:"24"},md:{span:"24"},lg:{span:"24"},xl:{span:"20",offset:"2"},xxl:{span:"18",offset:"3"}},l.a.createElement("div",{className:"proxy-head-up"},l.a.createElement(s.a,{firstItem:"代理管理"}),l.a.createElement(i.a,{code:"hadess_proxy_add",key:"hadess_proxy_delete"},l.a.createElement(d.a,{type:"primary",title:"添加代理",onClick:function(){return E(!0)}}))),l.a.createElement("div",{className:"proxy-table-style"},l.a.createElement(o.default,{bordered:!1,columns:N,dataSource:g,rowKey:function(e){return e.id},pagination:!1})),l.a.createElement(I,{editVisible:x,setEditVisible:E,compileType:j,createRemoteProxy:t,updateRemoteProxy:r,remoteProxy:L,setRemoteProxy:_})))}))}}]);