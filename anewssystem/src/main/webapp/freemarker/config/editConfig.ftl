<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl">
    <TITLE>Config</TITLE>
    <link type="text/css" href="${ctx}/css/admin.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/inc/validation.jsp"></script>
    <@validator.javascript formName="config" staticJavascript="false"/>
  </head>
  <body>
<div class="pageTitle">Config信息</div>
<#if config??>
  <@m.showError "config.*"/>
</#if>
<#if (config.id)?? && config.id != 0>
  <#assign action="update">
<#else>
  <#assign action="insert">
</#if>
<#include "/include/messages.ftl"/>
<form name="config"
    enctype="multipart/form-data"
    action="${ctx}/config/${action}.htm"
    method="post" onsubmit="return validateConfig(this)">
  <@jodd.form beans="config" scopes="request">
      <input type="hidden" name="id">
    <table class="border" width="90%" cellSpacing="0" cellPadding="2" align="center">
    <tr>
      <td class="left" width="20%"><@spring.messageText code="config.newsNeedAudit" text="newsNeedAudit"/>:</td>
      <td class="right">
        <input type="text" name="newsNeedAudit" id="newsNeedAudit" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="config.commnetNeedAudit" text="commnetNeedAudit"/>:</td>
      <td class="right">
        <input type="text" name="commnetNeedAudit" id="commnetNeedAudit" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="config.couldComment" text="couldComment"/>:</td>
      <td class="right">
        <input type="text" name="couldComment" id="couldComment" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td colspan="2" class="bottom">
        <input type="submit" class="submitButton" value="确定" style="margin-right:60px"/>
        <input type="button" class="submitButton" value="返回" onclick="history.back();"/>
      </td>
    </tr>
  </table>
</@jodd.form>
</form>

  </body>
</html>
