(window.webpackJsonp=window.webpackJsonp||[]).push([[19],{404:function(e,t,a){"use strict";a.r(t);a(248);var n=a(247),l=(a(241),a(243)),s=(a(132),a(63)),r=(a(242),a(244)),m=(a(246),a(245)),c=a(10),i=a.n(c),o=(a(254),a(255)),u=a(41),p=a.n(u),d=a(38),g=a.n(d),f=a(0),b=a.n(f),y=a(133),E=a(68),T=a(88),O=a(252),v=a(46),S=(a(250),a(249)),j=a(176),P=a.n(j),k=(a(253),a(251)),w=(a(260),a(257)),C=m.a.TextArea,h=w.a.Option,I={labelCol:{span:4},wrapperCol:{span:20}},x=function(e){var t=Object(E.a)().t,a=k.a.useForm(),n=p()(a,1)[0],s=e.visible,c=e.onCancel,i=e.editdata,o=Object(f.useState)([]),u=p()(o,2),d=u[0],g=u[1],y=Object(f.useState)([]),O=p()(y,2),v=O[0],j=O[1],x=Object(f.useState)(!0),z=p()(x,2),D=(z[0],z[1]);Object(f.useEffect)((function(){i&&(D(1===i.contentConfigType),1===i.contentConfigType?n.setFieldsValue({name:i.name,id:i.id,title:i.title,content:i.content,msgType:i.msgType.id,msgSendType:i.msgSendType.id,contentConfigType:i.contentConfigType,link:i.link}):n.setFieldsValue({name:i.name,id:i.id,title:i.title,contentUrl:i.contentUrl,msgType:i.msgType.id,msgSendType:i.msgSendType.id,link:i.link,contentConfigType:i.contentConfigType}))}),[i]),Object(f.useEffect)((function(){M(),q()}),[]);var q=function(){T.a.post("/messageSendType/findMessageSendTypeList",{pageParam:{pageSize:999999,currentPage:1}}).then((function(e){e.code||j(e.data)}))},M=function(){T.a.post("/messageType/findMessageTypeList",{pageParam:{pageSize:999999,currentPage:1}}).then((function(e){e.code||g(e.data)}))},F=function(){D(!1),c()};return b.a.createElement(S.a,{visible:s,closable:!1,title:t(i?"message-template-edittemplate":"message-template-addtemplate"),destroyOnClose:!0,okText:t("message-save"),cancelText:t("message-table-cancel"),onOk:function(){var e="/messageTemplate/createMessageTemplate";n.validateFields().then((function(t){var a={name:t.name,msgType:{id:t.msgType},title:t.title,msgSendType:{id:t.msgSendType},contentConfigType:t.contentConfigType,link:t.link};a.content=t.content,i&&(e="/messageTemplate/updateMessageTemplate",a.id=i.id),T.a.post(e,a).then((function(e){e.code||F()}))}))},onCancel:F,width:800},b.a.createElement(l.a,null,b.a.createElement(r.a,{span:24},b.a.createElement(k.a,P()({},I,{form:n,preserve:!1}),b.a.createElement(k.a.Item,{name:"name",label:t("message-template-modal-name"),rules:[{required:!0,message:"字段类型名称不能包含非法字符，如&,%，&，#……等"}]},b.a.createElement(m.a,null)),b.a.createElement(k.a.Item,{name:"id",label:t("message-template-modal-ID")},b.a.createElement(m.a,{disabled:!0})),b.a.createElement(k.a.Item,{name:"msgType",label:t("message-template-modal-messagetype"),rules:[{required:!0,message:"".concat(t("message-template-modal-messagetype")," 必填")}]},b.a.createElement(w.a,null,d.map((function(e){return b.a.createElement(h,{value:e.id,key:e.id},e.name)})))),b.a.createElement(k.a.Item,{name:"msgSendType",label:t("message-send-type"),rules:[{required:!0,message:"".concat(t("message-send-type")," 必填")}]},b.a.createElement(w.a,{onSelect:function(e){var t=v.findIndex((function(t){return t.id===e}));v[t].code}},v.map((function(e){return b.a.createElement(h,{value:e.id,key:e.id},e.name)})))),b.a.createElement(k.a.Item,{name:"title",label:t("message-template-modal-title"),rules:[{required:!0,message:"".concat(t("message-template-modal-title")," 必填")}]},b.a.createElement(m.a,null)),b.a.createElement(k.a.Item,{name:"contentConfigType",label:t("message-template-config"),initialValue:1,rules:[{required:!0,message:"".concat(t("message-send-type")," 必填")}]},b.a.createElement(w.a,{disabled:!0},b.a.createElement(h,{value:1},"文本"),b.a.createElement(h,{value:2},"模板路径或对象"))),b.a.createElement(k.a.Item,{name:"content",label:t("message-template-modal-content"),rules:[{required:!0,message:"".concat(t("message-template-modal-content")," 必填")}]},b.a.createElement(C,{autoSize:{minRows:5,maxRows:5}})),b.a.createElement(k.a.Item,{name:"link",label:"消息链接",rules:[{required:!0,message:"消息链接必填"}]},b.a.createElement(m.a,null))))))},z=a(123),D=["messageTemplateStore"],q=["name"];function M(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,n)}return a}function F(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?M(Object(a),!0).forEach((function(t){i()(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):M(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}var L=[{disabled:!0,breadcrumbName:"消息模板管理 "},{path:"list",breadcrumbName:"消息模板列表"}];t.default=Object(y.b)(z.a)(Object(y.c)((function(e){var t=Object(E.a)().t,a=e.messageTemplateStore,c=(g()(e,D),a.getMessageTemplateData),i=a.messageTemplateData,u=Object(f.useState)(""),d=p()(u,2),y=d[0],S=d[1],j=Object(f.useState)(!1),P=p()(j,2),k=P[0],w=P[1],C=Object(f.useState)(null),h=p()(C,2),I=h[0],z=h[1],M=Object(f.useState)(10),N=p()(M,1)[0],R=Object(f.useState)(1),V=p()(R,2),J=V[0],U=V[1],A=Object(f.useState)(0),K=p()(A,2),B=K[0],G=K[1],H=Object(f.useState)({pageParam:{pageSize:N,currentPage:J}}),Q=p()(H,2),W=Q[0],X=Q[1],Y=[{title:t("message-template-name"),dataIndex:"name",key:"name"},{title:t("message-template-modal-ID"),dataIndex:"id",key:"code"},{title:t("message-template-title"),dataIndex:"title",key:"title"},{title:t("message-table-action"),dataIndex:"action",key:"action",render:function(e,a){return b.a.createElement(o.b,{size:"middle"},b.a.createElement("a",{onClick:function(){return _(a)}},t("message-edit")),b.a.createElement("a",{onClick:function(){return $(a.id)}},t("message-delete")))}}];Object(f.useEffect)((function(){Z(W)}),[W]);var Z=function(e){T.a.post("/messageTemplate/findMessageTemplatePage",e).then((function(e){e.code||(G(e.data.totalRecord),c(e.data.dataList))}))},$=function(e){var t=new FormData;t.append("id",e),T.a.post("/messageTemplate/deleteMessageTemplate",t).then((function(e){if(!e.code){var t=Object(v.a)(B,N,J),a=F(F({},W),{},{pageParam:{pageSize:N,currentPage:t}});U(t),X(a)}}))},_=function(e){w(!0),z(e)};return b.a.createElement("div",{id:"message-template"},b.a.createElement(l.a,{justify:"center",style:{width:"100%"}},b.a.createElement(r.a,{xl:{span:20},lg:{span:24},xxl:{span:16}},b.a.createElement(O.b,{routes:L}),b.a.createElement(l.a,{justify:"space-between",gutter:[26,16]},b.a.createElement(r.a,{span:8},b.a.createElement(m.a,{placeholder:t("message-management-search-input"),value:y,onChange:function(e){return S(e.target.value)},onPressEnter:function(e){return function(e){var t=e.target.value;U(1);W.name;var a=F(F({},g()(W,q)),{},{pageParam:{pageSize:N,currentPage:1}});t&&(a.name=t),X(a)}(e)}})),b.a.createElement(r.a,{span:8,offset:8},b.a.createElement("div",{className:"message-template-btn"},b.a.createElement(s.a,{onClick:function(){w(!0)}},"+",t("message-template-addtemplate"))))),b.a.createElement(l.a,null,b.a.createElement(r.a,{span:24},b.a.createElement(n.a,{dataSource:i,columns:Y,rowKey:function(e){return e.id},tableLayout:"fixed",pagination:{current:J,pageSize:N,total:B},onChange:function(e,t,a){return function(e,t,a){U(e.current);var n=F(F({},W),{},{orderParams:[],pageParam:{pageSize:N,currentPage:e.current}});X(n)}(e)}}))))),b.a.createElement(x,{visible:k,editdata:I,onCancel:function(){return z(null),w(!1),void Z(W)}}))})))}}]);