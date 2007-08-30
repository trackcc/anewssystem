<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<!--DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"-->
<HTML>
  <head>
    <#include "/include/meta.ftl">
    <title>左侧菜单</title>
    <@strutsmenu.useMenuDisplayer name="OutlookMenu">
      <@strutsmenu.displayMenu name="anews"/>
<#--
      <@strutsmenu.displayMenu name="search"/>
-->
      <@strutsmenu.displayMenu name="template"/>
      <@strutsmenu.displayMenu name="Logout"/>
    </@strutsmenu.useMenuDisplayer>

    <SCRIPT type="text/javascript" language=javascript>
        document.write("<font id=\"ready\">正在展开菜单<font>");

        SS_Top = -1;                        //菜单距离顶部的象素值；
        SS_Left = 0;                        //菜单距离左侧的象素值；
        SS_Margin = 10;                     //top and bottom margins between icons and borders

        SS_Width = 154;                     //菜单宽度
        SS_MenuBarHeight = 19;              //按钮的高度

        if (navigator.appName == "Netscape") {
            SS_Height = window.innerHeight;
        } else {
            SS_Height = document.body.clientHeight;
        }
        SS_SlideSpeed = 4;                    //菜单运动速度
        SS_BackgroundColor = "#FFFFFF";        //背景色
        SS_BackgroundImg = '../images/menu/bgup.jpg';  //菜单背景图片
        SS_BackgroundDownImg = '../images/menu/bgdown.jpg';  //菜单点击后的图片
        SS_BackgroundOverImg = '../images/menu/bgoverup.jpg';  //菜单经过时的图片
        SS_BackgroundOverDownImg = '../images/menu/bgoverdown.jpg';  //菜单点击后经过的图片
        SS_ItemsSpacing = 0;                   //2个图标间的距离
        SS_ItemsLeftSpacing = 5;               //图标与左边的距离

        SS_BorderWidth = 0;                    //border宽度
        SS_BorderStyle = "ridge";              //border风格
        SS_BorderColor = "#D4E2FF";            //border颜色

        SS_IconsWidth = 16;                    //图标宽度
        SS_IconsHeight = 16;                   //图标高度

        SS_MenuBarFontFamily = "bold";             //按钮上字体的字型
        SS_MenuBarFontSize = 9;                    //按钮上字体大小
        SS_MenuBarFontColor = "#3e5190";           //按钮上字体颜色
        SS_MenuBarFontShadowColor = "#000000";     //按钮上字体阴影颜色


        SS_LabelFontFamily = "bold";           //LOGO下字体的字型
        SS_LabelFontSize = 9;                  //LOGO下面的字体大小
        SS_LabelFontColor = "#1861C2";         //LOGO下的字体颜色
        SS_LabelFontShadowColor = "#FFFFFF";   //LOGO下的字体颜色
        SS_LabelMargin = 7;                    //margin between labels and icons

        SS_UpArrow = "../images/menu/arrowup.gif";            //向上滚动的logo箭头
        SS_DownArrow = "../images/menu/arrowdown.gif";        //向下滚动的logo箭头
        SS_ArrowWidth = 16;                    //箭头的宽度
        SS_ArrowHeight = 16;                   //箭头的高度
        SS_ArrowSlideSpeed = 10;               //项目列表滚动的速度；

        SS_FolderItem = 4;
        ITEM_TITLE = '标题';
    </SCRIPT>

    <script type="text/javascript" src="${ctx}/js/menu.js"></script>

  </head>

  <body onmousewheel="Onwheel()">
  </body>

</HTML>
