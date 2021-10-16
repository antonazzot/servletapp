<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Group create Page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<h1> ADD NEW Group </h1>
<body>
  <form id="data" method = "post" action="GroupCreaterServlet">
   <p>Выберите темы для группы</p>
      <c:forEach var = "set" items="${set}">
          <c:out value = "${set.value}" />
          <input type="checkbox" name="th" value="${set.value}"> <br>
       </c:forEach>

    <p>Выберите студентов для группы</p>
    <c:forEach var = "entry" items="${map}">
       <c:out value = "${entry.key.name}" />
       <input type="checkbox" name="user" value="${entry.key.id}"> </p>
    </c:forEach>

    <p>Выберите тренера для группы</p>
            <c:forEach var = "entry1" items="${map1}">
            <c:out value = "${entry1.key.name}" />
            <input type="radio" name="trainer" value="${entry1.key.id}"> </p>
             </c:forEach>


   <p><input type="submit" value="Отправить"></p>
  </form>
  <form  action="hello">
      <input type="submit" value="Go to Main Admin!">
  </form>
<br>

<form  action="logout">
    <input type="submit" value="LogOut!">
</form>

</body>
</html>