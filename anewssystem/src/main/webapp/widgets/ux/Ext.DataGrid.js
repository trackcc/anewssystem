var datagrids = new Array(), comboboxArray = new Array(), nowDate = new Date(), nowFormateDate = nowDate.dateFormat("Y年m月d日");

Ext.DataGrid = function(config) {
    var cfg = config;
    this.dlg;
    this.dlgTitle = config.dlgTitle == undefined ? "" : config.dlgTitle;
    this.dlgDiv = cfg.gridDiv + "-dlg";
    this.tabs;
    this.dlgContentDiv = cfg.dlgContentDiv;
    this.dlgWidth = cfg.dlgWidth;
    this.dlgHeight = cfg.dlgHeight;
    this.yesBtn;
    this.noBtn;
    this.gridDiv = cfg.gridDiv;
    this.paging = cfg.paging;
    this.pageInfo = cfg.pageInfo;
    this.pageSize = cfg.pageSize;
    this.grid;
    this.dataModel;
    this.colModel;
    this.defaultSort;
    this.header;
    this.column = new Array();
    this.fields;
    this.initPaging = !cfg.initPaging ? true : cfg.initPaging;
    this.exec = eval(cfg.exec);
    this.url = setSitePath("./sandbox/listMenu.htm");
    this.rowid = cfg.gridDiv + "_rowid";
    this.colClick = false;
    this.row;
    this.checkList = new Array();
    this.params;
    this.datagrids;
    this.dialogActivate;
    if(!this.editor) {
        Ext.DataGrid.superclass.constructor.call(this);
    }
};
Ext.extend(Ext.DataGrid, Ext.DaoFactory, {
    hideDlg : function(B) {
        this.datagrids = datagrids.length;
        datagrids[this.datagrids] = this;
        for(var A = 0; A < datagrids.length; A++) {
            if(datagrids[A].dlgDiv != this.dlgDiv || B) {
                if(typeof datagrids[A].dlg == "object" && datagrids[A].dlg.isVisible()) {
                    datagrids[A].dlg.hide();
                }
            }
        }
    }, showDlg : function(tbBtn) {
        var b = tbBtn.cls == "view" ? true : eval("this." + tbBtn.cls + "()");

        if(b) {
            if(!this.dlg) {
                this.createDialog();
            }
            this.hideDlg();
            this.tabs.getTab(0).setText(tbBtn.text + this.dlgTitle);
            this.setDialog({
                width  : this.dlgWidth,
                height : this.dlgHeight,
                tbBtn  : tbBtn
            });
            if(tbBtn.cls == "view") {
                this.dlg.show(tbBtn.btnText);
                if(!this.dialogActivate) {
                    this.yesBtn.hide();
                    this.tabs.getTab(0).on("activate", function() {
                        this.yesBtn.hide();
                    }, this, true);
                }
            } else {
                this.dlg.show(tbBtn.getEl());
                if(!this.dialogActivate) {
                    this.yesBtn.show();
                    this.tabs.getTab(0).on("activate", function() {
                        this.yesBtn.show();
                    }, this, true);
                }
            }
            this.tabs.activate(0);
        }
        datagrids._obj = this;
    }, createDlgContentDiv : function() {
        var K = document.getElementById(this.dlgContentDiv), I = document.createElement("div");
        I.id = this.gridDiv + "-content";
        I.appendChild(K);
        var A = document.createElement("div"), H = document.createElement("div"), J = document.createElement("div");
        A.id = "dlg-msg";
        H.id = "post-wait";
        H.className = "posting-msg";
        J.className = "waitting";
        J.innerHTML = "正在保存，请稍候...";
        H.appendChild(J);
        A.appendChild(H);
        var B = document.createElement("div"), C = document.createElement("div"), F = document.createElement("div"), D = document.createElement("div"), L = document.createElement("div"), E = document.createElement("div"), G = document.createElement("div");
        B.id = this.dlgDiv;
        B.style.visibility = "hidden";
        C.className = "x-dlg-hd";
        F.className = "x-dlg-bd";
        G.className = "x-dlg-ft";
        D.className = "x-dlg-tab";
        L.className = "x-dlg-tab";
        L.title=" 帮助 ";
        E.innerHTML = "<div id='help-content'><div id='standard-panel'>帮助...</div></div><div id='temp-content'></div>";
        L.appendChild(E);
        F.appendChild(D);
        F.appendChild(L);
        G.appendChild(A);
        B.appendChild(C);
        B.appendChild(F);
        B.appendChild(G);
        document.body.appendChild(B);
        document.body.appendChild(I);
        datagrids._obj = this;
    }, createDialog : function() {
        if(this.editor) {
            alert("error!\neditgrid不允许设置link属性！");
        }
        var A = this.dlgWidth, B = this.dlgHeight;
        this.createDlgContentDiv();
        this.dlg = new Ext.BasicDialog(this.dlgDiv, {
            modal     : false,
            autoTabs  : true,
            width     : (A == undefined ? 500 : A),
            height    : (B == undefined ? 300 : B),
            shadow    : false,
            minWidth  : 200,
            minHeight : 100,
            closable  : true
        });
        this.dlg.addKeyListener(27, this.dlg.hide, this.dlg);
        this.yesBtn = this.dlg.addButton("确定", function() {
        }, this.dlg);
        this.tabs = this.dlg.getTabs();
        this.tabs.getTab(1).on("activate", function(){
            this.yesBtn.hide();
        }, this, true);
        this.tabs.getTab(0).setContent();
        var C = $(this.gridDiv + "-content");
        this.tabs.getTab(0).setContent(C.innerHTML);
        this.applyElements();
        document.body.removeChild(C);
        if(this.dialogActivate) {
            this.dialogActivate.call(this);
        }
        this.noBtn = this.dlg.addButton("取消", this.dlg.hide, this.dlg);
    }, applyElements : function(B) {
        //var D = $(this.dlgContentDiv).getElementsByTagName("input"), G = $(this.dlgContentDiv).getElementsByTagName("textarea"), A = D.length + G.length;
        var div = Ext.get(this.dlgContentDiv);
        var D = div.query("input"), G = div.query("textarea"), A = D.length + G.length;
        for(var C = 0; C < A; C++) {
            var F, E = C < D.length ? D[C] : G[Math.abs(A - C - G.length)];
            try {
                var vType = E.getAttribute("vtype");
            if(vType == "date") {
                F = Forms.date(E);
            } else if(vType == "textArea") {
                F = Forms.textArea(E);
            } else if(vType == "comboBox") {
                F = Forms.comboBox(E);
            } else if(vType == "treeField") {
                F = Forms.treeField(E);
            } else if(("text,hidden,password").indexOf(E.type) >= 0) {
                F = Forms.input(E);
            }

            }catch(e) {
                var buff = "";
                for (var i in e) {
                    buff += i + "," + e[i] + "\n";
                }
                alert(buff);
            }
            this.column[this.column.length] = {
                id         : E.id,
                type       : E.type,
                vType      : E.getAttribute("vtype"),
                allowBlank : E.getAttribute("allowblank"),
                alt        : E.alt,
                format     : E.getAttribute("format"),
                obj        : F
            }
        }
        //alert(this.column.length);
    }, checkValue : function() {
        // 返回的结果，应该是result
        var C = new Array();
        // TODO:
        var J = true;
        // 遍历所有字段
        var result = {};
        for(i = 0; i < this.column.length; i++) {
            // M是当前字段，currentField
            var M = this.column[i];
            //console.info(M);
            //console.info(M.id);
            // B是当前值，currentValue
            var B = M.obj.getValue();
            // K是是否通过验证，isValid
            var K = Forms.validateField(M.obj, B);
            // 如果没通过验证，就返回null
            if(!K) {
                M.obj.focus();
                return null;
            }
            // 遇到下拉列表的时候，特殊处理
            if(M.vType == "comboBox"){
                // 当前字段的dom
                var H = M.obj.el.dom;
                // 取value
                B = H.key;
                // 如果是多选
                if(H.multiple == "true") {
                    var D = B.split(",");
                    var I = M.obj.getText().split(",");
                    var F = "";
                    var G = "";
                    for(var A = 0; A < D.length; A++) {
                        var E = D[A].split("|"), L = A < D.length - 1 ? "," : "";
                        F += E[0] + L;
                        G += E[1] + L;
                    }
                    B = F + "|" + G;
                }
                C[i] = B;
            } else if(M.vType == "date") {
                C[i] = B == "" ? "" : B.dateFormat("Y-m-d");
                result[M.id] = B == "" ? "" : B.dateFormat("Y-m-d");
            } else {
                C[i] = B;
                result[M.id] = M.obj.getValue();
            }
            //var str = "result." + M.id + "=\'" + M.obj.getValue() + "\';";
            //console.debug(str);
            //eval(str);
        }
        //var buff = "";
        //for (var i in result) {
        //    buff += i + "," + result[i] + "  "
        //}
        //console.error(buff);
        return result;
    }, setDialog : function(A) {
        this.yesBtn.cls = A.tbBtn.cls;
        this.yesBtn.setText(A.tbBtn.btnText);
        this.yesBtn.setHandler(function() {
            this.save(A.tbBtn.cls)
        }, this);
    }, setValueofEmpty : function(A) {
        if(A) {
            for(i = 0; i < this.column.length; i++) {
                var B = this.column[i];
                if(B.vType == "date") {
                    if(B.obj.defValue != undefined) {
                        B.obj.setValue(B.obj.defValue);
                    } else {
                        B.obj.setValue(nowDate);
                    }
                } else if(B.vType == "comboBox"){
                    if(B.obj.el.dom.allowBlank == "true") {
                        B.obj.setValue("");
                        B.obj.el.dom.key = "";
                    }
                } else if(B.vType == "textArea") {
                    B.obj.setValue("");
                } else if(("text,password").indexOf(B.type) >= 0) {
                    if(B.obj.defValue != undefined) {
                        B.obj.el.dom.value = B.obj.defValue;
                    }
                } else {
                    B.obj.el.dom.value = "";
                }
                if(B.obj.disabled) {
                    B.obj.enable();
                }
            }
            return true
        }
    }, setValue : function(G, E) {
        var B;
        if(G) {
            B = this.dataModel.getAt(G);
        } else {
            var D = this.grid.selModel.getSelected;
            if(D) {
                B = this.dataModel.getAt(this.checkList[this.checkList.length - 1]);
                B = B ? B : this.grid.selModel.getSelected();
            } else {
                B = this.grid.selModel.selection.record;
            }
        }
        for(var A = 0; A < this.column.length; A++) {
            var H = this.column[A], C = B.data[H.id] + "";
            if(H.vType == "date") {
                H.obj.setValue(this.date(C));
            } else if(H.vType == "comboBox") {
                var F = H.obj.el.dom;
                if(C.indexOf("|")>=0) {
                    C = C.split("|");
                    C[1] = String.format(C[1], "|");
                    H.obj.setValue(C[1]);
                    F.key = C[0];
                } else {
                    H.obj.setValue(C);
                    F.key = C;
                }
                if(C.length == 0) {
                    H.obj.setValue(H.obj.emptyText);
                }
            } else if(H.type == "password") {
                H.obj.setValue("______");
            } else if("text,hidden,textArea".indexOf(H.type) >= 0) {
                if(C && C != "undefined") {
                    if(H.obj.getEl().dom.format == "date") {
                        C = this.date(C);
                    }
                    H.obj.setValue(C);
                }
            }
            if(this.yesBtn && this.yesBtn.cls == "view" && !E) {
                if(!H.obj.disabled) {
                    H.obj.disable();
                }
            } else if(H.obj.disabled) {
                H.obj.enable();
            }
        }
    }, getRowId : function() {
        return $(this.rowid).value;
    }, setRowId : function(A) {
        $(this.rowid).value = A;
    }, getSelectedId : function() {
        var A = this.grid ? this.grid : this, B = A.selModel.getSelected(), C = "0";
        if(B) {
            C = objectEval(B.data["idno"]).id;
        }
        return C;
    }, getSelectedColValueByName : function(C) {
        var A = this.grid ? this.grid : this, B = A.selModel.getSelected(), D = "";
        if(B) {
            D = B.data[C];
        }
        return D;
    }, refresh : function() {
        var A = this.row.pageNo * this.pageSize - this.pageSize, B;
        this.setRowId("");
        if(this.params == null) {
            B = {params:{start:A, limit:this.pageSize}};
        } else {
            B = {params:{start:A, limit:this.pageSize, params:this.params}};
        }
        this.dataModel._options = B;
        this.dataModel.load();
        if(this.editor) {
            this.newRecordCount = 0;
            this.dataModel.commitChanges();
        }
    }, progress : function(B, D, A) {
        var C = Ext.get(D);
        if(B) {
            C.radioClass("active-msg");
            this.yesBtn.disable();
            this.noBtn.disable();
        } else {
            if(A == undefined || A) {
                this.dlg.hide();
                this.setValueofEmpty(this.getRowId() == "");
                this.refresh();
            }
            C.removeClass("active-msg");
            this.yesBtn.enable();
            this.noBtn.enable();
        }
    }, getChecked : function() {
        var E = this.dataSource.getCount(), D = new Array();
        if(E == 0) {
            return null;
        }
        for(var B = 0; B < E; B++) {
            var A = this.dataSource.getAt(B), C = $(this.id + "_checkbox_" + B);
            if(C.checked) {
                D[D.length] = A;
            }
        }
        return D.length == 1 ? D[0] : D;
    }, onRowClick : function(A, G) {
        if(!this.colClick) {
            return;
        }
        var C = $(this.gridDiv + "_checkbox_" + G), F = $(this.gridDiv + "_mycheckbox_" + G), D = $(this.gridDiv + "_label_" + G), E = this.checkList;
        getId(C, F, D, $(this.rowid));
        if(C.checked) {
            E[E.length] = G;
        } else {
            for(var B = 0; B < E.length; B++) {
                if(E[B] == G) {
                    E[B] = "X";
                    var H = E.toString().replace(/\,X/g, "").replace(/\X,/g, "");
                    E = H.split(",");
                    this.checkList = E;
                    E.sort(function(B, A) {
                        return B * 1 > A * 1 ? -1 : 1;
                    });
                    G = E[0];
                    if(G == "X") {
                        G = -1;
                    } else {
                        H = A.selModel.selectRow;
                        if(H) {
                            A.selModel.selectRow(G);
                        } else {
                            A.selModel.select(G, 0, false, true);
                        }
                    }
                    break;
                }
            }
        }
        if(G >- 1) {
            G = G * 1;
            if(G > this.pageSize) {
                G = G / 10;
            }
            this.setValue(G);
        } else {
            this.setValue();
        }
        this.colClick = false;
        if(!this.dlg) {
            this.createDialog();
        }
    }, getToolBarEvent : function() {
        var A = function(A) {
            this.obj.showDlg(A);
        };
        return A;
    }, link : function(D, H, C, G, B, F) {
        var I = F.obj.gridDiv + "_" + H.cellId;
        if(D.indexOf(".gif") >= 0) {
            D = "<image src=\"" + path + "/widgets/extjs/1.1/resources/images/aero/user/tree/" + D + "\">";
        }
        var E = "datagrids[{1}].colClick=true;datagrids[{1}].view(this)";
        var A = "<span style='cursor:pointer;color=#5285C5' id='{0}' onclick='" + E + "'>{2}</span>";
        A = String.format(A, I, F.obj.datagrids, D, G);
        return A;
    }, date : function(C, F, B, E, A, D) {
        var G = (C == "") ? "" : String.format(C.split(" ")[0].replace("-", "{0}").replace("-", "{1}"), "年","月") + "日";
        return G;
    }, integer : function(C, F, B, E, A, D) {
        if(C.indexOf(".") >= 0) {
            C = C.substring(0, C.indexOf("."));
        }
        return C;
    }, separator : function(C, F, B, E, A, D) {
        if(C.indexOf("@") >= 0) {
            return C.split("@")[1];
        }
        if(C.indexOf("|") < 0) {
            return C;
        }
        return C.split("|")[1];
    }, setCheckBox : function (D, H, C, G, B, F) {
        if(!D || D == "") {
            return"";
        }
/*
        F.obj.row = objectEval(D);
        var E = (F.obj.getRowId().indexOf(F.obj.row.id) >= 0);
        var A = "<div style='display:none'><input type=checkbox id='{0}_checkbox_{1}' {2} value=\"{3}\" ></div>";
        A += "<div id='{0}_mycheckbox_{1}' class='{4}' onclick='datagrids[{7}].colClick = true'></div>";
        A += "<label id='{0}_label_{1}' onclick='datagrids[{7}].colClick = true' class='{5}'>{6}.</label>";
        A = String.format(A, F.obj.gridDiv, G, E ? "checked" : "", F.obj.row.id, E ? "checkboxAreaChecked" : "checkboxArea", E ? "chosen" : "", F.obj.row.no, F.obj.datagrids);
*/
        var E = false;
        var A = "<div style='display:none'><input type=checkbox id='{0}_checkbox_{1}' {2} value=\"{3}\" ></div>";
        A += "<div id='{0}_mycheckbox_{1}' class='{4}' onclick='datagrids[{7}].colClick = true'></div>";
        A += "<label id='{0}_label_{1}' onclick='datagrids[{7}].colClick = true' class='{5}'>{6}.</label>";
        A = String.format(A, F.obj.gridDiv, G, E ? "checked" : "", D, E ? "checkboxAreaChecked" : "checkboxArea", E ? "chosen" : "", D, F.obj.datagrids);
        return A;
    }, setHeader : function(h) {
        var tagsArray, dataIndex;
        if(!h[0].dataIndex) {
            tagsArray = "";
            var tags = function(A) {
                tagsArray = A;
            };
            DWREngine.setAsync(false);
            this.exec.getTags(tags);
            DWREngine.setAsync(true);
        }
        var header = [{
            header    : "序号",
            dataIndex : "id",
            width     : 55,
            align     : "left",
            hidden    : false,
            sortable  : false,
            renderer  : this.setCheckBox
        }], fields = [{
            name:"id", mapping:"id"
        }];
        for(var i = 0; i < h.length; i++) {
            var renderer = h[i].renderer;
            //console.error(h[i].type);
            var align = "left";
            var hidden = false;
            var draw = h[i].draw != undefined ? h[i].draw : true;
            if(h[i].type != undefined) {
                renderer = eval("this." + h[i].type);
            }
            if(h[i].separator != undefined) {
                renderer = this.separator;
            }
            if(h[i].align != undefined) {
                align = h[i].align;
            }
            if(h[i].hidden != undefined) {
                hidden = h[i].hidden;
            }
            dataIndex = tagsArray ? tagsArray[i + 1] : h[i].dataIndex;
            h[i] = {
                header:h[i].header, dataIndex:dataIndex, width:h[i].width, align:align, hidden:hidden, sortable:h[i].sortable, renderer:renderer, draw:draw, id:dataIndex
            };
            if(this.editor) {
                h[i] = this.getEditor(h[i], dataIndex, i == 0);
            }
            fields = fields.concat([{
                name:dataIndex, mapping:dataIndex
            }]);
        }
        this.header = header.concat(h);
        this.fields = fields;
        var row_input = document.createElement("input");
        row_input.id = this.rowid;
        row_input.type = "hidden";
        document.body.appendChild(row_input);
        datagrids._obj = this;
        this.datagrids = datagrids.length;
        datagrids[this.datagrids] = this;
    }, getParams : function(H) {
        if(H != undefined) {
            var E = new Array(), G = new Array(), K = new Array(), I = "<separator>";
            for(var F = 0; F < H.length; F++) {
                var M = H[F], B = " ? ", L = M.pName, C = M.pMore, A = M.pValue, D  = M.pType, J = C == "like";
                L = L.replace(/\+/g, "||");
                A = J ? "%" + A + "%" : A;
                if(L.indexOf(")") >= 0 && L.indexOf("(") < 0) {
                    L = L.replace(")", "");
                    B = " ?) ";
                }
                if(C == "in") {
                    L = "instr(" + A + "|" + L + ")";
                    C = ">";
                    A = 0;
                    D = "Integer";
                }
                E[E.length] = L + " " + C + B + (M.pLogic == undefined ? "" : M.pLogic);
                if(A.length == 0) {
                    A = "null";
                }
                G[G.length] = A;
                K[K.length] = D;
            }
            H = E.toString().replace(/\,/g, " ").replace("|", ",");
            H += I + G.toString();
            H += I + K.toString();
        } else {
            H = null;
        }
        return H;
    }, addListener : function(B,A) {
        this.grid.addListener(B, A);
    }, getView : function() {
        return this.grid.getView();
    }, render : function(A, C) {
        this.params = this.getParams(A);
        if(!this.colModel) {
            this.row = {
                "pageNo":1
            };
            this.colModel = new Ext.grid.ColumnModel(this.header);
            var B = {
                root:"root", record:"rows", totalRecords:"TotalCount"
            };

    var recordType = Ext.data.Record.create([
        {name: "id",      mapping: "id",      type: "int"},
        {name: "image",   mapping: "image",   type: "string"},
        {name: "name",    mapping: "name",    type: "string"},
        {name: "forward", mapping: "forward", type: "string"},
        {name: "theSort", mapping: "theSort", type: "int"}
    ]);
            this.dataModel = new Ext.data.Store({
                proxy: new Ext.data.DWRProxy(MenuHelper.pagedQuery, true),
                reader: new Ext.data.ListRangeReader({
                    totalProperty: 'totalCount',
                    id: 'id'
                }, recordType),
                // 远端排序开关
                remoteSort: true,
                obj:this
/*
                proxy:new Ext.data.HttpProxy({
                    url:this.url
                }),
                reader:new Ext.data.XmlReader(B, this.fields),
                remoteSort:true,
                obj:this
*/
            });
            if(this.defaultSort) {
                this.colModel.defaultSortable = true;
                this.dataModel.setDefaultSort(this.defaultSort.field, this.defaultSort.dir);
            }
        }
        if(!this.grid) {
            this.grid = new Ext.grid.Grid(this.gridDiv, {
                ds:this.dataModel,
                colModel:this.colModel,
                enableColLock:false,
                loadMask:true
            });
            this.grid.addListener("rowclick", function(A) {
                A.isSelected = false;
            });
            this.grid.addListener("rowclick", this.onRowClick, this, true);

            //var rz = new Ext.Resizable("resize-grid", {
            //    wrap:true,
            //    minHeight:500,
            //    pinned:true,
            //    handles: 's'
            //});
            //rz.on('resize', this.grid.autoSize, this.grid);
            this.grid.render();
            this.setPagingBar();
            this.grid.getSelectedId = this.getSelectedId;
            this.grid.getChecked = this.getChecked;
            this.grid.getSelectedColValueByName = this.getSelectedColValueByName;
        }
        if(C != false) {
            this.pagingToolbar.loading.show();
            this.refresh();
        }
    }, getHeaderPanel : function() {
        this.headPanel = this.grid.getView().getHeaderPanel(true);
        return this.headPanel;
    }, getGridToolbar : function() {
        return new Ext.Toolbar(this.getHeaderPanel());
    }, setPagingBar : function(){
        var A = "显示&nbsp;{0}&nbsp;-&nbsp;{1}&nbsp;,&nbsp;共&nbsp;{2}&nbsp;条", C = "显示&nbsp;0&nbsp;-&nbsp;0&nbsp;,&nbsp;共&nbsp;0&nbsp;条";
        if(this.pageInfo == false) {
            A = "共&nbsp;{2}&nbsp;条";
            C = "共&nbsp;0&nbsp;条";
        }
        var B = this.grid.getView().getFooterPanel(this.paging);
        this.pagingToolbar = new Ext.PagingToolbar(B, this.dataModel, {
            pageSize:this.pageSize, displayInfo:true, displayMsg:A, emptyMsg:C
        });
        this.pagingToolbar.loading.hide();
        if(this.editor) {
            this.pagingToolbar.loading.on("click", function() {
                this.newRecordCount = 0;
                this.dataModel.commitChanges();
            } .createDelegate(this));
        }
    }, insertIntoPagingBar : function(A) {
        this.pagingToolbar.add("-", {
            pressed:(A.pressed ? A.pressed : false),
            enableToggle:(A.enableToggle ? A.enableToggle : false),
            text:A.text,
            cls:(A.cls ? A.cls : "detail"),
            toggleHandler:A.handler.createDelegate(this)
        });
    }
})
