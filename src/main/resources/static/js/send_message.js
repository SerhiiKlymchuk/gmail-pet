const form = document.querySelector("#new_message_form");
const receiversBox = document.querySelector("#receivers-box");

form.addEventListener("keydown", e => {

    if (e.code === "Enter") {
        e.preventDefault();
    }

})

receiversBox.addEventListener("keydown", e => {

    if (e.code === "Enter") {
        let username = receiversBox
            .outerText
            .trim();

        if (username !== "") {
            let input = `<input type="text" name="receiverUsername" class="form_send_to" value="${username}">`;

            receiversBox.removeChild(receiversBox.lastChild);
            receiversBox.insertAdjacentHTML("beforeend", input);

            placeCaretAtEnd(receiversBox);
        }
    }

})

function placeCaretAtEnd(box) {
    box.focus();

    let range = document.createRange();
    let sel = window.getSelection();

    range.selectNodeContents(box);
    range.collapse(false);

    sel.removeAllRanges();
    sel.addRange(range);
}

