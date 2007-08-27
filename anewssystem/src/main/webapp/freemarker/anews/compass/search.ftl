<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
  <head>
    <#include "/include/meta.ftl">
    <title>Compass Search</title>
  </head>
  <body>
    <P>
      <H2>Compass Search:</H2>
    <FORM method="GET">
      <INPUT type="text" size="20" name="query" value="${command.query!}" />
      <INPUT type="submit" value="Search"/>
    </FORM>
    <#if searchResults??>
      Search took ${searchResults.searchTime}ms.
      <#list searchResults.hits as item>
        <#if item.alias=="a_news">
          <p>
            <a href="${ctx}/news/edit.htm?id=${item.data.id}"><#if item.highlightedText.name??>${item.highlightedText.name}<#else>${item.data.name}</#if> (News)</a>
            <br>content:<#if item.highlightedText.content??>${item.highlightedText.content}<#else>${item.data.content}</#if>
            <br>source:<#if item.highlightedText.source??>${item.highlightedText.source}<#else>${item.data.source}</#if>
            <br>editor:<#if item.highlightedText.editor??>${item.highlightedText.editor}<#else>${item.data.editor}</#if>
        </#if>
      </#list>
      <#if searchResults.pages??>
        <BR><BR><BR>
        <table>
          <tr>
            <#list searchResults.pages! as item>
              <td>
                <#if item.selected>
                  ${item.from}-${item.to}
                <#else>
                  <FORM method="GET">
                    <input type="hidden" name="query" value="${command.value!}" />
                    <input type="hidden" name="page" value="${item_index}" />
                    <INPUT type = "submit" value="${item.from}-${item.to}" />
                  </FORM>
                </#if>
              </td>
            </#list>
          </tr>
        </table>
      </#if>
    </#if>
    <P>
      <BR>
  </body>
</html>
