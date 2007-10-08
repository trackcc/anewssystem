<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
  <head>
    <#include "/include/meta.ftl">
    <title>更多新闻</title>
    <#assign ext="${ctx}/widgets/extjs/1.1"/>
    <link rel="stylesheet" type="text/css" href="${ext}/resources/css/yext-all.css" />
    <link rel="stylesheet" type="text/css" href="${ext}/resources/css/reset-min.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/widgets/asecurity/welcome/welcome.css"/>
  </head>
  <body>
    <div class="block">
      <h3 class="block-title">欢迎使用新闻发布</h3>
      <div class="block-body">
        <p><b>欢迎使用新闻发布。</b></p>
        <br />
        <p>本来这个项目是基于http://www.springside.org.cn的，原本打算成长起来之后，向江南白衣申请一个孵化器项目或者卫星项目的。可springside最近基本上呈现僵死状态，我们可能借不到它的光了。遗憾啊。</p>
        <br />
      </div>
    </div>
    <div class="col">
      <div class="block">
        <h3 class="block-title">数据模型与接口文档</h3>
        <div class="block-body">
          <b>包括两个模块：</b>
          <a href="${ctx}/sandbox/index.htm">新闻发布</a><br />
          <span style="color:green">后台使用了extjs的widgets。包括树形作为无级分类管理，模板拖拽效果演示。 树形支持</span>
          <ul class="list">
            <li>异步读取节点（不过管理分类的时候一次性读取了所有节点）</li>
            <li>双击节点编辑节点内容</li>
            <li>拖拽排序</li>
            <li>右键弹出菜单，进行操作</li>
          </ul><br />
          <a href="${ctx}/sandbox/index.htm">权限管理</a><br />
          <span style="color:green">感谢力奇提供的权限前台，无私贡献了自己制作的extjs权限前台脚本和页面</span>
          <ul class="list">
            <li>grid, form, dialog, pager</li>
          </ul><br />
          目前还在试验阶段，js封装参考了<b>尖叫的土豆</b>提供的范例，<span style="color:red;">这小子不提供源代码，太不人道了。</span>
          <em>前台使用Ext-1.1</em>
        </div>
      </div>
    </div>
    <div class="col">
      <div class="block">
        <h3 class="block-title"><a href="${ctx}/index/index.htm">首页</a> - ${newsCategory.name}</h3>
      </div>
      <div class="block">
        <h3 class="block-title">${newsCategory.name}</h3>
        <div class="block-body">
<#list newsCategory.newses! as news>
          <p>&nbsp;<a href="${news.link}">${news.name}</a> <a href="${ctx}/news/detail.htm?id=${news.id}">[-]</a></p>
</#list>
        </div>
      </div>
    </div>
  </body>
</html>
