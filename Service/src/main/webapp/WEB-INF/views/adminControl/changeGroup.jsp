<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Change group</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <link href="/css/style.css" rel="stylesheet"/>
   <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>
<body>
<section class="container">
<div class="login">
<h1> Change group </h1>
 <form id="data" method = "post"  action="/web/mvc/views/groupforchange">
         <c:forEach var = "entry" items="${map}">
         <c:out value = "${entry.value.name}" />
         <input type="radio" name="groupid" value="${entry.key}" > <br>
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