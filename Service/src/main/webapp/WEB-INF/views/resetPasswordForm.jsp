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
<h1> Reset your passswword </h1>

<form id="data" method = "post" action="/web/mvc/resetpassword" >

    <br>
    Enter your Login: <input  name="login", type="text"> <br>
    Enter your EMAIL: <input  name="email", type="text"> <br>
    Enter new Password: <input  name="password", type="text"> <br>
    Repeat new Password: <input  name="repeatpassword", type="text"> <br>

    <br>
    <br>
    <input type="submit" form="data" />
    </form>
<br>
      </div>
          </section>

<br>
 <jsp:include page="logout.jsp" />
</body>
</html>