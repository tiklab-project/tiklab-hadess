(window.webpackJsonp=window.webpackJsonp||[]).push([[4],{1021:function(e,t,n){"use strict";n.r(t),n.d(t,"default",(function(){return Ce}));n(450);var r=n(223),o=(n(198),n(84)),a=(n(40),n(449),n(194),n(3)),i=n(9),c=n(0),l=n.n(c),u=n(17),s=n(1),f=n(4),p=n(167),d=n(997),y=n(698),v=n(5),b=n.n(v),h=n(883),m=n.n(h),g=n(71),O=n(47),w=n(27),j=n(37),E=n(33),x=n(23),S=n(61),C=n(72),k=n(16),L=function(e,t){var n={};for(var r in e)Object.prototype.hasOwnProperty.call(e,r)&&t.indexOf(r)<0&&(n[r]=e[r]);if(null!=e&&"function"==typeof Object.getOwnPropertySymbols){var o=0;for(r=Object.getOwnPropertySymbols(e);o<r.length;o++)t.indexOf(r[o])<0&&Object.prototype.propertyIsEnumerable.call(e,r[o])&&(n[r[o]]=e[r[o]])}return n},P={border:0,background:"transparent",padding:0,lineHeight:"inherit",display:"inline-block"},R=c.forwardRef((function(e,t){var n=e.style,r=e.noStyle,o=e.disabled,i=L(e,["style","noStyle","disabled"]),l={};return r||(l=Object(a.a)({},P)),o&&(l.pointerEvents="none"),l=Object(a.a)(Object(a.a)({},l),n),c.createElement("div",Object(a.a)({role:"button",tabIndex:0,ref:t},i,{onKeyDown:function(e){e.keyCode===k.a.ENTER&&e.preventDefault()},onKeyUp:function(t){var n=t.keyCode,r=e.onClick;n===k.a.ENTER&&r&&r()},style:l}))})),N=n(233),T=n(100),I=n(2),D={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"path",attrs:{d:"M864 170h-60c-4.4 0-8 3.6-8 8v518H310v-73c0-6.7-7.8-10.5-13-6.3l-141.9 112a8 8 0 000 12.6l141.9 112c5.3 4.2 13 .4 13-6.3v-75h498c35.3 0 64-28.7 64-64V178c0-4.4-3.6-8-8-8z"}}]},name:"enter",theme:"outlined"},A=n(11),M=function(e,t){return c.createElement(A.a,Object(I.a)(Object(I.a)({},e),{},{ref:t,icon:D}))},_=c.forwardRef(M);_.displayName="EnterOutlined";var z=_,U=n(425),F=n(26),G=function(e){var t=e.prefixCls,n=e["aria-label"],r=e.className,o=e.style,a=e.direction,i=e.maxLength,l=e.autoSize,u=void 0===l||l,p=e.value,d=e.onSave,y=e.onCancel,v=e.onEnd,h=e.component,m=e.enterIcon,g=void 0===m?c.createElement(z,null):m,O=c.useRef(null),w=c.useRef(!1),j=c.useRef(),E=c.useState(p),x=Object(f.a)(E,2),S=x[0],C=x[1];c.useEffect((function(){C(p)}),[p]),c.useEffect((function(){if(O.current&&O.current.resizableTextArea){var e=O.current.resizableTextArea.textArea;e.focus();var t=e.value.length;e.setSelectionRange(t,t)}}),[]);var L=function(){d(S.trim())},P=h?"".concat(t,"-").concat(h):"",R=b()(t,"".concat(t,"-edit-content"),Object(s.a)({},"".concat(t,"-rtl"),"rtl"===a),r,P);return c.createElement("div",{className:R,style:o},c.createElement(U.a,{ref:O,maxLength:i,value:S,onChange:function(e){var t=e.target;C(t.value.replace(/[\n\r]/g,""))},onKeyDown:function(e){var t=e.keyCode;w.current||(j.current=t)},onKeyUp:function(e){var t=e.keyCode,n=e.ctrlKey,r=e.altKey,o=e.metaKey,a=e.shiftKey;j.current!==t||w.current||n||r||o||a||(t===k.a.ENTER?(L(),null==v||v()):t===k.a.ESC&&y())},onCompositionStart:function(){w.current=!0},onCompositionEnd:function(){w.current=!1},onBlur:function(){L()},"aria-label":n,rows:1,autoSize:u}),null!==g?Object(F.a)(g,{className:"".concat(t,"-edit-content-confirm")}):null)};function H(e,t){return c.useMemo((function(){var n=!!e;return[n,Object(a.a)(Object(a.a)({},t),n&&"object"===Object(i.a)(e)?e:null)]}),[e])}var K=function(e,t){var n=c.useRef(!1);c.useEffect((function(){n.current?e():n.current=!0}),t)},V=function(e,t){var n={};for(var r in e)Object.prototype.hasOwnProperty.call(e,r)&&t.indexOf(r)<0&&(n[r]=e[r]);if(null!=e&&"function"==typeof Object.getOwnPropertySymbols){var o=0;for(r=Object.getOwnPropertySymbols(e);o<r.length;o++)t.indexOf(r[o])<0&&Object.prototype.propertyIsEnumerable.call(e,r[o])&&(n[r[o]]=e[r[o]])}return n},B=c.forwardRef((function(e,t){var n=e.prefixCls,r=e.component,o=void 0===r?"article":r,i=e.className,l=e.setContentRef,f=e.children,p=e.direction,d=V(e,["prefixCls","component","className","setContentRef","children","direction"]),y=c.useContext(S.b),v=y.getPrefixCls,h=y.direction,m=null!=p?p:h,g=t;l&&(Object(u.a)(!1,"Typography","`setContentRef` is deprecated. Please use `ref` instead."),g=Object(x.a)(t,l));var O=v("typography",n),w=b()(O,Object(s.a)({},"".concat(O,"-rtl"),"rtl"===m),i);return c.createElement(o,Object(a.a)({className:w,ref:g},d),f)}));B.displayName="Typography";var W=B;function J(e){var t=Object(i.a)(e);return"string"===t||"number"===t}function Y(e,t){for(var n=0,r=[],o=0;o<e.length;o+=1){if(n===t)return r;var a=e[o],i=n+(J(a)?String(a).length:1);if(i>t){var c=t-n;return r.push(String(a).slice(0,c)),r}r.push(a),n=i}return e}var X=function(e){var t=e.enabledMeasure,n=e.children,r=e.text,o=e.width,i=e.fontSize,l=e.rows,u=e.onEllipsis,s=c.useState([0,0,0]),p=Object(f.a)(s,2),d=Object(f.a)(p[0],3),y=d[0],v=d[1],b=d[2],h=p[1],m=c.useState(0),g=Object(f.a)(m,2),j=g[0],E=g[1],x=c.useState(0),S=Object(f.a)(x,2),C=S[0],k=S[1],L=c.useRef(null),P=c.useRef(null),R=c.useMemo((function(){return Object(O.a)(r)}),[r]),N=c.useMemo((function(){return function(e){var t=0;return e.forEach((function(e){J(e)?t+=String(e).length:t+=1})),t}(R)}),[R]),T=c.useMemo((function(){return t&&3===j?n(Y(R,v),v<N):n(R,!1)}),[t,j,n,R,v,N]);Object(w.a)((function(){t&&o&&i&&N&&(E(1),h([0,Math.ceil(N/2),N]))}),[t,o,i,r,N,l]),Object(w.a)((function(){var e;1===j&&k((null===(e=L.current)||void 0===e?void 0:e.offsetHeight)||0)}),[j]),Object(w.a)((function(){var e,t;if(C)if(1===j)((null===(e=P.current)||void 0===e?void 0:e.offsetHeight)||0)<=l*C?(E(4),u(!1)):E(2);else if(2===j)if(y!==b){var n=(null===(t=P.current)||void 0===t?void 0:t.offsetHeight)||0,r=y,o=b;y===b-1?o=y:n<=l*C?r=v:o=v;var a=Math.ceil((r+o)/2);h([r,a,o])}else E(3),u(!0)}),[j,y,b,l,C]);var I,D,A={width:o,whiteSpace:"normal",margin:0,padding:0},M=function(e,t,n){return c.createElement("span",{"aria-hidden":!0,ref:t,style:Object(a.a)({position:"fixed",display:"block",left:0,top:0,zIndex:-9999,visibility:"hidden",pointerEvents:"none",fontSize:2*Math.floor(i/2)},n)},e)};return c.createElement(c.Fragment,null,T,t&&3!==j&&4!==j&&c.createElement(c.Fragment,null,M("lg",L,{wordBreak:"keep-all",whiteSpace:"nowrap"}),1===j?M(n(R,!1),P,A):(I=P,D=Y(R,v),M(n(D,!0),I,A))))};X.displayName="Ellipsis";var $=X,q=function(e){var t=e.enabledEllipsis,n=e.isEllipsis,r=e.children,o=e.tooltipProps;return(null==o?void 0:o.title)&&t?c.createElement(T.default,Object(a.a)({open:!!n&&void 0},o),r):r};q.displayName="EllipsisTooltip";var Q=q,Z=function(e,t){var n={};for(var r in e)Object.prototype.hasOwnProperty.call(e,r)&&t.indexOf(r)<0&&(n[r]=e[r]);if(null!=e&&"function"==typeof Object.getOwnPropertySymbols){var o=0;for(r=Object.getOwnPropertySymbols(e);o<r.length;o++)t.indexOf(r[o])<0&&Object.prototype.propertyIsEnumerable.call(e,r[o])&&(n[r[o]]=e[r[o]])}return n};function ee(e,t,n){return!0===e||void 0===e?t:e||n&&t}function te(e){return!1===e?[!1,!1]:Array.isArray(e)?e:[e]}var ne=c.forwardRef((function(e,t){var n,r,o,l=e.prefixCls,u=e.className,v=e.style,h=e.type,k=e.disabled,L=e.children,P=e.ellipsis,I=e.editable,D=e.copyable,A=e.component,M=e.title,_=Z(e,["prefixCls","className","style","type","disabled","children","ellipsis","editable","copyable","component","title"]),z=c.useContext(S.b),U=z.getPrefixCls,F=z.direction,V=Object(C.b)("Text")[0],B=c.useRef(null),J=c.useRef(null),Y=U("typography",l),X=Object(E.a)(_,["mark","code","delete","underline","strong","keyboard","italic"]),q=H(I),ne=Object(f.a)(q,2),re=ne[0],oe=ne[1],ae=Object(j.a)(!1,{value:oe.editing}),ie=Object(f.a)(ae,2),ce=ie[0],le=ie[1],ue=oe.triggerType,se=void 0===ue?["icon"]:ue,fe=function(e){var t;e&&(null===(t=oe.onStart)||void 0===t||t.call(oe)),le(e)};K((function(){var e;ce||null===(e=J.current)||void 0===e||e.focus()}),[ce]);var pe=function(e){null==e||e.preventDefault(),fe(!0)},de=H(D),ye=Object(f.a)(de,2),ve=ye[0],be=ye[1],he=c.useState(!1),me=Object(f.a)(he,2),ge=me[0],Oe=me[1],we=c.useRef(),je={};be.format&&(je.format=be.format);var Ee=function(){window.clearTimeout(we.current)},xe=function(e){var t;null==e||e.preventDefault(),null==e||e.stopPropagation(),m()(be.text||String(L)||"",je),Oe(!0),Ee(),we.current=window.setTimeout((function(){Oe(!1)}),3e3),null===(t=be.onCopy)||void 0===t||t.call(be,e)};c.useEffect((function(){return Ee}),[]);var Se=c.useState(!1),Ce=Object(f.a)(Se,2),ke=Ce[0],Le=Ce[1],Pe=c.useState(!1),Re=Object(f.a)(Pe,2),Ne=Re[0],Te=Re[1],Ie=c.useState(!1),De=Object(f.a)(Ie,2),Ae=De[0],Me=De[1],_e=c.useState(!1),ze=Object(f.a)(_e,2),Ue=ze[0],Fe=ze[1],Ge=c.useState(!1),He=Object(f.a)(Ge,2),Ke=He[0],Ve=He[1],Be=c.useState(!0),We=Object(f.a)(Be,2),Je=We[0],Ye=We[1],Xe=H(P,{expandable:!1}),$e=Object(f.a)(Xe,2),qe=$e[0],Qe=$e[1],Ze=qe&&!Ae,et=Qe.rows,tt=void 0===et?1:et,nt=c.useMemo((function(){return!Ze||void 0!==Qe.suffix||Qe.onEllipsis||Qe.expandable||re||ve}),[Ze,Qe,re,ve]);Object(w.a)((function(){qe&&!nt&&(Le(Object(N.a)("webkitLineClamp")),Te(Object(N.a)("textOverflow")))}),[nt,qe]);var rt=c.useMemo((function(){return!nt&&(1===tt?Ne:ke)}),[nt,Ne,ke]),ot=Ze&&(rt?Ke:Ue),at=Ze&&1===tt&&rt,it=Ze&&tt>1&&rt,ct=function(e){var t;Me(!0),null===(t=Qe.onExpand)||void 0===t||t.call(Qe,e)},lt=c.useState(0),ut=Object(f.a)(lt,2),st=ut[0],ft=ut[1],pt=c.useState(0),dt=Object(f.a)(pt,2),yt=dt[0],vt=dt[1],bt=function(e){var t;Fe(e),Ue!==e&&(null===(t=Qe.onEllipsis)||void 0===t||t.call(Qe,e))};c.useEffect((function(){var e=B.current;if(qe&&rt&&e){var t=it?e.offsetHeight<e.scrollHeight:e.offsetWidth<e.scrollWidth;Ke!==t&&Ve(t)}}),[qe,rt,L,it,Je]),c.useEffect((function(){var e=B.current;if("undefined"!=typeof IntersectionObserver&&e&&rt&&Ze){var t=new IntersectionObserver((function(){Ye(!!e.offsetParent)}));return t.observe(e),function(){t.disconnect()}}}),[rt,Ze]);var ht={};ht=!0===Qe.tooltip?{title:null!==(n=oe.text)&&void 0!==n?n:L}:c.isValidElement(Qe.tooltip)?{title:Qe.tooltip}:"object"===Object(i.a)(Qe.tooltip)?Object(a.a)({title:null!==(r=oe.text)&&void 0!==r?r:L},Qe.tooltip):{title:Qe.tooltip};var mt=c.useMemo((function(){var e=function(e){return["string","number"].includes(Object(i.a)(e))};if(qe&&!rt)return e(oe.text)?oe.text:e(L)?L:e(M)?M:e(ht.title)?ht.title:void 0}),[qe,rt,M,ht.title,ot]);if(ce)return c.createElement(G,{value:null!==(o=oe.text)&&void 0!==o?o:"string"==typeof L?L:"",onSave:function(e){var t;null===(t=oe.onChange)||void 0===t||t.call(oe,e),fe(!1)},onCancel:function(){var e;null===(e=oe.onCancel)||void 0===e||e.call(oe),fe(!1)},onEnd:oe.onEnd,prefixCls:Y,className:u,style:v,direction:F,component:A,maxLength:oe.maxLength,autoSize:oe.autoSize,enterIcon:oe.enterIcon});var gt=function(){if(re){var e=oe.icon,t=oe.tooltip,n=Object(O.a)(t)[0]||V.edit,r="string"==typeof n?n:"";return se.includes("icon")?c.createElement(T.default,{key:"edit",title:!1===t?"":n},c.createElement(R,{ref:J,className:"".concat(Y,"-edit"),onClick:pe,"aria-label":r},e||c.createElement(y.default,{role:"button"}))):null}},Ot=function(){if(ve){var e=be.tooltips,t=be.icon,n=te(e),r=te(t),o=ge?ee(n[1],V.copied):ee(n[0],V.copy),a=ge?V.copied:V.copy,i="string"==typeof o?o:a;return c.createElement(T.default,{key:"copy",title:o},c.createElement(R,{className:b()("".concat(Y,"-copy"),ge&&"".concat(Y,"-copy-success")),onClick:xe,"aria-label":i},ge?ee(r[1],c.createElement(p.a,null),!0):ee(r[0],c.createElement(d.a,null),!0)))}};return c.createElement(g.a,{onResize:function(e,t){var n,r=e.offsetWidth;ft(r),vt(parseInt(null===(n=window.getComputedStyle)||void 0===n?void 0:n.call(window,t).fontSize,10)||0)},disabled:!Ze||rt},(function(n){return c.createElement(Q,{tooltipProps:ht,enabledEllipsis:Ze,isEllipsis:ot},c.createElement(W,Object(a.a)({className:b()(Object(s.a)(Object(s.a)(Object(s.a)(Object(s.a)(Object(s.a)(Object(s.a)({},"".concat(Y,"-").concat(h),h),"".concat(Y,"-disabled"),k),"".concat(Y,"-ellipsis"),qe),"".concat(Y,"-single-line"),Ze&&1===tt),"".concat(Y,"-ellipsis-single-line"),at),"".concat(Y,"-ellipsis-multiple-line"),it),u),prefixCls:l,style:Object(a.a)(Object(a.a)({},v),{WebkitLineClamp:it?tt:void 0}),component:A,ref:Object(x.a)(n,B,t),direction:F,onClick:se.includes("text")?pe:void 0,"aria-label":null==mt?void 0:mt.toString(),title:M},X),c.createElement($,{enabledMeasure:Ze&&!rt,text:L,rows:tt,width:st,fontSize:yt,onEllipsis:bt},(function(t,n){var r=t;return t.length&&n&&mt&&(r=c.createElement("span",{key:"show-content","aria-hidden":!0},r)),function(e,t){var n=e.mark,r=e.code,o=e.underline,a=e.delete,i=e.strong,l=e.keyboard,u=e.italic,s=t;function f(e,t){e&&(s=c.createElement(t,{},s))}return f(i,"strong"),f(o,"u"),f(a,"del"),f(r,"code"),f(n,"mark"),f(l,"kbd"),f(u,"i"),s}(e,c.createElement(c.Fragment,null,r,function(e){return[e&&c.createElement("span",{"aria-hidden":!0,key:"ellipsis"},"..."),Qe.suffix,(t=e,[t&&(r=Qe.expandable,o=Qe.symbol,r?(n=o||V.expand,c.createElement("a",{key:"expand",className:"".concat(Y,"-expand"),onClick:ct,"aria-label":V.expand},n)):null),gt(),Ot()])];var t,n,r,o}(n)))}))))}))})),re=function(e,t){var n={};for(var r in e)Object.prototype.hasOwnProperty.call(e,r)&&t.indexOf(r)<0&&(n[r]=e[r]);if(null!=e&&"function"==typeof Object.getOwnPropertySymbols){var o=0;for(r=Object.getOwnPropertySymbols(e);o<r.length;o++)t.indexOf(r[o])<0&&Object.prototype.propertyIsEnumerable.call(e,r[o])&&(n[r[o]]=e[r[o]])}return n},oe=c.forwardRef((function(e,t){var n=e.ellipsis,r=e.rel,o=re(e,["ellipsis","rel"]);Object(u.a)("object"!==Object(i.a)(n),"Typography.Link","`ellipsis` only supports boolean value.");var l=Object(a.a)(Object(a.a)({},o),{rel:void 0===r&&"_blank"===o.target?"noopener noreferrer":r});return delete l.navigate,c.createElement(ne,Object(a.a)({},l,{ref:t,ellipsis:!!n,component:"a"}))})),ae=c.forwardRef((function(e,t){return c.createElement(ne,Object(a.a)({ref:t},e,{component:"div"}))})),ie=function(e,t){var n={};for(var r in e)Object.prototype.hasOwnProperty.call(e,r)&&t.indexOf(r)<0&&(n[r]=e[r]);if(null!=e&&"function"==typeof Object.getOwnPropertySymbols){var o=0;for(r=Object.getOwnPropertySymbols(e);o<r.length;o++)t.indexOf(r[o])<0&&Object.prototype.propertyIsEnumerable.call(e,r[o])&&(n[r[o]]=e[r[o]])}return n},ce=function(e,t){var n=e.ellipsis,r=ie(e,["ellipsis"]),o=c.useMemo((function(){return n&&"object"===Object(i.a)(n)?Object(E.a)(n,["expandable","rows"]):n}),[n]);return Object(u.a)("object"!==Object(i.a)(n)||!n||!("expandable"in n)&&!("rows"in n),"Typography.Text","`ellipsis` do not support `expandable` or `rows` props."),c.createElement(ne,Object(a.a)({ref:t},r,{ellipsis:o,component:"span"}))},le=c.forwardRef(ce),ue=n(38),se=function(e,t){var n={};for(var r in e)Object.prototype.hasOwnProperty.call(e,r)&&t.indexOf(r)<0&&(n[r]=e[r]);if(null!=e&&"function"==typeof Object.getOwnPropertySymbols){var o=0;for(r=Object.getOwnPropertySymbols(e);o<r.length;o++)t.indexOf(r[o])<0&&Object.prototype.propertyIsEnumerable.call(e,r[o])&&(n[r[o]]=e[r[o]])}return n},fe=Object(ue.b)(1,2,3,4,5),pe=c.forwardRef((function(e,t){var n,r=e.level,o=void 0===r?1:r,i=se(e,["level"]);return fe.includes(o)?n="h".concat(o):(Object(u.a)(!1,"Typography.Title","Title only accept `1 | 2 | 3 | 4 | 5` as `level` value. And `5` need 4.6.0+ version."),n="h1"),c.createElement(ne,Object(a.a)({ref:t},i,{component:n}))})),de=W;de.Text=le,de.Link=oe,de.Title=pe,de.Paragraph=ae;var ye=de,ve=n(69);Object(ve.a)(".library-details .library-details-not {\n  margin-top: 10%;\n}");var be=n(68),he=n(665),me=n(790),ge=n(226);function Oe(e){return(Oe="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e})(e)}function we(){return(we=Object.assign?Object.assign.bind():function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var r in n)({}).hasOwnProperty.call(n,r)&&(e[r]=n[r])}return e}).apply(null,arguments)}function je(){/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */je=function(){return t};var e,t={},n=Object.prototype,r=n.hasOwnProperty,o=Object.defineProperty||function(e,t,n){e[t]=n.value},a="function"==typeof Symbol?Symbol:{},i=a.iterator||"@@iterator",c=a.asyncIterator||"@@asyncIterator",l=a.toStringTag||"@@toStringTag";function u(e,t,n){return Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}),e[t]}try{u({},"")}catch(e){u=function(e,t,n){return e[t]=n}}function s(e,t,n,r){var a=t&&t.prototype instanceof b?t:b,i=Object.create(a.prototype),c=new P(r||[]);return o(i,"_invoke",{value:S(e,n,c)}),i}function f(e,t,n){try{return{type:"normal",arg:e.call(t,n)}}catch(e){return{type:"throw",arg:e}}}t.wrap=s;var p="suspendedStart",d="executing",y="completed",v={};function b(){}function h(){}function m(){}var g={};u(g,i,(function(){return this}));var O=Object.getPrototypeOf,w=O&&O(O(R([])));w&&w!==n&&r.call(w,i)&&(g=w);var j=m.prototype=b.prototype=Object.create(g);function E(e){["next","throw","return"].forEach((function(t){u(e,t,(function(e){return this._invoke(t,e)}))}))}function x(e,t){function n(o,a,i,c){var l=f(e[o],e,a);if("throw"!==l.type){var u=l.arg,s=u.value;return s&&"object"==Oe(s)&&r.call(s,"__await")?t.resolve(s.__await).then((function(e){n("next",e,i,c)}),(function(e){n("throw",e,i,c)})):t.resolve(s).then((function(e){u.value=e,i(u)}),(function(e){return n("throw",e,i,c)}))}c(l.arg)}var a;o(this,"_invoke",{value:function(e,r){function o(){return new t((function(t,o){n(e,r,t,o)}))}return a=a?a.then(o,o):o()}})}function S(t,n,r){var o=p;return function(a,i){if(o===d)throw Error("Generator is already running");if(o===y){if("throw"===a)throw i;return{value:e,done:!0}}for(r.method=a,r.arg=i;;){var c=r.delegate;if(c){var l=C(c,r);if(l){if(l===v)continue;return l}}if("next"===r.method)r.sent=r._sent=r.arg;else if("throw"===r.method){if(o===p)throw o=y,r.arg;r.dispatchException(r.arg)}else"return"===r.method&&r.abrupt("return",r.arg);o=d;var u=f(t,n,r);if("normal"===u.type){if(o=r.done?y:"suspendedYield",u.arg===v)continue;return{value:u.arg,done:r.done}}"throw"===u.type&&(o=y,r.method="throw",r.arg=u.arg)}}}function C(t,n){var r=n.method,o=t.iterator[r];if(o===e)return n.delegate=null,"throw"===r&&t.iterator.return&&(n.method="return",n.arg=e,C(t,n),"throw"===n.method)||"return"!==r&&(n.method="throw",n.arg=new TypeError("The iterator does not provide a '"+r+"' method")),v;var a=f(o,t.iterator,n.arg);if("throw"===a.type)return n.method="throw",n.arg=a.arg,n.delegate=null,v;var i=a.arg;return i?i.done?(n[t.resultName]=i.value,n.next=t.nextLoc,"return"!==n.method&&(n.method="next",n.arg=e),n.delegate=null,v):i:(n.method="throw",n.arg=new TypeError("iterator result is not an object"),n.delegate=null,v)}function k(e){var t={tryLoc:e[0]};1 in e&&(t.catchLoc=e[1]),2 in e&&(t.finallyLoc=e[2],t.afterLoc=e[3]),this.tryEntries.push(t)}function L(e){var t=e.completion||{};t.type="normal",delete t.arg,e.completion=t}function P(e){this.tryEntries=[{tryLoc:"root"}],e.forEach(k,this),this.reset(!0)}function R(t){if(t||""===t){var n=t[i];if(n)return n.call(t);if("function"==typeof t.next)return t;if(!isNaN(t.length)){var o=-1,a=function n(){for(;++o<t.length;)if(r.call(t,o))return n.value=t[o],n.done=!1,n;return n.value=e,n.done=!0,n};return a.next=a}}throw new TypeError(Oe(t)+" is not iterable")}return h.prototype=m,o(j,"constructor",{value:m,configurable:!0}),o(m,"constructor",{value:h,configurable:!0}),h.displayName=u(m,l,"GeneratorFunction"),t.isGeneratorFunction=function(e){var t="function"==typeof e&&e.constructor;return!!t&&(t===h||"GeneratorFunction"===(t.displayName||t.name))},t.mark=function(e){return Object.setPrototypeOf?Object.setPrototypeOf(e,m):(e.__proto__=m,u(e,l,"GeneratorFunction")),e.prototype=Object.create(j),e},t.awrap=function(e){return{__await:e}},E(x.prototype),u(x.prototype,c,(function(){return this})),t.AsyncIterator=x,t.async=function(e,n,r,o,a){void 0===a&&(a=Promise);var i=new x(s(e,n,r,o),a);return t.isGeneratorFunction(n)?i:i.next().then((function(e){return e.done?e.value:i.next()}))},E(j),u(j,l,"Generator"),u(j,i,(function(){return this})),u(j,"toString",(function(){return"[object Generator]"})),t.keys=function(e){var t=Object(e),n=[];for(var r in t)n.push(r);return n.reverse(),function e(){for(;n.length;){var r=n.pop();if(r in t)return e.value=r,e.done=!1,e}return e.done=!0,e}},t.values=R,P.prototype={constructor:P,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=e,this.done=!1,this.delegate=null,this.method="next",this.arg=e,this.tryEntries.forEach(L),!t)for(var n in this)"t"===n.charAt(0)&&r.call(this,n)&&!isNaN(+n.slice(1))&&(this[n]=e)},stop:function(){this.done=!0;var e=this.tryEntries[0].completion;if("throw"===e.type)throw e.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var n=this;function o(r,o){return c.type="throw",c.arg=t,n.next=r,o&&(n.method="next",n.arg=e),!!o}for(var a=this.tryEntries.length-1;a>=0;--a){var i=this.tryEntries[a],c=i.completion;if("root"===i.tryLoc)return o("end");if(i.tryLoc<=this.prev){var l=r.call(i,"catchLoc"),u=r.call(i,"finallyLoc");if(l&&u){if(this.prev<i.catchLoc)return o(i.catchLoc,!0);if(this.prev<i.finallyLoc)return o(i.finallyLoc)}else if(l){if(this.prev<i.catchLoc)return o(i.catchLoc,!0)}else{if(!u)throw Error("try statement without catch or finally");if(this.prev<i.finallyLoc)return o(i.finallyLoc)}}}},abrupt:function(e,t){for(var n=this.tryEntries.length-1;n>=0;--n){var o=this.tryEntries[n];if(o.tryLoc<=this.prev&&r.call(o,"finallyLoc")&&this.prev<o.finallyLoc){var a=o;break}}a&&("break"===e||"continue"===e)&&a.tryLoc<=t&&t<=a.finallyLoc&&(a=null);var i=a?a.completion:{};return i.type=e,i.arg=t,a?(this.method="next",this.next=a.finallyLoc,v):this.complete(i)},complete:function(e,t){if("throw"===e.type)throw e.arg;return"break"===e.type||"continue"===e.type?this.next=e.arg:"return"===e.type?(this.rval=this.arg=e.arg,this.method="return",this.next="end"):"normal"===e.type&&t&&(this.next=t),v},finish:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var n=this.tryEntries[t];if(n.finallyLoc===e)return this.complete(n.completion,n.afterLoc),L(n),v}},catch:function(e){for(var t=this.tryEntries.length-1;t>=0;--t){var n=this.tryEntries[t];if(n.tryLoc===e){var r=n.completion;if("throw"===r.type){var o=r.arg;L(n)}return o}}throw Error("illegal catch attempt")},delegateYield:function(t,n,r){return this.delegate={iterator:R(t),resultName:n,nextLoc:r},"next"===this.method&&(this.arg=e),v}},t}function Ee(e,t,n,r,o,a,i){try{var c=e[a](i),l=c.value}catch(e){return void n(e)}c.done?t(l):Promise.resolve(l).then(r,o)}function xe(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=n){var r,o,a,i,c=[],l=!0,u=!1;try{if(a=(n=n.call(e)).next,0===t){if(Object(n)!==n)return;l=!1}else for(;!(l=(r=a.call(n)).done)&&(c.push(r.value),c.length!==t);l=!0);}catch(e){u=!0,o=e}finally{try{if(!l&&null!=n.return&&(i=n.return(),Object(i)!==i))return}finally{if(u)throw o}}return c}}(e,t)||function(e,t){if(e){if("string"==typeof e)return Se(e,t);var n={}.toString.call(e).slice(8,-1);return"Object"===n&&e.constructor&&(n=e.constructor.name),"Map"===n||"Set"===n?Array.from(e):"Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?Se(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function Se(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,r=Array(t);n<t;n++)r[n]=e[n];return r}var Ce=Object(be.observer)((function(e){var t=e.match.params,n=e.publicState,a=ge.a.findVersionByLibraryId,i=ge.a.libraryVersionData,u=ge.a.detailsLoad,s=ge.a.deleteLibrary,f=ge.a.refresh,p=xe(Object(c.useState)(!0),2),d=p[0],y=p[1];Object(c.useEffect)((function(){v()}),[f]);var v=function(){var e,n=(e=je().mark((function e(n){return je().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:a(t.libraryId,n).then((function(e){57404===e.code&&y(!1)}));case 1:case"end":return e.stop()}}),e)})),function(){var t=this,n=arguments;return new Promise((function(r,o){var a=e.apply(t,n);function i(e){Ee(a,r,o,i,c,"next",e)}function c(e){Ee(a,r,o,i,c,"throw",e)}i(void 0)}))});return function(e){return n.apply(this,arguments)}}();return l.a.createElement("div",{className:"library-details hadess-data-width"},l.a.createElement(r.default,{sm:{span:"24"},md:{span:"24"},lg:{span:"24"},xl:{span:"20",offset:"2"},xxl:{span:"18",offset:"3"}},l.a.createElement(he.a,{className:"add-title",firstItem:"制品详情",goBack:function(){e.history.go(-1)}}),d?l.a.createElement(me.a,we({},e,{libraryVersionData:i,cuteVersion:v,detailsLoad:u,publicState:n})):l.a.createElement("div",{className:"library-details-not"},l.a.createElement(o.default,{description:l.a.createElement(ye.Text,null,"制品文件不存在 ",l.a.createElement("a",{onClick:function(){s(t.libraryId).then((function(n){0===n.code&&e.history.push("/repository/".concat(t.id,"/library"))}))}},"删除"))}))))}))},883:function(e,t,n){"use strict";var r=n(884),o={"text/plain":"Text","text/html":"Url",default:"Text"};e.exports=function(e,t){var n,a,i,c,l,u,s=!1;t||(t={}),n=t.debug||!1;try{if(i=r(),c=document.createRange(),l=document.getSelection(),(u=document.createElement("span")).textContent=e,u.ariaHidden="true",u.style.all="unset",u.style.position="fixed",u.style.top=0,u.style.clip="rect(0, 0, 0, 0)",u.style.whiteSpace="pre",u.style.webkitUserSelect="text",u.style.MozUserSelect="text",u.style.msUserSelect="text",u.style.userSelect="text",u.addEventListener("copy",(function(r){if(r.stopPropagation(),t.format)if(r.preventDefault(),void 0===r.clipboardData){n&&console.warn("unable to use e.clipboardData"),n&&console.warn("trying IE specific stuff"),window.clipboardData.clearData();var a=o[t.format]||o.default;window.clipboardData.setData(a,e)}else r.clipboardData.clearData(),r.clipboardData.setData(t.format,e);t.onCopy&&(r.preventDefault(),t.onCopy(r.clipboardData))})),document.body.appendChild(u),c.selectNodeContents(u),l.addRange(c),!document.execCommand("copy"))throw new Error("copy command was unsuccessful");s=!0}catch(r){n&&console.error("unable to copy using execCommand: ",r),n&&console.warn("trying IE specific stuff");try{window.clipboardData.setData(t.format||"text",e),t.onCopy&&t.onCopy(window.clipboardData),s=!0}catch(r){n&&console.error("unable to copy using clipboardData: ",r),n&&console.error("falling back to prompt"),a=function(e){var t=(/mac os x/i.test(navigator.userAgent)?"⌘":"Ctrl")+"+C";return e.replace(/#{\s*key\s*}/g,t)}("message"in t?t.message:"Copy to clipboard: #{key}, Enter"),window.prompt(a,e)}}finally{l&&("function"==typeof l.removeRange?l.removeRange(c):l.removeAllRanges()),u&&document.body.removeChild(u),i()}return s}},884:function(e,t){e.exports=function(){var e=document.getSelection();if(!e.rangeCount)return function(){};for(var t=document.activeElement,n=[],r=0;r<e.rangeCount;r++)n.push(e.getRangeAt(r));switch(t.tagName.toUpperCase()){case"INPUT":case"TEXTAREA":t.blur();break;default:t=null}return e.removeAllRanges(),function(){"Caret"===e.type&&e.removeAllRanges(),e.rangeCount||n.forEach((function(t){e.addRange(t)})),t&&t.focus()}}}}]);