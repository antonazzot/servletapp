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

 <p>Student name: ${student.name} </p>
  <p>Theam: ${th.theamName}  </p>

  <p>ВВедите новое значение для изменения оценки</p>

    <form id="data" method = "post"  action="/web/mvc/trainer/dochangemark">
    <input type="hidden" name="studentId" value="${student.id}"/>
    <input type="hidden" name="thId" value="${th.id}"/>

    <c:forEach var = "entry" items="${markIDListbyTheam}">
    <c:out value = "Текущее значение оценки" /> <br>
    <c:out value = "${entry.value.valuesOfMark}" /> <br>
           <input type="hidden" name="markId" value="${entry.key}"/>
          <input type="text"  name="markValue"  placeholder="${entry.value.valuesOfMark}"> <br>
    </c:forEach>

     <p><input type="submit" form="data" value="Изменить оценки"></p>
  </form>
<br>
 <jsp:include page="mainpage.jsp" />
<br>
   </div>
  </section>
<br>
 <jsp:include page="logout.jsp" />


</body>
</html>