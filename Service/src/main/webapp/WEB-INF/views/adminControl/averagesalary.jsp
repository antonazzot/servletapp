<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Average salary choose page</title>
<link href="/css/style.css" rel="stylesheet"/>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>
<body>
<br>
<section class="container">
<div class="login">
<h1> Add salary list </h1>

<form id="data" action="/web/mvc/views/avarageCalc">
 <h1><p>Выбирите тренера и введите колличество месяцев
 для расчета средней зарплаты </p></h1>

         <c:forEach var = "entry" items="${allTrainer}">
         <c:out value = "${entry.key.name}" />  <input type="radio" name="trId" value="${entry.key.id}" >
           <br>
          <c:forEach var = "list" items="${entry.value}">
           <c:out value = "${list.bigDecimalSalary}" /> <br>
            </c:forEach>
            </hr>
          </c:forEach>

    <br>
    <p><input type="text" name="period" placeholder="Enter calculate period"></p>
     <p><input type="submit" form="data" value="Рассчитать"></p>
</form>
<jsp:include page="mainpage.jsp" />
     </div>
    </section>
<br>

<br>
 <jsp:include page="logout.jsp" />
</body>
</html>