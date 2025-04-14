document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const user = JSON.parse(sessionStorage.getItem("user"));

    if (!user || !user.id) {
      alert("No user found in sessionStorage");
      return;
    }

    const task = {
      title: document.getElementById("text").value,
      description: document.getElementById("description").value,
      expirationDate: document.getElementById("expirationDate").value,
      creationDate: new Date().toISOString().split("T")[0],
      completed: false,
      userId: user.id
    };

    try {
      const response = await fetch("http://localhost:444/task-service/api/task/create", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
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
