var menuManage = function(){

    //建立DataGrid 对象
    //@dlgContentDiv: 弹出框内容
    //@dlgWidth: 弹出框宽度
    //@dlgHeight: 弹出框高度
    //@dlgTitle: 弹出框标题
    //@initPaging: Grid分页数据读取路径
    //@paging: 是否分页
    //@pageSize: 每页数据条数
    //@exec: 业务对象,对应dwr.xml
    var grid = new Ext.DataGrid({
        gridDiv: 'datagrid',
        dlgTitle: '菜单设置',
        dlgContentDiv: 'content',
        dlgWidth: 560,
        dlgHeight: 360,
        paging: true,
        pageSize: 15,
        exec: 'MenuHelper'
    });
    // return a public interface
    return {
        init : function(){
            // 打开提示功能
            Ext.QuickTips.init();

            Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
            var layout = new Ext.BorderLayout(document.body, {
                center: {
                    autoScroll:true,
                    titlebar: false,
                    tabPosition: 'top',
                    closeOnTab: true,
                    alwaysShowTabs: true,
                    resizeTabs: true,
                    fillToFrame: true
                }
            });

            layout.beginUpdate();
                var tb = new Ext.Toolbar('toolbar');
                //绑定表格对象
                tb.bind(grid);

                //设置工具条按钮
                //@ text:按钮名称
                //@ cls:按钮样式表
                //@ dlgWidth:弹出框宽度
                //@ dlgHeight:弹出框高度
                //@ btnText:弹出框提交按钮名称
                //@ tooltip:按钮标签
                tb.add(
                    {text: '新增', cls: 'add',  btnText: '保存'}, '-',
                    {text: '删除', cls: 'del',  btnText: '确定'}, '-',
                    {text: '修改', cls: 'edit', btnText: '保存'}
                );
                Forms.createQuicksearch({tb: tb}, function(value){
                    var param = [{pName: 'name', pMore: 'like', pValue: value, pType:'String'}];
                    grid.render(param);
                });
                layout.add('center', new Ext.ContentPanel('tab1', {
                    title: '菜单设置',
                    toolbar: tb,
                    closable: false,
                    fitToFrame:true
                }));
/*
                layout.add('center', new Ext.ContentPanel('tab2', {
                    title: "帮助",
                    toolbar: tb,
                    closable: false,
                    fitToFrame: true
                }));
*/
                layout.restoreState();
            layout.endUpdate();

            //Grid列定义
            //@ header:列名称
            //@ width :列宽度
            //@ align :对齐方式
            //@ sortable:是否排序
            //@ type: (link:'查看'链接)
            grid.setHeader([
                {header: "",         width: 30,  sortable: true, type: 'link'},
                {header: "菜单名称", width: 100, sortable: true, type: 'link'},
                {header: "访问路径", width: 200, sortable: true, align: 'left'},
                {header: "上级ID",   width: 80,  sortable: true, align: 'left'},
                {header: "排序",     width: 40,  sortable: true, align: 'left'},
                {header: "操作",     width: 240, sortable: true, align: 'left'}
            ]);
            grid.defaultSort = {field: 'theSort', dir: 'desc'};
            grid.render();
        }
    };
}();

Ext.onReady(menuManage.init, menuManage, true);
