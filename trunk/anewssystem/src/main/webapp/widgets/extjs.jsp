<%@ page contentType="text/html;charset=UTF-8"%><%@ page isELIgnored="false"%><%
    String ctx = request.getContextPath();
    String ext = ctx + "/widgets/extjs/1.1-rc1";
    pageContext.setAttribute("ctx", ctx);
    pageContext.setAttribute("ext", ext);
%>    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="${ext}/resources/css/ext-all.css" />
    <script type="text/javascript" src="${ext}/adapter/yui/yui-utilities.js"></script>
    <script type="text/javascript" src="${ext}/adapter/yui/ext-yui-adapter.js"></script>
    <script type="text/javascript" src="${ext}/ext-all.js"></script>
