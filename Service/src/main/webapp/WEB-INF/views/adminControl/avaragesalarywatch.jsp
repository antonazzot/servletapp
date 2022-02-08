<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Average salary page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/css/style.css" rel="stylesheet"/>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>
<body>
<br>
<section class="container">
<div class="login">
<h1> Average salary list </h1>

<p>Name: ${trainer.name}  have got average salary ${avarage} </p>
<br>
<jsp:include page="mainpage.jsp" />
   </div>
  </section>
<br>
 <jsp:include page="logout.jsp" />
</body>
</html>