<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import="java.util.*" %>

<head>
    <meta charset="UTF-8">
<link href="/css/style.css" rel="stylesheet"/>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    <title>Sender page</title>
</head>
<body>
<br> <br/>
<section class="container">
<div class="login">

  <form id="data" method = "post"  action="/web/mvc/views/individualsend">
   <p>Select a recipient</p>
    <input name="theam" type="hidden"  value = "${theam}" /> <br>
    <input name="message" type="hidden"  value = "${message}" /> <br>
    </hr>
<p>Administrator:</p>
</hr>
 <c:forEach var = "entry" items="${allAdmin}">
<div>
   <p> ID:    <c:out value = "${entry.key}" />  <input type="checkbox" name="id" value="${entry.key}" ><br>
   <p> Name:      <c:out value = "${entry.value.name}" /> </p>
   <p> Login:      <c:out value = "${entry.value.login}" /> </p>
</div>
<hr>
  </c:forEach>
</hr>
</hr>
</br>
  <p>Student:</p>
 <c:forEach var = "entry" items="${allStudent}">
<div>
   <p> ID:    <c:out value = "${entry.key}" />  <input type="checkbox" name="id" value="${entry.key}" ><br>
   <p> Name:      <c:out value = "${entry.value.name}" /> </p>
   <p> Login:      <c:out value = "${entry.value.login}" /> </p>
</div>
<hr>
  </c:forEach>
</hr>
</hr>
</br>
  <p>Trainer:</p>
 <c:forEach var = "entry" items="${allTrainer}">
<div>
   <p> ID:    <c:out value = "${entry.key}" />  <input type="checkbox" name="id" value="${entry.key}" ><br>
   <p> Name:      <c:out value = "${entry.value.name}" /> </p>
   <p> Login:      <c:out value = "${entry.value.login}" /> </p>
</div>
<hr>
  </c:forEach>
   <p><input type="submit" form="data" value="Send"></p>
  </form>


<br>
 <jsp:include page="mainpage.jsp" />
   </div>
  </section>
 <jsp:include page="logout.jsp" />
</body>
</html>