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

  <p>ВВедите новое значение для изменения оценки</p>

    <form id="data" action="changeandcreatemark">
    <input type="hidden" name="student" value="${student.id}"/>
<input type="hidden" name="act" value="change"/>
    <c:forEach var = "entry" items="${map}">
    <c:out value = "${entry.key}" /> <br>
    <input type="hidden" name="th" value="${entry.key}"/>

     <c:forEach var = "list" items="${entry.value}">
                <input type="text"  name="marks"  placeholder="${list.valuesOfMark}"> <br>
       </c:forEach>
     </c:forEach>


     <p><input type="submit" form="data" value="Отправить"></p>
  </form>
<br>
<br>
<form  action="TrainerControlPage/trainerActList.jsp">
    <input type="submit" value="Go to Main Page">
</form>
<br>
<form  action="logout">
    <input type="submit" value="LogOut">
</form>


</body>
</html>