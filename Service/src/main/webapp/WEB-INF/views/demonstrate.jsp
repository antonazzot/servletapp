<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import="java.util.*" %>

<head>
    <meta charset="UTF-8">
<link href="/css/style.css" rel="stylesheet"/>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    <title>Demo page</title>
</head>
<body>
<br> <br/>
<section class="container">
<div class="login">
 <c:forEach var = "entry" items="${map}">
<div>
   <p> ID:    <c:out value = "${entry.key}" /> </p>
   <p> Name:      <c:out value = "${entry.value.name}" /> </p>
   <p> Login:      <c:out value = "${entry.value.login}" /> </p>
    <p> Age:     <c:out value = "${entry.value.age}" /> </p>
    <p> Email:     <c:out value = "${entry.value.email}" /> </p>
</div>
<br>
<hr>
  </c:forEach>

<br>
 <jsp:include page="mainpage.jsp" />
   </div>
  </section>
 <jsp:include page="logout.jsp" />
</body>
</html>