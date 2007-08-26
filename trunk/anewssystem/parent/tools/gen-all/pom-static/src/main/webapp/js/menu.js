/*  Copyright SpringSide, 2005-2006  |  www.springside.org.cn
 * -----------------------------------------------------------
 * The SpringSide Menu, version 1.0
 *
 * Author: cac
 */


//actform
document.write("<form id='menuActform' method='GET' target='' action=''></form>");

//创建上下移动箭头
document.write("<DIV id='Smenu' style='position:absolute;top:" + SS_Top + ";left:" + SS_Left + ";width:" + SS_Width + ";height:" + SS_Height + ";border:" + SS_BorderWidth + " " + SS_BorderStyle + " " + SS_BorderColor + ";background-color:" + SS_BackgroundColor + ";z-index:0;visibility:hidden;clip:rect(0," + SS_Width + "," + SS_Height + ",0)'>");
document.write("<img onMouseUp='Smenu.ArrowSelected(this)' onMouseDown='Smenu.ArrowClicked(this)' onMouseOver='Smenu.OverArrow(this)' onMouseOut='Smenu.OutArrow(this);' id='SS_SlideUp' height='" + SS_ArrowHeight + "' width='" + SS_ArrowWidth + "' src='" + SS_DownArrow + "' style='position:absolute;top:0;left:0;cursor:pointer;visibility:hidden;z-index:650'>");
document.write("<img onMouseUp='Smenu.ArrowSelected(this)' onMouseDown='Smenu.ArrowClicked(this)' onMouseOver='Smenu.OverArrow(this)' onMouseOut='Smenu.OutArrow(this);' id='SS_SlideDown' height='" + SS_ArrowHeight + "' width='" + SS_ArrowWidth + "' src='" + SS_UpArrow + "' style='position:absolute;top:0;left:0;cursor:pointer;visibility:hidden;z-index:650'>");
document.getElementById("Smenu").style.visibility = "visible";

//创建菜单
var itemNum = 1;
while (eval("window.Menu" + itemNum))  itemNum++;
itemNum--;
i = itemNum;
while (i > 0)
{
    Folder = eval("Menu" + i);
    MakeMenu(Folder, i);
    i--;
}
document.write("</DIV>");
document.write("<style>");
document.write(".OB1_Class{position:absolute;left:0;width:" + SS_Width + ";height:" + SS_MenuBarHeight + ";line-height:2;font-size:" + SS_MenuBarFontSize + "pt;cursor:pointer;color:" + SS_MenuBarFontColor + ";border-width:0;background-color:#2159DA}");
document.write(".OBs_Class{left:0;width:" + SS_Width + ";height:" + SS_MenuBarHeight + ";line-height:2;font-family:" + SS_MenuBarFontFamily + ";font-size:" + SS_MenuBarFontSize + "pt;cursor:pointer;color:" + SS_MenuBarFontColor + "; border-width:0; background-color: #2159DA}");
document.write("</style>");

var Smenu = new SideMenu(SS_Width, SS_Height, itemNum, SS_MenuBarHeight, SS_BorderWidth, SS_SlideSpeed, SS_IconsHeight + SS_LabelFontSize + SS_LabelMargin + SS_ItemsSpacing, SS_ArrowSlideSpeed);
InitBar(SS_BackgroundImg, SS_BackgroundDownImg, SS_BackgroundOverImg, SS_BackgroundOverDownImg);
window.status = ITEM_TITLE;

//------------------------------------------------------------------------------------------------------------------
/*
 * 创建菜单
 */
