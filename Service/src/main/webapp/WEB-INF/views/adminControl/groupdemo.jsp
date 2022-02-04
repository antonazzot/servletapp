<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <meta charset="UTF-8">
    <title>Demonstrate group  page</title>
    <link href="/css/style.css" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>
<body>
<section class="container">
<div class="login">

 <form id="data" method = "post"  action="/web/mvc/views/groupforwatch">
         <c:forEach var = "entry" items="${map}">
         <c:out value = "${entry.value.name}" />
         <input type="radio" name="groupid" value="${entry.key}" > <br>
          </c:forEach>
     <p><input type="submit" form="data" value="Отправить"></p>
  </form>

<br>
  <form  action="hello">
      <input type="submit" value="Go to Main Page!">
  </form>
     </div>
    </section>
<br>
<br>
 <jsp:include page="logout.jsp" />
</body>
</html>