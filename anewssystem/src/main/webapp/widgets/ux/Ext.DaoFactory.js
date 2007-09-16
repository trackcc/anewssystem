Ext.DaoFactory = function() {
    this.addEvents({
        "beforesave":true,
        "aftersave":true
    })
};
Ext.extend(Ext.DaoFactory, Ext.util.Observable, {
    save : function(D) {
        // E是this
        var E = this, B = function(C) {
            var B, A = C;
            // 如果C中包含{，就认识C不是简单文本，而是json对象
            if(C.indexOf("{") >= 0){
                // 解析json对象，A是消息msg，B是隐藏的对话框
                C = objectEval(C);
                A = C.msg;
                B = C.hideDlg; // 隐藏弹出对话框的函数
            }
            // 显示信息
            Msg.suggest("信息：", A);
            E.progress(false, "post-wait", B); // TODO:进度条
            // 操作完成后，触发aftersave事件
            E.fireEvent("aftersave", this);
        };
        // 如果触发beforesave事件失败，就强制中断
        if(!this.fireEvent("beforesave", this)) {
            return false; // TODO:是否需要显示错误信息？
        }
        // A是用所有字段拼接成的字符串
        var A = this.checkValue();
        if(A != null) {
            // C应该是id值，D应该是操作的类型，edit或add
            var C = (D == "add") ? "0" : this.getRowId();
            // 在进行edit操作，并且id中包含,或者id为空的情况下，执行edit方法
            if(D == "edit" && (C.indexOf(",") >= 0 || C == "")) { // TODO:无法理解对id的判断
                this.edit();
            }　else {
                // 显示等待进度条，同时执行function B
                this.progress(true, "post-wait");
                this.exec.save(C, A, B)
            }
        }
    }, add : function() {
        return this.setValueofEmpty(true)
    }, edit : function(C) {
        var B = this.getRowId(), A = C ? C : {
            err1:"请选择需要修改的记录！",
            err2:"一次只能修改一条记录！"
        };
        if(B == "") {
            "请在单位月明细中选择需要缴费的参保险种！";
            Msg.suggest("错误：", A.err1);
            return false;
        } else if(B.indexOf(",") >= 0) {
            Msg.suggest("错误：", A.err2);
            return false;
        } else {
            this.setValue(false, true);
            return true;
        }
    }, del : function() {
        var B = this.getRowId(), A = function(A) {
            if(A == "yes") {
                var C = this.getRowId(), B = function(A) {
                    datagrids._obj.setRowId("");
                    Msg.suggest("信息：", A);
                    datagrids._obj.refresh();
                } ;
                Msg.suggest("信息：", "正在删除，请稍候... ");
                if(this.exec.dwrDelete) {
                    this.exec.dwrDelete(C,B);
                } else {
                    this.exec.del(C, B);
                }
            }
        };
        if(B == "") {
            Msg.suggest("错误：", "请选择需要删除的记录！");
            return false;
        } else {
            if(!this.editor && this.dlg != true) {
                this.dlg.hide();
            }
            Msg.confirm("信息", "您选择的&nbsp;<font color=red>" + B.split(",").length +
                "</font>&nbsp;条记录删除后将无法恢复，确认删除？", A, this);
        }
        return false;
    }, view : function(A) {
        this.showDlg({
            text    : "查看",
            cls     : "view",
            btnText : $(A)
        });
    }
})
