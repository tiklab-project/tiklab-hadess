(window.webpackJsonp=window.webpackJsonp||[]).push([[42],{998:function(e,t,r){"use strict";r.r(t),r.d(t,"default",(function(){return h}));r(141);var n=r(47),a=(r(443),r(214)),l=(r(188),r(43)),i=(r(446),r(127)),o=r(0),c=r.n(o),s=r(6),u=r(114);function d(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var r=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=r){var n,a,l,i,o=[],c=!0,s=!1;try{if(l=(r=r.call(e)).next,0===t){if(Object(r)!==r)return;c=!1}else for(;!(c=(n=l.call(r)).done)&&(o.push(n.value),o.length!==t);c=!0);}catch(e){s=!0,a=e}finally{try{if(!c&&null!=r.return&&(i=r.return(),Object(i)!==i))return}finally{if(s)throw a}}return o}}(e,t)||function(e,t){if(e){if("string"==typeof e)return m(e,t);var r={}.toString.call(e).slice(8,-1);return"Object"===r&&e.constructor&&(r=e.constructor.name),"Map"===r||"Set"===r?Array.from(e):"Arguments"===r||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(r)?m(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function m(e,t){(null==t||t>e.length)&&(t=e.length);for(var r=0,n=Array(t);r<t;r++)n[r]=e[r];return n}var f=function(e){var t=e.loginGoRouter,r=Object(s.urlQuery)(window.location.search||window.location.href),o=d(i.default.useForm(),1)[0],m=function(){if(r.redirect){var n=Object(s.parseUserSearchParams)({ticket:Object(s.getUser)().ticket});return/^(((ht|f)tps?):\/\/)?[\w-]+(\.[\w-]+)+([\w.,@?^=%&:/~+#-]*[\w@?^=%&/~+#-])?$/.test(r.redirect)?window.location.href="".concat(r.redirect,"?").concat(n):e.history.push(r.redirect)}return t?e.history.push(t):e.history.push("/")};return c.a.createElement("div",{className:"login"},c.a.createElement("div",{className:"login-header"},c.a.createElement("div",{className:"login-logo"},c.a.createElement("img",{src:s.tiklabImg,alt:"logo",width:32,height:32}),c.a.createElement("div",null,"tiklab")),c.a.createElement("div",{className:"login-main"},c.a.createElement("div",{className:"login-main_title"},"修改密码"),c.a.createElement("div",{className:"login-main_desc"},"当前密码安全性过低，请重新修改密码"),c.a.createElement("div",{className:"login-main_form"},c.a.createElement(i.default,{form:o,onFinish:function(e){Object(u.g)({newPassword:e.password,id:Object(s.getUser)().userId}).then((function(e){0===e.code?m():l.default.error(e.msg)}))},layout:"vertical"},c.a.createElement(i.default.Item,{name:"password",label:"密码",rules:[{required:!0,message:"新密码不能为空!"},{pattern:/^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,}$/,message:"新密码最低8位，由数字和字母组成!"}]},c.a.createElement(a.default.Password,null)),c.a.createElement(i.default.Item,{name:"confirm",label:"确定密码",dependencies:["password"],rules:[{required:!0,message:"请确定密码!"},function(e){var t=e.getFieldValue;return{validator:function(e,r){return r&&t("password")!==r?Promise.reject(new Error("两次密码不一致!")):Promise.resolve()}}}]},c.a.createElement(a.default.Password,null)),c.a.createElement(n.default,{size:"large",type:"primary",htmlType:"submit",style:{width:"100%"}},"确定"))))),c.a.createElement("div",{className:"login-footer"},"© 2020-2024 tiklab.net 版权所有"))};function g(){return(g=Object.assign?Object.assign.bind():function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var n in r)({}).hasOwnProperty.call(r,n)&&(e[n]=r[n])}return e}).apply(null,arguments)}var h=function(e){return c.a.createElement(f,g({},e,{loginGoRouter:"/"}))}}}]);