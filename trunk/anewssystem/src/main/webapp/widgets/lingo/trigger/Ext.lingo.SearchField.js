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
Ext.lingo.SearchField = function(config) {
    Ext.lingo.SearchField.superclass.constructor.call(this, config);
};
Ext.extend(Ext.lingo.SearchField, Ext.form.TriggerField, {
    defaultAutoCreate : {
        tag          : "input",
        type         : "text",
        size         : "10",
        autocomplete : "off"
    },

    onRender : function(ct, position) {
        Ext.lingo.SearchField.superclass.onRender.call(this, ct, position);
        var hiddenId = this.el.id + "_id";
        this.hiddenField = this.el.insertSibling(
            {tag:'input', type:'hidden', name: hiddenId, id: hiddenId},
            'before', true);
    },

    onTriggerClick : function(){
        if(this.disabled){
            return;
        }
        if(this.menu == null){
            this.menu = new Ext.lingo.SearchMenu(this.initialConfig);
        }

//        this.menu.picker.setValue(this.getValue());
        this.menu.show(this.el, "tl-bl?");
    }

});

Ext.lingo.SearchMenu = function(config) {
    Ext.lingo.SearchMenu.superclass.constructor.call(this, config);
    var item = new Ext.lingo.SearchItem(config);
    this.add(item);
};

Ext.extend(Ext.lingo.SearchMenu, Ext.menu.Menu);

Ext.lingo.SearchItem = function(config){
    Ext.lingo.SearchItem.superclass.constructor.call(this, new Ext.lingo.SearchPicker(config), config);
    this.picker = this.component;
    this.addEvents({select: true});

    this.picker.on("render", function(picker){
        //picker.getEl().swallowEvent("click");
        //picker.container.addClass("x-menu-date-item");
    });

    this.picker.on("select", this.onSelect, this);
};

Ext.extend(Ext.lingo.SearchItem, Ext.menu.Adapter);

Ext.lingo.SearchPicker = function(config){
    Ext.lingo.SearchPicker.superclass.constructor.call(this, config);


    var ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'Ext.lingo.SearchField.json'
        }),
        reader: new Ext.data.JsonReader({
            root: 'topics',
            totalProperty: 'totalCount',
            id: 'post_id'
        }, [
            {name: 'postId', mapping: 'post_id'},
            {name: 'title', mapping: 'topic_title'},
            {name: 'topicId', mapping: 'topic_id'},
            {name: 'author', mapping: 'author'},
            {name: 'lastPost', mapping: 'post_time', type: 'date', dateFormat: 'Y-m-d'},
            {name: 'excerpt', mapping: 'post_text'}
        ]),

        baseParams: {limit:20, forumId: 4}
    });

    // Custom rendering Template for the View
    var resultTpl = new Ext.Template(
        '<div class="search-item">',
            '<h3><span>{lastPost:date("M j, Y")}<br />by {author}</span>',
            '<a href="http://extjs.com/forum/showthread.php?t={topicId}&p={postId}" target="_blank">{title}</a></h3>',
            '<p>{excerpt}</p>',
        '</div>'
    );

    this.ds = ds;
    this.resultTpl = resultTpl;

    //var view = new Ext.View('test', resultTpl, {store: ds});
    //ds.load();

};

Ext.extend(Ext.lingo.SearchPicker, Ext.Component, {
    onRender : function(container) {
        var el = document.createElement("div");
        el.className = "x-date-picker";
        el.innerHTML = '';
        var eo = Ext.get(el);
        this.el = eo;
        container.dom.appendChild(el);

        var view = new Ext.View(this.el, this.resultTpl, {store:this.ds});
        this.ds.load();
    }
});


