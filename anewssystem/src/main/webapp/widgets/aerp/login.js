/*
 * Ext JS Library 1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author Lingo
 * @since 2007-11-02
 * http://code.google.com/p/anewssystem/
 */
Ext.onReady(function(){

    // 开启提示功能
    Ext.QuickTips.init();

    // 使用cookies保持状态
    // TODO: 完全照抄，作用不明
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    var form = new Ext.form.Form({
        labelAlign:'right',
        labelWidth:100,
        url:'../j_acegi_security_check',
        method: 'POST',
        waitMsgTarget:'login-box'
    });

    var username = new Ext.form.TextField({
        name:'j_username',
        fieldLabel:'用户名',
        width:175,
        allowBlank:false
    });
    var password = new Ext.form.TextField({
        name:'j_password',
        inputType:'password',
        fieldLabel:'密　码',
        width:175,
        allowBlank:false
    });
    var rememberMe = new Ext.form.Checkbox({
        name:'_acegi_security_remember_me',
        fieldLabel:'记住我',
        width:175,
        boxLabel:'保存登录信息'
    });
    var captcha = new Ext.form.TextField({
        name:'j_captcha_response',
        fieldLabel:'验证码',
        width:175,
        allowBlank:false
    });

    form.fieldset(
        {id:'login',legend:'登录'},
        username,
        password,
        rememberMe,
        captcha
    );

    form.addButton('提交');
    form.addButton('重置');
    form.render('login-form');

    form.buttons[0].addListener("click", function() {
        if (form.isValid()) {
            form.submit({
                success:function(form, action){
                    Ext.suggest.msg('信息', '登录成功');
					window.open("index.htm", '_self');
                }
                ,failure:function(form, action){
                    Ext.suggest.msg('错误', action.result.response);
                }
                ,waitMsg:'登录中...'
            });
        }
    });

    form.buttons[1].addListener("click", function() {
        form.reset();
    });

    // 创建彩色验证码图片
    var login = Ext.get("login");

    var c = login.createChild({
        tag:'center',
        cn:[{
            tag:'img',
            id:'imageCaptcha',
            src:'../captcha.jpg?' + new Date().getTime(),
            style:'cursor:pointer;border:0px black solid;',
            onclick:'changeCaptcha()'
        },{
            tag:'a',
            id:'tip',
            html:'刷新图片',
            href:'#',
            onclick:'changeCaptcha()'
        }]
    });

});

Ext.suggest = function(){
    var msgCt;

    function createBox(t, s){
        return ['<div class="msg">',
                '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
                '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>', t, '</h3>', s, '</div></div></div>',
                '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
                '</div>'].join('');
    }
    return {
        msg : function(title, format){
            if(!msgCt){
                msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
            }
            msgCt.alignTo(document, 't-t');
            var s = String.format.apply(String, Array.prototype.slice.call(arguments, 1));
            var m = Ext.DomHelper.append(msgCt, {html:createBox(title, s)}, true);
            m.slideIn('t').pause(1).ghost("t", {remove:true});
        },

        init : function(){
            var s = Ext.get('extlib'), t = Ext.get('exttheme');
            if(!s || !t){ // run locally?
                return;
            }
            var lib = Cookies.get('extlib') || 'ext',
                theme = Cookies.get('exttheme') || 'aero';
            if(lib){
                s.dom.value = lib;
            }
            if(theme){
                t.dom.value = theme;
                Ext.get(document.body).addClass('x-'+theme);
            }
            s.on('change', function(){
                Cookies.set('extlib', s.getValue());
                setTimeout(function(){
                    window.location.reload();
                }, 250);
            });

            t.on('change', function(){
                Cookies.set('exttheme', t.getValue());
                setTimeout(function(){
                    window.location.reload();
                }, 250);
            });
        }
    };
}();

function changeCaptcha() {
    var imageCaptcha = document.getElementById("imageCaptcha");
    imageCaptcha.src = '../captcha.jpg?' + new Date().getTime();
}


