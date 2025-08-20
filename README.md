# 🚍 TripPass (Authentication  Service - Microservice 1)


---

### 🔗 [TripWise-Architecture 🍀 Overview Repository ](https://github.com/Ochwada/TripWise-Architecture)
Microservices ⬇️ part of **TripWise System**


#### 🖇️ Microservice 1 : TripHub - [ Gateway  Service](https://github.com/Ochwada/TripWise-Pass)
#### 🖇️ Microservice 2: TripPass - [ Authentication Service](https://github.com/Ochwada/TripWise-Pass)

---

## 📖 About 
TripWise-Pass is a microservice within the **TripWise** system,  purpose-built for handling user authentication via
OAuth2 with Google as the social login provider.

Developed with Java 17+, Spring Boot, and Spring Security, this service ensures a secure and extensible authentication 
flow, seamlessly integrable into modern microservice architectures.

##  Features
- Update passenger profiles
- Secure authentication & role-based access
- Integration with booking and trip services 
- OAuth2-based login using Google 
- Secure, token-based authentication (JWT-ready)
- Modular and easily customizable for additional providers 
- Plug-and-play integration with microservice ecosystems


##  Tech Stack
- **Java 17**
- **Spring Boot**
- **Maven**
- **PostgreSQL / MongoDB** (choose based on architecture)
- **Docker**


## Project Structure
```yaml

trippass/
│
├── src/
│   ├── main/
│   │   ├── src/main/java/com/tripwise/passenger/
│   │   │   ├── config/             # Spring Security config
│   │   │   ├── controller/         # Auth controller endpoints
│   │   │   └── TrippassApplication.java
│   │   └── resources/
│   │       ├── template/           # Thymeleaf templates
│   │       └── application.yml     # Configuration
│
├── .env                            # (optional) for secrets
├── README.md
├── pom.xml

```

##  Get Started
### Clone repo

```yaml
git clone https://github.com/<your-username>/TripWise-Pass.git TripPass
cd TripPass
```

### Environment Configurations
The variables are defined  in a file located at:
```.dotenv
authentication-service/.env
```
> These credentials are used to authenticate users via Google OAuth2/OpenID Connect.

```.dotenv
#-------------------------------------------
#  Configuration
#-------------------------------------------
GOOGLE_CLIENT_ID=google_client_id
GOOGLE_CLIENT_SECRET=google_secret
```

#### Variable Reference

| Variable               | Description                                      | Where to Get It                                    |
|------------------------|--------------------------------------------------|----------------------------------------------------|
| `GOOGLE_CLIENT_ID`     | OAuth2 client ID for Google login                | From Google Cloud Console under OAuth2 credentials |
| `GOOGLE_CLIENT_SECRET` | Secret used to authenticate your app with Google | Same place as above (keep this secure!)            |


Service will be available at:
```yaml
# Localhost
http://localhost:9091/trippass

# Dockerized
https://tripwise:9091/trippass
```

### Docker 
```yaml
docker build -t trippass .
docker run -p 9091:9091 trippass
```

# API Endpoints
| Method | Endpoint                       | Auth Required | Description                                                                 |
|--------|--------------------------------|---------------|-----------------------------------------------------------------------------|
| `GET`  | `/`                            | ❌ No          | Public home page (renders `home.html`)                                      |
| `GET`  | `/login`                       | ❌ No          | Custom login page (renders `login.html` if defined)                         |
| `GET`  | `/oauth2/authorization/google` | ❌ Redirect    | Triggers Google OAuth2 login via Spring Security                            |
| `GET`  | `/internal/token`              | ✅ Yes         | Internal endpoint to get the authenticated user's raw ID Token (JWT)  <br/> |
| `GET`  | `/dashboard`                   | ✅ Yes         | User dashboard after successful login (renders `dashboard.html`)            |

