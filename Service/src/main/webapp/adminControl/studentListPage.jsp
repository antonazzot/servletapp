<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>It adminList</title>
</head>
<body>
<br>
<h1> admin list </h1>

<form id="data" action="watchServlet">
   <select name="user", form="data">
            <option value="Student">Student</option>
            <option value="Trainer">Trainer</option>
            <option value="Administrator">Administrator</option>
            <option value="Group">Group</option>
    </select>
    <br>
    <br>
    <br>
    <br>
    <select name="act", form="data1">
        <option value="create">ADD </option>
        <option value="delete">Delete </option>
        <option value="change">Change </option>
        <option value="watch">Watch </option>
    </select>
    <br>
    <br>

    <br>
    <br>
    Enter  ID: <input name ="id", type="text">
     <input type="submit" form="data" form="data1">
</form>
<br>
  <form  action="adminControl/adminActList.jsp">
      <input type="submit" value="Go to Main Admin!">
  </form>
<br>
<form  action="logout">
    <input type="submit" value="Go to LogIN page!">
</form>
</body>
</html>