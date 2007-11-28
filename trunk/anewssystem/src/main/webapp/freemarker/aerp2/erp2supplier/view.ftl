<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>查看供应商信息</title>
  </head>
  <body>
    <h3>查看供应商信息</h3>
    <table border="1">
        <tr>
            <td>序号:</td><td>${entity.id!}</td>
        </tr>
        <tr>
            <td>供应商名称:</td><td>${entity.name!}</td>
        </tr>
        <tr>
            <td>类别:</td><td><#if entity.type==0>染料供应商<#elseif entity.type==1>助剂供应商<#elseif entity.type==2>设备供应商<#elseif entity.type==3>综合供应商</#if></td>
        </tr>
        <tr>
            <td>所在地:</td><td>${entity.area!}</td>
        </tr>
        <tr>
            <td>联系人:</td><td>${entity.linkman!}</td>
        </tr>
        <tr>
            <td>电话:</td><td>${entity.tel!}</td>
        </tr>
        <tr>
            <td>传真:</td><td>${entity.fax!}</td>
        </tr>
        <tr>
            <td>地址:</td><td>${entity.address!}</td>
        </tr>
        <tr>
            <td>邮编:</td><td>${entity.zip!}</td>
        </tr>
        <tr>
            <td>E-mail:</td><td>${entity.email!}</td>
        </tr>
        <tr>
            <td>信用等级:</td><td><#if entity.rank==0>优秀<#elseif entity.rank==1>良好<#elseif entity.rank==2>一般<#elseif entity.rank==3>差</#if></td>
        </tr>
        <tr>
            <td>负责人:</td><td>${entity.lead!}</td>
        </tr>
        <tr>
            <td>网站主页:</td><td>${entity.homepage!}</td>
        </tr>
        <tr>
            <td>备注:</td><td>${entity.descn!}</td>
        </tr>
    </table>
    <hr>
    供应商品
    <table border="1">
        <tr>
            <td>序号</td>
            <td>品名</td>
            <td>类别</td>
            <td>型号</td>
            <td>生产厂家</td>
            <td>单价</td>
            <td>计量单位</td>
        </tr>
<#list entity.erp2Products! as item>
        <tr>
            <td>${item.id!}</td>
            <td>${item.name!}</td>
            <td><#if item.type==0>染料<#elseif item.type==1>助剂<#elseif item.type==2>设备</#if></td>
            <td>${item.material!}</td>
            <td>${item.factory!}</td>
            <td>${item.price!}</td>
            <td><#if item.unit==0>千克<#elseif item.unit==1>克<#elseif item.unit==2>升<#elseif item.unit==3>毫升<#elseif item.unit==4>台</#if></td>
        </tr>
</#list>
    </table>
  </body>
</html>

