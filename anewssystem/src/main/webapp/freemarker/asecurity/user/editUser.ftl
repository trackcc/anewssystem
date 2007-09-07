<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl">
    <TITLE>User</TITLE>
    <link type="text/css" href="${ctx}/css/admin.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/inc/validation.jsp"></script>
    <@validator.javascript formName="user" staticJavascript="false"/>
    <#include "/include/calendar.ftl">
  </head>
  <body>
<div class="pageTitle">User信息</div>
<#if user??>
  <@m.showError "user.*"/>
</#if>
<#if (user.id)?? && user.id != 0>
  <#assign action="update">
<#else>
  <#assign action="insert">
</#if>
<#include "/include/messages.ftl"/>
<form name="user"
    enctype="multipart/form-data"
    action="${ctx}/user/${action}.htm"
    method="post" onsubmit="return validateUser(this)">
  <@jodd.form beans="user" scopes="request">
      <input type="hidden" name="id">
    <table class="border" width="90%" cellSpacing="0" cellPadding="2" align="center">
<#--
    <tr>
      <td class="left" width="20%"><@spring.messageText code="user.dept" text="dept"/>:</td>
      <td class="right">
        <input type="text" name="dept" id="dept" size="35">
        <label class="star">*</label>
      </td>
    </tr>
-->
    <tr>
      <td class="left" width="20%"><@spring.messageText code="user.username" text="username"/>:</td>
      <td class="right">
        <input type="text" name="username" id="username" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="user.password" text="password"/>:</td>
      <td class="right">
        <#if action=="insert">
          <input type="password" name="password" size="26" value=""/>
          <label class="star">*</label>
        <#else>
          <a href="#" onclick="modpsw(this);">修改密码</a>
          <div id="pswddiv" style="display: none;">
            输入密码&nbsp;
            <input type="password" name="pswd" size="26" value=""/>
            <br />
            再次输入&nbsp;
            <input type="password" name="repeatpswd" size="26" value=""/>
          </div>
        </#if>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="user.status" text="status"/>:</td>
      <td class="right">
        <input type="text" name="status" id="status" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="user.code" text="code"/>:</td>
      <td class="right">
        <input type="text" name="code" id="code" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="user.truename" text="truename"/>:</td>
      <td class="right">
        <input type="text" name="truename" id="truename" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="user.sex" text="sex"/>:</td>
      <td class="right">
        <input type="text" name="sex" id="sex" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="user.birthday" text="birthday"/>:</td>
      <td class="right">
        <input type="text" name="birthday" id="birthday" size="35">
        <button id="birthdayBt" type="button" class="button">...</button>
<script type="text/javascript">
        Calendar.setup({
        inputField  : "birthday",   // id of the input field
        ifFormat    : "%Y-%m-%d",   // the date format
        button      : "birthdayBt"  // id of the button
        });
</script>
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="user.tel" text="tel"/>:</td>
      <td class="right">
        <input type="text" name="tel" id="tel" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="user.mobile" text="mobile"/>:</td>
      <td class="right">
        <input type="text" name="mobile" id="mobile" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="user.email" text="email"/>:</td>
      <td class="right">
        <input type="text" name="email" id="email" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="user.duty" text="duty"/>:</td>
      <td class="right">
        <input type="text" name="duty" id="duty" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="20%"><@spring.messageText code="user.descn" text="descn"/>:</td>
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

<script language="javascript">
    function modpsw(item) {
        if (item.innerHTML == '修改密码') {
            item.innerHTML = '取消修改';
            $('pswddiv').show();
        } else {
            item.innerHTML = '修改密码';
            $('pswddiv').hide();
        }
    }

    function validateForm(form) {
        if (!validateUser(form))
            return false;
        if ($('pswddiv').visible()) {
            var pswd = document.getElementsByName('pswd')[0].value;
            var repeatpswd = document.getElementsByName('repeatpswd')[0].value;
            if (pswd == '' || repeatpswd == '') {
                alert('密码不能为空!');
                return false;
            } else if (pswd != repeatpswd) {
                alert('两次密码输入不一致!');
                return false;
            } else {
                return true;
            }
        } else {
            document.getElementsByName('pswd')[0].value = '';
            document.getElementsByName('repeatpswd')[0].value = '';
            return true;
        }
    }
</script>
  </body>
</html>
