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
 <c:forEach var = "entry" items="${map}">
 <div>

       <p> Название группы: </p>
       <c:out value = "${entry.value.name}" />
       <p> Id группы: </p>
       <c:out value = "${entry.key}" />
       <p> Тренер группы: </p>
       <c:out value = "${entry.value.trainer.name}" />

                <p> Темы группы: </p>
                         <c:forEach var = "theamset" items="${entry.value.theamsSet}">
                                  <c:out value = "${theamset.theamName}" /> <br>
                          </c:forEach><br>

                <p> Студенты группы: </p>
                          <c:forEach var = "studentmap" items="${entry.value.studentMap}">
                                  <c:out value = "${studentmap.value.name}" /> <br>
                          </c:forEach>

        <form id="deleteentity" method = "get"  action="/web/mvc/views/dodeleteentity">
        <input type="hidden" name="entityId" value="${entry.value.id}">
        <input type="hidden" name="entity"  value="${entity}">
        <p><input type="submit" form="deleteentity" value="Delete this"></p>
        </form>
                           <hr/>
                          <hr/>
   </div>
   <hr>
  </c:forEach>

  <jsp:include page="mainpage.jsp" />
     </div>
    </section>

<br>
<br>
 <jsp:include page="logout.jsp" />
</body>
</html>