/*
 * Ext JS Library 1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author Lingo
 * @since 2007-11-11
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
            title      : '采购订单管理',
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
        {header:'订单编号',sortable:true,dataIndex:'code',width:80},
        {header:'订单状态',sortable:true,dataIndex:'status',width:70,renderer:function(value){
            if (value == 0) {
                return '待审';
            } else if (value == 1) {
                return '审核';
            } else if (value == 2) {
                return '完成';
            }
        }},
        {header:'订单名称',sortable:true,dataIndex:'name',width:80},
        {header:'供应商',sortable:true,dataIndex:'supplier',width:60},
        {header:'联系人',sortable:true,dataIndex:'linkman',width:60},
        {header:'要求供货日期',sortable:true,dataIndex:'provideDate',width:80},
        {header:'审核受理',sortable:true,dataIndex:'audit',width:60,renderer:function(value){
            if (value == 0) {
                return '立即审核';
            } else if (value == 1) {
                return '常规审核';
            }
        }},
        {header:'签订日期',sortable:true,dataIndex:'orderDate',width:80},
        {header:'订单签订人员',sortable:true,dataIndex:'username',width:80}
    ]);
    columnModel.defaultSortable = false;

    // 创建数据模型
    var dataRecord = Ext.data.Record.create([
        {name:'id'},
        {name:'code'},
        {name:'status'},
        {name:'name'},
        {name:'supplier',mapping:'erp2Supplier.name'},
        {name:'linkman'},
        {name:'provideDate'},
        {name:'audit'},
        {name:'orderDate'},
        {name:'username'}
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
    toolbar.add('-','采购订单管理');

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
    var code = new Ext.form.TextField({
        id:'code',
        name:'code',
        fieldLabel:'订单编号',
        width:100
    });
    var status = new Ext.form.ComboBox({
        id:'statusId',
        name:'status',
        readOnly:true,
        fieldLabel:'订单状态',
        hiddenName:'status',
        store:new Ext.data.SimpleStore({
            fields:['id', 'name'],
            data:[[0,'待审'],[1,'审核'],[2,'完成']]
        }),
        valueField:'id',
        displayField:'name',
        typeAhead:true,
        mode:'local',
        triggerAction:'all',
        emptyText:'请选择',
        selectOnFocus:true,
        hideClearButton:false,
        hideTrigger:false,
        resizable:false,
        width:120
    });
    var name = new Ext.form.TextField({
        id:'name',
        name:'name',
        fieldLabel:'订单名称',
        width:100
    });
    var orderDate = new Ext.form.DateField({
        id:'orderDate',
        name:'orderDate',
        fieldLabel:'签订日期',
        width:100
    });
    code.applyTo('code');
    status.applyTo('statusId');
    name.applyTo('name');
    orderDate.applyTo('orderDate');
    var search = new Ext.Button('search-button', {
        text:'查询',
        handler:function() {
            dataStore.reload();
            status.setValue('');
            status.setRawValue('请选择');
            status.clearValue();
        }
    });

    var insertHtml =
        '<div style="width:100%px;margin:auto;" id="insert-box">' +
            '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>' +
            '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">' +
                '<h3 style="text-align:left;">填写采购清单</h3>' +
                '<div id="insert-form"></div>' +
            '</div></div></div>' +
            '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>' +
        '</div>' +
        '<div class="x-form-clear"></div>';
    var updateHtml =
        '<div style="width:100%px;margin:auto;" id="update-box">' +
            '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>' +
            '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">' +
                '<h3 style="text-align:left;">修改采购清单</h3>' +
                '<div id="update-form"></div>' +
            '</div></div></div>' +
            '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>' +
        '</div>' +
        '<div class="x-form-clear"></div>';

    var view = new Ext.Button('view-button', {
        text:'查看',
        handler:function(){
            var m = grid.getSelections();
            if(m.length == 0) {
                Ext.MessageBox.alert('提示', '请选择查看的记录');
            } else if (m.length == 1) {
                window.open('./view.htm?id=' + m[0].get('id'));
            } else {
                Ext.MessageBox.alert('提示', '每次只能选择一条记录');
            }
        }
    });
    var insert = new Ext.Button('insert-button', {
        text:'添加',
        handler:function(){
            //layout.getRegion("center").showPanel("tab1");
            var tabs = layout.getRegion("center").tabs;
            tabs.removeTab('insert-tab');
            tabs.removeTab('update-tab');
            var tab = tabs.addTab('insert-tab', '填写采购订单', insertHtml, true);
            var insertForm = EditForm.getInsertForm(tabs.removeTab.createDelegate(tabs, ['insert-tab']));
            tabs.activate('insert-tab');
        }
    });
    var update = new Ext.Button('update-button', {
        text:'修改',
        handler:function(){
            var m = grid.getSelections();
            if(m.length == 0) {
                Ext.MessageBox.alert('提示', '请选择修改的记录');
            } else if (m.length == 1) {
                var tabs = layout.getRegion("center").tabs;
                tabs.removeTab('insert-tab');
                tabs.removeTab('update-tab');
                var tab = tabs.addTab('update-tab', '修改采购清单', updateHtml, true);
                var closeTabDelegate = tabs.removeTab.createDelegate(tabs, ['update-tab']);
                var updateForm = EditForm.getUpdateForm(closeTabDelegate, m[0].get('id'));
                tabs.activate('update-tab');
            } else {
                Ext.MessageBox.alert('提示', '每次只能选择一条记录');
            }
        }
    });
    var remove = new Ext.Button('remove-button', {
        text:'删除',
        handler:function() {
            var m = grid.getSelections();
            if(m.length > 0) {
                Ext.MessageBox.confirm('提示', '是否确定删除', function(btn){
                    if(btn == 'yes') {
                        var selections = grid.getSelections();
                        var ids = new Array();
                        for(var i = 0, len = selections.length; i < len; i++){
                            try {
                                selections[i].get("id");
                                ids[i] = selections[i].get("id");
                                dataStore.remove(selections[i]);//从表格中删除
                            } catch (e) {
                            }
                        }
                        Ext.Ajax.request({
                            url     : 'remove.htm?ids=' + ids,
                            success : function() {
                                Ext.MessageBox.alert('提示', '删除成功！');
                                //dataStore.reload();
                            },
                            failure : function(){Ext.MessageBox.alert('提示', '删除失败！');}
                        });
                    }
                });
            } else {
                Ext.MessageBox.alert('警告', '至少要选择一条记录');
            }
        }
    });

    dataStore.on('beforeload', function() {
        dataStore.baseParams = {
            code      : code.getValue(),
            status    : status.getValue(),
            name      : name.getValue(),
            orderDate : orderDate.getValue()
        };
    });

    dataStore.load({
        params:{start:0, limit:paging.pageSize}
    });
});

EditForm = {
    getInsertForm : function(closeTagHandler) {
        this.createForm('./insert.htm', 'insert-box');
        var form = this.form;

        form.addButton({
            text:'提交',
            handler:function(){
                if (!form.isValid()){
                    return;
                }
                form.el.mask('提交数据，请稍候...', 'x-mask-loading');
                var hide = function(){
                    form.el.unmask();
                };

                var buyOrderValue = EditForm.form.getValues();
                buyOrderValue.totalPrice = document.getElementById("amount1").innerHTML;
                EditForm.grid.stopEditing();
                var infoValues = new Array();
                var ds = EditForm.grid.dataSource;
                for (var i = 0; i < ds.getCount(); i++) {
                    var record = ds.getAt(i);
                    var item = {};
                    item.id = record.data.id;
                    item.productId = record.data.productId;
                    item.code = record.data.code;
                    item.parameter = record.data.parameter;
                    item.num = record.data.num;
                    item.unit = record.data.unit;
                    item.price = record.data.price;
                    item.totalPrice = record.data.totalPrice;
                    item.descn = record.data.descn;
                    infoValues.push(item);
                }
                var json = {buyOrderValue:buyOrderValue,infos:infoValues}
                Ext.lib.Ajax.request(
                    'POST',
                    './insert.htm',
                    {success:function(){
                        form.el.unmask();
                        Ext.MessageBox.confirm('提示', '保存添加成功，是否继续添加', function(btn){
                            if (btn == 'yes') {
                                form.reset();
                                EditForm.grid.dataSource.removeAll();
                                EditForm.form.findField("statusId2").clearValue("");
                                EditForm.form.findField("supplierNameId2").clearValue("");
                                EditForm.form.findField("auditId2").clearValue("");
                            } else {
                                closeTagHandler();
                            }
                        });
                    },failure:function(){
                        form.el.unmask();
                    }},
                    'data=' + encodeURIComponent(Ext.encode(json))
                );
            }
        });
        form.addButton({
            text:'重置',
            handler:function(){
                form.reset();
                EditForm.grid.dataSource.removeAll();
                EditForm.form.findField("statusId2").clearValue("");
                EditForm.form.findField("supplierNameId2").clearValue("");
                EditForm.form.findField("auditId2").clearValue("");
            }
        });
        form.addButton({
            text:'取消',
            handler:closeTagHandler
        });
        form.render("insert-form");

        this.createGrid();
        var grid = this.grid;

        return form;
    },
    getUpdateForm : function(closeTagHandler, id) {
        this.createForm('./update.htm', 'update-box');
        this.id = id;
        var form = this.form;
        form.load({url:'./loadData.htm?id=' + id});

        form.addButton({
            text:'提交',
            handler:function(){
                if (!form.isValid()) {
                    return;
                }
                form.el.mask('提交数据，请稍候...', 'x-mask-loading');
                var hide = function() {
                    form.el.unmask();
                };

                var buyOrderValue = EditForm.form.getValues();
                buyOrderValue.totalPrice = document.getElementById("amount1").innerHTML;
                buyOrderValue.id = EditForm.id;

                EditForm.grid.stopEditing();
                var infoValues = new Array();
                var ds = EditForm.grid.dataSource;
                for (var i = 0; i < ds.getCount(); i++) {
                    var record = ds.getAt(i);
                    var item = {};
                    item.id = record.data.id;
                    item.productId = record.data.productId;
                    item.code = record.data.code;
                    item.parameter = record.data.parameter;
                    item.num = record.data.num;
                    item.unit = record.data.unit;
                    item.price = record.data.price;
                    item.totalPrice = record.data.totalPrice;
                    item.descn = record.data.descn;
                    infoValues.push(item);
                }
                var json = {buyOrderValue:buyOrderValue,infos:infoValues}

                Ext.lib.Ajax.request(
                    'POST',
                    './update.htm',
                    {success:function(){
                        form.el.unmask();
                        Ext.MessageBox.confirm('提示', '修改成功，是否返回' , function(btn){
                            if (btn == 'yes') {
                                closeTagHandler();
                            } else {
                                form.reset();
                                EditForm.grid.dataSource.load({params:{id:EditForm.id}});
                            }
                        });
                    },failure:function(){
                        form.el.unmask();
                    }},
                    'data=' + encodeURIComponent(Ext.encode(json))
                );
            }
        });
        form.addButton({
            text:'重置',
            handler:function(){
                form.load({url:'./loadData.htm?id=' + this.id});
            }
        });
        form.addButton({
            text:'取消',
            handler:closeTagHandler
        });
        form.render("update-form");

        this.createGrid();
        var grid = this.grid;
        grid.dataSource.load({params:{id:id}});

        return form;
    },
    getRecordBuyOrderInfo : function() {
        // 数据模型
        this.RecordBuyOrderInfo = Ext.data.Record.create([
            {name:"id"},
            {name:"code"},
            {name:'productId',mapping:'erp2Product.id'},
            {name:"productName",mapping:'erp2Product.name'},
            {name:"productType",mapping:'erp2Product.type'},
            {name:"material",mapping:'erp2Product.material'},
            {name:'parameter'},
            {name:"factory",mapping:'erp2Product.factory'},
            {name:"num"},
            {name:"unit"},
            {name:"price"},
            {name:"totalPrice"},
            {name:"descn"}
        ]);
    },
    createForm : function(url, waitMsgTarget) {
        this.getRecordBuyOrderInfo();

        var form = new Ext.form.Form({
            labelAlign:'right',
            labelWidth:80,
            url:url,
            method: 'POST',
            waitMsgTarget:waitMsgTarget,
            reader : new Ext.data.JsonReader({}, [
                'code',
                'status',
                'name',
                {name:'supplierName',mapping:'erp2Supplier.name'},
                'linkman',
                'provideDate',
                'orderDate',
                'audit',
                'username'
            ])
        });

        form.fieldset(
            {legend:'采购订单', hideLabels:false}
        );
        var supplierDataStore = new Ext.data.Store({
                    proxy: new Ext.data.HttpProxy({url:'../erp2supplier/pagedQueryForCombo.htm'}),
                    reader: new Ext.data.JsonReader({
                        root          : "result",
                        totalProperty : "totalCount",
                        id            : "id"
                    },['id','name'])
                })
        var supplierName = new Ext.form.ComboBox({
                id:'supplierNameId2',
                name:'supplierName',
                readOnly:true,
                fieldLabel:'供应商名称',
                hiddenName:'supplierName',
                store: supplierDataStore,
                valueField:'id',
                displayField:'name',
                typeAhead:true,
                mode:'remove',
                triggerAction:'all',
                emptyText:'请选择',
                selectOnFocus:true,
                width:200,
                allowBlank:false,
                pageSize:10
            });
        form.column(
            {width:320},

            new Ext.form.TextField({
                fieldLabel:'采购订单编号',
                name:'code',
                width:200
            }),

            new Ext.form.ComboBox({
                id:'statusId2',
                name:'status',
                readOnly:true,
                fieldLabel:'订单状态',
                hiddenName:'status',
                store: new Ext.data.SimpleStore({
                    fields:['id', 'name'],
                    data:[[0,'待审'],[1,'审核'],[2,'完成']]
                }),
                valueField:'id',
                displayField:'name',
                typeAhead:true,
                mode:'local',
                triggerAction:'all',
                emptyText:'请选择',
                selectOnFocus:true,
                width:200,
                allowBlank:false
            }),

            new Ext.form.TextField({
                fieldLabel:'订单名称',
                name:'name',
                width:200
            }),

            supplierName,

            new Ext.form.TextField({
                fieldLabel:'联系人',
                name:'linkman',
                width:200
            })
        );

        form.column(
            {width:320, clear:true},

            new Ext.form.DateField({
                fieldLabel:'要求供货日期',
                name:'provideDate',
                width:200,
                format:'Y-m-d'
            }),

            new Ext.form.DateField({
                fieldLabel:'签订日期',
                name:'orderDate',
                width:200,
                format:'Y-m-d'
            }),

            new Ext.form.ComboBox({
                id:'auditId2',
                name:'audit',
                readOnly:true,
                fieldLabel:'审核受理',
                hiddenName:'audit',
                store: new Ext.data.SimpleStore({
                    fields:['id', 'name'],
                    data:[[0,'立即审核'],[1,'常规审核']]
                }),
                valueField:'id',
                displayField:'name',
                typeAhead:true,
                mode:'local',
                triggerAction:'all',
                emptyText:'请选择',
                selectOnFocus:true,
                width:200,
                allowBlank:false
            }),

            new Ext.form.TextField({
                fieldLabel:'订单签订人员',
                name:'username',
                width:200
            })
        );

        form.end();

        form.fieldset(
            {id:'insert-grid', legend:'订单详情', hideLabels:true}
        );

        //
        this.form = form;
    },
    createGrid : function() {
        // 选择产品的combo
        var productDataStore = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:'../erp2product/pagedQuery.htm'}),
                reader: new Ext.data.JsonReader({
                    root          : "result",
                    totalProperty : "totalCount",
                    id            : "id"
                },['id','name','material','factory','type','price','unit'])
            });
        var productCombo = new Ext.form.ComboBox({
                id:'productId2',
                name:'productName',
                readOnly:true,
                fieldLabel:'产品名称',
                hiddenName:'productName',
                store:productDataStore,
                valueField:'id',
                displayField:'name',
                typeAhead:true,
                mode:'remote',
                triggerAction:'all',
                emptyText:'请选择',
                selectOnFocus:true,
                width:200,
                allowBlank:false,
                pageSize:10
            });

        // 列模型
        var cm = new Ext.grid.ColumnModel([{
            header:"序号",
            dataIndex:"id",
            width:40
        },{
            header:"编号",
            dataIndex:"code",
            editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:false})),
            width:40
        },{
            header:"商品名称",
            dataIndex:"productName",
            editor:new Ext.grid.GridEditor(productCombo),
            width:100,
            renderer:function(value, cellmeta, record, RowIndex, ColumnIndex, store){
                try {
                    var productId = productCombo.getValue();
                    var productRecord = productDataStore.getById(productId);
                    return productRecord.data.name;
                } catch(e) {
                    return value;
                }
            }
        },{
            header:"类别",
            dataIndex:"productType",
            width:40,
            renderer:function(value){
                if (value == 0) {
                    return "<span style='color:green;'>染料</span>";
                } else if (value == 1) {
                    return "<span style='color:black;'>助剂</span>";
                } else if (value == 2) {
                    return "<span style='color:blue;'>设备</span>";
                }
            }
        },{
            header:"型号",
            dataIndex:"material",
            width:40
        },{
            header:"生产厂家",
            dataIndex:"factory",
            width:70
        },{
            header:"技术参数",
            dataIndex:"parameter",
            editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:false})),
            width:70
        },{
            header:'数量',
            dataIndex:'num',
            editor:new Ext.grid.GridEditor(new Ext.form.NumberField({allowBlank:false})),
            width:50
        },{
            header:'计量单位',
            dataIndex:'unit',
            width:70,
            renderer:function(value){
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
            },
            editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
                id:'unitId',
                name:'unit',
                readOnly:true,
                fieldLabel: '计量单位',
                hiddenName:'unit',
                store: new Ext.data.SimpleStore({
                    fields: ['id', 'name'],
                    data : [['0','千克'],['1','克'],['2','升'],['3','毫升'],['4','台']]
                }),
                valueField:'id',
                displayField:'name',
                typeAhead: true,
                mode: 'local',
                triggerAction: 'all',
                emptyText:'请选择',
                selectOnFocus:true
            }))
        },{
            header:'单价（元）',
            dataIndex:'price',
            editor:new Ext.grid.GridEditor(new Ext.form.NumberField({allowBlank:false})),
            width:70
        },{
            header:'金额（元）',
            dataIndex:'totalPrice',
            width:70
        },{
            header:'备注',
            dataIndex:'descn',
            editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:false})),
            width:70
        }]);
        cm.defaultSortable = true;//排序

        // 数据存储器
        var ds = new Ext.data.Store({
            proxy:new Ext.data.HttpProxy({url:'getBuyOrderInfosByBuyOrder.htm'}),
            reader:new Ext.data.JsonReader({}, this.RecordBuyOrderInfo),
            remoteSort:false
        });
        productCombo.on('select', function(){
            // 取得产品信息
            var productId = productCombo.getValue();
            var productRecord = productDataStore.getById(productId);
            // 取得选中这行的record
            var cell = grid.getSelectionModel().getSelectedCell();
            var record = ds.getAt(cell[0]);

            record.data.productId = productRecord.data.id;
            if (productRecord.data.type == 0) {
                record.data.code = 'RL' + new Date().format("Ymd") + "xxxx";
            } else if (productRecord.data.type == 1) {
                record.data.code = 'ZJ' + new Date().format("Ymd") + "xxxx";
            } else if (productRecord.data.type == 2) {
                record.data.code = 'SB' + new Date().format("Ymd") + "xxxx";
            }
            record.data.productName = productRecord.data.name;
            record.data.productType = productRecord.data.type;
            record.data.material = productRecord.data.material;
            record.data.factory = productRecord.data.factory;
            record.data.unit = productRecord.data.unit;
            record.data.price = productRecord.data.price;
            return false;
        });

        var gridDiv = Ext.get('insert-grid');
        gridDiv.createChild({
            tag:'div',
            id:'insert-grid2-container',
            cn:[{
                tag:'div',
                id:'insert-grid2',
                style:'border:1px solid #6FA0DF;'
            },{
                tag:'div',
                style:'margin-top:5px;margin-bottom:5px;',
                cn:[{
                    tag:'span',
                    html:'金额合计：'
                },{
                    tag:'span',
                    id:'amount1',
                    html:'0'
                },{
                    tag:'span',
                    html:'元 （大写） '
                },{
                    tag:'span',
                    id:'amount2',
                    html:'零'
                }]
            }]
        });
/*
        insertGrid.createChild({
            tag:'div',
            id:'insert-grid2-resize'
        });
*/
        var grid = new Ext.grid.EditorGrid('insert-grid2', {ds: ds, cm: cm, enableColLock: false});

        ds.on("update", function() {
            var amount = 0;
            for(var i = 0, len = ds.getCount(); i < len; i++){
                var record = ds.getAt(i);
                var num = record.data.num;
                var price = record.data.price;
                record.data.totalPrice = num * price;
                amount += record.data.totalPrice;
            }
            document.getElementById("amount1").innerHTML = amount;
            document.getElementById("amount2").innerHTML = convertCurrency(amount);
        });