function MakeMenu(Folder, zorder)
{
    var i = zorder;
    if (i == 1)
    {
        document.write("<INPUT position='UP' id='SS_MenuBar1' TYPE='button' style='height:" + SS_MenuBarHeight + ";top:0;' value='" + Folder[0] + "' class='OB1_Class'  onClick='this.blur();Smenu.FolderClicked(" + i + ");SetBgimage(" + i + ");'>");
        var input = document.createElement('INPUT');
        input.type = 'hidden';
        input.value = 'UP';
        input.id = 'SS_Position1';
        document.getElementById('menuActform').appendChild(input);
        MakeSubMenu(Folder, i, SS_MenuBarHeight);
    }
    else
    {
        document.write("<INPUT position='DOWN' id='SS_MenuBar" + i + "'  TYPE='button' value='" + Folder[0] + "' style=\"height:" + SS_MenuBarHeight + ";position:absolute;top:" + (SS_Height - (itemNum + 1 - i) * SS_MenuBarHeight - SS_BorderWidth * 2) + "\" class='OBs_Class'  onClick='this.blur();Smenu.FolderClicked(" + i + ");SetBgimage(" + i + ");'>");
        var input = document.createElement('INPUT');
        input.type = 'hidden';
        input.value = 'DOWN';
        input.id = 'SS_Position' + i;
        document.getElementById('menuActform').appendChild(input);
        MakeSubMenu(Folder, i, (SS_Height - (itemNum + 1 - i) * SS_MenuBarHeight - SS_BorderWidth * 2) + SS_MenuBarHeight);
    }
}

/*
 * 创建二级菜单
 */
function MakeSubMenu(Folder, zorder, top)
{
    var folderWidth = (SS_Width - SS_BorderWidth * 2);
    var sfolderWidth = folderWidth - 15;
    var items = 0;
    var j = 1;
    while (Folder[j] != null)
    {
        items++;
        j++;
    }
    document.write("<DIV id='SS_Folder" + zorder + "' style='FILTER: DropShadow(Color=" + SS_LabelFontShadowColor + ", OffX=1, OffY=1, Positive=1);position:absolute;left:0;top:" + top + ";width:" + folderWidth + ";height:" + (SS_Margin * 2 + items * (SS_IconsHeight + SS_LabelFontSize + SS_LabelMargin) + (items - 1) * SS_ItemsSpacing) + ";z-index:" + zorder + ";clip:rect(0 0 0 0);'  >");

    for (var i = 1; i <= items; i++)
    {
        var subfolder = Folder[i];
        var top = (SS_Margin + Math.ceil(i - 1) * (SS_ItemsSpacing + SS_LabelFontSize + SS_IconsHeight) - 4) ;
        if (navigator.appName == "Netscape") {
            var imgbg = 0;
        } else {
            var imgbg = 4;
        }
        document.write("<div style='cursor: pointer;margin-left: 5px;padding:2px 5px 2px " + (SS_IconsWidth + 3) + "px;background-position:0 " + imgbg + ";background-image:url(" + subfolder[0] + ");background-repeat:no-repeat;width:" + sfolderWidth + ";height:" + SS_IconsHeight + ";position:absolute;top:" + top + ";left:0;' onclick=doSubTag('" + subfolder[3] + "','" + subfolder[2] + "') onMouseDown='Smenu.ItemClicked(this)' onMouseOver='Smenu.OverItems(this)' onMouseOut='Smenu.OutItems(this)' >&nbsp;");
        document.write("<font style='font-family:" + SS_LabelFontFamily + ";font-size:" + SS_LabelFontSize + "pt;color:" + SS_LabelFontColor + "'>" + subfolder[1] + "</font>");
        document.write("</div>");
    }
    document.write("</DIV>");
}

/*
 * 转向指定页面
 */
function doSubTag(target, link)
{
    document.getElementById("menuActform").action = link;
    document.getElementById("menuActform").target = target;
    document.getElementById("menuActform").submit();
}

/*
 * SpringSide Menu Object 
 */
