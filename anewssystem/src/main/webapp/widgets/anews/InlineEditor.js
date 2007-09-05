// JavaScript Document
Ext.onReady (function() {
  function formatBoolean(value){
    return value ? 'Yes' : 'No';
  };

  function formatDate(value){
    return value ? value.dateFormat('M, d, Y') : '';
  };

  deleteRow = function(){
    alert('确定要删除?');
    var sm = grid.getSelectionModel();
    var cell = sm.getSelectedCell();
    var ds = grid.getDataSource();
    var record = ds.getAt(cell[0]);
    var doSuccess = function() {
      ds.remove(record);
    };
    NewsTagHelper.removeRow(record.id, doSuccess);
  }

  function deleteRowRender(value){
    var htmlString = '<input title="delete" value="x" type="button" class="delete" onclick="deleteRow()"/>';
    return htmlString;
  }

  var cm = new Ext.grid.ColumnModel([{
    header: "序号",
    dataIndex: "id",
    width: 100
  }, {
    header: "关键字",
    dataIndex: "name",
    width: 100,
    editor: new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank: false}))
  }, {
    id: "deleteRow",
    header: "删除",
    dataIndex: "deleteRow",
    renderer: deleteRowRender,
    width: 50
  }]);
  cm.defaultSortable = true;//排序

  var Tag = Ext.data.Record.create([
    {name: "id", type: "int"},
    {name: "name", type: "string"}
  ]);

  var ds = new Ext.data.Store({
    proxy: new Ext.data.DWRProxy(NewsTagHelper.getItems, true),
    reader: new Ext.data.ListRangeReader({
      totalProperty: 'totalCount',
      id: 'id'
    }, Tag),
    remoteSort: true
  });

  var grid = new Ext.grid.EditorGrid("grid-example", {ds: ds, cm: cm, enableColLock: false});

  var layout = Ext.BorderLayout.create({
    center: {margins:{left:30,top:30,right:100,bottom:100}, panels: [new Ext.GridPanel(grid)]}
  }, document.body);

  // make the grid resizable, do before render for better performance
  var rz = new Ext.Resizable('grid-example', {
    wrap:true,
    minHeight:100,
    pinned:true,
    handles:'s'
  });
  rz.on('resize', grid.autoSize, grid);

  grid.render();

  var gridHead = grid.getView().getHeaderPanel(true);

  var tb = new Ext.Toolbar(gridHead, [{
    text: '新增关键字',
    handler : function(){
      var tag = new Tag({
        id: -1,
        name: ''
      });
      grid.stopEditing();
      ds.insert(0, tag);
      grid.startEditing(0, 1);
    }
  }, {
    text: '保存',
    handler: function() {
      grid.stopEditing();
      var data = new Array();
      for (var i = 0; i < ds.getCount(); i++) {
        var record = ds.getAt(i);
        if (record.data.id == -1 || record.dirty) {
          data.push(record.data.name);
        }
      }
      var doSuccess = function() {
        //alert("完成");
        ds.load({start:0,limit:10});
      };
      NewsTagHelper.save(data, doSuccess);
    }
  }]);

  var gridFoot = grid.getView().getFooterPanel(true);

  // add a paging toolbar to the grid's footer
  var paging = new Ext.PagingToolbar(gridFoot, ds, {
    pageSize: 10,
    displayInfo: true,
    displayMsg: '显示 {0} - {1} 共 {2}',
    emptyMsg: "没有数据"
  });

  //查询时需要用到的参数
  //ds.on('beforeload', function() {
  //  ds.baseParams = {
  //    param1: 'test111',
  //    param2: true
  //  };
  //});

  //分页基本参数
  ds.load({params:{start:0, limit:10}});
});
