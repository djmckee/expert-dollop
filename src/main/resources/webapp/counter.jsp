<html>
<%@ page session="true" %>
<body>
<jsp:useBean id='counter' scope='session' class='main.Counter' type="main.Counter"/>

<h1>JSP with MVC</h1>
Username: ${username}
Counter accessed
<jsp:getProperty name="counter" property="count"/>
times.<br/>
Counter last accessed by
<jsp:getProperty name="counter" property="last"/>
<br/>
<jsp:setProperty name="counter" property="last" value="<%= request.getRequestURI()%>"/>

<form action="counter" method="POST">
    <input type="text" name="username" value="username">
    <input type="submit"/>
</form>

</body>
</html>