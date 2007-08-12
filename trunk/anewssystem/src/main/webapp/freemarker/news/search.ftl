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
  </head>
  <body>
<#assign keywords=RequestParameters.keywords!""/>
    <div id="operationDiv" style="padding:20px">
      <span class="operations" style="text-align:center">
        <form name="searchForm" method="post" action="${ctx}/news/search.htm" onsubmit="if (document.searchForm.keywords.value==''){alert('填写查询内容');document.searchForm.keywords.focus();return false;}">
          查询：<input type="text" name="keywords" value="${keywords}">
          <input type="image" src="${ctx}/images/icon/16x16/search.gif">
        </form>
      </span>
    </div>
    <div id="tableDiv">
      <table class="eXtremeTable" width="100%" cellSpacing="0" cellPadding="2">
        <tr>
          <td class="tableHeader">序号</td>
          <td class="tableHeader">所属分类</td>
          <td class="tableHeader">新闻标题</td>
          <td class="tableHeader">副标题</td>
          <td class="tableHeader">编辑</td>
          <td class="tableHeader">发布日期</td>
          <td class="tableHeader">修改</td>
        </tr>
<#if (page?? && page.result?size > 1)>
  <#list (page.result)! as item>
    <#if item_index%2==2><#assign className="odd"><#else><#assign className="even"></#if>
        <tr class="${className}" onmouseover="this.className='highlight'" onmouseout="this.className='${className}'">
          <td>${item_index + 1}</td>
          <td>${item.category.name}</td>
          <td>${item.name}</td>
          <td>${item.subtitle}</td>
          <td>${item.editor}</td>
          <td>${item.updateDate?date}</td>
          <td><a href="${ctx}/news/edit.htm?id=${item.id}"><img src="${ctx}/images/icon/16x16/modify.gif" border="0"/></a></td>
        </tr>
  </#list>
<#else>
        <tr>
	  <td colspan="7" class="even" style="text-align:center">无记录</td>
        </tr>
</#if>
      </table>
    </div>
  </body>
</html>
