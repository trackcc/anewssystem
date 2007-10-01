<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="zh">
  <head>
    <#include "/include/meta.ftl">
    <title>welcome</title>
    <#include "/include/extjs.ftl"/>
    <script type="text/javascript">
// 用这里调用/sandbox/index.ftl里定义的checkLogin()函数进行验证。
// 是不是比较乱啊？
window.top.index.checkLogin();
    </script>
  </head>
  <body>
    <div class="x-layout-panel-hd" style="height: 22px;"></div>
    <div class="welcome"></div>
  </body>
</html>
