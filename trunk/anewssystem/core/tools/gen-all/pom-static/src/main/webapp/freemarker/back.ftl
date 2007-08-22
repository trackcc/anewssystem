<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
  <head>
    <#include "/include/meta.ftl">
    <title>back</title>
  </head>
  <body>
    <script type="text/javascript">
<#if msg??>
	  alert('${msg}');
</#if>
      history.back();
    </script>
  </body>
</html>

