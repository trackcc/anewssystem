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
var index = function() {
    var layout;
    return {
        init : function() {
            // 开启提示功能
            Ext.QuickTips.init();

            // 使用cookies保持状态
            // TODO: 完全照抄，作用不明
            Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

            // 创建主面板
            layout = new Ext.BorderLayout(document.body, {
                north: {
                    split       : false,
                    initialSize : 40,
                    titlebar    : false
                }, west: {
                    split       : true,
                    initialSize : 200,
                    minSize     : 150,
                    maxSize     : 400,
                    titlebar    : true,
                    collapsible : true,
                    animate     : true,
                    useShim     : true,
                    cmargins    : {top:2,bottom:2,right:2,left:2},
                    collapsed   : false
                }, center: {
                    titlebar   : false,
                    title      : '',
                    autoScroll : false
                }
            });
            // 让布局在我们安排了所以部分之后，再显示
            var innerLayout = new Ext.BorderLayout('west', {
                center: {
                    autoScroll  : false,
                    split       : true ,
                    titlebar    : false,
                    collapsible : true,
                    showPin     : true,
                    animate     : true
                }, south: {
                    initialSize : '26px',
                    autoScroll  : false,
                    split       : false
                }
            });
            this.createToolbar();
            innerLayout.add('center', new Ext.ContentPanel('menu-tree'));
            innerLayout.add('south', new Ext.ContentPanel('toolbar'));

            layout.beginUpdate();
                layout.add('north', new Ext.ContentPanel('header'));
                layout.add('west', new Ext.NestedLayoutPanel(innerLayout));
                layout.add('center', new Ext.ContentPanel('main', {title: '', fitToFrame: true}));
                layout.restoreState();
            layout.endUpdate();

            //------------------------------------------------------------------------------
            this.menuLayout = layout.getRegion("west");
            this.menuLayout.id = 'menuLayout';
            this.iframe = Ext.get('main').dom;
            this.loadMain('./welcome.htm');

            Ext.lib.Ajax.request(
                'POST',
                "../login/isLogin.htm",
                {success:function(data){
                    var json = eval("(" + data.responseText + ")");
                    if (json.success) {
                        this.setLoginName(json.response);
                    }
                }.createDelegate(this),failure:function(){
                }},
                ''
            );
            MenuHelper.getMenus(this.render);
        }

        // 注销
        , logout : function() {
            Ext.lib.Ajax.request(
                'POST',
                "../j_acegi_logout",
                {success:function(){
                    window.open("login.htm", "_self");
                },failure:function(){
                    window.open("login.htm", "_self");
                }},
                ''
            );

        }

        // 创建更换主题的菜单
        , createToolbar : function() {
            var theme = Cookies.get('xrinsurtheme') || 'aero'
            var menu = new Ext.menu.Menu({
                id: 'mainMenu',
                items: [
                    new Ext.menu.CheckItem({
                        id: 'aero',
                        text: '默认风格',
                        checked: (theme == 'aero' ? true : false),
                        group: 'theme',
                        handler: Ext.theme.apply
                    }),
                    new Ext.menu.CheckItem({
                        id: 'vista',
                        text: '夜色朦胧',
                        checked: (theme == 'vista' ? true : false),
                        group: 'theme',
                        handler: Ext.theme.apply
                    }),
                    new Ext.menu.CheckItem({
                        id: 'gray',
                        text: '秋意盎然',
                        checked: (theme == 'gray' ? true : false),
                        group: 'theme',
                        handler: Ext.theme.apply
                    }),
                    new Ext.menu.CheckItem({
                        id: 'default',
                        text: '蓝色回忆',
                        checked: (theme == 'default' ? true : false),
                        group: 'theme',
                        handler: Ext.theme.apply
                    })
                ]
            });
            var tb = new Ext.Toolbar('toolbar');
            tb.add({
                cls    : 'theme'
                , text : '系统风格'
                , menu : menu
            }, '-', {
                cls       : 'add'
                , text    : '退出'
                , handler : this.logout.createDelegate(this)
            });
        }

        // 获得layout
        , getLayout : function() {
            return layout;
        }

        // 设置登录名
        , setLoginName : function(user) {
            user = user == null ? '' : user;
            this.menuLayout.updateTitle("登录用户：" + user);
        }

        // 点左边，链接，右边的iframe更新
        , loadMain : function(url) {
            this.iframe.src = url;
            Cookies.set('xrinsurMainSrc', url);
        }

        // 渲染accordion菜单
        , render : function(data) {
            var menuList = data;

            Ext.get('menu-tree').update('');
            var hdt = new Ext.Template('<div><div>{head}</div></div>'); // hdt.compile();
            var bdt = new Ext.Template('<div></div>'); // bdt.compile();

            var itemTpl = new Ext.Template(
                '<div class="dl-group-child">' +
                  '<div class="dl-selection">' +
                    '<img src=../widgets/extjs/1.1/resources/images/default/user/tree/{image}>&nbsp;' +
                    '<span>{title}</span>' +
                  '</div>' +
                '</div>'
            );
            itemTpl.compile();
            var acc = new Ext.ux.Accordion('menu-tree', {
                boxWrap      : false,
                body         : 'menu-tree',
                fitContainer : true,
                fitToFrame   : true,
                draggable    : false,
                fitHeight    : true,
                useShadow    : false,
                initialHeight: layout.getRegion("west").el.getHeight() - 50
            });
            for(var g in menuList) {
                var group = menuList[g];
                var hd = hdt.append('menu-tree', {head: g});
                var bd = bdt.append(hd);
                acc.add(new Ext.ux.InfoPanel(hd, {autoScroll:true,icon: '../widgets/extjs/1.1/resources/images/default/user/menu/' + (group[0] ? group[0].parentImg : '')}));
                //, collapsed: true, showPin: false, collapseOnUnpin: true
                for(var i = 0; i < group.length; i++) {
                    var f = group[i];
                    var item = itemTpl.append(bd, f, true);
                    var cb = item.dom.firstChild.firstChild;
                    f.cb = cb;
                    //cb.disabled = (g == '<title>');
                    item.mon('click', function(e) {
                        if(e.getTarget() != this.cb && !this.cb.disabled){
                            index.loadMain(this.href ? './' + this.href : 'http://localhost:8080/');
                        }
                    }, f, true);
                    item.addClassOnOver('dl-selection-over');
                }
            }
            layout.getRegion("west").expand();
        }
    }
}();

