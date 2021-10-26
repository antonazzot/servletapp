<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <meta charset="UTF-8">
    <title>Demonstrate group  page</title>
</head>
<body>

 <c:forEach var = "entry" items="${map}">
       <c:out value = "${entry.key}" />
          <c:forEach var = "map1" items="${entry.value}"> <br>
                <p> Темы группы </p>
                         <c:forEach var = "theam" items="${map1.key}">
                                  <c:out value = "${theam}" /> <br>
                          </c:forEach><br>
                 <p> Студенты группы </p>
                          <c:forEach var = "student" items="${map1.value}">
                                  <c:out value = "${student.name}" /> <br>
                          </c:forEach>

           </c:forEach> <br>
       <c:out value = "${entry.value}" /><p>
  </c:forEach>

<br>
  <form  action="hello">
      <input type="submit" value="Go to Main Page!">
  </form>
<br>
<form  action="logout">
    <input type="submit" value="LogOut">
</form>
</body>
</html>