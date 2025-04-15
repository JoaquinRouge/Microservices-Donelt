const params = new URLSearchParams(window.location.search);

const title = decodeURIComponent(params.get("title"));
const description = decodeURIComponent(params.get("description"));
const expirationDate = params.get("expirationDate");
const taskId = params.get("id");

// Rellenar los inputs con los valores actuales
document.getElementById("text").value = title;
document.getElementById("description").value = description;
document.getElementById("expirationDate").value = expirationDate;

// Enviar el formulario
document.getElementById("loginForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const updatedTask = {
    id: taskId,
    title: document.getElementById("text").value,
    description: document.getElementById("description").value,
    expirationDate: document.getElementById("expirationDate").value
  };

  fetch("http://localhost:444/task-service/api/task/update", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(updatedTask)
  })
    .then(response => {
      if (!response.ok) {
        throw new Error("Error updating the task");
      }
      alert("Task updated successfully!");
      window.location.href = "donelt.html"; 
    })
    .catch(error => {
      console.error("Update error:", error.message);
      alert("Something went wrong while updating the task.");
    });
});

