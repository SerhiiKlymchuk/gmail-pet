<div class="message_header">
    <#assign searchQuery = searchQuery ! "">

    <div class="message_header__checkbox">
        <span class="box">&#9744;</span>
        <span class="refresh">&#8635;</span>
    </div>

    <div class="message_header__nav">
        <p class="message_header__nav__stats">
            ${messages.number*10} -
            ${messages.number*10 + messages.numberOfElements} of
            ${messages.totalElements}
        </p>

        <form action="/messages/${sendUrl}">
            <input type="submit"
                   id="${messages.hasPrevious() ? then("", "disabled")}"
                   value="&#8249;">

            <input type="hidden" name="searchQuery" value="${searchQuery}">
            <input type="hidden" name="page" value="${messages.number-1}">
        </form>

        <form action="/messages/${sendUrl}">
            <input type="submit"
                   id="${messages.hasNext() ? then("", "disabled")}"
                   value="&#8250;">

            <input type="hidden" name="searchQuery" value="${searchQuery}">
            <input type="hidden" name="page" value="${messages.number+1}">
        </form>
    </div>

</div>

<#if messageSuccess??>
    <li id="success">
        <p>${messageSuccess}</p>
    </li>
</#if>