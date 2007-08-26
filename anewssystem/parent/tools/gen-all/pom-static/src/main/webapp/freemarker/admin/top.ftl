<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<HTML>
  <head>
    <#include "/include/meta.ftl">
    <title>顶部导航</title>
    <STYLE>
        BODY {
            FONT-SIZE: 12px;
            LINE-HEIGHT: 140%;
            margin-top: 0px;
            margin-left: 0px;
            margin-right: 0px;
        }
        A {
            FONT-SIZE: 12px;
            COLOR: #403f3f;
            LINE-HEIGHT: 140%;
            TEXT-DECORATION: none
        }
        A:hover {
            COLOR: #403f3f;
            LINE-HEIGHT: 140%;
            TEXT-DECORATION: underline
        }
        A:active {
            FONT-SIZE: 12px;
            COLOR: #403f3f;
            LINE-HEIGHT: 140%;
        }
        A:visited {
            FONT-SIZE: 12px;
            COLOR: #403f3f;
            LINE-HEIGHT: 140%;
        }
        table {
            FONT-SIZE: 12px;
            LINE-HEIGHT: 140%;
        }
        TD {
            FONT-SIZE: 12px;
            LINE-HEIGHT: 140%;
        }
    </STYLE>
  </head>

  <body leftmargin="1" topmargin="0" rightmargin="0">
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" background="${ctx}/images/menu/top_bg.gif">
      <tr>
        <td align="left">
          <a href=""><img src="${ctx}/images/menu/top_home.gif"border="0"></a>
        </td>
        <td align="right" valign="middle">
          <img src='${ctx}/images/menu/top_line1.gif' align="absmiddle"/>
          <a href="javascript:top.document.location.reload();">&nbsp;后台首页&nbsp;</a>
          <img src='${ctx}/images/menu/top_line1.gif' align="absmiddle">
          <a href='${ctx}/' target="_blank">&nbsp;网站首页&nbsp;</a>
          <img src='${ctx}/images/menu/top_line1.gif' align="absmiddle">
          <a href="#" onclick="hidFrame(this);this.blur();">&nbsp;隐藏菜单&nbsp;</a>
          <img src='${ctx}/images/menu/top_line1.gif' align="absmiddle">
          <a href="javascript:top.document.location.href='${ctx}/j_acegi_logout';">&nbsp;退出系统&nbsp;&nbsp;</a>
        </td>
        <td align="right"><img src="${ctx}/images/menu/top_line2.gif"></td>
    </tr>
</table>

    <SCRIPT type="text/javascript" language="javascript">
    //显示/隐藏左边帧( 默认情况下为显示左边帧)
    var currentChoice = true;

    function hidFrame(text)
    {
        if (currentChoice)
        {

            if (parent.document.getElementsByTagName("frameset")[1].cols == "154,*")
            {
                parent.document.getElementsByTagName("frameset")[1].cols = "0,*";
                text.innerHTML = '&nbsp;显示菜单&nbsp;';
            }
        }
        else
        {
            if (parent.document.getElementsByTagName("frameset")[1].cols == "0,*")
            {
                parent.document.getElementsByTagName("frameset")[1].cols = "154,*";
                text.innerHTML = '&nbsp;隐藏菜单&nbsp;';
            }
        }
        currentChoice = !currentChoice;
    }
    </SCRIPT>

  </body>
</HTML>
