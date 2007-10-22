/*
 * Ext JS Library 1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author Lingo
 * @since 2007-09-19
 * http://code.google.com/p/anewssystem/
 */
/**
 * 声明Ext.lingo命名控件
 * TODO: 完全照抄，作用不明
 */
Ext.namespace("Ext.lingo");
/**
 * 拥有CRUD功能的表格.
 *
 * @param container 被渲染的html元素的id，<div id="lightgrid"></div>
 * @param config    需要的配置{}
 */
Ext.lingo.JsonGrid = function(container, config) {
    this.container = Ext.get(container);
    this.id        = container;
    this.config    = config;
    this.metaData  = config.metaData;
    this.genHeader = config.genHeader !== false;
    this.useHistory = config.useHistory !== false;
    this.pageSize   = config.pageSize ? config.pageSize : 15;
    this.dialogWidth  = config.dialogWidth;
    this.dialogHeight = config.dialogHeight;
    this.urlPagedQuery = config.urlPagedQuery ? config.urlPagedQuery : "pagedQuery.htm";
    this.urlLoadData   = config.urlLoadData   ? config.urlLoadData   : "loadData.htm";
    this.urlSave       = config.urlSave       ? config.urlSave       : "save.htm";
    this.urlRemove     = config.urlRemove     ? config.urlRemove     : "remove.htm";

    this.filterTxt = "";

    Ext.lingo.JsonGrid.superclass.constructor.call(this);
}

