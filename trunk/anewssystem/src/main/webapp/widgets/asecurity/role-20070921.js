/*
 * Ext JS Library 1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author Lingo
 * @since 2007-09-21
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
            title      : '角色管理',
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

    // 默认需要id, name, theSort, parent, children
    // 其他随意定制
    var metaData = [
        {id : 'id',         qtip : "ID",       vType : "integer",  allowBlank : true,  defValue : -1},
        {id : 'name',       qtip : "角色名称", vType : "chn",      allowBlank : false},
        {id : 'descn',      qtip : "描述",     vType : "chn",      allowBlank : true}
    ];

    // 创建表格
    var lightGrid = new Ext.lingo.JsonGrid("lightgrid", {
        metaData      : metaData,
        dialogContent : "content"
    });

    // 渲染表格
    lightGrid.render();

    // ========================================================================
    // ========================================================================
    // 在工具栏上添一个按钮
    lightGrid.toolbar.insertButton(3, {
        id      : 'config',
        text    : '配置资源',
        cls     : 'add',
        tooltip : '配置资源',
        handler : doAuth
    });

    // ========================================================================
    // ========================================================================
    // 渲染表格的方法
    function renderResource(value, p, record){
        if(record.data['authorized']==true){
            return String.format("<b><font color=green>已分配</font></b>");
        }else{
            return String.format("<b><font color=red>未分配</font></b>");
        }
    }
    function renderNamePlain(value){
        return String.format('{0}', value);
    }
    // 建一个资源数据映射数组
    var resourceRecord = Ext.data.Record.create([
        {name: "id",         mapping:"id",         type: "int"},
        {name: "resType",    mapping:"resType",    type: "string"},
        {name: "name",       mapping:"name",       type: "string"},
        {name: "resString",  mapping:"resString",  type: "string"},
        {name: "descn",      mapping:"descn",      type: "string"},
        {name: "authorized", mapping:"authorized", type: "boolean"}
    ]);
    // 配置资源
    var resStore = new Ext.data.Store({
        proxy      : new Ext.data.HttpProxy({url:'getResources.htm'}),
        reader     : new Ext.data.JsonReader({},resourceRecord),
        remoteSort : false
    });
    var resColumnModel = new Ext.grid.ColumnModel([{
        // 设置了id值，我们就可以应用自定义样式 (比如 .x-grid-col-topic b { color:#333 })
        id        : 'id',
        header    : "编号",
        dataIndex : "id",
        width     : 80,
        sortable  : true,
        renderer  : renderNamePlain,
        css       : 'white-space:normal;'
    }, {
        id        : 'name',
        header    : "资源名称",
        dataIndex : "name",
        sortable  : true,
        width     : 150 ,
        css       : 'white-space:normal;'
    }, {
        id        : 'resType',
        header    : "资源类型",
        dataIndex : "resType",
        sortable  : true,
        width     : 80
    }, {
        id        : 'resString',
        header    : "资源地址",
        dataIndex : "resString",
        sortable  : true,
        width     : 150
    }, {
        id        : 'descn',
        header    : "资源描述",
        dataIndex : "descn",
        sortable  : true,
        width     : 80
    }, {
        id        : 'authorized',
        header    : "是否授权",
        dataIndex : "authorized",
        sortable  : true,
        width     : 80,
        renderer  : renderResource
    }]);
    var resourceGrid = new Ext.grid.EditorGrid('resource-grid', {
        ds            : resStore,
        cm            : resColumnModel,
        selModel      : new Ext.grid.RowSelectionModel({singleSelect:false}),
        enableColLock : false,
        loadMask      : false
    });
    // 渲染表格
    resourceGrid.render();
    var resourceFooter = resourceGrid.getView().getFooterPanel(true);
    var resourcePaging = new Ext.PagingToolbar(resourceFooter, resStore, {
        pageSize    : 10,
        displayInfo : true,
        displayMsg  : '显示: {0} - {1} 共 {2}',
        emptyMsg    : "没有找到相关数据"
    });
    resourcePaging.add('-', {
        pressed       : true,
        enableToggle  : true,
        text          : '授权',
        cls           : '',
        toggleHandler : function(){
            //授权事件
            var mRole = lightGrid.grid.getSelections();
            var mResc = resourceGrid.getSelections();
            if(mResc.length <= 0) {
                Ext.MessageBox.alert('提示', '请选择至少一行纪录进行操作！');
                return;
            } else {
                var ids = new Array();
                for (var i = 0; i < mResc.length; i++) {
                    var rescId = mResc[i].get('id');
                    ids[ids.length] = rescId;
                }
                var roleId = mRole[0].get('id');
                Ext.lib.Ajax.request(
                    'POST',
                    'auth.htm',
                    {success:end,failure:end},
                    'ids=' + ids.join(",") + "&roleId=" + roleId + "&isAuth=true"
                );
            }
            resStore.load({params:{
                start  : 0,
                limit  : 10,
                roleId : mRole[0].get('id')
            }});
        }
    }, '-', {
        pressed      : true,
        enableToggle : true,
        text         : '取消',
        cls          : '',
        toggleHandler: function(){
            //取消授权事件
            var mRole = lightGrid.grid.getSelections();
            var mResc = resourceGrid.getSelections();
            if(mResc.length <= 0) {
                Ext.MessageBox.alert('提示', '请选择至少一行纪录进行操作！');
                return;
            } else {
                var ids = new Array();
                for (var i = 0; i < mResc.length; i++) {
                    var rescId = mResc[i].get('id');
                    ids[ids.length] = rescId;
                }
                var roleId = mRole[0].get('id');
                Ext.lib.Ajax.request(
                    'POST',
                    'auth.htm',
                    {success:end,failure:end},
                    'ids=' + ids.join(",") + "&roleId=" + roleId + "&isAuth=false"
                );
            }
            resStore.load({params:{
                start  : 0,
                limit  : 10,
                roleId : mRole[0].get('id')
            }});
        }
    });

    function end() {
        Ext.Msg.alert("提示", "操作成功");
        resStore.load({params:{
            start  : 0,
            limit  : 10,
            roleId : lightGrid.grid.getSelections()[0].get('id')
        }});
    }

    function doAuth() {
        var m = lightGrid.grid.getSelections();
        if(m.length <= 0) {
            Ext.MessageBox.alert('提示', '请选择需要配置的角色！');
            return;
        }
        resStore.load({params:{
            start  : 0,
            limit  : 10,
            roleId : m[0].get('id')
        }});
        var aAddInstanceDlg = createNewDialog("resource-dlg");
        var layout = aAddInstanceDlg.getLayout();
        layout.beginUpdate();
          layout.add('center', new Ext.ContentPanel('resource-inner', {title: '角色授权'}));
          layout.endUpdate();
        aAddInstanceDlg.show();
    }
    // 新建对话框
    function createNewDialog(dialogName) {
        var thisDialog = new Ext.LayoutDialog(dialogName, {
            modal     : false,
            autoTabs  : true,
            proxyDrag : true,
            resizable : true,
            width     : 650,
            height    : 500,
            shadow    : true,
            center: {
                autoScroll     : true,
                tabPosition    : 'top',
                closeOnTab     : true,
                alwaysShowTabs : false
            }
        });
        thisDialog.addKeyListener(27, thisDialog.hide, thisDialog);
        thisDialog.addButton('关闭', function() {thisDialog.hide();});

        return thisDialog;
    };

});
