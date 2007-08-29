<#assign ctx=springMacroRequestContext.getContextPath()>
<#include "/include/taglibs.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
  <head>
    <#include "/include/meta.ftl"/>
    <title>Drag and Drop</title>
    <#include "/include/extjs.ftl"/>
    <script src="${ctx}/widgets/dd/dd.js" type="text/javascript"></script>
    <link href="${ctx}/widgets/dd/dd.css" type="text/css" rel="stylesheet"/>
  </head>
  <body>
    <p>新闻分类：<#list newsCategoryList! as item>[<a href="#" class="add-block" id="${item.id}">${item.name}</a>] </#list></p>
    <div class="section" id="container">
      <div class="slot" id="slot1">
<#list newsCategoryList! as item>
        <div class="block" id="block${item.id}">
          <div class="operation">[<a href="#" class="add-line" id="addLine${item.id}">+</a>][<a href="#" class="delete-line" id="deleteLine${item.id}">-</a>][<a href="#" class="delete-block" id="deleteBlock${item.id}">x</a>]</div>
          <div class="title">${item.name}</div>
          <div class="content">
            <p>&nbsp;新闻</p>
            <p>&nbsp;新闻</p>
            <p>&nbsp;新闻</p>
            <p>&nbsp;新闻</p>
            <p>&nbsp;新闻</p>
          </div>
          <div class="foot">更多</div>
        </div>
</#list>
      </div>
      <div class="slot" id="slot2">
      </div>
      <hr />
    </div>
  </body>
</html>

