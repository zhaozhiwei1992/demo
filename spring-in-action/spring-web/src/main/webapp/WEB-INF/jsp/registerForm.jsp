<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>RegisterForm</title>
  </head>
  <body bgcolor="white">
    <h2>This is registerForm</h2>
    <form method="post">
      姓名: <input type="text" name="name" size="25" /> <br/>
      密码: <input type="password" name="password" size="25" /><br/>
      年龄: <input type="text" name="age" size="3" /><br/>
      <p></p>
      <input type="submit" value="Submit" />
      <input type="reset" value="Reset" />
    </form>
  </body>
</html>
