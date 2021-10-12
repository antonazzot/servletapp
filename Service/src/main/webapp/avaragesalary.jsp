<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Test Page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<br>
<h1> Add salary list </h1>

<form id="data" action="calculateAvarageSalary">

         <c:forEach var = "entry" items="${map}">
         <c:out value = "${entry.key}" />  <input type="radio" name="trainer" value="${entry.key.id}" >
           <br>
          <c:forEach var = "list" items="${entry.value}">
           <c:out value = "${list.bigDecimalSalary}" /> <br>
            </c:forEach>
          </c:forEach>

    <br>  <p><h1> Введите колличество месяцев для расчета средней зарплаты </h1>
    <p><input type="text" name="sal" ></p>
     <p><input type="submit" form="data" value="Отправить"></p>
</form>

<br>

<br>
<form  action="logout">
    <input type="submit" value="Go to LogIN page!">
</form>
</body>
</html>