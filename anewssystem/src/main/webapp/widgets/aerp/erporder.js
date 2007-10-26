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
            title      : '添加订单',
            toolbar    : null,
            closable   : false,
            fitToFrame : true
        }));
        layout.add('center', new Ext.ContentPanel('tab2', {
            title: "管理订单",
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
        {id:'code',      qtip:"订单编号", vType:"alphanum", allowBlank:false,w:200},
        {id:'customer',  qtip:"客户",     vType:"chn",      allowBlank:false,w:100},
        {id:'orderDate', qtip:"订货日期", vType:"date",     allowBlank:false,w:100},
        {id:'amount',    qtip:"总金额",   vType:"float",    allowBlank:false,w:80},
        {id:'handMan',   qtip:"经手人",   vType:"chn",      allowBlank:false,w:80},
        {id:'payment',   qtip:"付款情况", vType:"comboBox", allowBlank:false,w:80},
        {id:'delivery',  qtip:"运货情况", vType:"comboBox", allowBlank:false,w:80},
        {id:'status',    qtip:"状态",     vType:"comboBox", allowBlank:false,w:100,showInGrid:false},
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

    var manageButton = new Ext.Toolbar.Button({
        icon    : "../widgets/lingo/list-items.gif",
        id      : 'manageButton',
        text    : '管理',
        cls     : 'add',
        tooltip : '管理',
        handler : lightGrid.edit.createDelegate(lightGrid)
    });
    var printButton = new Ext.Toolbar.Button({
        icon    : "../widgets/lingo/list-items.gif",
        id      : 'printButton',
        text    : '打印',
        cls     : 'add',
        tooltip : '打印',
        handler : function() {
			alert("还没做完呢，2007-10-26 00:31");
		}
    });
    var print2Button = new Ext.Toolbar.Button({
        icon    : "../widgets/lingo/list-items.gif",
        id      : 'print2Button',
        text    : '打印送货单',
        cls     : 'add',
        tooltip : '打印送货单',
        handler : function() {
			alert("还没做完呢，2007-10-26 00:31");
		}
    });
    var print3Button = new Ext.Toolbar.Button({
        icon    : "../widgets/lingo/list-items.gif",
        id      : 'print3Button',
        text    : '打印送货汇总',
        cls     : 'add',
        tooltip : '打印送货汇总',
        handler : function() {
			alert("还没做完呢，2007-10-26 00:31");
		}
    });

    lightGrid.toolbar.insertButton(3, manageButton);
    lightGrid.toolbar.insertButton(4, printButton);
    lightGrid.toolbar.insertButton(5, print2Button);
    lightGrid.toolbar.insertButton(6, print3Button);

    Ext.get("add").setStyle("display", "none");
    Ext.get("edit").setStyle("display", "none");
    Ext.get("del").setStyle("display", "none");

    // ====================================================
    // 添加订单的部分
    var orderDate = new Ext.form.DateField({
        id:'orderDateIn'
        ,name:'orderDate'
        ,format:'Y-m-d'
        ,readOnly:true
    });
    orderDate.setValue(new Date());
    var customer = new Ext.form.TextField({
        id:'customerIn'
        ,name:'customer'
    });
    var linkMan = new Ext.form.TextField({
        id:'linkMan'
        ,name:'linkMan'
    });
    var tel = new Ext.form.TextField({
        id:'tel'
        ,name:'tel'
    });
    orderDate.applyTo('orderDateIn');
    customer.applyTo('customerIn');
    linkMan.applyTo('linkMan');
    tel.applyTo('tel');

    // ====================================================
    // 添加订单条目
    var cm = new Ext.grid.ColumnModel([{
        header: "产品名",
        dataIndex: "product",
        sortable:false,
        width: 100,
        editor: new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank: false}))
    }, {
        header: "类别",
        dataIndex: "type",
        sortable:false,
        width: 100,
        editor: new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank: false}))
    }, {
        header: "单位",
        dataIndex: "unit",
        sortable:false,
        width: 50,
        editor: new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank: false}))
    }, {
        header: "长",
        dataIndex: "height",
        sortable:false,
        width: 50,
        editor: new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank: false}))
    }, {
        header: "宽",
        dataIndex: "width",
        sortable:false,
        width: 50,
        editor: new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank: false}))
    }, {
        header: "面积（尺寸）",
        dataIndex: "area",
        sortable:false,
        width: 70,
        editor: new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank: false}))
    }, {
        header: "单价（元）",
        dataIndex: "price",
        sortable:false,
        width: 60,
        editor: new Ext.grid.GridEditor(new Ext.form.NumberField({allowBlank: false}))
    }, {
        header: "金额（元）",
        dataIndex: "amount",
        sortable:false,
        width: 60,
        editor: new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank: false}))
    }, {
        header: "数量",
        dataIndex: "num",
        sortable:false,
        width: 50,
        editor: new Ext.grid.GridEditor(new Ext.form.NumberField({allowBlank: false}))
    }, {
        header: "单位类型",
        dataIndex: "unitType",
        sortable:false,
        width: 80,
        editor: new Ext.grid.GridEditor(new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               transform:'unitSelect',
               lazyRender:true
            }))
    }, {
        header: "状态",
        dataIndex: "status",
        sortable:false,
        width: 80,
        editor: new Ext.grid.GridEditor(new Ext.form.ComboBox({
               typeAhead: true,
               triggerAction: 'all',
               transform:'statusSelect',
               lazyRender:true
            }))
    }]);
    cm.defaultSortable = true;//排序

    var orderInfoRecord = Ext.data.Record.create([
        {name: "product", type: "string"},
        {name: "type", type: "string"},
        {name: "unit", type: "string"},
        {name: "height", type: "string"},
        {name: "width", type: "string"},
        {name: "area", type: "string"},
        {name: "price", type: "string"},
        {name: "amount", type: "string"},
        {name: "price", type: "string"},
        {name: "status", type: "string"}
    ]);

    var ds = new Ext.data.Store({
        proxy: new Ext.data.DataProxy(),
        reader: new Ext.data.DataReader({
            id:0
        }, orderInfoRecord),
        remoteSort: false
    });
    var grid = new Ext.grid.EditorGrid("orderInfoGrid", {
        ds: ds,
        cm: cm,
        enableDragDrop : false,
        clicksToEdit:1,
        selModel:new Ext.grid.RowSelectionModel()
    });
    grid.render();

    var gridHead = grid.getView().getHeaderPanel(true);

    var header = new Ext.Toolbar(gridHead, [{
        text: '添加行',
        handler : function(){
            var orderInfo = new orderInfoRecord({
                id: Ext.id(),
                product: '',
                type:'',
                unit:'',
                height:'',
                width:'',
                area:'',
                price:'',
                amount:'',
                status:'',
                num:'',
                unitType:''
            });
            ds.insert(0, orderInfo);
        }
    },{
        text: '删除行',
        handler : function(){
            grid.stopEdit();
            var selections = grid.getSelections();
            if (selections.length == 0) {
                Ext.MessageBox.alert("提示", "请选择希望删除的记录！");
                return;
            } else {
                Ext.Msg.confirm("提示", "是否确定删除？", function(btn, text) {
                    if (btn == 'yes') {
                        var selections = grid.getSelections();
                        var ids = new Array();
                        for(var i = 0, len = selections.length; i < len; i++){
                            ds.remove(selections[i]);//从表格中删除
                        }
                    }
                });
            }
        }
    }]);

    grid.on("click", function() {
        var selections = grid.getSelections();
        var amount = 0;
        for(var i = 0, len = selections.length; i < len; i++){
            var record = selections[i];
            var num = record.data.num;
            var price = record.data.price;
            record.data.amount = num * price;
            amount += record.data.amount;
        }
        document.getElementById("amount1").innerHTML = amount;
        document.getElementById("amount2").innerHTML = convertCurrency(amount);
    });

    var resetFields = function() {
        grid.stopEditing();
        ds.removeAll();
        orderDate.reset();
        customer.reset();
        linkMan.reset();
        tel.reset();
    };

    var submitAndSave = function() {
        grid.stopEditing();
        var data = {};
        data.orderDate = orderDate.getRawValue();
        data.customer = customer.getValue();
        data.linkMan = linkMan.getValue();
        data.tel = tel.getValue();
        data.amount = document.getElementById("amount1").innerHTML;
        data.infos = [];
        for (var i = 0; i < ds.getCount(); i++) {
            var record = ds.getAt(i);
            var item = {};

            item.product = record.data.product;
            item.type = record.data.type;
            item.unit = record.data.unit;
            item.height = record.data.height;
            item.width = record.data.width;
            item.area = record.data.area;
            item.price = record.data.price;
            item.amount = record.data.amount;
            item.status = record.data.status;
            item.num = record.data.num;
            item.unitType = record.data.unitType;
            data.infos.push(item);
        }

        Ext.lib.Ajax.request(
            'POST',
            'insert.htm',
            {success:function() {
                Ext.MessageBox.alert(' 提示', '操作成功');
                resetFields();
            },failure:function(){Ext.MessageBox.alert('提示', '操作失败！')}},
            'data=' + encodeURIComponent(Ext.encode(data))
        );
    };

    var save = new Ext.Button('save', {
        text:'保存',
        handler: submitAndSave
    });

    var add = new Ext.Button('addOrderInfo', {
        text:'添加新单据',
        handler:resetFields
    });
    var submit = new Ext.Button('submit', {
        text:'确认单据',
        handler:submitAndSave
    });
    var del = new Ext.Button('delOrderInfo', {
        text:'删除',
        handler:resetFields
    });
    var print = new Ext.Button('print', {
        text:'打印',
        handler:function() {
            grid.stopEditing();
            alert("还没做好呢。2007-10-25 23:29");
        }
    });


});
