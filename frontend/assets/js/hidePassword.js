const passwordDiv = document.getElementById("passwordDiv")
const password = document.getElementById("password")

hidden = true

function setIcon() {
    passwordDiv.innerHTML = ""
    passwordDiv.innerHTML += hidden
        ? `<i class="fa-solid fa-eye" id="hide"></i>`
        : `<i class="fa-solid fa-eye-slash" id="hide"></i>`
    
    document.getElementById("hide").addEventListener("click", function () {
        hidden = !hidden
        password.type = password.type === "password" ? "text" : "password";
        setIcon()
    })
}

setIcon()

