<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>采购管理系统</title>
    <#include "/include/extjs.ftl"/>
    <SCRIPT type="text/javascript" language="javascript">
        if (window.top != window) {
            window.top.location = window.location;
        }
    </script>
    <link rel="stylesheet" type="text/css" href="${ctx}/widgets/ux/accordion.css" />
    <script type='text/javascript' src='${ctx}/widgets/ux/Ext.ux.Accordion.js'></script>
    <script type='text/javascript' src='${ctx}/widgets/ux/Ext.ux.InfoPanel.js'></script>

    <script type='text/javascript' src='${ctx}/dwr/interface/MenuHelper.js'></script>
    <script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
    <script type='text/javascript' src='${ctx}/dwr/util.js'></script>

    <script type="text/javascript" src="${ctx}/widgets/aerp/index.js"></script>
  </head>
  <body scroll='no' id='top'>
    <div id='header' class='ylayout-inactive-content'>
      <div id='logo'></div>
      <div style='padding-top:4px;'>
        <!--<img src="${ctx}/images/banner.jpg">--><span style="font-size:24px;">imut.edu.cn 中小型染整企业电子采购供应链管理系统</span>
      </div>
    </div>
    <div id='west'>
      <div id='menu-tree'>
        <div class='waitting'>正在载入菜单，请稍候...</div>
      </div>
      <div id="toolbar"></div>
    </div>
    <iframe id='main' frameborder='no' scrolling='yes'></iframe>
    <div id="error_message" style="display:block"></div>
<script type="text/javascript">
//DWREngine.setErrorHandler(errorHandler);
function errorHandler(errorString, exception) {
    console.error(errorString);
    console.error(exception);
}
</script>
</body>
</html>

