<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <TITLE>用户信息</TITLE>
    <#include "/include/extjs.ftl"/>
    <script type="text/javascript" src="${ctx}/widgets/asecurity/user/grid_inline_editor.js"></script>
  </head>

  <body>
    <select name="sex" id="sex" style="display: none;">
      <option value="男">男</option>
      <option value="女">女</option>
    </select>
    <div id="mygridpanel" style="width:600px;height:300px;">
      <div id="myeditordata"></div>
    </div>
  </body>
</html>
