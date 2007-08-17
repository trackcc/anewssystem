<#assign ctx=springMacroRequestContext.getContextPath()>
<#assign ext="${ctx}/widgets/extjs/1.1-rc1">
<#include "/include/taglibs.ftl">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>新闻分类管理</title>
    <link rel="stylesheet" type="text/css" href="${ext}/resources/css/ext-all.css" />
    <script type="text/javascript" src="${ext}/adapter/yui/yui-utilities.js"></script>
    <script type="text/javascript" src="${ext}/adapter/yui/ext-yui-adapter.js"></script>
    <script type="text/javascript" src="${ext}/ext-all.js"></script>
    <script type="text/javascript" src="${ctx}/widgets/tree/category.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/widgets/examples.css" />
    <style type="text/css">
.x-tree-node {
  font-size:12px;
}.x-btn button {
  font-size:12px;
}
    </style>
    <script type="text/javascript">
Ext.BLANK_IMAGE_URL = '${ext}/resources/images/default/s.gif';
    </script>
  </head>
  <body>
    <div id="main"></div>
  </body>
</html>

