(window.webpackJsonp=window.webpackJsonp||[]).push([[33],{1036:function(e,t,a){"use strict";a.r(t),a.d(t,"default",(function(){return T}));a(450);var n=a(223),r=(a(663),a(662)),l=(a(449),a(222)),o=(a(708),a(696)),c=a.n(o),i=a(0),s=a.n(i),u=a(665),d=a(664),f=a(671),m=a(725),h=a(69);Object(h.a)(".scanHole .scanHole-up {\n  display: flex;\n  flex-direction: row;\n  justify-content: space-between;\n  align-items: center;\n}\n.scanHole .scanHole-find {\n  display: flex;\n  gap: 20px;\n  margin-top: 20px;\n  margin-left: 10px;\n}\n.scanHole .scanHole-table {\n  padding-top: var(--xpack-table-top);\n}");a(227);var p=a(148),b=a(85),y=a(152);Object(h.a)(".hole-add .hole-add-style {\n  display: flex;\n}\n.hole-add .hole-add-style .hole-add-title {\n  width: 100px;\n  color: #999999;\n}\n\n.hole-add-top .hole-add-search {\n  display: flex;\n  gap: 20px;\n}\n\n.hole-table-top {\n  margin-top: 20px;\n}\n\n.hole-table-footer {\n  display: flex;\n  justify-content: end;\n  gap: 10px;\n}\n\n.text-red {\n  color: red;\n}\n\n.text-dired {\n  color: #df5000;\n}\n\n.text-yellow {\n  color: #ee9900;\n}\n\n.text-blue {\n  color: #1890ff;\n}");var v=a(736),g=a(673);function S(e){return(S="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function j(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,n)}return a}function O(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?j(Object(a),!0).forEach((function(t){E(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):j(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}function E(e,t,a){return(t=function(e){var t=function(e,t){if("object"!=S(e)||!e)return e;var a=e[Symbol.toPrimitive];if(void 0!==a){var n=a.call(e,t||"default");if("object"!=S(n))return n;throw new TypeError("@@toPrimitive must return a primitive value.")}return("string"===t?String:Number)(e)}(e,"string");return"symbol"==S(t)?t:t+""}(t))in e?Object.defineProperty(e,t,{value:a,enumerable:!0,configurable:!0,writable:!0}):e[t]=a,e}function x(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var a=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=a){var n,r,l,o,c=[],i=!0,s=!1;try{if(l=(a=a.call(e)).next,0===t){if(Object(a)!==a)return;i=!1}else for(;!(i=(n=l.call(a)).done)&&(c.push(n.value),c.length!==t);i=!0);}catch(e){s=!0,r=e}finally{try{if(!i&&null!=a.return&&(o=a.return(),Object(o)!==o))return}finally{if(s)throw r}}return c}}(e,t)||function(e,t){if(e){if("string"==typeof e)return w(e,t);var a={}.toString.call(e).slice(8,-1);return"Object"===a&&e.constructor&&(a=e.constructor.name),"Map"===a||"Set"===a?Array.from(e):"Arguments"===a||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(a)?w(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function w(e,t){(null==t||t>e.length)&&(t=e.length);for(var a=0,n=Array(t);a<t;a++)n[a]=e[a];return n}var N=[{key:1,value:"严重漏洞"},{key:2,value:"高危漏洞"},{key:3,value:"中危漏洞"},{key:4,value:"低危漏洞"}],k=function(e){var t=e.visible,a=e.setVisible,n=e.schemeId,o=e.scanScheme,c=m.a.findNotScanHolePage,u=m.a.createScanSchemeHole,h=m.a.fresh,S=x(Object(i.useState)(1),2),j=S[0],E=S[1],w=x(Object(i.useState)(),2),k=w[0],I=w[1],P=x(Object(i.useState)(15),1)[0],H=x(Object(i.useState)(),2),C=H[0],L=H[1],A=x(Object(i.useState)([]),2),_=A[0],T=A[1],D=x(Object(i.useState)([]),2),V=D[0],z=D[1],R=x(Object(i.useState)(null),2),M=R[0],B=R[1],J=x(Object(i.useState)([]),2),K=J[0],U=J[1],$=x(Object(i.useState)(!1),2),q=$[0],F=$[1],G=x(Object(i.useState)(),2),Q=G[0],W=G[1];Object(i.useEffect)((function(){t&&Y(1),"java"===o.language&&W("java")}),[h,t]);var X=[{title:"漏洞名称",dataIndex:"holeName",key:"holeName",width:"30%",ellipsis:!0},{title:"漏洞编号",dataIndex:"holeNumber",key:"holeNumber",width:"40%",ellipsis:!0},{title:"产品",dataIndex:"product",key:"product",width:"20%",ellipsis:!0},{title:"等级",dataIndex:"holeLevel",key:"holeLevel",width:"10%",ellipsis:!0,render:function(e){return 1===e&&s.a.createElement("div",{className:"text-red"},"严重漏洞")||2===e&&s.a.createElement("div",{className:"text-dired"},"高危漏洞")||3===e&&s.a.createElement("div",{className:"text-yellow"},"中危漏洞")||4===e&&s.a.createElement("div",{className:"text-blue"},"低危漏洞")}}],Y=function(e,t){c({scanSchemeId:n,holeLevelList:t,language:Q,holeName:M,pageParam:{currentPage:e,pageSize:P}}).then((function(e){0===e.code&&(z(e.data.dataList),I(e.data.totalPage),L(e.data.totalRecord))}))},Z=function(){a(!1),B(null),E(1),U([]),W(null)},ee={onChange:function(e,t){T(e)}},te=s.a.createElement("div",{className:"hole-table-footer"},s.a.createElement(d.a,{onClick:Z,title:"取消"}),s.a.createElement(d.a,{onClick:function(){u({scanSchemeId:n,scanHoleIdList:_}).then((function(e){0===e.code&&Z()}))},title:"确定",type:"primary",isMar:!0}));return s.a.createElement(p.default,{title:"添加漏洞",placement:"right",closable:!1,width:"60%",onClose:Z,visible:t,footer:te,extra:s.a.createElement(b.a,{style:{cursor:"pointer"},onClick:Z})},s.a.createElement("div",{className:"hole-add-top"},s.a.createElement("div",{className:"hole-add-search"},s.a.createElement(v.a,{visible:q,setVisible:F,dataList:N,setNav:function(e){E(1),U(e),Y(1,e)},nav:K,title:"等级"}),s.a.createElement(l.default,{placeholder:"漏洞名称",onChange:function(e){B(e.target.value)},value:M,onPressEnter:function(){E(1),Y(1,K)},size:"middle",style:{width:200},prefix:s.a.createElement(y.default,null)})),s.a.createElement("div",{className:"hole-table-top"},s.a.createElement(r.default,{rowSelection:O({type:"checkbox"},ee),dataSource:V,rowKey:function(e){return e.id},columns:X,pagination:!1,locale:{emptyText:s.a.createElement(g.a,{title:"暂无方案"})}}),s.a.createElement(f.a,{pageCurrent:j,changPage:function(e){E(e),Y(e,K)},totalPage:k,totalRecord:C,refresh:function(){Y(j,K)}}))))},I=a(793),P=a(722),H=a(68),C=a(679);function L(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var a=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=a){var n,r,l,o,c=[],i=!0,s=!1;try{if(l=(a=a.call(e)).next,0===t){if(Object(a)!==a)return;i=!1}else for(;!(i=(n=l.call(a)).done)&&(c.push(n.value),c.length!==t);i=!0);}catch(e){s=!0,r=e}finally{try{if(!i&&null!=a.return&&(o=a.return(),Object(o)!==o))return}finally{if(s)throw r}}return c}}(e,t)||function(e,t){if(e){if("string"==typeof e)return A(e,t);var a={}.toString.call(e).slice(8,-1);return"Object"===a&&e.constructor&&(a=e.constructor.name),"Map"===a||"Set"===a?Array.from(e):"Arguments"===a||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(a)?A(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function A(e,t){(null==t||t>e.length)&&(t=e.length);for(var a=0,n=Array(t);a<t;a++)n[a]=e[a];return n}var _=[{key:1,value:"严重漏洞"},{key:2,value:"高危漏洞"},{key:3,value:"中危漏洞"},{key:4,value:"低危漏洞"}],T=Object(H.observer)((function(e){var t=e.match.params,a=m.a.findSchemeHolePage,o=m.a.deleteScanSchemeHoleByCond,h=m.a.fresh,p=P.a.findScanScheme,b=L(Object(i.useState)(""),2),S=b[0],j=b[1],O=L(Object(i.useState)([]),2),E=O[0],x=O[1],w=L(Object(i.useState)(1),2),N=w[0],H=w[1],A=L(Object(i.useState)(),2),T=A[0],D=A[1],V=L(Object(i.useState)(15),1)[0],z=L(Object(i.useState)(),2),R=z[0],M=z[1],B=L(Object(i.useState)(!1),2),J=B[0],K=B[1],U=L(Object(i.useState)(!1),2),$=U[0],q=U[1],F=L(Object(i.useState)(""),2),G=F[0],Q=F[1],W=L(Object(i.useState)([]),2),X=W[0],Y=W[1],Z=L(Object(i.useState)([]),2),ee=Z[0],te=Z[1],ae=L(Object(i.useState)(!1),2),ne=ae[0],re=ae[1],le=L(Object(i.useState)(),2),oe=le[0],ce=le[1];Object(i.useEffect)((function(){ue(1),p(t.schemeId).then((function(e){j(e.data),ie(e.data)}))}),[h]);var ie=function(e){"default"===e.schemeType?Y(se):Y(se.concat({title:"操作",dataIndex:"action",key:"action",width:"10%",render:function(e,a){return s.a.createElement(c.a,{code:"hadess_scan_hole_delete",key:"hadess_scan_hole_delete"},s.a.createElement(C.a,{value:a,deleteData:o,title:"确认删除",type:"schemeHole",schemeId:t.schemeId}))}}))},se=[{title:"漏洞名称",dataIndex:"holeName",key:"holeName",width:"30%",ellipsis:!0,render:function(e,t){return s.a.createElement("div",{className:"text-color",onClick:function(){return de(t)}},e)}},{title:"漏洞编号",dataIndex:"holeNumber",key:"holeNumber",width:"30%",ellipsis:!0},{title:"漏洞等级",dataIndex:"holeLevel",key:"holeLevel",width:"10%",ellipsis:!0,render:function(e){return 1===e&&s.a.createElement("div",{className:"text-red"},"严重漏洞")||2===e&&s.a.createElement("div",{className:"text-dired"},"高危漏洞")||3===e&&s.a.createElement("div",{className:"text-yellow"},"中危漏洞")||4===e&&s.a.createElement("div",{className:"text-blue"},"低危漏洞")}},{title:"创建时间",dataIndex:"createTime",key:"createTime",width:"15%",ellipsis:!0}],ue=function(e,n){a({scanSchemeId:t.schemeId,holeLevelList:n,holeName:oe,pageParam:{currentPage:e,pageSize:V}}).then((function(e){0===e.code&&(x(e.data.dataList),D(e.data.totalPage),M(e.data.totalRecord))}))},de=function(e){Q(e),q(!0)};return s.a.createElement("div",{className:"scanHole hadess-data-width"},s.a.createElement(n.default,{sm:{span:"24"},md:{span:"24"},lg:{span:"24"},xl:{span:"22",offset:"1"},xxl:{span:"18",offset:"3"}},s.a.createElement("div",{className:"scanHole-up"},s.a.createElement(u.a,{firstItem:"方案漏洞",goBack:function(){e.history.go(-1)}}),S&&"default"!==S.schemeType&&s.a.createElement(c.a,{code:"hadess_scan_hole_add",key:"system_scan_hole_add"},s.a.createElement(d.a,{type:"primary",title:"添加漏洞",onClick:function(){K(!0)}}))),s.a.createElement("div",{className:"scanHole-find"},s.a.createElement(v.a,{visible:ne,setVisible:re,dataList:_,setNav:function(e){H(1),te(e),ue(1,e)},nav:ee,title:"等级"}),s.a.createElement(l.default,{placeholder:"漏洞名称",value:oe,onChange:function(e){ce(e.target.value)},onPressEnter:function(){H(1),ue(1)},size:"middle",style:{width:190},prefix:s.a.createElement(y.default,null)})),s.a.createElement("div",{className:"scanHole-table"},s.a.createElement(r.default,{bordered:!1,columns:X,dataSource:E,rowKey:function(e){return e.id},pagination:!1,locale:{emptyText:s.a.createElement(g.a,{title:"暂无方案"})}}),s.a.createElement(f.a,{pageCurrent:N,changPage:function(e){H(e),ue(e)},totalPage:T,totalRecord:R,refresh:function(){ue(N)}}))),s.a.createElement(k,{visible:J,setVisible:K,schemeId:t.schemeId,scanScheme:S}),s.a.createElement(I.a,{visible:$,setVisible:q,hole:G}))}))}}]);