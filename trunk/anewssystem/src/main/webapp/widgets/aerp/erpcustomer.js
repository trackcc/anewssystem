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
            title      : '客户',
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
        {id:'name',      qtip:"客户名称", vType:"chn",      allowBlank:false,w:200},
        {id:'code',      qtip:"客户编码", vType:"chn",      allowBlank:false,w:100,showInGrid:false},
        {id:'type',      qtip:"客户类型", vType:"comboBox", allowBlank:false,w:100,showInGrid:false},
        {id:'zip',       qtip:"邮编",     vType:"alphanum", allowBlank:false,w:80,showInGrid:false},
        {id:'leader',    qtip:"负责人",   vType:"chn",      allowBlank:false,w:80,showInGrid:false},
        {id:'fax',       qtip:"传真",     vType:"chn",      allowBlank:false,w:100,showInGrid:false},
        {id:'linkMan',   qtip:"联系人",   vType:"chn",      allowBlank:false,w:200},
        {id:'email',     qtip:"电子邮件", vType:"alphanum", allowBlank:false,w:100,showInGrid:false},
        {id:'tel',       qtip:"电话",     vType:"alphanum", allowBlank:false,w:100},
        {id:'homepage',  qtip:"主页",     vType:"url",      allowBlank:false,w:100,showInGrid:false},
        {id:'province',  qtip:"省",       vType:"comboBox", allowBlank:false,w:100,showInGrid:false,skip:true},
        {id:'city',      qtip:"市",       vType:"comboBox", allowBlank:false,w:100,showInGrid:false,skip:true},
        {id:'town',      qtip:"县",       vType:"comboBox", allowBlank:false,w:100,showInGrid:false,skip:true},
        {id:'address',   qtip:"地址",     vType:"chn",      allowBlank:false,w:100,showInGrid:false},
        {id:'source',    qtip:"客户渠道", vType:"comboBox", allowBlank:false,w:100,showInGrid:false},
        {id:'rank',      qtip:"信用等级", vType:"comboBox", allowBlank:false,w:100},
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

    var provinceId;
    var cityId;
    var townId;

    var regionRecord = Ext.data.Record.create([
        {name: 'id'},
        {name: 'name'}
    ]);

    var provinceStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'../region/getChildren.htm'}),
        reader: new Ext.data.JsonReader({},regionRecord)
    });
    var cityStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'../region/getChildren.htm'}),
        reader: new Ext.data.JsonReader({},regionRecord)
    });
    var townStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'../region/getChildren.htm'}),
        reader: new Ext.data.JsonReader({},regionRecord)
    });
    lightGrid.createDialog();
    lightGrid.columns.province = new Ext.form.ComboBox({
        id:'province',
        name:'province',
        fieldLabel: '省',
        hiddenName:'province',
        store: provinceStore,
        valueField:'id',
        displayField:'name',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'请选择',
        selectOnFocus:true,
        width:200,
        transform:'province'
    });
    lightGrid.columns.city = new Ext.form.ComboBox({
        id:'city',
        name:'city',
        fieldLabel: '市',
        hiddenName:'city',
        store: cityStore,
        valueField:'id',
        displayField:'name',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'请选择',
        selectOnFocus:true,
        width:200,
        transform:'city'
    });
    lightGrid.columns.town = new Ext.form.ComboBox({
        id:'town',
        name:'town',
        fieldLabel: '县',
        hiddenName:'town',
        store: townStore,
        valueField:'id',
        displayField:'name',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'请选择',
        selectOnFocus:true,
        width:200,
        transform:'town'
    });
    provinceStore.load();
    //cityStore.load();
    //townStore.load();

    lightGrid.columns.province.on('select',function() {
        provinceId = lightGrid.columns.province.getValue();
        cityStore.load({
            params:{node:provinceId}
        });
    });

    lightGrid.columns.city.on('select',function() {
        cityId = lightGrid.columns.city.getValue();
        townStore.load({
            params:{node:cityId}
        });
    });

});
