<#if ctx??><#else><#assign ctx=springMacroRequestContext.getContextPath()></#if>
<#assign ext="${ctx}/widgets/extjs/1.1">
<link rel="stylesheet" type="text/css" href="${ext}/resources/css/yext-all.css" />
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
<script type="text/javascript" src="${ctx}/widgets/lingo/Ext.form.VTypes.js"></script>
<script type="text/javascript" src="${ctx}/widgets/lingo/Ext.lingo.Theme.js"></script>
<script type="text/javascript" src="${ctx}/widgets/lingo/Ext.data.JsonReader.js"></script>
<script type="text/javascript" src="${ctx}/widgets/lingo/form/HtmlEditor.js"></script>
<script type="text/javascript" src="${ctx}/widgets/lingo/form/Ext.lingo.FormUtils.js"></script>
<script type="text/javascript" src="${ctx}/widgets/lingo/form/Ext.lingo.LoginDialog.js"></script>
<script type="text/javascript" src="${ctx}/widgets/lingo/form/Ext.lingo.TreeField.js"></script>
<script type="text/javascript" src="${ctx}/widgets/lingo/form/Ext.lingo.TagField.js"></script>
<script type="text/javascript" src="${ctx}/widgets/lingo/jsongrid/Ext.lingo.CheckRowSelectionGrid.js"></script>
<script type="text/javascript" src="${ctx}/widgets/lingo/jsongrid/Ext.ux.PageSizePlugin.js"></script>
<script type="text/javascript" src="${ctx}/widgets/lingo/jsongrid/Ext.lingo.JsonGrid.js"></script>
<script type="text/javascript" src="${ctx}/widgets/lingo/jsontree/Ext.lingo.JsonTree.js"></script>
<script type="text/javascript" src="${ctx}/widgets/lingo/checkboxtree/Ext.lingo.JsonCheckBoxTree.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/lingo/checkboxtree/Ext.lingo.JsonCheckBoxTree.css" />

<script type="text/javascript" src="${ctx}/widgets/ux/Ext.ux.PasswordMeter.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/ux/passwordmeter.css" />
<link rel="stylesheet" type="text/css" href="${ext}/resources/css/editor.css" />

