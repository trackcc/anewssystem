<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>目录管理</title>
    <#include "/include/extjs.ftl"/>
    <script type="text/javascript" src="${ctx}/widgets/asecurity/menu.js"></script>
  </head>
  <body>
    <div id="main"></div>
    <div id="a-updateInstance-dialog" style="visibility:hidden;">
      <div class="x-dlg-hd">菜单信息</div>
      <div class="x-dlg-bd">
        <div id="a-updateInstance-inner" class="x-layout-inactive-content">
          <div id="a-updateInstance-form"></div>
        </div>
      </div>
    </div>
  </body>
</html>
