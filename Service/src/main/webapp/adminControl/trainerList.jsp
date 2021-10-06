<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>It adminLisst</title>
</head>
<body>
<br>
<h1> Trainer control List </h1>

 <form id="data" action="">

      <p>Выберите студентов из группы</p>
      <c:forEach var = "entry" items="${map}">
         <c:out value = "${entry.value.name}" />
         <input type="radio" name="student" value="${entry.key.id}"> </p>
      </c:forEach>
      <br>
      <p>Выберите необходимое действие</p>

       <select name="act", form="data">
        <option value="create">ADD MARK</option>
        <option value="delete">Delete MARK </option>
        <option value="change">Change MARK</option>
        <option value="watch">Watch  MARK</option>
        </select>

     <p><input type="submit" value="Отправить"></p>
    </form>

<br>
<form  action="/web/hello">
    <input type="submit" value="Go to LogIN page!">
</form>
</body>
</html>