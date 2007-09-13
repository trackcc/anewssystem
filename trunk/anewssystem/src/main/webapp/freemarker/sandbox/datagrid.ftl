<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <#include "/include/meta.ftl">
    <title>welcome</title>
    <#include "/include/extjs.ftl"/>
    <script type='text/javascript' src='${ctx}/widgets/ux/Ext.Accordion.js'></script>
    <script type='text/javascript' src='${ctx}/widgets/ux/Ext.InfoPanel.js'></script>

    <script type='text/javascript' src='${ctx}/dwr/interface/MenuHelper.js'></script>
    <script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
    <script type='text/javascript' src='${ctx}/dwr/util.js'></script>
    <script type='text/javascript' src='${ctx}/widgets/ux/Ext.grid.Grid.js'></script>
    <script type='text/javascript' src='${ctx}/widgets/ux/Ext.Common.js'></script>
    <script type='text/javascript' src='${ctx}/widgets/ux/Ext.DaoFactory.js'></script>
    <script type='text/javascript' src='${ctx}/widgets/ux/Ext.DataGrid.js'></script>
    <script type='text/javascript' src='${ctx}/widgets/ux/Ext.Forms.js'></script>
    <script type='text/javascript' src='${ctx}/widgets/ux/DWRProxy.js'></script>

    <script type="text/javascript" src="${ctx}/widgets/sandbox/menuManage.js"></script>
  </head>
  <body>
    <div id="loading">
      <div class="waitting">请稍候...</div>
    </div>
    <div id="tabs">
      <div id="tab1">
        <div id="toolbar"></div>
        <div id="datagrid" style="border: 0px solid #cccccc; overflow: hidden; width:auto;height:100%;"></div>
        <div id="resize-grid"></div>
      </div>
      <div id="tab2"></div>
    </div>
    <!--
    allowBlank: false       //不允许为空
    vtype:
            'alpha'         //只允许输入英文字母
            'alphanum'      //只允许输入英文字母和数字
            'integer'       //整型
            'number'        //数值型
            'email'         //校验email地址
            'url'           //校验http地址
            'chn'           //汉字和英文字母
     -->
    <!-- 增加、修改、查看 弹出框内容-->
    <table id='content' width="95%" border="0" align="center" valign="middlen" cellpadding="0" cellspacing="10">
      <tr height="50%"><td>&nbsp;</td></tr>
      <tr>
        <td align="right" width="15%">访问路径：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="forward" type="text" size="75" vType="chn" allowBlank="true" alt="访问路径">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right">图片名称：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="image" type="text" size="25" vtype="chn" allowBlank="false" alt="图片名称" defvalue='user.gif'>
          </div>
        </td>
        <td width="15%" align="right">菜单名称：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="name" type="text" size="25" vtype="chn" allowblank="false" alt="菜单名称">
          </div>
        </td>
      </tr>
      <tr>
        <td align="right">上级id：</td>
        <td>
          <div class="x-form-item">
            <input id="parentid" type="text" size="25" vtype="integer" allowblank="false" alt="上级id">
          </div>
        </td>
        <td align="right">排序：</td>
        <td>
          <div class="x-form-item">
            <input id="theSort" type="text" size="25" vtype="integer" allowblank="false" alt="排序">
          </div>
        </td>
      </tr>
<#--
      <tr>
        <td align="right" width="15%">操作日期：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="oper" type="text" size="25" vtype="date" allowblank="false" alt="操作日期">
          </div>
        </td>
      </tr>
-->
      <tr>
        <td align="right" width="15%">id：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="id" type="text" size="75" vtype="chn" allowblank="true" readonly>
          </div>
        </td>
      </tr>
      <tr height="50%"><td>&nbsp;</td></tr>
    </table>
    <div id="error_message" style="display:block"></div>
<script type="text/javascript">
DWREngine.setErrorHandler(errorHandler);
function errorHandler(errorString, exception) {
    console.error(errorString);
    console.error(exception);
}
</script>
  </body>
</html>
