Ext.form.VTypes = function(){
    // closure these in so they are only created once.
    var alpha = /^[a-zA-Z\ _]+$/;
    var alphanum = /^[a-zA-Z0-9\ _]+$/;
    var email = /^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/;
    var url = /(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i;

    var chn = /[^\xB0-\xF7]+$/;//davi

    // All these messages and functions are configurable
    return {
        //email
        'email' : function(v){
            return email.test(v);
        },
        'emailText' : 'This field should be an e-mail address in the format "user@domain.com"',
        'emailMask' : /[a-z0-9_\.\-@]/i,

        //url
        'url' : function(v){
            return url.test(v);
        },
        'urlText' : 'This field should be a URL in the format "http:/'+'/www.domain.com"',

        //英文字母
        'alpha' : function(v){
            return alpha.test(v);
        },
        'alphaText' : 'This field should only contain letters and _',
        'alphaMask' : /[a-z\ _]/i,

        //英文字母和数字和下划线和点
        'alphanum' : function(v){
            return alphanum.test(v);
        },
        'alphanumText' : 'This field should only contain letters, numbers and _',
        'alphanumMask' : /[a-z0-9\.\ _]/i,//davi

        //整数------
        'integer' : function(v){
            return alphanum.test(v);
        },
        'integerText' : 'This field should only contain letters, integer',
        'integerMask' : /[0-9]/i,

        //数值型
        'number' : function(v){
            var sign = v.indexOf('-')>=0 ? '-' : '+';
            return IsNumber(v, sign);//double.test(v);
        },
        'numberText' : 'This field should only contain letters, number',
        'numberMask' : /[0-9\.]/i,

        //汉字和英文字母
        'chn' : function(v){
            return chn.test(v);
        },
        'chnText' : 'This field should only contain letters, number',
        'chnMask' : /[\xB0-\xF7a-z0-9\.\/\#\,\ _-]/i
    };
}();