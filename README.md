# E-Commerce Simple Showcase

This is a **E-Commerce Backend Platform** intended to demonstrate a clean, layered backend architecture. While the initial setup is straightforward, the system is built with a **Scalable First** mindset, allowing for easy adding new features.

### Key Scalability Features:

- **Containerized Environment:** Fully Dockerized for consistent deployment across any infrastructure.
- **Database Isolation:** Decoupled PostgreSQL instance with persistent volume mapping.
- **Environment-Driven:** All configurations are managed via `.env` for security and flexibility.

---

## Technology Stack

### Current Implementation:

- **Runtime:** Java 17
- **Framework:** Spring Boot 3+
- **Web:** Spring Web (RESTful API)
- **Security**: Spring Security (Securing the application with basic authentication)
- **Data:** Spring Data JPA (Hibernate)
- **Database:** PostgreSQL 
- **DevOps:** Docker & Docker Compose
- **Build Tool:** Maven

### Planned Roadmap (Future Enhancements):

- **Security:** Integration of **Spring Security** with JWT for stateless authentication (DONE).
- **Migrations:** **Liquibase** for version-controlled database schema management.
- **Messaging:** **Spring SMTP** for automated order confirmation and registration emails (DONE).
- **Caching:** Redis integration for product catalog performance.

---
## Docker Deployment
### First Time
```shell
./scripts/buildContainer.sh
```
### Image created
```shell
docker compose up
```

## Documentation
### Browsing endpoints
- After running containers deployment
- go to your browser 
> http://localhost:8080/swagger-ui/index.html
### Features
- Category Management.
- Product Management.
- Customer registration.
- Customer Login
- Securing Endpoints with JWT Access Tokens.
- Using ***HATEOS*** for linking relative endpoints.
- Sending emails with ***Spring SMTP***.
- Sending Welcome mail after new customer registration.
- Cart Management.
- Checkout (Place Order).
- Sending Welcome Email after customer registration.
- Sending confirmation email after order checkout.
- User can login and ***JWT Access Token***.
- OpenAPI Documentation.
### Exceptions

- Using ***GlobalExceptionHandler*** for centralized exception handling 
- Creating initial exceptions for ***Clear and Documented*** API responses

### Entities
- Initial required entities for the project
- Fetching data lazily to avoid fetching unnecessary data
- Using ***@NameEntityGraph*** to efficiently fetch data from the Database.

### Repositories
- Initial ***JPA*** repository.
- Using ***JpaRepository*** for ***Paging and Sorting & other JPA features***.

### Services
- Using the naming convention IServiceName to separate the service from it's implementation
  better than using ServiceNameImp Convention making the code cleaner and robust.

