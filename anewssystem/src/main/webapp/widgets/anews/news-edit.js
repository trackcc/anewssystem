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

    var form = new Ext.form.Form({
        fileUpload:true,
        labelAlign:'right',
        labelWidth:75,
        url:'insert.htm',
        method: 'POST',
        enctype:"multipart/form-data"
    });

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
});
