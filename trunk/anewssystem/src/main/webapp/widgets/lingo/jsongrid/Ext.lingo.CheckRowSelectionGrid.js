/*
 * Ext JS Library 1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author Lingo
 * @since 2007-09-13
 * http://code.google.com/p/anewssystem/
 */
/**
 * 声明Ext.lingo命名控件
 * TODO: 完全照抄，作用不明
 */
Ext.namespace('Ext.lingo');
/**
 * 带checkbox的多选grid.
 *
 * @param container 被渲染的html元素的id，<div id="lightgrid"></div>
 * @param config    需要的配置{}
 * @see http://extjs.com/forum/showthread.php?t=8162 Ext.ux.CheckRowSelectionGrid
 */
Ext.lingo.CheckRowSelectionGrid = function(container, config) {
    // id
    var id = this.root_cb_id = Ext.id(null, 'cbsm-');
    // checkbox模板
    var cb_html = String.format("<input class='l-tcb' type='checkbox' id='{0}'/>", this.root_cb_id);
    // grid
    var grid = this;
    // columnModel
    var cm = config.cm;
    // Hack
    var cm = config.cm || config.colModel;
    // 操作columnModel
    cm.config.unshift({
        id        : id,
        header    : cb_html,
        width     : 20,
        resizable : false,
        fixed     : true,
        sortable  : false,
        dataIndex : -1,
        renderer  : function(data, cell, record, rowIndex, columnIndex, store) {
            return String.format(
                "<input class='l-tcb' type='checkbox' id='{0}-{1}' {2}/>",
                id,
                rowIndex,
                grid.getSelectionModel().isSelected(rowIndex) ? "checked='checked'" : ''
            );
        }
    });
    cm.lookup[id] = cm.config[0];

    Ext.lingo.CheckRowSelectionGrid.superclass.constructor.call(this, container, config);
}

Ext.extend(Ext.lingo.CheckRowSelectionGrid, Ext.grid.Grid, {
    root_cb_id : null

    // 获得选择模型，如果当前没有设置，就返回咱们定义的这个带checkbox的东东
    , getSelectionModel: function() {

        if (!this.selModel) {
            this.selModel = new Ext.lingo.CheckRowSelectionModel();
        }
        return this.selModel;
    }
});

Ext.lingo.Store = function(config){
    Ext.lingo.Store.superclass.constructor.call(this, config);
}

Ext.extend(Ext.lingo.Store, Ext.data.Store, {

    // 重写sort方法，让我们可以用mapping代替name进行排序
    sort : function(fieldName, dir){
        var f = this.fields.get(fieldName);

        // 如果存在mapping，就使用mapping替换name实现排序
        var sortName = f.name;
        if (f.mapping) {
            sortName = f.mapping;
        }

        if(!dir){
            if(this.sortInfo && this.sortInfo.field == sortName){
                dir = (this.sortToggle[sortName] || "ASC").toggle("ASC", "DESC");
            }else{
                dir = f.sortDir;
            }
        }
        this.sortToggle[sortName] = dir;
        this.sortInfo = {field: sortName, direction: dir};
        if(!this.remoteSort){
            this.applySort();
            this.fireEvent("datachanged", this);
        }else{
            this.load(this.lastOptions);
        }
    }
});


// 行模型
Ext.lingo.CheckRowSelectionModel = function(options) {
    Ext.lingo.CheckRowSelectionModel.superclass.constructor.call(this, options);

    this.useHistory = options.useHistory === true;
    if (this.useHistory) {
        this.Set = {
            items : {}

            , add : function(r) {
                this.items[r.id] = r;
            }

            , remove : function(r) {
                this.items[r.id] = null;
            }

            , clear : function() {
                this.items = {};
            }

            , values : function() {
                var array = new Array();
                for (var i in this.items) {
                    if (this.items[i]) {
                        array[array.length] = this.items[i];
                    }
                }
                return array;
            }
        };
    }
}

