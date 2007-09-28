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


UserGridPanel = function(container, config) {
    UserGridPanel.superclass.constructor.call(this, container, config);
    this.addMetaData = [
        {id : 'dept',     qtip : '部门', vType : 'treeField', url : "../dept/getChildren.htm", mapping : "dept.name"},
        {id : 'username', qtip : "帐号", vType : "chn",       allowBlank : false},
        {id : 'password', qtip : '密码', vType : "passwordmeta",  allowBlank : false},
        {id : 'confirmpassword', qtip : '确认密码', vType : "password",  allowBlank : false},
        {id : 'truename', qtip : '姓名', vType : "chn"},
        {id : 'sex',      qtip : '性别', vType : "radio", values : [{id : '0', name : '男'}, {id : '1', name : '女'}], defValue : '0', renderer : Ext.lingo.FormUtils.renderSex},
        {id : 'birthday', qtip : '生日', vType : "date"},
        {id : 'tel',      qtip : '电话', vType : "alphanum"},
        {id : 'mobile',   qtip : '手机', vType : "alphanum"},
        {id : 'email',    qtip : '邮箱', vType : "email"},
        {id : 'duty',     qtip : '职务', vType : "chn"},
        {id : 'descn',    qtip : "备注", vType : "chn"}
    ];
    this.editMetaData = [
        {id : 'id2',       qtip : "ID",   vType : "integer",  defValue : -1, mapping : "id"},
        {id : 'dept2',     qtip : '部门', vType : 'treeField', url : "../dept/getChildren.htm", mapping : "dept.name"},
        {id : 'username2', qtip : "帐号", vType : "chn",       allowBlank : false, mapping : "username"},
        {id : 'oldpassword2', qtip : '密码', vType : "password",  allowBlank : true, mapping : "oldpassword2"},
        {id : 'password2', qtip : '密码', vType : "passwordmeta",  allowBlank : true, mapping : "password2"},
        {id : 'confirmpassword2', qtip : '确认密码', vType : "password",  allowBlank : true, mapping : "confirmpassword2"},
        {id : 'truename2', qtip : '姓名', vType : "chn", mapping : "truename"},
        {id : 'sex2',      qtip : '性别', vType : "radio", values : [{id : '0', name : '男'}, {id : '1', name : '女'}], defValue : '0', renderer : Ext.lingo.FormUtils.renderSex, mapping : "sex"},
        {id : 'birthday2', qtip : '生日', vType : "date", mapping : "birthday"},
        {id : 'tel2',      qtip : '电话', vType : "alphanum", mapping : "tel"},
        {id : 'mobile2',   qtip : '手机', vType : "alphanum", mapping : "mobile"},
        {id : 'email2',    qtip : '邮箱', vType : "email", mapping : "email"},
        {id : 'duty2',     qtip : '职务', vType : "chn", mapping : "duty"},
        {id : 'descn2',    qtip : "备注", vType : "chn", mapping : "descn"}
    ];
    this.headers = ['id','dept','username','password','truename','sex','birthday','tel','mobile','email','duty','descn'];
};

