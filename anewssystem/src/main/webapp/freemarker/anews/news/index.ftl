<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl">
    <TITLE>管理新闻</TITLE>
    <#include "/include/extjs.ftl">
    <script type="text/javascript" src="${ctx}/widgets/anews/news-20071022.js"></script>
  </head>
  <body>
    <div id="loading">
      <div class="waitting">请稍候...</div>
    </div>
    <div id="tabs">
      <div id="tab1"></div>
      <div id="tab2"></div>
      <div id="tab3"></div>
      <div id="tab4"></div>
      <div id="tab5"></div>
    </div>
    <div id="gridPanel">
      <div id="lightgrid" style="border: 0px solid #cccccc; overflow: hidden; width:auto;height:100%;"></div>
    </div>
    <div id="editGrid">
        <div id="editGridContent"></div>
        <div style="width:700px;margin-left:50px;">
            <div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>
            <div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">
                <h3 style="margin-bottom:5px;">编辑新闻</h3>
                <div id="news-form"></div>
            </div></div></div>
            <div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>
        </div>
        <div class="x-form-clear"></div>
    </div>
    <div id="error_message" style="display:block"></div>
<script type="text/javascript">
//DWREngine.setErrorHandler(errorHandler);
function errorHandler(errorString, exception) {
    console.error(errorString);
    console.error(exception);
}
</script>
  </body>
</html>