function SideMenu(width, height, items, buttonHeight, borderWidth, slideSpeed, slideArrowValue, ArrowSlideSpeed)
{
    this.currentFolder = 1;
    this.currentItem = null;
    this.slideCount = 0;
    this.slideStep = 1;
    this.slideArrowValue = slideArrowValue;
    this.slideSpeed = slideSpeed;
    this.borderWidth = borderWidth;
    this.width = width;
    this.visibleAreaHeight = height - 2 * borderWidth - items * buttonHeight;
    this.visibleAreaWidth = width;
    this.FolderClicked = FolderClicked;
    this.SlideFolders = SlideFolders;
    this.ItemClicked = ItemClicked;
    this.OverItems = OverItems;
    this.OutItems = OutItems;
    this.OverArrow = OverArrow;
    this.OutArrow = OutArrow;
    this.ArrowClicked = ArrowClicked;
    this.ArrowSelected = ArrowSelected;
    this.ArrowSlideSpeed = ArrowSlideSpeed;
    this.SlideItems = SlideItems;
    this.SlideItemsAction = SlideItemsAction;
    this.Start = Start;
    this.ClipFolder = ClipFolder;
    this.SetArrows = SetArrows;
    this.HideArrows = HideArrows;
    this.sliding = false;
    this.items = items;
    this.started = false;
    if (navigator.appName == "Netscape") {
        this.filter = /rect\([\+-]?(\d*)p[x|t],? [\+-]?(\d*)p[x|t],? [\+-]?(\d*)p[x|t],? [\+-]?(\d*)p[x|t],?\)/;
    } else {
        this.filter = /rect\([\+-]?(\d*)p[x|t],? [\+-]?(\d*)p[x|t],? [\+-]?(\d*)p[x|t],? [\+-]?(\d*)p[x|t],?\)/;
    }
    this.Start();
}

/*
 * 初始化菜单图片及背景
 */
function InitBar(img, dimg, oimg, odimg)
{
    var orign = new Array();
    var oldTD = null;
    for (var i = 1; i <= itemNum; i++)
    {
        document.getElementById("SS_MenuBar" + i).cid = Math.ceil((new Date().getTime()) * Math.random());
        if (i == 1) document.getElementById("SS_MenuBar" + i).style.background = 'url(' + dimg + ')';
        else document.getElementById("SS_MenuBar" + i).style.background = 'url(' + img + ')';

        document.getElementById("SS_MenuBar" + i).onmousedown = function()
        {
            this.style.background = 'url(' + dimg + ')';
            orign[this.cid] = this.style.background;
        }
        document.getElementById("SS_MenuBar" + i).onmouseover = function()
        {
            oldTD = this;
            orign[this.cid] = this.style.background;
            if (this.style.background == 'url(' + dimg + ')')
                this.style.background = 'url(' + odimg + ')';
            else this.style.background = 'url(' + oimg + ')';
        }
        document.getElementById("SS_MenuBar" + i).onmouseout = function()
        {
            if (oldTD != null && oldTD.cid == this.cid)
            {
                this.style.background = orign[this.cid];
            }
        }
    }

}
/*
 * 设置背景图片
 */
function SetBgimage(folder)
{
    for (var i = 1; i <= itemNum; i++)
    {
        if (i != folder)
        {
            document.getElementById("SS_MenuBar" + i).style.background = 'url(' + SS_BackgroundImg + ')';
        }
    }
}

/*
 * 菜单点击时动作
 */
function FolderClicked(folder)
{
    if (this.sliding)
        return;
    if (folder == this.currentFolder)
        return;
    this.sliding = true;
    this.slideCount = this.visibleAreaHeight;
    this.slideStep = 1;
    this.countStep = 0;
    this.HideArrows();
    this.SlideFolders(folder, document.getElementById("SS_Position" + folder).value == "DOWN");
}

