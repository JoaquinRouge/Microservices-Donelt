const token = sessionStorage.getItem("token");

if (!token) {
  window.location.href = "/index.html";
}

document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const task = {
      title: document.getElementById("text").value,
      description: document.getElementById("description").value,
      expirationDate: document.getElementById("expirationDate").value,
      creationDate: new Date().toISOString().split("T")[0],
      lastNotified:null,
      completed: false,
      userId: JSON.parse(sessionStorage.getItem("id"))
    };

    try {
      const response = await fetch("http://localhost:444/task-service/api/task/create", {
            method: 'POST',
          headers: {
          'Authorization': 'Bearer ' + JSON.parse(sessionStorage.getItem("token")),
          'Content-Type': 'application/json'
      },
        body: JSON.stringify(task)
      });

      if (!response.ok) {
        const errorMessage = await response.text();
        throw new Error(errorMessage);
      }

      alert("Task created successfully!");
      window.location.href = "./donelt.html";

    } catch (error) {
      console.error("Error:", error.message);
      alert("Error creating task: " + error.message);
    }
  });
