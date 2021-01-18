<!doctype html>
<html lang="en">
<head>
   <#include "../include/meta.ftl">
    <title>Gmail</title>
</head>
<body>
<h3>Welcome, to Gmail!</h3>

<#--This ftl is unnecessary for this PR-->
<#--Only for demonstration purpposes-->

    <form action="/logout" method="POST">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/><br>
        <button type="submit">Logout</button>
    </form>



</body>
</html>