function SlideFolders(folder, down)
{
    var step;
    //alert(document.getElementById("SS_MenuBar1").style.pixelHeight+1 );
    //alert(document.getElementById("SS_MenuBar1").style.height);
    if (down)
    {
        this.slideCount -= Math.floor(this.slideStep);
        if (this.slideCount < 0)
            this.slideStep += this.slideCount;
        step = Math.floor(this.slideStep);
        for (var i = 2; i <= folder; i++)
            if (document.getElementById("SS_Position" + i).value == "DOWN")
            {
                if (navigator.appName == "Netscape") {
                    var buttonTop = parseInt(document.getElementById("SS_MenuBar" + i).style.top);
                    document.getElementById("SS_MenuBar" + i).style.top = buttonTop - step;
                    var folderTop = parseInt(document.getElementById("SS_Folder" + i).style.top);
                    document.getElementById("SS_Folder" + i).style.top = folderTop - step;
                    //alert(document.getElementById("SS_Folder" + i).style.top);
                } else {
                    document.getElementById("SS_MenuBar" + i).style.pixelTop -= step;
                    document.getElementById("SS_Folder" + i).style.pixelTop -= step;
                    //alert(document.getElementById("SS_Folder" + i).style.pixelTop);
                }
            }

            //filter = /rect\((\d*)px (\d*)px (\d*)px (\d*)px\)/;

        var clipString = document.getElementById("SS_Folder" + folder).style.clip;
        var clip = clipString.match(this.filter);

        this.ClipFolder(folder, parseInt(clip[1]), this.visibleAreaWidth, (parseInt(clip[3]) + step), 0);

        var clipString = document.getElementById("SS_Folder" + this.currentFolder).style.clip;
        var clip = clipString.match(this.filter);
        //alert(clip);
        this.ClipFolder(this.currentFolder, parseInt(clip[1]), this.visibleAreaWidth, (parseInt(clip[3]) - step), 0);

        this.slideStep *= this.slideSpeed;
        if (this.slideCount > 0)
            setTimeout("Smenu.SlideFolders(" + folder + ",true)", 20);
        else
        {
            for (var k = 2; k <= folder; k++)
            {
                document.getElementById("SS_Position" + k).value = "UP";
            }
            this.currentFolder = folder;
            this.SetArrows();
            this.sliding = false;
        }
    }
    else
    {
        this.slideCount -= Math.floor(this.slideStep);
        if (this.slideCount < 0)
            this.slideStep += this.slideCount;
        step = Math.floor(this.slideStep);
        for (var i = folder + 1; i <= this.items; i++)
            if (document.getElementById("SS_Position" + i).value == "UP")
            {
                if (navigator.appName == "Netscape") {
                    var buttonTop = parseInt(document.getElementById("SS_MenuBar" + i).style.top);
                    document.getElementById("SS_MenuBar" + i).style.top = buttonTop + step;
                    var folderTop = parseInt(document.getElementById("SS_Folder" + i).style.top);
                    document.getElementById("SS_Folder" + i).style.top = folderTop + step;
                    //alert(document.getElementById("SS_MenuBar" + i).style.top);
                } else {
                    document.getElementById("SS_MenuBar" + i).style.pixelTop += step;
                    document.getElementById("SS_Folder" + i).style.pixelTop += step;
                    //alert(document.getElementById("SS_Folder" + i).style.pixelTop);
                }

            }

            //filter = /rect\((\d*)px (\d*)px (\d*)px (\d*)px\)/;

        var clipString = document.getElementById("SS_Folder" + folder).style.clip;
        var clip = clipString.match(this.filter);
        this.ClipFolder(folder, parseInt(clip[1]), this.visibleAreaWidth, (parseInt(clip[3]) + step), 0);

        var clipString = document.getElementById("SS_Folder" + this.currentFolder).style.clip;
        var clip = clipString.match(this.filter);
        this.ClipFolder(this.currentFolder, parseInt(clip[1]), this.visibleAreaWidth, (parseInt(clip[3]) - step), 0);

        this.slideStep *= this.slideSpeed;
        if (this.slideCount > 0)
            setTimeout("Smenu.SlideFolders(" + folder + ",false)", 20);
        else
        {
            for (var k = folder + 1; k <= this.items; k++)
                document.getElementById("SS_Position" + k).value = "DOWN";
            this.currentFolder = folder;
            this.SetArrows();
            this.sliding = false;
        }
    }
}

function ItemClicked(item)
{
    if (this.sliding)
        return;
    item.style.border = "1 inset #ffffff";
}

function OverItems(item)
{
    if (this.sliding)
        return;
    item.style.border = '1px solid #8BA8C6';
    item.style.backgroundColor = '#CEE9FE';
}

