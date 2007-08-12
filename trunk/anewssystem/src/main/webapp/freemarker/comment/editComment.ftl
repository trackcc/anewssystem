<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl">
    <TITLE>Comment</TITLE>
    <link type="text/css" href="${ctx}/css/admin.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/inc/validation.jsp"></script>
    <@validator.javascript formName="comment" staticJavascript="false"/>
  </head>
  <body>
<div class="pageTitle">Comment信息</div>
<#if comment??>
  <@m.showError "comment.*"/>
</#if>
<#if (comment.id)?? && comment.id != 0>
  <#assign action="update">
<#else>
  <#assign action="insert">
</#if>
<#include "/include/messages.ftl"/>
<form name="comment"
    enctype="multipart/form-data"
    action="${ctx}/comment/${action}.htm"
    method="post" onsubmit="return validateComment(this)">
  <@jodd.form beans="comment" scopes="request">
      <input type="hidden" name="id">
    <table class="border" width="90%" cellSpacing="0" cellPadding="2" align="center">
    <tr>
      <td class="left" width="20%"><@spring.messageText code="comment.news" text="news"/>:</td>
      <td class="right">
        <input type="text" name="news" id="news" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="comment.name" text="name"/>:</td>
      <td class="right">
        <input type="text" name="name" id="name" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="comment.content" text="content"/>:</td>
      <td class="right">
        <input type="text" name="content" id="content" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="comment.username" text="username"/>:</td>
      <td class="right">
        <input type="text" name="username" id="username" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="comment.updateDate" text="updateDate"/>:</td>
      <td class="right">
        <input type="text" name="updateDate" id="updateDate" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="comment.status" text="status"/>:</td>
      <td class="right">
        <input type="text" name="status" id="status" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="comment.ip" text="ip"/>:</td>
      <td class="right">
        <input type="text" name="ip" id="ip" size="35">
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
