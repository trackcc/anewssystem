<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>目录管理</title>
    <#include "/include/extjs.ftl"/>
    <script type="text/javascript" src="${ctx}/widgets/asecurity/menu-20070916.js"></script>
  </head>
  <body>
    <div id="loading">
      <div class="waitting">请稍候...</div>
    </div>
    <div id="tabs">
      <div id="tab1">
        <div id="toolbar"></div>
        <div id="lighttree" style="border: 0px solid #cccccc; overflow: hidden; width:auto;height:100%;"></div>
      </div>
      <div id="tab2"></div>
    </div>
    <!-- 弹出框内容 -->
    <table id='content' width="95%" border="0" align="center" valign="middlen" cellpadding="0" cellspacing="10">
      <tr height="50%"><td>&nbsp;</td></tr>
      <tr>
        <td width="20%" align="right"><label for="url" style="font-size:12px;">链接地址：</label></td>
        <td width="80%" colspan="3">
          <div class="x-form-item">
            <input id="url" type="text" size="65">
          </div>
        </td>
      </tr>
      <tr>
        <td width="20%" align="right"><label for="name" style="font-size:12px;">菜单名称：</label></td>
        <td width="30%">
          <div class="x-form-item">
            <input id="name" type="text" size="20">
          </div>
        </td>
        <td width="20%" align="right"><label for="qtip" style="font-size:12px;">提示信息：</label></td>
        <td width="30%">
          <div class="x-form-item">
            <input id="qtip" type="text" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="20%" align="right"><label for="name" style="font-size:12px;">图片：</label></td>
        <td width="30%">
          <div class="x-form-item">
            <input id="image" type="text" size="20">
          </div>
        </td>
        <td width="20%" align="right"><label for="qtip" style="font-size:12px;">描述：</label></td>
        <td width="30%">
          <div class="x-form-item">
            <input id="descn" type="text" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td align="right" width="20%"><label for="descn" style="font-size:12px;">id：</label></td>
        <td width="80%" colspan="3" colspan="3">
          <div class="x-form-item">
            <input id="id" type="text" size="65" readonly>
          </div>
        </td>
      </tr>
      <tr height="50%"><td>&nbsp;</td></tr>
    </table>
    <div id="error_message" style="display:block"></div>
<script type="text/javascript">
//DWREngine.setErrorHandler(errorHandler);
//function errorHandler(errorString, exception) {
//    console.error(errorString);
//    console.error(exception);
//}
</script>
  </body>
</html>
