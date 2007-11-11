<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>查看中标结果</title>
  </head>
  <body>
    <h3>查看中标结果</h3>
    <table border="1">
        <tr>
            <td>公示结果日期:</td><td>${entity.publishDate!}</td>
        </tr>
        <tr>
            <td>标书编号:</td><td>${entity.erp2InviteBid.erp2Bid.code!}</td>
        </tr>
        <tr>
            <td>中标单位:</td><td>${entity.erp2InviteBid.erp2Supplier.name!}</td>
        </tr>
        <tr>
            <td>招标内容:</td><td>${entity.bidContent!}</td>
        </tr>
        <tr>
            <td>中标成交金额:</td><td>${entity.price!}</td>
        </tr>
        <tr>
            <td>中标、成交项目主要内容:</td><td>${entity.descn!}</td>
        </tr>
    </table>
  </body>
</html>

