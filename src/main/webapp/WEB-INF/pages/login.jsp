<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Mr Bookkeeper login</title>
    <meta charset="UTF-8">
</head>
<style>
    body{
        background: url("https://thumbs.dreamstime.com/b/vector-seamless-pattern-business-money-icons-image-consisting-finance-use-as-background-59853064.jpg");
    }

    form {
        width: 300px;
        margin: 0 auto;
        font-family: Arial, sans-serif;
        font-size: 20px;

    }
    form input {
        width: 100%;
        line-height: 40px;
        font-size: 20px;
        padding-left: 15px;
        margin: 9px 0;
    }
    form button {
        width: 100%;
        height: 40px;
        margin-top: 15px;
        text-transform: uppercase;
        font-weight: bold;
    }
</style>
<body>

<form action="/login" method="POST">
    Login:<br/><input type="text" name="login"><br/>
    Password:<br/><input type="password" name="password"><br/>
    <button type="submit">login</button>

    <p><a href="/register">Register new user</a></p>

    <c:if test="${param.error ne null}">
        <p>Wrong login or password!</p>
    </c:if>

    <c:if test="${param.logout ne null}">
        <p>See you...</p>
    </c:if>
</form>

</body>
</html>
