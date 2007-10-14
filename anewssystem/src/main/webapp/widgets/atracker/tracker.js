
Tracker = {
    // 初始化
    init : function() {
        // 开启提示功能
        Ext.QuickTips.init();

        this.buildLayout();
        this.buildTree();
        this.buildGrid();
    }

    // 布局
    , buildLayout : function() {
        var layout = new Ext.BorderLayout(document.body, {
            hideOnLayout:false,
            west: {
                initialSize:169,
                minSize:169,
                split:true,
                titlebar:true
            },
            north: {
                initialSize:31,
                split:false,
                titlebar:false
            },
            center: {
                alwaysShowTabs:true,
                closeOnTab: true,
                tabPosition:'top'
            },
            south:{
                initialSize:18,
                split:false,
                titlebar:false
            }
        });

        layout.beginUpdate();

        this.nav_area = layout.add('north', new Ext.ContentPanel('nav_area', {
            fitToFrame:true,
            autoScroll:false,
            autoCreate:true
        })).el;

        this.tree_areaEl = layout.add('west', new Ext.ContentPanel('tree_area', {
            title:'分类',
            fitToFrame:true,
            autoScroll:true,
            autoCreate:true
        })).el.unselectable();

        this.state_areaEl = layout.add('south', new Ext.ContentPanel('state_area', {
            fitToFrame:true,
            autoScroll:false,
            autoCreate:true
        })).el;

        this.main_areaEl = layout.add('center', new Ext.ContentPanel('main_area', {
            title:'列表',
            fitToFrame:true,
            autoScroll:false,
            autoCreate:true
        })).el;

        layout.endUpdate();
        layout.layout();

        this.centerRegion = layout.getRegion('center');
    }

    // 左侧树形
    , buildTree : function() {
        var metaData = [
            {id : 'id',      qtip : "ID",       vType : "integer", allowBlank : true,  defValue : -1},
            {id : 'name',    qtip : "项目名称", vType : "chn",     allowBlank : false},
            {id : 'founder', qtip : "创建者",   vType : "chn",     allowBlank : false},
            {id : 'summary', qtip : "项目描述", vType : "editor",  allowBlank : true}
        ];
        Tracker.tree = new TrackerTree("tree_area", {
            metaData      : metaData,
            rootName      : '所有工程',
            dlgWidth      : 450,
            dlgHeight     : 250,
            dialogContent : "tree_content",
            urlGetAllTree : "../trackerproject/getAllTree.htm",
            urlInsertTree : "../trackerproject/insertTree.htm",
            urlRemoveTree : "../trackerproject/removeTree.htm",
            urlSortTree   : "../trackerproject/sortTree.htm",
            urlLoadData   : "../trackerproject/loadData.htm",
            urlUpdateTree : "../trackerproject/updateTree.htm"
        });

        Tracker.tree.render();

        Tracker.tree.treePanel.on("click", function(node, e) {
            Tracker.updateGrid(node);
        });
    }

    // 创建表格
    , buildGrid : function() {
        var metaData = [
            {id : 'id',         qtip : "ID",       vType : "integer",  allowBlank : true,  defValue : -1, w:40},
            {id : 'project_id', qtip : "项目id",   vType : "integer",  allowBlank : false,mapping:'trackerProject.id',showInGrid:false},
            {id : 'issuename',  qtip : "任务名称", vType : "chn",      allowBlank : false, w:160, mapping:'name'},
            {id : 'priority',   qtip : "优先级",   vType : "comboBox", allowBlank : false, w:50, renderer:this.renderPriority},
            {id : 'severity',   qtip : "严重度",   vType : "comboBox", allowBlank : false, w:50, renderer:this.renderSeverity},
            {id : 'status',     qtip : "状态",     vType : "comboBox", allowBlank : true, w:50, renderer:this.renderStatus},
            {id : 'assignTo',   qtip : "负责人",   vType : "chn",      allowBlank : false, w:60},
            {id : 'submitBy',   qtip : "提交者",   vType : "chn",      allowBlank : false, w:60},
            {id : 'addTime',    qtip : "提交时间", vType : "date",     allowBlank : false, w:100},
            {id : 'updateDate', qtip : "修改时间", vType : "date",     allowBlank : false, w:100},
            {id : 'content',    qtip : "内容",     vType : "editor",   allowBlank : true, w:100, renderer:this.renderContent},
            {id : 'file',       qtip : "附件",     vType : "alphanum", allowBlank : true, w:50}
        ];
        Tracker.grid = new TrackerGrid("main_area", {
            metaData      : metaData,
            dlgWidth      : 450,
            dlgHeight     : 250,
            dialogContent : "grid_content",
            urlPagedQuery : "../trackerissue/pagedQuery.htm",
            urlSave       : "../trackerissue/save.htm",
            urlLoadData   : "../trackerissue/loadData.htm",
            urlRemove     : "../trackerissue/remove.htm"
        });

        Tracker.grid.render();
    }

    // 更新grid显示对应projectId的数据
    , updateGrid : function(node) {
        Tracker.grid.projectId = node.id;
        Tracker.grid.dataStore.reload();
    }

    // priority优先级
    , renderPriority : function(value) {
        if (value == 0) {
            return "<span style='color:green;font-style:italic;'>低</span>";
        } else if (value == 1) {
            return "<span style='color:black;font-weight:normal;'>中</span>"
        } else {
            return "<span style='color:red;font-weight:bold;'>高</span>"
        }
    }

    // severity严重度
    , renderSeverity : function(value) {
        if (value == 0) {
            return "<span style='color:green;font-style:italic;'>轻微</span>";
        } else if (value == 1) {
            return "<span style='color:black;font-weight:normal;'>一般</span>"
        } else {
            return "<span style='color:red;font-weight:bold;'>严重</span>"
        }
    }

    // status状态
    , renderStatus : function(value) {
        if (value == 0) {
            return "<span style='color:red;font-weight:bold;'>待处理</span>";
        } else if (value == 1) {
            return "<span style='color:green;font-weight:normal;'>已处理</span>"
        } else {
            return "<span style='color:gray;font-style:italic;'>已关闭</span>"
        }
    }

    // content内容
    , renderContent : function(value) {
        if (value.length > 6) {
            return value.substring(0, 6) + "...";
        } else {
            return value;
        }
    }
};

Ext.onReady(Tracker.init, Tracker);
