<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>Users</title>
  </head>
  <body bgcolor="white">
    <h2>UserList</h2>

    <c:forEach items="${userList}" var="user">
        <ul>
            <li >
                <span><c:out value="${user.id}"/></span>
                <span><c:out value="${user.createTime}"/></span>
            </li>
        </ul>
    </c:forEach>
  </body>
</html>