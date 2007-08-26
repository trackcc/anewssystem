${r"<#assign"} ctx=springMacroRequestContext.getContextPath()>
${r"<#include"} "/include/taglibs.ftl">
<#assign ctx=r"${ctx}">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    ${r"<#include"} "/include/meta.ftl">
    <TITLE>${clz}</TITLE>
    <link type="text/css" href="${ctx}/css/admin.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/inc/validation.jsp"></script>
    ${"<@validator.javascript"} formName="${clz?uncap_first}" staticJavascript="false"/>
  </head>
  <body>
<div class="pageTitle">${clz}信息</div>
${r"<#if"} ${clz?uncap_first}??>
  ${r"<@m.showError"} "${clz?uncap_first}.*"/>
${r"</#if>"}
${r"<#if"} (${clz?uncap_first}.id)?? && ${clz?uncap_first}.id != 0>
  ${r"<#assign"} action="update">
${r"<#else>"}
  ${r"<#assign"} action="insert">
${r"</#if>"}
${r"<#include"} "/include/messages.ftl"/>
<form name="${clz?uncap_first}"
    enctype="multipart/form-data"
    action="${ctx}/${clz?lower_case}/${r"${action}"}.htm"
    method="post" onsubmit="return validate${clz}(this)">
  ${r"<@jodd.form"} beans="${clz?uncap_first}" scopes="request">
      <input type="hidden" name="id">
    <table class="border" width="90%" cellSpacing="0" cellPadding="2" align="center">
<#list fields as field>
  <#if field.name != "id" && field.type.name != "java.util.Set">
    <tr>
      <td class="left" width="20%">${r"<@spring.messageText"} code="${clz?uncap_first}.${field.name}" text="${field.name}"/>:</td>
      <td class="right">
        <input type="text" name="${field.name}" id="${field.name}" size="35">
        <label class="star">*</label>
      </td>
    </tr>
  </#if>
</#list>
    <tr>
      <td colspan="2" class="bottom">
        <input type="submit" class="submitButton" value="确定" style="margin-right:60px"/>
        <input type="button" class="submitButton" value="返回" onclick="history.back();"/>
      </td>
    </tr>
  </table>
${r"</@jodd.form>"}
</form>

  </body>
</html>
