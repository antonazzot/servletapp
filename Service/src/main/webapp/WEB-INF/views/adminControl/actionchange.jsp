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
<h1> Update User </h1>
  <form id="data" method = "post"  action="changeUserServlet">
   <p>Внесите данные для изменения</p>
    <input name="id" type="hidden"  value = "${id}" /> <br>
        <select name="role", form="data">
            <option value="student">Student</option>
            <option value="trainer">Trainer</option>
            <option value="administrator">Administrator</option>
           </select> <br>
    <input name="login" type="text" placeholder="${login}"  />
    <input name="pass" type="text" placeholder="${pass}"  />
    <input name="name" type="text" placeholder="${name}"  />
    <input name="age" type="number" placeholder="${age}"  />

   <p><input type="submit" form="data" value="Отправить"></p>
  </form>
  <br>
 </div>
</section>

  <form  action="hello">
      <input type="submit" value="Go to Main Admin!">
  </form>

<br>
 <jsp:include page="logout.jsp" />
<br>

</body>
</html>