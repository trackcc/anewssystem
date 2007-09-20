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
    this.pageSize  = config.pageSize ? config.pageSize : 15;
    this.urlPagedQuery = config.urlPagedQuery ? config.urlPagedQuery : "pagedQuery.htm";
    this.urlLoadData   = config.urlLoadData   ? config.urlLoadData   : "loadData.htm";
    this.urlSave       = config.urlSave       ? config.urlSave       : "save.htm";
    this.urlRemove     = config.urlRemove     ? config.urlRemove     : "remove.htm";

    this.filterTxt = "";

    Ext.lingo.JsonGrid.superclass.constructor.call(this);
}

Ext.extend(Ext.lingo.JsonGrid, Ext.util.Observable, {
    init : function() {
        // 根据this.headers生成columnModel
        if (!this.columnModel) {
            var columnHeaders = new Array();
            for (var i = 0; i < this.config.metaData.length; i++) {
                var item = {};
                item.header    = this.config.metaData[i].qtip;
                item.dataIndex = this.config.metaData[i].id;
                item.width     = 110;
                // item.hidden = false;
                // item.renderer = formatDate;
                columnHeaders[columnHeaders.length] = item;
            }
            this.columnModel = new Ext.grid.ColumnModel(columnHeaders);
            this.columnModel.defaultSortable = false;
        }

        // 生成data record
        if (!this.dataRecord) {
            var recordHeaders = new Array();
            for (var i = 0; i < this.config.metaData.length; i++) {
                var item = {};
                item.name       = this.config.metaData[i].id;
                item.type       = "string";
                //item.mapping    = "enterDate";
                //item.dateFormat = "yyyy-MM-dd";
                recordHeaders[recordHeaders.length] = item;
            }
            this.dataRecord = Ext.data.Record.create(recordHeaders);
        }

        // 生成data store
        if (!this.dataStore) {
            this.dataStore = new Ext.data.Store({
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
                ds : this.dataStore,
                cm : this.columnModel,
                // selModel: new Ext.grid.CellSelectionModel(),
                // selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
                enableColLock:false,
                loadMask : true
            });
        }

        //右键菜单
        this.grid.addListener('rowcontextmenu', this.contextmenu.createDelegate(this));
    }, render : function() {
        this.init();
        this.grid.render();

        // header
        var gridHeader = this.grid.getView().getHeaderPanel(true);
        var toolbar = new Ext.Toolbar(gridHeader);
        var checkItems = new Array();
        for (var i = 0; i < this.config.metaData.length; i++) {
            var item = new Ext.menu.CheckItem({
                text         : this.config.metaData[i].qtip,
                value        : this.config.metaData[i].id,
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
        toolbar.add({
            id      : 'add',
            text    : '新增',
            cls     : 'add',
            tooltip : '新增',
            handler : this.add.createDelegate(this)
        }, {
            id      : 'edit',
            text    : '修改',
            cls     : 'edit',
            tooltip : '修改',
            handler : this.edit.createDelegate(this)
        }, {
            id      : 'del',
            text    : '删除',
            cls     : 'del',
            tooltip : '新增',
            handler : this.del.createDelegate(this)
        }, '->', this.filterButton);

        // 输入框
        this.filter = Ext.get(toolbar.addDom({
             tag   : 'input',
             type  : 'text',
             size  : '20',
             value : '',
             style : 'background: #F0F0F9;'
        }).el);

        this.filter.on('keypress', function(e) {
          if(e.getKey() == e.ENTER && this.filter.getValue().length > 0) {
              this.dataStore.load({params:{start:0, limit:this.pageSize}});
          }
        }.createDelegate(this));

        this.filter.on('keyup', function(e) {
          if(e.getKey() == e.BACKSPACE && this.filter.getValue().length === 0) {
              this.dataStore.load({params:{start:0, limit:this.pageSize}});
          }
        }.createDelegate(this));

        // 页脚
        var gridFooter = this.grid.getView().getFooterPanel(true);

        // 把分页工具条，放在页脚
        var paging = new Ext.PagingToolbar(gridFooter, this.dataStore, {
            pageSize    : this.pageSize,
            displayInfo : true,
            displayMsg  : '显示: {0} - {1} 共 {2}',
            emptyMsg    : "没有找到相关数据"
        });

        // 读取数据
        this.dataStore.on('beforeload', function() {
            this.dataStore.baseParams = {
                filterValue : this.filter.getValue(),
                filterTxt   : this.filterTxt
            };
        }.createDelegate(this));
        this.dataStore.load({
            params:{start:0, limit:this.pageSize}
        });
    },
    // 弹出添加对话框，添加一条新记录
    add : function() {
        if (!this.dialog) {
            this.createDialog();
        }
        for (var i = 0; i < this.columns.length; i++) {
            var column = this.columns[i];
            if (column.vType == "integer") {
                column.setValue(0);
            } else {
                column.reset();
            }
        }
        this.dialog.show(Ext.get("add"));
    },
    // 弹出修改对话框
    edit : function() {
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
            for (var i = 0; i < this.headers.length; i++) {
                var id = this.headers[i];
                this.columns[i].setValue(this.menuData.getAt(0).data[id]);
            }
            this.dialog.show(Ext.get("edit"));
        }.createDelegate(this));
        this.menuData.load();

    },
    // 删除记录
    del : function() {

        var selections = this.grid.getSelections();
        if (selections.length == 0) {
            Ext.MessageBox.alert("提示", "请选择希望修改的记录！");
            return;
        } else {
            Ext.Msg.confirm("提示", "是否确定删除？", function(btn, text) {
                if (btn == 'yes') {
                    var selections = this.grid.getSelections();
                    var ids =new Array();
                    for(var i = 0, len = selections.length; i < len; i++){
                        selections[i].get("id");
                        ids[i] = selections[i].get("id");
                        this.dataStore.remove(selections[i]);//从表格中删除
                    }
                    Ext.Ajax.request({
                        url     : this.urlRemove + '?id=' + ids,
                        success : function(){
                            Ext.MessageBox.alert(' 提示', '删除成功！');
                            this.refresh();
                        }.createDelegate(this),
                        failure : function(){Ext.MessageBox.alert('提示', '删除失败！');}
                    });
                }
            }.createDelegate(this));
        }
    }, input : function(meta) {
        var field = new Ext.form.TextField({
            allowBlank : meta.allowBlank == undefined ? false : meta.allowBlank,
            vType      : meta.vType,
            cls        : meta.type == "password" ? meta.cls : null,
            width      : meta.vWidth,
            id         : meta.id,
            name       : meta.id,
            style      : (meta.vType == "integer" || meta.vType == "number" ? "text-align: right;" : ""),
            readOnly   : meta.readOnly,
            defValue   : meta.defValue,
            alt        : meta.alt,
            maxLength  : meta.maxlength ? meta.maxlength : Number.MAX_VALUE,
            minLength  : meta.minlength ? meta.minlength : 0,
            minValue   : meta.minvalue ? meta.minvalue : 0,
            maxValue   : meta.maxvalue ? meta.maxvalue : Number.MAX_VALUE
        });
        if(meta.readOnly) {
            field.style += "color:#656B86;";
        }
        //if(meta.value != "" && meta.format == "date") {
        //    field.value = datagrids[0].date(meta.value);
        //}
        field.applyTo(meta.id);
        if(meta.defValue) {
            field.setValue(meta.defValue);
        }
        return field;
    }, date : function(meta) {
        var field = new Ext.form.DateField({
            id          : meta.id,
            name        : meta.id,
            allowBlank  : meta.allowBlank == undefined ? false : eval(meta.allowBlank),
            format      : meta.format ? meta.format : "Y年m月d日",
            readOnly    : true,
            width       : meta.vWidth,
            defValue    : meta.defValue,
            vType       : "date",
            alt         : meta.alt,
            setAllMonth : meta.setAllMonth ? el.setAllMonth : false
        });
        field.applyTo(meta.id);
        if(meta.defValue) {
            field.setValue(meta.defValue);
        } else {
            field.setValue(new Date());
        }
        return field;
    }, createDlgContentDiv : function() {
        // 内容
        var dialogContent = document.getElementById(this.config.dialogContent);
        var contentDiv = document.createElement("div");
        contentDiv.id = this.id + "-content";
        contentDiv.appendChild(dialogContent);

        // 消息
        var dialogMessage = document.createElement("div");
        var waitMessage = document.createElement("div");
        var waitText = document.createElement("div");
        dialogMessage.id = "dlg-msg";
        waitMessage.id = "post-wait";
        waitMessage.className = "posting-msg";
        waitText.className = "waitting";
        waitText.innerHTML = "正在保存，请稍候...";
        waitMessage.appendChild(waitText);
        dialogMessage.appendChild(waitMessage);

        //
        var dialogDiv = document.createElement("div");
        var dialog_head = document.createElement("div");
        var dialog_body = document.createElement("div");
        var dlg_tab = document.createElement("div");
        var dlg_help = document.createElement("div");
        var helpContent = document.createElement("div");
        var dialog_foot = document.createElement("div");
        dialogDiv.id = this.id + "-dialog-content";
        dialogDiv.style.visibility = "hidden";
        dialog_head.className = "x-dlg-hd";
        dialog_body.className = "x-dlg-bd";
        dialog_foot.className = "x-dlg-ft";
        dlg_tab.className = "x-dlg-tab";
        dlg_tab.title = " 详细配置 ";
        dlg_help.className = "x-dlg-tab";
        dlg_help.title = " 帮助 ";
        helpContent.innerHTML = "<div id='help-content'><div id='standard-panel'>帮助...</div></div><div id='temp-content'></div>";
        dlg_help.appendChild(helpContent);
        dialog_body.appendChild(dlg_tab);
        dialog_body.appendChild(dlg_help);
        dialog_foot.appendChild(dialogMessage);
        dialogDiv.appendChild(dialog_head);
        dialogDiv.appendChild(dialog_body);
        dialogDiv.appendChild(dialog_foot);

        document.body.appendChild(dialogDiv);
        document.body.appendChild(contentDiv);
    }, createDialog : function() {
        this.createDlgContentDiv();
        this.dialog = new Ext.BasicDialog(this.id + "-dialog-content", {
            modal     : false,
            autoTabs  : true,
            width     : (this.config.dlgWidth == undefined ? 600 : this.config.dlgWidth),
            height    : (this.config.dlgHeight == undefined ? 400 : this.config.dlgHeight),
            shadow    : false,
            minWidth  : 200,
            minHeight : 100,
            closable  : true,
            autoCreate : true
        });

        this.dialog.addKeyListener(27, this.dialog.hide, this.dialog);
        this.yesBtn = this.dialog.addButton("确定", function() {
            var item = {};
            for (var i = 0; i < this.columns.length; i++) {
                var obj = this.columns[i];
                item[obj.id] = obj.getValue();
            }
            this.dialog.el.mask('提交数据，请稍候...', 'x-mask-loading');
            // var hide = this.dialog.el.unmask.createDelegate(this.dialog.el);
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
        }.createDelegate(this), this.dialog);
        this.tabs = this.dialog.getTabs();
        this.tabs.getTab(0).on("activate", function() {
            this.yesBtn.show();
        }, this, true);
        this.tabs.getTab(1).on("activate", function(){
            this.yesBtn.hide();
        }, this, true);

        var dialogContent = Ext.get(this.id + "-content");
        this.tabs.getTab(0).setContent(dialogContent.dom.innerHTML);
        this.applyElements();
        this.noBtn = this.dialog.addButton("取消", this.dialog.hide, this.dialog);
    },
    // 超级重要的一个方法，自动生成表头，自动生成form，都是在这里进行的
    applyElements : function() {
        if (this.columns == null || this.headers == null) {
            this.columns = new Array();
            this.headers = new Array();
            for (var i = 0; i < this.config.metaData.length; i++) {
                this.headers[this.headers.length] = this.config.metaData[i].id;
            }

            // 打开验证功能
            //Ext.form.Field.prototype.msgTarget = 'side';
            //Ext.form.Field.prototype.height = 20;
            //Ext.form.Field.prototype.fieldClass = 'text-field-default';
            //Ext.form.Field.prototype.focusClass = 'text-field-focus';
            //Ext.form.Field.prototype.invalidClass = 'text-field-invalid';

            var dialogContent = Ext.get(this.config.dialogContent);
            for (var i = 0; i < this.config.metaData.length; i++) {
                var meta = this.config.metaData[i];
                var field;
                if (meta.vType == "date") {
                    field = this.date(meta);
                } else if (meta.vType == "comboBox") {
                } else if (meta.vType == "textArea") {
                } else if (meta.vType == "treeField") {
                } else {
                    field = this.input(meta);
                }
                this.columns[this.columns.length] = field;
            }
        }
    }, onItemCheck : function(item, checked) {
        if(checked) {
            this.filterButton.setText(item.text + ':');
            this.filterTxt = item.value;
        }
    },
    // 弹出右键菜单
    contextmenu : function(grid, rowIndex, e) {
        e.preventDefault();
        e.stopEvent();
        var infoMenu = new Ext.menu.Item({
            id      : 'infoMenu',
            text    : 'infoMenu',
            handler : function() {
                Ext.MessageBox.alert("info", Ext.util.JSON.encode(this.grid.dataSource.getAt(rowIndex).data));
            }.createDelegate(this)
        });
        var menuList = [infoMenu];

        grid_menu = new Ext.menu.Menu({
            id    : 'mainMenu',
            items : menuList
        });

        var coords = e.getXY();
        grid_menu.showAt([coords[0], coords[1]]);
    }, refresh : function() {
        this.dataStore.load({params:{start:0,limit:this.pageSize}});
    }
});
