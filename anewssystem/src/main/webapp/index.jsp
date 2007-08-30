<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:forward page="/news/index.htm"/>
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
      <p><a href="${ctx}/admin/index.htm">管理后台</a></p>
      <ol>
        <li><a href="${ctx}/newsconfig/list.htm">NewsConfig</a></li>
        <li><a href="${ctx}/newscategory/list.htm">NewsCategory</a></li>
        <li><a href="${ctx}/news/list.htm">News</a></li>
        <li><a href="${ctx}/newscomment/list.htm">NewsComment</a></li>
        <li><a href="${ctx}/newstag/list.htm">NewsTag</a></li>
      </ol>
    </div>
  </body>
</html>

