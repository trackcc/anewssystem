<#--
<#assign ctx=springMacroRequestContext.getContextPath()/>
<#include "/include/taglibs.ftl">
<#include "/include/meta.ftl">
<#include "/include/messages.ftl">
<#include "/include/taglibs.ftl"/>
-->

<#--除法-->
<#macro divide a=0 b=0>
  <#if (a?? && a?is_number && b?? && b?is_number)>${a?int / b?int}<#else>0</#if>
</#macro>

<#--分页-->
<#--<@m.pg "${ctx}/admin/logView.htm" page.totalCount?int "SMT_type" page.currentPageNo/>-->
<#macro pg pageUrl2 pageTotal pageParamName pageNo=1 pageMaxItems=15 pageMaxIndex=5>
<#include "/include/taglibs.ftl"/>
  <@pager.pager items = pageTotal
            index = "center"
            maxPageItems = pageMaxItems
            maxIndexPages = pageMaxIndex
            isOffset = true
            export = "pageOffset,currentPageNumber=pageNumber"
            scope = "request"
            url = pageUrl2>
    <@pager.param name = pageParamName/>
    <@pager.index>
      <@pager.first>
        <a href="${pageUrl}&pageNo=${pageNumber}">[首页]</a>
      </@pager.first>
      <@pager.skip pages=pageMaxIndex*-1>
        <a href = "${pageUrl}&pageNo=${pageNumber}">[跳前]</a>
      </@pager.skip>
      <@pager.prev>
        <a href="${pageUrl}&pageNo=${pageNumber}">[上一页]</a>
      </@pager.prev>
      <@pager.pages>
        <a href="${pageUrl}&pageNo=${pageNumber}">
        <#if (pageNumber == pageNo)>
          <font color="red">${pageNumber}</font>
        <#else>
          ${pageNumber}
        </#if>
        </a>
      </@pager.pages>
      <@pager.next>
        <a href="${pageUrl}&pageNo=${pageNumber}">[下一页]</a>
      </@pager.next>
      <@pager.skip pages=pageMaxIndex>
        <a href="${pageUrl}&pageNo=${pageNumber}">[跳进]</a>
      </@pager.skip>
      <@pager.last>
        <a href="${pageUrl}&pageNo=${pageNumber}">[尾页]</a>
      </@pager.last>
    </@pager.index>
  </@pager.pager>
</#macro>

<#--分页-->
<#--<@m.pg2 "${ctx}/admin/logView.htm" "a=1&b=2" page.totalCount?int page.currentPageNo/>-->
<#macro pg2 pageUrl2 params pageTotal pageNo=1 pageMaxItems=15 pageMaxIndex=5>
<#include "/include/taglibs.ftl"/>
  <@pager.pager items = pageTotal
            index = "center"
            maxPageItems = pageMaxItems
            maxIndexPages = pageMaxIndex
            isOffset = true
            export = "pageOffset,currentPageNumber=pageNumber"
            scope = "request"
            url = pageUrl2>${pageNo}
    <@pager.index>
      <@pager.first>
        <a href="${pageUrl}&pageNo=${pageNumber}&${params}">[首页]</a>
      </@pager.first>
      <@pager.skip pages=pageMaxIndex*-1>
        <a href = "${pageUrl}&pageNo=${pageNumber}&${params}">[跳前]</a>
      </@pager.skip>
      <@pager.prev>
        <a href="${pageUrl}&pageNo=${pageNumber}&${params}">[上一页]</a>
      </@pager.prev>
      <@pager.pages>
        <a href="${pageUrl}&pageNo=${pageNumber}&${params}">
        <#if (pageNumber == pageNo)>
          <font color="red">${pageNumber}</font>
        <#else>
          ${pageNumber}
        </#if>
        </a>
      </@pager.pages>
      <@pager.next>
        <a href="${pageUrl}&pageNo=${pageNumber}&${params}">[下一页]</a>
      </@pager.next>
      <@pager.skip pages=pageMaxIndex>
        <a href="${pageUrl}&pageNo=${pageNumber}&${params}">[跳进]</a>
      </@pager.skip>
      <@pager.last>
        <a href="${pageUrl}&pageNo=${pageNumber}&${params}">[尾页]</a>
      </@pager.last>
    </@pager.index>
  </@pager.pager>
</#macro>

<#--显示数据校验错误-->
<#macro showError path>
<@spring.bind path/>
<#if spring.status.errorMessages?? && (spring.status.errorMessages?size > 0)>
  <div class="error" width="80%">
    <#list spring.status.errorMessages as error>
      ${error}<br/>
    </#list>
  </div>
</#if>
</#macro>


<#macro selectParent defaultName parents current>
<select name="parent_id" id="parent_id">
  <option value="">${defaultName}</option>
  <#if (current.id)??>
    <#list parents! as parent>
      <#if parent.id!=current.id>
        <#if current.parent?? && parent.id==current.parent.id>
          <option value="${parent.id}" selected>${parent.name}</option>
        <#else>
          <option value="${parent.id}">${parent.name}</option>
        </#if>
      </#if>
    </#list>
  <#else>
    <#list parents! as parent>
      <option value="${parent.id}">${parent.name}</option>
    </#list>
  </#if>
</select>
</#macro>


<#macro jscalendar name dateValue="">
<input type="text" name="${name}" id="${name}" size="20" readonly value="<#if dateValue??>${dateValue}</#if>">
<button id="${name}Bt" type="button" class="button">...</button>
<script type="text/javascript">
Calendar.setup({
  inputField  : "${name}",
  ifFormat    : "%Y-%m-%d",
  button      : "${name}Bt"
});
<#if (!dateValue?? || dateValue=="")>
document.getElementById("${name}").value = new Date().print("%Y-%m-%d");
</#if>
</script>
</#macro>


<#macro fckeditor name width="80%" height="300px" toolbarSet="Basic" value="">
<#assign ctx=springMacroRequestContext.getContextPath()/>
<#assign fck=JspTaglibs["/WEB-INF/tld/FCKEditor.tld"] />
<@fck.editor id="${name}" basePath="${ctx}/widgets/fckeditor/" width="${width}" height="${height}" toolbarSet="${toolbarSet}">${value}</@fck.editor>
</#macro>


<#macro treeSelect treeNode level defaultValue=0>
<option value="${treeNode.id}" <#if defaultValue==treeNode.id>selected</#if>><#list 0..level as x>├</#list>${treeNode.name}</option>
<#list treeNode.children! as item>
<@treeSelect item level+1 defaultValue/>
</#list>
</#macro>
