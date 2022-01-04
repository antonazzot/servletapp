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
<h1> Update Group </h1>
   <p>Внесите данные для изменения</p>
<form id="data" method = "post" action="/web/mvc/views/resultchangegroup">
    <p>Студенты группы, выберите студента для удаления из группы </p>
      <c:forEach var = "gs" items="${groupForChange.studentMap}">
             <c:out value = "${gs.value.name}" />
             <input type="checkbox" name="grstid" value="${gs.key}" ><br>
      </c:forEach>
      <input name="id" type="hidden"  value = ${id} /> <br>
      <input name="act" type="hidden"  value = "studentdelete" /> <br>
      <input type="submit" form="data" value="Удалить студента">
      <hr>
</form>

<form id="data1" method = "post"  action="/web/mvc/views/resultchangegroup">
      <p>Выберите студента для добавления в группу </p>
           <c:forEach var = "as" items="${freestudent}">
                      <c:out value = "${as.value.name}" />
                      <input type="checkbox" name="astid" value="${as.key}" ><br>
           </c:forEach>
           <input name="id" type="hidden"  value = ${id} /> <br>
           <input name="act" type="hidden"  value = "studentadd" /> <br>
           <input type="submit" form="data1" value="Добавить студента">
      <hr>
</form>

<form id="data2" method = "post"  action="/web/mvc/views/resultchangegroup">
       <input name="id" type="hidden"  value = ${id} />
        <input name="act" type="hidden"  value = "theamdelete" /> <br>
        <p>Темы группы, выберите тему для удаления из группы </p>
             <c:forEach var = "gth" items="${groupForChange.theamsSet}">
                   <c:out value = "${gth.theamName}" />
                   <input type="checkbox" name="grth" value="${gth.id}"> <br>
             </c:forEach>
             <input type="submit" form="data2" value="Удалить тему">
      <hr>
</form>

<form id="data3" method = "post"  action="/web/mvc/views/resultchangegroup">
          <p>Все доступные темы, выберите тему для добавления в группу </p>
               <c:forEach var = "fth" items="${freetheam}">
                          <c:out value = "${fth.value.theamName}" />
                          <input type="checkbox" name="frth" value="${fth.key}" > <br>
                </c:forEach>
                <input name="id" type="hidden"  value = ${id} />
                <input name="act" type="hidden"  value = "theamadd" /> <br>
                <input type="submit" form="data3" value="Добавить тему">
    <hr>
</form>

 <form id="data4" method = "post"  action="/web/mvc/views/resultchangegroup">
                 <p> выберите тренера для добавления тренера, либо замены существующего </p>
                 <c:forEach var = "ftr" items="${freetrainer}">
                 <c:out value = "${ftr.value.name}" />
                 <input type="radio" name="frtr" value="${ftr.key}" >
           </c:forEach>
        <input name="id" type="hidden"  value = ${id} />
        <input name="act" type="hidden"  value = "trainer" /> <br>
        <br>
        <p><input type="submit" form="data4" value="Заменить">
        <hr>
</form>
        <br>
   </div>
  </section>

  <form  action="hello">
      <input type="submit" value="Go to Main Admin!">
  </form>

<br>
 <jsp:include page="logout.jsp" />
</body>
</html>