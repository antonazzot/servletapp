<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Group create Page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<h1> Update Group </h1>

<body>
  <form id="data"  action="updateActionGroup">
   <p>Внесите данные для изменения</p>
    <input name="id" type="hidden"  value = "${id}" /> <br>

    <p>Студенты группы, выберите студента для удаления из группы </p>
      <c:forEach var = "gs" items="${groupst}">
             <c:out value = "${gs.value.name}" />
             <input type="checkbox" name="grstid" value="${gs.key}" ><br>
      </c:forEach>

      <p>Все студенты , выберите студента для добавления в группу </p>
           <c:forEach var = "as" items="${allst}">
                      <c:out value = "${as.value.name}" />
                      <input type="checkbox" name="astid" value="${as.key}" ><br>
           </c:forEach>

        <p>Темы группы, выберите тему для удаления из группы </p>
             <c:forEach var = "gth" items="${groupth}">
                   <c:out value = "${gth}" />
                   <input type="checkbox" name="grth" value="${gth}"> <br>
             </c:forEach>

          <p>Все доступные темы, выберите тему для добавления в группу </p>
               <c:forEach var = "fth" items="${freeth}">
                          <c:out value = "${fth}" />
                          <input type="checkbox" name="frth" value="${fth}" > <br>
                </c:forEach>

            <p>Тренер группы , выберите тренера для удаления из группы </p>
                <input type="checkbox" name="grtr" value=${grouptr.id} > ${grouptr.name} <input/>

               <p>Свободные тренеры, выберите тренера для добавления тренера, либо замены существующего </p>
                 <c:forEach var = "ftr" items="${freetr}">
                 <c:out value = "${ftr.name}" />
                 <input type="radio" name="frtr" value="${ftr.id}" >
                       </c:forEach>

   <p><input type="submit" form="data" value="Отправить">
  </form>
  <br>

  <form  action="hello">
      <input type="submit" value="Go to Main Admin!">
  </form>
<br>

<form  action="logout">
    <input type="submit" value="LogOut!">
</form>

</body>
</html>