Ext.extend(Ext.lingo.JsonGrid, Ext.util.Observable, {
    // 初始化
    init : function() {
        // 根据this.headers生成columnModel
        if (!this.columnModel) {
            this.initColumnModel();
        }

        // 生成data record
        if (!this.dataRecord) {
            var recordHeaders = new Array();
            for (var i = 0; i < this.metaData.length; i++) {
                var meta = this.metaData[i];
                var item = {};
                item.name       = meta.id;
                item.type       = "string";
                if (meta.mapping) {
                    item.mapping = meta.mapping;
                }
                item.defaultValue = "";
                //item.dateFormat = "yyyy-MM-dd";
                recordHeaders[recordHeaders.length] = item;
            }
            this.dataRecord = Ext.data.Record.create(recordHeaders);
        }

        // 生成data store
        if (!this.dataStore) {
            this.dataStore = new Ext.lingo.Store({
                proxy  : new Ext.data.HttpProxy({url:this.urlPagedQuery}),
                reader : new Ext.data.JsonReader({
                    root          : "result",
                    totalProperty : "totalCount",
                    id            : "id"
                }, recordHeaders),
                remoteSort : true
            });
            // this.dataStore.setDefaultSort("enterDate", "ASC");
        }

        // 生成表格
        if (!this.grid) {
            this.grid = new Ext.lingo.CheckRowSelectionGrid(this.id, {
                ds   : this.dataStore
                , cm : this.columnModel
                // selModel: new Ext.grid.CellSelectionModel(),
                // selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
                , selModel      : new Ext.lingo.CheckRowSelectionModel({useHistory:this.useHistory})
                , enableColLock : false
                , loadMask      : true
            });
            this.grid.on('rowdblclick', this.edit, this);
        }

        //右键菜单
        this.grid.addListener('rowcontextmenu', this.contextmenu, this);
    }

    // 初始化ColumnModel
    , initColumnModel : function() {
        var columnHeaders = new Array();
        for (var i = 0; i < this.metaData.length; i++) {
            var meta = this.metaData[i];
            if (meta.showInGrid === false) {
                continue;
            }
            var item = {};
            item.header    = meta.qtip;
            item.sortable  = true;
            item.dataIndex = meta.id;
            item.mapping   = meta.mapping;
            item.width     = meta.w ? meta.w : 110;
            item.defaultValue = "";
            // item.hidden = false;
            if (meta.renderer) {
                item.renderer = meta.renderer;
            }
            columnHeaders[columnHeaders.length] = item;
        }
        this.columnModel = new Ext.grid.ColumnModel(columnHeaders);
        this.columnModel.defaultSortable = false;
    }

    // 渲染
    , postRender : function() {
        // 生成头部工具栏
        if (this.genHeader) {
            var gridHeader = this.grid.getView().getHeaderPanel(true);
            this.toolbar = new Ext.Toolbar(gridHeader);
            var checkItems = new Array();
            for (var i = 0; i < this.metaData.length; i++) {
                var meta = this.metaData[i];
                if (meta.showInGrid === false) {
                    continue;
                }
                var item = new Ext.menu.CheckItem({
                    text         : meta.qtip,
                    value        : meta.id,
                    checked      : true,
                    group        : "filter",
                    checkHandler : this.onItemCheck.createDelegate(this)
                });
                checkItems[checkItems.length] = item;
            }

            this.filterButton = new Ext.Toolbar.MenuButton({
                icon     : "../widgets/lingo/list-items.gif",
                cls      : "x-btn-text-icon",
                text     : "请选择",
                tooltip  : "选择搜索的字段",
                menu     : checkItems,
                minWidth : 105
            });
            this.toolbar.add({
                icon     : "../widgets/lingo/list-items.gif",
                id      : 'add',
                text    : '新增',
                cls     : 'add',
                tooltip : '新增',
                handler : this.add.createDelegate(this)
            }, {
                icon     : "../widgets/lingo/list-items.gif",
                id      : 'edit',
                text    : '修改',
                cls     : 'edit',
                tooltip : '修改',
                handler : this.edit.createDelegate(this)
            }, {
                icon     : "../widgets/lingo/list-items.gif",
                id      : 'del',
                text    : '删除',
                cls     : 'del',
                tooltip : '删除',
                handler : this.del.createDelegate(this)
            }, '->', this.filterButton);

            // 输入框
            this.filter = Ext.get(this.toolbar.addDom({
                 tag   : 'input',
                 type  : 'text',
                 size  : '20',
                 value : '',
                 style : 'background: #F0F0F9;'
            }).el);

            this.filter.on('keypress', function(e) {
                if(e.getKey() == e.ENTER && this.filter.getValue().length > 0) {
                    this.dataStore.reload();
                }
            }.createDelegate(this));

            this.filter.on('keyup', function(e) {
                if(e.getKey() == e.BACKSPACE && this.filter.getValue().length === 0) {
                    this.dataStore.reload();
                }
            }.createDelegate(this));

            // 设置baseParams
            this.setBaseParams();
        }

        // 页脚
        var gridFooter = this.grid.getView().getFooterPanel(true);

        // 把分页工具条，放在页脚
        var paging = new Ext.PagingToolbar(gridFooter, this.dataStore, {
            pageSize         : this.pageSize
            , displayInfo    : true
            , displayMsg     : '显示: {0} - {1} 共 {2} 条记录'
            , emptyMsg       : "没有找到相关数据"
            , beforePageText : "第"
            , afterPageText  : "页，共{0}页"
        });

        var pageSizePlugin = new Ext.ux.PageSizePlugin();
        pageSizePlugin.init(paging);

        this.dataStore.load({
            params:{start:0, limit:paging.pageSize}
        });
    }

    // 设置baseParams
    , setBaseParams : function() {
        // 读取数据
        this.dataStore.on('beforeload', function() {
            this.dataStore.baseParams = {
                filterValue : this.filter.getValue(),
                filterTxt   : this.filterTxt
            };
        }.createDelegate(this));
    }

    // 进行渲染
    , render : function() {
        this.init();
        this.grid.render();
        this.postRender();
    }

    // 弹出添加对话框，添加一条新记录
    , add : function() {
        if (!this.dialog) {
            this.createDialog();
        }
        Ext.lingo.FormUtils.resetFields(this.columns);
        this.dialog.show(Ext.get("add"));
    }

    // 弹出修改对话框
    , edit : function() {
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
            reader     : new Ext.data.JsonReader({},this.headers),
            remoteSort : false
        });

        this.menuData.on('load', function() {
            for (var i = 0; i < this.metaData.length; i++) {
                var meta = this.metaData[i];

                var id = meta.id;
                var value;
                if (meta.mapping) {
                    try {
                        value = eval("this.menuData.getAt(0).data." + meta.mapping);
                    } catch (e) {
                        value = this.menuData.getAt(0).data[meta.mapping];
                    }
                } else {
                    value = this.menuData.getAt(0).data[id];
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
            this.dialog.show(Ext.get("edit"));
        }.createDelegate(this));
        this.menuData.load();
    }

    // 删除记录
    , del : function() {
        var selections = this.grid.getSelections();
        if (selections.length == 0) {
            Ext.MessageBox.alert("提示", "请选择希望删除的记录！");
            return;
        } else {
            Ext.Msg.confirm("提示", "是否确定删除？", function(btn, text) {
                if (btn == 'yes') {
                    var selections = this.grid.getSelections();
                    var ids = new Array();
                    for(var i = 0, len = selections.length; i < len; i++){
                        try {
                            // 如果选中的record没有在这一页显示，remove就会出问题
                            selections[i].get("id");
                            ids[i] = selections[i].get("id");
                            this.dataStore.remove(selections[i]);//从表格中删除
                        } catch (e) {
                        }
                        if (this.useHistory) {
                            this.grid.selModel.Set.clear();
                        }
                    }
                    Ext.Ajax.request({
                        url     : this.urlRemove + '?ids=' + ids,
                        success : function() {
                            Ext.MessageBox.alert(' 提示', '删除成功！');
                            this.refresh();
                        }.createDelegate(this),
                        failure : function(){Ext.MessageBox.alert('提示', '删除失败！');}
                    });
                }
            }.createDelegate(this));
        }
    }

    // 创建弹出式对话框
    , createDialog : function() {
        this.dialog = Ext.lingo.FormUtils.createTabedDialog('dialog', ['详细配置','帮助'], this.dialogWidth, this.dialogHeight);

        this.yesBtn = this.dialog.addButton("确定", function() {
            var item = Ext.lingo.FormUtils.serialFields(this.columns);

            if (!item) {
                return;
            }
            this.submitForm(item);
        }.createDelegate(this), this.dialog);

        // 设置两个tab
        this.tabs = this.dialog.getTabs();
        this.tabs.getTab(0).on("activate", function() {
            this.yesBtn.show();
        }, this, true);
        this.tabs.getTab(1).on("activate", function(){
            this.yesBtn.hide();
        }, this, true);

        var dialogContent = Ext.get(this.config.dialogContent);
        this.tabs.getTab(0).setContent(dialogContent.dom.innerHTML);
        document.body.removeChild(document.getElementById(this.config.dialogContent));
        this.applyElements();
        this.noBtn = this.dialog.addButton("取消", this.dialog.hide, this.dialog);
    }

    // 提交添加，修改表单
    , submitForm : function(item) {
        this.dialog.el.mask('提交数据，请稍候...', 'x-mask-loading');
        var hide = function() {
            this.dialog.el.unmask();
            this.dialog.hide();
            this.refresh.apply(this);
        }.createDelegate(this);
        Ext.lib.Ajax.request(
            'POST',
            this.urlSave,
            {success:hide,failure:hide},
            'data=' + encodeURIComponent(Ext.encode(item))
        );
    }

    // 超级重要的一个方法，自动生成表头，自动生成form，都是在这里进行的
    , applyElements : function() {

        if (this.columns == null || this.headers == null) {
            this.headers = new Array();
            for (var i = 0; i < this.metaData.length; i++) {
                if (this.metaData[i].mapping) {
                    this.headers[this.headers.length] = this.metaData[i].mapping;
                } else {
                    this.headers[this.headers.length] = this.metaData[i].id;
                }
            }

            // 打开验证功能
            //Ext.form.Field.prototype.msgTarget = 'side';
            //Ext.form.Field.prototype.height = 20;
            //Ext.form.Field.prototype.fieldClass = 'text-field-default';
            //Ext.form.Field.prototype.focusClass = 'text-field-focus';
            //Ext.form.Field.prototype.invalidClass = 'text-field-invalid';

            this.columns = Ext.lingo.FormUtils.createAll(this.metaData);

        }
    }

    // 选中搜索属性选项时
    , onItemCheck : function(item, checked) {
        if(checked) {
            this.filterButton.setText(item.text + ':');
            this.filterTxt = item.value;
        }
    }

    // 弹出右键菜单
    // 修改，和批量删除的功能
    // 多选的时候，不允许修改就好了
    , contextmenu : function(grid, rowIndex, e) {
        e.preventDefault();
        e.stopEvent();

        var updateMenu = new Ext.menu.Item({
            icon      : '../widgets/lingo/list-items.gif'
            , id      : 'updateMenu'
            , text    : '修改'
            , handler : this.edit.createDelegate(this)
        });
        var removeMenu = new Ext.menu.Item({
            icon      : '../widgets/lingo/list-items.gif'
            , id      : 'removeMenu'
            , text    : '删除'
            , handler :  this.del.createDelegate(this)
        });

        var selections = this.grid.getSelections();

        if (selections.length > 1) {
            updateMenu.disable();
        }

        var menuList = [updateMenu, removeMenu];

        grid_menu = new Ext.menu.Menu({
            id      : 'mainMenu'
            , items : menuList
        });

        var coords = e.getXY();
        grid_menu.showAt([coords[0], coords[1]]);
    }

    // 刷新表格数据
    , refresh : function() {
        this.dataStore.reload();
    }
});
