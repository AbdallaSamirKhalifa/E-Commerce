# 🛒 E-Commerce Simple Showcase

This is a **Simple Showcase E-Commerce Application** intended to demonstrate a clean, layered backend architecture. While the initial setup is straightforward, the system is built with a **Scalable First** mindset, allowing for easy adding new features.

### Key Scalability Features:

- **Containerized Environment:** Fully Dockerized for consistent deployment across any infrastructure.
- **Database Isolation:** Decoupled PostgreSQL instance with persistent volume mapping.
- **Stateless Design:** Ready for horizontal scaling behind a **load balancer** -> future plan.
- **Environment-Driven:** All configurations are managed via `.env` for security and flexibility.

---

## 🛠️ Technology Stack

### Current Implementation:

- **Runtime:** Java 17
- **Framework:** Spring Boot 3+
- **Web:** Spring Web (RESTful API)
- **Data:** Spring Data JPA (Hibernate)
- **Database:** PostgreSQL 16
- **DevOps:** Docker & Docker Compose
- **Build Tool:** Maven

### 📈 Planned Roadmap (Future Enhancements):

- **Security:** Integration of **Spring Security** with JWT for stateless authentication.
- **Migrations:** **Liquibase** for version-controlled database schema management.
- **Messaging:** **Spring SMTP** for automated order confirmation and registration emails.
- **Caching:** Redis integration for product catalog performance.

---

## Contact & Contribution

- **Author:** Abdalla Samir Khalifa
- **Role:** Systems Analyst & Backend Developer
- **Contact**:
  - GitHub: [@AbdallaSamirKhalifa](https://github.com/AbdallaSamirKhalifa)
  - Email: [abdallasamirkhalifa@gmail.com](abdallasamirkhalifa@gmail.com)
  - Linkedin: [Abdalla Khalifa](https://linkedin.com/in/abdalla-khalifa)
