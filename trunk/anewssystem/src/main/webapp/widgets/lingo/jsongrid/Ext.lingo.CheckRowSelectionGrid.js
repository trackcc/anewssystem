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
    var cb_html = String.format("<input type='checkbox' id='{0}'/>", this.root_cb_id);
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
                "<input type='checkbox' id='{0}-{1}' {2}/>",
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
    root_cb_id : null,

    getSelectionModel: function() {
        if (!this.selModel) {
            this.selModel = new Ext.lingo.CheckRowSelectionModel();
        }
        return this.selModel;
    }
});


// 行模型
Ext.lingo.CheckRowSelectionModel = function(options) {
    Ext.lingo.CheckRowSelectionModel.superclass.constructor.call(this, options);
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
        Ext.lingo.CheckRowSelectionModel.superclass.selectRow.call(
            this, index, keepExisting, preventViewNotify
        );

        var row_id = this.grid.root_cb_id + '-' + index;
        var cb_dom = Ext.fly(row_id).dom;
        cb_dom.checked = true;
    },

    // 反选，取消选择，这一行
    deselectRow: function(index, preventViewNotify) {
        Ext.lingo.CheckRowSelectionModel.superclass.deselectRow.call(
            this, index, preventViewNotify
        );

        var row_id = this.grid.root_cb_id + '-' + index;
        var cb_dom = Ext.fly(row_id).dom;
        cb_dom.checked = false;
    },

    onGridRefresh: function() {
        Ext.fly(this.grid.root_cb_id).on('click', this.onAllRowsCheckboxClick, this, {stopPropagation:true});
        // Attaching to row checkboxes events
        for (var i = 0; i < this.grid.getDataSource().getCount(); i++) {
            var cb_id = this.grid.root_cb_id + '-' + i;
            Ext.fly(cb_id).on('mousedown', this.onRowCheckboxClick, this, {stopPropagation:true});
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
