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
    <script type='text/javascript' src='${ctx}/widgets/grid/Ext.ux.CheckRowSelectionGrid.js'></script>
    <script type="text/javascript" src="${ctx}/widgets/grid/DialogEditor.js"></script>
  </head>

  <body>
    <div class="content">
      <h1>关键字管理</h1>
      <div id="grid-panel" style="width:100%;height:290px;" >
        <div id="editor-grid"></div>
      </div><br>
      <div id="a-addInstance-dlg" style="visibility:hidden;">
        <div class="x-dlg-hd">添加关键字</div>
        <div class="x-dlg-bd">
          <div id="a-addInstance-inner" class="x-layout-inactive-content">
            <div id="a-addInstance-form"></div>
          </div>
        </div>
      </div>
      <div id="a-updateInstance-dlg" style="visibility:hidden;">
        <div class="x-dlg-hd">更新关键字</div>
        <div class="x-dlg-bd">
          <div id="a-updateInstance-inner" class="x-layout-inactive-content">
            <div id="a-updateInstance-form"></div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
