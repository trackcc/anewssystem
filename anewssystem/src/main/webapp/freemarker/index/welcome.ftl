<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <#include "/include/meta.ftl">
    <title>欢迎</title>
    <#include "/include/extjs.ftl"/>
    <link rel="stylesheet" type="text/css" href="${ext}/resources/css/reset-min.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/widgets/asecurity/welcome/welcome.css"/>
  </head>
  <body>
    <div class="col">
      <div class="block">
        <h3 class="block-title">欢迎使用新闻发布</h3>
        <div class="block-body">
          <p><b>欢迎使用新闻发布。</b></p>
          <br />
          <p>本来这个项目是基于http://www.springside.org.cn的，原本打算成长起来之后，向江南白衣申请一个孵化器项目或者卫星项目的。可springside最近基本上呈现僵死状态，我们可能借不到它的光了。遗憾啊。</p>
          <br />
        </div>
      </div>
      <div class="block">
        <h3 class="block-title">数据模型与接口文档</h3>
        <div class="block-body">
          <b>包括两个模块：</b>
          <a href="${ctx}/news/create.htm">新闻发布</a><br />
          <span style="color:green">后台使用了extjs的widgets。包括树形作为无级分类管理，模板拖拽效果演示。 树形支持</span>
          <ul class="list">
            <li>异步读取节点（不过管理分类的时候一次性读取了所有节点）</li>
            <li>双击节点编辑节点内容</li>
            <li>拖拽排序</li>
            <li>右键弹出菜单，进行操作</li>
          </ul><br />
          <a href="${ctx}/user/index.htm">权限管理</a><br />
          <span style="color:green">感谢力奇提供的权限前台无私贡献了自己制作的extjs权限前台脚本和页面</span>
          <ul class="list">
            <li>grid, form, dialog, pager</li>
          </ul><br />
          目前还在试验阶段，很多功能尚未完成。
          <em>前台使用Ext-1.1</em>
        </div>
      </div>
    </div>
    <div class="col">
      <div class="block">
        <h3 class="block-title">相关链接</h3>
        <div class="block-body">
          新闻发布项目首页：
          <ul class="list">
            <li><a href="http://code.google.com/p/anewssystem/">http://code.google.com/p/anewssystem/</a></li>
            <li><a href="http://sourceforge.net/project/showfiles.php?group_id=204197">http://sourceforge.net/project/showfiles.php?group_id=204197</a><span style="color:green;"><br />（因为google code上下载包不能大于20m，需要完整发布包需要去sf.net）</span></li>
          </ul><br />
          qq群：
          <ul class="list">
            <li>3038490（临远自己的群）</li>
            <li>21796897（一些关注springside的开发者，extjs部分功能很多来自这里的讨论）</li>
          </ul>
        </div>
      </div>
      <div class="block">
        <h3 class="block-title">简介</h3>
        <div class="block-body">
          技术选型：
          <ul class="list">
            <li><a href="http://www.extjs.com/">前台控件：extjs-1.1</a></li>
            <li><a href="http://www.springframework.org/">mvc模型：springmvc-2.0.6</a></li>
            <li><a href="http://www.springframework.org/webflow/"><span style="color:red;">web流：spring web flow（暂时未用到）</span></a></li>
            <li><a href="http://www.springframework.org/">中间层：springframework-2.0.6</a></li>
            <li><a href="http://www.hibernate.org/">数据库访问层：hibernate-3.2.5.GA</a></li>
          </ul><br />
          最新修改：
          <ul class="list">
            <li><a href="${ctx}/user/index.htm">[2007-09-07 15:14]感谢力奇提供的权限前台，功能尚未完全，尚处于试验阶段。</a></li>
          </ul>
        </div>
      </div>
    </div>
  </body>
</html>
