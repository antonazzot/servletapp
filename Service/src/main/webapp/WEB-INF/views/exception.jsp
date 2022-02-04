<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Test Page</title>
<link href="/css/style.css" rel="stylesheet"/>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<section class="container">
<div class="login">
<h1> Введено неверное значение </h1>
<h1> Произошла ошибка </h1>
<p>${massage}</p>
<br>
 <jsp:include page="mainpage.jsp" />
<br>
<form  action="/web/mvc/logout">
    <input type="submit" value="LogIN page!">
</form>
   </div>
  </section>

</body>
</html>