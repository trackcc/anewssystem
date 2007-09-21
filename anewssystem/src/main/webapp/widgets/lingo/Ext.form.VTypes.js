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
 // 闭包，只创建一次
Ext.form.VTypes = function() {
    // 英文字母，空格，下划线
    var alpha = /^[a-zA-Z\ _]+$/;
    // 英文字幕，数字，空格，下划线
    var alphanum = /^[a-zA-Z0-9\ _]+$/;
    // 电子邮件
    var email = /^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/;
    // url
    var url = /(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i;
    // 中文
    var chn = /[^\xB0-\xF7]+$/; // davi

    // 下列信息和方法都是可配置的
    return {
        // email
        'email' : function(v){
            return email.test(v);
        },
        'emailText' : 'This field should be an e-mail address in the format "user@domain.com"',
        'emailMask' : /[a-z0-9_\.\-@]/i,

        // url
        'url' : function(v){
            return url.test(v);
        },
        'urlText' : 'This field should be a URL in the format "http:/'+'/www.domain.com"',

        // 英文字母
        'alpha' : function(v){
            return alpha.test(v);
        },
        'alphaText' : 'This field should only contain letters and _',
        'alphaMask' : /[a-z\ _]/i,

        // 英文字母和数字和下划线和点
        'alphanum' : function(v){
            return alphanum.test(v);
        },
        'alphanumText' : 'This field should only contain letters, numbers and _',
        'alphanumMask' : /[a-z0-9\.\ _]/i,//davi

        // 整数------
        'integer' : function(v){
            return alphanum.test(v);
        },
        'integerText' : 'This field should only contain letters, integer',
        'integerMask' : /[0-9]/i,

        // 数值型
        'number' : function(v){
            var sign = v.indexOf('-')>=0 ? '-' : '+';
            return IsNumber(v, sign); // double.test(v);
        },
        'numberText' : 'This field should only contain letters, number',
        'numberMask' : /[0-9\.]/i,

        // 汉字和英文字母
        'chn' : function(v){
            return chn.test(v);
        },
        'chnText' : 'This field should only contain letters, number',
        'chnMask' : /[\xB0-\xF7a-z0-9\.\/\#\,\ _-]/i
    };
}();