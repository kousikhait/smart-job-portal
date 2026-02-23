# Smart Job Portal

A Spring Boot job portal with roles for job seekers and recruiters, recommended jobs, and external job listings (LinkedIn, Naukri, Indeed).

## Tech stack

- Java 17
- Spring Boot 3.3
- Spring Data JPA, Thymeleaf
- PostgreSQL

## Run locally

1. Install Java 17 and Maven (or use the included Maven wrapper).
2. Configure PostgreSQL and set `application.properties` (or use `application-local.properties` for local DB URL, user, password).
3. Build and run:

   ```bash
   ./mvnw spring-boot:run
   ```

   On Windows:

   ```cmd
   .\mvnw.cmd spring-boot:run
   ```

4. Open http://localhost:8080

## Features

- User roles: Admin, Recruiter, Job Seeker
- Job listings and applications with resume upload
- Recommended jobs based on user skills
- External jobs sync from LinkedIn / Naukri / Indeed (sample data) with "Apply on site" links
- Recruiter dashboard to view and approve/reject applications

## License

MIT (or your choice).
