<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <#include "/include/meta.ftl">
    <title>首页</title>
    <#include "/include/extjs.ftl"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/widgets/asecurity/index/index.css" />
    <script type="text/javascript" src="${ctx}/widgets/asecurity/index/index.js"></script>
  </head>
  <body id="index" scroll="no">
    <div id="loading-mask" style="width:100%;height:100%;background:#c3daf9;position:absolute;z-index:20000;left:0;top:0;">&#160;</div>
    <div id="loading">
      <div class="loading-indicator"><img src="${ext}/resources/images/default/grid/loading.gif" style="width:16px;height:16px;" align="absmiddle">&#160;请稍候...</div>
    </div>
    <div id="header" class="ylayout-inactive-content">
      <a href="#" style="float:right;margin-right:10px;"></a>
      <div style="padding-top:3px;">新闻发布</div>
    </div>
    <div id="classes" class="ylayout-inactive-content">
      <a id="welcome-link" href="welcome.htm">首页</a>
      <div class="pkg">
        <h3>新闻管理</h3>
        <div class="pkg-body">
          <a href="${ctx}/newscategory/tree.htm">新闻分类管理</a>
          <a href="${ctx}/news/create.htm">添加新闻</a>
          <a href="${ctx}/news/list.htm">管理新闻</a>
          <a href="${ctx}/news/search.htm">新闻搜索</a>
          <a href="${ctx}/newstag/list.htm">管理关键字</a>
          <a href="${ctx}/newsconfig/manage.htm">新闻属性设置</a>
        </div>
      </div>
      <div class="pkg">
        <h3>权限管理</h3>
        <div class="pkg-body">
          <a href="${ctx}/user/index.htm">用户管理</a>
          <a href="${ctx}/menu/index.htm">菜单管理</a>
          <a href="${ctx}/role/index.htm">角色管理</a>
          <a href="${ctx}/resource/index.htm">资源管理</a>
          <a href="${ctx}/dept/index.htm">部门管理</a>
        </div>
      </div>
      <a href="welcome.htm">帮助</a>
    </div>
    <iframe id="main" id="main" frameborder="no"></iframe>
  </body>
</html>
