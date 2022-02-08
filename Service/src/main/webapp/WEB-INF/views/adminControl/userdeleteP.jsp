<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <meta charset="UTF-8">
    <title>Delete theam  page</title>
<link href="/css/style.css" rel="stylesheet"/>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>
<body>
<br> <br/>
<section class="container">
<div class="login">

<form id="deleteentity" method = "post"  action="/web/mvc/views/dodeleteentity">
<p> Выбирите пользователя для удаления</p>
<br>

 <c:forEach var = "entry" items="${map}">
    <div>
   <p> ID:    <c:out value = "${entry.key}" />
   <input type="checkbox"  name="entityId"  value="${entry.key}"> <br>  </p>
   <p> Name:      <c:out value = "${entry.value.name}" /> </p>
   <p> Login:      <c:out value = "${entry.value.login}" /> </p>
    <p> Age:     <c:out value = "${entry.value.age}" /> </p>
    <br>
    <hr>
    <hr>
  </c:forEach>

    <input type="hidden" name="entity"  value="${entity}">
      <p><input type="submit" form="deleteentity" value="Delete"></p>

</form>
</div>
<hr>

<br>
 <jsp:include page="mainpage.jsp" />
   </div>
  </section>
 <jsp:include page="logout.jsp" />
</body>
</html>