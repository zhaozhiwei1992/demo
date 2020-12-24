<html>
<head><title>展示用户数据~Freemarker语法</title>
    <meta charset="utf-9"></meta>
</head>
<body>
<table border="1" align="center" width="50%">
    <tr>
        <th>用户编号</th>
        <th>用户账号</th>
        <th>用户年龄</th>
    </tr>
    <#list users as user >
        <tr border="1" align="center" width="50%">
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.age}</td>
        </tr>
    </#list>
</table>

</body>
</html>