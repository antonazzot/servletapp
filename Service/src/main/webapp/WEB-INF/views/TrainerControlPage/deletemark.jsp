<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Test Page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="static/css/style.css" rel="stylesheet">
</head>
<body>
<section class="container">
<div class="login">
<h1> Trainer control List </h1>

 <p>Name: ${student.name} </p>
 <p>Theam: ${th.theamName}  </p>

  <p>Выберите оценку для удаления</p>
 <form id="data" method = "post"  action="/web/mvc/trainer/dodeletemark">
<input type="hidden" name="student" value="${student.id}"/>
<input type="hidden" name="act" value="delete"/>
<input type="hidden" name="th" value="${th.id}"/>
<input type="hidden" name="groupId" value="${groupId}"/>
    <c:forEach var = "entry" items="${mapOfMark}">
        <c:out value = "${entry.value.valuesOfMark}" />
        <input type="checkbox"  name="markId"  value="${entry.key}"> <br>
     </c:forEach>

     <p><input type="submit" form="data" value="Отправить"></p>
  </form>
<br>
 <jsp:include page="mainpage.jsp" />
   </div>
  </section>
<br>
<br>
 <jsp:include page="logout.jsp" />
</body>
</html>