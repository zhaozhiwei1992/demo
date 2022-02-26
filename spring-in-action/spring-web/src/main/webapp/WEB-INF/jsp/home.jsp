<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<html>
    <head>
        <title>HomePage</title>
    </head>
    <body>
        <%-- <h1>This is a HomePage</h1> --%>
        <h1><s:message code="system.welcome"/></h1>
        <h1>${message}</h1>

        <h1>如果用管理员登录下边有惊喜</h1>
        <security:authorize access="hasRole('ROLE_ADMIN')">
            <h1>Hello <security:authentication property="principal.username"/> !</h1>
        </security:authorize>
    </body>
</html>