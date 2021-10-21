<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import="java.util.*" %>

<head>
    <meta charset="UTF-8">
    <title>IT page</title>
</head>
<body>
<br> <br/>

<c:forEach items="${list}" var="item">
   ${item}
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