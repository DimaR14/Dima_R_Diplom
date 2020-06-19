<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Register</title>

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
</head>


<body>

<form action="/register" method="POST">
    Enter Login:<br/><input type="text" name="login" required><br/>
    Enter Password:<br/><input type="password" name="password" required><br/>
    <button type="submit">register</button>
    <c:if test="${exists ne null}">
        <p>User already exists!</p>
    </c:if>
</form>


</body>
</html>