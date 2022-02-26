<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Cart Application</title>
</head>
<body>
<h1>View Cart</h1>
<a href="test">Submit</a>
<form action="${flowExecutionUrl}" method="post">
    <input type="hidden" name="_eventId" value="confirm"/>
    <input type="text" name="userName" id="userName" value="ttang"/>
    <input type="password" name="password" id="password" value="1232"/>
    <input type="hidden" name="test" value="testdfd1"/>
    <input type="hidden" name="execution" value="${flowExecutionKey}"/>
    <input type="submit" value="sss"/>
</form>
<a href="${flowExecutionUrl}&_eventId=returnToIndex">return</a>
</body>
</html>