function OutItems(item)
{
    if (this.sliding)
        return;
    item.style.border = '0px solid #8BA8C6';
    item.style.backgroundColor = '#FFFFFF';
}

function ArrowClicked(arrow)
{
    if (this.sliding)
        return;
    arrow.style.border = "1 inset #ffffff";
}

function ArrowSelected(arrow)
{
    if (this.sliding)
        return;
    arrow.style.border = "0 none black";
    this.SlideItems(arrow.id == "SS_SlideUp");
}

function OverArrow(arrow)
{
    if (this.sliding)
        return;
    arrow.style.border = "1 outset #ffffff";

    var folder = document.getElementById("SS_Folder" + Smenu.currentFolder).style;
    if (navigator.appName == "Netscape") {
        var top = parseInt(document.getElementById("SS_MenuBar" + Smenu.currentFolder).style.top);
        var height = parseInt(document.getElementById("SS_MenuBar" + Smenu.currentFolder).style.height);
    } else {
        var top = document.getElementById("SS_MenuBar" + Smenu.currentFolder).style.pixelTop;
        var height = document.getElementById("SS_MenuBar" + Smenu.currentFolder).style.pixelHeight;
    }
    var startTop = top + height;
}

function OutArrow(arrow)
{
    if (this.sliding)
        return;
    arrow.style.border = "0 none black";
}

function ClipFolder(folder, top, right, bottom, left)
{
    document.getElementById("SS_Folder" + folder).style.clip = clip = 'rect(' + top + ' ' + right + ' ' + bottom + ' ' + left + ')';
}

function Start()
{
    if (!this.started)
    {
        this.ClipFolder(1, 0, this.visibleAreaWidth, this.visibleAreaHeight, 0);
        this.SetArrows();
    }
}

function SetArrows()
{
    var downHeight = document.getElementById("SS_SlideDown").height;
    var upWidth = document.getElementById("SS_SlideUp").width;
    var downWidth = document.getElementById("SS_SlideDown").width;
    if (navigator.appName == "Netscape") {
        var top = parseInt(document.getElementById("SS_MenuBar" + this.currentFolder).style.top);
        var height = parseInt(document.getElementById("SS_MenuBar" + this.currentFolder).style.height);

        document.getElementById("SS_SlideUp").style.top = top + height + this.visibleAreaHeight - downHeight - 20;
        document.getElementById("SS_SlideUp").style.left = this.width - upWidth - this.borderWidth - 10;
        document.getElementById("SS_SlideDown").style.top = top + height + 20;
        document.getElementById("SS_SlideDown").style.left = this.width - downWidth - this.borderWidth - 10;

        var startTop = top + height;
        var floderTop = parseInt(document.getElementById("SS_Folder" + this.currentFolder).style.top);
        var floderHeight = parseInt(document.getElementById("SS_Folder" + this.currentFolder).style.height);

    } else {
        var top = document.getElementById("SS_MenuBar" + this.currentFolder).style.pixelTop;
        var height = document.getElementById("SS_MenuBar" + this.currentFolder).style.pixelHeight;

        document.getElementById("SS_SlideUp").style.pixelTop = top + height + this.visibleAreaHeight - downHeight - 20;
        document.getElementById("SS_SlideUp").style.pixelLeft = this.width - upWidth - this.borderWidth - 10;
        document.getElementById("SS_SlideDown").style.pixelTop = top + height + 20;
        document.getElementById("SS_SlideDown").style.pixelLeft = this.width - downWidth - this.borderWidth - 10;

        var startTop = top + height;
        var floderTop = document.getElementById("SS_Folder" + this.currentFolder).style.pixelTop;
        var floderHeight = document.getElementById("SS_Folder" + this.currentFolder).style.pixelHeight;

    }

    if (floderTop < startTop)
        document.getElementById("SS_SlideDown").style.visibility = "visible";
    else
        document.getElementById("SS_SlideDown").style.visibility = "hidden";

    if (floderHeight - (startTop - floderTop) > this.visibleAreaHeight)
        document.getElementById("SS_SlideUp").style.visibility = "visible";
    else
        document.getElementById("SS_SlideUp").style.visibility = "hidden";
}

