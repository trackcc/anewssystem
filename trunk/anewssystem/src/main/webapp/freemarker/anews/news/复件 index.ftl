<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl">
    <TITLE>管理新闻</TITLE>
    <#include "/include/extjs.ftl">
    <script type="text/javascript" src="${ctx}/widgets/anews/news-20071006.js"></script>
  </head>
  <body>
    <div id="loading">
      <div class="waitting">请稍候...</div>
    </div>
    <div id="tabs">
      <div id="tab1"></div>
      <div id="tab2"></div>
      <div id="tab3"></div>
      <div id="tab4"></div>
      <div id="tab5"></div>
    </div>
    <div id="gridPanel">
      <div id="lightgrid" style="border: 0px solid #cccccc; overflow: hidden; width:auto;height:100%;"></div>
    </div>
    <!-- 增加、修改、查看 弹出框内容-->
    <div id="news-content">
    <table width="95%" border="0" align="center" valign="middle" cellpadding="0" cellspacing="4">
      <tr height="50%"><td>&nbsp;</td></tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">分类：</td>
        <td width="30%">
          <div class="x-form-item">
            <input id="category" type="text" size="20">
          </div>
        </td>
<#--
        <td align="right" width="15%">id：</td>
-->
        <td width="35%">
          <div class="x-form-item">
            <input id="id" type="hidden" size="25" readonly>
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">标题：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="name" type="text" size="40">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">副标题：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="subtitle" type="text" size="40">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">跳转链接：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="link" type="text" size="40" disabled style="background:#DDDDDD;">
            <input type="checkbox" onchange="if(this.checked){document.getElementById('link').disabled=false;document.getElementById('link').style.background='#FFFFFF';}else{document.getElementById('link').disabled=true;document.getElementById('link').style.background='#DDDDDD';}">使用链接
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">来源：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="source" type="text" size="40">&lt;=<a href="javascript:document.getElementById('source').value='【本站原创】';void(0);">【本站原创】</a> <a href="javascript:document.getElementById('source').value='【转帖】';void(0);">【转帖】</a>
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">简介：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <textarea id="summary" rows="2" cols="40"></textarea>
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">内容：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="content" value="">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">编辑：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="editor" type="text" size="25" readonly value="${(Session.loginUser.truename)!}">
          </div>
        </td>
        <td width="15%" align="right" style="font-size:12px;">发布日期：</td>
        <td width="35%">
          <div class="x-form-item">
            <input id="updateDate" type="text" size="25" readonly>
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">关键字：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input id="tags" type="text">&lt;=
<#list tagList! as item>
  <#if (item_index > 5)><#break/></#if>
        <a href="javascript:if(document.getElementById('tags').value!=''){document.getElementById('tags').value+=',${item.name}';}else{document.getElementById('tags').value='${item.name}';}void(0);">${item.name}</a><#if item_has_next> , </#if>
</#list>
<#if (tagList?? && tagList?size > 5)>
        <a id="tagMoreInfo" href="javascript:document.getElementById('tagMore').style.display='inline';document.getElementById('tagMoreInfo').style.display='none';void(0);">【更多】</a><div id="tagMore" style="display:none">
  <#list tagList! as item>
    <#if (item_index > 5)>
          <a href="javascript:if(document.getElementById('tags').value!=''){document.getElementById('tags').value+=',${item.name}';}else{document.getElementById('tags').value='${item.name}';}void(0);">${item.name}</a><#if item_has_next> , </#if>
    </#if>
  </#list>
        </div>
</#if>
        <br>用来查找相关文章，可输入多个关键字，中间用“,”隔开。关键字中不能出现英文“,”。
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">分页：</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <select name="page" id="page">
              <option value="0">不分页</option>
              <option value="1">手工分页</option>
              <option value="2">自动分页</option>
            </select>注：手动分页符使用编辑器中的“<b>插入分页符</b>”<br>
            自动分页时的每页大约字符数（包含HTML标记）： <input type="text" id="pagesize" value="1000">
          </div>
        </td>
      </tr>
      <tr>
        <td width="15%" align="right" style="font-size:12px;">立即发布:</td>
        <td width="85%" colspan="3">
          <div class="x-form-item">
            <input type="checkbox" id="quick" value="1" <#if (config?? && config.newsNeedAudit==0)>checked</#if>>是（如果选中的话将直接发布，否则审核后才能发布。）
          </div>
        </td>
      </tr>
      <tr height="50%"><td>&nbsp;</td></tr>
    </table>
    </div>


