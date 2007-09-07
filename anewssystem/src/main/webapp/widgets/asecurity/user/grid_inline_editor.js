// JavaScript Document
Ext.onReady (function() {
  function formatBoolean(value){
    return value ? 'Yes' : 'No';
  };

  function formatDate(value){
    return value ? value.dateFormat('M, d, Y') : '';
  };

  var cm = new Ext.grid.ColumnModel([{
      header: "姓名",
      dataIndex: "name",
      width: 100,
      editor: new Ext.grid.GridEditor(new Ext.form.TextField({allowBlank: false}))
    }, {
      header: "性别",
      dataIndex: "sex",
      width: 100,
      editor: new Ext.grid.GridEditor(new Ext.form.ComboBox({
        typeAhead: true,//自动完成
        triggerAction: "all",//
        transform: "sex",//列表(select)元素
        lazyRender: true
      }))
    }, {
      header: "工资",
      dataIndex: "money",
      width: 100,
      align: "right",
      renderer: "usMoney",
      editor: new Ext.grid.GridEditor(new Ext.form.NumberField({
        allowBlank: false,//不允许空
        allowNegative: false,//不允许负数
        maxValue: 1000//最大值
      }))
    }, {header: "参加工作时间",
      dataIndex: "time",
      width: 100,
      renderer: formatDate,
      editor: new Ext.grid.GridEditor(new Ext.form.DateField({
        format: "m/d/y",
        minValue: "01/01/06",
        disabledDays: [0,6],//不允许的日期
        disabledDaysText: "不允许为又休日"
      }))
    }, {
      header: "是否优秀",
      dataIndex: "isGood",
      width: 50,
      renderer: formatBoolean,
      editor: new Ext.grid.GridEditor(new Ext.form.Checkbox())
    }]);
    cm.defaultSortable = true;//排序

    var User = Ext.data.Record.create([
      {name: "name", type: "string"},
      {name: "address", type: "string"},
      {name: "sex"},
      {name: "money", type: "float"},
      {name: "time", mapping: "startworktime", type: "date", dateFormat: "m/d/Y"},
      {name: "isGood", type: "bool"}
    ]);

    var ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url: "userinfo.xml"}),
        reader: new Ext.data.XmlReader({record: "user"}, User)
    });

    var grid = new Ext.grid.EditorGrid("myeditordata", {ds: ds, cm: cm, enableColLock: false});

    var layout = Ext.BorderLayout.create({
      center: {margins:{left:3,top:3,right:3,bottom:3}, panels: [new Ext.GridPanel(grid)]}
    }, "mygridpanel");

    grid.render();

    var gridHead = grid.getView().getHeaderPanel(true);

    var tb = new Ext.Toolbar(gridHead, [{
      text: '新增人员信息',
      handler : function(){
        var p = new User({
          name: '姓名',
          sex: '男',
          money: 0,
          time: new Date(),
          isGood: false
        });
        grid.stopEditing();
        ds.insert(0, p);
        grid.startEditing(0, 0);
      }
    }]);
    // trigger the data store load
    ds.load();
  }
);
