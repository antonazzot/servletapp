<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>It adminLisst</title>
</head>
<body>
<br>
<h1> admin list </h1>

<form id="data" action="watchServlet">
    <select name="act", form="data">
        <option value="create">ADD Administrator</option>
        <option value="Delete">Delete Administrator</option>
        <option value="Change">Change Administrator</option>
        <option value="Watch">Watch Administrator</option>
    </select>
    <br>
    <br>
    <input  name="user", value="administrator">
     <input type="submit" form="data"/>
</form>
<br>
  <form  action="hello">
      <input type="submit" value="Go to Main Admin!">
  </form>
<br>
<form  action="logout">
    <input type="submit" value="Go to LogIN page!">
</form>
</body>
</html>