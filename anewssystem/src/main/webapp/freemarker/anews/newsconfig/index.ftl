<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <TITLE>新闻属性设置</TITLE>
    <#include "/include/extjs.ftl"/>
    <script type="text/javascript">
Ext.onReady(function(){

    var form = new Ext.form.Form({
        labelAlign: 'right',
        labelWidth: 75,
        url:'onSubmit.htm'
    });

    form.column({width:300, labelWidth:100});
    form.fieldset(
        {legend:'新闻审核', hideLabels:true},
        new Ext.form.Checkbox({
            id:'newsNeedAudit',
            boxLabel: '新闻是否需要审核',
            name: 'newsNeedAudit',
            inputValue: '1'
        })
    );
    form.fieldset(
        {legend:'评论审核', hideLabels:true},
        new Ext.form.Checkbox({
            id:'commentNeedAudit',
            boxLabel: '评论需要审核',
            name: 'commentNeedAudit',
            inputValue: '1'
        }),

        new Ext.form.Checkbox({
            id:'couldComment',
            boxLabel: '允许评论',
            name: 'couldComment',
            inputValue: '1'
        })
    );

    form.fieldset(
        {legend:'分类策略', hideLabels:true},
        new Ext.form.Radio({
            id:'categoryStrategy0',
            boxLabel:'位编码',
            name:'categoryStrategy',
            width:'auto',
            inputValue:'0'
        }),
        new Ext.form.Radio({
            id:'categoryStrategy1',
            boxLabel:'字符编码',
            name:'categoryStrategy',
            width:'auto',
            inputValue:'1'
        }),
        new Ext.form.Radio({
            id:'categoryStrategy2',
            boxLabel:'无编码',
            name:'categoryStrategy',
            width:'auto',
            inputValue:'2'
        })
    );
    form.end();

    form.column(
        {width:202, style:'margin-left:10px', clear:true}
    );

    form.fieldset(
        {legend:'选择模板', hideLabels:true},
        new Ext.form.Radio({
            id:'defaultTemplate',
            boxLabel:'默认模板',
            name:'templateName',
            width:'auto',
            inputValue:'default'
        }),
        new Ext.form.Radio({
            id:'extjsTemplate',
            boxLabel:'EXTJS模板',
            name:'templateName',
            width:'auto',
            inputValue:'extjs'
        })
    );

    form.applyIfToFields({
        width:230
    });

    form.addButton('提交');
    form.addButton('重置');

    form.buttons[0].addListener("click", function() {
        if (form.isValid()) {
            form.submit({success:function(){Ext.MessageBox.alert("提示", "修改成功");}});
        }
    });

    form.buttons[1].addListener("click", function() {
        form.reset();
    });

    form.render('config-form');


<#if config??>
    <#if config.newsNeedAudit?? && config.newsNeedAudit==1>
    Ext.get("newsNeedAudit").dom.checked = true;
    </#if>
    <#if config.commentNeedAudit?? && config.commentNeedAudit==1>
    Ext.get("commentNeedAudit").dom.checked = true;
    </#if>
    <#if config.couldComment?? && config.couldComment==1>
    Ext.get("couldComment").dom.checked = true;
    </#if>
    <#if config.categoryStrategy??>
    Ext.get("categoryStrategy${config.categoryStrategy}").dom.checked = true;
    </#if>
    <#if config.templateName??>
    Ext.get("${config.templateName}Template").dom.checked = true;
    </#if>
</#if>
});
    </script>
  </head>
  <body>
    <br/>
    <div style="width:550px;margin-left:150px;">
        <div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>
        <div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">
            <h3 style="margin-bottom:5px;">新闻属性设置</h3>
            <div id="config-form"></div>
        </div></div></div>
        <div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>
    </div>
    <div class="x-form-clear"></div>
  </body>
</html>
