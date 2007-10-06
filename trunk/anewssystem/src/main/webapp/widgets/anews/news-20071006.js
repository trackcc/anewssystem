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
        north: {
            autoScroll     : true,
            titlebar       : true,
            tabPosition    : 'top',
            closeOnTab     : false,
            alwaysShowTabs : true,
            resizeTabs     : true,
            fillToFrame    : true
        }, center: {
            autoScroll     : true,
            titlebar       : false,
            closeOnTab     : false,
            alwaysShowTabs : false,
            resizeTabs     : true,
            fillToFrame    : true
        }
    });

    // 设置布局
    layout.beginUpdate();
        layout.add('north', new Ext.ContentPanel('tab1', {
            title      : '已发布',
            toolbar    : null,
            closable   : false,
            fitToFrame : true
        }));
        layout.add('north', new Ext.ContentPanel('tab2', {
            title      : '待审批',
            toolbar    : null,
            closable   : false,
            fitToFrame : true
        }));
        layout.add('north', new Ext.ContentPanel('tab3', {
            title      : '被驳回',
            toolbar    : null,
            closable   : false,
            fitToFrame : true
        }));
        layout.add('north', new Ext.ContentPanel('tab4', {
            title      : '草稿箱',
            toolbar    : null,
            closable   : false,
            fitToFrame : true
        }));
        layout.add('north', new Ext.ContentPanel('tab5', {
            title      : '垃圾箱',
            toolbar    : null,
            closable   : false,
            fitToFrame : true
        }));
        layout.add('north', new Ext.ContentPanel('tab6', {
            title      : '推荐',
            toolbar    : null,
            closable   : false,
            fitToFrame : true
        }));
        layout.add('north', new Ext.ContentPanel('tab7', {
            title      : '隐藏',
            toolbar    : null,
            closable   : false,
            fitToFrame : true
        }));
        layout.add('center', new Ext.ContentPanel('gridPanel', {
            toolbar    : null,
            closable   : false,
            fitToFrame : true
        }));
        layout.restoreState();
    layout.endUpdate();

    var tabs = layout.getRegion('north').getTabs();
    layout.getRegion("north").showPanel("tab1");
    layout.getRegion('north').titleTextEl.innerHTML = "<b>新闻管理 - (" + tabs.getTab(0).getText() + ")</b>";
    var currentStatus = 0;

    // JsonGrid
    var metaData = [
        {id:'id',         qtip:"ID",       vType:"integer",   allowBlank:true, defValue:-1},
        {id:'category',   qtip:"分类",     vType:"treeField", allowBlank:false, mapping:"newsCategory.name", url:"../newscategory/getChildren.htm"},
        {id:'name',      qtip:"标题",     vType:"chn",       allowBlank:true},
        {id:'subtitle',   qtip:'副标题',   vType:'chn',       allowBlank:true},
        {id:'link',       qtip:'跳转链接', vType:'url',       allowBlank:true},
        {id:'editor',     qtip:'编辑',     vType:'chn',       allowBlank:true},
        {id:'updateDate', qtip:'发布日期', vType:'date'},
        {id:'source',     qtip:'来源',     vType:'chn',       allowBlank:true, showInGrid:false},
        {id:'summary',    qtip:'简介',     vType:'textArea',  allowBlank:true, showInGrid:false},
        {id:'content',    qtip:'内容',     vType:'editor',    allowBlank:true, showInGrid:false}
    ];

    // 创建表格
    var lightGrid = new Ext.lingo.JsonGrid("lightgrid", {
        metaData      : metaData,
        genHeader     : true,
        dialogWidth   : 750,
        dialogHeight  : 400,
        dialogContent : "news-content"
    });

    lightGrid.applyElements = function() {
        if (this.columns == null || this.headers == null) {
            this.headers = new Array();
            for (var i = 0; i < this.metaData.length; i++) {
                this.headers[this.headers.length] = this.metaData[i].id;
            }
            this.columns = Ext.lingo.FormUtils.createAll(this.metaData);
            this.columns.tags = Ext.lingo.FormUtils.createTextField({
                id:'tags'
                ,name:'tags'
                ,allowBlank:true
            });
            this.columns.page = Ext.lingo.FormUtils.createComboBox({
                id:'page'
                ,name:'page'
                ,allowBlank:false
            });
            this.columns.pageSize = Ext.lingo.FormUtils.createTextField({
                id:'pagesize'
                ,name:'pagesize'
                ,allowBlank:false
            });
            this.columns.quick = Ext.lingo.FormUtils.createCheckBox({
                id:'quick'
                ,name:'quick'
                ,allowBlank:false
            });

        }
    }.createDelegate(lightGrid);
    lightGrid.edit = function() {
        if (!this.dialog) {
            this.createDialog();
        }

        var selections = this.grid.getSelections();
        if (selections.length == 0) {
            Ext.MessageBox.alert("提示", "请选择希望修改的记录！");
            return;
        } else if (selections.length != 1) {
            Ext.MessageBox.alert("提示", "只允许选择单行记录进行修改！");
            return;
        }

        this.menuData = new Ext.data.Store({
            proxy      : new Ext.data.HttpProxy({url:this.urlLoadData + "?id=" + selections[0].id}),
            reader     : new Ext.data.JsonReader({},['id','newsCategory','name','subtitle','link','author','updateDate','source','summary','content','newsTags']),
            remoteSort : false
        });

        this.menuData.on('load', function() {

            for (var i = 0; i < this.metaData.length; i++) {
                var meta = this.metaData[i];

                var id = meta.id;
                var value = this.menuData.getAt(0).data[id];
                if (meta.mapping) {
                    try {
                        value = eval("this.menuData.getAt(0).data." + meta.mapping);
                    } catch (e) {
                    }
                }

                if (meta.vType == "radio") {
                    for (var j = 0; j < meta.values.length; j++) {
                        var theId = meta.values[j].id;
                        var theName = meta.values[j].name;

                        if (value == theId) {
                            this.columns[id + theId].checked = true;
                            this.columns[id + theId].el.dom.checked = true;
                        } else {
                            this.columns[id + theId].checked = false;
                            this.columns[id + theId].el.dom.checked = false;
                        }
                    }
                } else if (meta.vType == "date") {
                    if (value == null ) {
                        this.columns[id].setValue(new Date());
                    } else {
                        this.columns[id].setValue(value);
                    }
                } else {
                    this.columns[id].setValue(value);
                }

            }

            // 设置关键字
            var value = "";
            for (var i = 0; i < this.menuData.getAt(0).data.newsTags.length; i++) {
                this.columns.tags.setValue(this.menuData.getAt(0).data.newsTags.join(","));
                value += this.menuData.getAt(0).data.newsTags[i].name + ","
            }
            this.columns.tags.setValue(value.substring(0, value.length - 1));

            // 设置分类
            var newsCategory = this.menuData.getAt(0).data.newsCategory;
            this.columns.category.setValue(newsCategory.name);
            this.columns.category.selectedId = newsCategory.id;

            this.dialog.show(Ext.get("edit"));
        }.createDelegate(this));
        this.menuData.reload();
    }.createDelegate(lightGrid);

    // 渲染表格
    lightGrid.render();
    // 读取数据
    lightGrid.dataStore.on('beforeload', function() {
        lightGrid.dataStore.baseParams = {
            filterValue : this.filter.getValue(),
            filterTxt   : this.filterTxt,
            status      : currentStatus
        };
    }.createDelegate(lightGrid));
    lightGrid.dataStore.reload();

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
        hidden  : true,
        handler : changeStatus.createDelegate(lightGrid,[0, "审批"])
    });
    var dismissButton = new Ext.Toolbar.Button({
        icon    : "../widgets/lingo/list-items.gif",
        id      : 'dismissButton',
        text    : '驳回',
        cls     : 'add',
        tooltip : '驳回',
        hidden  : true,
        handler : changeStatus.createDelegate(lightGrid,[2, "驳回"])
    });
    var draftButton = new Ext.Toolbar.Button({
        icon    : "../widgets/lingo/list-items.gif",
        id      : 'draftButton',
        text    : '放入草稿箱',
        cls     : 'add',
        tooltip : '放入草稿箱',
        hidden  : true,
        handler : changeStatus.createDelegate(lightGrid,[3, "放入草稿箱"])
    });
    var rubbishButton = new Ext.Toolbar.Button({
        icon    : "../widgets/lingo/list-items.gif",
        id      : 'rubbishButton',
        text    : '放入垃圾箱',
        cls     : 'add',
        tooltip : '放入垃圾箱',
        hidden  : true,
        handler : changeStatus.createDelegate(lightGrid,[4, "放入垃圾箱"])
    });
    var cancelButton = new Ext.Toolbar.Button({
        icon    : "../widgets/lingo/list-items.gif",
        id      : 'submitButton',
        text    : '取消',
        cls     : 'add',
        tooltip : '取消',
        hidden  : true,
        handler : changeStatus.createDelegate(lightGrid,[0, "取消"])
    });
    lightGrid.toolbar.insertButton(3, hideButton);
    lightGrid.toolbar.insertButton(4, recommendButton);
    lightGrid.toolbar.insertButton(5, permissionButton);
    lightGrid.toolbar.insertButton(6, dismissButton);
    lightGrid.toolbar.insertButton(7, draftButton);
    lightGrid.toolbar.insertButton(8, rubbishButton);
    lightGrid.toolbar.insertButton(9, cancelButton);

    // Tabs
    tabs.getTab(0).on("activate", function(e) {
        layout.getRegion('north').titleTextEl.innerHTML = "<b>新闻管理 - (" + tabs.getTab(0).getText() + ")</b>";
        currentStatus = 0;
        lightGrid.dataStore.reload();

        hideButton.show();
        recommendButton.show();
        permissionButton.hide();
        dismissButton.hide();
        draftButton.hide();
        rubbishButton.hide();
        cancelButton.hide();
    });
    tabs.getTab(1).on("activate", function(e) {
        layout.getRegion('north').titleTextEl.innerHTML = "<b>新闻管理 - (" + tabs.getTab(1).getText() + ")</b>";
        currentStatus = 1;
        lightGrid.dataStore.reload();

        hideButton.hide();
        recommendButton.hide();
        permissionButton.show();
        dismissButton.show();
        draftButton.hide();
        rubbishButton.hide();
        cancelButton.hide();
    });
    tabs.getTab(2).on("activate", function(e) {
        layout.getRegion('north').titleTextEl.innerHTML = "<b>新闻管理 - (" + tabs.getTab(2).getText() + ")</b>";
        currentStatus = 2;
        lightGrid.dataStore.reload();

        hideButton.hide();
        recommendButton.hide();
        permissionButton.hide();
        dismissButton.hide();
        draftButton.show();
        rubbishButton.show();
        cancelButton.show();
    });
    tabs.getTab(3).on("activate", function(e) {
        layout.getRegion('north').titleTextEl.innerHTML = "<b>新闻管理 - (" + tabs.getTab(3).getText() + ")</b>";
        currentStatus = 3;
        lightGrid.dataStore.reload();

        hideButton.hide();
        recommendButton.hide();
        permissionButton.hide();
        dismissButton.hide();
        draftButton.hide();
        rubbishButton.show();
        cancelButton.show();
    });
    tabs.getTab(4).on("activate", function(e) {
        layout.getRegion('north').titleTextEl.innerHTML = "<b>新闻管理 - (" + tabs.getTab(4).getText() + ")</b>";
        currentStatus = 4;
        lightGrid.dataStore.reload();

        hideButton.hide();
        recommendButton.hide();
        permissionButton.hide();
        dismissButton.hide();
        draftButton.show();
        rubbishButton.hide();
        cancelButton.hide();
    });
    tabs.getTab(5).on("activate", function(e) {
        layout.getRegion('north').titleTextEl.innerHTML = "<b>新闻管理 - (" + tabs.getTab(5).getText() + ")</b>";
        currentStatus = 5;
        lightGrid.dataStore.reload();

        hideButton.hide();
        recommendButton.hide();
        permissionButton.hide();
        dismissButton.hide();
        draftButton.hide();
        rubbishButton.hide();
        cancelButton.show();
    });
    tabs.getTab(6).on("activate", function(e) {
        layout.getRegion('north').titleTextEl.innerHTML = "<b>新闻管理 - (" + tabs.getTab(6).getText() + ")</b>";
        currentStatus = 6;
        lightGrid.dataStore.reload();

        hideButton.hide();
        recommendButton.hide();
        permissionButton.hide();
        dismissButton.hide();
        draftButton.hide();
        rubbishButton.hide();
        cancelButton.show();
    });
});
