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

 <p>Name: ${student} </p>

  <p>Оцценки Студента:</p>
  <p></p>
 <form id="data" action="">


         <c:forEach var = "entry" items="${map}">
         <c:out value = "${entry.key}" /> <br>
          <c:forEach var = "list" items="${entry.value}">
           <c:out value = "${list.valuesOfMark}" /> <br>
            </c:forEach>
          </c:forEach>

     <p><input type="submit" form="data" value="Отправить"></p>
  </form>
<br>
<br>
<br>
<form  action="logout">
    <input type="submit" value="Go to LogIN page!">
</form>


</body>
</html>