<#--
    <table>
    <tr>
      <td class="left" width="15%"><@spring.messageText code="news.name" text="name"/>:</td>
      <td class="right">
        <input type="text" name="name" id="name" size="40"> 标题不能超过100个字符
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="15%"><@spring.messageText code="news.subtitle" text="subtitle"/>:</td>
      <td class="right">
        <input type="text" name="subtitle" id="subtitle" size="40"> 副标题不能超过100个字符
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="15%"><@spring.messageText code="news.link" text="link"/>:</td>
      <td class="right">
        <input type="text" name="link" id="link" size="40" disabled="true" style="background-color:#DDDDDD;">
        <input type="checkbox" name="useLink" onchange="if(this.checked){document.news.link.disabled=false;document.news.link.style.background='#FFFFFF';}else{document.news.link.disabled=true;document.news.link.style.background='#DDDDDD';}">使用链接
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="15%">关键字:</td>
      <td class="right">
        <input type="text" name="tags" id="tags" size="35" value="<#list (news.newsTags)! as item>${item.name}<#if item_has_next>,</#if></#list>">&lt;=
<#list tagList! as item>
  <#if (item_index > 5)><#break/></#if>
        <a href="javascript:if(document.news.tags.value!=''){document.news.tags.value+=',${item.name}';}else{document.news.tags.value='${item.name}';}void(0);">${item.name}</a><#if item_has_next> , </#if>
</#list>
<#if (tagList?? && tagList?size > 5)>
        <a id="tagMoreInfo" href="javascript:document.getElementById('tagMore').style.display='inline';document.getElementById('tagMoreInfo').style.display='none';void(0);">【更多】</a><div id="tagMore" style="display:none">
  <#list tagList! as item>
    <#if (item_index > 5)>
          <a href="javascript:if(document.news.tags.value!=''){document.news.tags.value+=',${item.name}';}else{document.news.tags.value='${item.name}';}void(0);">${item.name}</a><#if item_has_next> , </#if>
    </#if>
  </#list>
        </div>
</#if>
        <br>用来查找相关文章，可输入多个关键字，中间用“,”隔开。关键字中不能出现英文“,”。
      </td>
    </tr>
    <tr>
      <td class="left" width="15%"><@spring.messageText code="news.source" text="source"/>:</td>
      <td class="right">
        <input type="text" name="source" id="source" size="35">
        &lt;=<a href="javascript:document.news.source.value='【本站原创】';void(0);">【本站原创】</a>
        <a href="javascript:document.news.source.value='【转帖】';void(0);">【转帖】</a>
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="15%"><@spring.messageText code="news.editor" text="editor"/>:</td>
      <td class="right">
        <input type="text" name="editor" id="editor" size="35">
        &lt;=<a href="javascript:document.news.editor.value='【管理员】';void(0);">【管理员】</a>
        <a href="javascript:document.news.editor.value='【网站编辑】';void(0);">【网站编辑】</a>
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="15%"><@spring.messageText code="news.summary" text="summary"/>:</td>
      <td class="right">
        <textarea name="summary" id="summary" cols="70" rows="5"></textarea>简介在255个字符以内
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="15%"><@spring.messageText code="news.content" text="content"/>:</td>
      <td class="right">
        <input type="text" id="content">
        <label class="star">*</label>
      </td>
    </tr>
<#if news??>
    <tr>
      <td class="left" width="15%"><@spring.messageText code="news.updateDate" text="updateDate"/>:</td>
      <td class="right">${news.updateDate?datetime}</td>
    </tr>
</#if>
    <tr style="display:none">
      <td class="left" width="15%"><@spring.messageText code="news.image" text="image"/>:</td>
      <td class="right">
        <input type="text" name="image" id="image" size="40">
        <label class="star">*</label>
      </td>
    </tr>
    <tr style="display:none">
      <td class="left" width="15%"><@spring.messageText code="news.status" text="status"/>:</td>
      <td class="right">
        <input type="text" name="status" id="status" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr style="display:none">
      <td class="left" width="15%"><@spring.messageText code="news.hit" text="hit"/>:</td>
      <td class="right">
        <input type="text" name="hit" id="hit" size="35">
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="15%">是否进行内容分页:</td>
      <td class="right">
        <select name="page" id="page">
          <option value="0">不分页</option>
          <option value="1">手工分页</option>
          <option value="2">自动分页</option>
        </select>注：手动分页符使用编辑器中的“<b>插入分页符</b>”<br>
        自动分页时的每页大约字符数（包含HTML标记）： <input type="text" name="pagesize" id="pagesize" value="1000">
      </td>
    </tr>
    <tr>
      <td class="left" width="15%">立即发布:</td>
      <td class="right">
        <input type="checkbox" name="quick" id="quick" value="1" <#if (config?? && config.newsNeedAudit==0)>checked</#if>>是（如果选中的话将直接发布，否则审核后才能发布。）
      </td>
    </tr>
    <tr>
      <td colspan="2" class="bottom">
        <input type="submit" name="enter" class="submitButton" value="提交" style="margin-right:60px"/>
        <input type="submit" name="enter" class="submitButton" value="存为草稿" style="margin-right:60px"/>
        <input type="reset" name="enter" class="submitButton" value="清空" style="margin-right:60px"/>
      </td>
    </tr>
  </table>
    </div>
-->
    <div id="error_message" style="display:block"></div>
<script type="text/javascript">
//DWREngine.setErrorHandler(errorHandler);
function errorHandler(errorString, exception) {
    console.error(errorString);
    console.error(exception);
}
</script>
  </body>
</html>

