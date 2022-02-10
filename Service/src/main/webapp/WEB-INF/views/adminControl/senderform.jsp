<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sender</title>
<link href="/css/style.css" rel="stylesheet"/>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>

<body>
<br>
<section class="container">
<div class="login">
<h1> Admin page </h1>
<form id="data" method="post" action="/web/mvc/views/messager">
    <select name="tousers", form="data">
        <option value="student">Student</option>
        <option value="trainer">Trainer</option>
        <option value="administrator">Administrator</option>
        <option value="tempstudent">tempstudent</option>
        <option value="oneuser">User</option>
    </select>
    <br>
      Enter message theam: <br>
        <input name="theam" type="text" placeholder="theam name"  />
        Enter message: <br>
        <input name="message" type="text"  />

    <br>
    </hr>

    <input type="submit" form="data" value="  Do act   ">

</form>
 </div>
</section>

<br>
 <jsp:include page="mainpage.jsp" />

<br>
 <jsp:include page="logout.jsp" />
</body>
</html>