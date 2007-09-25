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
    var grid = new Ext.grid.Grid('grid-example', {
      ds             : ds,
      cm             : colModel,
      enableDragDrop : true
    });
    var grid2 = new Ext.grid.Grid('grid-example2', {
      ds             : ds2,
      cm             : colModel,
      enableDragDrop : true
    });

    var ddrow = new Ext.dd.DropTarget(grid.container, {
      ddGroup : 'GridDD',
      copy    : false,
      notifyDrop : function(dd, e, data){
        // 判断是本表格内拖动，还是表格间拖动
        var rows = grid2.getSelectionModel().getSelections();
        var source;
        if (rows.length > 0) {
            source = ds2;
        } else {
            rows = grid.getSelectionModel().getSelections();
            source = ds;
        }
        var index;
        try {
            index = dd.getDragData(e).rowIndex;
            if (typeof(index) == "undefined") {
                index = 0;
            }
        } catch (e) {
            index = 0;
        }
        for(i = 0; i < rows.length; i++) {
            var rowData = source.getById(rows[i].id);
            if(!this.copy) source.remove(rowData);
            ds.insert(index, rowData);
        }
      }
    });

    var ddrow2 = new Ext.dd.DropTarget(grid2.container, {
      ddGroup : 'GridDD',
      copy    : false,
      notifyDrop : function(dd, e, data){
        // 判断是本表格内拖动，还是表格间拖动
        var rows = grid.getSelectionModel().getSelections();
        var source;
        if (rows.length > 0) {
            source = ds;
        } else {
            rows = grid2.getSelectionModel().getSelections();
            source = ds2;
        }
        var index;
        try {
            index = dd.getDragData(e).rowIndex;
            if (typeof(index) == "undefined") {
                index = 0;
            }
        } catch (e) {
            index = 0;
        }
        for(var i = 0; i < rows.length; i++) {
            var rowData = source.getById(rows[i].id);
            if(!this.copy) source.remove(rowData);
            ds2.insert(index, rowData);
        }
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


    grid.render();
    grid2.render();
    grid.getSelectionModel().selectFirstRow();
  }

};

Ext.onReady(Example.init, Example);
