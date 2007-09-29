/*
* Ext JS Library 1.0
* Copyright(c) 2006-2007, Ext JS, LLC.
* licensing@extjs.com
*
* http://www.extjs.com/license
*/

Ext.BLANK_IMAGE_URL = '../../extjs/1.1/resources/images/default/s.gif';

var Example = {
  init : function(){
    // some data yanked off the web
    var myData = [
      ['3m Co',                                       71.72, 0.02, 0.03, '9/1 12:00am'],
      ['Alcoa Inc'                                  , 29.01, 0.42, 1.47, '9/1 12:00am'],
      ['Walt Disney Company (The) (Holding Company)', 29.89, 0.24, 0.81, '9/1 12:00am']
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

    function renderCompany(value, cellmeta, record, RowIndex, ColumnIndex, store) {
        var str = "<input type='text' id='inputGrid_" + record.id + "' value='";
        if (value != null) {
            str += value;
        }
        str += "'>";
        return str;
    }

    // the DefaultColumnModel expects this blob to define columns. It can be extended to provide
    // custom or reusable ColumnModels
    var colModel = new Ext.grid.ColumnModel([
      {header: "Company",      width: 260, sortable: true, dataIndex: 'company',    renderer: renderCompany, unselectable:false, locked:false},
      {header: "Price",        width: 75,  sortable: true, dataIndex: 'price',      renderer: Ext.util.Format.usMoney},
      {header: "Change",       width: 75,  sortable: true, dataIndex: 'change',     renderer: change},
      {header: "% Change",     width: 75,  sortable: true, dataIndex: 'pctChange',  renderer: pctChange},
      {header: "Last Updated", width: 85,  sortable: true, dataIndex: 'lastChange', renderer: Ext.util.Format.dateRenderer('m/d/Y')}
    ]);

    // create the Grid
    var grid = new Ext.grid.Grid('grid-example', {
      ds             : ds,
      cm             : colModel,
      enableDragDrop : false
    });
    grid.on('rowdblclick', function() {
        var selections = grid.getSelections();
		var company = document.getElementById("inputGrid_" + selections[0].id).value;
		Ext.MessageBox.alert("警告撒", company);
    });

    var layout = Ext.BorderLayout.create({
      center: {
        margins : {left:1,top:1,right:1,bottom:1},
        panels  : [new Ext.GridPanel(grid)]
      }
    }, 'grid-panel');


    grid.render();
    grid.getSelectionModel().selectFirstRow();
  }

};

Ext.onReady(Example.init, Example);
