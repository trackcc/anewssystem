<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/taglibs.jsp"%>
<%--
<%@ page import="org.acegisecurity.context.SecurityContextHolder" %>
<%@ page import="org.acegisecurity.Authentication" %>
<%@ page import="org.acegisecurity.ui.AccessDeniedHandlerImpl" %>

<h1>Sorry, access is denied</h1>


<p>
<%= request.getAttribute(AccessDeniedHandlerImpl.ACEGI_SECURITY_ACCESS_DENIED_EXCEPTION_KEY)%>

<p>

<%
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
%>
            Authentication object as a String: <%= auth.toString() %><BR><BR>
<%
    }
%>
--%>

<h1>对不起，您没有权限访问。</h1>
<a href="javascript:history.back()">返回</a>
