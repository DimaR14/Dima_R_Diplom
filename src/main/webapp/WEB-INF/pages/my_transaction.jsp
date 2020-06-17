<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Prog.kiev.ua</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <style>

        body{
            background: url("https://thumbs.dreamstime.com/b/vector-seamless-pattern-business-money-icons-image-consisting-finance-use-as-background-59853064.jpg");
        }

        table {
            font-size: 25px;
        }

        table input[type=checkbox] {
            width: 20px;
            height: 20px;
            margin-top: 8px;
        }

        td {
            padding: 20px 0 !important;
        }

        #delete_transaction {
            display: block;
            margin: 30px auto;
            width: 190px;
            height: 60px;
            background-color: red;
            border-radius: 500px;
            font-size: 35px;
            color: white;
            outline: none;
        }
        #title {
            float: none;
            margin-bottom: 50px;

        }

        #title a {
            color: black;
            font-size: 20px;
        }
    </style>
</head>
<body>

<button type="button" id="delete_transaction" class="btn btn-default navbar-btn">Delete</button>

<ul id="title" class="nav navbar-nav">
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
           aria-expanded="false">Choose title <span class="caret"></span></a>
        <ul class="dropdown-menu">
            <li><a href="/title/all">All</a></li>
            <c:forEach items="${title}" var="title">
                <li><a href="/title/${title.name}">${title.name}</a></li>
            </c:forEach>
        </ul>
    </li>
</ul>

<div class="container">
    <table class="table">
        <thead>
        <tr>
            <td></td>
            <td><b>Requisite</b></td>
            <td><b>Date</b></td>
            <td><b>Attribute</b></td>
            <td><b>Sum</b></td>
            <td><b>Title</b></td>
        </tr>
        </thead>
        <c:forEach items="${my_transaction}" var="my_transaction">
            <tr>
                <td><input type="checkbox" name="toDelete[]" value="${my_transaction.id}" id="checkbox_${my_transaction.id}"/></td>
                <td>${my_transaction.bookkeeper.requisite}</td>
                <td>${my_transaction.date}</td>
                <td>${my_transaction.attribute}</td>
                <td>${my_transaction.sum}</td>
                <td>${my_transaction.title}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<script>

    $('.dropdown-toggle').dropdown();

    $('#delete_transaction').click(function(){
        var data = { 'toDelete[]' : []};
        $(":checked").each(function() {
            data['toDelete[]'].push($(this).val());
        });
        $.post("/transaction/delete", data, function(data, status) {
            window.location.reload();
        });
    });
</script>
</body>
</html>