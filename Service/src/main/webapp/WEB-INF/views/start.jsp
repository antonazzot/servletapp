<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
        <link href="/resources/css/style.css" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>

<body>
  <section class="container">
    <div class="login">
      <h1>Login to Web App</h1>
      <form id="data" method = "post"  action="/web/mvc/checkUser">
        <p><input type="text" name="id" value="" placeholder="Username or Email"></p>
        <p><input type="password" name="password" value="" placeholder="Password"></p>
        <p class="remember_me">
          <label>
            <input type="checkbox" name="remember_me" id="remember_me">
            Remember me on this computer
          </label>
        </p>
        <p class="submit"><input type="submit" name="commit" value="Login"></p>
      </form>
    </div>

    <div class="login-help">
      <p>Forgot your password? <a href="index.html">Click here to reset it</a>.</p>
    </div>
  </section>

</body>
</html>
