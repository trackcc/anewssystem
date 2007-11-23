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
        {header:'供应商名称',sortable:true,dataIndex:'name',width:80},
        {header:'类别',sortable:true,dataIndex:'type',width:70,renderer:function(value){
            if (value == 0) {
                return "<span style='color:green;'>染料供应商</span>";
            } else if (value == 1) {
                return "<span style='color:black;'>助剂供应商</span>";
            } else if (value == 2) {
                return "<span style='color:blue;'>设备供应商</span>";
            } else if (value == 3) {
                return "<span style='color:red;'>综合供应商</span>";
            }
        }},
        {header:'所在地',sortable:true,dataIndex:'area',width:80},
        {header:'联系人',sortable:true,dataIndex:'linkman',width:60},
        {header:'电话',sortable:true,dataIndex:'tel',width:60},
        {header:'传真',sortable:true,dataIndex:'fax',width:60},
        {header:'地址',sortable:true,dataIndex:'address',width:60},
        {header:'邮编',sortable:true,dataIndex:'zip',width:80},
        {header:'E-mail',sortable:true,dataIndex:'email',width:80},
        {header:'信用等级',sortable:true,dataIndex:'rank',width:60,renderer:function(value){
            if (value == 0) {
                return "<span style='color:green;'>优秀</span>";
            } else if (value == 1) {
                return "<span style='color:black;'>良好</span>";
            } else if (value == 2) {
                return "<span style='color:blue;'>一般</span>";
            } else if (value == 3) {
                return "<span style='color:red;'>差</span>";
            }
        }}
    ]);
    columnModel.defaultSortable = false;

    // 创建数据模型
    var dataRecord = Ext.data.Record.create([
        {name:'id'},
        {name:'name'},
        {name:'type'},
        {name:'area'},
        {name:'linkman'},
        {name:'tel'},
        {name:'fax'},
        {name:'address'},
        {name:'zip'},
        {name:'email'},
        {name:'rank'},
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
    toolbar.add('-','供应商列表');

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
        fieldLabel: '供应商名称'
    });
    var type = new Ext.form.ComboBox({
        id:'typeId',
        name:'type',
        readOnly:true,
        fieldLabel: '类别',
        hiddenName:'type',
        store: new Ext.data.SimpleStore({
            fields: ['id', 'name'],
            data : [[0,'染料供应商'],[1,'助剂供应商'],[2,'设备供应商'],[3,'综合供应商']]
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
    var linkman = new Ext.form.TextField({
        id:'linkman',
        name:'linkman',
        fieldLabel:'联系人',
        width:100
    });
    var no = new Ext.form.NumberField({
        id:'no',
        name:'no',
        fieldLabel:'序号',
        width:60
    });
    name.applyTo('name');
    type.applyTo('typeId');
    linkman.applyTo('linkman');
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

    var insertHtml =
        '<div style="width:100%px;margin:auto;" id="insert-box">' +
            '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>' +
            '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">' +
                '<h3 style="text-align:left;">添加供应商</h3>' +
                '<div id="insert-form"></div>' +
            '</div></div></div>' +
            '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>' +
        '</div>' +
        '<div class="x-form-clear"></div>';
    var updateHtml =
        '<div style="width:100%px;margin:auto;" id="update-box">' +
            '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>' +
            '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">' +
                '<h3 style="text-align:left;">修改供应商</h3>' +
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
            var tab = tabs.addTab('insert-tab', '添加供应商', insertHtml, true);
            var insertForm = Supplier.getInsertForm(tabs.removeTab.createDelegate(tabs, ['insert-tab']));
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
                var tab = tabs.addTab('update-tab', '修改供应商', updateHtml, true);
                var closeTabDelegate = tabs.removeTab.createDelegate(tabs, ['update-tab']);
                var updateForm = Supplier.getUpdateForm(closeTabDelegate, m[0].get('id'));
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
                Ext.MessageBox.confirm('提示', '是否确定删除' , function(btn){
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
            name : name.getValue(),
            type     : type.getValue(),
            linkman  : linkman.getValue(),
            no       : no.getValue()
        };
    });

    dataStore.load({
        params:{start:0, limit:paging.pageSize}
    });
});

Supplier = {
    getInsertForm : function(closeTagHandler) {
        this.createForm('./insert.htm', 'insert-box');
        var form = this.form;

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

                var supplierValue = Supplier.form.getValues();
                Supplier.grid.stopEditing();
                var productValues = new Array();
                var ds = Supplier.grid.dataSource;
                for (var i = 0; i < ds.getCount(); i++) {
                    var record = ds.getAt(i);
                    var item = {};
                    item.id = record.data.id;
                    item.name = record.data.name;
                    item.type = record.data.type;
                    item.material = record.data.material;
                    item.factory = record.data.factory;
                    item.price = record.data.price;
                    item.unit = record.data.unit;
                    productValues.push(item);
                }
                var json = {supplierValue:supplierValue,products:productValues}
                Ext.lib.Ajax.request(
                    'POST',
                    './insert.htm',
                    {success:function(){
                        form.el.unmask();
                        Ext.MessageBox.confirm('提示', '保存添加成功，是否继续添加' , function(btn){
                            if (btn == 'yes') {
                                form.reset();
                                form.findField("typeId2").clearValue("");
                                form.findField("rankId").clearValue("");
                                Supplier.grid.dataSource.removeAll();
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
                Supplier.form.reset();
                form.findField("typeId2").clearValue("");
                form.findField("rankId").clearValue("");
                Supplier.grid.dataSource.removeAll();
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

        // 初始化地区的省市县信息
        Ext.lib.Ajax.request(
            'GET',
            './getRegion.htm?id=' + this.id,
            {success:function(response){
                var region = Ext.decode(response.responseText);

                Supplier.form.findField("town").setValue(region.id);
                Supplier.form.findField("townId").setRawValue(region.name);
                Supplier.form.findField("city").setValue(region.parent.id);
                Supplier.form.findField("cityId").setRawValue(region.parent.name);
                Supplier.form.findField("province").setValue(region.parent.parent.id);
                Supplier.form.findField("provinceId").setRawValue(region.parent.parent.name);
            },failure:function(){}}
        );

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

                var supplierValue = Supplier.form.getValues();
                supplierValue.id = Supplier.id;

                Supplier.grid.stopEditing();
                var productValues = new Array();
                var ds = Supplier.grid.dataSource;
                for (var i = 0; i < ds.getCount(); i++) {
                    var record = ds.getAt(i);
                    var item = {};
                    item.id = record.data.id;
                    item.name = record.data.name;
                    item.type = record.data.type;
                    item.material = record.data.material;
                    item.factory = record.data.factory;
                    item.price = record.data.price;
                    item.unit = record.data.unit;
                    productValues.push(item);
                }
                var json = {supplierValue:supplierValue,products:productValues}
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
                                Supplier.grid.dataSource.load({params:{id:Supplier.id}});
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
    createForm : function(url, waitMsgTarget) {
        var form = new Ext.form.Form({
            labelAlign:'right',
            labelWidth:80,
            url:url,
            method: 'POST',
            waitMsgTarget:waitMsgTarget,
            reader : new Ext.data.JsonReader({}, [
                {name:'name'},
                {name:'code'},
                {name:'homepage'},
                {name:'type'},
                {name:'area'},
                {name:'linkman'},
                {name:'lead'},
                {name:'tel'},
                {name:'fax'},
                {name:'address'},
                {name:'zip'},
                {name:'email'},
                {name:'rank'},
                {name:'descn'}
            ])
        });

        form.fieldset(
            {legend:'供应商信息', hideLabels:false}
        );
        form.column(
            {width:320},

            new Ext.form.TextField({
                fieldLabel: '供应商名称',
                name: 'name',
                width:200
            }),

            new Ext.form.ComboBox({
                id:'typeId2',
                name:'type',
                readOnly:true,
                fieldLabel:'类别',
                hiddenName:'type',
                store: new Ext.data.SimpleStore({
                    fields: ['id', 'name'],
                    data : [[0,'染料供应商'],[1,'助剂供应商'],[2,'设备供应商'],[3,'综合供应商']]
                }),
                valueField:'id',
                displayField:'name',
                typeAhead: true,
                mode: 'local',
                triggerAction: 'all',
                emptyText:'请选择',
                selectOnFocus:true,
                width:200,
                allowBlank:false
            }),

            new Ext.form.TextField({
                fieldLabel: '联系人',
                name: 'linkman',
                width:200
            }),

            new Ext.form.TextField({
                fieldLabel: '负责人',
                name: 'lead',
                width:200
            }),

            new Ext.form.TextField({
                fieldLabel: '电话',
                name: 'tel',
                width:200
            }),

            new Ext.form.ComboBox({
                id:'rankId',
                name:'rank',
                readOnly:true,
                fieldLabel: '信用等级',
                hiddenName:'rank',
                store: new Ext.data.SimpleStore({
                    fields: ['id', 'name'],
                    data : [[0,'优秀'],[1,'良好'],[2,'一般'],[3,'差']]
                }),
                valueField:'id',
                displayField:'name',
                typeAhead: true,
                mode: 'local',
                triggerAction: 'all',
                emptyText:'请选择',
                selectOnFocus:true,
                width:200,
                allowBlank:false
            })
        );

        form.column(
            {width:320, clear:true},
            new Ext.form.TextField({
                fieldLabel: '供应商编号',
                name: 'code',
                width:200
            }),

            new Ext.form.TextField({
                fieldLabel: '邮编',
                name: 'zip',
                width:200
            }),

            new Ext.form.TextField({
                fieldLabel: '传真',
                name: 'fax',
                width:200
            }),

            new Ext.form.TextField({
                fieldLabel: 'Email',
                name: 'email',
                width:200
            }),

            new Ext.form.TextField({
                fieldLabel: '主页',
                name: 'homepage',
                width:200
            }),

            new Ext.form.TextField({
                fieldLabel: '地址',
                name: 'address',
                width:200
            })
        );

        // 省市县三级级联
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
        var province = new Ext.form.ComboBox({
            id:'provinceId',
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
            width:100,
            readOnly:true,
            allowBlank:false
        });
        var city = new Ext.form.ComboBox({
            id:'cityId',
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
            width:100,
            readOnly:true,
            allowBlank:false
        });
        var town = new Ext.form.ComboBox({
            id:'townId',
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
            width:100,
            readOnly:true,
            allowBlank:true
        });
        provinceStore.load();
        //cityStore.load();
        //townStore.load();

        province.on('select',function() {
            provinceId = province.getValue();
            cityStore.load({
                params:{node:provinceId}
            });
        });

        city.on('select',function() {
            cityId = city.getValue();
            townStore.load({
                params:{node:cityId}
            });
        });


        form.column({width:210, labelWidth:80}, province);
        form.column({width:210, labelWidth:80}, city);
        form.column({width:210, labelWidth:80, clear:true}, town);
        form.column({width:640, clear:true},
            new Ext.form.TextField({
                id:'descn',
                name:'descn',
                fieldLabel:'备注',
                width:520,
                height:60
            })
        );
        form.end();

        form.fieldset(
            {id:'insert-grid', legend:'供应商品', hideLabels:true}
        );

        //
        this.form = form;
    },
    createGrid : function() {
        // 列模型
        var cm = new Ext.grid.ColumnModel([{
            header:"序号",
            dataIndex:"id"
        },{
            header:"品名",
            dataIndex:"name",
            editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:false}))
        },{
            header:"类别",
            dataIndex:"type",
            renderer:function(value){
                if (value == 0) {
                    return '染料';
                } else if (value == 1) {
                    return '助剂';
                } else if (value == 2) {
                    return '设备';
                }
            },
            editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
                id:'typeId3',
                name:'type2',
                readOnly:true,
                fieldLabel: '类别',
                hiddenName:'type2',
                store: new Ext.data.SimpleStore({
                    fields: ['id', 'name'],
                    data : [['0','染料'],['1','助剂'],['2','设备']]
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
            header:"型号",
            dataIndex:"material",
            editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:false}))
        },{
            header:"生产厂家",
            dataIndex:"factory",
            editor:new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank:false}))
        },{
            header:"单价",
            dataIndex:"price",
            editor:new Ext.grid.GridEditor(new Ext.form.NumberField({allowBlank:false}))
        },{
            header:"计量单位",
            dataIndex:"unit",
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
        }]);
        cm.defaultSortable = true;//排序

        // 数据模型
        var RecordProduct = Ext.data.Record.create([
            {name: "id"},
            {name: "name"},
            {name: "type"},
            {name: "material"},
            {name: "factory"},
            {name: "price"},
            {name: "unit"}
        ]);

        // 数据存储器
        var ds = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url:'getProductsBySupplier.htm'}),
            reader: new Ext.data.JsonReader({}, RecordProduct),
            remoteSort: false
        });

        var gridDiv = Ext.get('insert-grid');
        gridDiv.createChild({
            tag:'div',
            id:'insert-grid2',
            style:'border:1px solid #6FA0DF;'
        });
/*
        insertGrid.createChild({
            tag:'div',
            id:'insert-grid2-resize'
        });
*/
        var grid = new Ext.grid.EditorGrid('insert-grid2', {ds: ds, cm: cm, enableColLock: false});
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
              var entity = new RecordProduct({
                    id:-1,
                    name:'',
                    type:0,
                    material:'',
                    factory:'',
                    price:0,
                    unit:0
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
                                    url     : '../erp2product/remove.htm?ids=' + id,
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