Ext.extend(UserGridPanel, Ext.lingo.JsonGrid, {

    // 添加
    add : function() {
        this.createAddDialog();
        this.columns = this.addcolumns;
        this.dialog = this.adddialog;
        this.metaData = this.addMetaData;
        UserGridPanel.superclass.add.call(this);
    }

    // 修改
    , edit : function() {
        this.createEditDialog();
        this.columns = this.editcolumns;
        this.dialog = this.editdialog;
        this.metaData = this.editMetaData;
        UserGridPanel.superclass.edit.call(this);
    }

    // 创建弹出式对话框
    , createAddDialog : function() {
        if (this.adddialog) {
            return;
        }
        ///////////////////////////////////////////////////
        // add
        //
        this.adddialog = Ext.lingo.FormUtils.createTabedDialog('add-dialog', ['添加信息','帮助']);

        this.addyesBtn = this.adddialog.addButton("确定", function() {
            if (this.addcolumns.password.getValue() != this.addcolumns.confirmpassword.getValue()) {
                Ext.suggest.msg("错误", "两次输入的密码不一样");
                this.addcolumns.confirmpassword.focus();
                return;
            }
            var item = Ext.lingo.FormUtils.serialFields(this.addcolumns);
            if (!item) {
                return;
            }

            this.adddialog.el.mask('提交数据，请稍候...', 'x-mask-loading');
            var addhide = function() {
                this.adddialog.el.unmask();
                this.adddialog.hide();
                this.refresh.apply(this);
            }.createDelegate(this);
            Ext.lib.Ajax.request(
                'POST',
                this.urlSave,
                {success:addhide,failure:addhide},
                'data=' + encodeURIComponent(Ext.encode(item))
            );
        }.createDelegate(this), this.adddialog);

        // 设置两个tab
        var addtabs = this.adddialog.getTabs();
        addtabs.getTab(0).on("activate", function() {
            this.addyesBtn.show();
        }, this, true);
        addtabs.getTab(1).on("activate", function(){
            this.addyesBtn.hide();
        }, this, true);

        addtabs.getTab(0).setContent(Ext.get("add-content").dom.innerHTML);
        document.body.removeChild(document.getElementById("add-content"));

        this.addcolumns = Ext.lingo.FormUtils.createAll(this.addMetaData);
        this.addnoBtn = this.adddialog.addButton("取消", this.adddialog.hide, this.adddialog);
    }

    , createEditDialog : function() {
        if (this.editdialog) {
            return;
        }
        ///////////////////////////////////////////////////
        // edit
        //
        this.editdialog = Ext.lingo.FormUtils.createTabedDialog('edit-dialog', ['基本信息','详细信息','帮助']);

        this.edityesBtn = this.editdialog.addButton("确定", function() {
            if (this.editcolumns.oldpassword2.getValue() != "" &&
                    this.editcolumns.password2.getValue() != this.editcolumns.confirmpassword2.getValue()) {
                Ext.suggest.msg("错误", "两次输入的密码不一样");
                this.editcolumns.confirmpassword2.focus();
                return;
            }
            var item = Ext.lingo.FormUtils.serialFields(this.editcolumns);
            if (!item) {
                return
            }

            this.editdialog.el.mask('提交数据，请稍候...', 'x-mask-loading');
            var edithide = function() {
                this.editdialog.el.unmask();
                this.editdialog.hide();
                this.refresh.apply(this);
            }.createDelegate(this);
            Ext.lib.Ajax.request(
                'POST',
                this.urlSave,
                {success:edithide,failure:edithide},
                'data=' + encodeURIComponent(Ext.encode(item))
            );
        }.createDelegate(this), this.editdialog);

        // 设置三个tab
        var edittabs = this.editdialog.getTabs();
        edittabs.getTab(0).on("activate", function() {
            this.edityesBtn.show();
        }, this, true);
        edittabs.getTab(1).on("activate", function() {
            this.edityesBtn.show();
        }, this, true);
        edittabs.getTab(2).on("activate", function(){
            this.edityesBtn.hide();
        }, this, true);

        edittabs.getTab(0).setContent(Ext.get("edit-base-content").dom.innerHTML);
        edittabs.getTab(1).setContent(Ext.get("edit-detail-content").dom.innerHTML);
        document.body.removeChild(document.getElementById("edit-base-content"));
        document.body.removeChild(document.getElementById("edit-detail-content"));

        this.editcolumns = Ext.lingo.FormUtils.createAll(this.editMetaData);
        this.editnoBtn = this.editdialog.addButton("取消", this.editdialog.hide, this.editdialog);
    }

    // 超级重要的一个方法，自动生成表头，自动生成form，都是在这里进行的
    , applyElements : function() {
        // 重载父类方法，手工制作form
    }
});


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
            title      : '用户管理',
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
        {id : 'id',       qtip : "ID",   vType : "integer",   allowBlank : true,  defValue : -1},
        {id : 'dept',     qtip : '部门', vType : 'treeField', url : "../dept/getChildren.htm", mapping : "dept.name"},
        {id : 'username', qtip : "帐号", vType : "chn",       allowBlank : false},
        {id : 'password', qtip : '密码', vType : "password",  allowBlank : false},
        {id : 'truename', qtip : '姓名', vType : "chn"},
        {id : 'sex',      qtip : '性别', vType : "radio", values : [{id : '0', name : '男'}, {id : '1', name : '女'}], defValue : '0', renderer : Ext.lingo.FormUtils.renderSex},
        {id : 'birthday', qtip : '生日', vType : "date"},
        {id : 'tel',      qtip : '电话', vType : "alphanum"},
        {id : 'mobile',   qtip : '手机', vType : "alphanum"},
        {id : 'email',    qtip : '邮箱', vType : "email"},
        {id : 'duty',     qtip : '职务', vType : "chn"},
        {id : 'descn',    qtip : "备注", vType : "chn"}
    ];

    // 创建表格
    var lightGrid = new UserGridPanel("lightgrid", {
        metaData      : metaData,
        dialogContent : "content"
    });

    // 渲染表格
    lightGrid.render();

    // ========================================================================
    // ========================================================================
    // 在工具栏上添一个按钮
    lightGrid.toolbar.insertButton(3, {
        icon    : "../widgets/lingo/list-items.gif",
        id      : 'config',
        text    : '选择角色',
        cls     : 'add',
        tooltip : '选择角色',
        handler : selectRole
    });

    // ========================================================================
    // ========================================================================
    // 渲染表格的方法
    function renderRole(value, p, record){
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
    var roleRecord = Ext.data.Record.create([
        {name: "id",         mapping:"id",         type: "int"},
        {name: "name",       mapping:"name",       type: "string"},
        {name: "descn",      mapping:"descn",      type: "string"},
        {name: "authorized", mapping:"authorized", type: "boolean"}
    ]);
    // 配置资源
    var roleStore = new Ext.data.Store({
        proxy      : new Ext.data.HttpProxy({url:'getRoles.htm'}),
        reader     : new Ext.data.JsonReader({},roleRecord),
        remoteSort : false
    });
    var roleColumnModel = new Ext.grid.ColumnModel([{
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
        header    : "角色名称",
        dataIndex : "name",
        sortable  : true,
        width     : 150 ,
        css       : 'white-space:normal;'
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
        renderer  : renderRole
    }]);
    var roleGrid = new Ext.grid.EditorGrid('role-grid', {
        ds            : roleStore,
        cm            : roleColumnModel,
        selModel      : new Ext.grid.RowSelectionModel({singleSelect:false}),
        enableColLock : false,
        loadMask      : false
    });
    // 渲染表格
    roleGrid.render();
    var roleFooter = roleGrid.getView().getFooterPanel(true);
    var rolePagging = new Ext.PagingToolbar(roleFooter, roleStore, {
        pageSize    : 10,
        displayInfo : true,
        displayMsg  : '显示: {0} - {1} 共 {2}',
        emptyMsg    : "没有找到相关数据"
    });
    rolePagging.add('-', {
        pressed       : true,
        enableToggle  : true,
        text          : '授权',
        cls           : '',
        toggleHandler : function(){
            //授权事件
            var mRole = lightGrid.grid.getSelections();
            var mResc = roleGrid.getSelections();
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
                    'ids=' + ids.join(",") + "&userId=" + roleId + "&isAuth=true"
                );
            }
            roleStore.reload();
        }
    }, '-', {
        pressed      : true,
        enableToggle : true,
        text         : '取消',
        cls          : '',
        toggleHandler: function(){
            //取消授权事件
            var mRole = lightGrid.grid.getSelections();
            var mResc = roleGrid.getSelections();
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
                    'ids=' + ids.join(",") + "&userId=" + roleId + "&isAuth=false"
                );
            }
            roleStore.reload();
        }
    });

    function end() {
        Ext.Msg.alert("提示", "操作成功");
        roleStore.reload();
    }

    function selectRole() {
        var m = lightGrid.grid.getSelections();
        if(m.length <= 0) {
            Ext.MessageBox.alert('提示', '请选择需要配置的用户！');
            return;
        }
        // 读取数据需要的参数
        roleStore.on('beforeload', function() {
            roleStore.baseParams = {
                id : lightGrid.grid.getSelections()[0].get('id')
            };
        });
        roleStore.load({params:{
            start : 0,
            limit : 10
        }});
        var aAddInstanceDlg = createNewDialog("role-dlg");
        var layout = aAddInstanceDlg.getLayout();
        layout.beginUpdate();
          layout.add('center', new Ext.ContentPanel('role-inner', {title: '选择角色'}));
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


