<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl">
    <TITLE>选择[${user.username}]角色</TITLE>
    <link type="text/css" href="${ctx}/css/extremecomponents.css" rel="stylesheet">
    <link type="text/css" href="${ctx}/css/admin.css" rel="stylesheet">
    <script type="text/javascript" src="${ctx}/js/admin.js"></script>
  </head>

  <body>
    <div class="pageTitle">
      选择[${user.username}]角色
    </div>

    <div id="operationDiv">
      <#include "/include/messages.ftl">
      <span class="operations">
        <img src="${ctx}/images/icon/16x16/return.gif" align="top"/>
        &nbsp;
        <A href="${ctx}/user/list.htm">返回</A>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <img src="${ctx}/images/icon/16x16/box_checked.gif" align="top"/>
        &nbsp;
        <A href="javascript:batch_do('授权','${ctx}/user/authRoles.htm?auth=true');">
          授权
        </A>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <img src="${ctx}/images/icon/16x16/box_unchecked.gif" align="top"/>
        &nbsp;
        <A href="javascript:batch_do('撤销授权','${ctx}/user/authRoles.htm?auth=false');">
          撤销授权
        </A>
      </span>
    </div>

    <div id="tableDiv">
      <@ec.table items="roles" var="role" action="${ctx}/user/selectRoles.htm" autoIncludeParameters="false">
        <@ec.row>
          <@ec.column property="id" title="ID" width="26"/>
          <@ec.column property="name" title="${springMacroRequestContext.getMessage('role.name', 'name')}" width="200"/>
          <@ec.column property="descn" title="${springMacroRequestContext.getMessage('role.descn', 'descn')}"/>
          <@ec.column property="authorize" title="${springMacroRequestContext.getMessage('role.authorized', 'authorized')}" width="26" style="text-align:center">
            <#if role.authorized??>
              <img src="${ctx}/images/icon/16x16/box_checked.gif" border="0"/>
            <#else>
              <img src="${ctx}/images/icon/16x16/box_unchecked.gif" border="0"/>
            </#if>
          </@ec.column>
          <@ec.column property="checkbox" title="<input type='checkbox' onclick='selectAll(this)'/>选择" sortable="false" viewsAllowed="html" width="26">
            <input type="checkbox" name="itemlist" value="${role.id}" style="border:0px"/>
          </@ec.column>
        </@ec.row>
      </@ec.table>
    </div>
  </body>
</html>
