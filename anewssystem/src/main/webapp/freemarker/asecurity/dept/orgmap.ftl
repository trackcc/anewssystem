<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>组织结构</title>
    <#include "/include/extjs.ftl"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/widgets/orgmap/orgmap.css" />
  </head>
  <body>
    <h1>组织结构图</h1>
    <hr/>
<#macro renderDept dept isFirst isLast>
  <li>
    <div class="<#if isFirst>first<#elseif isLast>last</#if><#if dept.leaf!=true> section</#if>"><a href="#">${dept.name!}</a></div>
  <#if dept.leaf!=true>
    <ul>
    <#list dept.children! as child>
      <@renderDept child child_index==0 child_has_next!=true/>
    </#list>
    </ul>
  </#if>
  </li>
</#macro>
    <div id="contain">
      <ul id="map" class="solo">
        <li>
          <div class="root section"><a href="#">临远研发中心</a></div>
          <ul>
<#list deptList! as dept>
  <@renderDept dept dept_index==0 dept_has_next!=true/>
</#list>
      </ul>
    </div>
  </body>
</html>
