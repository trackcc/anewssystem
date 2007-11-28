<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>查看投标单</title>
  </head>
  <body>
    <h3>查看投标单</h3>
    <table border="1">
        <tr>
            <td>标书单位名称:</td><td>${entity.erp2Supplier.name!}</td>
        </tr>
        <tr>
            <td>所投标书编号:</td><td>${entity.erp2Bid.code!}</td>
        </tr>
        <tr>
            <td>竞标价格:</td><td>${entity.price!}</td>
        </tr>
        <tr>
            <td>投标单位地址:</td><td>${entity.address!}</td>
        </tr>
        <tr>
            <td>联系电话:</td><td>${entity.tel!}</td>
        </tr>
        <tr>
            <td>投标日期:</td><td>${entity.bidDate!}</td>
        </tr>
        <tr>
            <td>电子邮箱:</td><td>${entity.email!}</td>
        </tr>
        <tr>
            <td>详细说明:</td><td>${entity.descn!}</td>
        </tr>
    </table>
  </body>
</html>