function HideArrows()
{
    document.getElementById("SS_SlideUp").style.visibility = "hidden";
    document.getElementById("SS_SlideDown").style.visibility = "hidden";
}

function SlideItems(up)
{
    this.sliding = true;
    this.slideCount = Math.floor(this.slideArrowValue / this.ArrowSlideSpeed);
    up ? this.SlideItemsAction(-this.ArrowSlideSpeed) : this.SlideItemsAction(this.ArrowSlideSpeed);
}

function SlideItemsAction(value)
{
    if (navigator.appName == "Netscape") {
        var top = parseInt(document.getElementById("SS_Folder" + this.currentFolder).style.top);
        document.getElementById("SS_Folder" + this.currentFolder).style.top = top + value;

    } else {
        var top = document.getElementById("SS_Folder" + this.currentFolder).style.pixelTop;
        document.getElementById("SS_Folder" + this.currentFolder).style.pixelTop = top + value;
    }

    //filter = /rect\((\d*)px (\d*)px (\d*)px (\d*)px\)/;
    var clipString = document.getElementById("SS_Folder" + this.currentFolder).style.clip;
    var clip = clipString.match(this.filter);
    this.ClipFolder(this.currentFolder, (parseInt(clip[1]) - value), parseInt(clip[2]), (parseInt(clip[3]) - value), parseInt(clip[4]));
    this.slideCount--;
    if (this.slideCount > 0)
        setTimeout("Smenu.SlideItemsAction(" + value + ")", 20);
    else
    {
        if (Math.abs(value) * this.ArrowSlideSpeed != this.slideArrowValue)
        {
            document.getElementById("SS_Folder" + this.currentFolder).style.pixelTop += (value / Math.abs(value) * (this.slideArrowValue % this.ArrowSlideSpeed));
            //filter = /rect\((\d*)px (\d*)px (\d*)px (\d*)px\)/;
            var clipString = document.getElementById("SS_Folder" + this.currentFolder).style.clip;
            var clip = clipString.match(this.filter);
            this.ClipFolder(this.currentFolder, (parseInt(clip[1]) - (value / Math.abs(value) * (this.slideArrowValue % this.ArrowSlideSpeed))), parseInt(clip[2]), (parseInt(clip[3]) - (value / Math.abs(value) * (this.slideArrowValue % this.ArrowSlideSpeed))), parseInt(clip[4]));
        }
        this.SetArrows();
        this.sliding = false;
    }
}
function Onwheel()
{

    if (navigator.appName == "Netscape") {
        var top = parseInt(document.getElementById("SS_MenuBar" + Smenu.currentFolder).style.top);
        var height = parseInt(document.getElementById("SS_MenuBar" + Smenu.currentFolder).style.height);
        var folderTop = parseInt(document.getElementById("SS_Folder" + Smenu.currentFolder).style.top)
        var folderHeight = parseInt(document.getElementById("SS_Folder" + Smenu.currentFolder).style.height)


    } else {
        var top = document.getElementById("SS_MenuBar" + Smenu.currentFolder).style.pixelTop;
        var height = document.getElementById("SS_MenuBar" + Smenu.currentFolder).style.pixelHeight;
        var folderTop = document.getElementById("SS_Folder" + Smenu.currentFolder).style.pixelTop;
        var folderHeight = document.getElementById("SS_Folder" + Smenu.currentFolder).style.pixelHeight;

    }
    var startTop = top + height;


    if (event.wheelDelta >= 120 && folderTop < startTop)
    {
        Smenu.ArrowSelected(SS_SlideDown);
    }
    else if (event.wheelDelta <= -120 && folderHeight - (startTop - folderTop) > Smenu.visibleAreaHeight)
    {
        Smenu.ArrowSelected(SS_SlideUp);
    }
}