<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl">
    <TITLE>NewsCategory</TITLE>
    <link type="text/css" href="${ctx}/css/admin.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/inc/validation.jsp"></script>
    <@validator.javascript formName="newsCategory" staticJavascript="false"/>
  </head>
  <body>
<div class="pageTitle">NewsCategory信息</div>
<#if newsCategory??>
  <@m.showError "newsCategory.*"/>
</#if>
<#if (newsCategory.id)?? && newsCategory.id != 0>
  <#assign action="update">
<#else>
  <#assign action="insert">
</#if>
<#include "/include/messages.ftl"/>
<form name="newsCategory"
    enctype="multipart/form-data"
    action="${ctx}/newscategory/${action}.htm"
    method="post" onsubmit="return validateNewsCategory(this)">
  <@jodd.form beans="newsCategory" scopes="request">
      <input type="hidden" name="id">
    <table class="border" width="90%" cellSpacing="0" cellPadding="2" align="center">
    <tr>
      <td class="left" width="20%"><@spring.messageText code="newsCategory.newsCategoryByTopId" text="newsCategoryByTopId"/>:</td>
      <td class="right">
        <input type="text" name="newsCategoryByTopId" id="newsCategoryByTopId" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="newsCategory.newsCategoryByParentId" text="newsCategoryByParentId"/>:</td>
      <td class="right">
        <input type="text" name="newsCategoryByParentId" id="newsCategoryByParentId" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="newsCategory.name" text="name"/>:</td>
      <td class="right">
        <input type="text" name="name" id="name" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="newsCategory.theSort" text="theSort"/>:</td>
      <td class="right">
        <input type="text" name="theSort" id="theSort" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="newsCategory.status" text="status"/>:</td>
      <td class="right">
        <input type="text" name="status" id="status" size="35">
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
