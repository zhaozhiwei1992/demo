<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/style.css" type="text/css" />
<title>Welcome to jar home</title>
</head>
<body>
<br/>
el.jsp in public
<br/>
原生写法:
<br/>
now date : <%=(request.getAttribute("date")).toString()%>
<br/>
el表达式写法:
<br/>
el now date : ${requestScope.date}
<br/>
不需要必须用requestScope, model属性可以直接获取
<br/>
el username : ${username}

<br/>
<% String username="zhangsan"; request.setAttribute("username", username); %>
<c:out value="${empty username}"></c:out>
<br/>
<c:out value="${username}"></c:out>

<table border="1" align="center" width="50%">
    <tr>
        <th>用户编号</th>
        <th>用户账号</th>
        <th>用户年龄</th>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr border="1" align="center" width="50%">
            <td>${user.id }</td>
            <td>${user.name }</td>
            <td>${user.age }</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>