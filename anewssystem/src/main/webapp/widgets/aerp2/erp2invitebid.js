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
    bidDate.applyTo('bidDate');
    var search = new Ext.Button('search-button', {
        text:'查询',
        handler:function() {
            dataStore.reload();
            bidCode.clearValue();
        }
    });

    var insertHtml =
        '<div style="width:100%px;margin:auto;" id="insert-box">' +
            '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>' +
            '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">' +
                '<h3 style="text-align:left;">欢迎您投标</h3>' +
                '<div id="insert-form"></div>' +
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
            var tab = tabs.addTab('insert-tab', '填写投标书', insertHtml, true);
            var insertForm = EditForm.getInsertForm(tabs.removeTab.createDelegate(tabs, ['insert-tab']));
            tabs.activate('insert-tab');
        }
    });

    dataStore.on('beforeload', function() {
        dataStore.baseParams = {
            bidCode : bidCode.getValue(),
            bidDate : bidDate.getValue()
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
                            Ext.MessageBox.confirm('提示', '添加成功，是否继续填写其他投标单' , function(btn){
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
    createForm : function(url, waitMsgTarget) {
        var form = new Ext.form.Form({
            labelAlign:'right',
            labelWidth:150,
            url:url,
            method: 'POST',
            waitMsgTarget:waitMsgTarget
        });

        form.fieldset(
            {legend:'标书单', hideLabels:false},
            new Ext.form.ComboBox({
                id:'supplierNameId2',
                name:'supplierName',
                readOnly:true,
                fieldLabel:'投标单位名称',
                hiddenName:'supplierName',
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
            new Ext.form.ComboBox({
                id:'bidCodeId2',
                name:'bidCode',
                readOnly:true,
                fieldLabel:'所投标书编号',
                hiddenName:'bidCode',
                store: new Ext.data.Store({
                    proxy: new Ext.data.HttpProxy({url:'../erp2bid/pagedQueryForCombo.htm'}),
                    reader: new Ext.data.JsonReader({
                        root          : "result",
                        totalProperty : "totalCount",
                        id            : "id"
                    },['id','code'])
                }),
                valueField:'id',
                displayField:'code',
                typeAhead:true,
                mode:'remote',
                triggerAction:'all',
                emptyText:'请选择',
                selectOnFocus:true,
                width:400,
                allowBlank:false,
                pageSize:10
            }),

            new Ext.form.NumberField({
                fieldLabel:'进标价格',
                name:'price',
                width:400,
                allowBlank:false
            }),

            new Ext.form.TextField({
                fieldLabel:'投标单位地址',
                name:'address',
                width:400,
                allowBlank:false
            }),

            new Ext.form.TextField({
                fieldLabel:'联系电话',
                name:'tel',
                width:400,
                allowBlank:false
            }),

            new Ext.form.DateField({
                fieldLabel:'投标日期',
                name:'bidDate',
                width:400,
                format:'Y-m-d',
                readOnly:true,
                allowBlank:false
            }),

            new Ext.form.TextField({
                fieldLabel:'电子邮箱',
                name:'email',
                width:400,
                allowBlank:false
            }),
            new Ext.form.TextArea({
                fieldLabel:'详细说明',
                name:'descn',
                width:400,
                height:100
            })
        );
        //
        this.form = form;
    }
};
