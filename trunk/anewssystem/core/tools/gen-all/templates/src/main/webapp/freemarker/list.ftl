${r"<#assign"} ctx=springMacroRequestContext.getContextPath()>
${r"<#include"} "/include/taglibs.ftl">
<#assign ctx=r"${ctx}">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    ${r"<#include"} "/include/meta.ftl">
    <TITLE>${clz}</TITLE>
    <link type="text/css" href="${ctx}/css/extremecomponents.css" rel="stylesheet"/>
    <link type="text/css" href="${ctx}/css/admin.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/js/admin.js"></script>
  </head>
  <body>
<div id="operationDiv">
  <span class="operations"><a href="${ctx}/index.jsp">返回首页</a>&nbsp;
    <img src="${ctx}/images/icon/16x16/new.gif" align="top">
    &nbsp;<A href="${ctx}/${clz?lower_case}/create.htm">增加</A>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <img src="${ctx}/images/icon/16x16/delete.gif" align="top">
    &nbsp;<A href="javascript:batch_do('删除${clz}','${ctx}/${clz?lower_case}/removeAll.htm?token');">删除</A>
  </span>
</div>
<div id="tableDiv">
  ${r"<#include"} "/include/messages.ftl"/>
  ${r"<@ec.table"} items="page" var="item"
            action="${ctx}/${clz?lower_case}/list.htm"
            retrieveRowsCallback="limit"
            sortRowsCallback="limit"
            rowsDisplayed="15"
            autoIncludeParameters="false">
    ${r"<@ec.exportXls"} fileName="${clz}List.xls" tooltip="导出 Excel"/>
    ${"<@ec.row>"}
      ${"<@ec.column"} property="rowcount" cell="rowCount" title="序号" sortable="false"/>
<#list fields as field>
  <#if field.name != "id" && field.type.name != "java.util.Set">
      ${"<@ec.column"} property="${field.name}" title="${clz?uncap_first}.${field.name}"/>
  </#if>
</#list>
      ${r"<@ec.column"} property="edit" title="修改" sortable="false" viewsAllowed="html" width="40">
        <a href="${ctx}/${clz?lower_case}/edit.htm?id=${r"${item.id}"}">
          <img src="${ctx}/images/icon/16x16/modify.gif" border="0"/>
        </a>
      ${r"</@ec.column>"}
      ${r"<@ec.column"} property="checkbox" title="<input type='checkbox' onclick='selectAll(this)'/>选择" filterable="false" sortable="false" viewsAllowed="html" width="50">
        <input type="checkbox" name="itemlist" value="${r"${item.id}"}" style="border:0px"/>
      ${r"</@ec.column>"}
    ${r"</@ec.row>"}
  ${r"</@ec.table>"}
</div>
  </body>
</html>
