<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
  <head>
    <#include "/include/meta.ftl">
    <title>新闻首页</title>
    <style type="text/css">
p {
  padding: 0px;
  margin: 0px;
}
    </style>
  </head>
  <body>
    <h1>A News System</h1><a href="${ctx}/sandbox/index.htm">[测试后台]</a>
    <hr/>
<#list newsCategoryList! as category>
    <div class="category">
      <h3><a href="${ctx}/news/more.htm?id=${category.id}">${category.name}</a></h3>
  <#assign count=0/>
  <#list category.newses! as news>
    <#assign count=news_index/>
        <p>&nbsp;<a href="${news.link}">${news.name}</a></p>
    <#if (count>5)><#break/></#if>
  </#list>
  <#if (count<5)>
    <#list count..5 as x>
        <p>&nbsp;</p>
    </#list>
  </#if>
      <div style="text-align:right"><a href="${ctx}/news/more.htm?id=${category.id}">更多</a></div>
    </div>
    <hr/>
</#list>
  </body>
</html>
