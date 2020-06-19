<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mr Bookkeeper</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- CSS only -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <!-- JS, Popper.js, and jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
    <style>
        body{
            background: url("https://thumbs.dreamstime.com/b/vector-seamless-pattern-business-money-icons-image-consisting-finance-use-as-background-59853064.jpg");
        }
        div {
            padding: 10px;
        }
        table {
            font-size: 25px;
        }

        td {
            padding: 20px 0 !important;
        }
        img {
            width: 200px;
            height: 220px;
        }

    </style>
</head>
    <body>
    <div class="container-fluent">
        <div class="row">
            <div class="col align-self-start">
                <h1>${login}</h1>
                <p id="balanceInfo">               <svg class="bi bi-server" width="2em" height="2em" viewBox="0 0 25 25" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                    <path d="M13 2c0-1.105-2.239-2-5-2S3 .895 3 2s2.239 2 5 2 5-.895 5-2z"/>
                    <path d="M13 3.75c-.322.24-.698.435-1.093.593C10.857 4.763 9.475 5 8 5s-2.857-.237-3.907-.657A4.881 4.881 0 0 1 3 3.75V6c0 1.105 2.239 2 5 2s5-.895 5-2V3.75z"/>
                    <path d="M13 7.75c-.322.24-.698.435-1.093.593C10.857 8.763 9.475 9 8 9s-2.857-.237-3.907-.657A4.881 4.881 0 0 1 3 7.75V10c0 1.105 2.239 2 5 2s5-.895 5-2V7.75z"/>
                    <path d="M13 11.75c-.322.24-.698.435-1.093.593-1.05.42-2.432.657-3.907.657s-2.857-.237-3.907-.657A4.883 4.883 0 0 1 3 11.75V14c0 1.105 2.239 2 5 2s5-.895 5-2v-2.25z"/>
                </svg>Your balance: ${balance} ₴</p>
            </div>
            <div class="col-8 align-self-center">
                <nav class="navbar navbar-expand-lg navbar-light bg-light">
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav">
                            <li>
                            <button type="button" id="new_transaction" class="btn btn-lg navbar-btn">Add Transaction   <svg class="bi bi-pencil" width="1em" height="1em" viewBox="0 0 20 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                <path fill-rule="evenodd" d="M11.293 1.293a1 1 0 0 1 1.414 0l2 2a1 1 0 0 1 0 1.414l-9 9a1 1 0 0 1-.39.242l-3 1a1 1 0 0 1-1.266-1.265l1-3a1 1 0 0 1 .242-.391l9-9zM12 2l2 2-9 9-3 1 1-3 9-9z"/>
                                <path fill-rule="evenodd" d="M12.146 6.354l-2.5-2.5.708-.708 2.5 2.5-.707.708zM3 10v.5a.5.5 0 0 0 .5.5H4v.5a.5.5 0 0 0 .5.5H5v.5a.5.5 0 0 0 .5.5H6v-1.5a.5.5 0 0 0-.5-.5H5v-.5a.5.5 0 0 0-.5-.5H3z"/>
                            </svg>
                            </button>
                        </li>
                            <li>
                                <button type="button" id="add_user" class="btn btn-lg navbar-btn">Add User    <svg class="bi bi-plus-square" width="1em" height="1em" viewBox="0 0 20 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                    <path fill-rule="evenodd" d="M8 3.5a.5.5 0 0 1 .5.5v4a.5.5 0 0 1-.5.5H4a.5.5 0 0 1 0-1h3.5V4a.5.5 0 0 1 .5-.5z"/>
                                    <path fill-rule="evenodd" d="M7.5 8a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1H8.5V12a.5.5 0 0 1-1 0V8z"/>
                                    <path fill-rule="evenodd" d="M14 1H2a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>
                                </svg>
                                </button>
                            </li>
                            <li>
                                <button type="button" id="report" class="btn btn-lg navbar-btn">Reports    <svg class="bi bi-graph-up" width="1em" height="1em" viewBox="0 0 20 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                    <path d="M0 0h1v16H0V0zm1 15h15v1H1v-1z"/>
                                    <path fill-rule="evenodd" d="M14.39 4.312L10.041 9.75 7 6.707l-3.646 3.647-.708-.708L7 5.293 9.959 8.25l3.65-4.563.781.624z"/>
                                    <path fill-rule="evenodd" d="M10 3.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 .5.5v4a.5.5 0 0 1-1 0V4h-3.5a.5.5 0 0 1-.5-.5z"/>
                                </svg></button>
                            </li>
                            <li>
                                <button type="button" id="my_transaction" class="btn btn-lg navbar-btn">Transaction   <svg class="bi bi-bar-chart-fill" width="1em" height="1em" viewBox="0 0 20 20" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                    <rect width="4" height="5" x="1" y="10" rx="1"/>
                                    <rect width="4" height="9" x="6" y="6" rx="1"/>
                                    <rect width="4" height="14" x="11" y="1" rx="1"/>
                                </svg>
                                </button>
                            </li>
                            </li>
                        </ul>
                    </div>
                </nav>
            </div>
            <div class="col align-self-start">
                <form action="/logout" method="post">
                    <button type="submit" value="Sign out..." class="btn btn-dark">Sign out...</button>
                </form>
            </div>
        </div>
        <div class="row align-items-end">
            <div class="col align-self-end">

            </div>
            <div class="col-6 align-self-end">

                <table class="table table-striped">
                    <thead>
                    <h1>Requisite</h1>
                    </thead>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.requisite}</td>
                            <td>${user.balance} ₴</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div class="col align-self-end">

            </div>
        </div>
    </div>
    <script>
    $('.dropdown-toggle').dropdown();

    $('#new_transaction').click(function () {
    window.location.href = '/transaction';
    });

    $('#add_user').click(function () {
    window.location.href = '/add_user';
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
