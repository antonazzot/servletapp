<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <meta charset="UTF-8">
    <title>Demonstrate theam  page</title>
    <link href="static/css/style.css" rel="stylesheet">
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