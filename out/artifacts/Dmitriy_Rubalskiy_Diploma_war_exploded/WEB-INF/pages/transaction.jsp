<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Transaction</title>
</head>
<body>
<div align="center">
    <form action="/add_transaction" method="POST">
        sum:<br/><input type="text" name="sum"><br/>
        date:<br/><input type="date" name="date"><br/>
        attribute:<br/><input type="text" name="attribute"><br/>
        title:<br/><input type="text" name="title"><br/>
        <input type="submit" />
    </form>
</div>
</body>
</html>