Ext.extend(Ext.lingo.CheckRowSelectionModel, Ext.grid.RowSelectionModel, {

    init: function(grid) {
        Ext.lingo.CheckRowSelectionModel.superclass.init.call(this, grid);

        // Start of dirty hacking
        // Hacking grid
        if (grid.processEvent) {
            grid.__oldProcessEvent = grid.processEvent;
            grid.processEvent = function(name, e) {
                // The scope of this call is the grid object
                var target = e.getTarget();
                var view = this.getView();
                var header_index = view.findHeaderIndex(target);
                if (name == 'contextmenu' && header_index === 0) {
                    return;
                }
                this.__oldProcessEvent(name, e);
            }
        }

        // Hacking view
        var gv = grid.getView();
        if (gv.beforeColMenuShow) {
            gv.__oldBeforeColMenuShow = gv.beforeColMenuShow;
            gv.beforeColMenuShow = function() {
                // The scope of this call is the view object
                this.__oldBeforeColMenuShow();
                // Removing first - checkbox column from the column menu
                this.colMenu.remove(this.colMenu.items.first()); // he he
            }
        }
        // End of dirty hacking
    },

    initEvents: function() {
        Ext.lingo.CheckRowSelectionModel.superclass.initEvents.call(this);
        this.grid.getView().on('refresh', this.onGridRefresh, this);
    },

    // 选择这一行
    selectRow: function(index, keepExisting, preventViewNotify) {

        try {
            Ext.lingo.CheckRowSelectionModel.superclass.selectRow.call(
                this, index, keepExisting, preventViewNotify
            );

            var row_id = this.grid.root_cb_id + '-' + index;
            var cb_dom = Ext.fly(row_id).dom;
            cb_dom.checked = true;

            if (this.useHistory) {
                // change
                var r = this.grid.dataSource.getAt(index);
                this.Set.add(r);
            }
        } catch(e) {
            if (this.useHistory) {
                this.Set.clear();
            }
        }
    },

    // 反选，取消选择，这一行
    deselectRow: function(index, preventViewNotify) {

        try {
            Ext.lingo.CheckRowSelectionModel.superclass.deselectRow.call(
                this, index, preventViewNotify
            );

            var row_id = this.grid.root_cb_id + '-' + index;
            var cb_dom = Ext.fly(row_id).dom;
            cb_dom.checked = false;


            if (this.useHistory) {
                // change
                var r = this.grid.dataSource.getAt(index);
                this.Set.remove(r);
            }
        } catch (e) {
            if (this.useHistory) {
                this.Set.clear();
            }
        }
    },

    onGridRefresh: function() {
        Ext.fly(this.grid.root_cb_id).on('click', this.onAllRowsCheckboxClick, this, {stopPropagation:true});
        // Attaching to row checkboxes events
        for (var i = 0; i < this.grid.getDataSource().getCount(); i++) {
            var cb_id = this.grid.root_cb_id + '-' + i;
            Ext.fly(cb_id).on('mousedown', this.onRowCheckboxClick, this, {stopPropagation:true});
        }

        if (this.useHistory) {
            // change
            var ds = this.grid.dataSource, i, v = this.grid.view;
            var values = this.Set.values();

            for (var i = 0; i < values.length; i++) {
                var r = values[i];
                var id = r.id;
                if((index = ds.indexOfId(id)) != -1){
                    // 让GridView看起来是选中的样子
                    v.onRowSelect(index);
                    // 让checkbox看起来是选中的样子
                    var row_id = this.grid.root_cb_id + '-' + index;
                    var cb_dom = Ext.fly(row_id).dom;
                    cb_dom.checked = true;
                }
                // 让this.selections里边是选中的样子
                this.selections.add(r);
            }
        }
    },

    onAllRowsCheckboxClick: function(event, el) {
        if (el.checked) {
            this.selectAll();
        } else {
            this.clearSelections();
        }
    },

    onRowCheckboxClick: function(event, el) {
        var rowIndex = /-(\d+)$/.exec(el.id)[1];
        if (el.checked) {
            this.deselectRow(rowIndex);
            el.checked = true;
        } else {
            this.selectRow(rowIndex, true);
            el.checked = false;
        }
    }
});
