<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Test Page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

<h1> Student List </h1>

 <p>Name: ${student.name} </p>

  <p>Информация о вашей успеваемости</p>



    <c:forEach var = "entry" items="${mapmap}">
    <p> <c:forEach var = "map" items="${entry.value}">
                <c:out value = "${map.key.theamName}" /> <br>
                    <c:forEach var = "list" items="${map.value}">
                                <c:out value = "${list.valuesOfMark}" /> <br>
                 </c:forEach> </p> <hr>
       </c:forEach>
   </c:forEach>



<br>
<br>
<br>
<form  action="logout">
    <input type="submit" value="Go to LogIN page!">
</form>


</body>
</html>