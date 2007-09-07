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
    // 打开提示功能
    Ext.QuickTips.init();

    // ====================================================
    // 开始构造树形
    // ====================================================
    var treeloader = new Ext.tree.TreeLoader({dataUrl:'getAllTree.htm'});
    var tree = new Ext.tree.TreePanel('main', {
        animate:false,
        containerScroll: true,
        enableDD:true,
        lines: true,
        loader: treeloader
    });
    // 不使用自动排序
    //new Ext.tree.TreeSorter(tree, {folderSort:true});
    tree.el.addKeyListener(Ext.EventObject.DELETE, removeNode);

    // ====================================================
    // 工具栏
    // ====================================================
    var tb = new Ext.Toolbar(tree.el.createChild({tag:'div'}));
    tb.add({
        text: '新增子分类',
        icon: '../widgets/images/list-items.gif',
        cls: 'x-btn-text-icon album-btn',
        tooltip: '添加选中节点的下级分类',
        handler: createChild
    }, {
        text: '新增兄弟分类',
        icon: '../widgets/images/list-items.gif',
        cls: 'x-btn-text-icon album-btn',
        tooltip: '添加选中节点的同级分类',
        handler: createBrother
    }, {
        text: '修改分类',
        icon: '../widgets/images/list-items.gif',
        cls: 'x-btn-text-icon album-btn',
        tooltip: '修改选中分类',
        handler: updateNode
    }, {
        text: '删除分类',
        icon: '../widgets/images/list-items.gif',
        cls: 'x-btn-text-icon album-btn',
        tooltip:'删除一个分类',
        handler:removeNode
    }, '-', {
        text: '排序',
        icon: '../widgets/images/list-items.gif',
        cls: 'x-btn-text-icon album-btn',
        tooltip:'保存排序结果',
        handler:save
    }, '-', {
        text: '展开',
        icon: '../widgets/images/list-items.gif',
        cls: 'x-btn-text-icon album-btn',
        tooltip:'展开所有分类',
        handler:expandAll
    }, {
        text: '关闭',
        icon: '../widgets/images/list-items.gif',
        cls: 'x-btn-text-icon album-btn',
        tooltip:'关闭所有分类',
        handler:collapseAll
    }, {
        text: '刷新',
        icon: '../widgets/images/list-items.gif',
        cls: 'x-btn-text-icon album-btn',
        tooltip:'刷新所有节点',
        handler:refresh
    });

    // ====================================================
    // 工具栏操作函数
    // ====================================================
    function createChild() {
        var sm = tree.getSelectionModel();
        var n = sm.getSelectedNode();
        if (!n) {
            n = tree.getRootNode();
        } else {
            n.expand(false, false);
        }
        createNode(n);
    }
    function createBrother() {
        var n = tree.getSelectionModel().getSelectedNode();
        if (!n) {
            Ext.Msg.alert('提示', "请选择一个节点");
        } else if (n == tree.getRootNode()) {
            Ext.Msg.alert('提示', "不能为根节点增加兄弟节点");
        } else {
            createNode(n.parentNode);
        }
    }
    function createNode(n) {
        var node = n.appendChild(new Ext.tree.TreeNode({
            id:-1,
            text:'请输入分类名',
            cls:'album-node',
            allowDrag:true,
            allowDelete:true,
            allowEdit:true,
            allowChildren:true
        }));
        tree.getSelectionModel().select(node);
        setTimeout(function(){
            ge.editNode = node;
            ge.startEdit(node.ui.textNode);
        }, 10);
    }
    function updateNode() {
        var n = tree.getSelectionModel().getSelectedNode();
        if (!n) {
            Ext.Msg.alert('提示', "请选择一个节点");
        } else if (n == tree.getRootNode()) {
            Ext.Msg.alert('提示', "不能为根节点增加兄弟节点");
        } else {
            setTimeout(function(){
                ge.editNode = n;
                ge.startEdit(n.ui.textNode);
            }, 10);
        }
    }
    function removeNode() {
        var sm = tree.getSelectionModel();
        var n = sm.getSelectedNode();
        if(n && n.attributes.allowDelete){
            tree.getSelectionModel().selectPrevious();
            tree.el.mask('正在与服务器交换数据...', 'x-mask-loading');
            var hide = tree.el.unmask.createDelegate(tree.el);
            Ext.lib.Ajax.request(
                'POST',
                'removeTree.htm',
                {success:hide,failure:hide},
                'id='+n.id
            );
            n.parentNode.removeChild(n);
        }
    }
    function appendNode(node, array) {
        if (!node || node.childNodes.length < 1) {
            return;
        }
        for (var i = 0; i < node.childNodes.length; i++) {
            var child = node.childNodes[i];
            array.push({id:child.id,parentId:child.parentNode.id});
            appendNode(child, array);
        }
    }
    function save() {
        // 向数据库发送一个json数组，保存排序信息
        tree.el.mask('正在与服务器交换数据...', 'x-mask-loading');
        var hide = tree.el.unmask.createDelegate(tree.el);
        var ch = [];
        appendNode(root, ch);
        Ext.lib.Ajax.request(
            'POST',
            'sortTree.htm',
            {success:hide,failure:hide},
            'data='+encodeURIComponent(Ext.encode(ch))
        );
    }
    function collapseAll(){
        ctxMenu.hide();
        setTimeout(function(){
            root.eachChild(function(n){
               n.collapse(true, false);
            });
        }, 10);
    }
    function expandAll(){
        ctxMenu.hide();
        setTimeout(function(){
            root.eachChild(function(n){
               n.expand(false, false);
            });
        }, 10);
    }
    function refresh() {
        tree.root.reload();
        tree.root.expand(true, false);
    }

    // ====================================================
    // 树节点的即时编辑器
    // ====================================================
    var ge = new Ext.tree.TreeEditor(tree, {
        allowBlank:false,
        blankText:'请添写名称',
        selectOnFocus:true
    });
    ge.on('beforestartedit', function(){
        var node = ge.editNode;
        if(!node.attributes.allowEdit){
            return false;
        } else {
            node.attributes.oldText = node.text;
        }
    });
    ge.on('complete', function() {
        var node = ge.editNode;
        // 如果节点没有改变，就向服务器发送修改信息
        if (node.attributes.oldText == node.text) {
            node.attributes.oldText = null;
            return true;
        }
        var item = {
            id: node.id,
            text: node.text,
            parentId: node.parentNode.id
        };

        tree.el.mask('正在与服务器交换数据...', 'x-mask-loading');
        var hide = tree.el.unmask.createDelegate(tree.el);
        var doSuccess = function(responseObject) {
            eval("var o = " + responseObject.responseText + ";");
            ge.editNode.id = o.id;
            hide();
        };
        Ext.lib.Ajax.request(
            'POST',
            'insertTree.htm',
            {success:doSuccess,failure:hide},
            'data='+encodeURIComponent(Ext.encode(item))
        );
    });

    // ====================================================
    // 树型的根节点
    // ====================================================
    var root = new Ext.tree.AsyncTreeNode({
        text: '部门',
        draggable:true,
        id:-1
    });
    tree.setRootNode(root);
    tree.render();
    // true说明展开所有节点，false说明不使用动画
    root.expand(true, false);

    // ====================================================
    // 弹出对话框
    // ====================================================
    function createNewDialog(dialogName) {
        var thisDialog = new Ext.LayoutDialog(dialogName, {
            modal:true,
            autoTabs:true,
            proxyDrag:true,
            resizable:false,
            width: 410,
            height: 300,
            shadow:true,
            center: {
                autoScroll: true,
                tabPosition: 'top',
                closeOnTab: true,
                alwaysShowTabs: false
            }
        });
        thisDialog.addKeyListener(27, thisDialog.hide, thisDialog);
        thisDialog.addButton('取消', function() {thisDialog.hide();});

        return thisDialog;
    };
    function configMenu(){
        var sm = tree.getSelectionModel();
        var n = sm.getSelectedNode();
        // Ext.MessageBox.prompt('当前菜单URL:'+n.attributes.url, '请输入新的URL:', showResultText);
        var menuData = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url:'loadData.htm?id=' + n.id}),
            reader: new Ext.data.JsonReader({},['id','name',"descn"]),
            remoteSort: false
        });
        menuData.on('load', function() {
            var id = menuData.getAt(0).data['id'];
            var name = menuData.getAt(0).data['name'];
            fieldName.setValue(name);
            var descn = menuData.getAt(0).data['descn'];
            fieldDescn.setValue(descn);

            var dialog;
            if (!dialog) {
                dialog = createNewDialog("a-updateInstance-dialog");
                dialog.addButton('提交', function() {
                    if (menuForm.isValid()) {
                        menuForm.submit({
                            params:{id : id},
                            waitMsg:'更新数据...',
                            reset: false,
                            failure: function(menuForm, action) {
                                Ext.MessageBox.alert('错误', action.result.errorInfo);
                            },
                            success: function(menuForm, action) {
                                Ext.MessageBox.alert('成功', action.result.info);
                                dialog.hide();
                                refresh();
                            }
                        });
                    }else{
                        Ext.MessageBox.alert('错误', '请查看错误信息');
                    }
                });

                var layout = dialog.getLayout();
                layout.beginUpdate();
                layout.add('center', new Ext.ContentPanel('a-updateInstance-inner', {title: '修改菜单信息'}));
                layout.endUpdate();

                dialog.show();
            }
        });
        menuData.load();
    }
    // 打开验证功能
    Ext.form.Field.prototype.msgTarget = 'side';
    Ext.form.Field.prototype.height = 20;
    Ext.form.Field.prototype.fieldClass = 'text-field-default';
    Ext.form.Field.prototype.focusClass = 'text-field-focus';
    Ext.form.Field.prototype.invalidClass = 'text-field-invalid';
    var fieldName = new Ext.form.TextField({
        fieldLabel: '名称',
        name: 'name',
        width:170,
        readOnly: false,
        allowBlank:false
    });
    var fieldDescn = new Ext.form.TextField({
        fieldLabel: '描述',
        name: 'descn',
        width:170,
        readOnly: false,
        allowBlank:true
    });
    var menuForm = new Ext.form.Form({
        labelAlign: 'right',
        url:'update.htm'
    });

    menuForm.column({width: 360, labelWidth:100, style:'margin-left:10px;margin-top:10px'});
    menuForm.fieldset(
        {id:'id', legend:'修改'},
        fieldName,
        fieldDescn
    );

    menuForm.applyIfToFields({width:255});
    menuForm.render('a-updateInstance-form');
    menuForm.end();
    function showResultText(btn, text){
        var sm = tree.getSelectionModel();
        var n = sm.getSelectedNode();
        if(btn == 'ok'){
            Ext.example.msg('数据提交中....', '请稍候');
            Ext.Ajax.request({
                url:'menu.do?method=updateMenuUrl',
                success:function(){
                    Ext.MessageBox.alert('提示', '配置成功!');
                    tree.getNodeById(n.id).reload();
                },
                failure:function(){Ext.MessageBox.alert('提示', '配置失败!');},
                params:{id:n.id,url:text}
            });
        }else{
            return;
        }
    };

    // ====================================================
    // 右键菜单
    // ====================================================
    tree.on('contextmenu', prepareCtx);
    var ctxMenu = new Ext.menu.Menu({
        id:'copyCtx',
        items: [{
            id:'展开',
            handler:expandAll,
            cls:'expand-all',
            text:'展开'
        },{
            id:'收起',
            handler:collapseAll,
            cls:'collapse-all',
            text:'收起'
        },{
            id:'remove',
            handler:removeNode,
            cls:'remove-mi',
            text: '删除'
        },{
            id:'config',
            handler:configMenu,
            text: '配置部门'
        }]
    });
    function prepareCtx(node, e){
        node.select();
        ctxMenu.items.get('remove')[node.attributes.allowDelete ? 'enable' : 'disable']();
        ctxMenu.showAt(e.getXY());
    }

    // ====================================================
    // 拖拽
    // ====================================================
    tree.on("nodedragover", function(e){
        var n = e.target;
        if (n.leaf) {
            n.leaf = false;
        }
        return true;
    });
    // 拖拽后，就向服务器发送消息，更新数据
    // 本人不喜欢这种方式，屏蔽
/*
    tree.on('nodedrop', function(e){
        var n = e.dropNode;
        var item = {
            id: n.id,
            text: n.text,
            parentId: e.target.id
        };
        tree.el.mask('正在向服务器发送信息...', 'x-mask-loading');
        var hide = tree.el.unmask.createDelegate(tree.el);
        Ext.lib.Ajax.request(
            'POST',
            'insertTree.htm',
            {success:hide,failure:hide},
            'data='+encodeURIComponent(Ext.encode(item))
        );
    });
*/
});
