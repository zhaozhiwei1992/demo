<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<html>
    <head>
        <title>HomePage</title>
    </head>
    <body>
        <%-- <h1>This is a HomePage</h1> --%>
        <h1><s:message code="system.welcome"/></h1>
        <h1>${message}</h1>
    </body>
</html>