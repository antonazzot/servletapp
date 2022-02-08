<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import="java.util.*" %>

<head>
    <meta charset="UTF-8">
    <title>Add page</title>
<link href="/css/style.css" rel="stylesheet"/>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>
<body>
<section class="container">
<div class="login">
<h1> Registrate form for new student </h1>

<form id="data" method = "post" action="/web/mvc/tempstudent" >

    <br>
    Enter your NAME: <input  name="name", type="text"> <br>
    Enter your Login: <input  name="login", type="text"> <br>
    Enter your Password: <input  name="password", type="text"> <br>
    Enter your age: <input  name="age", type="text"> <br>
          <label>
            <input type="checkbox" name="remember_me" id="remember_me">
            Remember me on this computer
          </label>
    <br>
    <br>
    <input type="submit" form="data" />
<br>
      </div>
          </section>

<br>
 <jsp:include page="logout.jsp" />
</body>
</html>