/*
 * Ext JS Library 1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author Lingo
 * @since 2007-10-14
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
            title      : '审批新闻',
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

    var renderStatus = function(value) {
        if (value == 0) {
            return "已发布";
        } else if (value == 1) {
            return "<span style='color:red'>待审</span>";
        } else if (value == 5) {
            return "<span style='color:green;font-weight:bold;'>推荐</span>";
        } else if (value == 6) {
            return "<span style='color:gray;font-style:italic;'>隐藏</span>";
        }
    };

    // 默认需要id, name, theSort, parent, children
    // 其他随意定制
    var metaData = [
        {id:'id',         qtip:"ID",       vType:"integer",   allowBlank:true, defValue:-1, w:40},
        {id:'category',   qtip:"分类",     vType:"treeField", allowBlank:false, mapping:"newsCategory.name", url:"../newscategory/getChildren.htm"},
        {id:'name',       qtip:"标题",     vType:"chn",       allowBlank:true},
        {id:'subtitle',   qtip:'副标题',   vType:'chn',       allowBlank:true},
        {id:'link',       qtip:'跳转链接', vType:'url',       allowBlank:true},
        {id:'editor',     qtip:'编辑',     vType:'chn',       allowBlank:true},
        {id:'updateDate', qtip:'发布日期', vType:'date'},
        {id:'status',     qtip:'状态',     vType:'integer',   allowBlank:true, defValue:0, renderer:renderStatus},
        {id:'source',     qtip:'来源',     vType:'chn',       allowBlank:true, showInGrid:false},
        {id:'summary',    qtip:'简介',     vType:'textArea',  allowBlank:true, showInGrid:false},
        {id:'content',    qtip:'内容',     vType:'editor',    allowBlank:true, showInGrid:false}
    ];

    // 创建表格
    var lightGrid = new Ext.lingo.JsonGrid("lightgrid", {
        metaData      : metaData,
        dialogContent : "content",
        urlPagedQuery : "pagedQueryManage.htm"
    });

    // 渲染表格
    lightGrid.render();

    // 取消双击修改
    //lightGrid.grid.un("rowdblclick", lightGrid.edit);
    lightGrid.grid.events["rowdblclick"].clearListeners();
    // 取消右键菜单
    //lightGrid.grid.un("rowcontextmenu", lightGrid.contextmenu);
    lightGrid.grid.events["rowcontextmenu"].clearListeners();

    var changeStatus = function(status, operation) {
        var selections = lightGrid.grid.getSelections();
        if (selections.length == 0) {
            Ext.MessageBox.alert("提示", "请选择希望" + operation + "的记录！");
            return;
        } else {
            Ext.Msg.confirm("提示", "是否确定" + operation + "？", function(btn, text) {
                if (btn == 'yes') {
                    var selections = lightGrid.grid.getSelections();
                    var ids =new Array();
                    for(var i = 0, len = selections.length; i < len; i++){
                        selections[i].get("id");
                        ids[i] = selections[i].get("id");
                        lightGrid.dataStore.remove(selections[i]);
                    }
                    Ext.Ajax.request({
                        url     : 'changeStatus.htm?ids=' + ids + "&status=" + status,
                        success : function() {
                            Ext.MessageBox.alert(' 提示', operation + '成功！');
                            lightGrid.dataStore.reload();
                        }.createDelegate(this),
                        failure : function(){Ext.MessageBox.alert('提示', operation + '失败！');}
                    });
                }
            });
        }
        lightGrid.grid.selModel.Set.clear();
    };

    var hideButton = new Ext.Toolbar.Button({
        icon    : "../widgets/lingo/list-items.gif",
        id      : 'hideButton',
        text    : '隐藏',
        cls     : 'add',
        tooltip : '隐藏',
        handler : changeStatus.createDelegate(lightGrid,[6, "隐藏"])
    });
    var recommendButton = new Ext.Toolbar.Button({
        icon    : "../widgets/lingo/list-items.gif",
        id      : 'recommendButton',
        text    : '推荐',
        cls     : 'add',
        tooltip : '推荐',
        handler : changeStatus.createDelegate(lightGrid,[5, "推荐"])
    });
    var permissionButton = new Ext.Toolbar.Button({
        icon    : "../widgets/lingo/list-items.gif",
        id      : 'permissionButton',
        text    : '审批',
        cls     : 'add',
        tooltip : '审批',
        hidden  : false,
        handler : changeStatus.createDelegate(lightGrid,[0, "审批"])
    });
    var dismissButton = new Ext.Toolbar.Button({
        icon    : "../widgets/lingo/list-items.gif",
        id      : 'dismissButton',
        text    : '驳回',
        cls     : 'add',
        tooltip : '驳回',
        hidden  : false,
        handler : changeStatus.createDelegate(lightGrid,[2, "驳回"])
    });
    var cancelButton = new Ext.Toolbar.Button({
        icon    : "../widgets/lingo/list-items.gif",
        id      : 'submitButton',
        text    : '恢复发布状态',
        cls     : 'add',
        tooltip : '恢复发布状态',
        hidden  : false,
        handler : changeStatus.createDelegate(lightGrid,[0, "恢复发布状态"])
    });
    lightGrid.toolbar.insertButton(3, hideButton);
    lightGrid.toolbar.insertButton(4, recommendButton);
    lightGrid.toolbar.insertButton(5, permissionButton);
    lightGrid.toolbar.insertButton(6, dismissButton);
    lightGrid.toolbar.insertButton(7, cancelButton);

    Ext.get("add").setStyle("display", "none");
    Ext.get("edit").setStyle("display", "none");
    Ext.get("del").setStyle("display", "none");

});
