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
       <c:out value = "${entry.key}" />
       <c:out value = "${entry.value}" /><p>
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