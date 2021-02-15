<!doctype html>
<html lang="en">
<head>
    <#include "../include/meta.ftl">
    <link rel="stylesheet" href="/css/messages.css">
    <script defer src="/js/delete_message.js"></script>
    <title>Inbox</title>
</head>
<body id="inbox">
    <#include "../include/header.ftl">
    <#include "../include/sidebar.ftl">

    <div class="messages_box">
        <#include "../include/message_header.ftl">

        <ul>
            <#list inboxMessages.content as msg>
                <li class="message_item">
                    <div class="message_item__marks">
                        <span class="box">&#9744;</span>
                        <span class="star">&#9734;</span>
                        <p class="sender">${msg.senderUsername}</p>
                    </div>

                    <p class="subject">${msg.subject}</p>

                    <p class="content">
                        ${(msg.content?length > 50) ? then(msg.content?substring(0, 50)+'...', msg.content)}
                    </p>

                    <p class="date">
                        ${msg.date.hour}:
                        ${(msg.date.minute?string?length<2) ? then('0'+msg.date.minute, msg.date.minute+'')}
                    </p>

                    <form id="form_delete" action="/messages/delete/${msg.id}" method="POST">
                        <i class="fas fa-trash" title="Are you sure?"></i>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/><br>
                    </form>
                </li>
            </#list>
        </ul>
    </div>

</body>
</html>