<?xml version="1.0" encoding="UTF-8" ?>
<rss version="2.0">
  <channel>
    <title>anews</title>
    <link>http://code.google.com/p/anewssystem/</link>
    <language>zh-cn</language>
    <description >A news publishing system</description>
    <webMaster>xyz20003@gmail.com(Lingo)</webMaster>
    <lastBuildDate>${now?date}</lastBuildDate>
<#list page.result as item>
    <item>
      <title>${item.name?xml}</title>
      <link>http://localhost:8080/anews/${item.link!}</link>
      <pubDate>${item.updateDate?datetime}</pubDate>
      <description><![CDATA[${item.content}]]></description>
    </item>
</#list>
  </channel>
</rss>
