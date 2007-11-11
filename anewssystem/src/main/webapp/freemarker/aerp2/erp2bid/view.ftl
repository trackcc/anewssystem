<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>查看标书信息</title>
  </head>
  <body>
    <h3>查看标书信息</h3>
    <table border="1">
        <tr>
            <td>标书编号:</td><td>${entity.code!}</td>
        </tr>
        <tr>
            <td>产品名称:</td><td>${entity.erp2Product.name!}</td>
        </tr>
        <tr>
            <td>产品数量:</td><td>${entity.productNum!}</td>
        </tr>
        <tr>
            <td>技术参数:</td><td>${entity.parameter!}</td>
        </tr>
        <tr>
            <td>投标开始日期:</td><td>${entity.startDate!}</td>
        </tr>
        <tr>
            <td>投标截止日期:</td><td>${entity.endDate!}</td>
        </tr>
        <tr>
            <td>开标日期:</td><td>${entity.openDate!}</td>
        </tr>
        <tr>
            <td>备注:</td><td>${entity.descn!}</td>
        </tr>
    </table>
  </body>
</html>

