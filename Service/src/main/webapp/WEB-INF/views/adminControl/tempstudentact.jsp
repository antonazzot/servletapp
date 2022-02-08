<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import="java.util.*" %>

<head>
    <meta charset="UTF-8">
<link href="/css/style.css" rel="stylesheet"/>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    <title>Demo page</title>
</head>
<body>
<br> <br/>
<section class="container">
<div class="login">

<form id="doact" method = "post"  action="/web/mvc/views/doactwithtempstudent">

  <select name="act", form="doact">
        <option value="add">ADD </option>
        <option value="delete">Delete </option>
    </select>

 <c:forEach var = "entry" items="${list}">
    <div>
   <p> ID:    <c:out value = "${entry.id}" />
   <input type="checkbox"  name="entityId"  value="${entry.id}"> <br>  </p>
   <p> Name:      <c:out value = "${entry.name}" /> </p>
   <p> Login:      <c:out value = "${entry.login}" /> </p>
    <p> Age:     <c:out value = "${entry.age}" /> </p>
    <br>
    <hr>
    <hr>
  </c:forEach>

      <p><input type="submit" form="doact" value="Act"></p>

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