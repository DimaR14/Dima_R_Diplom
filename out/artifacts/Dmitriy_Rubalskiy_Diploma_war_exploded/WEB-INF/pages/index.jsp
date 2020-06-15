<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Index</title>
</head>
<body>
<div align="center">
    <form action="/addCard" method="POST">
        Requisite:<br/><input type="text" name="requisite"><br/>
        Balance:<br/><input type="text" name="balance"><br/>
        <input type="submit" />
    </form>
</div>
</body>
</html>