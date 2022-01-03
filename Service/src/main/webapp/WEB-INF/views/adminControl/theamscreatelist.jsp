<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Group create Page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="static/css/style.css" rel="stylesheet">
</head>


<body>
<section class="container">
<div class="login">
<h1> ADD NEW Group </h1>
  <form id="data" method = "post"  action="GroupCreaterServlet">
   <p>Выберите темы для группы</p>
      <c:forEach var = "th" items="${mapITe}">
          <c:out value = "${th.value.theamName}" />
          <input type="checkbox" name="th" value="${th.key}"> <br>
       </c:forEach>

    <p>Выберите студентов для группы</p>
    <c:forEach var = "entry" items="${mapIS}">
       <c:out value = "${entry.value.name}" />
       <input type="checkbox" name="user" value="${entry.key}"> </p>
    </c:forEach>

    <p>Выберите тренера для группы</p>
            <c:forEach var = "entry1" items="${mapITr}">
            <c:out value = "${entry1.value.name}" />
            <input type="radio" name="trainer" value="${entry1.key}"> </p>
             </c:forEach>


   <p><input type="submit" value="Отправить"></p>
  </form>
  <form  action="hello">
      <input type="submit" value="Go to Main Admin!">
  </form>
     </div>
    </section>
<br>
<br>
 <jsp:include page="logout.jsp" />

</body>
</html>