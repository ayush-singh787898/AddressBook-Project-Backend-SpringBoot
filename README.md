📖 Address Book Application
A full-stack application using Angular & Spring Boot with MySQL, Redis, RabbitMQ, Swagger, and Gmail authentication.


🚀 Features
✅ User Authentication (JWT-based)
✅ CRUD Operations (Add, Edit, Delete Contacts)
✅ MySQL Database Integration
✅ Redis Caching for Performance
✅ RabbitMQ for Message Queues
✅ Swagger API Documentation
✅ Gmail App Password Authentication

📌 Tech Stack
Frontend	Backend	Database	Other Services
TypeScript	Java	SQL	RabbitMQ
Bootstrap	Spring Security	JPA/Hibernate	Swagger API Docs
🎨 Frontend Setup (Angular 17)
🔹 Prerequisites
Ensure you have the following installed:

📌 Node.js & npm

📌 Angular CLI

⚡ Installation & Running
git clone https://github.com/ayush-singh787898/AddressBook-Project-Backend-SpringBoot.git
cd client_AddressBook
npm install
ng serve
🌍 Now, open your browser and visit http://localhost:4200 🎉

🛠️ Backend Setup (Spring Boot)
🔹 Prerequisites
Before running the backend, ensure the following services are set up:
✅ MySQL 🛢️
✅ Redis ⚡
✅ RabbitMQ 📩
✅ Swagger API Docs 📜
✅ Gmail App Password 📧

⚡ Steps to Configure & Run
🔹 1. Configure MySQL
Update your application.properties with:

spring.datasource.url=jdbc:mysql://localhost:3306/addressbook
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
🔹 2. Set Up Gmail App Password
Enable 2-Step Verification in Gmail

Generate an App Password

Update application.properties:

spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
🔹 3. Start Backend
Run the backend application:

mvn spring-boot:run
🔹 4. API Endpoints (Test via Postman)
📌 Register a New User

POST http://localhost:8080/api/auth/register
📄 Request Body (JSON - raw)

{
  "username": "your_username",
  "email": "your_email@example.com",
  "role": "USER",
  "password": "your_password"
}
🔑 Login Request

POST http://localhost:8080/api/auth/login
📄 Request Body (JSON - raw)

{
  "username": "your_username",
  "password": "your_password"
}
✅ Successful Login Response

{
  "userId": 1,
  "token": "your_jwt_token"
}
🔹 5. Update AddressBookController
✏️ Modify the username at line 114 and user ID at line 139 for first-time setup.

📄 API Documentation
📜 Swagger UI is available at:
http://localhost:8080/swagger-ui.html

🎯 Final Steps
Once everything is set up, both frontend and backend should be up and running smoothly! 🚀

💡 Need Help?
For any issues or support, feel free to reach out!

📧 Contact: ayushsingh05086@gmail.com
👨‍💻 Author: Ayush Kumar Singh

🌟 If you find this project useful, consider giving it a star! ⭐
