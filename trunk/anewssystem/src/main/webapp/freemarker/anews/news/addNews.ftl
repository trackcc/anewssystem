<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl">
    <TITLE>添加新闻</TITLE>
    <#include "/include/extjs.ftl">
    <script type="text/javascript" src="${ctx}/widgets/anews/news-edit.js"></script>
  </head>
  <body>
    <br/>
    <div style="width:700px;margin-left:50px;">
        <div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>
        <div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">
            <h3 style="margin-bottom:5px;">编辑新闻</h3>
            <div id="news-form"></div>
        </div></div></div>
        <div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>
    </div>
    <div class="x-form-clear"></div>
  </body>
</html>

