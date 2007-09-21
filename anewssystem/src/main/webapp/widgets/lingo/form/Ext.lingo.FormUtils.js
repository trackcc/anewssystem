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
        createTextField : function(meta) {
            var field = new Ext.form.TextField({
                allowBlank : meta.allowBlank == undefined ? false : meta.allowBlank,
                vType      : meta.vType,
                cls        : meta.type == "password" ? meta.cls : null,
                width      : meta.vWidth,
                id         : meta.id,
                name       : meta.id,
                style      : (meta.vType == "integer" || meta.vType == "number" ? "text-align: right;" : ""),
                readOnly   : meta.readOnly,
                defValue   : meta.defValue,
                alt        : meta.alt,
                maxLength  : meta.maxlength ? meta.maxlength : Number.MAX_VALUE,
                minLength  : meta.minlength ? meta.minlength : 0,
                minValue   : meta.minvalue ? meta.minvalue : 0,
                maxValue   : meta.maxvalue ? meta.maxvalue : Number.MAX_VALUE
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
        }, createDateField : function(meta) {
            var field = new Ext.form.DateField({
                id          : meta.id,
                name        : meta.id,
                allowBlank  : meta.allowBlank == undefined ? false : eval(meta.allowBlank),
                format      : meta.format ? meta.format : "Y年m月d日",
                readOnly    : true,
                width       : meta.vWidth,
                defValue    : meta.defValue,
                vType       : "date",
                alt         : meta.alt,
                setAllMonth : meta.setAllMonth ? el.setAllMonth : false
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
        },

        // 为对话框，生成div结构
        createDialogContent : function(meta) {
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
        },

        // 生成一个modal为true的对话框
        createDialog : function(meta) {
            var id = meta.id;
            var width = meta.width ? meta.width : 600;
            var height = meta.height ? meta.height : 400;
            var dialog = new Ext.BasicDialog(id, {
                modal     : false,
                autoTabs  : true,
                width     : width,
                height    : height,
                shadow    : false,
                minWidth  : 200,
                minHeight : 100,
                closable  : true,
                autoCreate : true
            });
            return dialog;
        }
    };
}();
