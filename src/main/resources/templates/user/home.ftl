<!doctype html>
<html lang="en">
<head>
   <#include "../include/meta.ftl">
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