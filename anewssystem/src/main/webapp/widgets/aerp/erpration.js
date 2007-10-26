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
            title      : '配方',
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
        {id:'id',        qtip:"ID",       vType:"integer",  allowBlank:true,defValue:-1,w:50},
        {id:'product',   qtip:"产品",     vType:"comboBox", allowBlank:false,w:200,skip:true},
        {id:'material',  qtip:"原料",     vType:"comboBox", allowBlank:false,w:100,skip:true},
        {id:'rate',      qtip:"比率",     vType:"alphanum", allowBlank:false,w:100},
        {id:'unit',      qtip:"计量单位", vType:"alphanum", allowBlank:false,w:80},
        {id:'status',    qtip:"状态",     vType:"comboBox", allowBlank:false,w:100},
        {id:'inputMan',  qtip:"录入人",   vType:"chn",      allowBlank:false,w:100,showInGrid:false},
        {id:'inputTime', qtip:"录入时间", vType:"date",     allowBlank:false,w:100,showInGrid:false},
        {id:'descn',     qtip:"备注",     vType:"editor",   allowBlank:false,w:100,showInGrid:false}
    ];

    // 创建表格
    var lightGrid = new Ext.lingo.JsonGrid("lightgrid", {
        metaData      : metaData,
        dialogContent : "content"
    });

    // 渲染表格
    lightGrid.render();

    lightGrid.createDialog();

    var productRecord = Ext.data.Record.create([
        {name: 'id'},
        {name: 'name'}
    ]);
    var materialRecord = Ext.data.Record.create([
        {name: 'id'},
        {name: 'name'}
    ]);

    var productStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'../erpproduct/pagedQuery.htm'}),
        reader: new Ext.data.JsonReader({root:'result'},productRecord)
    });
    var materialStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'../erpmaterial/pagedQuery.htm'}),
        reader: new Ext.data.JsonReader({root:'result'},materialRecord)
    });
    lightGrid.columns.product_id = new Ext.form.ComboBox({
        id:'productId',
        name:'product',
        fieldLabel: '产品',
        hiddenName:'product_id',
        store: productStore,
        valueField:'id',
        displayField:'name',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'请选择',
        selectOnFocus:true,
        width:200,
        transform:'product_id'
    });
    lightGrid.columns.material_id = new Ext.form.ComboBox({
        id:'materialId',
        name:'material',
        fieldLabel: '原材料',
        hiddenName:'material_id',
        store: materialStore,
        valueField:'id',
        displayField:'name',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'请选择',
        selectOnFocus:true,
        width:200,
        transform:'material_id'
    });
    productStore.load();
    materialStore.load();

});
