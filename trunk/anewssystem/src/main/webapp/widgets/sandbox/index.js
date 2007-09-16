var index = function(){
    var layout;
    return {
        init : function(){
            // initialize state manager, we will use cookies
            //Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
            // create the main layout
            layout = new Ext.BorderLayout(document.body, {
                north: {
                    split:false,
                    initialSize: 40,
                    titlebar: false
                }, west: {
                    split:true,
                    initialSize: 200,
                    minSize: 150,
                    maxSize: 400,
                    titlebar: true,
                    collapsible: true,
                    animate: true,
                    useShim: true//,
                    //cmargins: {top:2,bottom:2,right:2,left:2}
                }, center: {
                    titlebar: false,
                    title: '',
                    autoScroll: false
                }
            });
            // tell the layout not to perform layouts until we're done adding everything
            var innerLayout = new Ext.BorderLayout('south', {
                center: {
                    autoScroll: false,
                    split: true ,
                    titlebar: false,
                    collapsible: true,
                    showPin: true,
                    animate: true
                }, south: {
                    initialSize: '26px',
                    autoScroll: false,
                    split: false
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
            this.setLoginName('尖叫的土豆 监制');
            MenuHelper.init(this.render);
        },

        createToolbar : function(){
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
            tb.add({cls: 'theme', text:'系统风格', menu: menu});
        },

        getLayout : function() {
            return layout;
        },

        setLoginName : function(user) {
            user = user == null ? '' : user;
            this.menuLayout.updateTitle(user);
        },

        loadMain : function(url){
            this.iframe.src = url;
            Cookies.set('xrinsurMainSrc', url);
        },

        render : function(data) {
            var menuList = new Array();
            var menuArray = new Array();
            var lastParent = 0;
            for(i = 0; i < data.length; i++) {
                var child = data[i].children;
                menuArray = new Array();
                for (j = 0; j < child.length; j++) {
                    var col = "{image:'" + child[j].image + "',title:'" + child[j].name + "',href:'" + child[j].url + "', parentImg:'{0}'}";
                    menuArray = menuArray.concat(col);
                }
                if (menuArray.length > 0) {
                    menuArray[0] = String .format(menuArray[0], data[i].image);
                }
                menuList = menuList.concat("'" + data[i].name + "':[" + menuArray + "]");
            }

            menuList = objectEval("{" + menuList + "}");
            Ext.get('menu-tree').update('');
            var hdt = new Ext.Template('<div><div>{head}</div></div>')//hdt.compile();
            var bdt = new Ext.Template('<div></div>');//bdt.compile();

            var itemTpl = new Ext.Template(
                '<div class="dl-group-child">' +
                  '<div class="dl-selection">' +
                    '<img src=../widgets/extjs/1.1/resources/images/default/user/tree/{image}>&nbsp;' +
                    '<span>{title}</span>' +
                  '</div>' +
                '</div>'
            );
            itemTpl.compile();
            var acc = new Ext.Accordion('menu-tree', {
                boxWrap: false,
                body: 'menu-tree',
                fitContainer: true,
                fitToFrame: true,
                fitHeight: true,
                useShadow: true,
                initialHeight: layout.getRegion("west").el.getHeight() - 50
            });
            for(var g in menuList) {
                var group = menuList[g];
                var hd = hdt.append('menu-tree', {head: g});
                var bd = bdt.append(hd);
                acc.add(new Ext.InfoPanel(hd, {icon: '../widgets/extjs/1.1/resources/images/default/user/menu/' + (group[0] ? group[0].parentImg : '')}));
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
        }
    };
}();
function info(o) {
    var buff = "";
    for (var i in o) {
        buff += "" + i + "," + o[i] + "\n";
    }
    return buff;
}
Ext.onReady(index.init, index, true);
