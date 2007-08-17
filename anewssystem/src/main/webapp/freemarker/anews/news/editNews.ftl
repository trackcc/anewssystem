<#assign ctx=springMacroRequestContext.getContextPath()>
<#assign ext="${ctx}/widgets/extjs/1.1-rc1">
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <#include "/include/calendar.ftl"/>
    <TITLE>新闻</TITLE>
    <link type="text/css" href="${ctx}/css/admin.css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/inc/validation.jsp"></script>
    <@validator.javascript formName="news" staticJavascript="false"/>
    <link rel="stylesheet" type="text/css" href="${ext}/resources/css/ext-all.css" />
    <script type="text/javascript" src="${ext}/adapter/yui/yui-utilities.js"></script>
    <script type="text/javascript" src="${ext}/adapter/yui/ext-yui-adapter.js"></script>
    <script type="text/javascript" src="${ext}/ext-all.js"></script>
    <script type="text/javascript" src="${ctx}/widgets/tree/uiField.js"></script>
    <script type="text/javascript" src="${ctx}/widgets/tree/treecombo.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/widgets/examples.css" />
    <script type="text/javascript">
Ext.BLANK_IMAGE_URL = '${ext}/resources/images/default/s.gif';
    </script>
  </head>
  <body>
<#if (news.id)?? && news.id != 0>
  <#assign action="update">
<#else>
  <#assign action="insert">
</#if>
<div class="pageTitle"><#if action=="update">修改新闻<#else>添加新闻</#if></div>
<#if news??>
  <@m.showError "news.*"/>
</#if>
<#include "/include/messages.ftl"/>
<form name="news"
    enctype="multipart/form-data"
    action="${ctx}/news/${action}.htm"
    method="post" onsubmit="return validateNews(this)">
  <@jodd.form beans="news" scopes="request">
      <input type="hidden" name="id">
    <table class="border" width="90%" cellSpacing="0" cellPadding="2" align="center">
    <tr>
      <td class="left" width="20%"><@spring.messageText code="news.category" text="category"/>:</td>
      <td class="right">
        <input type="hidden" id="category_id" name="category_id" <#if action=="update">value="${news.newsCategory.id}"</#if> />
        <input type="text" id="category_field" name="category_field" <#if action=="update">value="${news.newsCategory.name}"</#if> />
<#--
        <select id="category_id" name="category_id">
          <option value="">请选择</option>
<#list categoryList! as item>
  <#if news??>
    <@m.treeSelect item 0 news.category.id/>
  <#else>
    <@m.treeSelect item 0/>
  </#if>
</#list>
        </select>
-->
        [<a href="${ctx}/newscategory/tree.htm">修改分类信息</a>]
        <label class="star">*</label>
<#if (categoryList?size<1)>
<font color="red" style="font-weight:bold;">尚未添加分类信息，请首先添加分类信息。</font>
</#if>
      </td>
    </tr>
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
        <input type="text" name="tags" id="tags" size="35" value="<#list (news.tags)! as item>${item.name}<#if item_has_next>,</#if></#list>">&lt;=
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
<#if news??>
        <@m.fckeditor "content" "100%" "300px" "Default" news.content/>
<#else>
        <@m.fckeditor "content" "100%" "300px" "Default"/>
</#if>
        <label class="star">*</label>
      </td>
    </tr>
    <tr>
      <td class="left" width="15%"><@spring.messageText code="news.updateDate" text="updateDate"/>:</td>
      <td class="right">
<#if news??>
        <@m.jscalendar "updateDate" news.updateDate?string("yyyy-MM-dd")/>
<#else>
        <@m.jscalendar "updateDate" ""/>
</#if>
        <label class="star">*</label>
      </td>
    </tr>
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
</@jodd.form>
</form>

  </body>
</html>
