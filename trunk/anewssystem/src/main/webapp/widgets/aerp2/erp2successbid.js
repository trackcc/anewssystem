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
        {header:'公示结果日期',sortable:true,dataIndex:'publishDate',width:80},
        {header:'标书编号',sortable:true,dataIndex:'bidCode',width:90},
        {header:'中标单位',sortable:true,dataIndex:'supplierName',width:80},
        {header:'招标内容',sortable:true,dataIndex:'bidContent',width:80},
        {header:'中标成交金额',sortable:true,dataIndex:'price',width:150},
        {header:'中标、成交项目主要内容',sortable:true,dataIndex:'descn',width:200,renderer:function(value){
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
        {name:'publishDate'},
        {name:'bidCode',mapping:"erp2InviteBid.erp2Bid.code"},
        {name:'supplierName',mapping:'erp2InviteBid.erp2Supplier.name'},
        {name:'bidContent'},
        {name:'price'},
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
    toolbar.add('-','中标结果');

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
    var bidContent = new Ext.form.TextField({
        id:'bidContent',
        name:'bidContent',
        fieldLabel:'招标内容'
    });
    var publishDate = new Ext.form.DateField({
        id:'publishDate',
        name:'publishDate',
        format:'Y-m-d',
        fieldLabel:'公示结果日期'
    });
    bidCode.applyTo('bidCodeId');
    bidContent.applyTo('bidContent');
    publishDate.applyTo('publishDate');
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
                '<h3 style="text-align:left;">添加中标结果</h3>' +
                '<div id="insert-form"></div>' +
            '</div></div></div>' +
            '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>' +
        '</div>' +
        '<div class="x-form-clear"></div>';
    var updateHtml =
        '<div style="width:100%px;margin:auto;" id="update-box">' +
            '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>' +
            '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">' +
                '<h3 style="text-align:left;">修改中标结果</h3>' +
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
            var tab = tabs.addTab('insert-tab', '填写中标结果', insertHtml, true);
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
                var tab = tabs.addTab('update-tab', '修改中标结果', updateHtml, true);
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
            bidCode     : bidCode.getValue(),
            bidContent  : bidContent.getValue(),
            publishDate : publishDate.getValue()
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
    getUpdateForm : function(closeTagHandler, id) {
        this.createForm('./insert.htm', 'update-box');
        var form = this.form;
        this.id = id;

        form.addButton({
            text:'提交',
            handler:function(){
                if (form.isValid()) {
                    form.submit({
                        params:{id:EditForm.id},
                        success:function(form, action){
                            Ext.MessageBox.confirm('提示', '修改成功，是否返回' , function(btn){
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
                EditForm.form.reset();
            }
        });
        form.addButton({
            text:'取消',
            handler:closeTagHandler
        });
        form.render("update-form");
        form.load({url:'./loadData.htm?id=' + id});

        // 初始化级联的信息
        Ext.lib.Ajax.request(
            'GET',
            './loadData.htm?id=' + this.id,
            {success:function(response){
                var entity = Ext.decode(response.responseText);
                var inviteBid = entity[0].erp2InviteBid;
                var bidId = inviteBid.erp2Bid.id;
                var bidCode = inviteBid.erp2Bid.code;
                var inviteBidId = inviteBid.id;
                var supplierName = inviteBid.erp2Supplier.name;

                form.findField('bidCode').setValue(bidId);
                form.findField('bidCodeId2').setRawValue(bidCode);
                form.findField('supplierName').setValue(inviteBidId);
                form.findField('supplierNameId2').setRawValue(supplierName);
            },failure:function(){}}
        );

        return form;
    },
    createForm : function(url, waitMsgTarget) {
        var form = new Ext.form.Form({
            labelAlign:'right',
            labelWidth:150,
            url:url,
            method: 'POST',
            waitMsgTarget:waitMsgTarget,
            reader : new Ext.data.JsonReader({}, [
                {name:'publishDate'},
                {name:'bidContent'},
                {name:'price'},
                {name:'descn'}
            ])
        });

        var publishDate = new Ext.form.DateField({
                id:'publishDate',
                name:'publishDate',
                readOnly:true,
                fieldLabel:'公示结果日期',
                format:'Y-m-d',
                width:400
            });

        var bidCode = new Ext.form.ComboBox({
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
            });
        var supplierDataStore = new Ext.data.Store({
                    proxy: new Ext.data.HttpProxy({url:'../erp2invitebid/pagedQueryByBidId.htm'}),
                    reader: new Ext.data.JsonReader({
                        root          : "result",
                        totalProperty : "totalCount",
                        id            : "id"
                    },['id',{name:'name',mapping:'erp2Supplier.name'}])
                });
        var supplierName = new Ext.form.ComboBox({
                id:'supplierNameId2',
                name:'supplierName',
                readOnly:true,
                fieldLabel:'中标单位名称',
                hiddenName:'supplierName',
                store: supplierDataStore,
                valueField:'id',
                displayField:'name',
                typeAhead:true,
                mode:'local',
                triggerAction:'all',
                emptyText:'请选择',
                selectOnFocus:true,
                width:400,
                allowBlank:false,
                pageSize:10
            });
        bidCode.on('select', function() {
            bidId = bidCode.getValue();
            supplierName.clearValue();
            supplierDataStore.load({
                params:{bidCode:bidId}
            });
        });

        var bidContent = new Ext.form.TextField({
                fieldLabel:'招标内容',
                name:'bidContent',
                width:400,
                allowBlank:false
            });

        var price = new Ext.form.NumberField({
                fieldLabel:'中标成交金额（元）',
                name:'price',
                width:400
            });

        var descn = new Ext.form.TextArea({
                fieldLabel:'中标、成交项目主要内容',
                name:'descn',
                width:400,
                height:100
            });

        form.fieldset(
            {legend:'中标结果', hideLabels:false},
            publishDate,
            bidCode,
            supplierName,
            bidContent,
            price,
            descn
        );
        //
        this.form = form;
    }
};
