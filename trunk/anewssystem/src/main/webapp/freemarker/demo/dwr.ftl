<#assign ctx=springMacroRequestContext.getContextPath()/>
<html>
  <head>
    <#include "/include/meta.ftl">
    <title>dwr</title>
    <script type='text/javascript' src='${ctx}/dwr/interface/newsServiceClient.js'></script>
    <script type='text/javascript' src='${ctx}/dwr/engine.js'></script>
    <script type='text/javascript' src='${ctx}/dwr/util.js'></script>
    <script type="text/javascript" src="${ctx}/js/jstemplate/template.js"></script>
    <script type="text/javascript">
DWREngine.setErrorHandler(errorHandler);
function errorHandler(errorString, exception) {
  DWRUtil.setValue("error_message", errorString);
  $('error_message').show();
}
window.onload = function() {
  DWRUtil.useLoadingMessage();
  newsServiceClient.getAll(showData);
}
function showData(data) {
  var myTemplateObj = TrimPath.parseDOMTemplate("jst");
  $("output").innerHTML = myTemplateObj.process({"data":data});
}
    </script>
  </head>
  <body>
    <div id="output"></div>
    <div id="error_message" style="display:none"></div>
<#noparse>
    <textarea id="jst" style="display:none;">
      <table border="1">
        <tr>
          <td>序号</td>
          <td>ID</td>
          <td>标题</td>
        </tr>
{for x in data}
        <tr>
          <td>${x_index}</td>
          <td>${x.id}</td>
          <td>${x.name}</td>
        </tr>
{/for}
      </table>
    </textarea>
</#noparse>
  </body>
</html>
