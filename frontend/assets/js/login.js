document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("http://localhost:444/user-service/user/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ 
                email: email, 
                password: password
            }),
        });

        if (!response.ok) {
            const errorMessage = await response.text(); // Captura el mensaje de error
            throw new Error(errorMessage);
        }

        const userData = await response.json();

        if (!userData) {
            throw new Error("Respuesta del servidor inválida");
        }

        console.log(userData)

        sessionStorage.setItem("user", JSON.stringify(userData));

         window.location.href ="donelt.html"

    } catch (error) {
        alert(error.message);
    }
});