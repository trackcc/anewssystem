<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>查看采购订单信息</title>
    <script type="text/javascript" src="${ctx}/widgets/aerp/convertCurrency.js"></script>
  </head>
  <body>
    <h3>查看采购订单信息</h3>
    <table border="1">
        <tr>
            <td>采购订单编号:</td><td>${entity.code!}</td>
        </tr>
        <tr>
            <td>订单状态:</td><td><#if entity.status==0>待审<#elseif entity.status==1>审核<#elseif entity.status=2>完成</#if></td>
        </tr>
        <tr>
            <td>订单名称:</td><td>${entity.name!}</td>
        </tr>
        <tr>
            <td>供应商名称:</td><td>${entity.erp2Supplier.name!}</td>
        </tr>
        <tr>
            <td>联系人:</td><td>${entity.linkman!}</td>
        </tr>
        <tr>
            <td>要求供货日期:</td><td>${entity.provideDate!}</td>
        </tr>
        <tr>
            <td>签订日期:</td><td>${entity.orderDate!}</td>
        </tr>
        <tr>
            <td>审核受理:</td><td><#if entity.audit==0>立即审核<#else>常规审核</#if></td>
        </tr>
        <tr>
            <td>订单签订人员:</td><td>${entity.username!}</td>
        </tr>
    </table>
    <hr>
    求购订单详细信息
    <table border="1">
        <tr>
            <td>序号</td>
            <td>编号</td>
            <td>商品名称</td>
            <td>类别</td>
            <td>成分</td>
            <td>生产厂家</td>
            <td>技术参数</td>
            <td>数量</td>
            <td>计量单位</td>
            <td>单价（元）</td>
            <td>金额（元）</td>
            <td>备注</td>
        </tr>
<#list entity.erp2BuyOrderInfos! as item>
        <tr>
            <td>${item.id!}</td>
            <td>${item.code!}</td>
            <td>${item.erp2Product.name!}</td>
            <td><#if item.erp2Product.type==0>染料<#elseif item.erp2Product.type==1>助剂<#else>设备</#if></td>
            <td>${item.erp2Product.material!}</td>
            <td>${item.erp2Product.factory!}</td>
            <td>${item.parameter!}</td>
            <td>${item.num!}</td>
            <td><#if item.erp2Product.unit==0>千克<#elseif item.erp2Product.unit==1>克<#elseif item.erp2Product.unit==2>升<#elseif item.erp2Product.unit==3>毫升<#elseif item.erp2Product.unit==4>台</#if></td>
            <td>${item.price!}</td>
            <td>${item.totalPrice!}</td>
            <td>${item.descn!}</td>
        </tr>
</#list>
    </table>
    <div style="margin-top:5px;margin-bottom:5px;">金额合计：<span id="amount1">${entity.totalPrice!}</span>元 （大写） <span id="amount2"><script type="text/javascript">document.write(convertCurrency(${entity.totalPrice!}));</script></span></div>
    <hr>
    <a href="${ctx}/erp2buyorder/audit.htm?id=${entity.id!}">审核通过</a> | <a href="${ctx}/erp2buyorder/dismissed.htm?id=${entity.id!}">审核不通过</a>
  </body>
</html>

