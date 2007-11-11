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
        {header:'标书编号',sortable:true,dataIndex:'code',width:80},
        {header:'产品名称',sortable:true,dataIndex:'productName',width:70},
        {header:'产品数量',sortable:true,dataIndex:'productNum',width:80},
        {header:'技术参数',sortable:true,dataIndex:'parameter',width:60},
        {header:'投标开始日期',sortable:true,dataIndex:'startDate',width:100},
        {header:'投标截止日期',sortable:true,dataIndex:'endDate',width:100},
        {header:'开标日期',sortable:true,dataIndex:'openDate',width:80},
        {header:'备注',sortable:true,dataIndex:'descn',width:100,renderer:function(value){
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
        {name:'code'},
        {name:'productName',mapping:"erp2Product.name"},
        {name:'productNum'},
        {name:'parameter'},
        {name:'startDate'},
        {name:'endDate'},
        {name:'openDate'},
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
    toolbar.add('-','招标管理');

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
        fieldLabel: '标书编号'
    });
    var productStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'../erp2product/pagedQueryForCombo.htm'}),
        reader: new Ext.data.JsonReader({
            root          : "result",
            totalProperty : "totalCount",
            id            : "id"
        },['name'])
    });
    var productName = new Ext.form.ComboBox({
        id:'productNameId',
        name:'productName',
        readOnly:false,
        fieldLabel: '产品名称',
        hiddenName:'productName',
        store: productStore,
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
    var startDate = new Ext.form.DateField({
        id:'startDate',
        name:'startDate',
        format:'Y-m-d',
        fieldLabel:'招标开始时间'
    });
    code.applyTo('code');
    productName.applyTo('productNameId');
    startDate.applyTo('startDate');
    var search = new Ext.Button('search-button', {
        text:'查询',
        handler:function() {
            dataStore.reload();
            productName.clearValue();
        }
    });

    var insertHtml =
        '<div style="width:100%px;margin:auto;" id="insert-box">' +
            '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>' +
            '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">' +
                '<h3 style="text-align:left;">添加标书</h3>' +
                '<div id="insert-form"></div>' +
            '</div></div></div>' +
            '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>' +
        '</div>' +
        '<div class="x-form-clear"></div>';
    var updateHtml =
        '<div style="width:100%px;margin:auto;" id="update-box">' +
            '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>' +
            '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">' +
                '<h3 style="text-align:left;">修改标书</h3>' +
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
            var tab = tabs.addTab('insert-tab', '添加标书', insertHtml, true);
            var insertForm = Bid.getInsertForm(tabs.removeTab.createDelegate(tabs, ['insert-tab']));
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
                var tab = tabs.addTab('update-tab', '修改标书', updateHtml, true);
                var closeTabDelegate = tabs.removeTab.createDelegate(tabs, ['update-tab']);
                var updateForm = Bid.getUpdateForm(closeTabDelegate, m[0].get('id'));
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
            code        : code.getValue(),
            productName : productName.getRawValue(),
            startDate   : startDate.getValue()
        };
    });

    dataStore.load({
        params:{start:0, limit:paging.pageSize}
    });
});


Bid = {
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
                Bid.form.reset();
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
                        params:{id:Bid.id},
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

        // 初始化地区的省市县信息
        Ext.lib.Ajax.request(
            'GET',
            './loadData.htm?id=' + this.id,
            {success:function(response){
                var entity = Ext.decode(response.responseText);
                var productId = entity[0].erp2Product.id;
                var productName = entity[0].erp2Product.name;

                form.findField('product').setValue(productId);
                form.findField('productId2').setRawValue(productName);
            },failure:function(){}}
        );

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
                {name:'productNum'},
                {name:'parameter'},
                {name:'startDate'},
                {name:'endDate'},
                {name:'openDate'},
                {name:'fax'},
                {name:'descn'}
            ])
        });

        form.fieldset(
            {legend:'标书信息', hideLabels:false}
        );

        form.column(
            {width:300},
            new Ext.form.TextField({
                fieldLabel:'标书编号',
                name:'code',
                width:200,
                allowBlank:false
            }),

            new Ext.form.TextField({
                fieldLabel: '技术参数',
                name: 'parameter',
                width:200,
                allowBlank:false
            }),

            new Ext.form.DateField({
                fieldLabel: '投标开始时间',
                name: 'startDate',
                format:'Y-m-d',
                width:200,
                allowBlank:false
            }),

            new Ext.form.DateField({
                fieldLabel: '投标截止时间',
                name: 'endDate',
                format:'Y-m-d',
                width:200,
                allowBlank:false
            }),

            new Ext.form.DateField({
                fieldLabel: '开标时间',
                name: 'openDate',
                format:'Y-m-d',
                width:200,
                allowBlank:false
            })
        );

        form.column(
            {width:300},
            new Ext.form.ComboBox({
                id:'productId2',
                name:'product',
                readOnly:true,
                fieldLabel:'产品名称',
                hiddenName:'product',
                store: new Ext.data.Store({
                    proxy: new Ext.data.HttpProxy({url:'../erp2product/pagedQueryForCombo.htm'}),
                    reader: new Ext.data.JsonReader({
                        root          : "result",
                        totalProperty : "totalCount",
                        id            : "id"
                    },['id','name'])
                }),
                valueField:'id',
                displayField:'name',
                typeAhead: true,
                mode: 'remote',
                triggerAction: 'all',
                emptyText:'请选择',
                selectOnFocus:true,
                width:200,
                allowBlank:false,
                pageSize:10
            }),

            new Ext.form.NumberField({
                fieldLabel: '产品数量',
                name: 'productNum',
                width:200,
                allowBlank:false
            }),

            new Ext.form.TextArea({
                fieldLabel:'备注',
                name:'descn',
                width:200,
                height:80
            })
        );
        //
        this.form = form;
    }
};
