<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (dates) -->
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Book Club</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link href="<c:url value="Desktop/BookBroker2/src/main/resources/static/css/bootstrap.min.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="Desktop/BookBroker2/src/main/resources/static/css/main.css" />" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/main.css">
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-light bg-secondary navbar-expand-lg mr-auto p-3">
    <div class="container">
        <span class="navbar-brand mb-0 h1 title">
            <img src="/imgs/book.png" width="25" height="25" class="d-inline-block align-top" alt="Book">
            Book Club
        </span>
        <div>
            <ul class="navbar-nav gap-3">
                <li class="nav-item">
                    <a class="btn btn-outline-light title" href="/home">Back to Home</a>
                </li>
                <li class="nav-item">
                    <a class="btn btn-outline-light title" href="/logout">Logout</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="col d-flex align-items-center justify-content-center">
            <div class="card mt-5 text-center mb-3 p-3">
                <div class="card-body">
                    <h5 class="card-title title text-dark">New Book</h5>
                    <form:form action="/new/book" method="post" modelAttribute="book">
                        <div class="row mt-4">
                            <div class="col">
                                <form:label path="title">Title</form:label>
                                <form:input class="form-control"  path="title"/>
                            </div>
                            <div class="col">
                                <form:label path="author">Author</form:label>
                                <form:input class="form-control"  path="author"/>
                            </div>
                        </div>
                        <div class="row mt-4">
                            <div class="col">
                                <form:label path="thoughts">My thoughts</form:label>
                                <form:textarea class="form-control"  path="thoughts"/>
                            </div>
                        </div>
                        <div class="row mt-4">
                            <div class="col d-flex flex-column">
                                <form:errors path="title" class="text-danger"/>
                                <form:errors path="author" class="text-danger"/>
                                <form:errors path="thoughts" class="text-danger"/>
                            </div>
                        </div>
                        <input class="btn btn-outline-light title mt-4 align-self-end" type="submit" value="Submit"/>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>