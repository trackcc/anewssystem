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
 * 继承JsonTree，自定义树形.
 *
 * @param container 被渲染的html元素的id，<div id="lighttree"></div>
 * @param config    需要的配置{}
 */
TrackerTree = function(container, config) {
    TrackerTree.superclass.constructor.call(this, container, config);
};

Ext.extend(TrackerTree, Ext.lingo.JsonTree, {
    // 生成工具条
    buildToolbar : function() {
        this.toolbar = new Ext.Toolbar(this.treePanel.el.createChild({tag:'div'}));

        this.toolbar.add({
            text    : '添加新项目',
            icon    : '../widgets/lingo/list-items.gif',
            cls     : 'x-btn-text-icon album-btn',
            tooltip : '添加选中节点的下级分类',
            handler : this.createNewProject.createDelegate(this)
        }, {
            text    : '刷新',
            icon    : '../widgets/lingo/list-items.gif',
            cls     : 'x-btn-text-icon album-btn',
            tooltip : '刷新所有节点',
            handler : this.refresh.createDelegate(this)
        });
    }

    // 生成右键菜单
    , buildContextMenu : function() {
        this.contextMenu = new Ext.menu.Menu({
            id    : 'copyCtx',
            items : [{
                    id      : 'createChild',
                    icon    : '../widgets/lingo/list-items.gif',
                    handler : this.createNewProject.createDelegate(this),
                    cls     : 'create-mi',
                    text    : '添加新项目'
                },{
                    id      : 'remove',
                    icon    : '../widgets/lingo/list-items.gif',
                    handler : this.removeNode.createDelegate(this),
                    cls     : 'remove-mi',
                    text    : '删除节点'
                },'-',{
                    id      : 'refresh',
                    icon    : '../widgets/lingo/list-items.gif',
                    handler : this.refresh.createDelegate(this),
                    cls     : 'refresh',
                    text    : '刷新'
                },{
                    id      : 'config',
                    icon    : '../widgets/lingo/list-items.gif',
                    handler : this.configInfo.createDelegate(this),
                    text    : '详细配置'
            }]
        });
    }

    // 生成新项目
    , createNewProject : function() {
        this.createNode(this.treePanel.getRootNode());
    }

});