### Controllers
#### Assemblers
- Using ***Spring HATEOS*** to add links to relevant operations.
---
### Diagrams
#### Basic Auth flow
```mermaid
   sequenceDiagram
    autonumber
    title Spring Security Basic Authentication Flow (Authenticated Request)

    actor Client
    participant Basic_Auth_Filter as Basic Authentication Filter
    participant Auth_Manager as Authentication Manager
    participant Auth_Provider as AuthenticationProvider
    participant User_Details_Service as UserDetailsService (JPA)
    participant Security_Context_Holder as Security Context Holder
    participant Controller as Secured Controller (Order/Cart)

    Client->>Basic_Auth_Filter: GET /api/orders (Authorization: Basic base64)
    Note over Client, Basic_Auth_Filter: Request contains credentials in Header.

    activate Basic_Auth_Filter
    Basic_Auth_Filter->>Basic_Auth_Filter: Extract Username & Password

    Basic_Auth_Filter->>Auth_Manager: authenticate(Token)
    activate Auth_Manager

    Auth_Manager->>Auth_Provider: authenticate(Token)
    activate Auth_Provider
    Note right of Auth_Provider: The standard Provider for UserDetails.

    Auth_Provider->>User_Details_Service: loadUserByUsername(username)
    activate User_Details_Service
    User_Details_Service-->>Auth_Provider: return UserDetails (from DB)
    deactivate User_Details_Service

    Auth_Provider->>Auth_Provider: Check password

    Auth_Provider-->>Auth_Manager: Authentication (Authenticated=true)
    deactivate Auth_Provider

    Auth_Manager-->>Basic_Auth_Filter: Authentication Object
    deactivate Auth_Manager

    Basic_Auth_Filter->>Security_Context_Holder: setAuthentication(auth)
    Note right of Security_Context_Holder: User is now globally "Logged In" for this thread.

    Basic_Auth_Filter->>Controller: Proceed to Method or Next filter
    deactivate Basic_Auth_Filter

    activate Controller
    Controller-->>Client: 200 OK (Response DTO)
    deactivate Controller
```
#### Global Exception handler
```mermaid
sequenceDiagram
    autonumber
    title Spring Boot: General Global Exception Handling (RuntimeException)

    actor Client
    participant Dispatcher_Servlet as Dispatcher Servlet
    participant Controller as RestController
    participant Service as Service
    participant Global_Handler as Global Exception Handler (@RestControllerAdvice)

    Client->>Dispatcher_Servlet: HTTP Request (POST/GET/PUT/DELETE)
    
    activate Dispatcher_Servlet
    Dispatcher_Servlet->>Controller: Route to Controller Method
    activate Controller
    
    Controller->>Service: Execute Business Logic
    activate Service
    Note over Service: [Transaction Started]

    alt Business Rule Violation / Data Error
        Service-->>Service: Logic fails (e.g., Null, Conflict, Logic Error)
        Note right of Service: Throws RuntimeException (or Subclass)
        Note over Service: [Transaction Rollback Triggered (If exists)]
        Service-->>Controller: Propagate Exception
        deactivate Service
        
        Controller-->>Dispatcher_Servlet: Propagate Exception
        deactivate Controller
        
        Note over Dispatcher_Servlet: Exception detected by DispatcherServlet
        
        Dispatcher_Servlet->>Global_Handler: Intercept Exception
        activate Global_Handler
        
        Note right of Global_Handler: Search for @ExceptionHandler(RuntimeException.class)
        Global_Handler->>Global_Handler: Build ErrorResponse DTO
        Global_Handler-->>Dispatcher_Servlet: ResponseEntity (Body + HTTP Status)
        deactivate Global_Handler

        Dispatcher_Servlet-->>Client: Structured Error JSON (e.g., 4xx or 500)
    %% else Success Flow
    %%     Service-->>Controller: Return Data/DTO
    %%     Note over Service: [Transaction Committed] (if Exists)
    %%     Controller-->>Client: 200 OK / 201 Created
    end
    deactivate Dispatcher_Servlet
```

