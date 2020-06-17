<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Report</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <style>

        body{
            background: url("https://thumbs.dreamstime.com/b/vector-seamless-pattern-business-money-icons-image-consisting-finance-use-as-background-59853064.jpg");
        }

        #tables, #info {
            display: flex;
            justify-content: space-around;
        }

        #tables .tableWrapper, #info div {
            width: 40%;
            border-top: 5px solid #FAF0E6;

        }

        #info p {
            font-size: 30px;
            margin-top: 15px;
        }

        #tables table {
            width: 100%;
        }

        #tables table td {
            width: 50%;
        }


        table {
            font-size: 25px;
        }

        td {
            padding: 20px 0 !important;
        }

        #vertSeparator {
            position: absolute;
            height: 100%;
            width: 1px;
            border-right: 5px solid #FAF0E6;
            top: 0;
        }

        #month {
            float: none;
            margin-bottom: 50px;

        }

        #month a {
            color: black;
            font-size: 20px;
        }

    </style>
</head>

<body>

<ul id="month" class="nav navbar-nav">
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
           aria-expanded="false">Choose month <span class="caret"></span></a>
        <ul class="dropdown-menu">
            <c:forEach items="${months}" var="month">
                <li><a href="/month/${month.key}">${month.value}</a></li>
            </c:forEach>
        </ul>
    </li>
</ul>

<div id="tables">
    <div class="tableWrapper">
        <table class="table">
            <thead>
            <tr>
                <td><b>Title</b></td>
                <td><b>Sum</b></td>
            </tr>
            </thead>
            <c:forEach items="${my_report1}" var="my_report">
                <tr>
                    <td>${my_report.title}</td>
                    <td>${my_report.sum}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div id="vertSeparator"></div>


    <div class="tableWrapper">
        <table class="table">
            <thead>
            <tr>
                <td><b>Title</b></td>
                <td><b>Sum</b></td>
            </tr>
            </thead>
            <c:forEach items="${my_report2}" var="my_report">
                <tr>
                    <td>${my_report.title}</td>
                    <td>${my_report.sum}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<div id="info">
    <div>
        <p>Your profit: ${profit}</p>
    </div>
    <div>
        <p>Your spending: ${spend}</p>
    </div>
</div>


<script>
    $('.dropdown-toggle').dropdown();

    $('.selectpicker').selectpicker();


</script>
</body>
</html>