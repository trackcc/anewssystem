<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>用户管理</title>
    <#include "/include/extjs.ftl"/>
    <script type="text/javascript" src="${ctx}/widgets/asecurity/user-20070921.js"></script>
  </head>
  <body>
    <div id="loading">
      <div class="waitting">请稍候...</div>
    </div>
    <div id="tabs">
      <div id="tab1">
        <div id="toolbar"></div>
        <div id="lightgrid" style="border: 0px solid #cccccc; overflow: hidden; width:auto;height:100%;"></div>
      </div>
      <div id="tab2"></div>
    </div>
    <!-- 新增 -->
    <div id="add-content" style="display:none">
    <table width="95%" border="0" align="center" valign="middlen" cellpadding="0" cellspacing="10">
      <tr height="50%"><td>&nbsp;</td></tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">帐号：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="username" type="text" size="20"><span id="isUsernameValid"></span>
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">输入密码：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="password" type="password" size="20">
          </div>
        </td>
        <td width="15%" align="right" style="font-size:12px;">确认密码：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="confirmpassword" type="password" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">姓名：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="truename" type="text" size="20">
          </div>
        </td>
        <td width="15%" align="right" style="font-size:12px;">性别：</td>
        <td width="35%">
          <div class="x-form-item">
            <table border="0">
              <tr>
                <td style="font-size:12px;">
                  <input id="sex0" name="sex" type="radio" value="0">
                </td>
                <td style="font-size:12px;">
                  <input id="sex1" name="sex" type="radio" value="1">
                </td>
              </tr>
            </table>
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">生日：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="birthday" type="text" size="20">
          </div>
        </td>
        <td width="15%" align="right" style="font-size:12px;">电话：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="tel" type="text" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">手机：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="mobile" type="text" size="20">
          </div>
        </td>
        <td width="15%" align="right" style="font-size:12px;">邮箱：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="email" type="text" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">部门：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="dept" type="text" size=""/>
          </div>
        </td>
        <td width="15%" align="right" style="font-size:12px;">职务：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="duty" type="text" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">备注：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="descn" type="text" size="60">
          </div>
        </td>
      </tr>
      <tr height="50%"><td>&nbsp;</td></tr>
    </table>
    </div>
    <!-- 更新base部分-->
    <div id="edit-base-content" style="display:none">
    <table width="60%" border="0" align="left" valign="middlen" cellpadding="0" cellspacing="10">
      <tr height="50%"><td colspan="2">&nbsp;</td></tr>
      <tr>
        <td width="40%" align="right" style="font-size:12px;">帐号：</td>
        <td width="60%" colspan="3">
          <div class="x-form-item">
            <input id="username2" type="text" size="20" disabled>
          </div>
        </td>
      </tr>
      <tr>
        <td width="40%" align="right" style="font-size:12px;">旧密码：</td>
        <td width="60%" colspan="3">
          <div class="x-form-item">
            <input id="oldpassword2" type="password" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="40%" align="right" style="font-size:12px;">新密码：</td>
        <td width="60%" colspan="3">
          <div class="x-form-item">
            <input id="password2" type="password" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="40%" align="right" style="font-size:12px;">确认密码：</td>
        <td width="60%" colspan="3">
          <div class="x-form-item">
            <input id="confirmpassword2" type="password" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="40%" align="right" style="font-size:12px;">姓名：</td>
        <td width="60%">
          <div class="x-form-item">
            <input id="truename2" type="text" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="40%" align="right" style="font-size:12px;">性别：</td>
        <td width="60%">
          <div class="x-form-item">
            <table border="0">
              <tr>
                <td style="font-size:12px;">
                  <input id="sex20" name="sex2" type="radio" value="0">
                </td>
                <td style="font-size:12px;">
                  <input id="sex21" name="sex2" type="radio" value="1">
                </td>
              </tr>
            </table>
          </div>
        </td>
      </tr>
      <tr>
        <td align="right" width="40%">id：</td>
        <td width="60%" colspan="3">
          <div class="x-form-item">
            <input id="id2" type="text" size="20" readonly>
          </div>
        </td>
      </tr>
      <tr height="50%"><td>&nbsp;</td></tr>
    </table>
    </div>
    <!--修改detail部分-->
    <div id="edit-detail-content" style="display:none">
    <table width="95%" border="0" align="center" valign="middlen" cellpadding="0" cellspacing="10">
      <tr height="50%"><td>&nbsp;</td></tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">生日：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="birthday2" type="text" size="20">
          </div>
        </td>
        <td width="15%" align="right" style="font-size:12px;">电话：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="tel2" type="text" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">手机：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="mobile2" type="text" size="20">
          </div>
        </td>
        <td width="15%" align="right" style="font-size:12px;">邮箱：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="email2" type="text" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">部门：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="dept2" type="text" size=""/>
          </div>
        </td>
        <td width="15%" align="right" style="font-size:12px;">职务：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="duty2" type="text" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">备注：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="descn2" type="text" size="60">
          </div>
        </td>
      </tr>
      <tr height="50%"><td>&nbsp;</td></tr>
    </table>
    </div>
    <div id="role-dlg" style="visibility:hidden;">
      <div class="x-dlg-hd">选择角色</div>
      <div class="x-dlg-bd">
        <div id="role-inner" class="x-layout-inactive-content">
          <div id="role-grid" style="width:630px;height:380px;"></div>
        </div>
      </div>
    </div>
    <div id="error_message" style="display:block"></div>
<script type="text/javascript">
//DWREngine.setErrorHandler(errorHandler);
function errorHandler(errorString, exception) {
    console.error(errorString);
    console.error(exception);
}
</script>
</body>
</html>