#### Add To Cart flow
```mermaid
sequenceDiagram
  autonumber
  title E-Commerce: Add to Cart (Transactional Flow with Controller-Level Error Handling)

  participant Client
  participant Controller as CartController
  participant Service as CartService (Transactional)
  participant Helper as CartHelper
  participant Context as SecurityContextHolder
  participant DB as Database Repository

  Client->>Controller: POST /api/cart/addToCart (DTO)

  activate Controller
  Controller->>Service: addItemToCart(request)

  activate Service
  Note over Service: [Transaction Start]

  Service->>Helper: fetchContextCustomerWithCart()
  activate Helper
  Helper->>Context: getContext().getAuthentication().getPrincipal()
  Context-->>Helper: SecurityUser

  Helper->>DB: findWithCartByUserId(userId)
  Note right of DB: Deep Fetch: Cart + Items + Products
  DB-->>Helper: Customer Entity
  Helper-->>Service: Customer
  deactivate Helper

  alt Cart is Null
    Service->>Helper: createNewCart(customer)
    Helper-->>Service: new Cart
  end

  Note over Service: Guard 1: Is Cart Locked?
  alt cart.getIsLocked() == true
    Note over Service: [Transaction Rollback]
    Service-->>Controller: throw LockedCartException <br/> (GlobalExceptionHandler)
    Controller-->>Client: 409 Conflict
  end

  Service->>Helper: findValidateItemExistenceInCartByProductId(...)

  alt Item Exists
    Service->>Service: updateCartItem (Increment Qty)
  else Item is New
    Service->>Helper: fetchValidateProductAvailabilityById(id)
    activate Helper
    Helper->>DB: findById(productId)
    DB-->>Helper: Product Entity

    Note over Helper: Guard 2: isAvailable == false?
    alt Product.getIsAvailable() == false
      Helper-->>Service: throw ProductUnavailableException
      Note over Service: [Transaction Rollback]
      Service-->>Controller: throw ProductUnavailableException <br/> (GlobalExceptionHandler)
      Controller-->>Client: 404 Not Found
    end
    Helper-->>Service: Valid Product
    deactivate Helper

    Service->>Service: Create & Add New CartItem
  end

  Note over Service: [Transaction Commit]
  Note right of Service: Dirty check: Updates DB if needed
  Service->>Controller: CartResponse (Mapped DTO)
  deactivate Service

  Controller-->>Client: 200 OK
  deactivate Controller
```
#### Checkout & Place Order flow
```mermaid
sequenceDiagram
    autonumber
    title E-Commerce: Checkout & Place Order 

    participant Client
    participant Controller as OrderController
    participant Service as OrderService
    participant CartService as CartService
    participant Helper as CartHelper
    participant DB as Database Repositories
    participant EmailService as EmailService

    Client->>Controller: POST /api/orders/checkout (OrderRequest)
    activate Controller
    
    Controller->>Service: checkout(request)
    activate Service

    %% Fetch Context
    Service->>Helper: getContextCustomerWithCart()
    activate Helper
    Note right of Helper: Fetches SecurityUser & Deep Fetches Customer Graph
    Helper-->>Service: Customer Entity
    deactivate Helper

    %% Transactional placeOrder starts
    Service->>Service: placeOrder(request, customer)
    activate Service
    Note over Service: [Transaction Start]

    %% Guard 1: Address
    Service->>DB: customerAddressRepository.findById(addressId)
    
    Note over Service: Guard 1: Address Exists?
    alt Address Not Found
        DB-->>Service: Optional.empty()
        Note over Service: [Transaction Rollback]
        Service-->>Controller: throw ResourceNotFoundException <br/> (GlobalExceptionHandler)
        Controller-->>Client: 404 Not Found
    end
    DB-->>Service: CustomerAddress Entity

    %% Guard 2: Empty Cart
    Note over Service: Guard 2: Is Cart Empty?
    alt cart == null || cartItems.isEmpty()
        Note over Service: [Transaction Rollback]
        Service-->>Controller: throw EmptyCartException <br/> (GlobalExceptionHandler)
        Controller-->>Client: 400 Bad Request
    end

    %% Guard 3: Locked Cart
    Note over Service: Guard 3: Is Cart Locked?
    alt cart.getIsLocked() == true
        Note over Service: [Transaction Rollback]
        Service-->>Controller: throw LockedCartException <br/> (GlobalExceptionHandler)
        Controller-->>Client: 409 Conflict
    end

    %% Lock Cart
    Service->>DB: cartRepository.saveAndFlush(cart) (isLocked = true)
    
    %% Create Order Items & Guard 4: Availability
    Service->>Service: createOrderItems(cart.getCartItems())
    
    Note over Service: Guard 4: Product Available?
    alt Product.getIsAvailable() == false
        Note over Service: [Transaction Rollback]
        Service-->>Controller: throw ProductUnavailableException <br/> (GlobalExceptionHandler)
        Controller-->>Client: 404 Not Found
    end
    Note right of Service: Calculates subtotals, creates OrderItems

    %% Build and Save Order
    Service->>Service: Build Order (set address, sum subtotals)
    Service->>DB: orderRepository.saveAndFlush(order)
    DB-->>Service: Saved Order Entity
    
    %% Clear Cart
    Service->>CartService: clearCart(cart)
    activate CartService
    Note right of CartService: Empties cart items (batch deletion)
    CartService-->>Service: return
    deactivate CartService

    Service-->>Service: OrderResponse (Mapped DTO)
    Note over Service: [Transaction Commit]
    deactivate Service

    %% Send Email (Outside Transaction)
    Service->>Helper: sendOrderConfirmationEmail(...)
    activate Helper
    Helper-)EmailService: sendEmailAsync(to, subject, body)
    Note right of EmailService: ⚡ Asynchronous Execution (Fire & Forget)
    Helper-->>Service: return (Non-blocking)
    deactivate Helper

    Service-->>Controller: OrderResponse DTO
    deactivate Service
    
    Controller-->>Client: 200 OK
    deactivate Controller
```
## Contact & Contribution

- **Author:** Abdalla Samir Khalifa
- **Role:** Systems Analyst & Backend Developer
- **Contact**:
  - GitHub: [@AbdallaSamirKhalifa](https://github.com/AbdallaSamirKhalifa)
  - Email: [abdallasamirkhalifa@gmail.com](abdallasamirkhalifa@gmail.com)
  - Linkedin: [Abdalla Khalifa](https://linkedin.com/in/abdalla-khalifa)
