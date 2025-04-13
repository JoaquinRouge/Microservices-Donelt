const userId = JSON.parse(sessionStorage.getItem("user")).id;
const taskSection = document.getElementById("tasks")

function getNotifications() {
    fetch(`http://localhost:444/notification-service/api/notification/user/id/${userId}`)
      .then(response => {
        if (!response.ok) {
          throw new Error("Error al obtener las notificaciones");
        }
        return response.json();
      })
      .then(notifications => {
        const list = document.getElementById("notification-list");
        const count = document.getElementById("notifications-count");

        list.innerHTML = "";
        count.innerHTML = 0;

        notifications.forEach(notification => {

            // Agrego una notificación a la lista flotante
            const li = document.createElement("li");
            li.textContent = notification.message;
            list.appendChild(li);

            // Incremento el contador visual
            count.innerHTML++;
        });
      })
      .catch(error => {
        console.error("Error:", error.message);
      });
}


function getTasks() {
    fetch(`http://localhost:444/task-service/api/task/user/id/${userId}`)
    .then(response => {
      if (!response.ok) {
        throw new Error("Error al obtener las tareas");
      }
      return response.json();
    })
    .then(tasks => {
      tasks.forEach(task => {
          taskSection.innerHTML += 
              
              `
                <div class="task">
                <div class="task-info">
                    <p class="title">${task.title}</p>
                    <p class="description">
                        ${task.description}
                    </p>
                    <p class="expiration">Expires ${task.expirationDate}</p>
                </div>
                <button class="btn">Complete</button>
                </div>
              `
      });
  
    })
    .catch(error => {
      console.error("Error:", error.message);
    });
}

document.getElementById("bell-icon").addEventListener("click", () => {
    document.getElementById("notification-menu").classList.toggle("hidden");
  });
  
getNotifications()
getTasks()