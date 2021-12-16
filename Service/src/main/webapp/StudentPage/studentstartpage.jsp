<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Test Page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="static/css/style.css" rel="stylesheet">
</head>
<body>
<section class="container">
<div class="login">
<h1> Student List </h1>

 <p>Name: ${student.name} </p>

  <p>Страница студента </p>
<a href="studentservlet" > Смотреть оценки </a>
<br>
<br>
   </div>
  </section>
<br>
 <jsp:include page="logout.jsp" />


</body>
</html>