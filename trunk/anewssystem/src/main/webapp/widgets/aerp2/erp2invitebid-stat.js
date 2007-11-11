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
            title      : '招标投标管理',
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
        {header:'投标单位名称',sortable:true,dataIndex:'companyName',width:80},
        {header:'所投标书编号',sortable:true,dataIndex:'bidCode',width:90},
        {header:'进标价格',sortable:true,dataIndex:'price',width:80},
        {header:'投标单位地址',sortable:true,dataIndex:'address',width:80},
        {header:'联系电话',sortable:true,dataIndex:'tel',width:100},
        {header:'投标日期',sortable:true,dataIndex:'bidDate',width:100},
        {header:'电子信箱',sortable:true,dataIndex:'email',width:80},
        {header:'详细说明',sortable:true,dataIndex:'descn',width:100,renderer:function(value){
            if (value.length > 10) {
                return value.substring(0, 5) + "...";
            } else {
                return value;
            }
        }}
    ]);
    columnModel.defaultSortable = false;

    // 创建数据模型
    var dataRecord = Ext.data.Record.create([
        {name:'id'},
        {name:'companyName',mapping:'erp2Supplier.name'},
        {name:'bidCode',mapping:"erp2Bid.code"},
        {name:'price'},
        {name:'address'},
        {name:'tel'},
        {name:'bidDate'},
        {name:'email'},
        {name:'descn'}
    ]);

    // 创建数据存储
    var dataStore = new Ext.lingo.Store({
        proxy  : new Ext.data.HttpProxy({url:'pagedQueryForStat.htm'}),
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
    toolbar.add('-','投标管理');

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
    var bidStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'../erp2bid/pagedQueryForCombo.htm'}),
        reader: new Ext.data.JsonReader({
            root          : "result",
            totalProperty : "totalCount",
            id            : "id"
        },['code'])
    });
    var bidCode = new Ext.form.ComboBox({
        id:'bidCodeId',
        name:'bidCode',
        readOnly:false,
        fieldLabel:'标书编号',
        hiddenName:'bidCode',
        store:bidStore,
        valueField:'code',
        displayField:'code',
        typeAhead: true,
        mode:'remote',
        triggerAction:'all',
        emptyText:'请选择',
        selectOnFocus:true,
        hideClearButton: false,
        hideTrigger: false,
        resizable:false,
        pageSize:10
    });
    var supplierStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'../erp2supplier/pagedQueryForCombo.htm'}),
        reader: new Ext.data.JsonReader({
            root          : "result",
            totalProperty : "totalCount",
            id            : "id"
        },['name'])
    });
    var supplierName = new Ext.form.ComboBox({
        id:'supplierNameId',
        name:'supplierName',
        readOnly:false,
        fieldLabel:'投标单位名称',
        hiddenName:'supplierName',
        store:supplierStore,
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
    var bidDate = new Ext.form.DateField({
        id:'bidDate',
        name:'bidDate',
        format:'Y-m-d',
        fieldLabel:'投标时间'
    });
    bidCode.applyTo('bidCodeId');
    supplierName.applyTo('supplierNameId');
    bidDate.applyTo('bidDate');
    var search = new Ext.Button('search-button', {
        text:'查询',
        handler:function() {
            dataStore.reload();
            bidCode.clearValue();
        }
    });

    dataStore.on('beforeload', function() {
        dataStore.baseParams = {
            bidCode      : bidCode.getValue(),
            supplierName : supplierName.getRawValue(),
            bidDate      : bidDate.getValue()
        };
    });

    dataStore.load({
        params:{start:0, limit:paging.pageSize}
    });
});

