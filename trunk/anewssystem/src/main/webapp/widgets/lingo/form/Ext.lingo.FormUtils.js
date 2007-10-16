/*
 * Ext JS Library 1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author Lingo
 * @since 2007-09-19
 * http://code.google.com/p/anewssystem/
 */
/**
 * 声明Ext.lingo命名控件
 * TODO: 完全照抄，作用不明
 */
Ext.namespace("Ext.lingo");
/**
 * 封装表单操作的工具类.
 *
 */
Ext.lingo.FormUtils = function() {
    var isApply = true;
    return {
        // 创建<input type="text">输入框
        createTextField : function(meta) {
            var field = new Ext.form.TextField({
                allowBlank  : meta.allowBlank == undefined ? false : meta.allowBlank
                , vType     : meta.vType
                , cls       : meta.type == "password" ? meta.cls : null
                , width     : meta.vWidth
                , id        : meta.id
                , name      : meta.id
                , style     : (meta.vType == "integer" || meta.vType == "number" ? "text-align: right;" : "")
                , readOnly  : meta.readOnly
                , defValue  : meta.defValue
                , alt       : meta.alt
                , maxLength : meta.maxlength ? meta.maxlength : Number.MAX_VALUE
                , minLength : meta.minlength ? meta.minlength : 0
                , minValue  : meta.minvalue ? meta.minvalue : 0
                , maxValue  : meta.maxvalue ? meta.maxvalue : Number.MAX_VALUE
                , mapping   : meta.mapping
            });
            if(meta.readOnly) {
                field.style += "color:#656B86;";
            }
            //if(meta.value != "" && meta.format == "date") {
            //    field.value = datagrids[0].date(meta.value);
            //}
            if (isApply) {
                field.applyTo(meta.id);
            }
            if(meta.defValue) {
                field.setValue(meta.defValue);
            }
            return field;
        }

        // 创建textarea文本框
        , createTextArea : function(meta) {

            var field = new Ext.form.TextArea({
                allowBlank  : meta.allowBlank == undefined ? false : meta.allowBlank
                , vType     : meta.vType
                , width     : meta.vWidth
                , id        : meta.id
                , name      : meta.id
                , readOnly  : meta.readOnly
                , defValue  : meta.defValue
                , alt       : meta.alt
                , maxLength : meta.maxlength ? meta.maxlength : Number.MAX_VALUE
                , minLength : meta.minlength ? meta.minlength : 0
                , minValue  : meta.minvalue ? meta.minvalue : 0
                , maxValue  : meta.maxvalue ? meta.maxvalue : Number.MAX_VALUE
                , mapping   : meta.mapping
            });
            if(meta.readOnly) {
                field.style += "color:#656B86;";
            }
            if (isApply) {
                field.applyTo(meta.id);
            }
            if(meta.defValue) {
                field.setValue(meta.defValue);
            }

            return field;
        }

        // 创建日期选择框
        , createDateField : function(meta) {
            var field = new Ext.form.DateField({
                id            : meta.id
                , name        : meta.id
                , allowBlank  : meta.allowBlank == undefined ? false : eval(meta.allowBlank)
                , format      : meta.format ? meta.format : "Y年m月d日"
                , readOnly    : true
                , width       : meta.vWidth
                , defValue    : meta.defValue
                , vType       : "date"
                , alt         : meta.alt
                , setAllMonth : meta.setAllMonth ? el.setAllMonth : false
                , mapping    : meta.mapping
            });

            if (isApply) {
                field.applyTo(meta.id);
            }
            if(meta.defValue) {
                field.setValue(meta.defValue);
            } else {
                field.setValue(new Date());
            }
            return field;
        }

        // 创建下拉框
        , createComboBox : function(meta) {
            var field = new Ext.form.ComboBox({
                id              : meta.id
                , name          : meta.id
                , readOnly      : meta.readOnly !== false
                , triggerAction : "all"
                , allowBlank    : meta.allowBlank == undefined ? false : eval(meta.allowBlank)
                , transform     : meta.id
                , vType         : "comboBox"
                , width         : meta.vWidth
                , mapping       : meta.mapping
            });
            return field;
        }

        // 创建单选框
        , createRadio : function(meta) {
            var fields = new Array();

            for (var k = 0; k < meta.values.length; k++) {
                var value = meta.values[k];

                var field = new Ext.form.Radio({
                    fieldLabel : meta.qtip
                    , name     : meta.id
                    , boxLabel : value.name
                    , value    : value.id
                    , vType    : "radio"
                    , mapping  : meta.mapping
                });
                if (meta.defValue && value.id == meta.defValue) {
                    field.fireEvent("check");
                    field.checked = true;
                    document.getElementById(meta.id + value.id).checked = true;;
                }
                if (isApply) {
                    field.applyTo(meta.id + value.id);
                }
                // 横向排列
                field.el.dom.parentNode.style.display = "inline";
                fields[fields.length] = field;
            }
            return fields;
        }

        // 创建复选框
        , createCheckBox : function(meta) {
            var field = new Ext.form.Checkbox({
                id        : meta.id
                , name    : meta.id
                , vType   : 'checkbox'
                , mapping : meta.mapping
            });
            if (isApply) {
                field.applyTo(meta.id);
            }
            return field;
        }

        // 创建treeField
        , createTreeField : function(meta) {
            var el = Ext.get(meta.id).dom;
            var config = {
                title          : meta.qtip
                , rootId       : 0
                , height       : 200
                , dataTag      : meta.url
                , treeHeight   : 150
                , beforeSelect : function(){}
            };
            var field = new Ext.lingo.TreeField({
                id           : el.id
                , name       : el.id
                , allowBlank : false
                , treeConfig : config
                , mapping    : meta.mapping
            });
            field.vType = "treeField";

            //if(不是EditorGrid && 不是Form) object.applyTo(el.id);
            if (isApply) {
                field.applyTo(el.id);
            }
            return field;
        }

        // 生成密码框
        , createPasswordField : function(meta) {
            var field = new Ext.form.TextField({
                id           : meta.id
                , allowBlank : meta.allowBlank == undefined ? false : meta.allowBlank
                , cls        : 'password'
                , name       : meta.id
                , mapping    : meta.mapping
            });
            field.vType = "password";
            if (isApply) {
                field.applyTo(meta.id);
            }
            return field;
        }

        // 生成检测密码强度的密码框
        , createPasswordFieldMeta : function(meta) {
            var field = new Ext.ux.PasswordMeter({
                id           : meta.id
                , allowBlank : meta.allowBlank == undefined ? false : meta.allowBlank
                , cls        : 'password'
                , name       : meta.id
                , mapping    : meta.mapping
            });
            field.vType = "password";
            if (isApply) {
                field.applyTo(meta.id);
            }
            return field;
        }

        // 生成在线编辑器
        , createHtmlEditor : function(meta) {

            var field = new Ext.form.HtmlEditor({
                id        : meta.id
                , name    : meta.name
                , mapping : meta.mapping
                , width   : '100%'
                , height  : '40%'
            });
            field.vType = "editor";
            if (isApply) {
                //field.render(body);
                field.applyTo(meta.id);
            }

            return field;
        }

        // 根据输入的数组，生成所有的表单字段
        , createAll : function(metaArray) {
            var columns = {};
            for (var i = 0; i < metaArray.length; i++) {
                var meta = metaArray[i];

                try {
                    if (meta.vType == "date") {
                        var field = Ext.lingo.FormUtils.createDateField(meta);
                        columns[meta.id] = field;
                    } else if (meta.vType == "comboBox") {
                        var field = Ext.lingo.FormUtils.createComboBox(meta);
                        columns[meta.id] = field;
                    } else if (meta.vType == "textArea") {
                        var field = Ext.lingo.FormUtils.createTextArea(meta);
                        columns[meta.id] = field;
                    } else if (meta.vType == "password") {
                        var field = Ext.lingo.FormUtils.createPasswordField(meta);
                        columns[meta.id] = field;
                    } else if (meta.vType == "passwordmeta") {
                        var field = Ext.lingo.FormUtils.createPasswordFieldMeta(meta);
                        columns[meta.id] = field;
                    } else if (meta.vType == "treeField") {
                        var field = Ext.lingo.FormUtils.createTreeField(meta);
                        columns[meta.id] = field;
                    } else if (meta.vType == "radio") {
                        var fields = Ext.lingo.FormUtils.createRadio(meta);
                        for (var j = 0; j < fields.length; j++) {
                            columns[meta.id + fields[j].el.dom.value] = fields[j];
                        }
                    } else if (meta.vType == "editor") {
                        var field = Ext.lingo.FormUtils.createHtmlEditor(meta);
                        columns[meta.id] = field;
                    } else {
                        var field = Ext.lingo.FormUtils.createTextField(meta);
                        columns[meta.id] = field;
                    }
                } catch (e) {
                    console.error(e);
                    console.error(meta);
                }
            }
            return columns;
        }

        // 根据field数组，生成一组用来发送到服务器端的json
        // columns = [TextField, TreeField, Radio, CheckBox, ComboBox, DateField]
        , serialFields : function(columns) {
            var item = {};
            for (var i in columns) {
                var obj = columns[i];
                if(!obj.isValid()) {
                    Ext.suggest.msg('错误：', String.format(obj.invalidText, obj.id));
                    obj.focus();
                    return false;
                }

                var key = obj.mapping ? obj.mapping : obj.id;

                if (obj.vType == "radio") {
                    key = key ? key : obj.name;
                    if (obj.el.dom.checked) {
                        item[key] = obj.el.dom.value;
                    }
                } else if (obj.vType == "treeField") {
                    item[key] = obj.selectedId;
                } else if (obj.vType == "date") {
                    item[key] = obj.getRawValue();
                } else {
                    item[key] = obj.getValue();
                }
            }
            return item;
        }

        // 传入field数组，全部reset，为添加信息做准备
        , resetFields : function(columns) {
            for (var i in columns) {
                var obj = columns[i];
                if (obj.vType == "integer") {
                    obj.setValue(0);
                } else if(obj.vType == "date") {
                    if(obj.defValue) {
                        obj.setValue(obj.defValue);
                    } else {
                        obj.setValue(new Date());
                    }
                } else {
                    obj.reset();
                }
            }
        }

        // 为对话框，生成div结构
        , createDialogContent : function(meta) {
            var id = meta.id;
            var title = meta.title ? meta.title : " 详细配置 ";

            // 内容
            var dialogContent = document.getElementById(id);
            var contentDiv = document.createElement("div");
            contentDiv.id = id + "-content";
            contentDiv.appendChild(dialogContent);

            // 消息
            var dialogMessage = document.createElement("div");
            var waitMessage = document.createElement("div");
            var waitText = document.createElement("div");
            dialogMessage.id = "dlg-msg";
            waitMessage.id = "post-wait";
            waitMessage.className = "posting-msg";
            waitText.className = "waitting";
            waitText.innerHTML = "正在保存，请稍候...";
            waitMessage.appendChild(waitText);
            dialogMessage.appendChild(waitMessage);

            // 对话框需要的外框
            var dialogDiv = document.createElement("div");
            var dialog_head = document.createElement("div");
            var dialog_body = document.createElement("div");
            var dlg_tab = document.createElement("div");
            var dlg_help = document.createElement("div");
            var helpContent = document.createElement("div");
            var dialog_foot = document.createElement("div");
            dialogDiv.id = id + "-dialog-content";
            dialogDiv.style.visibility = "hidden";
            dialog_head.className = "x-dlg-hd";
            dialog_body.className = "x-dlg-bd";
            dialog_foot.className = "x-dlg-ft";
            dlg_tab.className = "x-dlg-tab";
            dlg_tab.title = title;
            dlg_help.className = "x-dlg-tab";
            dlg_help.title = " 帮助 ";
            helpContent.innerHTML = "<div id='help-content'><div id='standard-panel'>帮助...</div></div><div id='temp-content'></div>";
            dlg_help.appendChild(helpContent);
            dialog_body.appendChild(dlg_tab);
            dialog_body.appendChild(dlg_help);
            dialog_foot.appendChild(dialogMessage);
            dialogDiv.appendChild(dialog_head);
            dialogDiv.appendChild(dialog_body);
            dialogDiv.appendChild(dialog_foot);

            document.body.appendChild(dialogDiv);
            document.body.appendChild(contentDiv);
        }

        // 生成一个有指定tab的对话框，各自对话框的标题与id都被分别指定了
        // id = id
        // titles = ['title1','title2']
        , createTabedDialog : function(id, titles, width, height) {

            // 消息
            var dialogMessage = document.createElement("div");
            var waitMessage = document.createElement("div");
            var waitText = document.createElement("div");
            dialogMessage.id = "dlg-msg";
            waitMessage.id = "post-wait";
            waitMessage.className = "posting-msg";
            waitText.className = "waitting";
            waitText.innerHTML = "正在保存，请稍候...";
            waitMessage.appendChild(waitText);
            dialogMessage.appendChild(waitMessage);

            // 对话框
            var dialogDiv = document.createElement("div");
            dialogDiv.id = id;
            dialogDiv.style.visibility = "hidden";
            var dialog_head = document.createElement("div"); // 页头
            dialog_head.className = "x-dlg-hd";
            var dialog_body = document.createElement("div"); // 内容
            dialog_body.className = "x-dlg-bd";
            var dialog_foot = document.createElement("div"); // 页脚
            dialog_foot.className = "x-dlg-ft";
            for (var i = 0; i < titles.length; i++) {
                var tab = document.createElement("div");
                tab.className = "x-dlg-tab";
                tab.title = titles[i];
                dialog_body.appendChild(tab);
            }
            dialogDiv.appendChild(dialog_head);
            dialogDiv.appendChild(dialog_body);
            dialogDiv.appendChild(dialog_foot);
            document.body.appendChild(dialogDiv);

            var dialog = new Ext.BasicDialog(id, {
                modal        : false
                , autoTabs   : true
                , width      : width ? width : 600
                , height     : height ? height : 400
                , shadow     : false
                , minWidth   : 200
                , minHeight  : 100
                , closable   : true
                , autoCreate : true
                , title      : '&nbsp;'
            });

            // ESC键关闭对话框
            dialog.addKeyListener(27, dialog.hide, dialog);

            return dialog;
        }

        // 生成一个modal为true的对话框
        , createDialog : function(meta) {
            var id = meta.id;
            var width = meta.width ? meta.width : 600;
            var height = meta.height ? meta.height : 400;
            var dialog = new Ext.BasicDialog(id, {
                modal        : false
                , autoTabs   : true
                , width      : width
                , height     : height
                , shadow     : false
                , minWidth   : 200
                , minHeight  : 100
                , closable   : true
                , autoCreate : true
            });
            return dialog;
        }

        // 新建可布局的对话框
        , createLayoutDialog : function(dialogName, width, height) {
            var thisDialog = new Ext.LayoutDialog(dialogName, {
                modal       : false
                , autoTabs  : true
                , proxyDrag : true
                , resizable : true
                , width     : width ? width : 650
                , height    : height ? height : 500
                , shadow    : true
                , center    : {
                    autoScroll       : true
                    , tabPosition    : 'top'
                    , closeOnTab     : true
                    , alwaysShowTabs : false
                }
            });
            thisDialog.addKeyListener(27, thisDialog.hide, thisDialog);
            thisDialog.addButton('关闭', function() {thisDialog.hide();});

            return thisDialog;
        }

        // 为grid渲染性别
        , renderSex : function(value) {
            return value == '0' ? '<span style="font-weight:bold;color:red">男</span>' : '<span style="font-weight:bold;color:green;">女</span>';
        }
    };
}();
