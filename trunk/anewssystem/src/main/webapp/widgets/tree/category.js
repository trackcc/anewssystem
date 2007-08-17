
Ext.onReady(function(){

    // turn on quick tips
    Ext.QuickTips.init();

    // this is the source code tree
    var tree = new Ext.tree.TreePanel('main', {
        animate:true,
        containerScroll: true,
        enableDD:true,
        lines: true,
        loader: new Ext.tree.TreeLoader({dataUrl:'getChildren.htm'})
    });

    tree.el.addKeyListener(Ext.EventObject.DELETE, removeNode);
    //new Ext.tree.TreeSorter(tree, {folderSort:true});

    var tb = new Ext.Toolbar(tree.el.createChild({tag:'div'}));
    tb.add({
        text: '新增分类',
        cls: 'x-btn-text-icon album-btn',
        tooltip: '在选中节点下添加一个分类',
        handler: create
    }, {
        text: '删除分类',
        cls: 'x-btn-text-icon album-btn',
        tooltip:'删除一个分类',
        handler:removeNode
    }, {
        text: '保存',
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
    });

    // add an inline editor for the nodes
    var ge = new Ext.tree.TreeEditor(tree, {
        allowBlank:false,
        blankText:'请添写名称',
        selectOnFocus:true
    });
    ge.on('beforestartedit', function(){
        if(!ge.editNode.attributes.allowEdit){
            return false;
        }
    });
    ge.on('complete', function() {
        var node = ge.editNode;
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

    function create() {
        var sm = tree.getSelectionModel();
        var n = sm.getSelectedNode();
        if (!n) {
            n = tree.getRootNode();
        } else {
            n.expand(false, false);
        }
        // var selectedId = (n) ? n.id : -1;
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

    function insert() {
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
                id:'expand',
                handler:expandAll,
                cls:'expand-all',
                text:'Expand All'
            },{
                id:'collapse',
                handler:collapseAll,
                cls:'collapse-all',
                text:'Collapse All'
            },'-',{
                id:'remove',
                handler:removeNode,
                cls:'remove-mi',
                text: 'Remove Item'
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
});
