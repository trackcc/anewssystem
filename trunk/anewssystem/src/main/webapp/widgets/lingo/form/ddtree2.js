/*
* Ext JS Library 1.0
* Copyright(c) 2006-2007, Ext JS, LLC.
* licensing@extjs.com
*
* http://www.extjs.com/license
*/



var Example = {
  init : function(){
    // some data yanked off the web
    var myData = [
      ['3m Co',                                       71.72, 0.02, 0.03, '9/1 12:00am'],
      ['Alcoa Inc'                                  , 29.01, 0.42, 1.47, '9/1 12:00am'],
      ['Walt Disney Company (The) (Holding Company)', 29.89, 0.24, 0.81, '9/1 12:00am']
    ];
    var myData2 = [
      ['临远', '0.0', '0.0',  '0.0', '9/24 12:00am'],
      ['test', '1.0', '1.0',  '1.0', '9/24 12:00am'],
      ['dodo', '2.0', '-2.0', '2.0', '9/24 12:00am']
    ];

    var ds = new Ext.data.Store({
      proxy: new Ext.data.MemoryProxy(myData),
      reader: new Ext.data.ArrayReader({}, [
        {name: 'company'},
        {name: 'price', type: 'float'},
        {name: 'change', type: 'float'},
        {name: 'pctChange', type: 'float'},
        {name: 'lastChange', type: 'date', dateFormat: 'n/j h:ia'}
      ])
    });
    ds.load();

    var ds2 = new Ext.data.Store({
      proxy: new Ext.data.MemoryProxy(myData2),
      reader: new Ext.data.ArrayReader({}, [
        {name: 'company'},
        {name: 'price', type: 'float'},
        {name: 'change', type: 'float'},
        {name: 'pctChange', type: 'float'},
        {name: 'lastChange', type: 'date', dateFormat: 'n/j h:ia'}
      ])
    });
    ds2.load();

    // example of custom renderer function
    function italic(value){
      return '<i>' + value + '</i>';
    }

    // example of custom renderer function
    function change(val){
      if(val > 0){
        return '<span style="color:green;">' + val + '</span>';
      }else if(val < 0){
        return '<span style="color:red;">' + val + '</span>';
      }
      return val;
    }
    // example of custom renderer function
    function pctChange(val){
      if(val > 0){
        return '<span style="color:green;">' + val + '%</span>';
      }else if(val < 0){
        return '<span style="color:red;">' + val + '%</span>';
      }
      return val;
    }

    // the DefaultColumnModel expects this blob to define columns. It can be extended to provide
    // custom or reusable ColumnModels
    var colModel = new Ext.grid.ColumnModel([
      {header: "Company",      width: 260, sortable: true, dataIndex: 'company',    locked:false},
      {header: "Price",        width: 75,  sortable: true, dataIndex: 'price',      renderer: Ext.util.Format.usMoney},
      {header: "Change",       width: 75,  sortable: true, dataIndex: 'change',     renderer: change},
      {header: "% Change",     width: 75,  sortable: true, dataIndex: 'pctChange',  renderer: pctChange},
      {header: "Last Updated", width: 85,  sortable: true, dataIndex: 'lastChange', renderer: Ext.util.Format.dateRenderer('m/d/Y')}
    ]);

    // create the Grid
    var grid = new Ext.grid.Grid('grid-div', {
      ds             : ds,
      cm             : colModel,
      enableDragDrop : true
    });
    var grid2 = new Ext.grid.Grid('grid-div2', {
      ds             : ds2,
      cm             : colModel,
      enableDragDrop : true
    });

    var ddrow = new Ext.dd.DropTarget(grid.container, {
      ddGroup : 'GridDD',
      copy    : false,
      notifyDrop : function(dd, e, data){
      }
    });

    var ddrow2 = new Ext.dd.DropTarget(grid2.container, {
      ddGroup : 'GridDD',
      copy    : false,
      notifyDrop : function(dd, e, data){
      }
    });

    var layout = Ext.BorderLayout.create({
      center: {
        margins : {left:1,top:1,right:1,bottom:1},
        panels  : [new Ext.GridPanel(grid)]
      }, south : {
        margins : {left:2,top:2,right:2,bottom:2},
        panels  : [new Ext.GridPanel(grid2)]
      }
    }, 'grid-panel');













        // basic record
        var Skill = Ext.data.Record.create([
            {name: 'id',          type: 'int'},
            {name: 'name',        type: 'string'},
            {name: 'description', type: 'string'},
            {name: 'rating',      type: 'int'}
        ]);

        // tree setup
        var skillsTreeLoader = new Ext.tree.TreeLoader({
            dataUrl : 'ddtree.json'
        });
        var skillsTree = new Ext.tree.TreePanel('tree-div', {
            animate         : true,
            loader          : skillsTreeLoader,
            containerScroll : true,
            rootVisible     : true,
            enableDD        : true,
            ddGroup         : 'GridDD'/*,
            dropConfig: {
                appendOnly : true
            }*/
        });
        skillsTree.on('nodedragover', function(e) {
            //console.error("tree");
        });
        skillsTree.on('beforenodedrop', function(e){
            //console.error(e);
		});
        // Set the root node
        var skillsTreeRoot = new Ext.tree.AsyncTreeNode({
            text      : 'Skills',
            draggable : true,
            id        : 'root'
        });
        skillsTree.setRootNode(skillsTreeRoot);

        skillsTree.render();
        skillsTreeRoot.expand();






    grid.render();
    grid2.render();
    grid.getSelectionModel().selectFirstRow();
  }

};

Ext.onReady(Example.init, Example);
