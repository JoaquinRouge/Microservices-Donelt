const userId = JSON.parse(sessionStorage.getItem("id"));
const username = JSON.parse(sessionStorage.getItem("username"));
const taskSection = document.getElementById("tasks")
const completedTasksSection = document.getElementById("completed-tasks")
const title = document.getElementById("title")

title.innerHTML += " " + username

document.getElementById("bell-icon").addEventListener("click", () => {
  document.getElementById("notification-menu").classList.toggle("hidden");
});

function getNotifications() {
  fetch(`http://localhost:444/notification-service/api/notification/user/id/${userId}`, {
          method: 'GET',
          headers: {
          'Authorization': 'Bearer ' + JSON.parse(sessionStorage.getItem("token")),
          'Content-Type': 'application/json'
      }
    })
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
  fetch(`http://localhost:444/task-service/api/task/user/id/${userId}`, {
    method: 'GET',
    headers: {
          'Authorization': 'Bearer ' + JSON.parse(sessionStorage.getItem("token")),
          'Content-Type': 'application/json'
    }
    })
    .then(response => {
      if (!response.ok) {
        throw new Error("Error obtaining the tasks");
      }
      return response.json();
    })
    .then(tasks => {
      taskSection.innerHTML = ""
      completedTasksSection.innerHTML = ""
      tasks.forEach(task => {

        if (task.completed == false) {
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
                <button class="btn taskBtn" id="${task.id}"><i class="fa-solid fa-check"></i></button>
                <button class="btn deleteBtn" id="${task.id}"><i class="fa-solid fa-trash"></i></button>
                <button 
                  class="btn editBtn" 
                  id="${task.id}" 
                  data-title="${encodeURIComponent(task.title)}" 
                  data-description="${encodeURIComponent(task.description)}" 
                  data-expiration="${task.expirationDate}">
                  <i class="fa-solid fa-pencil"></i>
                </button>

                </div>
              `
          
        } else {
          completedTasksSection.innerHTML += 
            
          `
          <div class="task">
          <div class="task-info">
              <p class="title"><s>${task.title}</s></p>
              <p class="description">
                  ${task.description}
              </p>
              <p class="expiration">Expires ${task.expirationDate}</p>
          </div>
          <button class="btn deleteBtn" id="${task.id}"><i class="fa-solid fa-trash"></i></button>
          </div>
        `
          
        }
      });
      const buttons = document.getElementsByClassName("taskBtn");
      const deleteButtons = document.getElementsByClassName("deleteBtn")
      const editButtons = document.getElementsByClassName("editBtn")

      for (let i = 0; i < buttons.length; i++) {
        buttons[i].addEventListener("click", function () {
          if (confirm("This action will mark the task as completed, ¿continue?")) {
            completeTask(this.id)
          }
        });
      }

      for (let i = 0; i < deleteButtons.length; i++) {
        deleteButtons[i].addEventListener("click", function () {
          if (confirm("This action will delete the task, ¿continue?")) {
            deleteTask(this.id)
          }
        });
      }

      for (let i = 0; i < editButtons.length; i++) {
        editButtons[i].addEventListener("click", function () {
          const title = this.dataset.title;
          const description = this.dataset.description;
          const expirationDate = this.dataset.expiration;
      
          window.location.href =
            `editTask.html?title=${title}&description=${description}&expirationDate=${expirationDate}&id=${this.id}`;
        });
      }
      
    })
    .catch(error => {
      console.error("Error:", error.message);
    });
}

function deleteTask(id) {
  fetch(`http://localhost:444/task-service/api/task/delete/${id}`, {
    method: 'DELETE',
    headers: {
          'Authorization': 'Bearer ' + JSON.parse(sessionStorage.getItem("token")),
          'Content-Type': 'application/json'
    }
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
    method: 'DELETE',
    headers: {
          'Authorization': 'Bearer ' + JSON.parse(sessionStorage.getItem("token")),
          'Content-Type': 'application/json'
    }
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

function completeTask(id) {
  fetch(`http://localhost:444/task-service/api/task/complete/${id}`, {
    method: 'PUT',
    headers: {
          'Authorization': 'Bearer ' + JSON.parse(sessionStorage.getItem("token")),
          'Content-Type': 'application/json'
    }
  })
    .then(response => {
      if (!response.ok) {
        throw new Error("Error during fetch");
      }
      console.log(`Task with id ${id} marked as completed`);

      // Opcional: volver a cargar las tareas si querés refrescar la lista
      getTasks();
    })
    .catch(error => {
      console.error("Error:", error.message);
    });
}

getNotifications()
getTasks()