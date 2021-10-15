<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Test Page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h1> Trainer control List </h1>
 <p>Name: ${student.name} </p>
  <p>Оценки Студента:</p>
  <p></p>
 <form id="data" action="hello">
         <c:forEach var = "entry" items="${map}">
         <c:out value = "${entry.key}" /> <br>
          <c:forEach var = "list" items="${entry.value}">
           <c:out value = "${list.valuesOfMark}" /> <br>
            </c:forEach>
          </c:forEach>
     <p><input type="submit" form="data" value="Вернуться на главную страницу"></p>
  </form>
<br>
<br>
<form  action="logout">
    <input type="submit" value="LogOut!">
</form>


</body>
</html>