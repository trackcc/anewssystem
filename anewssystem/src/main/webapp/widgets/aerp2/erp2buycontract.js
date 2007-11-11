/*
 * Ext JS Library 1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author Lingo
 * @since 2007-11-12
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
            title      : '采购合同管理',
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
        {header:'合同编号',sortable:true,dataIndex:'code',width:80},
        {header:'合同状态',sortable:true,dataIndex:'status',width:70},
        {header:'合同名称',sortable:true,dataIndex:'name',width:80},
        {header:'供应商',sortable:true,dataIndex:'supplier',width:60},
        {header:'联系人',sortable:true,dataIndex:'linkman',width:60},
        {header:'要求供货日期',sortable:true,dataIndex:'provideDate',width:100},
        {header:'收货情况',sortable:true,dataIndex:'receipt',width:80},
        {header:'签订日期',sortable:true,dataIndex:'contractDate',width:80},
        {header:'订单签订人员',sortable:true,dataIndex:'username',width:80}
    ]);
    columnModel.defaultSortable = false;

    // 创建数据模型
    var dataRecord = Ext.data.Record.create([
        {name:'id'},
        {name:'code'},
        {name:'status'},
        {name:'name'},
        {name:'supplier',mapping:"erp2Supplier.name"},
        {name:'linkman'},
        {name:'provideDate'},
        {name:'receipt'},
        {name:'contractDate'},
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
    toolbar.add('-','采购合同管理');

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
        fieldLabel: '合同编号',
        width:100
    });
    var status = new Ext.form.TextField({
        id:'status',
        name:'status',
        fieldLabel:'合同状态',
        width:100
    });
    var name = new Ext.form.TextField({
        id:'name',
        name:'name',
        fieldLabel:'合同名称',
        width:100
    });
    var contractDate = new Ext.form.DateField({
        id:'contractDate',
        name:'contractDate',
        format:'Y-m-d',
        fieldLabel:'签订日期',
        width:100
    });
    code.applyTo('code');
    status.applyTo('status');
    name.applyTo('name');
    contractDate.applyTo('contractDate');
    var search = new Ext.Button('search-button', {
        text:'查询',
        handler:function() {
            dataStore.reload();
        }
    });

    var insertHtml =
        '<div style="width:100%px;margin:auto;" id="insert-box">' +
            '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>' +
            '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">' +
                '<h3 style="text-align:left;">添加采购合同</h3>' +
                '<div id="insert-form"></div>' +
            '</div></div></div>' +
            '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>' +
        '</div>' +
        '<div class="x-form-clear"></div>';
    var updateHtml =
        '<div style="width:100%px;margin:auto;" id="update-box">' +
            '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>' +
            '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">' +
                '<h3 style="text-align:left;">修改采购合同</h3>' +
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
            var tab = tabs.addTab('insert-tab', '添加采购合同', insertHtml, true);
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
                var tab = tabs.addTab('update-tab', '修改采购合同', updateHtml, true);
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
            code : code.getValue(),
            status : status.getValue(),
            name : name.getValue(),
            contractDate : contractDate.getValue()
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
                if (form.isValid()) {
                    form.submit({
                        success:function(form, action){
                            Ext.MessageBox.confirm('提示', '保存添加成功，是否继续添加' , function(btn){
                                if (btn == 'yes') {
                                    form.reset();
                                } else {
                                    closeTagHandler();
                                }
                            });
                        }
                        ,failure:function(form, action){
                            Ext.suggest.msg('错误', action.result.response);
                        }
                        ,waitMsg:'提交中...'
                    });
                }
            }
        });
        form.addButton({
            text:'重置',
            handler:function(){
                EditForm.form.reset();
            }
        });
        form.addButton({
            text:'取消',
            handler:closeTagHandler
        });
        form.render("insert-form");

        return form;
    },
    getUpdateForm : function(closeTagHandler, id) {
        this.createForm('./insert.htm', 'update-box');
        this.id = id;
        var form = this.form;

        form.addButton({
            text:'提交',
            handler:function(){
                if (form.isValid()) {
                    form.submit({
                        params:{id:EditForm.id},
                        success:function(form, action){
                            Ext.MessageBox.confirm('提示', '保存修改成功，是否返回' , function(btn){
                                if (btn == 'yes') {
                                    closeTagHandler();
                                } else {
                                    form.reset();
                                }
                            });
                        }
                        ,failure:function(form, action){
                            Ext.suggest.msg('错误', action.result.response);
                        }
                        ,waitMsg:'提交中...'
                    });
                }
            }
        });
        form.addButton({
            text:'重置',
            handler:function(){
                form.reset();
            }
        });
        form.addButton({
            text:'取消',
            handler:closeTagHandler
        });
        form.render("update-form");
        form.load({url:'./loadData.htm?id=' + id});

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
                {name:'code'},
                {name:'status'},
                {name:'name'},
                {name:'supplier',mapping:"erp2Supplier.name"},
                {name:'linkman'},
                {name:'provideDate'},
                {name:'receipt'},
                {name:'contractDate'},
                {name:'username'}
            ])
        });

        form.fieldset(
            {legend:'采购合同信息', hideLabels:false},
            new Ext.form.TextField({
                fieldLabel:'合同编号',
                name:'code',
                width:400,
                allowBlank:false
            }),

            new Ext.form.TextField({
                fieldLabel:'合同状态',
                name:'status',
                width:400,
                allowBlank:false
            }),

            new Ext.form.TextField({
                fieldLabel:'合同名称',
                name:'name',
                width:400,
                allowBlank:false
            }),

            new Ext.form.ComboBox({
                id:'supplierNameId2',
                name:'supplier',
                readOnly:true,
                fieldLabel:'供应商',
                hiddenName:'supplier',
                store: new Ext.data.Store({
                    proxy: new Ext.data.HttpProxy({url:'../erp2supplier/pagedQueryForCombo.htm'}),
                    reader: new Ext.data.JsonReader({
                        root          : "result",
                        totalProperty : "totalCount",
                        id            : "id"
                    },['id','name'])
                }),
                valueField:'id',
                displayField:'name',
                typeAhead:true,
                mode:'remote',
                triggerAction:'all',
                emptyText:'请选择',
                selectOnFocus:true,
                width:400,
                allowBlank:false,
                pageSize:10
            }),

            new Ext.form.TextField({
                fieldLabel:'联系人',
                name:'linkman',
                width:400,
                allowBlank:false
            }),

            new Ext.form.DateField({
                fieldLabel:'要求供货日期',
                name:'provideDate',
                format:'Y-m-d',
                width:400,
                allowBlank:false
            }),

            new Ext.form.TextField({
                fieldLabel:'收货状态',
                name:'receipt',
                width:400,
                allowBlank:false
            }),

            new Ext.form.DateField({
                fieldLabel:'签订日期',
                name:'contractDate',
                format:'Y-m-d',
                width:400,
                allowBlank:false
            }),

            new Ext.form.TextField({
                fieldLabel:'订单签订人员',
                name:'username',
                width:400,
                allowBlank:false
            })
        );

        //
        this.form = form;
    }
};
