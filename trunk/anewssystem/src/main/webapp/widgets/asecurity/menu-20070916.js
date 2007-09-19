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
            title      : '菜单管理',
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
        {id : 'id',    qtip : "ID",       vType : "integer",  allowBlank : true,  defValue : -1},
        {id : 'url',   qtip : "链接地址", vType : "url",      allowBlank : false},
        {id : 'name',  qtip : "菜单名称", vType : "chn",      allowBlank : false},
        {id : 'qtip',  qtip : "提示信息", vType : "chn",      allowBlank : true},
        {id : 'image', qtip : "图片",     vType : "alphanum", allowBlank : true,  defValue : "user.gif"},
        {id : 'descn', qtip : "描述",     vType : "chn",      allowBlank : true}
    ];

    // 创建树形
    var lightTree = new Ext.lingo.JsonTree("lighttree", {
        metaData      : metaData,
        dlgWidth      : 500,
        dlgHeight     : 300,
        dialogContent : "content"
    });

    // 渲染树形
    lightTree.render();
});
