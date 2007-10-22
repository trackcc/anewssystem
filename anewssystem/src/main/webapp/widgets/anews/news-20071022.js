/*
 * Ext JS Library 1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author Lingo
 * @since 2007-10-02
 * http://code.google.com/p/anewssystem/
 */
Ext.onReady(function(){

    // 开启提示功能
    Ext.QuickTips.init();

    // 使用cookies保持状态
    // TODO: 完全照抄，作用不明
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
    layout = new Ext.BorderLayout(document.body, {
        north: {
            split:false,
            initialSize: 22,
            titlebar: false,
            collapsible: false,
            animate: false
        },
        south: {
            split:true,
            initialSize: 22,
            titlebar: true,
            collapsible: true,
            collapsed:true,
            autoScroll:true,
            useShim:true,
            animate: false
        },
        center: {
            titlebar: false,
            autoScroll:false,
            tabPosition: 'top',
            closeOnTab: true,
            alwaysShowTabs: false,
            resizeTabs: true
        }
    });

    layout.beginUpdate();
        layout.add('north', new Ext.ContentPanel('tab1', {
            title      : '已发布',
            toolbar    : null,
            closable   : false,
            fitToFrame : true
        }));
        layout.add('north', new Ext.ContentPanel('tab2', {
            title      : '待审批',
            toolbar    : null,
            closable   : false,
            fitToFrame : true
        }));
        layout.add('north', new Ext.ContentPanel('tab3', {
            title      : '被驳回',
            toolbar    : null,
            closable   : false,
            fitToFrame : true
        }));
        layout.add('north', new Ext.ContentPanel('tab4', {
            title      : '草稿箱',
            toolbar    : null,
            closable   : false,
            fitToFrame : true
        }));
        layout.add('north', new Ext.ContentPanel('tab5', {
            title      : '垃圾箱',
            toolbar    : null,
            closable   : false,
            fitToFrame : true
        }));
        layout.add('center', new Ext.ContentPanel('gridPanel', {
            toolbar    : null,
            closable   : false,
            fitToFrame : true
        }));
        layout.add('south', new Ext.ContentPanel('editGrid', {
                    resizeEl:'editGridContent'
        }));
    layout.restoreState();
    layout.endUpdate();


    var tabs = layout.getRegion('north').getTabs();
    layout.getRegion("north").showPanel("tab1");
    layout.getRegion('north').titleTextEl.innerHTML = "<b>新闻管理 - (" + tabs.getTab(0).getText() + ")</b>";
    var currentStatus = 0;

    // JsonGrid
    var metaData = [
        {id:'id',         qtip:"ID",       vType:"integer",   allowBlank:true, defValue:-1},
        {id:'category',   qtip:"分类",     vType:"treeField", allowBlank:false, mapping:"newsCategory.name", url:"../newscategory/getChildren.htm"},
        {id:'name',       qtip:"标题",     vType:"chn",       allowBlank:true},
        {id:'subtitle',   qtip:'副标题',   vType:'chn',       allowBlank:true},
        {id:'link',       qtip:'跳转链接', vType:'url',       allowBlank:true},
        {id:'editor',     qtip:'编辑',     vType:'chn',       allowBlank:true},
        {id:'updateDate', qtip:'发布日期', vType:'date'},
        {id:'source',     qtip:'来源',     vType:'chn',       allowBlank:true, showInGrid:false},
        {id:'summary',    qtip:'简介',     vType:'textArea',  allowBlank:true, showInGrid:false},
        {id:'content',    qtip:'内容',     vType:'editor',    allowBlank:true, showInGrid:false}
    ];

    // 创建表格
    var lightGrid = new Ext.lingo.JsonGrid("lightgrid", {
        metaData      : metaData,
        genHeader     : true,
        dialogWidth   : 750,
        dialogHeight  : 400,
        dialogContent : "news-content"
    });

    lightGrid.applyElements = function() {
        if (this.columns == null || this.headers == null) {
            this.headers = new Array();
            for (var i = 0; i < this.metaData.length; i++) {
                this.headers[this.headers.length] = this.metaData[i].id;
            }
            this.columns = Ext.lingo.FormUtils.createAll(this.metaData);
            this.columns.tags = Ext.lingo.FormUtils.createTextField({
                id:'tags'
                ,name:'tags'
                ,allowBlank:true
            });
            this.columns.page = Ext.lingo.FormUtils.createComboBox({
                id:'page'
                ,name:'page'
                ,allowBlank:false
            });
            this.columns.pageSize = Ext.lingo.FormUtils.createTextField({
                id:'pagesize'
                ,name:'pagesize'
                ,allowBlank:false
            });
            this.columns.quick = Ext.lingo.FormUtils.createCheckBox({
                id:'quick'
                ,name:'quick'
                ,allowBlank:false
            });

        }
    }.createDelegate(lightGrid);
    lightGrid.edit = function() {
        if (!this.dialog) {
            this.createDialog();
        }

        var selections = this.grid.getSelections();
        if (selections.length == 0) {
            Ext.MessageBox.alert("提示", "请选择希望修改的记录！");
            return;
        } else if (selections.length != 1) {
            Ext.MessageBox.alert("提示", "只允许选择单行记录进行修改！");
            return;
        }

        menuData = new Ext.data.Store({
            proxy      : new Ext.data.HttpProxy({url:this.urlLoadData + "?id=" + selections[0].id}),
            reader     : new Ext.data.JsonReader({},['id','newsCategory','name','subtitle','link','author','updateDate','source','summary','content','newsTags']),
            remoteSort : false
        });

        menuData.on('load', function() {

            for (var i = 0; i < this.metaData.length; i++) {
                var meta = this.metaData[i];

                var id = meta.id;
                var value = menuData.getAt(0).data[id];
                if (meta.mapping) {
                    try {
                        value = eval("menuData.getAt(0).data." + meta.mapping);
                    } catch (e) {
                    }
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

            // 设置关键字
            var value = "";
            for (var i = 0; i < menuData.getAt(0).data.newsTags.length; i++) {
                this.columns.tags.setValue(menuData.getAt(0).data.newsTags.join(","));
                value += menuData.getAt(0).data.newsTags[i].name + ","
            }
            this.columns.tags.setValue(value.substring(0, value.length - 1));

            // 设置分类
            var newsCategory = menuData.getAt(0).data.newsCategory;
            this.columns.category.setValue(newsCategory.name);
            this.columns.category.selectedId = newsCategory.id;

            this.dialog.show(Ext.get("edit"));
        }.createDelegate(this));
        menuData.reload();
    }.createDelegate(lightGrid);

    // 渲染表格
    lightGrid.render();
    // 读取数据
    lightGrid.dataStore.on('beforeload', function() {
        lightGrid.dataStore.baseParams = {
            filterValue : this.filter.getValue(),
            filterTxt   : this.filterTxt,
            status      : currentStatus
        };
    }.createDelegate(lightGrid));
    lightGrid.dataStore.reload();

    var changeStatus = function(status, operation) {
        var selections = lightGrid.grid.getSelections();
        if (selections.length == 0) {
            Ext.MessageBox.alert("提示", "请选择希望" + operation + "的记录！");
            return;
        } else {
            Ext.Msg.confirm("提示", "是否确定" + operation + "？", function(btn, text) {
                if (btn == 'yes') {
                    var selections = lightGrid.grid.getSelections();
                    var ids =new Array();
                    for(var i = 0, len = selections.length; i < len; i++){
                        selections[i].get("id");
                        ids[i] = selections[i].get("id");
                        lightGrid.dataStore.remove(selections[i]);
                    }
                    Ext.Ajax.request({
                        url     : 'changeStatus.htm?ids=' + ids + "&status=" + status,
                        success : function() {
                            Ext.MessageBox.alert(' 提示', operation + '成功！');
                            lightGrid.dataStore.reload();
                        }.createDelegate(this),
                        failure : function(){Ext.MessageBox.alert('提示', operation + '失败！');}
                    });
                }
            });
        }
        lightGrid.grid.selModel.Set.clear();
    };

    var draftButton = new Ext.Toolbar.Button({
        icon    : "../widgets/lingo/list-items.gif",
        id      : 'draftButton',
        text    : '放入草稿箱',
        cls     : 'add',
        tooltip : '放入草稿箱',
        hidden  : true,
        handler : changeStatus.createDelegate(lightGrid,[3, "放入草稿箱"])
    });
    var rubbishButton = new Ext.Toolbar.Button({
        icon    : "../widgets/lingo/list-items.gif",
        id      : 'rubbishButton',
        text    : '放入垃圾箱',
        cls     : 'add',
        tooltip : '放入垃圾箱',
        hidden  : true,
        handler : changeStatus.createDelegate(lightGrid,[4, "放入垃圾箱"])
    });
    lightGrid.toolbar.insertButton(3, draftButton);
    lightGrid.toolbar.insertButton(4, rubbishButton);

    // Tabs
    tabs.getTab(0).on("activate", function(e) {
        layout.getRegion('north').titleTextEl.innerHTML = "<b>新闻管理 - (" + tabs.getTab(0).getText() + ")</b>";
        currentStatus = 0;
        lightGrid.dataStore.reload();

        draftButton.hide();
        rubbishButton.hide();
    });
    tabs.getTab(1).on("activate", function(e) {
        layout.getRegion('north').titleTextEl.innerHTML = "<b>新闻管理 - (" + tabs.getTab(1).getText() + ")</b>";
        currentStatus = 1;
        lightGrid.dataStore.reload();

        draftButton.show();
        rubbishButton.show();
    });
    tabs.getTab(2).on("activate", function(e) {
        layout.getRegion('north').titleTextEl.innerHTML = "<b>新闻管理 - (" + tabs.getTab(2).getText() + ")</b>";
        currentStatus = 2;
        lightGrid.dataStore.reload();

        draftButton.show();
        rubbishButton.show();
    });
    tabs.getTab(3).on("activate", function(e) {
        layout.getRegion('north').titleTextEl.innerHTML = "<b>新闻管理 - (" + tabs.getTab(3).getText() + ")</b>";
        currentStatus = 3;
        lightGrid.dataStore.reload();

        draftButton.hide();
        rubbishButton.show();
    });
    tabs.getTab(4).on("activate", function(e) {
        layout.getRegion('north').titleTextEl.innerHTML = "<b>新闻管理 - (" + tabs.getTab(4).getText() + ")</b>";
        currentStatus = 4;
        lightGrid.dataStore.reload();

        draftButton.show();
        rubbishButton.hide();
    });



    // ====
    // form

    var form = new Ext.form.Form({
        fileUpload:true,
        labelAlign:'right',
        labelWidth:75,
        url:'insert.htm',
        method: 'POST',
        enctype:"multipart/form-data"
    });
    form.add(
        new Ext.form.TextField({
            id:'id',
            name:'id',
            inputType:'hidden',
            hidden:true
        })
    );

    form.column({width:400, labelWidth:75});
    form.fieldset(
        {legend:'基本信息', hideLabels:false},

        new Ext.form.TextField({
            id:'name',
            name:'name',
            allowBlank:false,
            fieldLabel:'新闻标题'
        })
/*
        , new Ext.form.TextField({
            id:'subtitle',
            name:'subtitle',
            fieldLabel:'副标题'
        })
*/
        , new Ext.lingo.TreeField({
            id:'category',
            name:'category',
            fieldLabel:'新闻分类',
            allowBlank:false,
            treeConfig:{
                title:'新闻分类',
                rootId:0,
                dataTag:'../newscategory/getChildren.htm',
                height:200,
                treeHeight:150
            }
        })

        , new Ext.form.TextField({
            id:'source',
            name:'source',
            fieldLabel:'来源',
            allowBlank:false,
            value:'原创'
        })

        , new Ext.form.TextField({
            id:'editor',
            name:'editor',
            fieldLabel:'编辑',
            allowBlank:false,
            value:'管理员'
        })

        , new Ext.form.DateField({
            id:'updateDate',
            name:'updateDate',
            format:'Y-m-d',
            fieldLabel:'发布日期',
            allowBlank:false,
            readOnly:true,
            value:new Date()
        })

        , new Ext.form.TextField({
            id:'tags',
            name:'tags',
            fieldLabel:'关键字'
        })
    );
    form.end();

    form.column(
        {width:250, style:'margin-left:10px', clear:true}
    );

    form.fieldset(
        {id:'photo', legend:'图片', hideLabels:true}

        , new Ext.form.TextField({
            id:'image',
            name:'image',
            inputType:'file',
            width:100,
            fieldLabel:'图片'
        })
    );

    form.fieldset(
        {legend:'内容分页', hideLabels:false},
        new Ext.form.ComboBox({
            id:'pageValue',
            fieldLabel:'分页方式',
            hiddenName:'pageType',
            store: new Ext.data.SimpleStore({
                fields:['value', 'text'],
                data:[
                    ['0','不分页'],
                    ['1','自动分页'],
                    ['2','手工分页']
                ]
            }),
            value:0,
            displayField:'text',
            valueField:'value',
            typeAhead:true,
            mode:'local',
            triggerAction:'all',
            selectOnFocus:true,
            editable:false,
            width:100
        })

        , new Ext.form.TextField({
            id:'pageSize',
            name:'pageSize',
            width:100,
            fieldLabel:'分页字数',
            value:1000
        })
    );
    form.end();

    form.column(
        //{width:650, style:'margin-left:0px', clear:true, labelAlign: 'top'}
        {width:650, style:'margin-left:0px'}
    );
    form.fieldset(
        {legend:'简介', hideLabels:true},
        new Ext.form.TextArea({
            id:'summary',
            name:'summary',
            width:'100%'
        })
    );

    form.fieldset(
        {legend:'内容', hideLabels:true},
        new Ext.form.HtmlEditor({
            id:'content',
            name:'content',
            width:'100%',
            height:'40%'
        })
    );
    form.end();

    form.applyIfToFields({
        width:230
    });

    form.addButton('提交');
    form.addButton('保存到草稿箱');
    form.addButton('重置');

    form.buttons[0].addListener("click", function() {
        if (form.isValid()) {
            form.submit({
                success:function(){
                    form.reset();
                    Ext.MessageBox.alert("提示", "修改成功");
                }
            });
        }
    });

    form.buttons[1].addListener("click", function() {
        if (form.isValid()) {
            form.submit({
                params:{status:3},
                success:function(){
                    Ext.MessageBox.alert("提示", "修改成功");
                    lightGrid.grid.reload();
                }
            });
        }
    });

    form.buttons[2].addListener("click", function() {
        form.reset();
    });

    form.render('news-form');

    // 添加图片
    var photo = Ext.get('photo');
    var c = photo.createChild({
        tag:'center',
        cn:[{
            tag:'img',
            id:'imagePreview',
            src: '../images/no.jpg',
            style:'margin-bottom:5px;width:80px;height:80px;'
        },{
            tag:'div',
            id:'tip',
            style:'text-align:left;color:red;display:none;',
            html:'Firefox需要修改默认安全策略，才能预览本地图片：<br>1.在Firefox的地址栏中输入“about:config”<br>2.继续输入“security.checkloaduri”<br>3.双击下面列出来的一行文字，把它的值由true改为false'
        }]
    });

    var button = new Ext.Button(c, {
        text:'修改图片'
    });

    // 让input=file消失，然后漂浮到修改图片的按钮上面
    var imageField = Ext.get("image");
    if (document.all) {
        // ie
        imageField.setStyle({
            border:'medium none',
            position:'absolute',
            fontSize:'18px',
            left:'50px',
            top:'90px',
            opacity:'0.0'
        });
    } else {
        // firefox
        imageField.setStyle({
            border:'medium none',
            position:'absolute',
            fontSize:'18px',
            left:'-125px',
            top:'85px',
            opacity:'0.0'
        });
    }
    imageField.on('change', function(e) {
        var imagePath = e.target.value;
        var imageUrl = "file:///" + imagePath.replace(/\\/g, "/");
        if (document.all) {
            document.getElementById('imagePreview').src = imageUrl;
        } else {
            // document.getElementById("tip").style.display = 'block';
            document.getElementById('imagePreview').src = imageUrl;
        }
    });


    // ====
    // 处理添加和修改事件

    var grid = lightGrid.grid;
    // 取消双击修改
    grid.events["rowdblclick"].clearListeners();
    // 取消右键菜单
    grid.events["rowcontextmenu"].clearListeners();

    var addNews = function() {
        form.reset();
        document.getElementById('imagePreview').src = '../images/no.jpg';
        if (layout.getRegion('south').collapsed) {
            layout.getRegion('south').expand();
        }
    };

    var editNews = function() {

        var selections = lightGrid.grid.getSelections();
        if (selections.length == 0) {
            Ext.MessageBox.alert("提示", "请选择希望修改的记录！");
            return;
        } else if (selections.length != 1) {
            Ext.MessageBox.alert("提示", "只允许选择单行记录进行修改！");
            return;
        }

        var menuData = new Ext.data.Store({
            proxy      : new Ext.data.HttpProxy({url:lightGrid.urlLoadData + "?id=" + selections[0].id}),
            reader     : new Ext.data.JsonReader({},[
                'id',
                'name',
                'newsCategory',
                'summary',
                'content',
                'updateDate',
                'newsTags',
                'editor',
                'source',
                'image',
                'pageType',
                'pageSize']),
            remoteSort : false
        });

        menuData.on('load', function() {
            var id = menuData.getAt(0).data["id"];
            var name = menuData.getAt(0).data["name"];
            var newsCategory = menuData.getAt(0).data["newsCategory"];
            var summary = menuData.getAt(0).data["summary"];
            var content = menuData.getAt(0).data["content"];
            var updateDate = menuData.getAt(0).data["updateDate"];
            var newsTags = menuData.getAt(0).data["newsTags"];
            var editor = menuData.getAt(0).data["editor"];
            var source = menuData.getAt(0).data["source"];
            var image = menuData.getAt(0).data["image"];
            var pageType = menuData.getAt(0).data["pageType"];
            var pageSize = menuData.getAt(0).data["pageSize"];

            form.setValues([
                {id:'id',value:id},
                {id:'name',value:name},
                {id:'category',value:newsCategory==null ? 0 : newsCategory.id},
                {id:'summary',value:summary},
                {id:'updateDate',value:updateDate},
                {id:'tags',value:newsTags.join(",")},
                {id:'content',value:content},
                {id:'editor',value:editor},
                {id:'source',value:source},
                //{id:'image',value:image},
                {id:'pageType',value:pageType},
                {id:'pageSize',value:pageSize}
            ]);
            if (image == null || image == "") {
                document.getElementById('imagePreview').src = '../images/no.jpg';
            } else {
                document.getElementById('imagePreview').src = "../" + image;
            }

            if (layout.getRegion('south').collapsed) {
                layout.getRegion('south').expand();
            }
        });
        menuData.load();
    };

    lightGrid.toolbar.items.items[0].setHandler(addNews)
    lightGrid.toolbar.items.items[1].setHandler(editNews)
});

