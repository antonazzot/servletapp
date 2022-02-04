<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Test Page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/css/style.css" rel="stylesheet"/>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>
<body>
<section class="container">
<div class="login">
<h1> Trainer control List </h1>
<form id="data" method="post" action="/web/mvc/trainer/traineract">
  <p>Выберите студента из группы</p>
    <c:forEach var = "entry" items="${groupT.studentMap}">
       <c:out value = "${entry.value.name}" />
       <input type="radio" name="studentId" value="${entry.key}"> </p>
    </c:forEach>

      <p>Выберите тему</p>
       <c:forEach var = "set" items="${groupT.theamsSet}">
               <c:out value = "${set.theamName}" />
               <input type="radio" name="thId" value="${set.id}"> </p>
       </c:forEach>


      <p>Выберите необходимое действие</p>
       <select name="act", form="data">
        <option value="create">ADD MARK</option>
        <option value="delete">Delete MARK </option>
        <option value="change">Change MARK</option>
        <option value="watch">Watch  MARK</option>
        </select>
     <p> <input name="groupId" type="hidden" value="${groupT.id}"/></p>
     <p><input name="mark" type="text" placeholder="ADD MARK"/><p>
     <p><input type="submit" value="Отправить"></p>
    </form>

<br>
   </div>
  </section>
<br>
 <jsp:include page="logout.jsp" />
</body>
</html>