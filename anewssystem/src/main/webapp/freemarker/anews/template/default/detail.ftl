<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
  <head>
    <#include "/include/meta.ftl">
    <title>${news.name}</title>
  </head>
  <body>
    <a href="${ctx}/news/index.htm">首页</a> - &gt; 新闻内容
    <hr/>
    <h1>${news.name}</h1>
    <h3>${news.subtitle}</h3>
    <p>发表时间：${news.updateDate?datetime}</p>
    <p>新闻来源：${news.source}</p>
    <p>责任编辑：${news.editor}</p>
    <p>新闻简介：${news.summary}</p>
    <div>${news.content}</div>
  </body>
</html>
