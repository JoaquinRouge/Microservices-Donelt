document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("http://localhost:444/user-service/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ 
                username: username, 
                password: password
            }),
        });

        if (!response.ok) {
            const errorMessage = await response.text(); // Captura el mensaje de error
            throw new Error(errorMessage);
        }

        const data = await response.json();

        if (!data) {
            throw new Error("Respuesta del servidor inválida");
        }

        console.log(data.jwt)

        sessionStorage.setItem("token", JSON.stringify(data.jwt));
        sessionStorage.setItem("username", JSON.stringify(data.username));
        sessionStorage.setItem("id", JSON.stringify(data.id));


        window.location.href ="/donelt.html"

    } catch (error) {
        alert(error.message);
    }
});