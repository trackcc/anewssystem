<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>订单</title>
    <#include "/include/extjs.ftl"/>
    <script type="text/javascript" src="${ctx}/widgets/aerp/erporder.js"></script>
    <script type="text/javascript" src="${ctx}/widgets/aerp/convertCurrency.js"></script>
  </head>
  <body>
    <div id="loading">
      <div class="waitting">请稍候...</div>
    </div>
    <div id="tabs">
      <div id="tab1">
        <div style="width:98%;margin-left:5px;">
            <div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>
            <div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">
                <h3 style="margin-bottom:5px;">添加订单</h3>
                <table>
                  <tr>
                    <td>订货日期</td>
                    <td><input id="orderDateIn" size="10"/></td>
                    <td>客户</td>
                    <td><input id="customerIn" size="10"/></td>
                    <td>联系人</td>
                    <td><input id="linkMan" size="10"/></td>
                    <td>电话</td>
                    <td><input id="tel" size="10"/></td>
                  </tr>
                </table>
                <div style="margin-top:5px;height:300px;border:1px #6FA0DF solid;">
                  <div id="orderInfoGrid"></div>
                </div>
                <div style="margin-top:5px;margin-bottom:5px;">金额合计：<span id="amount1">0</span>元 （大写） <span id="amount2"></span></div>
                <table>
                  <tr>
                    <td><div id="save"></div></td>
                    <td><div id="addOrderInfo"></div></td>
                    <td width="100"><div id="submit"></div></td>
                    <td width="100"><div id="delOrderInfo"></div></td>
                    <td><div id="print"></div></td>
                  </tr>
                </table>
                <select name='statusSelect' id='statusSelect' style='display:none;'>
                    <option value="当日生产">当日生产</option>
                    <option value="暂不生产">暂不生产</option>
                    <option value="已生产">已生产</option>
                </select>
                <select name='unitSelect' id='unitSelect' style='display:none;'>
                    <option value="平方">平方</option>
                    <option value="长度">长度</option>
                </select>
            </div></div></div>
            <div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>
        </div>
        <div class="x-form-clear"></div>
      </div>
      <div id="tab2">
        <div id="toolbar"></div>
        <div id="lightgrid" style="border: 0px solid #cccccc; overflow: hidden; width:auto;height:100%;"></div>
      </div>
    </div>
    <!-- 增加、修改、查看 弹出框内容-->
    <div id="content" style="visibility:hidden">
    <table width="95%" border="0" align="center" valign="middlen" cellpadding="0" cellspacing="10">
      <tr height="50%"><td>&nbsp;</td></tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">产品编号：</td>
        <td width="80%">
          <div class="x-form-item">
            <input id="code"/>
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">客户：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="customer"/>
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">订货日期：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="orderDate" type="text" size="60">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">总金额：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="amount" type="text" size="60">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">经手人：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="handMan" type="text" size="60">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">付款情况：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <select id="payment">
				<option value="全部">全部</option>
				<option value="未付">未付</option>
				<option value="已付">已付</option>
            </select>
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">运货情况：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <select id="delivery">
				<option value="全部">全部</option>
				<option value="未发货">未发货</option>
				<option value="已发货">已发货</option>
				<option value="收到货">收到货</option>
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
