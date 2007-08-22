<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <%@ include file="/inc/meta.jsp"%>
    <title> 首页 </title>
  </head>

  <body>
    <div align="left">
      <h1>首页</h1>
<#assign ctx=r"${ctx}">
      <p><a href="${ctx}/admin/index.htm">管理后台</a></p>
      <ol>
<#list classes as clz>
        <li><a href="${ctx}/${clz?lower_case}/list.htm">${clz}</a></li>
</#list>
      </ol>
    </div>
  </body>
</html>

