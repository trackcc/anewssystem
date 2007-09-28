/*
 * Ext JS Library 1.1
 * Copyright(c) 2006-2007, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://www.extjs.com/license
 *
 * @author Lingo
 * @since 2007-09-13
 * http://code.google.com/p/anewssystem/
 */
// cookieºÍÖ÷Ìâ
var Cookies = {
};

Cookies.set = function(H, B) {
  var F = arguments;
  var I = arguments.length;
  var E = (I > 3) ? F[3] : "/";
  var C = (I > 4) ? F[4] : null;
  var A = (I > 5) ? F[5] : false;
  var D = 60;
  var G = new Date();
  G.setTime(G.getTime() + D * 24 * 60 * 60 * 1000);
  document.cookie = H + "=" + escape(B) + ((G == null) ? "" : ("; expires=" + G.toGMTString())) + ((E == null) ? "" : ("; path=" + E)) + ((C == null) ? "" : ("; domain=" + C)) + ((A == true) ? "; secure":"");
};

Cookies.get = function(D) {
  var B = D + "=";
  var F = B.length;
  var E = document.cookie.length;
  var A = 0;
  var C = 0;
  while(A < E) {
    C = A + F;
    if(document.cookie.substring(A, C) == B) {
      return Cookies.getCookieVal(C);
    }
    A = document.cookie.indexOf(" ", A) + 1;
    if(A == 0) {
      break;
    }
  }
  return null;
};

Cookies.clear = function(A) {
  if(Cookies.get(A)) {
    document.cookie = A + "=" + "; expires=Thu, 01-Jan-70 00:00:01 GMT";
  }
} ;

Cookies.getCookieVal = function(B) {
  var A = document.cookie.indexOf(";", B);
  if(A == -1) {
    A = document.cookie.length;
  }
  return unescape(document.cookie.substring(B, A));
};

var xtheme = Cookies.get("xrinsurtheme");
if(!xtheme) {
  xtheme = "aero";
  Cookies.set("xrinsurtheme", xtheme);
}
var xthemePath = document.location.pathname;

if(xthemePath.indexOf(".html") >= 0 && xthemePath.indexOf("index.htm") < 0 && xthemePath.indexOf("welcome.htm") < 0) {
  document.write("<link id=\"theme\" rel=\"stylesheet\" type=\"text/css\" href=\"../extjs/1.1/resources/css/ytheme-" + xtheme + ".css\" />");
  document.write("<link id=\"theme-iframeLayout\" rel=\"stylesheet\" type=\"text/css\" href=\"../extjs/1.1/resources/css/ylayout.css\" />");
} else {
  document.write("<link id=\"theme\" rel=\"stylesheet\" type=\"text/css\" href=\"../extjs/1.1/resources/css/ytheme-" + xtheme + ".css\" />");
}


Ext.suggest = function() {
    var msgCt;

    function createBox(t, s){
        return ['<div class="msg">',
                '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
                '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>', t, '</h3>', s, '</div></div></div>',
                '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
                '</div>'].join('');
    }
    return{
        msg : function(title, format){
            if(!msgCt){
                msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
            }
            msgCt.alignTo(document, 't-t');
            var s = String.format.apply(String, Array.prototype.slice.call(arguments, 1));

            var m = Ext.DomHelper.append(msgCt, {html:createBox(title, s)}, true);

            m.slideIn('t').pause(1).ghost("t", {remove:true});
        }
    }
}();

Ext.theme = function() {
    return{
        apply:function(themeName) {
            themeName = themeName.id ? themeName.id : themeName;
            Cookies.set("xrinsurtheme", themeName);
            Ext.util.CSS.swapStyleSheet("theme", getSitePath() + "/widgets/extjs/1.1/resources/css/ytheme-" + themeName + ".css");
            //var iframe = Ext.isGecko ? document.getElementById("main").contentWindow : parent.frames["main"];
            var iframe = document.getElementById("main").contentWindow;
            iframe.Ext.util.CSS.swapStyleSheet("theme", getSitePath() + "/widgets/extjs/1.1/resources/css/ytheme-" + themeName + ".css");
            iframe.Ext.util.CSS.swapStyleSheet("theme-iframeLayout", getSitePath() + "/widgets/extjs/1.1/resources/css/layout.css")
        }
    }
} ();
var path = getSitePath();

function getSitePath() {
    var protocol = document.location.protocol;
    var host = document.location.host;
    var pathname = document.location.pathname;
    var search = document.location.search;
    var sitePath = protocol + "//" + host + "/" + (pathname.split("/"))[1];
    return sitePath;
}

function setSitePath(query) {
    return path + "/" + query;
}