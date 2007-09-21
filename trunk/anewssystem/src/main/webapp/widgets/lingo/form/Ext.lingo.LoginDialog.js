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
 * 弹出式登录对话框.
 *
 */
Ext.lingo.LoginDialog = function() {
    var loginDialog, loginBtn, resetBtn, tabs, userAccount, userPwd;
    return {
        init : function() {
            userAccount = new Ext.lingo.FormUtils.createTextField({
                id         : 'userAccount',
                type       : 'text',
                vType      : 'alphanum',
                allowBlank : false,
                maxLength  : 18
            });
            userPwd = new Ext.lingo.FormUtils.createTextField({
                id         : 'userPassword',
                type       : 'password',
                vType      : 'alphanum',
                allowBlank : false,
                maxLength  : 18,
                cls        : 'x-form-text'
            });
            this.showLoginDialog();
        },

        showLoginDialog : function() {
            if(!loginDialog) {
                loginDialog = new Ext.BasicDialog("login-dlg", {
                        title     : '&nbsp;',
                        modal     : true,
                        autoTabs  : true,
                        width     : 500,
                        height    : 300,
                        shadow    : true,
                        minWidth  : 300,
                        minHeight : 300,
                        closable  : false,
                        resizable : true
                });

                loginBtn = loginDialog.addButton('登陆', this.logIn, this);
                resetBtn = loginDialog.addButton('重写', this.loginReset, this);
                tabs =loginDialog.getTabs();
                tabs.getTab(0).on('activate', function() {
                    resetBtn.show();
                    loginBtn.show()
                }, this, true);
                tabs.getTab(1).on('activate', function() {
                    resetBtn.hide();
                    loginBtn.hide()
                }, this, true);
            }
            loginDialog.show();
        },

        hideLoginDialog : function() {
            loginDialog.hide();
        },

        showHideBtns : function(f) {
            var msg = Ext.get('post-wait');
            if (f) {
                tabs.getTab(1).enable();
                resetBtn.enable();
                loginBtn.enable();
                msg.removeClass('active-msg')
            } else {
                tabs.getTab(1).disable();
                resetBtn.disable();
                loginBtn.disable();
                msg.radioClass('active-msg');
            }
        },

        checkInput : function() {
            if(userAccount.isValid() == false) {
                Msg.suggest('错误：', String.format(userAccount.invalidText, userAccount.el.dom.alt));
                userAccount.focus();
                return false;
            }
            if(userPwd.isValid() == false) {
                Msg.suggest('错误：', String.format(userPwd.invalidText, userPwd.el.dom.alt));
                userPwd.focus();
                return false;
            }
            return true;
        },

        loginReset : function() {
            userAccount.setValue("");
            userPwd.setValue("");
        },

        logIn : function() {
            if(this.checkInput()) {
                this.showHideBtns(false);
                alert('login...');
                this.showHideBtns(true);
                this.hideLoginDialog();
            }
        }
    };
}();
