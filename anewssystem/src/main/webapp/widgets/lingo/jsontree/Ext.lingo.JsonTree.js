/*
 * Ext JS Library 1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author Lingo
 * @since 2007-09-13
 * http://code.google.com/p/anewssystem/
 */
/**
 * 声明Ext.lingo命名控件
 * TODO: 完全照抄，作用不明
 */
Ext.namespace("Ext.lingo");
/**
 * 拥有CRUD功能的树形.
 *
 * @param container 被渲染的html元素的id，<div id="lighttree"></div>
 * @param config    需要的配置{}
 */
Ext.lingo.JsonTree = function(container, config) {
    // <div id="container"></div>没有找到
    // 这个配置这里不是不清楚？是不是获得MenuTree.js中创建树形下的数据，为什么用了条件表达式
    // by 250678089 死胖子 2007-09-16 22:13
    //
    // container是一个变量，值是一个div的id。
    // 比如调用new Ext.lingo.JsonTree("jsonTree",{});
    // html就会有一个<div id="jsonTree"></div>，这样container的值就是"jsonTree"
    this.container     = Ext.get(container);
    this.id            = this.container.id;
    this.config        = config;
    this.rootName      = config.rootName ? config.rootName : '分类';
    this.metaData      = config.metaData;
    this.urlGetAllTree = config.urlGetAllTree ? config.urlGetAllTree : "getAllTree.htm";
    this.urlInsertTree = config.urlInsertTree ? config.urlInsertTree : "insertTree.htm";
    this.urlRemoveTree = config.urlRemoveTree ? config.urlRemoveTree : "removeTree.htm";
    this.urlSortTree   = config.urlSortTree ? config.urlSortTree : "sortTree.htm";
    this.urlLoadData   = config.urlLoadData ? config.urlLoadData : "loadData.htm";
    this.urlUpdateTree = config.urlUpdateTree ? config.urlUpdateTree : "updateTree.htm";

    // 什么意思这句，作用？
    // by 250678089 死胖子 2007-09-16 22:13
    //
    // 具体细节不明，猜想是调用父类的构造函数，进行初始化
    Ext.lingo.JsonTree.superclass.constructor.call(this);
};

