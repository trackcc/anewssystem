package anni.core.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * 完全使用js和css制作的图像幻灯片效果，没有flash实现的效果好看.
 * JSP：<pre><@modules.slide info=datas/></pre>
 * 需要的数据格式：<pre>
 * String[][] datas = new String[][3]{
 *     {"图片地址","图片链接","图片名称"}
 * };
 * </pre>
 *
 * @author Lingo
 */
public class SlideTag extends TagSupport {
    /** * 图片信息. */
    private String[][] info = null;

    /**
     * 设置图片信息.
     *
     * @param info 图片信息
     */
    public void setInfo(String[][] info) {
        this.info = info;
    }

    /**
     * 获得图片信息.
     *
     * @return 图片信息
     */
    public String[][] getInfo() {
        return info;
    }

    /**
     * 标签开始.
     *
     * @return 包含自行内容
     * @throws JspTagException 应该不会抛出这个异常
     */
    public int doStartTag() throws JspTagException {
        return EVAL_BODY_INCLUDE;
    }

    /**
     * 生成css.
     *
     * @param buff 字符缓存
     */
    private void appendStyle(StringBuffer buff) {
        buff.append("<style type=\"text/css\">")
            .append("#imgTitle{position:relative;left:1px;text-align:left;overflow:hidden;}")
            .append("#imgTitle_up{left:0px;text-align:left;height:1px;width:inherit;}")
            .append("#imgTitle_down{left:0px;text-align:left;width:inherit;}")
            .append(".imgClass{border:1px solid #FFCC00;}")
            .append("#txtFrom{text-align:center;vertical-align:middle;}")
            .append(".button{text-decoration:none;padding:2px 7px;background:#7B7B63;margin:0px;")
            .append("font:bold 12px sans-serif;}")
            .append("a.button,a.button:link,a.button:visited{font-family:sans-serif;text-decoration:none;")
            .append("color:#FFFFFF;background-color:#000000;}")
            .append("a.button:hover{font-family:sans-serif;text-decoration:none;color:#FFF;background:#FFCC00;}")
            .append(".buttonDiv{background:#000000;height:1px;width:22px;float:left;text-align:center;")
            .append("vertical-align:middle;}").append("</style>");
    }

