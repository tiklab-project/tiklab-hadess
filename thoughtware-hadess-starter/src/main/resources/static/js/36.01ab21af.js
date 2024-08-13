(window.webpackJsonp=window.webpackJsonp||[]).push([[36],{1049:function(e,t,a){"use strict";a.r(t),a.d(t,"default",(function(){return x}));a(265);var n=a(187),l=(a(471),a(470)),r=a(0),o=a.n(r),i=a(475),c=a(44);Object(c.a)(".hole .hole-up {\n  display: flex;\n  flex-direction: row;\n  justify-content: space-between;\n  align-items: center;\n}\n.hole .hole-search-style {\n  display: flex;\n  gap: 20px;\n  margin-top: 20px;\n}\n.hole .hole-search-style .input-icon {\n  font-size: var(--thoughtware-input-icon-15);\n}\n.hole .hole-table {\n  padding-top: var(--xpack-table-top);\n}\n.hole .text-red {\n  color: red;\n}\n.hole .text-dired {\n  color: #df5000;\n}\n.hole .text-yellow {\n  color: #ee9900;\n}\n.hole .text-blue {\n  color: #1890ff;\n}");var s=a(483),u=a(566),d=a(58),f=a(790),h=a(1003),m=a(576),p=a(482),v=a(545);function b(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var a=null==e?null:"undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"];if(null!=a){var n,l,r,o,i=[],c=!0,s=!1;try{if(r=(a=a.call(e)).next,0===t){if(Object(a)!==a)return;c=!1}else for(;!(c=(n=r.call(a)).done)&&(i.push(n.value),i.length!==t);c=!0);}catch(e){s=!0,l=e}finally{try{if(!c&&null!=a.return&&(o=a.return(),Object(o)!==o))return}finally{if(s)throw l}}return i}}(e,t)||function(e,t){if(e){if("string"==typeof e)return y(e,t);var a={}.toString.call(e).slice(8,-1);return"Object"===a&&e.constructor&&(a=e.constructor.name),"Map"===a||"Set"===a?Array.from(e):"Arguments"===a||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(a)?y(e,t):void 0}}(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}function y(e,t){(null==t||t>e.length)&&(t=e.length);for(var a=0,n=Array(t);a<t;a++)n[a]=e[a];return n}var g=[{key:1,value:"严重漏洞"},{key:2,value:"高危漏洞"},{key:3,value:"中危漏洞"},{key:4,value:"低危漏洞"}],S=[{key:"java",value:"Java"},{key:"node",value:"Node.js"}],x=Object(d.observer)((function(e){var t=u.a.findScanHolePage,a=u.a.createScanHole;u.a.deleteScanHole;var c=u.a.fresh,d=b(Object(r.useState)(),2),y=d[0],x=d[1],j=b(Object(r.useState)(1),2),E=j[0],w=j[1],O=b(Object(r.useState)(),2),N=O[0],k=O[1],L=b(Object(r.useState)(15),1)[0],P=b(Object(r.useState)(),2),I=P[0],A=P[1],C=b(Object(r.useState)(!1),2),V=C[0],H=C[1],z=b(Object(r.useState)(""),2),J=z[0],R=z[1],T=b(Object(r.useState)(!1),2),K=T[0],M=T[1],U=b(Object(r.useState)(null),2),$=U[0],q=U[1],B=b(Object(r.useState)([]),2),D=B[0],F=B[1],G=b(Object(r.useState)(!1),2),Q=G[0],W=G[1],X=b(Object(r.useState)(),2),Y=X[0],Z=X[1],_=b(Object(r.useState)(!1),2),ee=_[0],te=_[1],ae=b(Object(r.useState)(null),2),ne=ae[0],le=ae[1];Object(r.useEffect)((function(){oe(E)}),[c]);var re=[{title:"漏洞名称",dataIndex:"holeName",key:"holeName",width:"30%",ellipsis:!0,render:function(e,t){return o.a.createElement("div",{className:"text-color",onClick:function(){return ie(t)}},e)}},{title:"漏洞编号",dataIndex:"holeNumber",key:"holeNumber",width:"35%",ellipsis:!0},{title:"语言",dataIndex:"language",key:"language",width:"10%",ellipsis:!0},{title:"产品",dataIndex:"product",key:"product",width:"15%",ellipsis:!0},{title:"漏洞等级",dataIndex:"holeLevel",key:"holeLevel",width:"10%",ellipsis:!0,sorter:function(e,t){return e.holeLevel-t.holeLevel},render:function(e){return 1===e&&o.a.createElement("div",{className:"text-red"},"严重漏洞")||2===e&&o.a.createElement("div",{className:"text-dired"},"高危漏洞")||3===e&&o.a.createElement("div",{className:"text-yellow"},"中危漏洞")||4===e&&o.a.createElement("div",{className:"text-blue"},"低危漏洞")}}],oe=function(e,a,n,l){t({holeLevelList:a,language:n,holeName:$,sort:l,pageParam:{currentPage:e,pageSize:L}}).then((function(e){0===e.code&&(x(e.data.dataList),k(e.data.totalPage),A(e.data.totalRecord))}))},ie=function(e){R(e),H(!0)};return o.a.createElement("div",{className:"hole hadess-data-width"},o.a.createElement(n.default,{sm:{span:"24"},md:{span:"24"},lg:{span:"24"},xl:{span:"22",offset:"1"},xxl:{span:"18",offset:"3"}},o.a.createElement("div",{className:"hole-up"},o.a.createElement(i.a,{firstItem:"漏洞列表"})),o.a.createElement("div",{className:"hole-search-style"},o.a.createElement(v.a,{placeholder:"漏洞名称",onChange:function(e){var a=e.target.value;q(a),""===a&&(w(1),t({holeLevelList:D,language:Y,sort:ne,pageParam:{currentPage:1,pageSize:L}}).then((function(e){0===e.code&&(x(e.data.dataList),k(e.data.totalPage),A(e.data.totalRecord))})))},onPressEnter:function(){w(1),oe(1,D,Y)}}),o.a.createElement(m.a,{visible:ee,setVisible:te,dataList:S,setNav:function(e){w(1),Z(e),oe(1,D,e,ne)},nav:Y,type:"multiple",title:"语言"}),o.a.createElement(m.a,{visible:Q,setVisible:W,dataList:g,setNav:function(e){w(1),F(e),oe(1,e,Y,ne)},nav:D,title:"等级"})),o.a.createElement("div",{className:"hole-table"},o.a.createElement(l.default,{bordered:!1,columns:re,dataSource:y,rowKey:function(e){return e.id},onChange:function(e,t,a,n){"descend"===a.order&&(le("desc"),oe(E,D,Y,"desc")),"ascend"===a.order&&(le("asc"),oe(E,D,Y,"asc")),a.order||(le(null),oe(E,D,Y))},pagination:!1,locale:{emptyText:o.a.createElement(p.a,{title:"暂无数据"})}}),o.a.createElement(s.a,{pageCurrent:E,changPage:function(e){w(e),oe(e,D,Y,ne)},totalPage:N,totalRecord:I,refresh:function(){oe(E,D,Y,ne)}}))),o.a.createElement(f.a,{visible:V,setVisible:H,hole:J}),o.a.createElement(h.default,{editVisible:K,setEditVisible:M,createScanHole:a}))}))}}]);