<#if messages?exists>
  <div class="message" id="message">
    <#list messages as message>
      ${message}<br/>
    </#list>
  </div>
  <#include "/include/scriptaculous.ftl"/>
  <script type="text/javascript">
    new Effect.Highlight('message');
    window.setTimeout("Effect.DropOut('message')", 1000);
  </script>
</#if>
