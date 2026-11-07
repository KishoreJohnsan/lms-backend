# lms-backend

Backend for a learning management system, built as a training assignment. The frontend is in [lms-frontend](https://github.com/KishoreJohnsan/lms-frontend).
Users register and log in, anyone can browse courses, and an admin adds or removes them. It's four Spring Boot services behind an API gateway, sharing one MongoDB database.

## Services

| Service | Port | What it does |
|---|---|---|
| `discoveryserver` | 8761 | Eureka server. The other services register here. |
| `gateway` | 3000 | Spring Cloud Gateway. The only entry point you call. |
| `authservice` | 3050 | Registration, login, JWT checks. Also forwards course requests to `courseservice`. |
| `courseservice` | 3060 | Course catalog (create, read, delete) stored in MongoDB. |

A request goes gateway → `authservice` → `courseservice`. The gateway sends everything under `/api/v1/lms/` to `authservice`, which checks the JWT and then looks up `courseservice` through Eureka (`lb://course`). So course calls never go from the gateway straight to `courseservice`.

## API

Call everything through the gateway at `http://localhost:3000/api/v1/lms`.

| Method | Path | Access |
|---|---|---|
| POST | `/register` | public, creates a user with role `user` |
| POST | `/register/company` | public, creates a company with role `company` |
| POST | `/authenticate` | public, returns a JWT (valid for 1 hour) |
| GET | `/courses/getall` | public |
| GET | `/courses/{courseId}` | public |
| GET | `/courses/name/{name}` | public |
| GET | `/courses/info/{technology}` | public |
| GET | `/courses/get/{technology}/{durationFrom}/{durationTo}` | public |
| POST | `/courses` | admin only |
| DELETE | `/courses/{courseId}` | admin only |

Admin and non-admin are decided by the `role` field in MongoDB. Registration only ever creates `user` or `company`, so the admin account has to be created directly in the database (see below).
Send the token as `Authorization: Bearer <token>`. `cURLs.txt` has working examples of every call.

## Stack

- Java 17, Spring Boot 3.0.6, Spring Cloud 2022.0.2
- Spring Cloud Gateway, Netflix Eureka
- Spring Security with JWT (jjwt)
- MongoDB (Spring Data)
- springdoc OpenAPI, ModelMapper, Lombok

## Running it locally

You need Java 17 and a MongoDB instance on `localhost:27017`. Both services use the database `fse2`.

`fse2.user.json` and `fse2.course.json` hold sample data: an admin user (`admin@skillmind.com`, password `admin`) and a few courses. Import them into the `user` and `course` collections.

Start the services in this order, each in its own terminal:

```sh
cd discoveryserver && ./mvnw spring-boot:run
cd courseservice   && ./mvnw spring-boot:run
cd authservice     && ./mvnw spring-boot:run
cd gateway         && ./mvnw spring-boot:run
```

Then get a token:

```sh
curl -X POST http://localhost:3000/api/v1/lms/authenticate \
  -H 'Content-Type: application/json' \
  -d '{"email":"admin@skillmind.com","password":"admin"}'
```
