
## 📚 BookStore – Online Bookstore

**BookStore** is a **pet project** developed during studies at **Mate Academy**, showcasing a fully functional **API for online book sales**. This project is built using **Spring Boot**, demonstrating a modern approach to building **RESTful APIs** in Java.

Users can easily register, browse the catalog, add books to their cart, and place orders. Administrators have full control over the product catalog and orders, ensuring flexible management of the store.

---

## 🚀 Key Features

* **Full CRUD functionality**.
* **Role-based access** for users (**User**) and administrators (**Admin**).
* **Secure authentication** and authorization with **JWT tokens**.
* **Modern tech stack** based on Spring Boot.
* **API documentation** using Swagger/OpenAPI.

---

## 🛠️ Technologies and Tools

| Technology            | Purpose                                        |
| --------------------- | ---------------------------------------------- |
| **Java 17**           | Main programming language                      |
| **Spring Boot**       | Framework for building REST APIs               |
| **Spring Security**   | Authentication, authorization, and roles       |
| **Spring Data JPA**   | ORM-based database interaction                 |
| **Hibernate**         | JPA provider for interaction with MySQL        |
| **Spring Web**        | Handling HTTP requests                         |
| **Spring Test**       | Integration testing                            |
| **JUnit**             | Unit testing framework                         |
| **Mockito**           | Mocking for testing                            |
| **TestContainers**    | Isolated container-based testing               |
| **Liquibase**         | Database migrations                            |
| **MapStruct**         | Automatic mapping between DTO and Entity       |
| **Lombok**            | Generates getters, setters, constructors, etc. |
| **JWT Token**         | Secure token-based authentication              |
| **MySQL**             | Relational database                            |
| **Maven**             | Dependency management and build tool           |
| **Docker**            | Application containerization                   |
| **Swagger / OpenAPI** | REST API documentation                         |
| **Postman**           | API testing and demonstration                  |

---

## 📦 Functionality

BookStore includes a complete set of features for an online bookstore, with role-based access for **users (shoppers)** and **administrators (managers)**.

### 👤 For Users (Shoppers)

| Feature              | Description                                           |
| -------------------- | ----------------------------------------------------- |
| 🔐 Register & Login  | Create an account and log in to the system            |
| 📚 Browse Books      | View all books or detailed info about a specific book |
| 🗂 Browse Categories | View all categories and the books in them             |
| 🛒 Shopping Cart     | Add, view, and remove books from the cart             |
| 💳 Checkout          | Purchase all books in the cart                        |
| 📜 Order History     | View your order history                               |
| 📦 Order Details     | View the contents of a specific order                 |

### 🛠 For Admins (Managers)

| Feature              | Description                                               |
| -------------------- | --------------------------------------------------------- |
| ➕ Add Book           | Add a new book to the store                               |
| ✏️ Edit Book         | Update book details like title, description, price, etc.  |
| ❌ Delete Book        | Remove a book from the catalog                            |
| 🗂 Manage Categories | Create, update, and delete categories                     |
| 📋 Manage Orders     | Change order status (`PENDING`, `COMPLETED`, `DELIVERED`) |

---

## 🧩 Main Entities Structure

* `User` – Registered user (shopper or admin)
* `Role` – User role (`ROLE_USER`, `ROLE_ADMIN`)
* `Book` – Book entity with title, author, description, price, ISBN, image
* `Category` – Book category (e.g., "Science Fiction")
* `ShoppingCart` – User’s shopping cart
* `CartItem` – Individual book in the shopping cart
* `Order` – User’s placed order
* `OrderItem` – Book included in an order

---

## 🔐 API Access (Security Requirements)

Access to REST endpoints is restricted based on user roles. Authorization is handled via **JWT tokens**. After a successful login, a JWT token is issued and must be included in the request header as `Authorization: Bearer <token>` for all protected endpoints.

### 🔓 Publicly Accessible:

| Method | Endpoint             | Description                   |
| ------ | -------------------- | ----------------------------- |
| `POST` | `/api/auth/register` | Register a new user           |
| `POST` | `/api/auth/login`    | Login and receive a JWT token |

### 👤 Accessible to `USER` Role:

| Method   | Endpoint                               | Description                                |
| -------- | -------------------------------------- | ------------------------------------------ |
| `GET`    | `/api/books`                           | Get a list of all books                    |
| `GET`    | `/api/books/{id}`                      | Get detailed info about a specific book    |
| `GET`    | `/api/categories`                      | List all categories                        |
| `GET`    | `/api/categories/{id}`                 | Get a category by ID                       |
| `GET`    | `/api/categories/{id}/books`           | Get books within a category                |
| `GET`    | `/api/cart`                            | View shopping cart                         |
| `POST`   | `/api/cart`                            | Add a book to the cart                     |
| `PUT`    | `/api/cart/cart-items/{cartItemId}`    | Update book quantity in the cart           |
| `DELETE` | `/api/cart/cart-items/{cartItemId}`    | Remove a book from the cart                |
| `GET`    | `/api/orders`                          | View all your orders                       |
| `POST`   | `/api/orders`                          | Place a new order                          |
| `GET`    | `/api/orders/{orderId}/items`          | View books in a specific order             |
| `GET`    | `/api/orders/{orderId}/items/{itemId}` | View details of a book in a specific order |

### 🛠️ Accessible to `ADMIN` Role:

| Method   | Endpoint               | Description                                               |
| -------- | ---------------------- | --------------------------------------------------------- |
| `POST`   | `/api/books/`          | Add a new book                                            |
| `PUT`    | `/api/books/{id}`      | Update book details                                       |
| `DELETE` | `/api/books/{id}`      | Delete a book                                             |
| `POST`   | `/api/categories`      | Create a new category                                     |
| `PUT`    | `/api/categories/{id}` | Update a category                                         |
| `DELETE` | `/api/categories/{id}` | Delete a category                                         |
| `PATCH`  | `/api/orders/{id}`     | Change order status (`PENDING`, `COMPLETED`, `DELIVERED`) |

### Swagger UI:

![Swagger](https://i.ibb.co/tMknXbwn/2025-07-08-19-24-21.png)

---

## 🚀 Running the Project Locally

To run BookStore locally, you can use **Docker Compose** – the easiest way to automatically configure both the app and the database.

### 1. Prerequisites

Make sure you have the following installed:

* **Docker** and **Docker Compose**

### 2. Clone the Repository

```bash
git clone https://github.com/MykolaTurak/book-shop-app.git # Replace with your repo
cd BookStore
```

### 3. Run the App with Docker Compose

Navigate to the project root directory (where the `docker-compose.yml` file is located) and run:

```bash
docker-compose up --build
```

This command will:

* Build a Docker image for your app based on the `Dockerfile`.
* Start a **MySQL** container (if not already running).
* Start a container for your **BookStore** app.
* Automatically run Liquibase migrations at startup.

The app will be available at `http://localhost:8080`.
API docs (Swagger UI) will be available at `http://localhost:8080/swagger-ui.html`.

### 4. Stopping the App

To stop the running containers, press `Ctrl+C` in the terminal, then run:

```bash
docker-compose down
```

---

## 🤝 Contributing

All contributions are welcome!
If you'd like to improve this project, please fork the repository, make your changes, and submit a pull request.

---

## 📞 Contact

* **Author:** Mykola Turak
* **GitHub:** [https://github.com/MykolaTurak](https://github.com/MykolaTurak)
* **LinkedIn:** [https://www.linkedin.com/in/mykola-turak-1b6853312/](https://www.linkedin.com/in/mykola-turak-1b6853312/)

---