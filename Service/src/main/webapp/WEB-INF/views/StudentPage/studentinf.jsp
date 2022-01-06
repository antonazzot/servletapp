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

  <p>Информация о вашей успеваемости</p>

    <c:forEach var = "entry" items="${studentMapMark}">
    <c:out value = "${entry.key.name}" /> <br>
    <p> <c:forEach var = "map" items="${entry.value}">
                <c:out value = "Theam name: " />
                <c:out value = "${map.key.theamName}" /> <br>
                <div>  Mark value:
                    <c:forEach var = "list" items="${map.value}">
                                <c:out value = "${list.valuesOfMark}" /> <br>
                 </c:forEach> </p> <hr>
                 <div/>
       </c:forEach>
   </c:forEach>

   </div>
  </section>
<br>
<br>
<br>
 <jsp:include page="logout.jsp" />


</body>
</html>