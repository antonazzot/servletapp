<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <meta charset="UTF-8">
    <title>Demonstrate theam  page</title>
<link href="/css/style.css" rel="stylesheet"/>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>
<body>
<section class="container">
<div class="login">

<c:forEach var = "entry" items="${map}">
     <p> Id темы:
       <c:out value = "${entry.key}" /> <br>
       Название темы:
       <c:out value = "${entry.value.theamName}" /> </p>
           <hr />
  </c:forEach>
<br>

 </div>
  </section>
<br>
 <jsp:include page="mainpage.jsp" />
<br>
 <jsp:include page="logout.jsp" />
</body>
</html>