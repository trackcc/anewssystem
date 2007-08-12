<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl">
    <TITLE>关键字</TITLE>
    <link type="text/css" href="${ctx}/css/admin.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/inc/validation.jsp"></script>
    <@validator.javascript formName="tag" staticJavascript="false"/>
  </head>
  <body>
<div class="pageTitle">关键字</div>
<#if tag??>
  <@m.showError "tag.*"/>
</#if>
<#if (tag.id)?? && tag.id != 0>
  <#assign action="update">
<#else>
  <#assign action="insert">
</#if>
<#include "/include/messages.ftl"/>
<form name="tag"
    enctype="multipart/form-data"
    action="${ctx}/tag/${action}.htm"
    method="post" onsubmit="return validateTag(this)">
  <@jodd.form beans="tag" scopes="request">
      <input type="hidden" name="id">
    <table class="border" width="90%" cellSpacing="0" cellPadding="2" align="center">
    <tr>
      <td class="left" width="20%"><@spring.messageText code="tag.name" text="name"/>:</td>
      <td class="right">
        <input type="text" name="name" id="name" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="tag.theSort" text="theSort"/>:</td>
      <td class="right">
        <input type="text" name="theSort" id="theSort" size="35">
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
