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
<h1> Update theam </h1>
   <p>Внесите данные для изменения</p>
    <form id="data" method = "post"  action="updateTheam">
    <p>Темы группы, выберите тему для внесения изменений  </p>
      <c:forEach var = "entry" items="${map}">
             <c:out value = "${entry.value.theamName}" />
             <input type="radio" name="thid" value="${entry.key}" ><br>
      </c:forEach>
      <input  name="thname", type="text">

      <input type="submit" form="data" value="Изменить тему">
      <hr>
    </form>
     </div>
    </section>
  <form  action="hello">
      <input type="submit" value="Go to Main Admin!">
  </form>

<br>
<br>
 <jsp:include page="logout.jsp" />

</body>
</html>