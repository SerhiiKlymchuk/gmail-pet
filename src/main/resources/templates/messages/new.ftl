<!doctype html>
<html lang="en">
<head>
    <#include "../include/meta.ftl">
    <link rel="stylesheet" href="/css/messages.css">
    <title>New</title>
</head>
<body>
    <div class="blur_wrapper">
        <#include "../include/header.ftl">
        <#include "../include/sidebar.ftl">

        <div class="messages_box">

        </div>
    </div>

    <#if error??>
        <div id="error">
            <p>ALERT!!! ${error}</p>
        </div>
    </#if>

    <form id="new_message_form" method="POST">
        <div class="form_header">
            <p>New Message</p>
            <a href="/messages/inbox">&#9747;</a>
        </div>

        <div class="form_body">
            <input type="text" name="receiverUsername" id="form_send_to" placeholder="To">
            <input type="text" name="subject" placeholder="Subject" id="form_subject">
            <textarea name="content" id="form_content" placeholder="Some text...." ></textarea>
        </div>

        <div class="form_footer">
            <input id="submit_btn" type="submit" value="Send">
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/><br>
    </form>

</body>
</html>