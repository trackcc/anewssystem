<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
  <head>
    <#include "/include/meta.ftl">
    <title> 管理后台 </title>
  </head>

  <frameset rows="28,*" cols="*" frameborder="NO" border="0" framespacing="0" name="main">
    <frame src="${ctx}/admin/top.htm" name="topFrame" scrolling="NO" noresize>
    <frameset cols="154,*" frameborder="NO" border="0" framespacing="0" name="setyou">
      <frame src="${ctx}/admin/menu.htm" name="leftFrame" scrolling="NO" noresize>
      <frame src="${ctx}/admin/main.htm" name="dblselect" noresize>
    </frameset>
  </frameset>
  <noframes>
    <body>
    </body>
  </noframes>
</html>
