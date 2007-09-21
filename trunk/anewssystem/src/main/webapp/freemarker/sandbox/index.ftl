<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="zh">
  <head>
    <#include "/include/meta.ftl">
    <title> 管理后台 </title>
    <#include "/include/extjs.ftl"/>
    <script type='text/javascript' src='${ctx}/widgets/ux/Ext.Accordion.js'></script>
    <script type='text/javascript' src='${ctx}/widgets/ux/Ext.InfoPanel.js'></script>

    <script type='text/javascript' src='${ctx}/dwr/interface/MenuHelper.js'></script>
    <script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
    <script type='text/javascript' src='${ctx}/dwr/util.js'></script>
    <script type='text/javascript' src='${ctx}/widgets/ux/Ext.Common.js'></script>
<#--
    <script type='text/javascript' src='${ctx}/widgets/ux/Ext.DaoFactory.js'></script>
    <script type='text/javascript' src='${ctx}/widgets/ux/Ext.DataGrid.js'></script>
    <script type='text/javascript' src='${ctx}/widgets/ux/Ext.Forms.js'></script>
-->
    <script type='text/javascript' src='${ctx}/widgets/lingo/form/Ext.lingo.FormUtils.js'></script>
    <script type='text/javascript' src='${ctx}/widgets/sandbox/index.js'></script>
  </head>
  <body scroll='no' id='top'>
    <div id='loading'>
      <div class='waitting'>请稍候...</div>
    </div>
    <div id='header' class='ylayout-inactive-content'>
      <div id='logo'></div>
      <div style='padding-top:4px;'>
        <img src="${ctx}/images/banner.jpg">
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
              <tr height='40'>
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





        </body>
</html>

