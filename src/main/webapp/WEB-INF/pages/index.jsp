<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Prog.kiev.ua</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <style>
        body {
            padding: 15px;
        }

        #info {
            display: flex;
            justify-content: space-between;
            margin-bottom: 40px;
        }

        #info h1 {
            margin-top: 0;
        }

        #info #balanceInfo {
            font-size: 20px;
            font-weight: bold;
        }

        table {
            font-size: 25px;
        }

        td {
            padding: 20px 0 !important;
        }

        #transactionControl button {
            margin-right: 15px;
        }
    </style>
</head>

<body>
<div id="info">
    <div>
        <h1>${login}</h1>
        <p id="balanceInfo">Your balance: ${balance}</p>
    </div>

    <form action="/logout" method="post">
        <button type="submit" value="Sign out..." class="btn btn-default navbar-btn">Sign out...</button>
    </form>
</div>

<div class="container">

    <div id="transactionControl">
        <ul id="groupList" class="nav navbar-nav">
            <li>
                <button type="button" id="new_transaction" class="btn btn-default navbar-btn">Add Transaction
                </button>
            </li>
            <li>
                <button type="button" id="add_bookkeeper" class="btn btn-default navbar-btn">Add Bookkeeper
                </button>
            </li>
            <li>
                <button type="button" id="report" class="btn btn-default navbar-btn">Reports</button>
            </li>
            <li>
                <button type="button" id="my_transaction" class="btn btn-default navbar-btn">Transaction
                </button>
            </li>
            </li>
        </ul>
    </div>


    <table class="table table-striped">
        <thead>
        <tr>
            <td><b>Requisite</b></td>
            <td><b>Balance</b></td>
        </tr>
        </thead>
        <c:forEach items="${bookkeepers}" var="bookkeeper">
            <tr>
                <td>${bookkeeper.requisite}</td>
                <td>${bookkeeper.balance}</td>
            </tr>
        </c:forEach>
    </table>
</div>


<script>
    $('.dropdown-toggle').dropdown();

    $('#new_transaction').click(function () {
        window.location.href = '/transaction';
    });

    $('#add_bookkeeper').click(function () {
        window.location.href = '/add_bookkeeper';
    });

    $('#report').click(function () {
        window.location.href = '/report';
    });

    $('#my_transaction').click(function () {
        window.location.href = '/my_transaction';
    });
</script>
</body>
</html>