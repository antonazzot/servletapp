<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Demonstrate group  page</title>
    <link href="static/css/style.css" rel="stylesheet">
</head>
<body>
<section class="demonstrategroupcontainer">
<div class="demonstrategroup">
 <c:forEach var = "entry" items="${map}">
       <p> Название группы: </p>
       <c:out value = "${entry.key.name}" />
       <p> Id группы: </p>
       <c:out value = "${entry.key.id}" />
       <p> Тренер группы: </p>
       <c:out value = "${entry.key.trainer.name}" />
          <c:forEach var = "map1" items="${entry.value}"> <br>
                <p> Темы группы: </p>
                         <c:forEach var = "theam" items="${map1.key}">
                                  <c:out value = "${theam.theamName}" /> <br>
                          </c:forEach><br>
                <p> Студенты группы: </p>
                          <c:forEach var = "student" items="${map1.value}">
                                  <c:out value = "${student.name}" /> <br>
                          </c:forEach>
          </c:forEach> <br> <hr />
  </c:forEach>

<br>
 </div>
 </section>
  <form  action="hello">
      <input type="submit" value="Go to Main Page!">
  </form>
<br>
<br>
 <jsp:include page="logout.jsp" />

</body>
</html>