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
            autoScroll     : false,
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
            title      : '基础信息管理',
            toolbar    : null,
            closable   : false,
            fitToFrame : true
        }));
/*
        layout.add('center', new Ext.ContentPanel('tab2', {
            title: "帮助",
            toolbar: null,
            closable: false,
            fitToFrame: true
        }));
*/
        layout.restoreState();
    layout.endUpdate();
    layout.getRegion("center").showPanel("tab1");

    // 表格的列模型
    var columnModel = new Ext.grid.ColumnModel([
        {header:'序号',sortable:true,dataIndex:'id',width:40},
        {header:'品名',sortable:true,dataIndex:'name',width:80},
        {header:'类别',sortable:true,dataIndex:'type',width:70,renderer:function(value){
            if (value == 0) {
                return "<span style='color:green;'>染料</span>";
            } else if (value == 1) {
                return "<span style='color:black;'>助剂</span>";
            } else if (value == 2) {
                return "<span style='color:blue;'>设备</span>";
            }
        }},
        {header:'成分',sortable:true,dataIndex:'material',width:80},
        {header:'供应商',sortable:true,dataIndex:'supplier',width:60},
        {header:'生产厂家',sortable:true,dataIndex:'factory',width:60},
        {header:'数量',sortable:true,dataIndex:'num',width:60},
        {header:'计量单位',sortable:true,dataIndex:'unit',width:60,renderer:function(value){
            if (value == 0) {
                return '千克';
            } else if (value == 1) {
                return '克';
            } else if (value == 2) {
                return '升';
            } else if (value == 3) {
                return '毫升';
            } else if (value == 4) {
                return '台';
            }
        }},
        {header:'单价',sortable:true,dataIndex:'price',width:100},
        {header:'总额',sortable:true,dataIndex:'sum',width:100}
    ]);
    columnModel.defaultSortable = false;

    // 创建数据模型
    var dataRecord = Ext.data.Record.create([
        {name:'id'},
        {name:'name'},
        {name:'type'},
        {name:'material'},
        {name:'supplier',mapping:"erp2Supplier.name"},
        {name:'factory'},
        {name:'num'},
        {name:'unit'},
        {name:'price'},
        {name:'sum'},
        {name:'descn'}
    ]);

    // 创建数据存储
    var dataStore = new Ext.lingo.Store({
        proxy  : new Ext.data.HttpProxy({url:'pagedQuery.htm'}),
        reader : new Ext.data.JsonReader({
            root          : "result",
            totalProperty : "totalCount",
            id            : "id"
        }, dataRecord),
        remoteSort : true
    });
    var grid = new Ext.lingo.CheckRowSelectionGrid('main-grid', {
        ds   : dataStore
        , cm : columnModel
        , selModel      : new Ext.lingo.CheckRowSelectionModel({useHistory:false})
        , enableColLock : false
        , loadMask      : true
    });
    //grid.on('rowdblclick', this.edit, this);
    //右键菜单
    //grid.addListener('rowcontextmenu', this.contextmenu, this);

    // 渲染表格
    grid.render();

    // 页头
    var gridHeader = grid.getView().getHeaderPanel(true);
    var toolbar = new Ext.Toolbar(gridHeader);
    toolbar.add('-','库存信息管理');

    // 页脚
    var gridFooter = grid.getView().getFooterPanel(true);

    // 把分页工具条，放在页脚
    var paging = new Ext.PagingToolbar(gridFooter, dataStore, {
        pageSize         : 15
        , displayInfo    : true
        , displayMsg     : '显示: {0} - {1} 共 {2} 条记录'
        , emptyMsg       : "没有找到相关数据"
        , beforePageText : "第"
        , afterPageText  : "页，共{0}页"
    });

    var pageSizePlugin = new Ext.ux.PageSizePlugin();
    pageSizePlugin.init(paging);

    // 查询
    var name = new Ext.form.TextField({
        id:'name',
        name:'name',
        fieldLabel: '品名'
    });
    var type = new Ext.form.ComboBox({
        id:'typeId',
        name:'type',
        readOnly:true,
        fieldLabel: '类别',
        hiddenName:'type',
        store: new Ext.data.SimpleStore({
            fields: ['id', 'name'],
            data : [[0,'染料'],[1,'助剂'],[2,'设备']]
        }),
        valueField:'id',
        displayField:'name',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'请选择',
        selectOnFocus:true,
        hideClearButton: false,
        hideTrigger: false,
        resizable:false
    });
    var supplierStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'../erp2supplier/pagedQueryForCombo.htm'}),
        reader: new Ext.data.JsonReader({
            root          : "result",
            totalProperty : "totalCount",
            id            : "id"
        },['name'])
    });
    var supplier = new Ext.form.ComboBox({
        id:'supplierId',
        name:'supplier',
        readOnly:false,
        fieldLabel: '供应商',
        hiddenName:'supplier',
        store: supplierStore,
        valueField:'name',
        displayField:'name',
        typeAhead: true,
        mode: 'remote',
        triggerAction: 'all',
        emptyText:'请选择',
        selectOnFocus:true,
        hideClearButton: false,
        hideTrigger: false,
        resizable:false,
        pageSize:10
    });
    //supplierStore.load({params:{limit:100}});
    var no = new Ext.form.NumberField({
        id:'no',
        name:'no',
        fieldLabel:'序号',
        width:60
    });
    name.applyTo('name');
    type.applyTo('typeId');
    supplier.applyTo('supplierId');
    no.applyTo('no');
    var search = new Ext.Button('search-button', {
        text:'查询',
        handler:function() {
            dataStore.reload();
            type.setValue('');
            type.setRawValue('请选择');
            type.clearValue();
        }
    });

    dataStore.on('beforeload', function() {
        dataStore.baseParams = {
            name     : name.getValue(),
            type     : type.getValue(),
            supplier : supplier.getRawValue(),
            no       : no.getValue()
        };
    });

    dataStore.load({
        params:{start:0, limit:paging.pageSize}
    });
});
