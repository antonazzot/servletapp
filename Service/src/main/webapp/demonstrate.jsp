<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import="java.util.*" %>

<head>
    <meta charset="UTF-8">
    <title>Demo page</title>
</head>
<body>
<br> <br/>

 <c:forEach var = "entry" items="${map}">
<div>
   <p> ID:    <c:out value = "${entry.key}" /> </p>
   <p> Name:      <c:out value = "${entry.value.name}" /> </p>
   <p> Login:      <c:out value = "${entry.value.login}" /> </p>
   <p> Password:     <c:out value = "${entry.value.password}" /> </p>
    <p> Age:     <c:out value = "${entry.value.age}" /> </p>
</div>
<hr>
  </c:forEach>

<br>
  <form  action="hello">
      <input type="submit" value="Go to Main Page!">
  </form>
<br>
<form  action="logout">
    <input type="submit" value="LogOut">
</form>
</body>
</html>