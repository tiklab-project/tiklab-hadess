(window.webpackJsonp=window.webpackJsonp||[]).push([[20],{398:function(e,t,n){"use strict";n.r(t);var a=n(176),r=n.n(a),c=(n(241),n(243)),s=(n(242),n(244)),i=n(53),o=n.n(i),u=n(41),l=n.n(u),p=n(23),f=n.n(p),m=n(0),d=n.n(m),b=n(14),k=n.n(b),v=n(10),w=n.n(v),E=n(88),h=new function e(){k()(this,e),w()(this,"createWorkAppLink",function(){var e=o()(f.a.mark((function e(t){var n;return f.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,E.a.post("/workAppLink/createWorkAppLink",t);case 2:return n=e.sent,e.abrupt("return",n);case 4:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()),w()(this,"getWorkList",o()(f.a.mark((function e(){var t;return f.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,E.a.post("/workAppLink/findWorkAppLinkList",{});case 2:if((t=e.sent).code){e.next=5;break}return e.abrupt("return",t.data);case 5:return e.abrupt("return",[]);case 6:case"end":return e.stop()}}),e)})))),w()(this,"findWorkByID",function(){var e=o()(f.a.mark((function e(t){var n,a;return f.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return(n=new FormData).append("id",t),e.next=4,E.a.post("/workAppLink/findWorkAppLink",n);case 4:if((a=e.sent).code){e.next=7;break}return e.abrupt("return",a.data);case 7:return e.abrupt("return",{});case 8:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()),w()(this,"deleteWorkByID",function(){var e=o()(f.a.mark((function e(t){var n,a;return f.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return(n=new FormData).append("id",t),e.next=4,E.a.post("/workAppLink/deleteWorkAppLink",n);case 4:return a=e.sent,e.abrupt("return",a);case 6:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()),w()(this,"updateWork",function(){var e=o()(f.a.mark((function e(t){var n;return f.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,E.a.post("/workAppLink/updateWorkAppLink",t);case 2:return n=e.sent,e.abrupt("return",n);case 4:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}())},y=(n(250),n(249)),O=n(386),x=(n(246),n(245)),j=(n(260),n(257)),g=(n(136),n(80)),L=(n(253),n(251));function N(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);t&&(a=a.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,a)}return n}function W(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?N(Object(n),!0).forEach((function(t){w()(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):N(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}var A=function(e){var t=e.visible,n=e.setVisible,a=e.callBack,c=e.edit,s=L.a.useForm(),i=l()(s,1)[0];Object(m.useEffect)((function(){c&&i.setFieldsValue({appType:c.appType,appUrl:c.appUrl})}),[c]);return d.a.createElement(y.a,{title:"添加项目",visible:t,onOk:function(){i.submit()},onCancel:function(){i.resetFields(),a(),n(!1)},closable:!1,destroyOnClose:!0,preserve:!1},d.a.createElement(L.a,r()({},{labelCol:{span:6},wrapperCol:{span:18}},{form:i,onFinish:function(e){c?h.updateWork(W(W({},e),{},{id:c.id})).then((function(e){e.code?g.default.error("失败!"):(g.default.success("成功!"),a(),n(!1))})):h.createWorkAppLink(e).then((function(e){e.code?g.default.error("失败!"):(g.default.success("成功!"),a(),n(!1))}))}}),d.a.createElement(L.a.Item,{label:"标题",name:"appType",rules:[{required:!0,message:"用户名不能包含非法字符，如&,%，&，#……等"}]},d.a.createElement(j.a,{options:[{label:"项目管理",value:"project"},{label:"API BOX",value:"apibox"}]})),d.a.createElement(L.a.Item,{label:"应用链接地址",name:"appUrl",rules:[{required:!0,message:"请填写地址"}]},d.a.createElement(x.a,null))))},P="title--etkdn",C="head-action--2lzv2",D="box-gather--1t8f1",B="box-item--3tcR3",I="box-icon--ppPeh",F={apibox:{label:"API BOX"},project:{label:"项目管理"}},S=function(e){var t=e.visibleManagement,n=e.setVisibleManagement,a=e.workList,i=e.requestWorkList,u=Object(m.useState)(!1),p=l()(u,2),b=p[0],k=p[1],v=Object(m.useState)(null),w=l()(v,2),E=w[0],x=w[1],j=function(){var e=o()(f.a.mark((function e(t){return f.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,h.deleteWorkByID(t);case 2:e.sent.code||i();case 4:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}();return d.a.createElement(y.a,{title:"应用链接管理",visible:t,onOk:function(){i(),n(!1)},onCancel:function(){n(!1)},closable:!1,destroyOnClose:!0},d.a.createElement(c.a,null,d.a.createElement(s.a,{span:24,style:{overflow:"auto"}},d.a.createElement("div",{className:"title"},"default"),d.a.createElement("div",{className:D},a.map((function(e){return d.a.createElement("div",{className:B,key:e.id},d.a.createElement("div",{className:"box-icon management"},F[e.appType].label,d.a.createElement("div",{className:"action"},d.a.createElement("span",{onClick:function(){return x(e),void k(!0)}},"编辑"),d.a.createElement("span",{onClick:function(){return j(e.id)}},"删除"))))})),d.a.createElement("div",{className:B,onClick:function(){k(!0)}},d.a.createElement("div",{className:I},d.a.createElement(O.a,{className:{fontSize:20}})))))),d.a.createElement(A,r()({},e,{edit:E,visible:b,setVisible:k,callBack:function(){x(null),i()}})))},T={apibox:{label:"API BOX"},project:{label:"项目管理"}};t.default=function(e){var t=Object(m.useState)([]),n=l()(t,2),a=n[0],i=n[1],u=Object(m.useState)(!1),p=l()(u,2),b=p[0],k=p[1];Object(m.useEffect)((function(){w().then((function(e){}))}),[]);var v=function(e){k(!0)},w=function(){var e=o()(f.a.mark((function e(){var t;return f.a.wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,h.getWorkList();case 2:t=e.sent,i(t);case 4:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}();return d.a.createElement(d.a.Fragment,null,d.a.createElement(c.a,{justify:"center",style:{width:"100%"}},d.a.createElement(s.a,{xl:{span:24},xxl:{span:16}},d.a.createElement("div",{className:P},"default",d.a.createElement("span",{className:C,onClick:function(){return v()}},"管理")),d.a.createElement("div",{className:D},a.map((function(e){return d.a.createElement("div",{className:B,key:e.id},d.a.createElement("div",{className:I},d.a.createElement("a",{href:e.appUrl,target:"_blank"}," ",T[e.appType].label)))}))))),0===a.length&&d.a.createElement(c.a,{justify:"center",style:{width:"100%"}},d.a.createElement(s.a,{xl:{span:24},xxl:{span:16}},d.a.createElement("div",{className:P},"default",d.a.createElement("span",{className:C,onClick:function(){return v()}},"管理")),d.a.createElement("div",{className:D}))),d.a.createElement(S,r()({},e,{workList:a,requestWorkList:function(){w().then((function(e){}))},visibleManagement:b,setVisibleManagement:k})))}}}]);