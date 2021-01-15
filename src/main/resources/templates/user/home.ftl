<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Gmail</title>
</head>
<body>
<h3>Welcome, to Gmail!</h3>
<#if user??>
    <h3>Hello, ${user.username}@gmail.com</h3>
    <a href="/logout">Log out!</a>
<#else>
    <b>You're not logged in!!</b>
    <a href="/login">Log in!</a>
</#if>



</body>
</html>