/*
 * Ext JS Library 1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author Lingo
 * @since 2007-10-08
 * http://code.google.com/p/anewssystem/
 */
/**
 * 继承JsonGrid，自定义表格.
 *
 * @param container 被渲染的html元素的id，<div id="lightgrid"></div>
 * @param config    需要的配置{}
 */
TrackerGrid = function(container, config) {
    TrackerGrid.superclass.constructor.call(this, container, config);
};

Ext.extend(TrackerGrid, Ext.lingo.JsonGrid, {
    // 提交添加，修改表单
    submitForm : function(item) {

        if (item['trackerProject.id'] === "0") {
            item['trackerProject.id'] = Tracker.grid.projectId;
        }
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

    // 设置baseParams
    , setBaseParams : function() {
        // 读取数据
        this.dataStore.on('beforeload', function() {
            this.dataStore.baseParams = {
                filterValue : this.filter.getValue()
                , filterTxt : this.filterTxt
                , projectId : this.projectId
            };
        }.createDelegate(this));
    }
});
