<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl">
    <TITLE>选择[${role.name}]的资源</TITLE>
    <link type="text/css" href="${ctx}/css/extremecomponents.css" rel="stylesheet">
    <link type="text/css" href="${ctx}/css/admin.css" rel="stylesheet">
    <script type="text/javascript" src="${ctx}/js/admin.js"></script>
  </head>

  <body>
    <div class="pageTitle">
      选择[${role.name}]的资源
    </div>

    <div id="operationDiv">
      <#include "/include/messages.ftl"/>
      <span class="operations">
        <img src="${ctx}/images/icon/16x16/return.gif" align="top"/>
        &nbsp;
        <A href="${ctx}/role/list.htm">返回</A>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <img src="${ctx}/images/icon/16x16/box_checked.gif" align="top"/>
        &nbsp;
        <A href="javascript:batch_do('授权','${ctx}/role/authResources.htm?auth=true');">
          授权
        </A>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <img src="${ctx}/images/icon/16x16/box_unchecked.gif" align="top"/>
        &nbsp;
        <A href="javascript:batch_do('撤销授权','${ctx}/role/authResources.htm?auth=false');">
          撤销授权
        </A>
      </span>
    </div>

    <div id="tableDiv">
      <@ec.table items="resources" var="resource" action="${ctx}/role/selectResources.htm" autoIncludeParameters="false">
        <@ec.row>
          <@ec.column property="id" title="ID" width="26"/>
          <@ec.column property="name" title="${springMacroRequestContext.getMessage('resource.name', 'name')}"/>
          <@ec.column property="resType" title="${springMacroRequestContext.getMessage('resource.resType', 'resType')}"/>
          <@ec.column property="resString" title="${springMacroRequestContext.getMessage('resource.resString', 'resString')}"/>
          <@ec.column property="descn" title="${springMacroRequestContext.getMessage('resource.descn', 'descn')}"/>
          <@ec.column property="authorize" title="${springMacroRequestContext.getMessage('resource.authorized', 'authorized')}" width="26" style="text-align:center">
            <#if resource.authorized??>
              <img src="${ctx}/images/icon/16x16/box_checked.gif" border="0"/>
            <#else>
              <img src="${ctx}/images/icon/16x16/box_unchecked.gif" border="0"/>
            </#if>
          </@ec.column>
          <@ec.column property="checkbox" title="<input type='checkbox' onclick='selectAll(this)'/>选择" sortable="false" viewsAllowed="html" width="26">
            <input type="checkbox" name="itemlist" value="${resource.id}" style="border:0px"/>
          </@ec.column>
        </@ec.row>
      </@ec.table>
    </div>
  </body>
</html>
