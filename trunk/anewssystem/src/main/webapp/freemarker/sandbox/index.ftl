<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="zh">
  <head>
    <#include "/include/meta.ftl">
    <title> 管理后台 </title>
    <#include "/include/extjs.ftl"/>
    <SCRIPT type="text/javascript" language="javascript">
        if (window.top != window) {
            window.top.location = window.location;
        }
    </script>
    <script type='text/javascript' src='${ctx}/widgets/ux/Ext.ux.Accordion.js'></script>
    <script type='text/javascript' src='${ctx}/widgets/ux/Ext.ux.InfoPanel.js'></script>

    <script type='text/javascript' src='${ctx}/dwr/interface/MenuHelper.js'></script>
    <script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
    <script type='text/javascript' src='${ctx}/dwr/util.js'></script>
    <script type='text/javascript' src='${ctx}/widgets/ux/Ext.Common.js'></script>
    <script type='text/javascript' src='${ctx}/widgets/lingo/form/Ext.lingo.FormUtils.js'></script>
    <script type='text/javascript' src='${ctx}/widgets/sandbox/index.js'></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/widgets/ux/accordion.css" />
  </head>
  <body scroll='no' id='top'>
    <div id='loading'>
      <div class='waitting'>请稍候...</div>
    </div>
    <div id='header' class='ylayout-inactive-content'>
      <div id='logo'></div>
      <div style='padding-top:4px;'>
        <!--<img src="${ctx}/images/banner.jpg">--><span style="font-size:24px;">新闻发布</span>
      </div>
    </div>
    <div id='south'>
      <div id='menu-tree'>
        <div class='waitting'>正在载入菜单，请稍候...</div>
      </div>
    </div>
    <div id="toolbar"></div>
    <iframe id='main' frameborder='no' scrolling='yes'></iframe>
    <div id='login-dlg' style='visibility:hidden;'>
      <div class='x-dlg-hd'></div>
      <div class='x-dlg-bd' style='overflow:hidden;'>
        <div class='x-dlg-tab' title='用户登陆'>
          <div id='standard-panel'>
            <table width='100%' border='0' align='center' valign='middlen' cellpadding='0' cellspacing='3'>
              <tr height='20'>
                <td colspan='2'></td>
              </tr>
              <tr>
                <td width='30%' align='right'>用户名：</td>
                <td width='70%'>
                  <div class='x-form-item'>
                    <input id='userAccount' name='userAccount' type='text' size='30' alt='用户名'>
                  </div>
                </td>
              </tr>
              <tr>
                <td align='right'>密&nbsp;&nbsp;&nbsp;码：</td>
                <td>
                  <div class='x-form-item'>
                    <input id='userPassword' name='userPassword' type='password' size='30' autocomplete='off' alt='登陆密码'>
                  </div>
                </td>
              </tr>
              <tr>
                <td align='right'>保存我的信息：</td>
                <td>
                  <div class='x-form-item'>
                    <input id='_acegi_security_remember_me' name='_acegi_security_remember_me' type='checkbox' size='30'  alt='保存我的信息'>
                  </div>
                </td>
              </tr>
              <tr>
                <td align='right'>验证码：</td>
                <td align="left"><input id="j_captcha_response" name="j_captcha_response" style="width:60px" autocomplete="off"/>&#160;&#160;<img id="captcha" src="../captcha.jpg" align="top" title="点击刷新图片" alt="点击刷新图片" onclick="$('captcha').src='../captcha.jpg?' + new Date().getTime();this.blur();" style="cursor:pointer;border:0px black solid;"/>[<A onclick="$('captcha').src='../captcha.jpg?' + new Date().getTime();this.blur();" href="#">刷新图片</A>]</TD>
              </tr>
            </table>
          </div>
        </div>
        <div class='x-dlg-tab' title='关于'>
          <div id='standard-panel'>关于...</div>
        </div>
      </div>
      <div class='x-dlg-ft'>
        <div id='dlg-msg'>
          <div id='post-wait' class='posting-msg'>
            <div class='waitting'>正在验证，请稍候...</div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
