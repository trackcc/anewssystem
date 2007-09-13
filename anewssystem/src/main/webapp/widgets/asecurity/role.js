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
Ext.onReady(function(){
    // 新节点的后缀
    var cseed = 0, oseed = 0;
    var myPageSize = 20;
    // 打开提示功能
    Ext.QuickTips.init();
    // 为main-ct添加子无素
    var cview = Ext.DomHelper.append('main-ct',
        {cn:[{id:'main-tb'},{id:'cbody'}]}
    );
    var selectNode;
    var recordTypeRole = Ext.data.Record.create([
        {name: "id", mapping:"id", type: "int"},
        {name: "name", mapping:"name", type: "string"},
        {name: "descn", mapping:"descn", type: "string"}
    ]);
    // 建一个资源数据映射数组
    var recordTypeResc = Ext.data.Record.create([
        {name: "id", mapping:"id", type: "int"},
        {name: "resType", mapping:"resType", type: "string"},
        {name: "name", mapping:"name", type: "string"},
        {name: "resString", mapping:"resString", type: "string"},
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
    // 设置数据仓库，使用DWRProxy，ListRangeReader，recordType
    var dsResc = new Ext.data.Store({
        proxy: new Ext.data.DWRProxy(ResourceHelper.getResourceForRolePage, true),
        reader: new Ext.data.ListRangeReader({
            totalProperty: 'totalCount',
            id: 'id'
        }, recordTypeResc),
        // 远端排序开关
        remoteSort: false
    });
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
        header: "角色名称",
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
    }]);

    var cmResc = new Ext.grid.ColumnModel([{
        // 设置了id值，我们就可以应用自定义样式 (比如 .x-grid-col-topic b { color:#333 })
        id: 'id',
        header: "编号",
        dataIndex: "id",
        width: 80,
        sortable: true,
        renderer: renderNamePlain,
        css: 'white-space:normal;'
    },{
        id: 'name',
        header: "资源名称",
        dataIndex: "name",
        sortable: true,
        width: 150 ,
        css: 'white-space:normal;'
    },{
        id: 'resType',
        header: "资源类型",
        dataIndex: "resType",
        sortable: true,
        width: 80
    },{
        id: 'resString',
        header: "资源地址",
        dataIndex: "resString",
        sortable: true,
        width: 150
    },{
        id: 'descn',
        header: "资源描述",
        dataIndex: "descn",
        sortable: true,
        width: 80
    },{
        id: 'authorized',
        header: "是否授权",
        dataIndex: "authorized",
        sortable: true,
        width: 80,
        renderer:renderOP
    }]);

    // 渲染表格的方法
    function renderOP(value, p, record){
        if(record.data['authorized']==true){
            return String.format("<b><font color=green>已分配</font></b>");
        }else{
            return String.format("<b><font color=red>未分配</font></b>");
        }
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
    // 创建一个表格实例
    var grid = new Ext.grid.EditorGrid('main-tb', {
        ds: dsRole,
        cm: cmRole,
        selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
        enableColLock:false,
        loadMask: false
    });
    var gridResc = new Ext.grid.EditorGrid('roleAuthResc-grid', {
        ds: dsResc,
        cm: cmResc,
        selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
        enableColLock:false,
        loadMask: false
    });
    // 实例化布局
    var layout = new Ext.BorderLayout(document.body, {
        center: {
            title:'角色列表',
            resizeTabs: true,
            margins:{left:5,right:0,bottom:5,top:5}
        }
    }, 'main-ct');
    layout.batchAdd({
       center : {
           el: cview,
           autoScroll:true,
           fitToFrame:true,
           resizeEl:'main-tb'
       }
    });
    // 渲染表格
    grid.render();
    gridResc.render();
    // 取得表格的表脚
    var gridFoot = grid.getView().getFooterPanel(true);
    var gridFootResc = gridResc.getView().getFooterPanel(true);
    // 分页工具条
    var paging = new Ext.PagingToolbar(gridFoot, dsRole, {
        pageSize: myPageSize,
        displayInfo: true,
        displayMsg: '数据: {0} - {1} 共 {2}',
        emptyMsg: "没有找到相关数据"
    });
    var pagingResc = new Ext.PagingToolbar(gridFootResc, dsResc, {
        pageSize: myPageSize,
        displayInfo: true,
        displayMsg: '数据: {0} - {1} 共 {2}',
        emptyMsg: "没有找到相关数据"
    });
    pagingResc.add('-', {
        pressed: true,
        enableToggle:true,
        text: '授权',
        cls: '',
        toggleHandler: function(){
            //授权事件
            var mRole = grid.getSelections();
            var mResc = gridResc.getSelections();
            if(mResc.length<=0){
                Ext.MessageBox.alert('提示', '请选择至少一行纪录进行操作！');
                return;
            }else if(mResc.length==1){
                roleid=mRole[0].get('id');
                rescid=mResc[0].get('id');
                RoleHelper.authResourceForRole(
                    roleid,
                    rescid,
                    false,
                    function(aa){
                        Ext.MessageBox.alert('提示', '授权成功！');
                    });
            }else{
                for(var i = 0, len = mResc.length; i < len; i++){
                    rescid=mResc[i].get('id');
                    roleid=mRole[0].get('id');
                    RoleHelper.authResourceForRole(
                        roleid,
                        rescid,
                        false,
                        function(aa){
                            Ext.MessageBox.alert('提示', len+'角色授权成功！');
                        });
                }
            }
            dsResc.load({params:{start:0, limit:myPageSize,id:mRole[0].get('id')}});
        }
    }, '-', {
        pressed: true,
        enableToggle:true,
        text: '取消',
        cls: '',
        toggleHandler: function(){
            //取消授权事件
            var mRole = grid.getSelections();
            var mResc = gridResc.getSelections();
            console.info(mRole + "," + mRole);
            if(mResc.length<=0){
                Ext.MessageBox.alert('提示', '请选择至少一行纪录进行操作！');
                return;
            }else if(mResc.length==1){
                roleid=mRole[0].get('id');
                rescid=mResc[0].get('id');
                RoleHelper.authResourceForRole(roleid,rescid,true,function(aa){Ext.MessageBox.alert('提示', '授权取消成功！');});
            }else{
                for(var i = 0, len = mResc.length; i < len; i++){
                    rescid=mResc[i].get('id');
                    roleid=mRole[0].get('id');
                    RoleHelper.authResourceForRole(roleid,rescid,true,function(aa){Ext.MessageBox.alert('提示', len+'角色授权取消成功！');});
                }
            }
            dsResc.load({params:{start:0, limit:myPageSize,id:mRole[0].get('id')}});
        }
    });
    // 向表脚加视图按键
    paging.add('-', {
        pressed: true,
        enableToggle:true,
        text: '详细信息',
        // cls: 'x-btn-text-icon details',
        toggleHandler: toggleDetails
    }, '-', {
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
        text: '资源配置',
        cls: '',
        toggleHandler: doAuth
    });
    var gridHead = grid.getView().getHeaderPanel(true);
    var tb = new Ext.Toolbar(gridHead);
    filterButton = new Ext.Toolbar.MenuButton({
        cls: 'x-btn-text-icon',
        text: '请选择',
        tooltip: '请选择...',
        menu: {items: [
            new Ext.menu.CheckItem({ text: '名称', value: 'name', checked: true, group: 'filter', checkHandler: onItemCheck })
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
    // 回车键
    filter.on('keypress', function(e) {
        if(e.getKey() == e.ENTER && this.getValue().length > 0) {
            ds.load({params:{start:0, limit:myPageSize}});
        }
    });
    // 空格键
    filter.on('keyup', function(e) {
        if(e.getKey() == e.BACKSPACE && this.getValue().length === 0) {
            ds.load({params:{start:0, limit:myPageSize}});
        }
    });
    // 更新选择按钮
    function onItemCheck(item, checked) {
        if(checked) {
            filterButton.setText(item.text + ':');
        }
    }
    dsRole.on('beforeload', function() {
        //表格加载前的动作！
    });
    // 为表格加载数据
    dsRole.load({params:{start:0, limit:myPageSize}});
    function toggleDetails(btn, pressed){
        cmRole.getColumnById('name').renderer = pressed ? renderNamePlain : renderName;
        grid.getView().refresh();
    }
    // 新建对话框
    function createNewDialog(dialogName) {
        var thisDialog = new Ext.LayoutDialog(dialogName, {
            modal:false,
            autoTabs:true,
            proxyDrag:true,
            resizable:true,
            width: 650,
            height: 500,
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
        if (!aAddInstanceDlg) {
            aAddInstanceDlg = createNewDialog("a-addInstance-dlg");
            aAddInstanceDlg.addButton('重置', resetForm, aAddInstanceDlg);
            aAddInstanceDlg.addButton('保存', function() {
                // 提交
                if (form_instance_create.isValid()) {
                    form_instance_create.submit({
                        waitMsg:'数据提交中.....',
                        reset: false,
                        failure: function(form_instance_create, action) {
                            var info =
                            Ext.MessageBox.alert('Error Message', action.result.errorInfo);
                        },
                        success: function(form_instance_create, action) {
                            Ext.MessageBox.alert('Confirm', action.result.info);
                            aAddInstanceDlg.hide();
                            dsRole.load({params:{start:0, limit:myPageSize}});
                        }
                    });
                }else{
                    Ext.MessageBox.alert('Errors', '输入的数据有误，请重新输入!');
                }
            });
            var layout = aAddInstanceDlg.getLayout();
            layout.beginUpdate();
            layout.add('center', new Ext.ContentPanel('a-addInstance-inner', {title: 'create account'}));
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
            name_show.setValue(m[0].get("name"));
            descn_show.setValue(m[0].get("descn"));
            aAddInstanceDlg = createNewDialog("a-updateInstance-dlg");
            aAddInstanceDlg.addButton('重置', resetForm, aAddInstanceDlg);
            aAddInstanceDlg.addButton('保存', function() {
                // 提交
                if (form_instance_update.isValid()) {
                    form_instance_update.submit({
                        waitMsg:'数据提交中.....',
                        reset: false,
                        failure: function(form_instance_update, action) {
                            alert(action.result);
                            var info =
                            Ext.MessageBox.alert('提示信息', action.result.errorInfo);
                        },
                        success: function(form_instance_update, action) {
                            Ext.MessageBox.alert('Confirm', action.result.info);
                            aAddInstanceDlg.hide();
                            dsRole.load({params:{start:0, limit:myPageSize}});
                        }
                    });
                }else{
                    Ext.MessageBox.alert('提示', '输入的数据有误，请重新输入!');
                }
            });
            var layout = aAddInstanceDlg.getLayout();
            layout.beginUpdate();
            layout.add('center', new Ext.ContentPanel('a-updateInstance-inner', {title: '角色更新'}));
            layout.endUpdate();
            aAddInstanceDlg.show();
        };
    }
    function doAuth(){
        var m = grid.getSelections();
        if(m.length<=0){
            Ext.MessageBox.alert('提示', '请选择需要配置的角色！');
            return;
        }
        dsResc.load({params:{start:0, limit:myPageSize,id:m[0].get('id')}});
        var aAddInstanceDlg = createNewDialog("userAuthRole-dlg");
        var layout = aAddInstanceDlg.getLayout();
        layout.beginUpdate();
        layout.add('center', new Ext.ContentPanel('userAuthRole-inner', {title: '角色授权'}));
        layout.endUpdate();
        aAddInstanceDlg.show();
    }
    // 删除按钮方法
    function doDel(){
        var m = grid.getSelections();
        if(m.length > 0) {
            Ext.MessageBox.confirm('提示', '确定要删除吗?' , doDel2);
        } else {
            Ext.MessageBox.alert('提示', '请选择要删除的数据行!');
        }
    }
    // 删除回调方法
    function doDel2(btn) {
        if(btn=='yes'){
            var m = grid.getSelections();
            var roleid =new Array();
            for(var i = 0, len = m.length; i < len; i++){
                m[i].get("id");
                roleid[i]=m[i].get("id");
                dsRole.remove(m[i]);//从表格中删除
            }
            Ext.Ajax.request({
                url:'remove.htm?id='+roleid,
                success:function(){
                    Ext.MessageBox.alert('提示', '删除成功！');
                    dsRole.load({params:{start:0, limit:myPageSize}});
                },
                failure:function(){Ext.MessageBox.alert('提示', '删除失败！');}
            });
        }else{
            return;
        }
    }
    // 提交数据校验功能
    Ext.form.Field.prototype.msgTarget = 'side';
    Ext.form.Field.prototype.height = 20;
    Ext.form.Field.prototype.fieldClass = 'text-field-default';
    Ext.form.Field.prototype.focusClass = 'text-field-focus';
    Ext.form.Field.prototype.invalidClass = 'text-field-invalid';
    // 新建表单
    var name_tf = new Ext.form.TextField({
        fieldLabel: '角色名称',
        name: 'name',
        allowBlank:false
    });
    var descn_tf = new Ext.form.TextField({
        fieldLabel: '角色描述',
        name: 'descn',
        allowBlank:true
    });
    var form_instance_create = new Ext.form.Form({
        labelAlign: 'right',
        url:'insert.htm'
    })
    form_instance_create.column({width: 430, labelWidth:120, style:'margin-left:8px;margin-top:8px'});
    form_instance_create.fieldset({
        id:'addresc', legend:'添加角色----请填写数据:'},
        name_tf,
        descn_tf
    );
    form_instance_create.applyIfToFields({width:255});
    form_instance_create.render('a-addInstance-form');
    form_instance_create.end();
    resetForm = function() {
        name_tf.setValue('');
        descn_tf.setValue('');
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
        fieldLabel: '角色名称',
        name: 'name',
        allowBlank:false
    });
    var descn_show = new Ext.form.TextField({
        fieldLabel: '角色描述',
        name: 'descn',
        allowBlank:true
    });
    var form_instance_update = new Ext.form.Form({
        labelAlign: 'right',
        url:'update.htm'
    });
    form_instance_update.column({width: 430, labelWidth:120, style:'margin-left:8px;margin-top:8px'});
    form_instance_update.fieldset({
        id:'updateResc', legend:'更新角色----请填写数据:'},
        name_show,
        descn_show,
        id_show
    );
    form_instance_update.applyIfToFields({width:255});
    form_instance_update.render('a-updateInstance-form');
    form_instance_update.end();
});
