<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Test Page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
  <form action="GroupCreaterServlet">
   <p>Выберите темы для группы</p>
      <c:forEach var = "set" items="${set}">
          <c:out value = "${set.value}" />
          <input type="checkbox" name="th" value="${set.value}"> </p>
       </c:forEach>

    <p>Выберите студентов для группы</p>
    <c:forEach var = "entry" items="${map}">
       <c:out value = "${entry.key.name}" />
       <input type="checkbox" name="user" value="${entry.key.id}"> </p>
    </c:forEach>
    <p>Выберите тренера для группы</p>
        <c:forEach var = "entry1" items="${map1}">

           <c:out value = "${entry1.key.name}" />
           <input type="checkbox" name="trainer" value="${entry1.key.id}"> </p>
        </c:forEach>

   <p><input type="submit" value="Отправить"></p>
  </form>


</body>
</html>