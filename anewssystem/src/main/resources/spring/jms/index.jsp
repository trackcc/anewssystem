<%
	org.springframework.web.context.WebApplicationContext ctx = org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext(application);
	anni.components.activemq.MessageProducer producer = (anni.components.activemq.MessageProducer)ctx.getBean("messageProducer");
	out.print(producer);
	anni.components.activemq.MessageBean messageBean = new anni.components.activemq.MessageBean();
	producer.send(messageBean);
%>
<html>
  <head>
<script>
function go() {
	location="index.jsp"
}
</script>
  </head>
  <body>
    <h2>Hello World!</h2>
    <button onClick="go()">Go!</button>
  </body>
</html>