/*
        var rz = new Ext.Resizable('insert-grid2-resize', {
            wrap:true,
            minHeight:100,
            pinned:true,
            handles:'s'
        });
        rz.on('resize', insertGrid.autoSize, insertGrid);
*/
        grid.render();

        // 页头
        var gridHead = grid.getView().getHeaderPanel(true);

        var tb = new Ext.Toolbar(gridHead, [{
            text: '添加',
            handler : function(){
              var entity = new EditForm.RecordBuyOrderInfo({
                id:-1,
                code:'',
                productId:-1,
                productName:'',
                productType:'',
                material:'',
                parameter:'',
                factory:'',
                num:0,
                unit:'',
                price:'',
                totalPrice:'',
                descn:''
              });
              grid.stopEditing();
              ds.insert(0, entity);
              grid.startEditing(0, 1);
            }
        }, {
            text: '删除',
            handler: function() {
                if(grid.getSelectionModel().hasSelection()) {
                    Ext.MessageBox.confirm('提示', '是否确定删除' , function(btn){
                        if(btn == 'yes') {
                            var cell = grid.getSelectionModel().getSelectedCell();
                            var record = ds.getAt(cell[0]);
                            var id = record.get("id");
                            ds.remove(record);
                            if (id != -1) {
                                Ext.Ajax.request({
                                    url     : '../erp2buyorderinfo/remove.htm?ids=' + id,
                                    success : function() {
                                        Ext.MessageBox.alert('提示', '删除成功！');
                                    },
                                    failure : function(){
                                        Ext.MessageBox.alert('提示', '删除失败！');
                                    }
                                });
                            }
                        }
                    });
                } else {
                    Ext.MessageBox.alert('警告', '至少要选择一条记录');
                }
            }
        }]);

        this.grid = grid;
    }
};

