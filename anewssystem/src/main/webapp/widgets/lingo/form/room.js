Ext.onReady(function() {
    Ext.QuickTips.init();
    var areaid;

    var AreaDef = Ext.data.Record.create([
        {name: 'id'},{name: 'name'}
    ]);

    var BuildingDef = Ext.data.Record.create([
        {name: 'id'},{name: 'name'}
    ]);

    var areastore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'roomArea.json'}),
        reader: new Ext.data.JsonReader({id:'id',totalProperty:'results',root:'rows'},AreaDef)
    });
    areastore.load();

    var buildingstore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'roomBuilding.json'}),
        reader: new Ext.data.JsonReader({id:'id',totalProperty:'results',root:'rows'},BuildingDef)
    });
    // buildingstore.load();

    var top = new Ext.form.Form({
        labelAlign: 'top'
    });

    var area = new Ext.form.ComboBox({
        fieldLabel: '校区',
        hiddenName:'area',
        store: areastore,
        valueField:'id',
        displayField:'name',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'请选择',
        selectOnFocus:true,
        width:200
    });

    area.on('select',function() {
        areaid = area.getValue();
        buildingstore.load({
            params:{areaId:areaid}
        });
    });

    var building = new Ext.form.ComboBox({
        fieldLabel: '楼号',
        hiddenName:'building',
        store: buildingstore,
        valueField:'id',
        displayField:'name',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'请选择',
        selectOnFocus:true,
        width:200
    });

    top.column(
        {width:272},
        area,building
    );
    top.end();

    top.render('room');
});