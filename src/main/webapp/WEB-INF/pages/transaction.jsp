<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Transaction</title>
    <style>
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
<form action="/add_transaction" method="POST">
    Sum:<br/><input type="text" name="sum"><br/>
    Date:<br/><input type="date" name="date"><br/>
    Attribute:<br/><input type="text" name="attribute"><br/>
    Title:<br/><input type="text" name="title"><br/>
    <button type="submit">send</button>

    <c:if test="${error==true}">
        <p>Enter sum and attribute please...</p>
    </c:if>
</form>


</body>
</html>