Ext.extend(Ext.lingo.JsonTree, Ext.util.Observable, {
    init : function() {
        // 生成代理，具体的意思呢，是调用那个工具栏里的操作吗？
        // Ext.util.Observable,Ext.lingo.JsonTree 自己写的吗？
        // by 250678089 死胖子 2007-09-16 22:13
        //
        // Ext.util.Observable是Ext中一个基类，提供了事件绑定与监听的一些方法
        var createChild = this.createChild.createDelegate(this);
        var createBrother = this.createBrother.createDelegate(this);
        var updateNode = this.updateNode.createDelegate(this);
        var removeNode = this.removeNode.createDelegate(this);
        var save = this.save.createDelegate(this);
        var expandAll = this.expandAll.createDelegate(this);
        var collapseAll = this.collapseAll.createDelegate(this);
        var refresh = this.refresh.createDelegate(this);
        var configInfo = this.configInfo.createDelegate(this);
        var prepareContext = this.prepareContext.createDelegate(this);

        // 创建树形
        if (this.treePanel == null) {
            var treeLoader = new Ext.tree.TreeLoader({dataUrl:this.urlGetAllTree});
            this.treePanel = new Ext.tree.TreePanel(this.id, {
                animate         : true,
                containerScroll : true,
                enableDD        : true,
                lines           : true,
                loader          : treeLoader
            });

            // DEL快捷键，删除节点
            this.treePanel.el.addKeyListener(Ext.EventObject.DELETE, removeNode);

            // 自动排序
            if (this.config.folderSort) {
                new Ext.tree.TreeSorter(this.treePanel, {folderSort:true});
            }
        }
        // 生成工具条
        if (this.toolbar == null) {
            this.buildToolbar();
        }

        // 设置编辑器
        if (this.treeEditor == null) {
            this.treeEditor = new Ext.tree.TreeEditor(this.treePanel, {
                allowBlank    : false,
                blankText     : '请添写名称',
                selectOnFocus : true
            });
            // 这里不是很明白,什么意思?
            // by 250678089 死胖子 2007-09-16 22:13
            //
            // 绑定before edit事件，就是在鼠标双击节点，打算编辑这个节点的文字之前做判断
            // 1.如果这个节点的allowEdit属性不是true，就不让编辑这个节点
            // 2.如果节点可以编辑，就把当前的text保存到oldText属性中，为了在之后判断这个节点的文字是否被修改了
            this.treeEditor.on('beforestartedit', function() {
                var node = this.treeEditor.editNode;
                if(!node.attributes.allowEdit) {
                    return false;
                } else {
                    node.attributes.oldText = node.text;
                }
            }.createDelegate(this));
            this.treeEditor.on('complete', function() {
                var node = this.treeEditor.editNode;
                // 如果节点没有改变，就向服务器发送修改信息
                if (node.attributes.oldText == node.text) {
                    node.attributes.oldText = null;
                    return true;
                }
                var item = {
                    id       : node.id,
                    name     : node.text,
                    parentId : node.parentNode.id
                };

                this.treePanel.el.mask('提交数据，请稍候...', 'x-mask-loading');
                var hide = this.treePanel.el.unmask.createDelegate(this.treePanel.el);
                var doSuccess = function(responseObject) {
                    eval("var o = " + responseObject.responseText + ";");
                    this.treeEditor.editNode.id = o.id;
                    hide();
                }.createDelegate(this);
                Ext.lib.Ajax.request(
                    'POST',
                    this.urlInsertTree,
                    {success:doSuccess,failure:hide},
                    'data=' + encodeURIComponent(Ext.encode(item))
                );
            }.createDelegate(this));
        }

        // 右键菜单
        this.treePanel.on('contextmenu', prepareContext);
        if (this.contextMenu == null) {
            this.buildContextMenu();
        }

        // 拖拽判断
        this.treePanel.on("nodedragover", function(e){
          var n = e.target;
          if (n.leaf) {
            n.leaf = false;
          }
          return true;
        });
        // 拖拽后，就向服务器发送消息，更新数据
        // 本人不喜欢这种方式
        if (this.config.dropUpdate) {
          this.treePanel.on('nodedrop', function(e) {
            var n = e.dropNode;
            var item = {
              id       : n.id,
              text     : n.text,
              parentId : e.target.id
            };

            // 我用的连接不是这样的，用的或是,这里没有用Ajax，如果用你的Ext.lib.Ajax.request，用引是什么东西吗？
            // by 250678089 死胖子 2007-09-16 22:13
            //
            // 没用过connection。估计跟ajax的功能差不多
            /*
            var url = 'rssreaderAction.do?method=reNameChannel';
            var params = {data:Ext.util.JSON.encode({
                channelName:channelName,
                id:id,
                channelUrl:channelUrl,
                time_stamp:(new Date()).getTime()})
            };
            var connection = new Ext.data.Connection();
            connection.request({url:url,method:'POST',params:params,callback:handle});
            */

            this.treePanel.el.mask('提交数据，请稍候...', 'x-mask-loading');
            var hide = this.treePanel.el.unmask.createDelegate(this.treePanel.el);
            Ext.lib.Ajax.request(
              'POST',
              this.urlInsertTree,
              {success:hide,failure:hide},
              'data=' + encodeURIComponent(Ext.encode(item))
            );
          });
        } else {
          this.treePanel.on('nodedrop', function(e) {
            var n = e.dropNode;
            n.ui.textNode.style.fontWeight = "bold";
            n.ui.textNode.style.color = "red";
            n.ui.textNode.style.border = "1px red dotted";
          });
        }
    }

    // 生成工具条
    , buildToolbar : function() {
        this.toolbar = new Ext.Toolbar(this.treePanel.el.createChild({tag:'div'}));

        this.toolbar.add({
            text    : '新增下级分类',
            icon    : '../widgets/lingo/list-items.gif',
            cls     : 'x-btn-text-icon album-btn',
            tooltip : '添加选中节点的下级分类',
            handler : this.createChild.createDelegate(this)
        }, {
            text    : '新增同级分类',
            icon    : '../widgets/lingo/list-items.gif',
            cls     : 'x-btn-text-icon album-btn',
            tooltip : '添加选中节点的同级分类',
            handler : this.createBrother.createDelegate(this)
        }, {
            text    : '修改分类',
            icon    : '../widgets/lingo/list-items.gif',
            cls     : 'x-btn-text-icon album-btn',
            tooltip : '修改选中分类',
            handler : this.updateNode.createDelegate(this)
        }, {
            text    : '删除分类',
            icon    : '../widgets/lingo/list-items.gif',
            cls     : 'x-btn-text-icon album-btn',
            tooltip : '删除一个分类',
            handler : this.removeNode.createDelegate(this)
        }, '-', {
            text    : '保存排序',
            icon    : '../widgets/lingo/list-items.gif',
            cls     : 'x-btn-text-icon album-btn',
            tooltip : '保存排序结果',
            handler : this.save.createDelegate(this)
        }, '-', {
            text    : '展开',
            icon    : '../widgets/lingo/list-items.gif',
            cls     : 'x-btn-text-icon album-btn',
            tooltip : '展开所有分类',
            handler : this.expandAll.createDelegate(this)
        }, {
            text    : '合拢',
            icon    : '../widgets/lingo/list-items.gif',
            cls     : 'x-btn-text-icon album-btn',
            tooltip : '合拢所有分类',
            handler : this.collapseAll.createDelegate(this)
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
                    handler : this.createChild.createDelegate(this),
                    cls     : 'create-mi',
                    text    : '新增下级节点'
                },{
                    id      : 'createBrother',
                    icon    : '../widgets/lingo/list-items.gif',
                    handler : this.createBrother.createDelegate(this),
                    cls     : 'create-mi',
                    text    : '新增同级节点'
                },{
                    id      : 'updateNode',
                    icon    : '../widgets/lingo/list-items.gif',
                    handler : this.updateNode.createDelegate(this),
                    cls     : 'update-mi',
                    text    : '修改节点'
                },{
                    id      : 'remove',
                    icon    : '../widgets/lingo/list-items.gif',
                    handler : this.removeNode.createDelegate(this),
                    cls     : 'remove-mi',
                    text    : '删除节点'
                },'-',{
                    id      : 'expand',
                    icon    : '../widgets/lingo/list-items.gif',
                    handler : this.expandAll.createDelegate(this),
                    cls     : 'expand-all',
                    text    : '展开'
                },{
                    id      : 'collapse',
                    icon    : '../widgets/lingo/list-items.gif',
                    handler : this.collapseAll.createDelegate(this),
                    cls     : 'collapse-all',
                    text    : '合拢'
                },{
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

    // 渲染树形
    , render : function() {
        this.init();

        // 创建根节点，下面比较乱，最好整理一下！你这里用一个右键处理我认为更好些！
        // by 250678089 死胖子 2007-09-16 22:13
        //
        // 不知道具体是哪里乱，另外，这个里边是包含了右键功能的
        var root = new Ext.tree.AsyncTreeNode({
            text      : this.rootName,
            draggable : true,
            id        : '-1'
        });
        this.treePanel.setRootNode(root);
        this.treePanel.render();
        root.expand(false, false);
    }, createChild : function() {
        var sm = this.treePanel.getSelectionModel();
        var n = sm.getSelectedNode();
        if (!n) {
            n = this.treePanel.getRootNode();
        } else {
            n.expand(false, false);
        }
        this.createNode(n);
    }, createBrother : function() {
        var n = this.treePanel.getSelectionModel().getSelectedNode();
        if (!n) {
            Ext.Msg.alert('提示', "请选择一个节点");
        } else if (n == this.treePanel.getRootNode()) {
            Ext.Msg.alert('提示', "不能为根节点增加同级节点");
        } else {
            this.createNode(n.parentNode);
        }
    }, createNode : function(n) {
        var node = n.appendChild(new Ext.tree.TreeNode({
            id            : -1,
            text          : '请输入分类名',
            cls           : 'album-node',
            allowDrag     : true,
            allowDelete   : true,
            allowEdit     : true,
            allowChildren : true
        }));
        this.treePanel.getSelectionModel().select(node);
        setTimeout(function(){
            this.treeEditor.editNode = node;
            this.treeEditor.startEdit(node.ui.textNode);
        }.createDelegate(this), 10);
    }, updateNode : function() {
        var n = this.treePanel.getSelectionModel().getSelectedNode();
        if (!n) {
            Ext.Msg.alert('提示', "请选择一个节点");
        } else if (n == this.treePanel.getRootNode()) {
            Ext.Msg.alert('提示', "不能修改根节点");
        } else {
            setTimeout(function(){
                this.treeEditor.editNode = n;
                this.treeEditor.startEdit(n.ui.textNode);
            }.createDelegate(this), 10);
        }
    }, removeNode : function() {
        var sm = this.treePanel.getSelectionModel();
        var n = sm.getSelectedNode();
        if (n == null) {
            Ext.Msg.alert('提示', "请选择一个节点");
        } else if(n.attributes.allowDelete) {
            Ext.Msg.confirm("提示", "是否确定删除？", function(btn, text) {
                if (btn == 'yes') {
                    this.treePanel.getSelectionModel().selectPrevious();
                    this.treePanel.el.mask('提交数据，请稍候...', 'x-mask-loading');
                    // var hide = this.treePanel.el.unmask.createDelegate(this.treePanel.el);
                    var hide = function() {
                        this.treePanel.el.unmask(this.treePanel.el);
                        n.parentNode.removeChild(n);
                    }.createDelegate(this);
                    Ext.lib.Ajax.request(
                        'POST',
                        this.urlRemoveTree,
                        {success:hide,failure:hide},
                        'id=' + n.id
                    );
                }
            }.createDelegate(this));
        } else {
            Ext.Msg.alert("提示", "这个节点不能删除");
        }
    }, appendNode : function(node, array) {
        if (!node || node.childNodes.length < 1) {
            return;
        }
        for (var i = 0; i < node.childNodes.length; i++) {
            var child = node.childNodes[i];
            array.push({id:child.id,parentId:child.parentNode.id});
            this.appendNode(child, array);
        }
    }, save : function() {
        // 向数据库发送一个json数组，保存排序信息
        this.treePanel.el.mask('提交数据，请稍候...', 'x-mask-loading');
        // var hide = this.treePanel.el.unmask.createDelegate(this.treePanel.el);
        var hide = function() {
            this.treePanel.el.unmask(this.treePanel.el);
            this.refresh();
        }.createDelegate(this);
        var ch = [];
        this.appendNode(this.treePanel.root, ch);

        Ext.lib.Ajax.request(
            'POST',
            this.urlSortTree,
            {success:hide,failure:hide},
            'data=' + encodeURIComponent(Ext.encode(ch))
        );
    }, collapseAll : function() {
        this.contextMenu.hide();
        setTimeout(function() {
            var node = this.getSelectedNode();
            if (node == null) {
                this.treePanel.getRootNode().eachChild(function(n) {
                    n.collapse(true, false);
                });
            } else {
                node.collapse(true, false);
            }
        }.createDelegate(this), 10);
    }, expandAll : function() {
        this.contextMenu.hide();
        setTimeout(function() {
            var node = this.getSelectedNode();
            if (node == null) {
                this.treePanel.getRootNode().eachChild(function(n) {
                    n.expand(false, false);
                });
            } else {
                node.expand(false, false);
            }
        }.createDelegate(this), 10);
    }, prepareContext : function(node, e) {
        node.select();
        this.contextMenu.items.get('remove')[node.attributes.allowDelete ? 'enable' : 'disable']();
        this.contextMenu.showAt(e.getXY());
    }, refresh : function() {
        this.treePanel.root.reload();
        this.treePanel.root.expand(false, false);
    }, configInfo : function() {
        if (!this.dialog) {
            this.createDialog();
        }

        var n = this.getSelectedNode();
        //if (n == null) {
        //    Ext.MessageBox.alert("提示", "需要选中一个节点");
        //}
        this.menuData = new Ext.data.Store({
            proxy      : new Ext.data.HttpProxy({url:this.urlLoadData + "?id=" + n.id}),
            reader     : new Ext.data.JsonReader({},this.headers),
            remoteSort : false
        });

        this.menuData.on('load', function() {
            for (var i = 0; i < this.metaData.length; i++) {
                var meta = this.metaData[i];

                var id = meta.id;
                var value;
                if (meta.mapping) {
                    try {
                        value = eval("this.menuData.getAt(0).data." + meta.mapping);
                    } catch (e) {
                        value = this.menuData.getAt(0).data[meta.mapping];
                    }
                } else {
                    value = this.menuData.getAt(0).data[id];
                }

                if (meta.vType == "radio") {
                    for (var j = 0; j < meta.values.length; j++) {
                        var theId = meta.values[j].id;
                        var theName = meta.values[j].name;

                        if (value == theId) {
                            this.columns[id + theId].checked = true;
                            this.columns[id + theId].el.dom.checked = true;
                        } else {
                            this.columns[id + theId].checked = false;
                            this.columns[id + theId].el.dom.checked = false;
                        }
                    }
                } else if (meta.vType == "date") {
                    if (value == null ) {
                        this.columns[id].setValue(new Date());
                    } else {
                        this.columns[id].setValue(value);
                    }
                } else {
                    this.columns[id].setValue(value);
                }

            }
            this.dialog.show(this.treePanel.getSelectionModel().getSelectedNode().ui.textNode);
        }.createDelegate(this));

        this.menuData.load();
    }

    // 生成对话框
    , createDialog : function() {
        this.dialog = Ext.lingo.FormUtils.createTabedDialog(this.config.dialogContent + "-dialog", ['详细配置','帮助']);

        this.yesBtn = this.dialog.addButton("确定", function() {
            var item = Ext.lingo.FormUtils.serialFields(this.columns);
            if (!item) {
                return;
            }

            this.dialog.el.mask('提交数据，请稍候...', 'x-mask-loading');
            var hide = function() {
                this.dialog.el.unmask();
                this.dialog.hide();
                this.refresh();
            }.createDelegate(this);
            Ext.lib.Ajax.request(
                'POST',
                this.urlUpdateTree,
                {success:hide,failure:hide},
                'data=' + encodeURIComponent(Ext.encode(item))
            );
        }.createDelegate(this), this.dialog);
        this.tabs = this.dialog.getTabs();
        this.tabs.getTab(0).on("activate", function() {
            this.yesBtn.show();
        }, this, true);
        this.tabs.getTab(1).on("activate", function(){
            this.yesBtn.hide();
        }, this, true);

        var dialogContent = Ext.get(this.config.dialogContent);
        this.tabs.getTab(0).setContent(dialogContent.dom.innerHTML);
        document.body.removeChild(document.getElementById(this.config.dialogContent));
        this.applyElements();
        this.noBtn = this.dialog.addButton("取消", this.dialog.hide, this.dialog);
    }

    // 自动生成一切的地方
    , applyElements : function() {
        if (this.columns == null || this.headers == null) {
            this.headers = new Array();
            for (var i = 0; i < this.config.metaData.length; i++) {
                if (this.metaData[i].mapping) {
                    this.headers[this.headers.length] = this.metaData[i].mapping;
                } else {
                    this.headers[this.headers.length] = this.metaData[i].id;
                }
            }

            // 打开验证功能
            //Ext.form.Field.prototype.msgTarget = 'side';
            //Ext.form.Field.prototype.height = 20;
            //Ext.form.Field.prototype.fieldClass = 'text-field-default';
            //Ext.form.Field.prototype.focusClass = 'text-field-focus';
            //Ext.form.Field.prototype.invalidClass = 'text-field-invalid';

            this.columns = Ext.lingo.FormUtils.createAll(this.metaData);
        }
    }

    // 返回当前选中的节点，可能为null
    , getSelectedNode : function() {
        var selectionModel = this.treePanel.getSelectionModel();
        var node = selectionModel.getSelectedNode();
        return node;
    }
});
