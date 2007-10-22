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
/**
 * 声明Ext.lingo命名控件
 * TODO: 完全照抄，作用不明
 */
Ext.namespace("Ext.lingo");
/**
 * 下拉框.
 *
 * 下拉出现一个面板，可以选择一个或多个关键字的面板
 */
Ext.lingo.TagField = function(config) {
    config.readOnly = false; // inwei是选择关键字，所以text也是可以编辑的
    this.url = config.tagConfig.dataTag;
    this.start = 0;
    this.limit = 12;
    Ext.lingo.TagField.superclass.constructor.call(this, config);
};
Ext.extend(Ext.lingo.TagField, Ext.form.TriggerField, {
    triggerClass      : 'x-form-date-trigger',
    defaultAutoCreate : {
        tag          : "input",
        type         : "text",
        size         : "10",
        autocomplete : "off"
    },

    getValue : function() {
        return Ext.lingo.TagField.superclass.getValue.call(this) || "";
    },

    setValue : function(val) {
        Ext.lingo.TreeField.superclass.setValue.call(this, val);
    },

    menuListeners : {
        select:function (item, picker, node) {
            var v = node.text;
            var ed = this.ed;
            if(this.selfunc) {
                v = this.selfunc(node);
            }
            if(ed) {
                var r= ed.record;
                r.set(this.fn, v);
            } else {
                this.focus();
                this.setValue(v);
                // document.getElementById("category_id").value = node.id;
                this.selectedId = node.id;
            }
        }, hide : function(){
        }
    },

    onTriggerClick : function(){
        if(this.disabled){
            return;
        }
        if(this.menu == null){
            this.menu = new Ext.menu.TagMenu(this.initialConfig);
        }

        Ext.apply(this.menu.picker,  {
            url : this.url
        });
        this.menu.on(Ext.apply({}, this.menuListeners, {
            scope : this
        }));
        this.menu.picker.setValue(this.getValue());
        this.menu.show(this.el, "tl-bl?");
    },

    invalidText : "{0} is not a valid date - it must be in the format {1}"
});


Ext.menu.TagMenu = function(config) {

    Ext.menu.TagMenu.superclass.constructor.call(this, config);
    this.plain = true;
    var di = new Ext.menu.TagItem(config);
    this.add(di);
    this.picker = di.picker;
    this.relayEvents(di, ["select"]);
    this.relayEvents(di, ["beforesubmit"]);
};

Ext.extend(Ext.menu.TagMenu, Ext.menu.Menu);

Ext.menu.TagItem = function(config){
    Ext.menu.TagItem.superclass.constructor.call(this, new Ext.TagPicker(config), config);
    this.picker = this.component;
    this.addEvents({select: true});

    this.picker.on("render", function(picker){
        picker.getEl().swallowEvent("click");
        //picker.container.addClass("x-menu-date-item");
    });

    this.picker.on("select", this.onSelect, this);
};

Ext.extend(Ext.menu.TagItem, Ext.menu.Adapter, {
    onSelect : function(picker, node) {
        this.fireEvent("select", this, picker, node);
        Ext.menu.TagItem.superclass.handleClick.call(this);
    }
});

Ext.TagPicker = function(config){
    Ext.TagPicker.superclass.constructor.call(this, config);
    this.addEvents({select: true});
    if(this.handler){
        this.on("select", this.handler,  this.scope || this);
    }
};

Ext.extend(Ext.TagPicker, Ext.Component, {
    setValue : function(value) {
        this.value = value;
        if(this.tag)
            this.tag.selectPath(value, 'text');
    },

    getValue : function() {
        return this.value;
    },
    onRender : function(container) {
        var me = this;
        var dh = Ext.DomHelper;
        var el = document.createElement("div");
        el.className = "x-date-picker";
        el.innerHTML = '';
        var eo = Ext.get(el);
        this.el = eo;
        container.dom.appendChild(el);

        var tag = this.createTag(el, me.url, function() {
            var tag = this;
            tag.render();
            tag.selectPath(me.getValue(), 'text');
        });
        tag.on('click',function(node,e){
            me.fireEvent("select", me, node);
        });
        this.tag = tag;
    }

    , createTag : function(el, url, callback) {
        var Tree = Ext.tree;
        // id
        var tag = new Tree.TreePanel(el, {
            animate         : true,
            loader          : new Tree.TreeLoader({dataUrl:url}), // c.dataTag
            enableDD        : false,
            containerScroll : true
        });

        // 设置根节点
        var root = new Tree.AsyncTreeNode({
            text      : this.initialConfig.tagConfig.title, // c.title
            draggable : false,
            id        : 'source'
        });
        tree.setRootNode(root);
        // 渲染
        tree.render();
        root.expand();
        return tree;
    }
});

