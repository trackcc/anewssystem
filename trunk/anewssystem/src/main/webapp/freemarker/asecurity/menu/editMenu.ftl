<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl">
    <TITLE>Menu</TITLE>
    <link type="text/css" href="${ctx}/css/admin.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/inc/validation.jsp"></script>
    <@validator.javascript formName="menu" staticJavascript="false"/>
  </head>
  <body>
<div class="pageTitle">Menu信息</div>
<#if menu??>
  <@m.showError "menu.*"/>
</#if>
<#if (menu.id)?? && menu.id != 0>
  <#assign action="update">
<#else>
  <#assign action="insert">
</#if>
<#include "/include/messages.ftl"/>
<form name="menu"
    enctype="multipart/form-data"
    action="${ctx}/menu/${action}.htm"
    method="post" onsubmit="return validateMenu(this)">
  <@jodd.form beans="menu" scopes="request">
      <input type="hidden" name="id">
    <table class="border" width="90%" cellSpacing="0" cellPadding="2" align="center">
    <tr>
      <td class="left" width="20%"><@spring.messageText code="menu.parent" text="parent"/>:</td>
      <td class="right">
        <select name="parent_id" id="parent_id">
          <option value="">根菜单</option>
          <#if action=="update">
            <#list parents! as parent>
              <#if parent.id!=menu.id>
                <#if menu.parent?? && parent.id==menu.parent.id>
                  <option value="${parent.id}" selected>${parent.title}</option>
                <#else>
                  <option value="${parent.id}">${parent.title}</option>
                </#if>
              </#if>
            </#list>
          <#else>
            <#list parents! as parent>
              <option value="${parent.id}">${parent.title}</option>
            </#list>
          </#if>
        </select>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="menu.name" text="name"/>:</td>
      <td class="right">
        <input type="text" name="name" id="name" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="menu.seq" text="seq"/>:</td>
      <td class="right">
        <input type="text" name="seq" id="seq" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="menu.title" text="title"/>:</td>
      <td class="right">
        <input type="text" name="title" id="title" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="menu.tip" text="tip"/>:</td>
      <td class="right">
        <input type="text" name="tip" id="tip" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="menu.image" text="image"/>:</td>
      <td class="right">
        <input type="text" name="image" id="image" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="menu.forward" text="forward"/>:</td>
      <td class="right">
        <input type="text" name="forward" id="forward" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="menu.target" text="target"/>:</td>
      <td class="right">
        <input type="text" name="target" id="target" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="menu.descn" text="descn"/>:</td>
      <td class="right">
        <input type="text" name="descn" id="descn" size="35">
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
