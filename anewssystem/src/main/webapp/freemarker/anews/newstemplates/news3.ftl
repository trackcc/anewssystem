<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>${news.name}</title>
    <meta http-equiv="Content-Type" content="text/html;" charset="utf-8" />
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
<style type="text/css">
* {
  font-size:12px;
}
.style75 {
        COLOR: #ff6600
}
.style86 {
        FONT-WEIGHT: bold; FONT-SIZE: 11pt; COLOR: #6b1410
}
.rb {
        BACKGROUND: #f0f7ff
}
.ry {
        BACKGROUND: #fff8f0
}
.style102 {
        FONT-SIZE: 11pt; COLOR: #6b1410
}
.STYLE21 {
        COLOR: #333333
}
.z {
        FONT-SIZE: 12px; COLOR: #666666; LINE-HEIGHT: 20px; FONT-STYLE: normal
}
.indexlinkword {
        FONT-SIZE: 12px; COLOR: #666666; TEXT-DECORATION: none
}
.style129 {
        FONT-SIZE: 9pt; COLOR: #000000; TEXT-DECORATION: none
}
.style130 {
        COLOR: #000000;
        font-size: 10pt;
        text-decoration: none;
}
.style133 {font-size: 24pt}
.style134 {
        font-size: 24px;
        color: #049;
}
.style136 {FONT-SIZE: 9pt; COLOR: #049; TEXT-DECORATION: none; }
.style137 {color: #0055a1}
.style138 {FONT-SIZE: 11pt; COLOR: #004499; }
.style139 {
        TEXT-DECORATION: none;
        font-size: 9pt;
        color: 0055A1;
}
body {
        margin-top: 0px;
        background-color: #0071b5;
}
.style141 {
        font-size: 9pt;
        color: #049;
}
.style143 {FONT-WEIGHT: bold; FONT-SIZE: 11pt; COLOR: #000000; }
.style144 {COLOR: #000000; font-size: 11pt;}
</style>
    <link href="images/news/index.css" type="text/css" rel="stylesheet" />
    <style type="text/css">
.style133 {
}
.style133 A:link {
        TEXT-DECORATION: none
}
.style133 A:visited {
        TEXT-DECORATION: none
}
.style133 A:hover {
        TEXT-DECORATION: none
}
.style133 A:active {
        TEXT-DECORATION: none
}
.style128 {
        FONT-WEIGHT: normal
}
a:link{
    text-decoration: none;
} a:visited{
    text-decoration: none;
} a:hover{
    text-decoration: none;
} a:active{
    text-decoration: none;
}
</style>
  </head>

  <body>
    <table width="100%" border="1" cellspacing="0" cellpadding="1">
      <tr>
        <td bgcolor="#0071B5">
          <table width="82%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td bgcolor="#FFFFFF">
                <div style="text-align: center">

                  <table width="960" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>
                        <div style="text-align: left">
                          <span class="AT FL style141">首页&#160;&gt;&#160;资讯&#160;&gt;&#160;正文</span>

                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td bgcolor="808080"></td>
                            </tr>
                          </table>
                        </div>
                      </td>
                    </tr>
                  </table>

                  <table cellspacing="1" cellpadding="1" width="960" border="0">
                    <tbody>
                      <tr bgcolor="#ffffff">
                        <td valign="top" bgcolor="#ffffff">
                          <div class="style102" id="PL">
                            <div id="newsdetail-content" class="h4" style="text-align: center">
                              <table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="92B0DD">
                                <tr>
                                  <td height="138" bgcolor="EBF4FF">
                                    <div class="h4" style="text-align: center">
                                      <h3 class="style134"><br />
                                       ${news.name}</h3>
                                      <hr style="color: rgb(146, 176, 221); text-align: center" noshade="noshade" size="1" width="96%" />
                                    </div>

                                    <div class="newsdetail_content_shijian" style="text-align: center">
                                      <span class="style136">发布时间：${news.updateDate?date}</span>
                                    </div>

                                    <table width="88%" border="0" align="center" cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td>
                                          <div class="vc">
                                            <table width="100%" border="0" cellspacing="0" cellpadding="０">
                                              <tr>
                                                <td>
<#if (page?? && total>1)>
                                                  ${pageContent}
                                                  <br />
                                                  <#if (page>1)><a style="text-decorator:none;" href="${news.id}<#if page != 2>_${page-1}</#if>.html">上一页</a>&#160;</#if>第&nbsp;<#list 1..total as x><#if x != page><a href="${news.id}<#if x!=1>_${x}</#if>.html" style="background-color:#D2EAF6;border:1px solid #B7D8EE;padding:4px">${x}</a><#else>${x}</#if>&nbsp;</#list>页<#if (page<total)>&#160;<a href="${news.id}_${page+1}.html">下一页</a></#if></p>
<#else>
                                                  ${news.content}
                                                  <br />
</#if>
                                                </td>
                                              </tr>
                                            </table>
                                          </div>
                                        </td>
                                      </tr>
                                    </table>

                                    <div id="newsdetail_content_laiyuan">
                                      <ul>
                                        <li class="style129">
                                          <div class="style130" style="text-align: left">
                                            来源： ${news.source}
                                          </div>
                                        </li>

                                        <li class="style129">
                                          <div class="style130" style="text-align: left">
                                            责任编辑：${news.editor}
                                          </div>
                                        </li>
                                      </ul>

                                      <ul id="newsdetail_content_biaoqian">
                                      </ul>
                                    </div>
                                  </td>
                                </tr>
                              </table>
                            </div><br />


                            <table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="92B0DD">
                              <tbody>
                                <tr>
                                  <td bgcolor="#FFFFFF" height="0">
                                    <table cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                                      <tbody>
                                        <tr>
                                          <td width="270" height="26" background="" bgcolor="E2EAF7">
                                            <div class="style138" style="text-align: left">
                                              　　相关新闻
                                            </div>
                                          </td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </td>
                                </tr>

                                <tr>
                                  <td bgcolor="#FFFFFF" height="119">
                                    <div class="paihang_left" style="text-align: left">
                                      <table cellspacing="0" cellpadding="0" width="88%" align="center" border="0">
                                        <tbody>
                                          <tr>
                                            <td bgcolor="#FFFFFF">
<#list tagList! as item>
                                              <div class="paihang_left style137" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=146">${item.name}(${item.updateDate?date})</a></span>
                                              </div>
</#list>
                                            </td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </td>
                                </tr>
                              </tbody>
                            </table><br />


                            <div class="fanhui_info_banner">
                              <span class="style75" id="fanhui_info"><a href="http://epple.cn:8080/index/news2.htm?id=5">返回${news.newsCategory.name}</a></span>
                            </div>
                          </div>
                        </td>

                        <td width="303" rowspan="2" valign="top" bgcolor="FFFFFF">
                          <div id="kandian">
                            <div style="text-align: center">
                              <img src="" alt="" width="306" height="239" style="background-color: #000000" /><span class="style138">广告位zx</span>8
                            </div>

                            <table width="67%" border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="92B0DD">
                              <tbody>
                                <tr>
                                  <td bgcolor="#FFFFFF" height="0">
                                    <table cellspacing="0" cellpadding="0" width="270" align="center" border="0">
                                      <tbody>
                                        <tr>
                                          <td width="270" height="26" background="" bgcolor="E2EAF7">
                                            <div class="style138" style="text-align: center">
                                              今日焦点资讯排行<#--今日焦点-->
                                            </div>
                                          </td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </td>
                                </tr>

                                <tr>
                                  <td bgcolor="#FFFFFF" height="119">
                                    <div class="paihang_left" style="text-align: left">
                                      <table cellspacing="0" cellpadding="0" width="88%" align="center" border="0">
                                        <tbody>
                                          <tr>
                                            <td bgcolor="#FFFFFF">
                                              <div class="paihang_left style137" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=146">45万杭州产轮胎在美遭召回
                                                包括四个牌子 (2007-7-24)</a></span>
                                              </div>

                                              <div class="paihang_left" style137="" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=144">国际国内油市将持续季节性强势
                                                (2007-7-22)</a></span>
                                              </div>

                                              <div class="paihang_left" style137="" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=51">史上最坚固的MP4
                                                驰能T2900史上最坚固的驰能 (2007-7-15)</a></span>
                                              </div>

                                              <div class="paihang_left" style137="" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=38">国家开发银行浙江省分行行长徐勇来访
                                                (2007-7-15)</a></span>
                                              </div>

                                              <div class="paihang_left" style137="" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=36">取消退税后广东钢材出口不降反升
                                                (2007-7-15)</a></span>
                                              </div>

                                              <div class="paihang_left" style137="" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=35">金鱼涨价
                                                (2007-7-15)</a></span>
                                              </div>

                                              <div class="paihang_left" style137="" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=34">2G仅300出头
                                                大屏视频MP3爆最低价格2G仅300出头 大屏视频MP3爆最低价格 (2007-7-15)</a></span>
                                              </div>

                                              <div class="paihang_left" style137="" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=33">大理石雕刻 120G超薄硬盘
                                                (2007-7-15)</a></span>
                                              </div>

                                              <div class="paihang_left style137" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=32">持续狂降 佳能5D套装卖的火爆
                                                (2007-7-15)</a></span>
                                              </div>
                                            </td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </td>
                                </tr>
                              </tbody>
                            </table><br />
                            <br />


                            <table width="67%" border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="92B0DD">
                              <tbody>
                                <tr>
                                  <td bgcolor="#FFFFFF" height="0">
                                    <table cellspacing="0" cellpadding="0" width="270" align="center" border="0">
                                      <tbody>
                                        <tr>
                                          <td width="270" height="26" background="" bgcolor="E2EAF7">
                                            <div class="style138" style="text-align: center">
                                              网站精华<#--推荐-->
                                            </div>
                                          </td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </td>
                                </tr>

                                <tr>
                                  <td bgcolor="#FFFFFF" height="119">
                                    <div class="paihang_left" style="text-align: left">
                                      <table cellspacing="3" cellpadding="0" width="88%" align="center" border="0">
                                        <tbody>
                                          <tr>
                                            <td bgcolor="#FFFFFF">
                                              <div class="paihang_left style137" style="text-align: left">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                  <tr>
                                                    <td><span class="HC4"><tt class="33"><a href="http://msn.mtime.com/picture/"><img src="msn_files/1185867065081.jpg" alt="经典海报展"
                                                    border="0" height="80" width="80" /></a></tt></span></td>

                                                    <td><span class="HC4"><tt class="33"><a href="http://msn.mtime.com/picture/"></a><tt class="33"><a
                                                    href="http://msn.mtime.com/picture/"><img src="msn_files/1185867065081.jpg" alt="经典海报展" border="0" height="80"
                                                    width="80" /></a></tt></tt></span></td>

                                                    <td><span class="HC4"><tt class="33"><tt class="33"><a href="http://msn.mtime.com/picture/"><img
                                                    src="msn_files/1185867065081.jpg" alt="经典海报展" border="0" height="80" width="80" /></a></tt></tt></span></td>
                                                  </tr>

                                                  <tr>
                                                    <td class="style137"><tt class="style141">经典海报展</tt>)</td>

                                                    <td><span class="HC4"><tt class="33"><a href="http://msn.mtime.com/picture/"></a><tt
                                                    class="style141">经典海报展</tt>)</tt></span></td>

                                                    <td><tt class="style141">经典海报展</tt>)</td>
                                                  </tr>
                                                </table><span class="HC4"><tt class="33"><a href="http://msn.mtime.com/picture/"></a></tt></span><span class="HC4"><tt
                                                class="33"><a href="http://msn.mtime.com/picture/"></a><a
                                                href="http://msn.mtime.com/group/europeanfilm/discussion/89407/">画家手中的明星们</a><br />
                                                 <a href="http://msn.mtime.com/group/europeanfilm/discussion/89407/">画家手中的明星们</a><br />
                                                 <a href="http://msn.mtime.com/group/europeanfilm/discussion/89407/">画家手中的明星们</a></tt><tt class="33"><a
                                                href="http://msn.mtime.com/picture/"></a></tt><br />
                                                <tt class="33"><a href="http://msn.mtime.com/group/europeanfilm/discussion/89407/">画家手中的明星们</a><br />
                                                <a href="http://msn.mtime.com/group/europeanfilm/discussion/89407/">画家手中的明星们</a></tt><tt class="33"><a
                                                href="http://msn.mtime.com/picture/"></a></tt><br />
                                                </span>
                                              </div>
                                            </td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </td>
                                </tr>
                              </tbody>
                            </table><br />


                            <table width="67%" border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="92B0DD">
                              <tbody>
                                <tr>
                                  <td bgcolor="#FFFFFF" height="0">
                                    <table cellspacing="0" cellpadding="0" width="270" align="center" border="0">
                                      <tbody>
                                        <tr>
                                          <td width="270" height="26" background="" bgcolor="E2EAF7">
                                            <div class="style138" style="text-align: center">
                                              <tt><a href="http://sounews.ynet.com/">每日新闻滚动三万条</a></tt><a href="http://sounews.ynet.com/">新闻速递</a><#--最近新闻-->
                                            </div>
                                          </td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </td>
                                </tr>

                                <tr>
                                  <td bgcolor="#FFFFFF" height="119">
                                    <div class="paihang_left" style="text-align: left">
                                      <table cellspacing="0" cellpadding="0" width="88%" align="center" border="0">
                                        <tbody>
                                          <tr>
                                            <td bgcolor="#FFFFFF">
                                              <div class="paihang_left style137" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=146">45万杭州产轮胎在美遭召回
                                                包括四个牌子 (2007-7-24)</a></span>
                                              </div>

                                              <div class="paihang_left" style137="" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=144">国际国内油市将持续季节性强势
                                                (2007-7-22)</a></span>
                                              </div>

                                              <div class="paihang_left" style137="" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=51">史上最坚固的MP4
                                                驰能T2900史上最坚固的驰能 (2007-7-15)</a></span>
                                              </div>

                                              <div class="paihang_left" style137="" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=38">国家开发银行浙江省分行行长徐勇来访
                                                (2007-7-15)</a></span>
                                              </div>

                                              <div class="paihang_left" style137="" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=36">取消退税后广东钢材出口不降反升
                                                (2007-7-15)</a></span>
                                              </div>

                                              <div class="paihang_left" style137="" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=35">金鱼涨价
                                                (2007-7-15)</a></span>
                                              </div>

                                              <div class="paihang_left" style137="" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=34">2G仅300出头
                                                大屏视频MP3爆最低价格2G仅300出头 大屏视频MP3爆最低价格 (2007-7-15)</a></span>
                                              </div>

                                              <div class="paihang_left" style137="" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=33">大理石雕刻 120G超薄硬盘
                                                (2007-7-15)</a></span>
                                              </div>

                                              <div class="paihang_left style137" style="text-align: left">
                                                <img height="10" src="" width="9" /> <span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=32">持续狂降 佳能5D套装卖的火爆
                                                (2007-7-15)</a></span>
                                              </div>
                                            </td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </td>
                                </tr>
                              </tbody>
                            </table><br />
                            <br />

<#--
                            <table width="67%" border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="92B0DD">
                              <tbody>
                                <tr>
                                  <td bgcolor="#FFFFFF" height="0">
                                    <table cellspacing="0" cellpadding="0" width="270" align="center" border="0">
                                      <tbody>
                                        <tr>
                                          <td width="270" height="26" background="" bgcolor="E2EAF7">
                                            <div class="style138" style="text-align: center">
                                              <a href="http://msn.ynet.com/event/">精彩专题</a>
                                            </div>
                                          </td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </td>
                                </tr>

                                <tr>
                                  <td bgcolor="#FFFFFF" height="119">
                                    <div class="paihang_left" style="text-align: left">
                                      <table cellspacing="0" cellpadding="0" width="88%" align="center" border="0">
                                        <tbody>
                                          <tr>
                                            <td width="41%" bgcolor="#FFFFFF">
                                              <div class="paihang_left">
                                                <span class="HC4"><tt class="33"><a href="http://msn.mtime.com/picture/"><img src="msn_files/1185867065081.jpg" alt="经典海报展"
                                                border="0" height="80" width="80" /></a></tt></span>
                                              </div>
                                            </td>

                                            <td width="59%" bgcolor="#FFFFFF"><span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=51">史上最坚固的MP4 驰能T2900史上最坚固的驰能
                                            (2007-7-15)</a></span></td>
                                          </tr>

                                          <tr>
                                            <td bgcolor="#FFFFFF"><span class="HC4"><tt class="33"><a href="http://msn.mtime.com/picture/"><br />
                                             <img src="msn_files/1185867065081.jpg" alt="经典海报展" border="0" height="80" width="80" /></a></tt></span></td>

                                            <td bgcolor="#FFFFFF"><span class="style139"><a href="http://epple.cn:8080/index/news3.htm?id=51">史上最坚固的MP4 驰能T2900史上最坚固的驰能
                                            (2007-7-15)</a></span></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </td>
                                </tr>
                              </tbody>
                            </table>
-->
                          </div>
                        </td>
                      </tr>
                    </tbody>
                  </table>

                </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>

    <div style="text-align: center">
      <br />
    </div>
  </body>
</html>
