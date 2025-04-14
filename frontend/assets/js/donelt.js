const userId = JSON.parse(sessionStorage.getItem("user")).id;
const taskSection = document.getElementById("tasks")

document.getElementById("bell-icon").addEventListener("click", () => {
  document.getElementById("notification-menu").classList.toggle("hidden");
});

function getNotifications() {
    fetch(`http://localhost:444/notification-service/api/notification/user/id/${userId}`)
      .then(response => {
        if (!response.ok) {
          throw new Error("Error obtaining the notifications");
        }
        return response.json();
      })
      .then(notifications => {
        const list = document.getElementById("notification-list");
        const count = document.getElementById("notifications-count");

        list.innerHTML = "";
        count.innerHTML = 0;

        notifications.forEach(notification => {

            // Agrego una notificacion a la lista flotante
          let li =
            `
              <div class="notification">
                <li>${notification.message}</li>
                <i class="fa-solid fa-trash notiDeleteBtn" id="${notification.id}"></i>
              </div>
            `  
          ;
            list.innerHTML += li;

            // Incremento el contador visual
            count.innerHTML++;
        });

        const notis = document.getElementsByClassName("notiDeleteBtn");

        for (let i = 0; i < notis.length; i++) {
          notis[i].addEventListener("click", function () {
            if (confirm("This action will delete the notification, ¿continue?")) {
              deleteNotification(this.id)
            }
          });
        }

      })
      .catch(error => {
        console.error("Error:", error.message);
      });
}


function getTasks() {
    fetch(`http://localhost:444/task-service/api/task/user/id/${userId}`)
    .then(response => {
      if (!response.ok) {
        throw new Error("Error obtaining the tasks");
      }
      return response.json();
    })
    .then(tasks => {
      taskSection.innerHTML = ""
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
                <button class="btn deleteBtn" id="${task.id}">Complete</button>
                </div>
              `
      });
      const buttons = document.getElementsByClassName("deleteBtn");

      for (let i = 0; i < buttons.length; i++) {
        buttons[i].addEventListener("click", function () {
          if (confirm("This action will delete the task, ¿continue?")) {
            deleteTask(this.id)
          }
        });
      }
    })
    .catch(error => {
      console.error("Error:", error.message);
    });
}

function deleteTask(id) {
  fetch(`http://localhost:444/task-service/api/task/delete/${id}`, {
    method: "DELETE",
  })
    .then(response => {
      if (!response.ok) {
        throw new Error("Error al eliminar la tarea");
      }
      console.log(`Tarea con ID ${id} eliminada correctamente`);

      // Opcional: volver a cargar las tareas si querés refrescar la lista
      getTasks();
    })
    .catch(error => {
      console.error("Error:", error.message);
    });
}

function deleteNotification(id) {
  fetch(`http://localhost:444/notification-service/api/notification/delete/${id}`, {
    method: "DELETE",
  })
    .then(response => {
      if (!response.ok) {
        throw new Error("Error al eliminar la tarea");
      }
      console.log(`Tarea con ID ${id} eliminada correctamente`);

      // Opcional: volver a cargar las tareas si querés refrescar la lista
      getNotifications();
    })
    .catch(error => {
      console.error("Error:", error.message);
    });
}

getNotifications()
getTasks()