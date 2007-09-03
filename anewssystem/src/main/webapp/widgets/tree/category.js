
Ext.onReady(function(){

    // turn on quick tips
    Ext.QuickTips.init();

    // this is the source code tree
    var tree = new Ext.tree.TreePanel('main', {
        animate:true,
        containerScroll: true,
        enableDD:true,
        lines: true,
        //loader: new Ext.tree.TreeLoader({dataUrl:'getChildren.htm'})
		loader: new Ext.tree.TreeLoader({dataUrl:'getAllTree.htm'})
    });

    tree.el.addKeyListener(Ext.EventObject.DELETE, removeNode);
    //new Ext.tree.TreeSorter(tree, {folderSort:true});

    var tb = new Ext.Toolbar(tree.el.createChild({tag:'div'}));
    tb.add({
        text: '新增子分类',
        cls: 'x-btn-text-icon album-btn',
        tooltip: '添加选中节点的下级分类',
        handler: createChild
    }, {
        text: '新增兄弟分类',
        cls: 'x-btn-text-icon album-btn',
        tooltip: '添加选中节点的同级分类',
        handler: createBrother
    }, {
        text: '修改分类',
        cls: 'x-btn-text-icon album-btn',
        tooltip: '修改选中分类',
        handler: updateNode
    }, {
        text: '删除分类',
        cls: 'x-btn-text-icon album-btn',
        tooltip:'删除一个分类',
        handler:removeNode
    }, {
        text: '排序',
        cls: 'x-btn-text-icon album-btn',
        tooltip:'保存排序结果',
        handler:save
    }, {
        text: '展开',
        cls: 'x-btn-text-icon album-btn',
        tooltip:'展开所有分类',
        handler:expandAll
    }, {
        text: '关闭',
        cls: 'x-btn-text-icon album-btn',
        tooltip:'关闭所有分类',
        handler:collapseAll
    }, {
        text: '刷新',
        cls: 'x-btn-text-icon album-btn',
        tooltip:'刷新所有节点',
        handler:refresh
    });

    // add an inline editor for the nodes
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
            //alert("success\n" + responseObject.responseText);
            eval("var o = " + responseObject.responseText + ";");
            ge.editNode.id = o.id;
            hide();
        };
        var doFailure = function(responseObject) {
            //alert("faliure\n" + responseObject.responseText);
            hide();
        };
        Ext.lib.Ajax.request(
            'POST',
            'insertTree.htm',
            {success:doSuccess,failure:doFailure},
            'data='+encodeURIComponent(Ext.encode(item))
        );
    });

    var root = new Ext.tree.AsyncTreeNode({
        text: '分类',
        draggable:true,
        id:'-1'
    });
    tree.setRootNode(root);
    tree.render();
    root.expand(true, false);

    // function

    function createChild() {
        var sm = tree.getSelectionModel();
        var n = sm.getSelectedNode();
        if (!n) {
            n = tree.getRootNode();
        } else {
            n.expand(false, false);
        }
        // var selectedId = (n) ? n.id : -1;
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

    // save to the server in a format usable in PHP
    function save() {
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
        //ctxMenu.hide();
        setTimeout(function(){
            root.eachChild(function(n){
               n.collapse(false, false);
            });
        }, 10);
    }

    function expandAll(){
        //ctxMenu.hide();
        setTimeout(function(){
            root.eachChild(function(n){
               n.expand(false, false);
            });
        }, 10);
    }

    tree.on('contextmenu', prepareCtx);
    // context menus
    var ctxMenu = new Ext.menu.Menu({
        id:'copyCtx',
        items: [{
                id:'createChild',
                handler:createChild,
                cls:'create-mi',
                text: '新增子节点'
            },{
                id:'createBrother',
                handler:createBrother,
                cls:'create-mi',
                text: '新增兄弟节点'
            },{
                id:'updateNode',
                handler:updateNode,
                cls:'update-mi',
                text: '修改节点'
            },{
                id:'remove',
                handler:removeNode,
                cls:'remove-mi',
                text: '删除'
            },'-',{
                id:'expand',
                handler:expandAll,
                cls:'expand-all',
                text:'展开'
            },{
                id:'collapse',
                handler:collapseAll,
                cls:'collapse-all',
                text:'关闭'
            },{
                id:'refresh',
                handler:refresh,
                cls:'refresh',
                text:'刷新'
        }]
    });

    function prepareCtx(node, e){
        node.select();
        ctxMenu.items.get('remove')[node.attributes.allowDelete ? 'enable' : 'disable']();
        ctxMenu.showAt(e.getXY());
    }
    // handle drag over and drag drop
    tree.on('nodedrop', function(e){
        var n = e.dropNode;
        //alert(n + "," + e.target + "," + e.point);
        return true;
    });

    function refresh() {
        tree.root.reload();
        tree.root.expand(true, false);
    }
});
