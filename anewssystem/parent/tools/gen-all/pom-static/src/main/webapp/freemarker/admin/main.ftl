<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<!--
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
-->
<html>
  <head>
    <#include "/include/meta.ftl">
    <title>后台管理</title>
    <script type="text/javascript" src="${ctx}/js/dojo/0.4.3/dojo.js"></script>
    <style type="text/css">
        /* standard list style table */
        table.adminlist {
            background-color: #FFFFFF;
            margin: 0px;
            padding: 0px;
            border: 1px solid #CCCCCC;
            border-spacing: 0px;
            width: 100%;
            height: 100%;
            border-collapse: collapse;
            font-size: 14px;
        }

        table.adminlist td {
            border-bottom: 1px solid #e5e5e5;
            padding: 4px;
        }

        table.adminlist th {
            margin: 0px;
            padding: 6px 4px 2px 4px;
            height: 25px;
            background: url( ../images/tableTopbg.gif );
            background-repeat: repeat;
            color: #ffffff;
            text-align: left;
        }
        span {
            font-size: 14px;
        }
    </style>
  </head>
  <body>
    <div align="center">
      <div style="text-align: center;font-size: 18px;font-weight: bold;color: #1a7cdf;letter-spacing: 15px;"> 后台管理 </div>
      <div style="height: 20px;"></div>
      <div id="mainTabContainer" dojoType="TabContainer" style="width: 300px; height: 150px;">
        <div dojoType="ContentPane" label="登陆用户">
          <table class="adminlist">
            <tr>
              <th class="title">当前登陆用户</th>
            </tr>
            <tr>
              <td><@authz.authentication operation="username"/></td>
            </tr>
          </table>
        </div>
      </div>
    </div>
  </body>
</html>
