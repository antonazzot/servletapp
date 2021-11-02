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
  <p>Theam: ${th.theamName}  </p>

  <p>ВВедите новое значение для изменения оценки</p>

    <form id="data"  action="changeandcreatemark">
    <input type="hidden" name="student" value="${student.id}"/>
    <input type="hidden" name="act" value="change"/>
    <input type="hidden" name="th" value="${th.id}"/>

    <c:forEach var = "entry" items="${map}">
    <c:out value = "${entry.value.valuesOfMark}" /> <br>
           <input type="hidden" name="markid" value="${entry.key}"/>
          <input type="text"  name="marks"  placeholder="${list.valuesOfMark}"> <br>
    </c:forEach>



     <p><input type="submit" form="data" value="Отправить"></p>
  </form>
<br>
<br>
<form  action="hello">
    <input type="submit" value="Go to Main Page">
</form>
<br>
<form  action="logout">
    <input type="submit" value="LogOut">
</form>


</body>
</html>