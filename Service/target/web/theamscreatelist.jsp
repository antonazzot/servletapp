<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Test Page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
  <form action="handler.php">
   <p>Выберите темы для группы</p>
   <p><input type="checkbox" name="th" value="MATH"> MATH</p>
   <p><input type="checkbox" name="th" value="PHISICK"> PHISICK</p>
   <p><input type="checkbox" name="th" value="LANGVAGE"> LANGVAGE</p>
   <p><input type="checkbox" name="th" value="MUSICK"> MUSICK</p>
   <p><input type="checkbox" name="th" value="HIMIC"> HIMIC</p>
   <p><input type="checkbox" name="th" value="BIOLOGIC"> BIOLOGIC</p>
   <p><input type="checkbox" name="th" value="ECONOMIC"> ECONOMIC</p>
   <p><input type="checkbox" name="th" value="FINANC"> FINANC</p>

    <p>Выберите студентов для группы</p>
    <c:forEach var = "entry" items="${map}">
       <c:out value = "${entry.key.name}" />
       <input type="checkbox" name="user" value="${entry.key.name}"> </p>
    </c:forEach>
    <p>Выберите тренера для группы</p>
        <c:forEach var = "entry1" items="${map1}">
           <c:out value = "${entry1.key.name}" />
           <input type="checkbox" name="trainer" value="${entry1.key.name}"> </p>
        </c:forEach>

   <p><input type="submit" value="Отправить"></p>
  </form>


</body>
</html>