document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("registerForm");

    form.addEventListener("submit", async (event) => {
        event.preventDefault(); // Evita que la página se recargue

        // Capturar los valores del formulario
        const email = document.getElementById("email").value;
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        // Construir el objeto a enviar
        const userData = {
            email: email,
            username: username,
            password: password
        };

        try {
            const response = await fetch("http://localhost:444/user-service/user/create", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(userData)
            });

            if (response.ok) {
                alert("Cuenta creada exitosamente");
                window.location.href = "index.html"; // Redirige al login
            } else {
                const errorMessage = await response.text();
                alert("Error al registrarse: " + errorMessage);
            }
        } catch (error) {
            alert("Error en la conexión con el servidor");
            console.error(error);
        }
    });
});