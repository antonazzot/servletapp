<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Theam change page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/css/style.css" rel="stylesheet"/>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>


<body>
<section class="container">
<div class="login">
<h1> Update theam </h1>
    <form id="data" method = "post"  action="/web/mvc/views/changetheam">
    <p>Темы группы, выберите тему для внесения изменений  </p>
      <c:forEach var = "entry" items="${map}">
             <c:out value = "${entry.value.theamName}" />
             <input type="radio" name="theamid" value="${entry.key}" ><br>
      </c:forEach>
      <input  name="newName", type="text">

      <input type="submit" form="data" value="Изменить тему">
      <hr>
    </form>
    <jsp:include page="mainpage.jsp" />
     </div>
    </section>

<br>
 <jsp:include page="logout.jsp" />

</body>
</html>