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


  <p>ВВедите новое значение для изменения оценки</p>
  <p>либо выберите оценку для удаления</p>
 <form id="data" action="changeandcreatemark">

    <c:forEach var = "en" items="${thst}">
     <c:out value = "${en.value.name}" />
     <c:out value = "${en.key}" />
    <input name="th" contentType="${en.key}"  />
    <input name="student" contentType="${en.value.id}">
    <br>

     </c:forEach>
    <c:forEach var = "entry" items="${map}">
    <input name="mark" type="text" placeholder="${entry.value}"/>
    <input type="checkbox" name="del" value="${entry.key}">
    <br>
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