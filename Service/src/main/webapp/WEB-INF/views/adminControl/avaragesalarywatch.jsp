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
<br>
<section class="container">
<div class="login">
<h1> Avarage salary list </h1>

<p>Name: ${trainer.name}  have got avarage salary ${avarage} </p>
<br>
<form  action="hello">
    <input type="submit" value="Go to Main Admin!">
</form>
<br>
<jsp:include page="mainpage.jsp" />
   </div>
  </section>
<br>
 <jsp:include page="logout.jsp" />
</body>
</html>