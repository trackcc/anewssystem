<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <#include "/include/meta.ftl"/>
    <title>项目跟踪</title>
    <#include "/include/extjs.ftl"/>
    <script type="text/javascript" src="${ctx}/widgets/atracker/tracker.js"></script>
    <script type="text/javascript" src="${ctx}/widgets/atracker/tree.js"></script>
    <script type="text/javascript" src="${ctx}/widgets/atracker/grid.js"></script>
  </head>
  <body>
    <div id="loading">
      <div class="waitting">请稍候...</div>
    </div>

    <!--导航区域-->
    <div id="nav_area">
        <table cellspacing="0" border="0" cellpadding="0" width="100%">
            <tr>
                <td><h1>项目跟踪</h1></td>
                <td align="right">Project Tracker</td>
            </tr>
        </table>
    </div>
    <!--END-->

    <div id="tree_area""></div>
    <div id="tree_content">
      <table>
        <tr style="display:none;">
          <td>id</td>
          <td><input id="id" type="hidden"></td>
        </tr>
        <tr>
          <td>项目名</td>
          <td><input id="name" type="text"></td>
        </tr>
          <td>创建者</td>
          <td><input id="founder" type="text"></td>
        <tr>
          <td>简介</td>
          <td><input id="summary" type="text" value=""></td>
        </tr>
      </table>
    </div>

    <!--主区域-->
    <div id="main_area"></div>
    <div id="grid_content" style="visibility:hidden">
      <table>
        <tr style="display:none;">
          <td>id</td>
          <td><input id="id" type="hidden"></td>
        </tr>
        <tr style="display:none;">
          <td>project_id</td>
          <td><input id="project_id" type="hidden"></td>
        </tr>
        <tr>
          <td>任务名称</td>
          <td><input id="issuename" type="text"></td>
        </tr>
        <tr>
          <td>优先级</td>
          <td>
            <select id="priority">
              <option value="0">低</option>
              <option value="1" selected>中</option>
              <option value="2">高</option>
            </select>
          </td>
        </tr>
        <tr>
          <td>严重度</td>
          <td>
            <select id="severity">
              <option value="0">轻微</option>
              <option value="1" selected>一般</option>
              <option value="2">严重</option>
            </select>
          </td>
        </tr>
        <tr>
          <td>状态</td>
          <td>
            <select id="status">
              <option value="0">等待处理</option>
              <option value="1">已处理</option>
              <option value="2">已关闭</option>
            </select>
          </td>
        </tr>
        <tr>
          <td>负责人</td>
          <td><input id="assignTo" value="Lingo"></td>
        </tr>
        <tr>
          <td>提交者</td>
          <td><input id="submitBy" value="anyone"></td>
        </tr>
        <tr>
          <td>提交时间</td>
          <td><input id="addTime"></td>
        </tr>
        <tr>
          <td>更新时间</td>
          <td><input id="updateDate"></td>
        </tr>
        <tr>
          <td>内容</td>
          <td><input id="content" type="text" value=""></td>
        </tr>
        <tr>
          <td>附件</td>
          <td><input id="file" type="text"></td>
        </tr>
      </table>
    </div>
    <div id="state_area" style="color:red;">灵感来自Frank的Project Tracker，没有那么多功能。要是没有记录，我就没办法保持工作效率。2007-10-07 22:30[*注]投诉email:echo_o@163.com</div>
  </body>
</html>
