# 📋 Donelt – Sistema de Gestión de Tareas
![Java](https://img.shields.io/badge/Java-21-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen?logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-3.0-brightgreen?logo=springsecurity)
![Docker](https://img.shields.io/badge/Docker-19.03%2B-brightblue?logo=docker)

**Donelt** es un software desarrollado con arquitectura de microservicios que permite a los usuarios gestionar sus tareas de manera eficiente. Con esta plataforma podrás crear, editar, eliminar y completar tareas, además de recibir notificaciones automáticas cuando se aproximen las fechas de vencimiento.

---
## 🖼️ Imagenes del proyecto

<img src="images/login.jpg" width="700"/>
<img src="images/donelt.jpg" width="700"/>
<img src="images/updateTask.jpg" width="700"/>

## ⚙️ Tecnologías Utilizadas

### 🧠 Backend
- Java 21
- Spring Boot
- Spring Cloud (Eureka, Feign Client)
- Spring Security
- JWT
- Feign Client (Comunicación entre microservicios)
- Loadbalancer
- Spring Data JPA + Hibernate
- MySQL
- Resilience4j (Circuit Breaker)
- Spring Scheduler

### 🎨 Frontend
- HTML5
- CSS3
- JavaScript (Vanilla)

### 🔧 Infraestructura
- Eureka Server (Service Discovery)
- API Gateway (enrutamiento)
- Microservicios separados por responsabilidad
  
### ✅ Funcionalidades
- Registro y login de usuarios con JSON Web Token

- Securizacion de los endpoints mendiante PreAuthorize

- Crear, editar y eliminar tareas

- Marcar tareas como completadas

- Notificaciones automáticas (programadas)

- Sistema tolerante a fallos con Circuit Breaker

- Interfaz clara, intuitiva y funcional
---
## 📅 Notificaciones Automáticas
El sistema utiliza @Scheduled para verificar diariamente (a las 9:00, 10:00 y 11:00) si existen tareas que vencen en 3 días o menos, y genera una notificación si aún no fue enviada ese día.

Esto está implementado con:

- @Scheduled

- @CircuitBreaker (para manejar fallos del microservicio de notificaciones)

- Campo lastNotified en la entidad Task para evitar duplicados.

## 🚀 Cómo Ejecutar el Proyecto
### Clonar el repositorio:

git clone https://github.com/JoaquinRouge/Microservices-Donelt.git

### Configurar MySQL:

- Crear una base de datos llamada donelt

- Actualizar las credenciales de conexión en los application.properties de cada microservicio (Por defecto el usuario es root y no tiene contraseña).

### Ejecutar con Docker 🐳
ℹ️ Nota: Subí el archivo .env al repositorio para facilitar la ejecución del proyecto. Sé que esto no es una buena práctica en producción, pero en este caso el objetivo es facilitar la ejecucion del proyecto.

✅ Requisitos
Docker Desktop

### 📦 Pasos para levantar el entorno
Levantar los contenedores:
En la terminal (carpeta raiz) ejecuta:

 - docker-compose build (crea las imagenes)
 - docker-compose up (levanta el contenedor)

Esto creará y levantará automáticamente:
- Eureka Server
- API Gateway
- Task Service
- Notification Service
- User service

### Iniciar la interfaz

 - Inicia un Live Server de index.html (carpeta frontend)

## 📌 Mejoras Futuras
- Implementar sistema de prioridad para tareas

- Agregar filtro por estado y fecha

- Dashboard de productividad del usuario

- Envío de notificaciones por email

## 👨‍💻 Autor
Joaquín Rougé Núñez
