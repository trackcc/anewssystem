/*--------------Ext.form.TreeField--------------------*/
function createXmlTree(el, url, callback) {
/*
*/
        var Tree = Ext.tree;
        var tree = new Tree.TreePanel(el, {//id
            animate:true,
            loader: new Tree.TreeLoader({dataUrl:'/anews/newscategory/getChildren.htm'}),//c.dataTag
            enableDD:false,
            containerScroll: true
        });
        // set the root node
        var root = new Tree.AsyncTreeNode({
            text: '分类',//c.title
            draggable:false,
            id:'source'//c.rootId
        });
        tree.setRootNode(root);
        // render the tree
        tree.render();
        root.expand();
        return tree;
/*
    var tree = new Ext.tree.TreePanel(el,{containerScroll: true});
    var p = new Ext.data.HttpProxy({url:url});
    p.on("loadexception", function(o, response, e) {
        if (e) throw e;
    });
    p.load(null, {
        read: function(response) {
            var doc = response.responseXML;
            tree.setRootNode(rpTreeNodeFromXml(doc.documentElement || doc));
        }
    }, callback || tree.render, tree);
    return tree;
*/
}
//rp tree from xml
function rpTreeNodeFromXml(XmlEl) {
//  Text is nodeValue to text node, otherwise it's the tag name
    var t = ((XmlEl.nodeType == 3) ? XmlEl.nodeValue : XmlEl.tagName);

//  No text, no node.
    if (t.replace(/\s/g,'').length == 0) {
        return null;
    }

//  Special case of an element containing no attributes and just one text node
    var leafTextNode = ((XmlEl.attributes.length == 0) && (XmlEl.childNodes.length == 1) && (XmlEl.firstChild.nodeType == 3));
    if (leafTextNode ) {
        return new Ext.tree.TreeNode({
            tagName: XmlEl.tagName,
            text: XmlEl.firstChild.nodeValue
        });
    }

    var result = new Ext.tree.TreeNode({
        text : t
    });
    result.tagName=XmlEl.tagName;
//  For Elements, process attributes and children
    if (XmlEl.nodeType == 1) {
        Ext.each(XmlEl.attributes, function(a) {
            result.attributes[a.nodeName]=a.nodeValue;
            if(a.nodeName=='text')
              result.setText(a.nodeValue);
        });
        if (!leafTextNode) {
            Ext.each(XmlEl.childNodes, function(el) {
//          Only process Elements and TextNodes
                if ((el.nodeType == 1) || (el.nodeType == 3)) {
                    var c = rpTreeNodeFromXml(el);
                    if (c) {
                        result.appendChild(c);
                    }
                }
            });
        }
    }
    return result;
}


Ext.form.TreeField = function(config) {
  config.readOnly = true;
  Ext.form.TreeField.superclass.constructor.call(this, config);
};
Ext.extend(Ext.form.TreeField, Ext.form.TriggerField, {
  triggerClass : 'x-form-date-trigger',
  defaultAutoCreate : {tag: "input", type: "text", size: "10", autocomplete: "off"},

  getValue : function() {
    return Ext.form.TreeField.superclass.getValue.call(this)|| "";
  },

  setValue : function(val) {
    Ext.form.TreeField.superclass.setValue.call(this, val);
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
        //Ext.get("category_id").value = node.id;
        //alert(Ext.get("category_id").value);
        document.getElementById("category_id").value = node.id;
        //alert(document.getElementById("category_id"));
        //alert(document.getElementById("category_id").value);
      }
    },
    hide : function(){
    }
  },
  onTriggerClick : function(){
    if(this.disabled){
      return;
    }
    if(this.menu == null){
      this.menu = new Ext.menu.TreeMenu();
    }
    Ext.apply(this.menu.picker,  {
      url:this.url
    });
    this.menu.on(Ext.apply({}, this.menuListeners, {
      scope:this
    }));
    this.menu.picker.setValue(this.getValue());
    this.menu.show(this.el, "tl-bl?");
  },
  invalidText : "{0} is not a valid date - it must be in the format {1}"
});


Ext.menu.TreeMenu = function(config){
    Ext.menu.TreeMenu.superclass.constructor.call(this, config);
    this.plain = true;
    var di = new Ext.menu.TreeItem(config);
    this.add(di);
    this.picker = di.picker;
    this.relayEvents(di, ["select"]);
    this.relayEvents(di, ["beforesubmit"]);
};
Ext.extend(Ext.menu.TreeMenu, Ext.menu.Menu);
Ext.menu.TreeItem = function(config){
    Ext.menu.TreeItem.superclass.constructor.call(this, new Ext.TreePicker(config), config);
    this.picker = this.component;
    this.addEvents({select: true});

    this.picker.on("render", function(picker){
        picker.getEl().swallowEvent("click");
        //picker.container.addClass("x-menu-date-item");
    });

    this.picker.on("select", this.onSelect, this);
};

Ext.extend(Ext.menu.TreeItem, Ext.menu.Adapter, {
    onSelect : function(picker, node){
        this.fireEvent("select", this, picker, node);
        Ext.menu.TreeItem.superclass.handleClick.call(this);
    }
});
Ext.TreePicker = function(config){
    Ext.TreePicker.superclass.constructor.call(this, config);
    this.addEvents({select: true});
    if(this.handler){
        this.on("select", this.handler,  this.scope || this);
    }
};
Ext.extend(Ext.TreePicker, Ext.Component, {
    setValue : function(value){
        this.value=value;
        if(this.tree)
          this.tree.selectPath(value,'text');
    },

    getValue : function(){
        return this.value;
    },
    onRender : function(container){
        var me=this;
        var dh = Ext.DomHelper;
        var el = document.createElement("div");
        el.className = "x-date-picker";
        el.innerHTML='';
        var eo= Ext.get(el);
        this.el=eo;
        container.dom.appendChild(el);

        var tree=createXmlTree(el,me.url,function(){
            var tree=this;
            tree.render();
            tree.selectPath(me.getValue(),'text');
        });
        tree.on('click',function(node,e){
            me.fireEvent("select", me, node);
        });
        this.tree=tree;
      }
}
);

