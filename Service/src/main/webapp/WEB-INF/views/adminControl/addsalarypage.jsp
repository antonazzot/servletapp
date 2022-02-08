<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add salary page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/css/style.css" rel="stylesheet"/>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>
<body>
<br>
<section class="container">
<div class="login">
<h1> Add salary </h1>

<form id="data" method = "post" action="/web/mvc/views/addSalaryForTrainer">
        <p><h1> Выбирите тренера и введите значение зарплаты для добавления </h1>

         <c:forEach var = "entry" items="${salmap}">
         <c:out value = "${entry.key.name}" />
         <input type="radio" name="trainerId" value="${entry.key.id}" >
           <br>
          <c:forEach var = "list" items="${entry.value}">
           <c:out value = "${list.bigDecimalSalary}" /> <br>
            </c:forEach>
            <hr/>
            <hr/>
          </c:forEach>

    <br>

    <p><input type="text" name="sal" placeholder="Enter salary value" ></p>
     <p><input type="submit" form="data" value="Отправить"></p>
</form>
<jsp:include page="mainpage.jsp" />
     </div>
    </section>
<br>
 <jsp:include page="logout.jsp" />
</body>
</html>