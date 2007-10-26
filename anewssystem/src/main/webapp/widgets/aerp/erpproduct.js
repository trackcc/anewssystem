/*
 * Ext JS Library 1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author Lingo
 * @since 2007-10-02
 * http://code.google.com/p/anewssystem/
 */
Ext.onReady(function(){

    // 开启提示功能
    Ext.QuickTips.init();

    // 使用cookies保持状态
    // TODO: 完全照抄，作用不明
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    // 布局管理器
    var layout = new Ext.BorderLayout(document.body, {
        center: {
            autoScroll     : true,
            titlebar       : false,
            tabPosition    : 'top',
            closeOnTab     : true,
            alwaysShowTabs : true,
            resizeTabs     : true,
            fillToFrame    : true
        }
    });

    // 设置布局
    layout.beginUpdate();
        layout.add('center', new Ext.ContentPanel('tab1', {
            title      : '产品',
            toolbar    : null,
            closable   : false,
            fitToFrame : true
        }));
        layout.add('center', new Ext.ContentPanel('tab2', {
            title: "帮助",
            toolbar: null,
            closable: false,
            fitToFrame: true
        }));
        layout.restoreState();
    layout.endUpdate();
    layout.getRegion("center").showPanel("tab1");

    // 默认需要id, name, theSort, parent, children
    // 其他随意定制
    var metaData = [
        {id:'id',        qtip:"ID",       vType:"integer", allowBlank:true,defValue:-1,w:50},
        {id:'name',      qtip:"名称",     vType:"chn",     allowBlank:false,w:80},
        {id:'code',      qtip:"产品编码", vType:"chn",     allowBlank:false,w:80},
        {id:'type',      qtip:"产品类别", vType:"chn",     allowBlank:false,w:80},
        {id:'buyPrice',  qtip:"采购价格", vType:"float",   allowBlank:false,w:60},
        {id:'salePrice', qtip:"销售价格", vType:"float",   allowBlank:false,w:60},
        {id:'unit',      qtip:"单位",     vType:"chn",     allowBlank:false,w:40},
        {id:'pic',       qtip:"图片",     vType:"chn",     allowBlank:false,w:80},
        {id:'status',    qtip:"状态",     vType:"integer", allowBlank:false,w:80},
        {id:'inputMan',  qtip:"录入人",   vType:"chn",     allowBlank:false,w:80},
        {id:'inputTime', qtip:"录入时间", vType:"date",    allowBlank:false,w:90},
        {id:'descn',     qtip:"备注",     vType:"editor",  allowBlank:false,w:80,showInGrid:false}
    ];

    // 创建表格
    var lightGrid = new Ext.lingo.JsonGrid("lightgrid", {
        metaData      : metaData,
        dialogContent : "content"
    });

    // 渲染表格
    lightGrid.render();

});
