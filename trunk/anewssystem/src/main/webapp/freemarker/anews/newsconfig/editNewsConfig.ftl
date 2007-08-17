<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl">
    <TITLE>NewsConfig</TITLE>
    <link type="text/css" href="${ctx}/css/admin.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/inc/validation.jsp"></script>
    <@validator.javascript formName="newsConfig" staticJavascript="false"/>
  </head>
  <body>
<div class="pageTitle">NewsConfig信息</div>
<#if newsConfig??>
  <@m.showError "newsConfig.*"/>
</#if>
<#if (newsConfig.id)?? && newsConfig.id != 0>
  <#assign action="update">
<#else>
  <#assign action="insert">
</#if>
<#include "/include/messages.ftl"/>
<form name="newsConfig"
    enctype="multipart/form-data"
    action="${ctx}/newsconfig/${action}.htm"
    method="post" onsubmit="return validateNewsConfig(this)">
  <@jodd.form beans="newsConfig" scopes="request">
      <input type="hidden" name="id">
    <table class="border" width="90%" cellSpacing="0" cellPadding="2" align="center">
    <tr>
      <td class="left" width="20%"><@spring.messageText code="newsConfig.newsNeedAudit" text="newsNeedAudit"/>:</td>
      <td class="right">
        <input type="text" name="newsNeedAudit" id="newsNeedAudit" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="newsConfig.commentNeedAudit" text="commentNeedAudit"/>:</td>
      <td class="right">
        <input type="text" name="commentNeedAudit" id="commentNeedAudit" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="newsConfig.couldComment" text="couldComment"/>:</td>
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
