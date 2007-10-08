<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl">
    <TITLE>TrackerIssue</TITLE>
    <link type="text/css" href="${ctx}/css/admin.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/inc/validation.jsp"></script>
    <@validator.javascript formName="trackerIssue" staticJavascript="false"/>
  </head>
  <body>
<div class="pageTitle">TrackerIssue信息</div>
<#if trackerIssue??>
  <@m.showError "trackerIssue.*"/>
</#if>
<#if (trackerIssue.id)?? && trackerIssue.id != 0>
  <#assign action="update">
<#else>
  <#assign action="insert">
</#if>
<#include "/include/messages.ftl"/>
<form name="trackerIssue"
    enctype="multipart/form-data"
    action="${ctx}/trackerissue/${action}.htm"
    method="post" onsubmit="return validateTrackerIssue(this)">
  <@jodd.form beans="trackerIssue" scopes="request">
      <input type="hidden" name="id">
    <table class="border" width="90%" cellSpacing="0" cellPadding="2" align="center">
    <tr>
      <td class="left" width="20%"><@spring.messageText code="trackerIssue.trackerProject" text="trackerProject"/>:</td>
      <td class="right">
        <input type="text" name="trackerProject" id="trackerProject" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="trackerIssue.name" text="name"/>:</td>
      <td class="right">
        <input type="text" name="name" id="name" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="trackerIssue.priority" text="priority"/>:</td>
      <td class="right">
        <input type="text" name="priority" id="priority" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="trackerIssue.severity" text="severity"/>:</td>
      <td class="right">
        <input type="text" name="severity" id="severity" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="trackerIssue.status" text="status"/>:</td>
      <td class="right">
        <input type="text" name="status" id="status" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="trackerIssue.assignTo" text="assignTo"/>:</td>
      <td class="right">
        <input type="text" name="assignTo" id="assignTo" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="trackerIssue.submitBy" text="submitBy"/>:</td>
      <td class="right">
        <input type="text" name="submitBy" id="submitBy" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="trackerIssue.addTime" text="addTime"/>:</td>
      <td class="right">
        <input type="text" name="addTime" id="addTime" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="trackerIssue.updateDate" text="updateDate"/>:</td>
      <td class="right">
        <input type="text" name="updateDate" id="updateDate" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="trackerIssue.content" text="content"/>:</td>
      <td class="right">
        <input type="text" name="content" id="content" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="trackerIssue.file" text="file"/>:</td>
      <td class="right">
        <input type="text" name="file" id="file" size="35">
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
