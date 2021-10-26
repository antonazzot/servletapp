<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>This Page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

<h1> Change user </h1>
 <form id="data"  action="updateUser">

         <c:forEach var = "entry" items="${map}">
         <c:out value = "${entry.value.name}" />
         <input type="radio" name="id" value="${entry.key}" >
          </c:forEach>

     <p><input type="submit" form="data" value="Отправить"></p>
  </form>
<br>
<br>
<form  action="hello">
    <input type="submit" value="Go to Main Admin!">
</form>
<br>
<form  action="logout">
    <input type="submit" value="LogOut!">
</form>


</body>
</html>