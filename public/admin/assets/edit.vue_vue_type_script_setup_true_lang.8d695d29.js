import{G as C,H as k,C as x,D as R,v as B,F as y}from"./element-plus.fa1aa32c.js";import{a as g,c as h,b as I}from"./role.0eca5d9e.js";import{P as U}from"./index.8bea8b0f.js";import{f as N}from"./index.b4f64333.js";import{d as T,s as c,r as G,e as P,a0 as S,o as j,c as q,V as a,M as t,u as z,T as _}from"./@vue.cab01781.js";const A={class:"edit-popup"},H=_("\u6B63\u5E38"),M=_("\u505C\u7528"),Z=T({__name:"edit",emits:["success","close"],setup(J,{expose:f,emit:d}){const p=c(),r=c(),m=G("add"),F=P(()=>m.value=="edit"?"\u7F16\u8F91\u89D2\u8272":"\u65B0\u589E\u89D2\u8272"),o=S({id:"",name:"",remark:"",sort:0,isDisable:0,menus:[]}),b={name:[{required:!0,message:"\u8BF7\u8F93\u5165\u540D\u79F0",trigger:["blur"]}]},V=async()=>{var e,l;await((e=p.value)==null?void 0:e.validate());const s={...o,menuIds:o.menus.join()};m.value=="edit"?await g(s):await h(s),(l=r.value)==null||l.close(),N.msgSuccess("\u64CD\u4F5C\u6210\u529F"),d("success")},D=()=>{d("close")};return f({open:(s="add")=>{var e;m.value=s,(e=r.value)==null||e.open()},setFormData:async s=>{const e=await I({id:s.id});for(const l in o)e[l]!=null&&e[l]!=null&&(o[l]=e[l])}}),(s,e)=>{const l=x,n=R,E=B,i=C,w=k,v=y;return j(),q("div",A,[a(U,{ref_key:"popupRef",ref:r,title:z(F),async:!0,width:"550px",onConfirm:V,onClose:D},{default:t(()=>[a(v,{class:"ls-form",ref_key:"formRef",ref:p,rules:b,model:o,"label-width":"60px"},{default:t(()=>[a(n,{label:"\u540D\u79F0",prop:"name"},{default:t(()=>[a(l,{class:"ls-input",modelValue:o.name,"onUpdate:modelValue":e[0]||(e[0]=u=>o.name=u),placeholder:"\u8BF7\u8F93\u5165\u540D\u79F0",clearable:""},null,8,["modelValue"])]),_:1}),a(n,{label:"\u5907\u6CE8",prop:"remark"},{default:t(()=>[a(l,{modelValue:o.remark,"onUpdate:modelValue":e[1]||(e[1]=u=>o.remark=u),type:"textarea",autosize:{minRows:4,maxRows:6},placeholder:"\u8BF7\u8F93\u5165\u5907\u6CE8",maxlength:"200","show-word-limit":""},null,8,["modelValue"])]),_:1}),a(n,{label:"\u6392\u5E8F",prop:"sort"},{default:t(()=>[a(E,{modelValue:o.sort,"onUpdate:modelValue":e[2]||(e[2]=u=>o.sort=u)},null,8,["modelValue"])]),_:1}),a(n,{label:"\u72B6\u6001",prop:"sort"},{default:t(()=>[a(w,{modelValue:o.isDisable,"onUpdate:modelValue":e[3]||(e[3]=u=>o.isDisable=u)},{default:t(()=>[a(i,{label:0},{default:t(()=>[H]),_:1}),a(i,{label:1},{default:t(()=>[M]),_:1})]),_:1},8,["modelValue"])]),_:1})]),_:1},8,["model"])]),_:1},8,["title"])])}}});export{Z as _};
