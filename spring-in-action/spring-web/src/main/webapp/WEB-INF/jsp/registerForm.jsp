<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<html>
  <head>
    <title>RegisterForm</title>
  </head>
  <body bgcolor="white">
    <h2>This is registerForm</h2>
    <sf:form method="post" modelAttribute="user"
    enctype="multipart/form-data"
    >
      统一异常区:  <sf:errors path="*" element="div"/> <br/>
      <input type="file" name="profilePicture"><br/>
      照片名称: <input type="text" name="picture.name"/> <br/>
      姓名: <sf:input type="text" name="name" size="25" path="name"/> <sf:errors path="name"/><br/>
      密码: <sf:input type="password" name="password" size="25" path="password" /><br/>
      年龄: <sf:input type="text" name="age" size="3" path="age"/><sf:errors path="age"/><br/>
      <p></p>
      <input type="submit" value="Submit" />
      <input type="reset" value="Reset" />
    </sf:form>
  </body>
</html>
