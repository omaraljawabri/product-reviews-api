# ⭐ Product Reviews API

This project is a product reviews API created to learn more about NoSQL databases like MongoDB and their interaction with Spring. Cloudinary was also used to understand more about image storage services.

## 📋 Requirements 
    Docker installed and running on your local machine
    Or
    JDK 21, Maven 3.9.x, MongoDB and PostgreSQL installed on your local machine

  If you're not sure wheter JDK 21 and Maven 3.9.x are installed, run the following commands in your terminal:
  
    $ java --version
  
    $ mvn --version

## ⚙️ Starting the application

If you're using Docker, follow the instructions below to start the application:
    
    1. Clone this repository or download the zip version and unzip it
    2. Open the terminal in your application
    3. Type the command: $ docker-compose up
    4. Wait until all the containers are up and running
    5. The application is up! http://localhost:8080

If you're not using Docker but have JDK 21 and Maven 3.9.x installed, follow these steps to start the application:

    1. Clone this repository or download the zip version and unzip it
    2. Make sure PostgreSQL and MongoDB are up and running
    3. Open the terminal in your application
    4. Type the command: $ mvn spring-boot:run
    5. Wait until the application completely starts
    6. The application is up! http://localhost:8080

## 🖼️ Cloudinary Service

To use cloudinary service in this application, you'll need to create an account on Cloudinary. For that, you can follow the instructions on this link: https://cloudinary.com/ 

Then, create a `.env` file in the project root and populate the fields below with your cloudinary credentials:

```bash
# Cloudinary configs
CLOUDINARY_NAME=<your-cloudinary-name>
CLOUDINARY_KEY=<your-cloudinary-key>
CLOUDINARY_SECRET=<your-cloudinary-secret>
```

## 📄 Documentation

Springdoc OpenAPI was used to document this API. You can access the documentation at the link below after starting the application:
`http://localhost:8080/swagger-ui/index.html`

    Note: The POST and PUT endpoints at http://localhost:8080/api/v1/products do not work in Swagger. For these, use Postman or another HTTP request tool.

## 💻 Project used technologies

- Java 21
- Maven 3.9.x
- Spring Boot, JPA and Security
- Docker
- PostgreSQL
- FlyWay
- MongoDB
- Swagger
- Jib for docker images
