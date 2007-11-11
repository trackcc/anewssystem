<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>登录页面</title>
    <#include "/include/extjs.ftl"/>
    <SCRIPT type="text/javascript" language="javascript">
        if (window.top != window) {
            window.top.location = window.location;
        }
    </script>
    <script type="text/javascript" src="${ctx}/widgets/aerp/login.js"></script>
    <style type="text/css">
.msg .x-box-mc {
    font-size:14px;
}
#msg-div {
    position:absolute;
    left:35%;
    top:10px;
    width:250px;
    z-index:20000;
}
    </style>
  </head>
  <body>
    <br/>
    <div style="width:400px;margin:auto;" id="login-box">
        <div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>
        <div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">
            <h3 style="text-align:center;">中小型染整企业电子采购供应链管理系统</h3>
            <div id="login-form"></div>
            <div style="text-align:right;">&copy;Inner Mongolia University of Technology</div>
        </div></div></div>
        <div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>
    </div>
    <div class="x-form-clear"></div>

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

