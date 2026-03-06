# E-Commerce-Backend
A fully functional **E-Commerce REST API** built with **Spring Boot**, featuring user authentication, product management, cart operations, and order processing.

---

## 🚀 Tech Stack

| Technology | Usage |
|---|---|
| **Spring Boot** | Backend Framework |
| **JPA / Hibernate** | ORM — Database Mapping |
| **MySQL** | Relational Database |
| **REST API** | Communication Protocol |
| **Maven** | Build & Dependency Management |

---

## 🏗️ Architecture

```
Client (Postman / Frontend)
            ↓
      Controller Layer       ← Request receive karta hai
            ↓
       Service Layer         ← Business logic
            ↓
     Repository Layer        ← Database operations
            ↓
          MySQL              ← Data storage
```

**Design Pattern:** MVC + Service Layer (Separation of Concerns)

---

## 📦 Modules

### 👤 User Module
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | User login |
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| DELETE | `/api/users/{id}` | Delete user |

### 🛍️ Product Module
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/products` | Get all products |
| GET | `/api/products/{id}` | Get product by ID |
| GET | `/api/products/search?keyword=` | Search by keyword |
| GET | `/api/products/category/{category}` | Filter by category |
| GET | `/api/products/filter/price?min=&max=` | Filter by price range |
| GET | `/api/products/sort?order=low/high` | Sort by price |
| GET | `/api/products/filter?category=&keyword=&min=&max=` | Advanced filter |
| POST | `/api/products` | Add new product |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |

### 🛒 Cart Module
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/cart/add` | Add item to cart |
| GET | `/api/cart/{userId}` | Get user's cart |
| GET | `/api/cart/{userId}/total` | Get cart total |
| PUT | `/api/cart/item/{cartItemId}?quantity=` | Update quantity |
| DELETE | `/api/cart/item/{cartItemId}` | Remove item |
| DELETE | `/api/cart/clear/{userId}` | Clear entire cart |

### 📦 Order Module
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/orders` | Place order |
| GET | `/api/orders/user/{userId}` | Get user's orders |
| GET | `/api/orders` | Get all orders (Admin) |
| PUT | `/api/orders/{id}/status?status=` | Update order status |

---

## ⭐ Smart Features

### 1. Duplicate Cart Prevention
Same product dobara add karo toh **naya row nahi banta** — quantity automatically update hoti hai.
```java
Optional<Cart> existing = cartRepository.findByUserIdAndProductId(userId, productId);
if (existing.isPresent()) {
    cartItem.setQuantity(cartItem.getQuantity() + quantity); // quantity update!
}
```

### 2. Order Placement — 3 Operations at Once
Ek order place hone pe **3 kaam automatically** hote hain:
- ✅ Total price calculate
- ✅ Product stock deduct
- ✅ Cart auto-clear

### 3. Advanced Optional Filtering
Saare filter params **optional** hain — jo chahiye woh do, baaki ignore:
```
/api/products/filter?category=Electronics          → sirf category
/api/products/filter?min=500&max=2000              → sirf price
/api/products/filter?category=Electronics&min=500  → dono
```

---

## 🔧 Optimizations Done

### N+1 Problem Fix
`getCartTotal()` mein pehle har item ke liye alag DB call hoti thi:
```
10 cart items = 11 DB calls ❌
```
`findAllById()` se fix kiya:
```
10 cart items = 2 DB calls ✅
```

---

## 🗄️ Database Setup

```sql
CREATE DATABASE ecommerce;
```

`application.properties` mein configure karo:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## ▶️ How to Run

```bash
# 1. Clone the repo
git clone https://github.com/yourusername/ecommerce-backend.git

# 2. MySQL mein database banao
CREATE DATABASE ecommerce;

# 3. application.properties mein credentials update karo

# 4. Run karo
mvn spring-boot:run
```

Server start hoga: `http://localhost:8080`

---

## 🧪 API Testing (Postman)

### Register User
```json
POST /api/auth/register
{
    "name": "Raj Sharma",
    "email": "raj@gmail.com",
    "password": "1234",
    "phone": "9876543210"
}
```

### Add Product
```json
POST /api/products
{
    "name": "iPhone 15",
    "description": "Latest Apple smartphone",
    "price": 79999,
    "stock": 10,
    "category": "Electronics"
}
```

### Add to Cart
```json
POST /api/cart/add
{
    "userId": 1,
    "productId": 1,
    "quantity": 2
}
```

### Place Order
```json
POST /api/orders
{
    "userId": 1,
    "productId": 1,
    "quantity": 2
}
```

---

## 📁 Project Structure

```
src/
├── main/java/com/ecommerce/
│   ├── controller/
│   │   ├── UserController.java
│   │   ├── ProductController.java
│   │   ├── CartController.java
│   │   └── OrderController.java
│   ├── service/
│   │   ├── UserService.java
│   │   ├── ProductService.java
│   │   ├── CartService.java
│   │   └── OrderService.java
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── ProductRepository.java
│   │   ├── CartRepository.java
│   │   └── OrderRepository.java
│   └── entity/
│       ├── User.java
│       ├── Product.java
│       ├── Cart.java
│       └── Order.java
└── resources/
    └── application.properties
```

---

## 🔮 Future Improvements

- [ ] JWT Authentication & Authorization
- [ ] Password Hashing (BCrypt)
- [ ] JPA Mappings (`@ManyToOne`, `@OneToMany`)
- [ ] Exception Handling (Global `@ControllerAdvice`)
- [ ] Pagination & Sorting
- [ ] Payment Gateway Integration

---

## 👨‍💻 Author

**Your Name**  
Learning Spring Boot — Building Real Projects 🚀
