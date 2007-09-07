<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>资源管理</title>
    <#include "/include/extjs.ftl"/>
<#--
    <link rel="stylesheet" type="text/css" href="${ext}/organize/dependency.css" />
    <link rel="stylesheet" type="text/css" href="${extpage}/grid-examples.css" />
    <script type="text/javascript" src="${exthome}/source/data/Connection.js"></script>
    <script type="text/javascript" src="${exthome}/TreeDWRLoader.js"></script>
-->
    <script src="${ctx}/dwr/interface/ResourceHelper.js"></script>
    <script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
    <script type='text/javascript' src='${ctx}/dwr/util.js'></script>
    <script type="text/javascript" src="${ctx}/widgets/asecurity/DWRProxy.js"></script>
    <script type="text/javascript" src="${ctx}/widgets/asecurity/resource.js"></script>
  </head>
  <body>
    <div id="main-ct" style="width:840px;height:510px;"></div>
    <div id="a-addInstance-dlg" style="visibility:hidden;">
      <div class="x-dlg-hd">新增资源</div>
      <div class="x-dlg-bd">
        <div id="a-addInstance-inner" class="x-layout-inactive-content">
          <div id="a-addInstance-form"></div>
        </div>
      </div>
    </div>
    <div id="a-updateInstance-dlg" style="visibility:hidden;">
      <div class="x-dlg-hd">修改资源</div>
      <div class="x-dlg-bd">
        <div id="a-updateInstance-inner" class="x-layout-inactive-content">
          <div id="a-updateInstance-form"></div>
        </div>
      </div>
    </div>
    <div id="userAuthRole-dlg" style="visibility:hidden;">
      <div class="x-dlg-hd">用户授权</div>
      <div class="x-dlg-bd">
        <div id="userAuthRole-inner" class="x-layout-inactive-content">
          <div id="userAuthRole-grid" style="width:430px;height:220px;"></div>
        </div>
      </div>
    </div>
  </body>
</html>