Ext.onReady(index.init, index, true);



var Cookies = {
};

Cookies.set = function(H, B) {
  var F = arguments, I = arguments.length, E = (I > 3) ? F[3] : "/", C = (I > 4) ? F[4] : null, A = (I > 5) ? F[5] : false, D = 60, G = new Date();
  G.setTime(G.getTime() + D * 24 * 60 * 60 * 1000);
  document.cookie = H + "=" + escape(B) + ((G == null) ? "" : ("; expires=" + G.toGMTString())) + ((E == null) ? "" : ("; path=" + E)) + ((C == null) ? "" : ("; domain=" + C)) + ((A == true) ? "; secure":"");
};

Cookies.get = function(D) {
  var B = D + "=", F = B.length, E = document.cookie.length, A = 0, C = 0;
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
};

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
  document.write("<link id=\"theme\" rel=\"stylesheet\" type=\"text/css\" href=\"../widgets/extjs/1.1/resources/css/ytheme-" + xtheme + ".css\" />");
  document.write("<link id=\"theme-iframeLayout\" rel=\"stylesheet\" type=\"text/css\" href=\"../widgets/extjs/1.1/resources/css/ylayout.css\" />");
} else {
  document.write("<link id=\"theme\" rel=\"stylesheet\" type=\"text/css\" href=\"../widgets/extjs/1.1/resources/css/ytheme-" + xtheme + ".css\" />");
}
