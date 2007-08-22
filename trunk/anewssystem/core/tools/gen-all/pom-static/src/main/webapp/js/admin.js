//后台管理界面用到的一些函数

function batch_do(entityName, action)
{
    if (confirm("确定要" + entityName + "?"))
    {
        if (!atleaseOneCheck())
        {
            alert('请至少选择一' + entityName + '！');
            return;
        }
        var form = document.forms.ec;
        form.action = action + '&autoInc=false';
        form.submit();
    }
}

function openwin(url, width, height, scroll)
{
    if (!document.all)
    {
        document.captureEvents(Event.MOUSEMOVE);
        x = e.pageX + width - 30;
        y = e.pageY + height - 30;
    }
    else
    {
        x = document.body.scrollLeft + event.clientX + width - 30;
        y = document.body.scrollTop + event.clientY + height - 30;
    }
    window.open(url, "newWindow", "height=" + height + ", width=" + width + ", toolbar =no, menubar=no, scrollbars=" + scroll + ", resizable=no, location=no, status=no, top=" + y + ", left=" + x + "") //写成一行
}

function win(url, width, height) {
    var win = window.open(url, "viewImage", "height=" + height + ",width=" + width + ",toolbar=no,menubar=0,scrollbars=no,resizable=no,location=no,status=no,top=20,left=20");
    return win;
}

//checkbox中至少有一项被选中
function atleaseOneCheck()
{
    var items = document.getElementsByName('itemlist');
    if (items.length > 0) {
        for (var i = 0; i < items.length; i++)
        {
            if (items[i].checked == true)
            {
                return true;
            }
        }
    } else {
        if (items.checked == true) {
            return true;
        }
    }
    return false;
}

// 全选，全不选
function selectAll(item) {
    var value = false;
    if (item.checked) {
        value = true;
    } else {
        value = false;
    }
    var items = document.getElementsByName('itemlist');
    for (var i = 0; i < items.length; i++) {
        items[i].checked = value;
    }
}

function info(item) {
    var buff = "";
    for (var i in item) {
        try {
            buff += i + ":" + item[i] + "\n";
        } catch (e) {
            alert(e);
        }
    }
    alert(buff);
}

