<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Main Page</title>
</head>
<body>
<br> <br/>
<form id="data" action="checkUser">
    <select name="role", form="data">
        <option value="Administrator">Administrator</option>
        <option value="Trainer">Trainer</option>
        <option value="Student">Student</option>
    </select>
    <br>
    <br>
    <br>
    <br>
    Enter id or login: <input name="id", type="text"> <br/>
    Enter password:      <input name="password", type="text">
    <input type="submit" form="data"/>
</form>

</body>
</html>