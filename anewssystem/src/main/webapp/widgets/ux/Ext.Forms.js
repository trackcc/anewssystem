
Ext.Forms = function() {
    this.Form = Ext.form;

    this.isApply = true;
};

Ext.extend(Ext.Forms, Ext.util.Observable, {
    createQuicksearch:function(E, C) {
        var G = "", D = "", F = Ext.id();
        if(E.convert) {
            G = "char=\"" + E.convert + "\"";
        }
        if(!E.vType) {
            var B = E.vWidth ? E.vWidth : 23;
            D = "<input id=\"" + F + "\" size=\"" + B + "\" vType=\"chn\" " + G + " allowBlank=true>";
        } else if(E.vType == "date") {
            B = E.vWidth ? E.vWidth : 20;
            D = "<input id=\"" + F + "\" size=\"" + B + "\" vType=\"date\" " + G + " format=\"" + (E.format ? E.format : "Y年m月d日") + "\" setAllMonth=" + E.setAllMonth + ">";
        } else if(E.vType == "comboBox") {
            B = E.vWidth ? E.vWidth : 150;
            D = "<input id=\"" + F + "\" vType=\"comboBox\" " + G + " vWidth=\"" + B + "\" vType=\"comboBox\" allowBlank=false vForceSelection=\"true\" data=\"" + E.data + "\" readonly>";
        }
        E.label = E.label ? E.label + ":" : "快速检索:";
        if(!E.textRight) {
            E.textRight = 160;
        }
        if(!E.inputRight) {
            E.inputRight = 8;
        }
        E.tb.addDom({
            tag:"div", id:"quicksearchText", style:"position:absolute; right:" + E.textRight + "px; top:8px;", html:E.label
        });
        E.tb.addDom({
            tag:"div", id:"quicksearchDiv", style:"position:absolute; right:" + E.inputRight + "px; top:3px;", html:D
        });
        var A = Ext.get(F);
        A.on("mouseover", function() {
            this.focus();
        });
        A.dom.eventTab = false;
        if(E.vType == "date") {
            A = Forms.date(A.dom);
        } else if(E.vType == "comboBox") {
            A = Forms.comboBox(A.dom);
        } else {
            A = Forms.input(A.dom);
        }
        if(C) {
            A.el.on("keydown", function(e) {
                if(e.keyCode == 13) {
                    var A = this.getValue().replace(/^\s+|\s+$/g, "");
                    C.call(this, A);
                }
            });
        }
        return A
    }, applyEl : function(G, H, B) {
        this.isApply = false;
        G = Ext.get(G);
        var E = G.dom.getElementsByTagName("input");
        var K = G.dom.getElementsByTagName("textarea");
        var I = new Array();
        var A = E.length + K.length;
        var D;
        for(var C = 0; C < A; C++) {
            var F = C < E.length ? E[C] : K[Math.abs(A - C - K.length)];
            if(F.vType == "date") {
                D = Forms.date(F);
            } else if(F.vType == "comboBox") {
                D = Forms.comboBox(F);
            } else if(F.vType == "textArea") {
                D = Forms.textArea(F);
            } else if(F.vType == "treeField") {
                D = Forms.treeField(F);
            } else if(("text,hidden,password").indexOf(F.type) >= 0) {
                D = Forms.input(F);
            }
            D.unit = F.unit;
            D.docType = B;
            D.background = H;
            I[I.length] = D;
        }
        try {
            G.update("");
        } catch(J) {
            G.dom.innerHTML = "";
        }
        this.isApply = true;
        this.elements = I;
        return I;
    }, getElementById : function(A) {
        for(i = 0; i < this.elements.length; i++) {
            var B = this.elements[i];
            if(B.id == A) {
                return B;
            }
        }
        return null;
    }, convert : function(s) {
        this.char = this.char ? this.char : s;
        if(this.char == 'upper') {
            this.value = this.value.toUpperCase();
        } else if(this.char == 'lower') {
            this.value = this.value.toLowerCase();
        }
        return this.value;
    }, validateField : function(E, A) {
        var D = true, B = E.allowBlank, G = E.alt, F = true, C = this.yesBtn ? ((A + "").indexOf("______") == 0 && this.yesBtn.cls == "add") : false;
        try {
            F = !E.isValid();
        } catch(H){
            F = A == "" && !B;
        }
        if(F || C) {
            if(E.maxLength) {
                D = A.length > E.maxLength;
                if(D) {
                    Msg.suggest("错误：", G + "最多允许输入<font color=blue>" + E.maxLength + "</font>位！");
                    return false;
                }
            }
            Msg.suggest("错误：", String.format(E.invalidText, G));
            return false;
        }
        if(E.vtype == "integer" || E.vtype == "number") {
            if(E.maxValue) {
                D = A * 1 > E.maxValue * 1;
                if(D) {
                    Msg.suggest("错误：", G + "不得大于&nbsp;<font color=red>" + E.maxValue + "</font>");
                    return false;
                }
            }
            if(E.minValue) {
                D = A * 1 < E.maxValue * 1;
                if(D) {
                    Msg.suggest("错误：", G + "不得小于&nbsp;<font color=red>" + E.maxValue + "</font>");
                    return false;
                }
            }
        }
        return true;
    }, input:function(el) {
        var object = new this.Form.TextField({
            allowBlank : el.getAttribute("allowblank") == undefined ? false : eval(el.getAttribute("allowblank")),
            vtype      : el.getAttribute("vtype"),
            cls        : el.type == "password" ? el.getAttribute("cls") : null,
            //width       : el.getAttribute("vwidth") ? null : null,
            id         : el.id,
            name       : el.id,
            style      : (el.getAttribute("vtype") == "integer" || el.getAttribute("vtype") == "number" ? "text-align: right;" : ""),
            readOnly   : el.readOnly,
            defValue   : el.getAttribute("defvalue"),
            alt        : el.alt,
            maxLength  : el.getAttribute("maxlength") ? el.getAttribute("maxlength") : Number.MAX_VALUE,
            minLength  : el.getAttribute("minlength") ? el.getAttribute("minlength") : 0,
            minValue   : el.getAttribute("minvalue") ? el.getAttribute("minvalue") : 0,
            maxValue   : el.getAttribute("maxvalue") ? el.getAttribute("maxvalue") : Number.MAX_VALUE
        });
        if(el.readOnly) {
            object.style += "color:#656B86;";
        }
        if(el.value != "" && el.format == "date") {
            el.value = datagrids[0].date(el.value);
        }
        if(this.isApply) {
            object.applyTo(el.id);
        }
        if(el.getAttribute("defvalue")) {
            object.setValue(el.getAttribute("defvalue"));
        }
        return object;
    }, date : function(el) {
        var object = new this.Form.DateField({
            id          : el.id,
            name        : el.id,
            allowBlank  : el.getAttribute("allowblank") == undefined ? false : eval(el.getAttribute("allowblank")),
            format      : (el.getAttribute("format") ? el.getAttribute("format") : "Y年m月d日"),
            readOnly    : true,
            //width       : el.getAttribute("vwidth") ? null : null,
            defValue    : el.getAttribute("defvalue"),
            vType       : "date",
            alt         : el.alt,
            setAllMonth : (el.setAllMonth ? el.setAllMonth : false)
        });
        if(this.isApply) {
            object.applyTo(el.id);
        }
        if(el.getAttribute("defvalue")) {
            object.setValue(el.getAttribute("defvalue"));
        } else {
            object.setValue(nowDate);
        }
        return object;
    }
});
var Forms = new Ext.Forms();

