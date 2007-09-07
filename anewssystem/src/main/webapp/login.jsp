<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/taglibs.jsp"%>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter"%>
<%@ page import="org.acegisecurity.AuthenticationException"%>
<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML>
  <HEAD>
    <TITLE>SpringSide 后台管理</TITLE>
    <META http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <META http-equiv="Cache-Control" content="no-store" />
    <META http-equiv="Pragma" content="no-cache" />
    <META http-equiv="Expires" content="0" />
    <LINK type="text/css" href="${ctx}/css/default.css" rel="stylesheet" />
    <LINK type="text/css" href="${ctx}/css/admin.css" rel="stylesheet" />
    <SCRIPT type="text/javascript" src="${ctx}/js/prototype/1.5.0/prototype.js"></SCRIPT>
    <SCRIPT type="text/javascript" language="javascript">
        if (window.top != window) {
            window.top.location = window.location;
        }

        function efocus(event) {
            var elem = Event.element(event);
            elem.style.border = '1px solid #9e9e9e';
        }

        function eblur(event) {
            var elem = Event.element(event);
            elem.style.border = '1px solid #cccccc';
        }

        function ekeydown(event) {
            if (event.keyCode == Event.KEY_RETURN)
                event.keyCode = Event.KEY_TAB;
        }
    </SCRIPT>
    <META content="MSHTML 6.00.2900.3020" name="GENERATOR" />
  </HEAD>
  <BODY id="login" style="margin: auto">
    <table align="center" border="0">
      <tr>
        <td>
          <DIV id="loginHead">
            <SPAN class="logo">
              <%--IMG alt="" src="images/admin/springside.gif" /--%>
            </SPAN>
            <SPAN class="topbar"><%--&#160;&#160;|&#160;&#160;
            <A href="http://localhost:8080/springside/;jsessionid=527491B95AADCFBAA056155CF8BBB02F">书店首页</A>--%>&#160;&#160;|&#160;&#160;
            <A href="#">帮助</A>&#160;&#160;|&#160;&#160;
            <A href="#">联系我们</A>&#160;&#160;|&#160;&#160;</SPAN>
          </DIV>
        </td>
      </tr>
      <tr>
        <td>
          <DIV id="loginContent">
            <DIV id="loginHint">
              <h1>内蒙古呼和浩特创益科技有限责任公司</h1>
              <DIV class="hintTitle">样品管理，包括入库，出库，搜索和汇总</DIV>
              <DIV class="hintTitle">登陆页面来自springside-1.0</DIV>
            </DIV>
            <DIV id="loginForm">
              <DIV class="login_table_bg">
                <FORM name="form1" action="${ctx}/j_acegi_security_check" method="post">
                  <TABLE class="login_table" cellSpacing="0" cellPadding="5" width="100%" border="0" style="margin:2px;">
                    <TR>
                      <TD colSpan="2">
                        <c:if test="${not empty param.login_error}">
                        <div class="error">
                          <c:if test="${param.login_error == '1'}">
                          无效用户名或密码，请重试！
                          </c:if>
                          <c:if test="${param.login_error == 'code_error'}">
                          验证码输入错误！
                          </c:if>
                          <c:if test="${param.login_error == 'user_psw_error'}">
                          无效用户名或密码，请重试！
                          </c:if>
                          <c:if test="${param.login_error == 'too_many_user_error'}">
                          多处使用同一用户名登陆,请等候！
                          </c:if>
                        </div>
                        </c:if>
                      </TD>
                    </TR>
                    <TR>
                      <TD>帐 号：</TD>
                      <TD align="left">
                        <INPUT onkeydown="ekeydown(event)" onblur="eblur(event);" style="WIDTH: 120px" onfocus="efocus(event);" name="j_username" />
                      </TD>
                    </TR>
                    <TR>
                      <TD>密 码：</TD>
                      <TD align="left">
                        <INPUT onkeydown="ekeydown(event)" onblur="eblur(event);" style="WIDTH: 120px" onfocus="efocus(event);" type="password" name="j_password" />
                      </TD>
                    </TR>
<%--
                    <TR>
                      <TD>验证码：</TD>
                      <TD align="left">
                      <INPUT onkeydown="ekeydown(event)" onblur="eblur(event);" onfocus="efocus(event);" style="WIDTH: 60px" name="j_captcha_response" autocomplete="off" />
                      &#160;&#160;[
                      <A onclick="$('captcha').src='/springside/captcha.jpg;jsessionid=527491B95AADCFBAA056155CF8BBB02F';this.blur();"
                      href="http://localhost:8080/springside/security/login.jsp#">刷新图片</A>]</TD>
                    </TR>
                    <TR>
                      <TD align="center" colSpan="2">
                        <IMG id="captcha" src="images/captcha.jpg" align="top" alt="" />
                      </TD>
                    </TR>
--%>
                    <TR style="visibility:hidden">
                      <TD>&#160;</TD>
                      <TD align="left">
                      <INPUT style="BORDER: 0px;" type="checkbox" name="_acegi_security_remember_me" checked/>
                      &#160;&#160; 保存我的信息</TD>
                    </TR>
                    <TR align="center">
                      <TD colSpan="2">
                      <BUTTON type="submit">登 录</BUTTON>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
                      <BUTTON type="reset">清 空</BUTTON></TD>
                    </TR>
                  </TABLE>
                </FORM>
              </DIV>
              <BR />
              <DIV class="login_table_bg">
                <TABLE class="login_table" cellSpacing="0" cellPadding="5" width="100%" border="0" style="margin:2px;">
                  <TR>
                    <TD align="center" colSpan="2" style="">默认帐号</TD>
                  </TR>
                  <TR>
                    <TD align="right">管理员：</TD>
                    <TD>admin / admin</TD>
                  </TR>
                  <TR>
                    <TD align="right">员工：</TD>
                    <TD>employee / employee</TD>
                  </TR>
                </TABLE>
              </DIV>
            </DIV>
          </DIV>
        </td>
      </tr>
      <tr>
        <td align="center">
          <DIV id="loginFoot">Copyright ©2006 - 2007 cysoft All Rights Reserved</DIV>
        </td>
      </tr>
    </table>
  </BODY>
</HTML>
