<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
<link href="/css/style.css" rel="stylesheet"/>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>

<body>
<br>
<section class="container">
<div class="login">
<h1> Admin page </h1>
<form id="data" method="post" action="/web/mvc/views/adminact">
    <select name="entity", form="data">
        <option value="student">Student</option>
        <option value="trainer">Trainer</option>
        <option value="administrator">Administrator</option>
        <option value="group">Group</option>
        <option value="theam">Theam</option>
    </select>
    <br>
       <select name="act", form="data">
        <option value="create">ADD </option>
        <option value="delete">Delete </option>
        <option value="change">Change </option>
        <option value="watch">Watch </option>
    </select>

    <br>
    </hr>

    <input type="submit" form="data" value="  Do act   ">

</form>
 </div>
</section>

<br>

<form action="/web/mvc/views/propose">
<input type="submit" value="Candidate form">
</form>
<br>

<form action="/web/mvc/views/addsalary">
<input type="submit" value="Add salary">
</form>

<form action="/web/mvc/views/avarageSalary">
<input type="submit" value="Average salary">
</form>

<br>
 <jsp:include page="logout.jsp" />
</body>
</html>