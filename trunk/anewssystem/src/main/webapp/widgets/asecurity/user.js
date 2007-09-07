/*
 * Ext JS Library 1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author Lingo
 * @since 2007-09-06
 * http://code.google.com/p/anewssystem/
 */
function checkRoleForUser(id){
    UserHelper.getRolesForUser(id,function(roles){
        if(id==null||roles==""||!roles){
            Ext.MessageBox.alert('提示:', '该用户还没角色授权!');
        }
        Ext.MessageBox.alert('提示:', '已授权的角色:' + roles);
    });
}
Ext.onReady(function(){
    // 新节点后缀
    var cseed = 0, oseed = 0;
    var myPageSize = 20;
    // 快速提示
    Ext.QuickTips.init();
    // main-ct添加子无素
    var cview = Ext.DomHelper.append('main-ct',
        {cn:[{id:'main-tb'},{id:'cbody'}]}
    );
    var selectNode;
    var deptId = 0;
    var userId = 0;
    //建列表======================================================================================
    // 建一个用户数据映射数组
    var recordType = Ext.data.Record.create([
        {name: "id", mapping:"id", type: "int"},
        {name: "password", mapping:"password", type: "string"},
        {name: "username", mapping:"username", type: "string"},
        {name: "descn", mapping:"descn", type: "string"},
        {name: "status", mapping:"status", type: "int"},
        {name: "email", mapping:"email", type: 'string'},
        {name: "dept", mapping:"dept.name", type: 'string'},
        {name: "roles", mapping:"roles", type: ''}
    ]);
    // 建一个角色数据映射数组
    var recordTypeRole = Ext.data.Record.create([
        {name: "id", mapping:"id", type: "int"},
        {name: "name", mapping:"name", type: "string"},
        {name: "descn", mapping:"descn", type: "string"},
        {name: "authorized", mapping:"authorized", type: "boolean"}
    ]);
    //设置数据仓库，使用DWRProxy，ListRangeReader，recordType
    var dsRole = new Ext.data.Store({
        proxy: new Ext.data.DWRProxy(RoleHelper.getRoleForUserPage, true),
        reader: new Ext.data.ListRangeReader({
            totalProperty: 'totalCount',
            id: 'id'
        }, recordTypeRole),
        // 远端排序开关
        remoteSort: false
    });
    //设置数据仓库，使用DWRProxy，ListRangeReader，recordType
    var ds = new Ext.data.Store({
        proxy: new Ext.data.DWRProxy(UserHelper.pagedQuery, true),
        reader: new Ext.data.ListRangeReader({
            totalProperty: 'totalCount',
            id: 'id'
        }, recordType),
        // 远端排序开关
        remoteSort: false
    });

    //创建表格头格式
    var cm = new Ext.grid.ColumnModel([{
        // 设置了id值，我们就可以应用自定义样式 (比如 .x-grid-col-topic b { color:#333 })
        id: 'id',
        header: "编号",
        dataIndex: "id",
        width: 100,
        sortable: true,
        renderer: renderNamePlain,
        css: 'white-space:normal;'
    },{
        id: 'username',
        header: "用户名",
        dataIndex: "username",
        sortable: true,
        width: 150 ,
        css: 'white-space:normal;'
    },{
        id: 'email',
        header: "电子邮件",
        dataIndex: "email",
        sortable: true,
        width: 150
    },{
        id: 'dept',
        header: "所属部门",
        dataIndex: "dept",
        sortable: true,
        width: 150
    },{
        id: 'op',
        header: "所属角色",
        width: 150,
        renderer:renderOP
    }]);
    //创建Role表格头格式
    var cmRole = new Ext.grid.ColumnModel([{
        // 设置了id值，我们就可以应用自定义样式 (比如 .x-grid-col-topic b { color:#333 })
        id: 'id',
        header: "编号",
        dataIndex: "id",
        width: 100,
        sortable: true,
        css: 'white-space:normal;'
    },{
        id: 'name',
        header: "角色",
        dataIndex: "name",
        sortable: true,
        width: 150 ,
        css: 'white-space:normal;'
    },{
        id: 'descn',
        header: "描述",
        dataIndex: "descn",
        sortable: true,
        width: 150 ,
        css: 'white-space:normal;'
    },{
        id: 'authorized',
        header: "是否授权",
        dataIndex: "authorized",
        sortable: true,
        width: 80,
        renderer:renderAuthorized
    }]);

    function renderAuthorized(value, p, record){
        if(record.data['authorized']==true){
            return String.format("<b><font color=green>已分配</font></b>");
        }else{
            return String.format("<b><font color=red>未分配</font></b>");
        }
    }
    //渲染表格的方法
    function renderHaveRole(value, p, record){
        var role=checkRoleForUser(record.data['id']);
        return String.format(role);
       // return String.format('<a href=# onclick="checkRoleForUser({0})">查看</a>',record.data['id']);
    }

    function renderOP(value, p, record){
        return String.format('<a href=# onclick="checkRoleForUser({0})">查看</a>',record.data['id']);
    }
    function renderName(value, p, record){
        return String.format('<b>{0}</b><br>{1}', value, record.data['descn']==null?"":record.data['descn']);
    }
    function renderNamePlain(value){
        return String.format('{0}', value);
    }
    function renderaddtime(value){
        return String.format('<b>{0}</b>',  typeof(value)=='string'?"":value.format('Y-m-d'));
    }
    // 新建一个表格实例
    var grid = new Ext.grid.EditorGrid('main-tb', {
        ds: ds,
        cm: cm,
        //selModel: new Ext.grid.CellSelectionModel(),
        selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
        enableColLock:false
    });

    var grid1 = new Ext.grid.Grid('userAuthRole-grid', {
        ds: dsRole,
        cm: cmRole,
        selModel: new Ext.grid.RowSelectionModel({singleSelect:true}),
        enableColLock:false,
        loadMask: false
    });
    //================================================================================
    // 实例化布局
    var layout = new Ext.BorderLayout(document.body, {
        west: {
            split:true,
            initialSize: 200,
            minSize: 175,
            maxSize: 400,
            titlebar: true,
            margins:{left:5,right:0,bottom:5,top:5}
        }, center: {
            title:'用户列表',
            resizeTabs: true,
            margins:{left:5,right:0,bottom:5,top:5}
        }
    }, 'main-ct');
    layout.batchAdd({
        west: {
            id: 'source-files',
            autoCreate:true,
            title:'部门树',
            autoScroll:true,
            fitToFrame:true
        }, center : {
            el: cview,
            autoScroll:true,
            fitToFrame:true,
            resizeEl:'main-tb'
        }
    });
    grid.on('cellcontextmenu', gridPrepareCtx);
    //==============================================================
    //渲染表格
    grid1.render();
    grid.render();
    //取得表格的表脚
    var gridFoot = grid.getView().getFooterPanel(true);
    var gridFootRole = grid1.getView().getFooterPanel(true);
    // 分页工具栏
    var paging = new Ext.PagingToolbar(gridFoot, ds, {
        pageSize: myPageSize,
        displayInfo: true,
        displayMsg: '数据: {0} - {1} 共 {2}',
        emptyMsg: "没有找到相关数据"
    });
    // 分页工具栏
    var pagingRole = new Ext.PagingToolbar(gridFootRole, dsRole, {
        pageSize: myPageSize,
        displayInfo: true,
        displayMsg: '数据: {0} - {1} 共 {2}',
        emptyMsg: "没有找到相关数据"
    });
    // 向表脚加视图按键
    pagingRole.add('-', {
        pressed: true,
        enableToggle:true,
        text: '授权',
        toggleHandler: function(){
            //授权事件
            var mRole = grid1.getSelections();
            var mUser = grid.getSelections();
            if(mRole.length<=0){
                Ext.MessageBox.alert('提示', '请选择至少一个角色操作！');
                return;
            }else if(mUser.length==1){
                userid=mUser[0].get('id');
                roleid=mRole[0].get('id');
                UserHelper.authRoleForUser(userid,roleid,false,function(aa){Ext.MessageBox.alert('提示', '授权成功！');});
            }else{
                for(var i = 0, len = mUser.length; i < len; i++){
                    userid=mUser[i].get('id');
                    roleid=mRole[0].get('id');
                    UserHelper.authRoleForUser(userid,roleid,false,function(aa){Ext.MessageBox.alert('提示', len+'用户授权成功！');});
                }
            }
            dsRole.load({params:{start:0, limit:myPageSize}});
        }
    }, '-', {
        pressed: true,
        enableToggle:true,
        text: '取消授权',
        toggleHandler: function(){
            //授权事件
            var m = grid1.getSelections();
            var m1 = grid.getSelections();
            if(m.length<=0){
                Ext.MessageBox.alert('提示', '请选择至少一个角色操作！');
                return;
            }else if(m1.length==1){
                userid=m1[0].get('id');
                roleid=m[0].get('id');
                UserHelper.authRoleForUser(userid,roleid,true,function(aa){Ext.MessageBox.alert('提示', '授权取消成功！');});
            }else{
                for(var i = 0, len = m1.length; i < len; i++){
                    userid=m1[i].get('id');
                    roleid=m[0].get('id');
                    UserHelper.authRoleForUser(userid,roleid,true,function(aa){Ext.MessageBox.alert('提示', len+'用户授权取消成功！');});
                }
            }
            dsRole.load({params:{start:0, limit:myPageSize,id:m1[0].get('id')}});
        }
    });

    // 向表脚加视图按键
    paging.add('-', {
        pressed: true,
        enableToggle:true,
        text: '详细信息',
        // cls: 'x-btn-text-icon details',
        toggleHandler: toggleDetails
    });

    paging.add('-', {
        pressed: true,
        enableToggle:true,
        text: '新增',
        cls: '',
        toggleHandler: doAdd
    }, '-', {
        pressed: true,
        enableToggle:true,
        text: '修改',
        cls: '',
        toggleHandler: doSave
    }, '-', {
        pressed: true,
        enableToggle:true,
        text: '删除',
        cls: '',
        toggleHandler: doDel
    }, '-', {
        pressed: true,
        enableToggle:true,
        text: '角色授权',
        cls: '',
        toggleHandler: doAuth
    });

    var gridHead = grid.getView().getHeaderPanel(true);
    var tb = new Ext.Toolbar(gridHead);
    filterButton = new Ext.Toolbar.MenuButton({
        cls: 'x-btn-text-icon',
        text: '请选择',
        tooltip: '选择搜索的字段',
        menu: {items: [
            new Ext.menu.CheckItem({ text: '名称', value: 'username', checked: true, group: 'filter', checkHandler: onItemCheck })
        ]},
        minWidth: 105
    });
    tb.add(filterButton);
    // 过滤器
    var filter = Ext.get(tb.addDom({
         tag: 'input',
         type: 'text',
         size: '30',
         value: '',
         style: 'background: #F0F0F9;'
    }).el);

    // 回车
    filter.on('keypress', function(e) {
      if(e.getKey() == e.ENTER && this.getValue().length > 0) {
          ds.load({params:{start:0, limit:myPageSize}});
      }
    });

    // 空格
    filter.on('keyup', function(e) {
      if(e.getKey() == e.BACKSPACE && this.getValue().length === 0) {
          ds.load({params:{start:0, limit:myPageSize}});
      }
    });

    // 更新搜索文字
    function onItemCheck(item, checked) {
        if(checked) {
            filterButton.setText(item.text + ':');
        }
    }
/*
    ds.on('beforeload', function() {
        ds.baseParams = { deptId: deptId };
    });
*/
    // 为表格加载数据
    ds.load({params:{start:0, limit:myPageSize}});

    function toggleDetails(btn, pressed){
        cm.getColumnById('username').renderer = pressed ? renderNamePlain : renderName;
        grid.getView().refresh();
    }
    //当点树接点将在列表中打开该部门下的下一级部门
    function createNewDialog(dialogName) {
        var thisDialog = new Ext.LayoutDialog(dialogName, {
            modal:false,
            autoTabs:true,
            proxyDrag:true,
            resizable:true,
            width: 480,
            height: 302,
            shadow:true,
            center: {
                autoScroll: true,
                tabPosition: 'top',
                closeOnTab: true,
                alwaysShowTabs: false
            }
        });
        thisDialog.addKeyListener(27, thisDialog.hide, thisDialog);
        thisDialog.addButton('关闭', function() {thisDialog.hide();});
        return thisDialog;
    };
    //----------------------Begin----------------添加表单动作（提交）
    function doAdd(){
        var aAddInstanceDlg;
        if(!selectNode){
            Ext.MessageBox.alert('提示：', '请先选择部门！');
        }
        if(selectNode.leaf==false){
            Ext.MessageBox.alert('提示：', '不能在当前部门下创建用户！');
            return;
        }
        if (!aAddInstanceDlg) {
            aAddInstanceDlg = createNewDialog("a-addInstance-dlg");
            aAddInstanceDlg.addButton('重置', resetForm, aAddInstanceDlg);
            aAddInstanceDlg.addButton('保存', function() {
                deptId_tf.setValue(deptId);
                // 数据校验
                if (deptId_tf.getValue()==''||deptId_tf.getValue()==0) {
                    Ext.MessageBox.alert('提示：', '请在部门树中选择你的所属部门!');
                    return;
                }
                if (password_tf.getValue()!=cPassword_tf.getValue()) {
                    password_tf.markInvalid("2次输入的密码不正确!");
                    password_tf.focus();
                    return;
                }
                //设置所属部门的ID
                if (form_instance_create.isValid()) {
                    form_instance_create.submit({
                        waitMsg:'数据提交中.....',
                        reset: false,
                        failure: function(form_instance_create, action) {
                            var info =
                            Ext.MessageBox.alert('错误信息', action.result.errorInfo);
                        },
                        success: function(form_instance_create, action) {
                            Ext.MessageBox.alert('成功', action.result.info);
                            aAddInstanceDlg.hide();
                            ds.load({params:{start:0, limit:myPageSize}});
                        }
                    });
                }else{
                    Ext.MessageBox.alert('提示', '输入的数据有误，请重新输入!');
                }
            });

            var layout = aAddInstanceDlg.getLayout();
            layout.beginUpdate();
            layout.add('center', new Ext.ContentPanel('a-addInstance-inner', {title: '添加用户'}));
            layout.endUpdate();

            aAddInstanceDlg.show();
        };

    }
    //End-----------------------------------------------
    //----------------------Begin----------------修改表单动作（提交）
    function doSave(){
        var aAddInstanceDlg;
        if (!aAddInstanceDlg) {
            var m = grid.getSelections();
            if(m.length!=1){
                Ext.MessageBox.alert('Error', '只允许选择单行纪录进行修改！');
                return;
            }
            id_show.setValue(m[0].get("id"));
            name_show.setValue(m[0].get("username"));
            email_show.setValue(m[0].get("email"));
            descn_show.setValue(m[0].get("descn"));
            deptId_show.setValue(selectNode.id);
            dept_show.setValue(selectNode.text);
            password_show.setValue("");
            cPassword_show.setValue("");
            aAddInstanceDlg = createNewDialog("a-updateInstance-dlg");
            aAddInstanceDlg.addButton('重置', resetForm, aAddInstanceDlg);
            aAddInstanceDlg.addButton('保存', function() {
                if(password_show.getValue()==""){
                    Ext.MessageBox.alert('提示', '请输入密码！');
                }
                if (password_show.getValue()!=cPassword_show.getValue()) {
                    password_show.markInvalid("2次输入的密码不正确!");
                    password_show.focus();
                    return;
                }
                if(selectNode.leaf!=true){
                    Ext.MessageBox.alert('提示', '用户不能创建在该部门下！');
                    return;
                }
                // 向服务器提交数据
                if (form_instance_update.isValid()) {
                    form_instance_update.submit({
                        waitMsg:'数据提交中.....',
                        reset: false,
                        failure: function(form_instance_update, action) {
                            alert(action.result);
                            var info =
                            Ext.MessageBox.alert('Error Message', action.result.errorInfo);
                        },
                        success: function(form_instance_update, action) {
                            Ext.MessageBox.alert('Confirm', action.result.info);
                            aAddInstanceDlg.hide();
                            ds.load({params:{start:0, limit:myPageSize}});
                        }
                    });
                }else{
                    Ext.MessageBox.alert('提示', '输入的数据有误，请重新输入!');
                }
            });
            var layout = aAddInstanceDlg.getLayout();
            layout.beginUpdate();
            layout.add('center', new Ext.ContentPanel('a-updateInstance-inner', {title: '用户更新'}));
            layout.endUpdate();
            aAddInstanceDlg.show();
        };
    }
    function doAuth(){
        var m = grid.getSelections();
            if(m.length<=0){
                Ext.MessageBox.alert('提示', '请选择需要授权的用户！');
                return;
            }
        dsRole.load({params:{start:0, limit:myPageSize,id:m[0].get('id')}});
        var aAddInstanceDlg = createNewDialog("userAuthRole-dlg");
        var layout = aAddInstanceDlg.getLayout();
        layout.beginUpdate();
        layout.add('center', new Ext.ContentPanel('userAuthRole-inner', {title: '用户授权'}));
        layout.endUpdate();
        aAddInstanceDlg.show();
    }
    //删除按钮方法
    function doDel(){
        var m = grid.getSelections();
        if(m.length > 0) {
            Ext.MessageBox.confirm('提示', '确定要删除吗?' , doDel2);
        } else {
            Ext.MessageBox.alert('提示', '请选择要删除的数据行!');
        }
    }
    //删除回调方法
    function doDel2(btn) {
        if(btn=='yes'){
            var m = grid.getSelections();
            var userid =new Array();
            for(var i = 0, len = m.length; i < len; i++){
                m[i].get("id");
                userid[i]=m[i].get("id");
                ds.remove(m[i]);
            }
            Ext.Ajax.request({
                url:'remove.htm?id='+userid,
                success:function(){
                    Ext.MessageBox.alert('提示', '删除成功！');
                    ds.load({params:{start:0, limit:myPageSize}});
                },
                failure:function(){Ext.MessageBox.alert('提示', '删除失败！');}
            });
        }else{
            return;
        }
    }
    // 提供验证功能
    Ext.form.Field.prototype.msgTarget = 'side';
    Ext.form.Field.prototype.height = 20;
    Ext.form.Field.prototype.fieldClass = 'text-field-default';
    Ext.form.Field.prototype.focusClass = 'text-field-focus';
    Ext.form.Field.prototype.invalidClass = 'text-field-invalid';
    // 添加表单
    var name_tf = new Ext.form.TextField({
        fieldLabel: '用户名',
        name: 'username',
        allowBlank:false
    });
    var password_tf = new Ext.form.TextField({
        fieldLabel: '密码',
        name: 'password',
        inputType: 'password',
        allowBlank:false
    });
    var cPassword_tf = new Ext.form.TextField({
        fieldLabel: '密码确认',
        name: 'cPassword',
        inputType: 'password',
        allowBlank:false
    });
    var deptId_tf = new Ext.form.TextField({
        name: 'deptId',
        inputType:'hidden',
        labelStyle:'display: none'
    });
    var descn_tf = new Ext.form.TextField({
        fieldLabel: '描述',
        name: 'descn',
        allowBlank:true
    });
    var email_tf = new Ext.form.TextField({
        fieldLabel: '电子邮件',
        name: 'email',
        allowBlank:true
    });
    var form_instance_create = new Ext.form.Form({
        labelAlign: 'right',
        url:'insert.htm'
    });
    form_instance_create.column({width: 430, labelWidth:120, style:'margin-left:8px;margin-top:8px'});
    form_instance_create.fieldset({
        id:'adduser', legend:'添加用户----请填写数据:'},
        name_tf,
        password_tf,
        cPassword_tf,
        descn_tf,
        email_tf,
        deptId_tf
    );
    form_instance_create.applyIfToFields({width:255});
    form_instance_create.render('a-addInstance-form');
    form_instance_create.end();
    resetForm = function() {
        name_tf.setValue('');
        email_tf.setValue('');
        descn_tf.setValue('');
        deptId_tf.setValue('');
        password_tf.setValue('');
        cPassword_tf.setValue('');
    };
    // 更新表单
    var id_show = new Ext.form.TextField({
        // labelStyle: 'display: none',
        inputType: 'hidden',
        name: 'id',
        readOnly: true,
        allowBlank:false,
        labelStyle:'display: none'
    });
    var name_show = new Ext.form.TextField({
        fieldLabel: '用户名',
        name: 'username',
        allowBlank:false
    });
    var password_show = new Ext.form.TextField({
        fieldLabel: '密码',
        name: 'password',
        inputType: 'password',
        allowBlank:false
    });
    var cPassword_show = new Ext.form.TextField({
        fieldLabel: '密码确认',
        name: 'cPassword',
        inputType: 'password',
        allowBlank:false
    });
    var descn_show = new Ext.form.TextField({
        fieldLabel: '描述',
        name: 'descn',
        allowBlank:true
    });
    var email_show = new Ext.form.TextField({
        fieldLabel: '电子邮件',
        name: 'email',
        allowBlank:true
    });
    var dept_show = new Ext.form.TextField({
        fieldLabel: '所属部门',
        name: 'orgText',
        allowBlank:false
    });
    var deptId_show = new Ext.form.TextField({
        inputType: 'hidden',
        name: 'deptId',
        readOnly: true,
        allowBlank:false,
        labelStyle:'display: none'
    });
    var form_instance_update = new Ext.form.Form({
        labelAlign: 'right',
        url:'update.htm'
    });
    form_instance_update.column({width: 430, labelWidth:120, style:'margin-left:8px;margin-top:8px'});
    form_instance_update.fieldset({
        id:'updateuser', legend:'更新用户----请填写数据:'},
        name_show,
        password_show,
        cPassword_show,
        descn_show,
        email_show,
        dept_show,
        deptId_show,
        id_show
    );
    form_instance_update.applyIfToFields({width:255});
    form_instance_update.render('a-updateInstance-form');
    form_instance_update.end();
    //-------------------------表格右键菜单BEGAIN
    var gridCtxMenu = new Ext.menu.Menu({
        id:'gridCtx',
        items: [{
            id:'addDept',
            handler:gridAddDept,
            cls:'collapse-all',
            text:'添加部门'
        },{
            id:'addUser',
            handler:gridAddUser,
            cls:'collapse-all',
            text:'添加用户'
        },'-',{
            id:'remove',
            cls:'remove-mi',
            text: '移除'
        }]
    });
    function gridPrepareCtx(grid, rowIndex,cellIndex,e){
        ctxMenu.items.get('remove')[node.attributes.allowDelete ? 'enable' : 'disable']();
        gridCtxMenu.showAt(e.getXY());
    }
    function gridAddDept(node){
        gridCtxMenu.hide();
        setTimeout(function(){
            node.eachChild(function(n){
               //n.collapse(false, false);
           });
        }, 10);
    }
    function gridAddUser(node){
        gridCtxMenu.hide();
        setTimeout(function(){
            node.eachChild(function(n){
              // n.expand(false, false);
            });
        }, 10);
    }
    //-------------------------表格右键菜单END
    //=============================================================
    //================创建用户树============================
    var userTreeloader = new Ext.tree.TreeLoader({
        dataUrl:'#user.do?method=rendererUserTree'
    });
    var Tree = Ext.tree;
    var tree = new Tree.TreePanel('source-files', {
        animate:true,
        loader: new Tree.TreeLoader({
            dataUrl:'../dept/getAllTree.htm'
        }),
        enableDD:true,
        containerScroll: true,
        dropConfig: {appendOnly:true}
    });
    tree.on('move',function(tree,thisnode,oldParent,newParent,index){
        //操作
    });
    // 树的根节点
    var root = new Tree.AsyncTreeNode({
        text: '临远研发中心',
        draggable:false,
        id:'1',
        cls:'folder',
        leaf:false
    });
    tree.setRootNode(root);
    //tree.on("expand",reloadds);
    tree.on('contextmenu', prepareCtx);
    tree.on('click',function(node,e){
        selectNode = node;
    });
    tree.on('click',reloadds);
    // 渲染树
    tree.render();
    root.expand();
    function reloadds(node,e){
        deptId=node.id;
        deptId_show.setValue(selectNode.id);
        dept_show.setValue(selectNode.text);
        ds.load({params:{start:0, limit:myPageSize,deptId:node.id}});
    }
    //---------------树右键菜单BEGAIN
    var ctxMenu = new Ext.menu.Menu({
        id:'copyCtx',
        items: [{
            id:'expand',
            handler:expandAll,
            cls:'expand-all',
            text:'展开'
        },{
            id:'collapse',
            handler:collapseAll,
            cls:'collapse-all',
            text:'收拢'
        },{
            id:'addDept',
            handler:collapseAll,
            cls:'collapse-all',
            text:'添加部门'
        },{
            id:'addUser',
            handler:collapseAll,
            cls:'collapse-all',
            text:'添加用户'
        },'-',{
            id:'remove',
            cls:'remove-mi',
            text: '删除'
        }]
    });
    function prepareCtx(node, e){
        node.select();
        // ctxMenu.items.get('remove')[node.attributes.allowDelete ? 'enable' : 'disable']();
        ctxMenu.items.get('addUser')[node.isLeaf ? 'enable' : 'disable']();
        ctxMenu.items.get('addDept')[node.isLeaf ? 'disable' : 'enable']();
        ctxMenu.showAt(e.getXY());
    }
    function collapseAll(node){
        ctxMenu.hide();
        setTimeout(function(){
            node.eachChild(function(n){
               n.collapse(false, false);
           });
        }, 10);
    }
    function expandAll(node){
        ctxMenu.hide();
        setTimeout(function(){
            node.eachChild(function(n){
                n.expand(false, false);
            });
        }, 10);
    }
    //---------------树右键菜单END

    ds.on('beforeload', function() {
        ds.baseParams = {
            deptId: deptId
        };
    });
    dsRole.on('beforeload', function() {
        dsRole.baseParams = {
            userId:grid.getSelections()[0].id
        };
    });
});
