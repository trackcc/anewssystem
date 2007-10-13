<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:forward page="/index/index.htm"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <%@ include file="/inc/meta.jsp"%>
    <title> 首页 </title>
  </head>

  <body>
    <div>
      <h1>首页</h1>
      <p><a href="${ctx}/admin/index.htm">新闻后台0.0.1版</a></p>
      <p><a href="${ctx}/index/index.htm">新闻后台0.0.1a版</a></p>
      <p><a href="${ctx}/sandbox/index.htm">新闻后台0.0.2-M2版</a></p>
    </div>
    <hr>
    <div>
      直接看第三个链接sandbox，前两个链接都是老版本<br>
      完全权限测试用户:test，测试密码:test<br>
      部分权限测试用户:user，测试密码:user<br>
      <hr>
      没搞定的问题<br>
    </div>
  </body>
</html>