    /**
     * 标签结束.
     *
     * @return 继续执行页面
     * @throws JspTagException 应该不会抛出这个异常
     */
    public int doEndTag() throws JspTagException {
        StringBuffer buff = new StringBuffer();
        appendStyle(buff);

        buff.append("<script type=\"text/javascript\">")
            .append("var imgWidth=230;").append("var imgHeight=170;")
            .append("var textFromHeight=30;")
            .append("var textStyle=\"f12\";")
            .append("var textLinkStyle=\"linkwhite\";")
            .append("var buttonLineOn=\"#FFCC00\";")
            .append("var buttonLineOff=\"#000\";")
            .append("var TimeOut=5000;").append("var imgUrl=new Array();")
            .append("var imgLink=new Array();")
            .append("var imgtext=new Array();")
            .append("var imgAlt=new Array();").append("var adNum=0;")
            .append("document.write('<style type=\"text/css\">');")
            .append("document.write('#focuseFrom{width:'+(imgWidth+2)+';margin:0px;padding:0px;")
            .append("height:'+(imgHeight+textFromHeight)+'px;overflow:hidden;}');")
            .append("document.write('#txtFrom{height:'+textFromHeight+'px;line-height:'+textFromHeight+'px;")
            .append("width:'+imgWidth+'px;}');")
            .append("document.write('#imgTitle{width:'+imgWidth+';top:-'+(textFromHeight+19)+'px;height:18px}');")
            .append("document.write('</style>');")
            .append("document.write('<div id=\"focuseFrom\">');");

        //.append("//焦点字框高度样式表 结束");
        for (int i = 0; i < info.length; i++) {
            buff.append("imgUrl[" + (i + 1) + "]='" + info[i][0] + "';")
                .append("imgtext[" + (i + 1) + "]='<a href=\""
                + info[i][1]
                + "\" target=\"_blank\" class=\"'+textLinkStyle+'\">"
                + info[i][2] + "</a>';")
                .append("imgLink[" + (i + 1) + "]='" + info[i][1] + "';")
                .append("imgAlt[" + (i + 1) + "]='" + info[i][2] + "';");
        }

        buff.append("function changeimg(n){").append("adNum=n;")
            .append("window.clearInterval(theTimer);").append("adNum--;")
            .append("nextAd();").append("}").append("function goUrl(){")
            .append("window.open(imgLink[adNum],'_blank');").append("}")
            .append("if (navigator.appName == \"Netscape\"){")
            .append("document.write('<style type=\"text/css\">');")
            .append("document.write('.buttonDiv{height:4px;width:21px;}');")
            .append("document.write('</style>');")
            .append("function nextAd(){")
            .append("if(adNum<(imgUrl.length-1))adNum++;")
            .append("else adNum=1;")
            .append("theTimer=setTimeout(\"nextAd()\", TimeOut);")
            .append("document.images.imgInit.src=imgUrl[adNum];")
            .append("document.images.imgInit.alt=imgAlt[adNum];")
            .append("document.getElementById('focustext').innerHTML=imgtext[adNum];")
            .append("document.getElementById('link'+adNum).style.background=buttonLineOn;")
            .append("document.getElementById('imgLink').href=imgLink[adNum];")
            .append("for (var i=1;i<imgUrl.length;i++){")
            .append("if (i!=adNum){document.getElementById('link'+i).style.background=buttonLineOff;}")
            .append("}").append("}")
            .append("document.write('<a id=\"imgLink\" href=\"'+imgLink[1]+'\" target=_blank class=\"linkwhite\">")
            .append("<img src=\"'+imgUrl[1]+'\" name=\"imgInit\" width='+imgWidth+' height='+imgHeight+' ")
            .append("border=1 alt=\"'+imgAlt[1]+'\" class=\"imgClass\"></a><div id=\"txtFrom\">")
            .append("<span id=\"focustext\" class=\"'+textStyle+'\">'+imgtext[1]+'</span></div>');")
            .append("document.write('<div id=\"imgTitle\">');")
            .append("document.write('<div id=\"imgTitle_down\">');")
            .append("for(var i=1;i<imgUrl.length;i++){")
            .append("document.write('<a href=\"javascript:changeimg('+i+')\" class=\"button\" ")
            .append("style=\"cursor:pointer\" title=\"'+imgAlt[i]+'\">'+i+'</a>');}")
            .append("document.write('</div>');")
            .append("document.write('<div id=\"imgTitle_up\">');")
            .append("for(var i=1;i<imgUrl.length;i++){")
            .append("document.write('<div id=\"link'+i+'\" class=\"buttonDiv\"></div>')}")
            .append("document.write('</div>');")
            .append("document.write('</div>');")
            .append("document.write('</div>');").append("nextAd();")
            .append("}").append("else{").append("var count=0;")
            .append("for (i=1;i<imgUrl.length;i++){")
            .append("if( (imgUrl[i]!=\"\") && (imgLink[i]!=\"\")&&(imgtext[i]!=\"\")&&(imgAlt[i]!=\"\")){")
            .append("count++;").append("} else {").append("break;")
            .append("}").append("}").append("function playTran(){")
            .append("if (document.all)")
            .append("imgInit.filters.revealTrans.play();").append("}")
            .append("var key=0;").append("function nextAd(){")
            .append("if(adNum<count)adNum++ ;").append("else adNum=1;")
            .append("if( key==0 ){").append("key=1;")
            .append("} else if (document.all){")
            .append("imgInit.filters.revealTrans.Transition=6;")
            .append("imgInit.filters.revealTrans.apply();")
            .append("playTran();").append("}")
            .append("document.images.imgInit.src=imgUrl[adNum];")
            .append("document.images.imgInit.alt=imgAlt[adNum];")
            .append("document.getElementById('link'+adNum).style.background=buttonLineOn;")
            .append("for (var i=1;i<=count;i++){")
            .append("if (i!=adNum){document.getElementById('link'+i).style.background=buttonLineOff;}")
            .append("}").append("focustext.innerHTML=imgtext[adNum];")
            .append("theTimer=setTimeout(\"nextAd()\", TimeOut);")
            .append("}")
            .append("document.write('<a target=_self href=\"javascript:goUrl()\">")
            .append("<img style=\"FILTER: revealTrans(duration=1,transition=5);\" src=\"javascript:nextAd()\" ")
            .append("width='+imgWidth+' height='+imgHeight+' border=0 vspace=\"0\" name=imgInit class=\"imgClass\">")
            .append("</a>');")
            .append("document.write('<div id=\"txtFrom\"><span id=\"focustext\" class=\"'+textStyle+'\"></span>")
            .append("</div>');")
            .append("document.write('<div id=\"imgTitle\">');")
            .append("document.write('<div id=\"imgTitle_down\">');")
            .append("for(var i=1;i<imgUrl.length;i++){")
            .append("document.write('<a href=\"#\" onclick=\"changeimg('+i+')\" class=\"button\" ")
            .append("style=\"cursor:pointer\" title=\"'+imgAlt[i]+'\">'+i+'</a>');}")
            .append("document.write('</div>');")
            .append("document.write('<div id=\"imgTitle_up\">');")
            .append("for(var i=1;i<imgUrl.length;i++){document.write('<div id=\"link'+i+'\" class=\"buttonDiv\">")
            .append("</div>')}").append("document.write('</div>');")
            .append("document.write('</div>');")
            .append("document.write('</div>');").append("}")
            .append("//IE结束").append("</script>");

        try {
            pageContext.getOut().write(buff.toString());
        } catch (IOException ex) {
            throw new JspTagException("Error!");
        }

        return EVAL_PAGE;
    }
}
