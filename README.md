Here’s your **`README.md`** file, structured professionally for GitHub. 🚀  

---

# **Blog Project** 📝  

A **Spring Boot Blog Application** where users can **create, update, delete, and read blog posts**.  
Users can **comment on posts** using their **ID, username, and email**.  
The project includes **role-based authentication (Admin & User)** using **Spring Security** with `antMatchers()`.  
Also, it has **Signup & Signin features**, **validations**, **pagination**, **exception handling**, and **DTO mapping with ModelMapper**.  

---

## **Features** 🚀  

✅ **User Authentication & Authorization** – **Signup & Signin with JWT Authentication**.  
✅ **Role-based Access Control** – **Admin & User roles with Spring Security (`antMatchers()`)**.  
✅ **CRUD Operations on Blog Posts** – Create, Read, Update, and Delete posts.  
✅ **Commenting System** – Users can comment on posts with **ID, username, and email**.  
✅ **Validations** – Proper validation checks for **posts & comments**.  
✅ **Exception Handling** – Centralized error handling using `@ControllerAdvice`.  
✅ **Pagination & Sorting** – Implemented for **posts & comments** using **Spring Data JPA**.  
✅ **DTO & ModelMapper** – Used for efficient data conversion between **Entity & DTO**.  

---

## **Tech Stack** 🛠  

- **Backend:** Spring Boot, Spring Security  
- **Database:** MySQL / 
- **ORM:** Hibernate / JPA  
- **Security:** JWT Authentication, Spring Security (`antMatchers()`)  
- **Validation:** Bean Validation (Hibernate Validator)  
- **API Testing:** Postman  

---

## **Installation & Setup** ⚙️  

### **1. Clone the Repository**  
```bash
git clone https://github.com/your-username/blog-project.git
cd blog-project
```

### **2. Configure Database**  
Update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/blog_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### **3. Install Dependencies & Run**  
```bash
mvn clean install
mvn spring-boot:run
```

---

## **Roles & Permissions** 🔒  

| Role  | Permissions |
|--------|------------|
| **Admin** | Can create, update, delete, and manage all posts & comments |
| **User** | Can create, read, and comment on posts but cannot delete others' posts |

### **Spring Security Configuration (`antMatchers`)**
```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/api/admin/**").hasRole("ADMIN")
        .antMatchers("/api/posts/**").hasAnyRole("ADMIN", "USER")
        .antMatchers("/api/comments/**").hasAnyRole("ADMIN", "USER")
        .antMatchers("/api/auth/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
}
```

---

## **API Endpoints** 🔥  

### **Authentication (Signup & Signin)**
| Method | Endpoint        | Description  |
|--------|----------------|--------------|
| POST   | `/api/auth/signup` | Register a new user |
| POST   | `/api/auth/login`  | User login (returns JWT token) |

### **Post Management**
| Method | Endpoint        | Role  | Description  |
|--------|----------------|--------|--------------|
| GET    | `/api/posts`   | USER, ADMIN | Get all posts (paginated) |
| POST   | `/api/posts`   | ADMIN | Create a new post |
| GET    | `/api/posts/{id}` | USER, ADMIN | Get a single post |
| PUT    | `/api/posts/{id}` | ADMIN | Update a post |
| DELETE | `/api/posts/{id}` | ADMIN | Delete a post |

### **Commenting System**
| Method | Endpoint               | Role  | Description  |
|--------|------------------------|--------|--------------|
| GET    | `/api/posts/{id}/comments` | USER, ADMIN | Get comments for a post |
| POST   | `/api/posts/{id}/comments` | USER, ADMIN | Add a comment |
| DELETE | `/api/comments/{id}` | ADMIN | Delete a comment |

---

## **Exception Handling** ⚠️  
Handled using `@ControllerAdvice` to manage exceptions globally.  

### **Example: Custom Exception Handler**
```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiResponse response = new ApiResponse(ex.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
```

---

## **Pagination Implementation** 🔄  
Implemented using **Spring Data JPA**.  
- Example API request for paginated posts:
  ```
  GET /api/posts?page=0&size=5&sort=title,desc
  ```

---

## **DTO & ModelMapper Usage** 🔄  
Used **ModelMapper** to convert `Entity <--> DTO` to keep the API clean.  

### **Example: Post DTO**
```java
@Data
public class PostDTO {
    private Long id;
    private String title;
    private String content;
}
```

### **Example: ModelMapper Configuration**
```java
@Bean
public ModelMapper modelMapper() {
    return new ModelMapper();
}
```

---

## **Postman Testing** 📬  
1. Use Postman to test **signup & signin**, then obtain a JWT token.  
2. Include the JWT token in the `Authorization` header as **Bearer Token** to test secured endpoints.  

---

## **Future Enhancements** 🚀  
- Implement **like/dislike** functionality.  
- Add **user profile management**.  
- Improve **UI/UX with frontend frameworks**.  

---

## **Contributing** 🤝  
1. Fork the repository  
2. Create a new branch (`feature-xyz`)  
3. Commit and push your changes  
4. Open a pull request  

---

## **License** 📜  
This project is open-source under the **MIT License**.  

---

🚀 **Your blog project is now ready to be showcased on GitHub!** Let me know if you need any refinements. 🎯
