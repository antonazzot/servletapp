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
<h1> Trainer control List </h1>

 <form id="data" action="/traineract">

      <p>Выберите студентов из группы</p>
      <c:forEach var = "entry" items="${map}">
         <c:out value = "${entry.value.name}" />
         <input type="radio" name="student" value="${entry.key.id}"> </p>
      </c:forEach>
      <br>
      <p>Выберите необходимое действие</p>

       <select name="act", form="data">
        <option value="create">ADD MARK</option>
        <option value="delete">Delete MARK </option>
        <option value="change">Change MARK</option>
        <option value="watch">Watch  MARK</option>
        </select>

     <p><input type="submit" value="Отправить"></p>
    </form>

<br>
  <form  action="hello">
      <input type="submit" value="Go to Main Admin!">
  </form>
<br>
<form  action="logout">
    <input type="submit" value="Go to LogIN page!">
</form>


</body>
</html>