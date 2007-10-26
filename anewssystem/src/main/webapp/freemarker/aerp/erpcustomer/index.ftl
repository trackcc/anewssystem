<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>客户</title>
    <#include "/include/extjs.ftl"/>
    <script type="text/javascript" src="${ctx}/widgets/aerp/erpcustomer.js"></script>
  </head>
  <body>
    <div id="loading">
      <div class="waitting">请稍候...</div>
    </div>
    <div id="tabs">
      <div id="tab1">
        <div id="toolbar"></div>
        <div id="lightgrid" style="border: 0px solid #cccccc; overflow: hidden; width:auto;height:100%;"></div>
      </div>
      <div id="tab2"></div>
    </div>
    <!-- 增加、修改、查看 弹出框内容-->
    <div id="content">
    <table width="95%" border="0" align="center" valign="middlen" cellpadding="0" cellspacing="10">
      <tr height="50%"><td>&nbsp;</td></tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">客户名称：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="name" type="text" size="20">
          </div>
        </td>
        <td width="15%" align="right" style="font-size:12px;">客户编号：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="code" type="text" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">客户类型：</td>
        <td width="35%">
          <div class="x-form-item">
            <select id="type">
                <option value="供应商">供应商</option>
                <option value="销售商">销售商</option>
                <option value="货运单位">货运单位</option>
            </select>
          </div>
        </td>
        <td width="15%" align="right" style="font-size:12px;">邮编：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="zip" type="text" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">负责人：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="leader" type="text" size="20">
          </div>
        </td>
        <td width="15%" align="right" style="font-size:12px;">传真：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="fax" type="text" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">联系人：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="linkMan" type="text" size="20">
          </div>
        </td>
        <td width="15%" align="right" style="font-size:12px;">email：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="email" type="text" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">电话：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="tel" type="text" size="20">
          </div>
        </td>
        <td width="15%" align="right" style="font-size:12px;">主页：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="homepage" type="text" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">地区：</td>
        <td width="35%">
          <div class="x-form-item">
            <select id="province"></select>省
            <select id="city"></select>市
            <select id="town"></select>县
          </div>
        </td>
        <td width="15%" align="right" style="font-size:12px;">地址：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="address" type="text" size="20">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">客户渠道：</td>
        <td width="35%">
          <div class="x-form-item">
            <select id="source">
                <option value="朋友介绍">朋友介绍</option>
                <option value="广告">广告</option>
                <option value="展销会">展销会</option>
                <option value="其他">其他</option>
            </select>
          </div>
        </td>
        <td width="15%" align="right" style="font-size:12px;">信用等级：</td>
        <td width="35%">
          <div class="x-form-item">
            <select id="rank">
                <option value="优秀">优秀</option>
                <option value="良好">良好</option>
                <option value="一般">一般</option>
                <option value="差">差</option>
            </select>
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">状态：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <select id="status">
                <option value="正常">正常</option>
                <option value="失效">失效</option>
            </select>
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">录入人：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="inputMan" type="text" size="60">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">录入时间：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="inputTime" type="text" size="60">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">备注：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="descn" type="text" size="60">
          </div>
        </td>
      </tr>
      <tr>
        <td align="right" width="15%">id：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="id" type="text" size="60" readonly>
          </div>
        </td>
      </tr>
      <tr height="50%"><td>&nbsp;</td></tr>
    </table>
    </div>
    <div id="resource-dlg" style="visibility:hidden;">
      <div class="x-dlg-hd">角色授权</div>
      <div class="x-dlg-bd">
        <div id="resource-inner" class="x-layout-inactive-content">
          <div id="resource-grid" style="width:630px;height:380px;"></div>
        </div>
      </div>
    </div>
    <div id="menuDialog"></div>
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
