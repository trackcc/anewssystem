<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl">
    <TITLE>TrackerIssue</TITLE>
    <link type="text/css" href="${ctx}/css/extremecomponents.css" rel="stylesheet"/>
    <link type="text/css" href="${ctx}/css/admin.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/js/admin.js"></script>
  </head>
  <body>
<div id="operationDiv">
  <span class="operations"><a href="${ctx}/index.jsp">返回首页</a>&nbsp;
    <img src="${ctx}/images/icon/16x16/new.gif" align="top">
    &nbsp;<A href="${ctx}/trackerissue/create.htm">增加</A>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <img src="${ctx}/images/icon/16x16/delete.gif" align="top">
    &nbsp;<A href="javascript:batch_do('删除TrackerIssue','${ctx}/trackerissue/removeAll.htm?token');">删除</A>
  </span>
</div>
<div id="tableDiv">
  <#include "/include/messages.ftl"/>
  <@ec.table items="page" var="item"
            action="${ctx}/trackerissue/list.htm"
            retrieveRowsCallback="limit"
            sortRowsCallback="limit"
            rowsDisplayed="15"
            autoIncludeParameters="false">
    <@ec.exportXls fileName="TrackerIssueList.xls" tooltip="导出 Excel"/>
    <@ec.row>
      <@ec.column property="rowcount" cell="rowCount" title="序号" sortable="false"/>
      <@ec.column property="trackerProject" title="${springMacroRequestContext.getMessage('trackerIssue.trackerProject', 'trackerIssue.trackerProject')}"/>
      <@ec.column property="name" title="${springMacroRequestContext.getMessage('trackerIssue.name', 'trackerIssue.name')}"/>
      <@ec.column property="priority" title="${springMacroRequestContext.getMessage('trackerIssue.priority', 'trackerIssue.priority')}"/>
      <@ec.column property="severity" title="${springMacroRequestContext.getMessage('trackerIssue.severity', 'trackerIssue.severity')}"/>
      <@ec.column property="status" title="${springMacroRequestContext.getMessage('trackerIssue.status', 'trackerIssue.status')}"/>
      <@ec.column property="assignTo" title="${springMacroRequestContext.getMessage('trackerIssue.assignTo', 'trackerIssue.assignTo')}"/>
      <@ec.column property="submitBy" title="${springMacroRequestContext.getMessage('trackerIssue.submitBy', 'trackerIssue.submitBy')}"/>
      <@ec.column property="addTime" title="${springMacroRequestContext.getMessage('trackerIssue.addTime', 'trackerIssue.addTime')}"/>
      <@ec.column property="updateDate" title="${springMacroRequestContext.getMessage('trackerIssue.updateDate', 'trackerIssue.updateDate')}"/>
      <@ec.column property="content" title="${springMacroRequestContext.getMessage('trackerIssue.content', 'trackerIssue.content')}"/>
      <@ec.column property="file" title="${springMacroRequestContext.getMessage('trackerIssue.file', 'trackerIssue.file')}"/>
      <@ec.column property="edit" title="修改" sortable="false" viewsAllowed="html" width="40">
        <a href="${ctx}/trackerissue/edit.htm?id=${item.id}">
          <img src="${ctx}/images/icon/16x16/modify.gif" border="0"/>
        </a>
      </@ec.column>
      <@ec.column property="checkbox" title="<input type='checkbox' onclick='selectAll(this)'/>选择" filterable="false" sortable="false" viewsAllowed="html" width="50">
        <input type="checkbox" name="itemlist" value="${item.id}" style="border:0px"/>
      </@ec.column>
    </@ec.row>
  </@ec.table>
</div>
  </body>
</html>
