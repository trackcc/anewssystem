<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <TITLE>关键字</TITLE>
    <#include "/include/extjs.ftl"/>
    <script type="text/javascript" src='${ctx}/dwr/interface/NewsTagHelper.js'></script>
    <script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
    <script type='text/javascript' src='${ctx}/dwr/util.js'></script>
    <script type='text/javascript' src='${ctx}/widgets/grid/DWRProxy.js'></script>
    <script type="text/javascript" src="${ctx}/widgets/grid/InlineEditor.js"></script>
  </head>

  <body>
    <div id="grid-example"></div>
  </body>
</html>
