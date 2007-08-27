<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
  <head>
    <#include "/include/meta.ftl">
    <title>compass index</title>
  </head>
  <body>
    <P>
      <H2>Compass Index</H2>
    <P>
      Use the Index button to index the database using Compass::Gps. The operation will
      delete the current index and reindex the database based on the mappings and devices
      defined in the Compass::Gps configuration context.
    <FORM method="POST" action="${ctx}/compassindex.htm">
      <input type="hidden" name="doIndex" value="true" />
      <INPUT type="submit" value="Index"/>
    </FORM>
    <#if indexResults??>
      <P>Indexing took: ${indexResults.indexTime}ms.
    </#if>
    <P>
      <BR>
  </body>
</html>
