/**
 * Copyright(c) 2006-2007, FeyaSoft Inc.
 *
 * This JS is mainly used to handle action in the list
 * and create, edit, delete.
 * There includes Search function, paging function etc
 * Create/update is using pop-up dialog
 * Delete can select multiple line
 */
Ext.onReady(function(){
    var myPageSize = 10;

    // shorthand alias
    var fm = Ext.form, Ed = Ext.grid.GridEditor;

    //we enable the QuickTips for the later Pagebar
    Ext.QuickTips.init();

    function formatBoolean(value){
        return value ? 'Yes' : 'No';
    }

    function formatDate(value){
        return value ? value.dateFormat('m d, Y') : '';
    }

   /************************************************************
    * Display the result in page
    * the column model has information about grid columns
    * dataIndex maps the column to the specific data field in
    * the data store (created below)
    ************************************************************/
    var cm = new Ext.grid.ColumnModel([{
           id: 'id',
           header: "序号",
           dataIndex: 'id',
           width: 150
        }, {
           header: "关键字",
           dataIndex: 'name',
           width: 90
        }
       ]);


    // by default columns are sortable
    cm.defaultSortable = false;

    // this could be inline, but we want to define the NewAccount record
    // type so we can add records dynamically
    var Tag = Ext.data.Record.create([
           {name: 'id', type: 'int'},
           {name: 'name', type: 'string'}
      ]);

   /************************************************************
    * connect to backend - grid - core part
    * create the Data Store
    *   connect with backend and list the result in page
    *   through JSON format
    ************************************************************/
    var ds = new Ext.data.Store({
      proxy: new Ext.data.DWRProxy(NewsTagHelper.getItems, true),
      reader: new Ext.data.ListRangeReader({
        totalProperty: 'totalCount',
        id: 'id'
      }, Tag),
      remoteSort: true
    });
    ds.setDefaultSort('id', 'ASC');

    // create the editor grid
    var grid = new Ext.ux.CheckRowSelectionGrid('editor-grid', {
        ds: ds,
        cm: cm,
        //selModel: new Ext.grid.CellSelectionModel(),
        //selModel: new Ext.grid.RowSelectionModel({singleSelect:false}),
        enableColLock:false
    });

    var layout = Ext.BorderLayout.create({
        center: {
            margins:{left:2,top:3,right:2,bottom:3},
            panels: [new Ext.GridPanel(grid)]
        }
    }, 'grid-panel');

    // render it
    grid.render();

   /************************************************************
    * create header panel
    * add filter field - search function
    ************************************************************/
    var gridHead = grid.getView().getHeaderPanel(true);
    var tb = new Ext.Toolbar(gridHead);

    filterButton = new Ext.Toolbar.MenuButton({
        icon: 'public/image/list-items.gif',
        cls: 'x-btn-text-icon',
        text: 'Choose Filter',
        tooltip: 'Select one of filter',
        menu: {items: [
            new Ext.menu.CheckItem({ text: 'name', value: 'name', checked: true, group: 'filter', checkHandler: onItemCheck })
        ]},
        minWidth: 105
    });
    tb.add(filterButton);

    // Create the filter field
    var filter = Ext.get(tb.addDom({ // add a DomHelper config to the toolbar and return a reference to it
         tag: 'input',
         type: 'text',
         size: '30',
         value: '',
         style: 'background: #F0F0F9;'
    }).el);

    // press enter keyboard
    filter.on('keypress', function(e) { // setup an onkeypress event handler
      if(e.getKey() == e.ENTER && this.getValue().length > 0) {// listen for the ENTER key
          ds.load({params:{start:0, limit:myPageSize}});
      }
    });

    filter.on('keyup', function(e) { // setup an onkeyup event handler
      if(e.getKey() == e.BACKSPACE && this.getValue().length === 0) {// listen for the BACKSPACE key and the field being empty
          ds.load({params:{start:0, limit:myPageSize}});
      }
    });

    // Update search button text with selected item
    function onItemCheck(item, checked) {
        if(checked) {
            filterButton.setText(item.text + ':');
        }
    }

   /************************************************************
    * create footer panel
    *    actions and paging
    ************************************************************/
    var gridFoot = grid.getView().getFooterPanel(true);

    // add a paging toolbar to the grid's footer
    var paging = new Ext.PagingToolbar(gridFoot, ds, {
        pageSize: myPageSize,
        displayInfo: true,
        displayMsg: 'total {2} results found. Current shows {0} - {1}',
        emptyMsg: "not result to display"
    });
    // add the detailed add button
    paging.add('-', {
        pressed: true,
        enableToggle:true,
        text: 'Add',
        cls: '',
        toggleHandler: doAdd
    });
    // add the detailed delete button
    paging.add('-', {
        pressed: true,
        enableToggle:true,
        text: 'Delete',
        cls: '',
        toggleHandler: doDel
    });
    // --- end -- create foot panel

   /************************************************************
    * load parameter to backend
    *    have beforelaod function
    ************************************************************/
    ds.on('beforeload', function() {
      ds.baseParams = { // modify the baseParams setting for this request
        filterValue: filter.getValue(),// retrieve the value of the filter input and assign it to a property named filter
        filterTxt: filterButton.getText()
      };
    });
    // trigger the data store load
    ds.load({params:{start:0, limit:myPageSize}});

   /************************************************************
    * Action - delete
    *   start to handle delete function
    *   need confirm to delete
    ************************************************************/
    function doDel(){
        var m = grid.getSelections();
        if(m.length > 0) {
            Ext.MessageBox.confirm('Message', 'Do you really want to delete them?' , doDel2);
        } else {
            Ext.MessageBox.alert('Error', 'Please select at least one item to delete');
        }
    }

    function doDel2(btn) {
      if(btn == 'yes') {
        var m = grid.getSelections();
        var data = new Array();
        for (var i = 0, len = m.length; i < len; i++) {
          data.push(m[i].get("id"));
          ds.remove(m[i]);
        }
        NewsTagHelper.removeAll(data);
      }
    }

   /************************************************************
    *  Add an "clickoutside" event to a Grid.
    *     @param grid {Ext.grid.Grid} The grid to add a clickoutside event to.
    *     The handler is passed the Event and the Grid.
    ************************************************************/
    function addClickOutsideEvent(grid) {
        if (!grid.events.clickoutside) {
            grid.addEvents({"clickoutside": true});
        }
        if (!Ext.grid.Grid.prototype.handleClickOutside) {
            Ext.grid.Grid.override({
                handleClickOutside: function(e) {
                    var p = Ext.get(e.getTarget()).findParent(".x-grid-container");
                    if (p != this.container.dom) {
                        this.fireEvent("clickoutside", e, grid);
                    }
                }
            });
        }
        Ext.get(document.body).on("click", grid.handleClickOutside, grid);
    }

   /************************************************************
    * Create a new dialog - reuse by create and edit part
    ************************************************************/
    function createNewDialog(dialogName) {
        var thisDialog = new Ext.LayoutDialog(dialogName, {
            modal:true,
            autoTabs:true,
            proxyDrag:true,
            resizable:false,
            width: 480,
            height: 302,
            shadow:true,
            center: {
                autoScroll: true,
                tabPosition: 'top',
                closeOnTab: true,
                alwaysShowTabs: false
            }
        });
        thisDialog.addKeyListener(27, thisDialog.hide, thisDialog);
        thisDialog.addButton('Cancel', function() {thisDialog.hide();});

        return thisDialog;
    };

   /************************************************************
    * Action - update
    *   handle double click
    *   user select one of the item and want to update it
    ************************************************************/
    grid.on('rowdblclick', function(grid, rowIndex, e) {
        var selectedId = ds.data.items[rowIndex].id;

        // get information from DB and set form now...
        var account_data = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url:'loadData.htm?id=' + selectedId}),
            reader: new Ext.data.JsonReader({},['id','name']),
            remoteSort: false
        });

        account_data.on('load', function() {
            console.info(account_data);
            // set value now
            var updateId = account_data.getAt(0).data['id'];
            name_show.setValue(account_data.getAt(0).data['name']);
            console.info(account_data.getAt(0).data['name']);

            var updateInstanceDlg;
            if (!updateInstanceDlg) {
                updateInstanceDlg = createNewDialog("a-updateInstance-dlg");
                updateInstanceDlg.addButton('Save', function() {

                    // submit now... all the form information are submit to the server
                    // handle response after that...
                    if (form_instance_update.isValid()) {
                        form_instance_update.submit({
                            params:{id : updateId},
                            waitMsg:'更新数据...',
                            reset: false,
                            failure: function(form_instance_update, action) {
                                Ext.MessageBox.alert('Error Message', action.result.errorInfo);
                            },
                            success: function(form_instance_update, action) {
                                Ext.MessageBox.alert('Confirm', action.result.info);
                                updateInstanceDlg.hide();
                                ds.load({params:{start:0, limit:myPageSize}});
                            }
                        });
                    }else{
                        Ext.MessageBox.alert('Errors', 'Please fix the errors noted.');
                    }
                });

                var layout = updateInstanceDlg.getLayout();
                layout.beginUpdate();
                layout.add('center', new Ext.ContentPanel('a-updateInstance-inner', {title: 'Update Account'}));
                layout.endUpdate();

                updateInstanceDlg.show();
            }
        });

        account_data.load();
    });



   /************************************************************
    * Action - add
    *   start to handle add function
    *   new page appear and allow to submit
    ************************************************************/

   /************************************************************
    *  To create add new account dialog now....
    ************************************************************/
    function doAdd() {
        var aAddInstanceDlg;

        if (!aAddInstanceDlg) {
            aAddInstanceDlg = createNewDialog("a-addInstance-dlg");
            aAddInstanceDlg.addButton('Reset', resetForm, aAddInstanceDlg);
            aAddInstanceDlg.addButton('Save', function() {

                // submit now... all the form information are submit to the server
                // handle response after that...
                if (form_instance_create.isValid()) {
                    form_instance_create.submit({
                        waitMsg:'Creating new account now...',
                        reset: false,
                        failure: function(form_instance_create, action) {
                            var info =
                            Ext.MessageBox.alert('Error Message', action.result.errorInfo);
                        },
                        success: function(form_instance_create, action) {
                            Ext.MessageBox.alert('Confirm', action.result.info);
                            aAddInstanceDlg.hide();
                            ds.load({params:{start:0, limit:myPageSize}});
                        }
                    });
                }else{
                    Ext.MessageBox.alert('Errors', 'Please fix the errors noted.');
                }
            });

            var layout = aAddInstanceDlg.getLayout();
            layout.beginUpdate();
            layout.add('center', new Ext.ContentPanel('a-addInstance-inner', {title: 'create account'}));
            layout.endUpdate();

            resetForm();
            aAddInstanceDlg.show();
        }
    }

   /************************************************************
    * Form information - pop-up dialog
    * turn on validation errors beside the field globally
    ************************************************************/
    Ext.form.Field.prototype.msgTarget = 'side';
    Ext.form.Field.prototype.height = 20;
    Ext.form.Field.prototype.fieldClass = 'text-field-default';
    Ext.form.Field.prototype.focusClass = 'text-field-focus';
    Ext.form.Field.prototype.invalidClass = 'text-field-invalid';

    /************************************************************
    * Create new form to hold user enter information
    * This form is used to create new instance
    ************************************************************/
    var name_tf = new Ext.form.TextField({
        fieldLabel: '关键字',
        name: 'name',
        allowBlank:false
    });
    var form_instance_create = new Ext.form.Form({
        labelAlign: 'right',
        url:'insertRow.htm'
    });

    form_instance_create.column({width: 430, labelWidth:120, style:'margin-left:8px;margin-top:8px'});
    form_instance_create.fieldset(
        {id:'desc', legend:'Please fill the field'},
        name_tf
    );

    form_instance_create.applyIfToFields({width:255});
    form_instance_create.render('a-addInstance-form');
    form_instance_create.end();

    resetForm = function() {
        name_tf.setValue('');
    };


    /************************************************************
    * Create form to hold user enter information
    * This form is used to update current instance
    ************************************************************/
    var name_show = new Ext.form.TextField({
        // labelStyle: 'display: none',
        fieldLabel: '关键字',
        name: 'name',
        readOnly: false,
        allowBlank:false
    });
    var form_instance_update = new Ext.form.Form({
        labelAlign: 'right',
        url:'updateRow.htm'
    });

    form_instance_update.column({width: 430, labelWidth:120, style:'margin-left:8px;margin-top:8px'});
    form_instance_update.fieldset(
        {id:'', legend:'修改'},
        name_show
    );

    form_instance_update.applyIfToFields({width:255});
    form_instance_update.render('a-updateInstance-form');
    form_instance_update.end();

    //右键菜单
    function contextmenu(grid, rowIndex,e){
        e.preventDefault();
        e.stopEvent();
        var infoMenu = new Ext.menu.Item({
            id:'infoMenu',
            text: 'infoMenu',
            handler:function(){
                Ext.MessageBox.alert("info",Ext.util.JSON.encode(grid.dataSource.getAt(rowIndex).data));
            }
        });
        var menuList = [infoMenu];

        grid_menu = new Ext.menu.Menu({
            id: 'mainMenu',
            items: menuList
        });

        var coords = e.getXY();
        grid_menu.showAt([coords[0], coords[1]]);
    }
    grid.addListener('rowcontextmenu', contextmenu);
});
