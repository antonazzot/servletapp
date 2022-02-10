<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Group create Page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/css/style.css" rel="stylesheet"/>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>

<body>
<section class="container">
<div class="login">
<h1> Update User </h1>
  <form id="data" method = "post"  action="/web/mvc/views/resultchangeuser">
   <p>Внесите данные для изменения</p>
    <input name="id" type="hidden"  value = "${userForChange.id}" /> <br>

        <select name="role", form="data">
            <option value="student">Student</option>
            <option value="trainer">Trainer</option>
            <option value="administrator">Administrator</option>
         </select> <br>
    Enter name: <br>
    <input name="name" type="text" placeholder="${userForChange.name}"  />
    Enter login: <br>
    <input name="login" type="text" placeholder="${userForChange.login}"  />
    Enter password: <br>
    <input name="password" type="text" placeholder="password"  />
    Enter email: <br>
    <input name="email" type="text" placeholder="email"  />
    Enter age: <br>
    <input name="age" type="number" placeholder="${userForChange.age}"  />


   <p><input type="submit" form="data" value="Отправить"></p>
  </form>
  <br>
  <jsp:include page="mainpage.jsp" />
 </div>
</section>


<br>
 <jsp:include page="logout.jsp" />
<br>

</body>
</html>