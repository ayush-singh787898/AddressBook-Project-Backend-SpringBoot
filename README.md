📖 Address Book Application
🔹 Overview
The Address Book Application is a full-stack project featuring an Angular-based frontend and a Spring Boot backend. The backend is integrated with MySQL 🛢️, Redis ⚡, RabbitMQ 📩, Swagger 📜, and Gmail App Password Authentication 📧 for secure email services.

🎨 Frontend Setup (Angular 17)
✅ Prerequisites
Before running the frontend, make sure you have:
✔️ Node.js and npm installed
✔️ Angular CLI installed

🚀 Steps to Set Up and Run the Frontend
1️⃣ Clone the repository:

git clone https://github.com/ayush-singh787898/AddressBook-Project-Backend-SpringBoot.git
cd client_AddressBook
2️⃣ Install dependencies:

npm install
3️⃣ Start the Angular development server:

ng serve
4️⃣ Access the application:
Open your browser and navigate to 🌐 http://localhost:4200.

🛠️ Backend Setup (Spring Boot)
✅ Prerequisites
Before running the backend, ensure the following services are installed and properly configured:
🔹 MySQL (Database) 🛢️
🔹 Redis (Caching) ⚡
🔹 RabbitMQ (Message Broker) 📩
🔹 Swagger (API Documentation) 📜
🔹 Gmail App Password (For email authentication) 📧

🏗️ Steps to Configure and Run the Backend
🔹 1. Configure MySQL
Update application.properties with your MySQL username and password.

🔹 2. Setup Gmail App Password
Enable 2-Step Verification in your Gmail account.

Generate an App Password and update it in application.properties.

🔹 3. Start the Spring Boot Backend
Run the backend application using:

mvn spring-boot:run
🔹 4. User Registration via Postman
📌 Register a New User
Make a POST request to create a new user:

POST http://localhost:8080/api/auth/register
📄 Request Body (JSON - raw):

{
  "username": "your_username",
  "email": "your_email@example.com",
  "role": "USER",
  "password": "your_password"
}
🔑 Login Request
Authenticate the user by sending a POST request:

POST http://localhost:8080/api/auth/login
📄 Request Body (JSON - raw):

{
  "username": "your_username",
  "password": "your_password"
}
✅ On successful login, you will receive a user ID.

🔹 5. Update AddressBookController
✏️ Modify the username at line 114 to match your registered username.
✏️ Update the user ID at line 139 (Default: 1 for initial setup).

🎯 Final Steps
Once both the frontend and backend are successfully configured and running, your Address Book Application will be fully functional. 🎉

💡 Need help? Feel free to reach out!

🤝 Best Regards,
💻 Ayush Kumar Singh

📩 Contact: ayushsingh05086@gmail.com
