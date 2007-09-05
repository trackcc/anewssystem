<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>新闻分类管理</title>
    <#include "/include/extjs.ftl"/>
    <script type="text/javascript" src="${ctx}/widgets/anews/category.js"></script>
  </head>
  <body>
    <div id="main"></div>
  </body>
</html>

