<#if ctx??><#else><#assign ctx=springMacroRequestContext.getContextPath()></#if>
<#assign ext="${ctx}/widgets/extjs/1.1">
<link rel="stylesheet" type="text/css" href="${ext}/resources/css/yext-all.css" />
<#--<link rel="stylesheet" type="text/css" href="${ctx}/widgets/examples.css" />-->
<style type="text/css">
body {
  font-size:12px;
}.x-tree-node {
  font-size:12px;
}.x-btn button {
  font-size:12px;
}.x-menu-list-item {
  font-size:12px;
}.x-layout-panel-hd-text {
  font-size:12px;
}.x-grid-hd-row td, .x-grid-row td {
  font-size:12px;
}.x-layout-panel-hd-text {
  font-size:12px;
}.x-tabs-strip .x-tabs-text {
  font-size:12px;
}.x-toolbar td, .x-toolbar span, .x-toolbar input, .x-toolbar div, .x-toolbar select, .x-toolbar label {
  font-size:12px;
}
</style>
<script type="text/javascript" src="${ext}/adapter/yui/yui-utilities.js"></script>
<script type="text/javascript" src="${ext}/adapter/yui/ext-yui-adapter.js"></script>
<#--
<script type="text/javascript" src="${ext}/ext-all.js"></script>
-->
<script type="text/javascript" src="${ext}/ext-all-debug.js"></script>
<script type="text/javascript">
Ext.BLANK_IMAGE_URL = '${ext}/resources/images/default/s.gif';
</script>
