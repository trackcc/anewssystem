<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl">
    <TITLE>新闻属性设置</TITLE>
    <link type="text/css" href="${ctx}/css/admin.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/inc/validation.jsp"></script>
    <@validator.javascript formName="newsConfig" staticJavascript="false"/>
  </head>
  <body>
<div class="pageTitle">新闻属性设置</div>
<#if config??>
  <@m.showError "config.*"/>
</#if>
<#if (config.id)?? && config.id != 0>
  <#assign action="update">
<#else>
  <#assign action="insert">
</#if>
<#include "/include/messages.ftl"/>
<form name="newsConfig"
    enctype="multipart/form-data"
    action="${ctx}/newsconfig/${action}.htm"
    method="post" onsubmit="return validateConfig(this)">
  <@jodd.form beans="config" scopes="request">
      <input type="hidden" name="id">
    <table class="border" width="90%" cellSpacing="0" cellPadding="2" align="center">
    <tr>
      <td class="left" width="20%"><@spring.messageText code="newsConfig.newsNeedAudit" text="newsNeedAudit"/>:</td>
      <td class="right">
        <input type="checkbox" name="newsNeedAudit" id="newsNeedAudit" value="1">
        <label class="star" for="newsNeedAudit">是 * 选中则新闻需要先进行审核才能在网上查看</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="newsConfig.commentNeedAudit" text="commentNeedAudit"/>:</td>
      <td class="right">
        <input type="checkbox" name="commentNeedAudit" id="commentNeedAudit" value="1">
        <label class="star" for="commentNeedAudit">是 * 选中则评论需要先进行审核才能在网上查看</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="newsConfig.couldComment" text="couldComment"/>:</td>
      <td class="right">
        <input type="checkbox" name="couldComment" id="couldComment" value="1">
        <label class="star" for="couldComment">是 * 选中则可以发表评论</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="newsConfig.categoryStrategy" text="categoryStrategy"/>:</td>
      <td class="right">
        <input type="radio" name="categoryStrategy" id="bitCode" value="0">
        <label for="bitCode">位编码</label>
        <input type="radio" name="categoryStrategy" id="charCode" value="1">
        <label for="charCode">字符编码</label>
        <input type="radio" name="categoryStrategy" id="recursion" value="2">
        <label for="recursion">无编码</label>
        <label class="star">*</label>
      </td>
    </tr>
	<tr>
      <td class="left" width="20%"><@spring.messageText code="newsConfig.templateName" text="templateName"/>:</td>
      <td class="right">
	    <input type="radio" name="templateName" id="defaultTemplate" value="default"/>
		<label for="defaultTemplate">默认模板</label>
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
