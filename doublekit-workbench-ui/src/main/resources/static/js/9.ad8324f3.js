(window.webpackJsonp=window.webpackJsonp||[]).push([[9],{395:function(e,t,n){"use strict";n.r(t);n(248);var r=n(247),a=(n(241),n(243)),l=(n(242),n(244)),i=(n(246),n(245)),o=n(10),c=n.n(o),u=n(41),d=n.n(u),p=n(0),s=n.n(p),m=n(133),f=n(68),g=n(88),v=n(176),b=n.n(v),h=(n(259),n(256)),j=n(263),O=(n(132),n(63)),E=(n(254),n(255)),y=(n(250),n(249)),P=(n(253),n(251));function w(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function S(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?w(Object(n),!0).forEach((function(t){c()(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):w(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}var I=function(e){var t=Object(f.a)().t,n=e.visible,o=e.onCancel,c=e.id,u=void 0===c?"":c,m=e.addSelectedRowKeys,v=e.userIDWidthId,b=e.domainId,h=Object(p.useState)([]),j=d()(h,2),E=j[0],w=j[1],I=Object(p.useState)([]),D=d()(I,2),R=D[0],k=D[1],F=Object(p.useState)(10),x=d()(F,1)[0],C=Object(p.useState)(1),N=d()(C,2),U=N[0],z=N[1],L=Object(p.useState)(0),K=d()(L,2),A=K[0],B=K[1],T={domainId:b,pageParam:{pageSize:x,currentPage:U}},J=Object(p.useState)(T),W=d()(J,2),G=W[0],q=W[1];Object(p.useEffect)((function(){H(G)}),[G]),Object(p.useEffect)((function(){w(m)}),[m]);var H=function(e){g.a.post("dmUser/findDmUserPage",e).then((function(e){e.code||(B(e.data.totalRecord),k(e.data.dataList.map((function(e){return e.user}))))}))},M=function(){q({pageParam:{pageSize:x,currentPage:1}}),w([]),z(1),B(0),o()},Q=[{title:t("privilege-role.name"),dataIndex:"name",key:"name",width:"20%",ellipsis:!0},{title:t("privilege-role.phone"),dataIndex:"phone",key:"phone",width:"20%",ellipsis:!0},{title:t("privilege-role.email"),dataIndex:"email",key:"email",width:"20%",ellipsis:!0},{title:t("privilege-role.org"),dataIndex:"org",key:"org",width:"20%",ellipsis:!0},{title:t("privilege-role.type"),dataIndex:"userType",key:"userType",width:"10%",ellipsis:!0,render:function(e){switch(e){case 2:return t("privilege-role.thirdparty");case 1:return t("privilege-role.internal");default:return"--"}}}],V={selectedRowKeys:E,onChange:function(e){w(e)},onSelect:function(e){var t,n="prjRoleUser/deletePrjRoleUser";E.some((function(t){return t===e.id}))?(t=new FormData).append("id",v[e.id]):(t={role:{id:u},user:{id:e.id}},n="prjRoleUser/createPrjRoleUser"),g.a.post(n,t)}};return s.a.createElement(y.a,{title:t("privilege-select-user"),visible:n,onOk:function(){M()},onCancel:M,closable:!1,width:800,destroyOnClose:!0},s.a.createElement(a.a,null,s.a.createElement(l.a,{span:24},s.a.createElement(P.a,{layout:"inline",onFinish:function(e){},preserve:!1},s.a.createElement(P.a.Item,{name:"name",label:t("privilege-user")},s.a.createElement(i.a,null)),s.a.createElement(P.a.Item,null,s.a.createElement(O.a,{type:"primary",htmlType:"submit"},t("privilege-search")))))),s.a.createElement(a.a,null,s.a.createElement(l.a,{span:24},s.a.createElement("div",{style:{width:"100%"}},s.a.createElement(r.a,{columns:Q,dataSource:R,rowSelection:V,rowKey:function(e){return e.id},pagination:{current:U,pageSize:x,total:A,onChange:function(e){z(e);var t=S(S({},t),{},{pageParam:{pageSize:x,currentPage:e}});q(t)}}})))))},D=function(e){var t=e.roleDetail,n=e.domainId,o=Object(f.a)().t,c=[{title:o("privilege-role.name"),dataIndex:["user","name"]},{title:o("privilege-role.phone"),dataIndex:["user","phone"]},{title:o("privilege-role.email"),dataIndex:["user","email"]},{title:o("privilege-role.type"),dataIndex:["user","userType"],render:function(e){switch(e){case 2:return o("privilege-role.thirdparty");case 1:return o("privilege-role.internal");default:return"--"}}},{title:o("privilege-common.action"),key:"action",render:function(e,t){return s.a.createElement(E.b,{size:"middle"},s.a.createElement("a",{onClick:function(){return z(t.id)}},o("privilege-common.delete")))}}],u=Object(p.useState)([]),m=d()(u,2),v=m[0],h=m[1],j=Object(p.useState)(!1),y=d()(j,2),P=y[0],w=y[1],S=Object(p.useState)([]),D=d()(S,2),R=D[0],k=D[1],F=Object(p.useState)({}),x=d()(F,2),C=x[0],N=x[1];Object(p.useEffect)((function(){U(t.roleId)}),[t]);var U=function(e){var t={roleId:e};g.a.post("prjRoleUser/findPrjRoleUserList",t).then((function(e){if(!e.code){h(e.data);var t=e.data.map((function(e){return e.user.id})),n=e.data.reduce((function(e,t){return e[t.user.id]=t.id,e}),{});k(t),N(n)}}))},z=function(e){var n=new FormData;n.append("id",e),g.a.post("prjRoleUser/deletePrjRoleUser",n).then((function(e){e.code||U(t.id)}))};return s.a.createElement(s.a.Fragment,null,s.a.createElement(a.a,null,s.a.createElement(l.a,{span:6},s.a.createElement(i.a,{placeholder:o("privilege-search")+o("privilege-user-and-org")})),s.a.createElement(l.a,{span:18},s.a.createElement("div",{className:"privilege-role-detail-btn"},s.a.createElement(O.a,{onClick:function(){w(!0)}},"+",o("privilege-add-user"))))),s.a.createElement(a.a,null,s.a.createElement(l.a,{span:24},s.a.createElement(r.a,{columns:c,dataSource:v,rowKey:function(e){return e.id}}))),s.a.createElement(I,b()({},e,{visible:P,onCancel:function(){return w(!1),void U(t.roleId)},id:t.roleId,addSelectedRowKeys:R,userIDWidthId:C,domainId:n})))},R=n(78),k=n.n(R),F=(n(275),n(264));function x(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function C(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?x(Object(n),!0).forEach((function(t){c()(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):x(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}var N=F.a.Group,U=function(e){var t=e.roleDetail,n=e.group,r=Object(p.useState)([]),a=d()(r,2),l=a[0],i=a[1],o=Object(p.useState)({}),c=d()(o,2),u=c[0],m=c[1],f=Object(p.useState)({}),v=d()(f,2),b=v[0],h=v[1];Object(p.useEffect)((function(){t&&j()}),[t]);var j=function(){g.a.post("/prjFunction/findPrjFunctionList",{group:n}).then((function(e){if(!e.code){var n=O(e.data);i(n.result),m(n.resultChild),y(t.roleId,!0,n.result,n.resultChild)}}))},O=function(e){var t=e.map((function(e){return!e.parentFunction&&{value:e.id,label:e.name}})).filter(Boolean),n=e.map((function(e){return e.parentFunction&&e.parentFunction.id&&{value:e.id,label:e.name,parent:e.parentFunction.id}})).filter(Boolean),r=[],a={};return t.forEach((function(e){var t=e.value,l=n.filter((function(e){return e.parent===t}));r.push({value:e.value,label:e.label,indeterminate:!1,checkedList:[],checkAll:!1}),a[e.value]=l})),{result:r,resultChild:a}},E=function(e){var t=e.filter((function(e){return e.function&&e.function.parentFunction})).map((function(e){return{parentID:e.function.parentFunction.id,id:e.function.id}})),n=e.filter((function(e){return!e.function.parentFunction})).map((function(e){return e.function.id})),r={};return n.forEach((function(e){var n=t.map((function(t){if(t.parentID===e)return t.id})).filter(Boolean);r[e]=[].concat(k()(n),[e])})),r},y=function(e){var t=arguments.length>1&&void 0!==arguments[1]&&arguments[1],n=arguments.length>2&&void 0!==arguments[2]?arguments[2]:[],r=arguments.length>3&&void 0!==arguments[3]?arguments[3]:{},a={roleId:e};g.a.post("prjRoleFunction/findPrjRoleFunctionList",a).then((function(e){if(!e.code){var a=E(e.data),o=e.data.reduce((function(e,t){return e[t.function.id]=t.id,e}),{}),c=t?r:u,d=(t?n:l).map((function(e){var t=a[e.value]?a[e.value]:[],n=0===c[e.value].length,r=t.length===c[e.value].length+1&&0!==t.length,l=r?!r:!!t.length&&t.length<c[e.value].length+1;return n&&1===t.length&&(r=!0,l=!1),C(C({},e),{},{checkedList:t,indeterminate:l,checkAll:r})}));h(o),i(d)}}))},P=function(e,t){var n=t,r=l.filter((function(t){return t.value===e}))[0].checkedList,a=n.length<r.length,i=a?w(r,n):w(n,r);if(a)if(r.length>2){var o=i.filter((function(t){return t!==e}));o.map((function(e,t){I(e,t+1===o.length)}))}else{var c=Array.from(new Set(0===n.length?[e].concat(k()(i)):i));c.map((function(e,t){I(e,t+1===c.length)}))}else{var u=0===r.length?[e].concat(k()(i)):i;u.map((function(e,t){S(e,t+1===u.length)}))}},w=function(e,t){for(var n=[],r=function(r){var a=e[r];t.some((function(e){return e===a}))&&0!==t.length||n.push(a)},a=0;a<e.length;a++)r(a);return n},S=function(e){var n=!(arguments.length>1&&void 0!==arguments[1])||arguments[1],r={role:{id:t.roleId},function:{id:e}};g.a.post("prjRoleFunction/createPrjRoleFunction",r).then((function(e){n&&y(t.roleId)}))},I=function(e){var n=!(arguments.length>1&&void 0!==arguments[1])||arguments[1],r=b[e],a=new FormData;a.append("id",r),g.a.post("prjRoleFunction/deletePrjRoleFunction",a).then((function(e){n&&y(t.roleId)}))};return s.a.createElement(s.a.Fragment,null,l.map((function(e){return s.a.createElement("div",{key:e.value},s.a.createElement(F.a,{indeterminate:e.indeterminate,onChange:function(t){return function(e,t){var n=Object.keys(b),r=n.includes(e),a=n.filter((function(t){return u[e].some((function(e){return e.value===t}))})),l=u[e].filter((function(e){if(!a.some((function(t){return t===e.value})))return e})),i=(!r||0!==u[e].length)&&u[e].length===l.length;if(r||(i=!0,l=u[e]),i)n.includes(e)||(l=[].concat(k()(l),[{value:e}])),l.length>0?l.map((function(e,t){S(e.value,t+1===l.length)})):S(e,!0);else{if(n.includes(e)){var o=a.map((function(e){return{value:e}}));l=[].concat(k()(o),[{value:e}])}l.length>0?l.map((function(e,t){I(e.value,t+1===l.length)})):I(e,!0)}}(e.value)},checked:e.checkAll},e.label),s.a.createElement("br",null),"       ",s.a.createElement(N,{options:u[e.value],value:e.checkedList,onChange:function(t){return function(e,t){P(e,t)}(e.value,t)}}))})))},z=h.a.TabPane,L=function(e){var t=Object(f.a)().t,n=e.roleDetailParams,r=e.clickBreadcrumb,i=e.domainId,o=Object(p.useState)({}),c=d()(o,2),u=c[0],m=c[1],v=[{click:r,breadcrumbName:"角色管理 "},{path:"/projectRole/feature",breadcrumbName:"角色详情"}],O=Object(p.useRef)(null);Object(p.useEffect)((function(){return O.current=!0,n.id&&E(n.id),function(){return O.current=!1}}),[n]);var E=function(e){var t=new FormData;t.append("id",e),g.a.post("/dmPrjRole/findDmPrjRole",t).then((function(e){!e.code&&O.current&&m(e.data)}))};return s.a.createElement("div",{className:"projectRole-role-detail"},s.a.createElement(a.a,{justify:"center"},s.a.createElement(l.a,{xl:{span:20},lg:{span:24},xxl:{span:16}},s.a.createElement(j.a,{routes:v}),s.a.createElement("div",{className:"projectRole-role-detail-container"},s.a.createElement(a.a,null,s.a.createElement(l.a,{span:6},s.a.createElement("div",{className:"projectRole-role-detail-item"},s.a.createElement("label",null,t("privilege-role.roleName"),":"),"  ",s.a.createElement("span",null,u.role&&u.role.name)))),s.a.createElement(a.a,null,s.a.createElement(l.a,{span:6},s.a.createElement("div",{className:"projectRole-role-detail-item"},s.a.createElement("label",null,t("privilege-role.roleDesc"),":"),"  ",s.a.createElement("span",null,u.role&&u.role.desc)))),s.a.createElement(a.a,null,s.a.createElement(l.a,{span:24},s.a.createElement("div",{className:"card-container"},s.a.createElement(h.a,{type:"card",defaultActiveKey:n.type},s.a.createElement(z,{tab:t("privilege-role.people"),key:"people"},s.a.createElement(D,b()({},e,{group:n.group,roleDetail:n,domainId:i}))),s.a.createElement(z,{tab:t("privilege-role.promise"),key:"promise"},s.a.createElement(U,b()({},e,{group:n.group,roleDetail:n})))))))))))},K=n(115),A=n(252);function B(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function T(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?B(Object(n),!0).forEach((function(t){c()(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):B(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}t.default=Object(m.b)(K.b)(Object(m.c)((function(e){var t=Object(f.a)().t,n=e.privilegeDomainRoleStore,o=e.domainId,c=n.getDomainRoles,u=n.privilegeDomainRole,m=Object(p.useState)(10),v=d()(m,1)[0],b=Object(p.useState)(1),h=d()(b,2),j=h[0],O=h[1],E=Object(p.useState)(0),y=d()(E,2),P=y[0],w=y[1],S=Object(p.useState)(null),I=d()(S,2),D=I[0],R=I[1],k=Object(p.useState)(!1),F=d()(k,2),x=F[0],C=F[1],N={domainId:o,pageParam:{pageSize:v,currentPage:j}},U=Object(p.useState)(N),z=d()(U,2),K=z[0],B=z[1],J=Object(p.useRef)(null);Object(p.useEffect)((function(){return J.current=!0,q(K),function(){return J.current=!1}}),[K]);var W=[{title:t("privilege-role.roleName"),dataIndex:["role","name"],render:function(e,t){return s.a.createElement("a",{onClick:function(){return G(t.id,t.group,"people",t.role.id)}},e)}},{title:t("privilege-role.roleDesc"),dataIndex:["role","desc"]},{title:t("privilege-role.people"),dataIndex:"people",render:function(e,t){return s.a.createElement("a",{onClick:function(){return G(t.id,t.group,"people",t.role.id)}},e)}},{title:t("privilege-role.promise"),dataIndex:"promise",render:function(e,t){return s.a.createElement("a",{onClick:function(){return G(t.id,t.group,"promise",t.role.id)}},e)}},{title:t("privilege-role.type"),dataIndex:["role","group"],render:function(e,n){switch(e){case"system":return t("privilege-role.system");case"custom":default:return t("privilege-role.custom")}}},{title:t("privilege-common.action"),dataIndex:"action",render:function(e,t){}}],G=function(e,t,n,r){R({id:e,group:t,type:n,roleId:r}),C(!0)},q=function(e){g.a.post("dmPrjRole/findDmPrjRolePage",e).then((function(e){!e.code&&J.current&&(c(e.data.dataList),w(e.data.totalRecord))}))};return s.a.createElement(s.a.Fragment,null,x?s.a.createElement(L,{roleDetailParams:D,clickBreadcrumb:C,domainId:o}):s.a.createElement("div",{className:"privilege-domain"},s.a.createElement(a.a,{justify:"center"},s.a.createElement(l.a,{xl:{span:20},lg:{span:24},xxl:{span:16}},s.a.createElement(A.b,{routes:[{disabled:!0,breadcrumbName:"角色管理 "},{path:"first",breadcrumbName:"角色列表"}]}),s.a.createElement("div",{className:"privilege-domain-container"},s.a.createElement(a.a,null,s.a.createElement(l.a,{span:6},s.a.createElement(i.a,{placeholder:t("privilege-common.please-input")+t("privilege-role.roleName"),onPressEnter:function(e){return function(e){B({name:e.target.value,pageParam:{pageSize:v,currentPage:1}}),O(1)}(e)}}))),s.a.createElement(a.a,null,s.a.createElement(l.a,{span:24},s.a.createElement(r.a,{columns:W,dataSource:u,rowKey:function(e){return e.id},pagination:{current:j,pageSize:v,total:P},onChange:function(e,t,n){return function(e,t,n){O(e.current);var r=T(T({},K),{},{orderParams:[],pageParam:{pageSize:v,currentPage:e.current}});B(r)}(e)}}))))))))})))}}]);