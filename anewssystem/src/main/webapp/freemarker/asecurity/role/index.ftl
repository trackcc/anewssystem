<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>角色管理</title>
    <#include "/include/extjs.ftl"/>
    <script type="text/javascript" src="${ctx}/widgets/asecurity/role-20070921.js"></script>
  </head>
  <body>
    <div id="loading">
      <div class="waitting">请稍候...</div>
    </div>
    <div id="tabs">
      <div id="tab1">
        <div id="toolbar"></div>
        <div id="lightgrid" style="border: 0px solid #cccccc; overflow: hidden; width:auto;height:100%;"></div>
      </div>
      <div id="tab2"></div>
    </div>
    <!-- 增加、修改、查看 弹出框内容-->
    <table id='content' width="95%" border="0" align="center" valign="middlen" cellpadding="0" cellspacing="10">
      <tr height="50%"><td>&nbsp;</td></tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">角色名称：</td>
        <td width="80%">
          <div class="x-form-item">
            <input id="name" type="text" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">描述：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="descn" type="text" size="60">
          </div>
        </td>
      </tr>
      <tr>
        <td align="right" width="15%">id：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="id" type="text" size="60" readonly>
          </div>
        </td>
      </tr>
      <tr height="50%"><td>&nbsp;</td></tr>
    </table>
    <div id="resource-dlg" style="visibility:hidden;">
      <div class="x-dlg-hd">角色授权</div>
      <div class="x-dlg-bd">
        <div id="resource-inner" class="x-layout-inactive-content">
          <div id="resource-grid" style="width:630px;height:380px;"></div>
        </div>
      </div>
    </div>
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
