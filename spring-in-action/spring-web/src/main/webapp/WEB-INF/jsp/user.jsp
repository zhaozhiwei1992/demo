<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>User</title>
  </head>
  <body bgcolor="white">
    <h2>UserInfo</h2>
    <div>
        用户: <span><c:out value="${user.name}"/></span>
        创建时间: <span><c:out value="${user.createTime}"/></span> <br/>
    <div/>
  </body>
</html>