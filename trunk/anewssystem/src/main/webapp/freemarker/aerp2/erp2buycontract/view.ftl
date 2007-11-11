<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>查看采购合同信息</title>
  </head>
  <body>
    <h3>查看采购合同信息</h3>
    <table border="1">
        <tr>
            <td>合同编号:</td><td>${entity.code!}</td>
        </tr>
        <tr>
            <td>合同状态名称:</td><td>${entity.status!}</td>
        </tr>
        <tr>
            <td>合同名称:</td><td>${entity.name!}</td>
        </tr>
        <tr>
            <td>供应商:</td><td>${entity.erp2Supplier.name!}</td>
        </tr>
        <tr>
            <td>联系人:</td><td>${entity.linkman!}</td>
        </tr>
        <tr>
            <td>要求供货日期:</td><td>${entity.provideDate!}</td>
        </tr>
        <tr>
            <td>收货情况:</td><td>${entity.receipt!}</td>
        </tr>
        <tr>
            <td>签订日期:</td><td>${entity.contractDate!}</td>
        </tr>
        <tr>
            <td>订单签订人员:</td><td>${entity.username!}</td>
        </tr>
    </table>
  </body>
</html>

