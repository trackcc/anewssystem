<#if ctx??><#else><#assign ctx=springMacroRequestContext.getContextPath()></#if>
<#assign ext="${ctx}/widgets/extjs/1.1">
<link rel="stylesheet" type="text/css" href="${ext}/resources/css/yext-all.css" />
<#--<link rel="stylesheet" type="text/css" href="${ctx}/widgets/examples.css" />-->
<script type="text/javascript" src="${ext}/adapter/yui/yui-utilities.js"></script>
<script type="text/javascript" src="${ext}/adapter/yui/ext-yui-adapter.js"></script>
<#--
<script type="text/javascript" src="${ext}/ext-all.js"></script>
-->
<script type="text/javascript" src="${ext}/ext-all-debug.js"></script>
<script type="text/javascript">
Ext.BLANK_IMAGE_URL = '${ext}/resources/images/default/s.gif';
</script>
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/lingo/lingo.css" />
