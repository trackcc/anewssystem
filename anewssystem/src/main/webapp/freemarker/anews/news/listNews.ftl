<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl">
    <TITLE>News</TITLE>
    <link type="text/css" href="${ctx}/css/extremecomponents.css" rel="stylesheet"/>
    <link type="text/css" href="${ctx}/css/admin.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/js/admin.js"></script>
    <style type="text/css">
.tabDiv {
  text-align: left;
  padding: 5px 0px;
}
.selected {
  font-weight: bold;
  font-size: 12pt;
  background-color: #DDFFDD;
  padding: 5px 10px;
}
.unselected {
  font-weight: normal;
  font-size: 12pt;
  padding: 5px 10px;
}
    </style>
  </head>
  <body>
<#assign status=(RequestParameters.status!"0")/>
    <div class="tabDiv">
<#list ["已发布","待审批","被驳回","草稿箱","垃圾箱","推荐","隐藏"] as item>
  <#if status==item_index?string>
      <span class="selected">${item}</span>
  <#else>
      <span class="unselected"><a href="list.htm?status=${item_index}">${item}</a></span>
  </#if>
</#list><div>
      <form action="${ctx}/news/list.htm">
        <input type="hidden" name="status" value="${status}">
        <span>按分类选择：</span>
        <select name="category_id" name="category_id">
          <option value="0">全部</option>
<#list categoryList! as item>
  <#if categoryId??>
    <@m.treeSelect item 0 categoryId/>
  <#else>
    <@m.treeSelect item 0/>
  </#if>
</#list>
        </select>
        <input type="submit">
      </form></div>
    </div>
      <div id="operationDiv">
        <span class="operations"><a href="${ctx}/index.jsp">返回首页</a>&nbsp;
<#if status=="0"><#--已发布-->
        <img src="${ctx}/images/icon/16x16/manage.gif" align="top">
        &nbsp;<A href="javascript:batch_do('隐藏新闻','${ctx}/news/changeStatus.htm?status=6');">隐藏</A>&nbsp;&nbsp;
        <img src="${ctx}/images/icon/16x16/manage.gif" align="top">
        &nbsp;<A href="javascript:batch_do('推荐新闻','${ctx}/news/changeStatus.htm?status=5');">推荐</A>&nbsp;&nbsp;
<#elseif status=="1"><#--待审批-->
        <img src="${ctx}/images/icon/16x16/manage.gif" align="top">
        &nbsp;<A href="javascript:batch_do('审批新闻','${ctx}/news/changeStatus.htm?status=0');">审批</A>&nbsp;&nbsp;
        <img src="${ctx}/images/icon/16x16/manage.gif" align="top">
        &nbsp;<A href="javascript:batch_do('驳回新闻','${ctx}/news/changeStatus.htm?status=2');">驳回</A>&nbsp;&nbsp;
<#elseif status=="2"><#--被驳回-->
        <img src="${ctx}/images/icon/16x16/manage.gif" align="top">
        &nbsp;<A href="javascript:batch_do('审批新闻','${ctx}/news/changeStatus.htm?status=0');">审批</A>&nbsp;&nbsp;
        <img src="${ctx}/images/icon/16x16/new.gif" align="top">
        &nbsp;<A href="javascript:batch_do('把新闻放入草稿箱','${ctx}/news/changeStatus.htm?status=3');">放入草稿箱</A>&nbsp;&nbsp;
        <img src="${ctx}/images/icon/16x16/new.gif" align="top">
        &nbsp;<A href="javascript:batch_do('把新闻放入垃圾箱','${ctx}/news/changeStatus.htm?status=4');">放入垃圾箱</A>&nbsp;&nbsp;
<#elseif status=="3"><#--草稿箱-->
        <img src="${ctx}/images/icon/16x16/manage.gif" align="top">
        &nbsp;<A href="javascript:batch_do('提交新闻','${ctx}/news/changeStatus.htm?status=1');">提交</A>&nbsp;&nbsp;
        <img src="${ctx}/images/icon/16x16/new.gif" align="top">
        &nbsp;<A href="javascript:batch_do('把新闻放入垃圾箱','${ctx}/news/changeStatus.htm?status=4');">放入垃圾箱</A>&nbsp;&nbsp;
<#elseif status=="4"><#--垃圾箱-->
        <img src="${ctx}/images/icon/16x16/new.gif" align="top">
        &nbsp;<A href="javascript:batch_do('把新闻放入草稿箱','${ctx}/news/changeStatus.htm?status=3');">放入草稿箱</A>&nbsp;&nbsp;
<#elseif status=="5"><#--推荐-->
        <img src="${ctx}/images/icon/16x16/manage.gif" align="top">
        &nbsp;<A href="javascript:batch_do('取消新闻推荐','${ctx}/news/changeStatus.htm?status=0');">取消</A>&nbsp;&nbsp;
<#elseif status=="6"><#--隐藏-->
        <img src="${ctx}/images/icon/16x16/manage.gif" align="top">
        &nbsp;<A href="javascript:batch_do('恢复新闻','${ctx}/news/changeStatus.htm?status=0');">恢复</A>&nbsp;&nbsp;
</#if>
        <img src="${ctx}/images/icon/16x16/new.gif" align="top">
        &nbsp;<A href="${ctx}/news/create.htm">增加</A>&nbsp;&nbsp;
        <img src="${ctx}/images/icon/16x16/delete.gif" align="top">
        &nbsp;<A href="javascript:batch_do('删除新闻','${ctx}/news/removeAll.htm?token');">删除</A>
        </span>
      </div>
<div id="tableDiv">
  <#include "/include/messages.ftl"/>
  <@ec.table items="page" var="item"
            action="${ctx}/news/list.htm?status=${status}"
            retrieveRowsCallback="limit"
            sortRowsCallback="limit"
            rowsDisplayed="15"
            autoIncludeParameters="true">
    <@ec.exportXls fileName="NewsList.xls" tooltip="导出 Excel"/>
    <@ec.row>
      <@ec.column property="rowcount" cell="rowCount" title="序号" sortable="false"/>
      <@ec.column property="newsCategory.name" title="${springMacroRequestContext.getMessage('news.category', 'news.category')}"/>
      <@ec.column property="name" title="${springMacroRequestContext.getMessage('news.name', 'news.name')}"/>
<#--
      <@ec.column property="subtitle" title="news.subtitle"/>
      <@ec.column property="link" title="news.link"/>
      <@ec.column property="image" title="news.image"/>
      <@ec.column property="hit" title="news.hit"/>
      <@ec.column property="summary" title="news.summary"/>
      <@ec.column property="content" title="news.content"/>
      <@ec.column property="source" title="news.source"/>
-->
      <@ec.column property="editor" title="${springMacroRequestContext.getMessage('news.editor', 'news.editor')}"/>
      <@ec.column property="updateDate" title="${springMacroRequestContext.getMessage('news.updateDate', 'news.updateDate')}" cell="date"/>
<#--
      <@ec.column property="status" title="news.status"/>
-->
      <@ec.column property="edit" title="修改" sortable="false" viewsAllowed="html" width="40">
        <a href="${ctx}/news/edit.htm?id=${item.id}">
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
