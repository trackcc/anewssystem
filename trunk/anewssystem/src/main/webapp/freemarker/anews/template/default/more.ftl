<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
  <head>
    <#include "/include/meta.ftl">
    <title>更多新闻</title>
    <style type="text/css">
p {
  padding: 0px;
  margin: 0px;
}
    </style>
  </head>
  <body>
    <h1>A News System - more newses</h1>
    <hr/>
    <a href="${ctx}/news/index.htm">首页</a> - &gt; 更多新闻
    <div class="category">
      <h3>${newsCategory.name}</h3>
<#list newsCategory.newses! as news>
        <p>&nbsp;<a href="${news.link}">${news.name}</a> <a href="${ctx}/news/detail.htm?id=${news.id}">[-]</a></p>
</#list>
    </div>
    <hr/>
  </body>
</html>
