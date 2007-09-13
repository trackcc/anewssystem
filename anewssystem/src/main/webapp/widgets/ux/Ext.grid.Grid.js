Ext.grid.Grid = function (container, config) {
    // 初始化容器
    this.container = Ext.get(container);
    this.container.update("");
    this.container.setStyle("overflow", "hidden");
    this.container.addClass("x-grid-container");

    this.id = this.container.id;

    Ext.apply(this, config);
    // 检测并确认配置的快捷方式
    if(this.ds) {
        this.dataSource = this.ds;
        delete this.ds;
    }
    if(this.cm) {
        this.colModel = this.cm;
        delete this.cm;
    }
    if(this.sm) {
        this.selModel = this.sm;
        delete this.sm;
    }

    if(this.width) {
        this.container.setWidth(this.width);
    }

    if(this.height) {
        this.container.setHeight(this.height);
    }
    /** @private */
    this.addEvents({
        // raw events
        /**
         * @event click
         * The raw click event for the entire grid.
         * @param {Ext.EventObject} e
         */
        "click":true,
        /**
         * @event dblclick
         * The raw dblclick event for the entire grid.
         * @param {Ext.EventObject} e
         */
        "dblclick":true,
        /**
         * @event contextmenu
         * The raw contextmenu event for the entire grid.
         * @param {Ext.EventObject} e
         */
        "contextmenu":true,
        /**
         * @event mousedown
         * The raw mousedown event for the entire grid.
         * @param {Ext.EventObject} e
         */
        "mousedown":true,
        /**
         * @event mouseup
         * The raw mouseup event for the entire grid.
         * @param {Ext.EventObject} e
         */
        "mouseup":true,
        /**
         * @event mouseover
         * The raw mouseover event for the entire grid.
         * @param {Ext.EventObject} e
         */
        "mouseover":true,
        /**
         * @event mouseout
         * The raw mouseout event for the entire grid.
         * @param {Ext.EventObject} e
         */
        "mouseout":true,
        /**
         * @event keypress
         * The raw keypress event for the entire grid.
         * @param {Ext.EventObject} e
         */
        "keypress":true,
        /**
         * @event keydown
         * The raw keydown event for the entire grid.
         * @param {Ext.EventObject} e
         */
        "keydown":true,

        // custom events

        /**
         * @event cellclick
         * Fires when a cell is clicked
         * @param {Grid} this
         * @param {Number} rowIndex
         * @param {Number} columnIndex
         * @param {Ext.EventObject} e
         */
        "cellclick":true,
        /**
         * @event celldblclick
         * Fires when a cell is double clicked
         * @param {Grid} this
         * @param {Number} rowIndex
         * @param {Number} columnIndex
         * @param {Ext.EventObject} e
         */
        "celldblclick":true,
        /**
         * @event rowclick
         * Fires when a row is clicked
         * @param {Grid} this
         * @param {Number} rowIndex
         * @param {Ext.EventObject} e
         */
        "rowclick":true,
        /**
         * @event rowdblclick
         * Fires when a row is double clicked
         * @param {Grid} this
         * @param {Number} rowIndex
         * @param {Ext.EventObject} e
         */
        "rowdblclick":true,
        /**
         * @event headerclick
         * Fires when a header is clicked
         * @param {Grid} this
         * @param {Number} columnIndex
         * @param {Ext.EventObject} e
         */
        "headerclick":true,
        /**
         * @event headerdblclick
         * Fires when a header cell is double clicked
         * @param {Grid} this
         * @param {Number} columnIndex
         * @param {Ext.EventObject} e
         */
        "headerdblclick":true,
        /**
         * @event rowcontextmenu
         * Fires when a row is right clicked
         * @param {Grid} this
         * @param {Number} rowIndex
         * @param {Ext.EventObject} e
         */
        "rowcontextmenu":true,
        /**
         * @event cellcontextmenu
         * Fires when a cell is right clicked
         * @param {Grid} this
         * @param {Number} rowIndex
         * @param {Number} cellIndex
         * @param {Ext.EventObject} e
         */
        "cellcontextmenu":true,
        /**
         * @event headercontextmenu
         * Fires when a header is right clicked
         * @param {Grid} this
         * @param {Number} columnIndex
         * @param {Ext.EventObject} e
         */
        "headercontextmenu":true,
        /**
         * @event bodyscroll
         * Fires when the body element is scrolled
         * @param {Number} scrollLeft
         * @param {Number} scrollTop
         */
        "bodyscroll":true,
        /**
         * @event columnresize
         * Fires when the user resizes a column
         * @param {Number} columnIndex
         * @param {Number} newSize
         */
        "columnresize":true,
        /**
         * @event columnmove
         * Fires when the user moves a column
         * @param {Number} oldIndex
         * @param {Number} newIndex
         */
        "columnmove":true,
        /**
         * @event startdrag
         * Fires when row(s) start being dragged
         * @param {Grid} this
         * @param {Ext.GridDD} dd The drag drop object
         * @param {event} e The raw browser event
         */
        "startdrag":true,
        /**
         * @event enddrag
         * Fires when a drag operation is complete
         * @param {Grid} this
         * @param {Ext.GridDD} dd The drag drop object
         * @param {event} e The raw browser event
         */
        "enddrag":true,
        /**
         * @event dragdrop
         * Fires when dragged row(s) are dropped on a valid DD target
         * @param {Grid} this
         * @param {Ext.GridDD} dd The drag drop object
         * @param {String} targetId The target drag drop object
         * @param {event} e The raw browser event
         */
        "dragdrop":true,
        /**
         * @event dragover
         * Fires while row(s) are being dragged. "targetId" is the id of the Yahoo.util.DD object the selected rows are being dragged over.
         * @param {Grid} this
         * @param {Ext.GridDD} dd The drag drop object
         * @param {String} targetId The target drag drop object
         * @param {event} e The raw browser event
         */
        "dragover":true,
        /**
         * @event dragenter
         *  Fires when the dragged row(s) first cross another DD target while being dragged
         * @param {Grid} this
         * @param {Ext.GridDD} dd The drag drop object
         * @param {String} targetId The target drag drop object
         * @param {event} e The raw browser event
         */
        "dragenter":true,
        /**
         * @event dragout
         * Fires when the dragged row(s) leave another DD target while being dragged
         * @param {Grid} this
         * @param {Ext.GridDD} dd The drag drop object
         * @param {String} targetId The target drag drop object
         * @param {event} e The raw browser event
         */
        "dragout":true,
        /**
         * @event render
         * Fires when the grid is rendered
         * @param {Grid} grid
         */
        render : true
    });

    Ext.grid.Grid.superclass.constructor.call(this);
};
Ext.extend(Ext.grid.Grid, Ext.util.Observable, {
    /**
     * @cfg {Number} minColumnWidth The minimum width a column can be resized to. Default is 25.
     */
    minColumnWidth:25,

    /**
     * @cfg {Boolean} autoSizeColumns True to automatically resize the columns to fit their content
     * <b>on initial render.</b> It is more efficient to explicitly size the columns
     * through the ColumnModel's {@link Ext.grid.ColumnModel#width} config option.  Default is false.
     */
    autoSizeColumns:false,

    /**
     * @cfg {Boolean} autoSizeHeaders True to measure headers with column data when auto sizing columns. Default is true.
     */
    autoSizeHeaders:true,

    /**
     * @cfg {Boolean} monitorWindowResize True to autoSize the grid when the window resizes. Default is true.
     */
    monitorWindowResize:true,

    /**
     * @cfg {Boolean} maxRowsToMeasure If autoSizeColumns is on, maxRowsToMeasure can be used to limit the number of
     * rows measured to get a columns size. Default is 0 (all rows).
     */
    maxRowsToMeasure:0,

    /**
     * @cfg {Boolean} trackMouseOver True to highlight rows when the mouse is over. Default is true.
     */
    trackMouseOver:true,

    /**
     * @cfg {Boolean} enableDragDrop True to enable drag and drop of rows. Default is false.
     */
    enableDragDrop:false,

    /**
     * @cfg {Boolean} enableColumnMove True to enable drag and drop reorder of columns. Default is true.
     */
    enableColumnMove:true,

    /**
     * @cfg {Boolean} enableColumnHide True to enable hiding of columns with the header context menu. Default is true.
     */
    enableColumnHide:true,

    /**
     * @cfg {Boolean} enableRowHeightSync True to manually sync row heights across locked and not locked rows. Default is false.
     */
    enableRowHeightSync:false,

    /**
     * @cfg {Boolean} stripeRows True to stripe the rows.  Default is true.
     */
    stripeRows:true,

    /**
     * @cfg {Boolean} autoHeight True to fit the height of the grid container to the height of the data. Default is false.
     */
    autoHeight:false,

    /**
     * @cfg {String} autoExpandColumn The id of a column in this grid that should expand to fill unused space. This id can not be 0. Default is false.
     */
    autoExpandColumn:false,

    /**
    * @cfg {Number} autoExpandMin The minimum width the autoExpandColumn can have (if enabled).
    * Default is 50.
    */
    autoExpandMin:50,

    /**
    * @cfg {Number} autoExpandMax The maximum width the autoExpandColumn can have (if enabled). Default is 1000.
    */
    autoExpandMax:1000,

    /**
     * @cfg {Object} view The {@link Ext.grid.GridView} used by the grid. This can be set before a call to render().
     */
    view:null,
    allowTextSelectionPattern:/INPUT|TEXTAREA|SELECT/i,

    /**
     * @cfg {Object} loadMask An {@link Ext.LoadMask} config or true to mask the grid while loading. Default is false.
     */
    loadMask:false,

    // private
    rendered:false,

    /**
    * @cfg {Number} maxHeight Sets the maximum height of the grid - ignored if autoHeight is not on.
    */
    /**
     * Called once after all setup has been completed and the grid is ready to be rendered.
     * @return {Ext.grid.Grid} this
     */
    render:function() {
        var D = this.container;
        // try to detect autoHeight/width mode
        if(D.getStyle("height").indexOf("%") < 0) {
            var C = D.dom.style.height, F = D.dom.style.minusHeight, B = document.documentElement.offsetHeight - 55;
            C = C.replace("px", "");
            if(F != undefined) {
                F = F.replace("px", "");
                C = B - F;
            } else if(D.getStyle("height") == "auto") {
                C = B;
            } else if(C.length == 4) {
                C = B - (C * 1 / 10) - 55;
            }
            var E = Cookies.get("xrinsurtheme");
            D.dom.style.height = C + "px";
        }
        var A = this.getView();
        A.init(this);
        D.on("click", this.onClick, this);
        D.on("dblclick", this.onDblClick, this);
        D.on("contextmenu", this.onContextMenu, this);
        D.on("keydown", this.onKeyDown, this);
        this.relayEvents(D, ["mousedown", "mouseup", "mouseover", "mouseout", "keypress"]);
        this.getSelectionModel().init(this);
        A.render();
        if(this.loadMask) {
            this.loadMask = new Ext.LoadMask(this.container, Ext.apply({
                store:this.dataSource
            }, this.loadMask));
        }
        this.rendered = true;
        return this;
    }, reconfigure:function(A, B) {
        if(this.loadMask) {
            this.loadMask.destroy();
            this.loadMask = new Ext.LoadMask(this.container, Ext.apply({
                store:A
            }, this.loadMask));
        }
        this.view.bind(A, B);
        this.dataSource = A;
        this.colModel = B;
        this.view.refresh(true);
    }, onKeyDown:function(A) {
        this.fireEvent("keydown", A);
    }, destroy:function(A, C) {
        if(this.loadMask) {
            this.loadMask.destroy();
        }
        var B = this.container;
        B.removeAllListeners();
        this.view.destroy();
        this.colModel.purgeListeners();
        if(!C) {
            this.purgeListeners();
        }
        B.update("");
        if(A === true) {
            B.remove();
        }
    }, processEvent:function(E, G) {
        this.fireEvent(E, G);
        var D = G.getTarget(), F = this.view, A = F.findHeaderIndex(D);
        if(A !== false) {
            this.fireEvent("header" + E, this, A, G);
        } else {
            var B = F.findRowIndex(D), C = F.findCellIndex(D);
            if(B !== false) {
                this.fireEvent("row" + E, this, B, G);
                if(C !== false) {
                    this.fireEvent("cell" + E, this , B, C, G);
                }
            }
        }
    }, onClick:function(A) {
        this.processEvent("click", A);
    }, onContextMenu:function(B, A) {
        this.processEvent("contextmenu", B);
    }, onDblClick:function(A) {
        this.processEvent("dblclick", A);
    }, walkCells:function(E, H, C, J, A) {
        var G = this.colModel, B = G.getColumnCount(), D = this.dataSource, I = D.getCount(), F = true;
        if(C < 0) {
            if(H < 0) {
                E--;
                F=false;
            }
            while(E >= 0) {
                if(!F) {
                    H = B - 1;
                }
                F = false;
                while(H >= 0) {
                    if(J.call(A || this, E, H, G) === true) {
                        return[E, H];
                    }
                    H--;
                }
                E--;
            }
        } else {
            if(H >= B) {
                E++;
                F = false;
            }
            while(E < I) {
                if(!F) {
                    H = 0;
                }
                F = false;
                while(H < B) {
                    if(J.call(A || this, E, H, G) === true) {
                        return[E, H];
                    }
                    H++;
                }
                E++;
            }
        }
        return null;
    }, getSelections:function() {
        return this.selModel.getSelections();
    }, autoSize:function() {
        if(this.rendered) {
            this.view.layout();
            if(this.view.adjustForScroll) {
                this.view.adjustForScroll();
            }
        }
    }, stopEditing:function() {
    }, getSelectionModel:function() {
        if(!this.selModel) {
            this.selModel = new Ext.grid.RowSelectionModel();
        }
        return this.selModel;
    }, getDataSource:function() {
        return this.dataSource;
    }, getColumnModel:function() {
        return this.colModel;
    }, getView:function() {
        if(!this.view) {
            this.view = new Ext.grid.GridView();
        }
        return this.view;
    }, getDragDropText:function() {
        var A = this.selModel.getCount();
        return String.format(this.ddText, A, A == 1 ? "" : "s");
    }, getGridEl : function(){
        return this.container;
    },
})
