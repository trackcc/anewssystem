/*
 * Ext JS Library 1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author Lingo
 * @since 2007-09-13
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
            title      : '菜单设置',
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
        {id : 'id',         qtip : "ID",       vType : "integer",  allowBlank : true,  defValue : -1, w:50},
        {id : 'name',       qtip : "菜单名称", vType : "chn",      allowBlank : false, w:100},
        {id : 'qtip',       qtip : "提示",     vType : "chn",      allowBlank : true, w:100},
        {id : 'url',        qtip : "访问路径", vType : "url",      allowBlank : true, w:100},
        {id : 'image',      qtip : "图片",     vType : "alphanum", allowBlank : true,  defValue : "user.gif", w:100},
        {id : 'updateDate', qtip : "更新时间", vType : "date",     allowBlank : false, w:120},
        {id : 'descn',      qtip : "描述",     vType : "chn",      allowBlank : true, w:150}
    ];

    // 创建表格
    var lightGrid = new Ext.lingo.JsonGrid("lightgrid", {
        metaData      : metaData,
        dialogContent : "content",
        urlPagedQuery : "pagedQuery.json",
        urlSave       : "success.json",
        urlLoadData   : "loadData.json",
        urlRemove     : "success.json"
    });

    // 渲染表格
    lightGrid.render();

});
