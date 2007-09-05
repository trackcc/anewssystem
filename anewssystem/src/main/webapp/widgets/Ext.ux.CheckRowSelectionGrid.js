Ext.namespace('Ext.ux');

// --- Ext.ux.CheckRowSelectionGrid --- //

Ext.ux.CheckRowSelectionGrid = function(container, config) {

	var id = this.root_cb_id = Ext.id(null, 'cbsm-');
	var cb_html = String.format("<input type='checkbox' id='{0}'/>", this.root_cb_id);
	var grid = this;
	var cm = config.cm;
	// Hack
	var cm = config.cm || config.colModel;
	cm.config.unshift({
		id: id,
		header: cb_html,
		width: 20,
		resizable: false,
		fixed: true,
		sortable: false,
		dataIndex: -1,
		renderer: function(data, cell, record, rowIndex, columnIndex, store) { 
			return String.format(
				"<input type='checkbox' id='{0}-{1}' {2}/>",
				id,
				rowIndex,
				grid.getSelectionModel().isSelected(rowIndex) ? "checked='checked'" : ''
			);
		}			
	});
	cm.lookup[id] = cm.config[0];

	Ext.ux.CheckRowSelectionGrid.superclass.constructor.call(this, container, config);
}

Ext.extend(Ext.ux.CheckRowSelectionGrid, Ext.grid.Grid, {

	root_cb_id : null,

	getSelectionModel: function() {
		if (!this.selModel) {
			this.selModel = new Ext.ux.CheckRowSelectionModel();
		}
		return this.selModel;
	}
});


Ext.ux.CheckRowSelectionModel = function(options)
{
	Ext.ux.CheckRowSelectionModel.superclass.constructor.call(this, options);
}

Ext.extend(Ext.ux.CheckRowSelectionModel, Ext.grid.RowSelectionModel, {

	init: function(grid)
	{
		Ext.ux.CheckRowSelectionModel.superclass.init.call(this, grid);

		// Start of dirty hacking
		// Hacking grid
		if (grid.processEvent) {
			grid.__oldProcessEvent = grid.processEvent;
			grid.processEvent = function(name, e) 
			{
				// The scope of this call is the grid object
				var target = e.getTarget();
				var view = this.getView();
				var header_index = view.findHeaderIndex(target);
				if (name == 'contextmenu' && header_index === 0) {
					return;
				}
				this.__oldProcessEvent(name, e);
			}
		}

		// Hacking view
		var gv = grid.getView();
		if (gv.beforeColMenuShow) {
			gv.__oldBeforeColMenuShow = gv.beforeColMenuShow;
			gv.beforeColMenuShow = function() 
			{ 
				// The scope of this call is the view object
				this.__oldBeforeColMenuShow();
				// Removing first - checkbox column from the column menu
		    this.colMenu.remove(this.colMenu.items.first()); // he he
			}
		}
		// End of dirty hacking
	},

	initEvents: function()
	{
		Ext.ux.CheckRowSelectionModel.superclass.initEvents.call(this);
		this.grid.getView().on('refresh', this.onGridRefresh, this);
	},

	selectRow: function(index, keepExisting, preventViewNotify)
	{
		Ext.ux.CheckRowSelectionModel.superclass.selectRow.call(
			this, index, keepExisting, preventViewNotify
		);

		var row_id = this.grid.root_cb_id + '-' + index;
		var cb_dom = Ext.fly(row_id).dom;
		cb_dom.checked = true;
	},

	deselectRow: function(index, preventViewNotify)
	{
		Ext.ux.CheckRowSelectionModel.superclass.deselectRow.call(
			this, index, preventViewNotify
		);

		var row_id = this.grid.root_cb_id + '-' + index;
		var cb_dom = Ext.fly(row_id).dom;
		cb_dom.checked = false;
	},

	onGridRefresh: function()
	{
		Ext.fly(this.grid.root_cb_id).on('click', this.onAllRowsCheckboxClick, this, {stopPropagation:true});
		// Attaching to row checkboxes events
		for (var i = 0; i < this.grid.getDataSource().getCount(); i++) {
			var cb_id = this.grid.root_cb_id + '-' + i;
			Ext.fly(cb_id).on('mousedown', this.onRowCheckboxClick, this, {stopPropagation:true});
		}
	},

	onAllRowsCheckboxClick: function(event, el)
	{
		if (el.checked) {
			this.selectAll();
		}
		else {
			this.clearSelections();
		}
	},

	onRowCheckboxClick: function(event, el) 
	{
		var rowIndex = /-(\d+)$/.exec(el.id)[1];
		if (el.checked) {		
			this.deselectRow(rowIndex);
			el.checked = true;		
		}
		else {
			this.selectRow(rowIndex, true);
			el.checked = false;
		}
	}
});
// --- end of Ext.ux.CheckRowSelectionGrid --- //