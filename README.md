# Gamevaultr

A video game tracking app to organize your collection, decide what to play, track playthroughs, and journal your play history.



---


## 🔗 Live Demo
https://gamevaultr.com

### Demo Access
You can explore the app using demo credentials:

- Email: demo@demo.com  
- Password: demo123

## Features

- Search the IGDB database and add games to your collection
- Manage your collection with play status, platform, filters & search
- Track playthroughs per game with progress details
- Log individual play sessions within playthroughs
- Write reviews, reflections, and notes per title

## Product Demo Media

### 1) Hero Demo (10-20s)

Show the end-to-end core flow in one short GIF or clip:

- Sign in
- Search for a game
- Add it to collection
- Update play status/progress

```md
![Hero Demo](docs/media/hero-demo.gif)
```

### 2) Key Feature Walkthroughs

Keep each clip focused on one action and add one sentence about user value.

#### Add games to your collection

```md
![Search and Add](docs/media/search-add.gif)
```
Find games through IGDB and add them to your collection in a few clicks.

#### Collection Management

```md
![Collection Management](docs/media/collection-manage.gif)
```
Track ownership and status to keep your library organized.

#### Playthrough and Session Logging

```md
![Playthrough and Sessions](docs/media/play-log.gif)
```
Record progress and sessions so your play history stays meaningful over time.

####  Home Dashboard


Quickly log sessions, track your activity, and find your next game — all from a single, streamlined dashboard.
### 3) UI Screenshots (Static)

Use static images for polished states and comparisons.

```md
![Home Screen](docs/media/home.png)
![Collection Detail](docs/media/collection-detail.png)
```

### 4) Full Walkthrough Video (Optional)

Host a longer video on YouTube/Loom and link it:

```md
[Watch full walkthrough](https://your-video-link-here)
```


## Tech Stack

- Java 21
- Spring Boot 4
- Spring MVC + Thymeleaf
- Spring Security
- Spring Data JPA
- H2 (local profile) and PostgreSQL (default profile)
- Maven Wrapper (`./mvnw`)



## Prerequisites

- JDK 21 installed
- No global Maven install required (wrapper included)

## Running Locally

1. Start the app with the local profile:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

2. Open:

- App: [http://localhost:8080](http://localhost:8080)
- H2 console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

The local profile uses file-backed H2 at `./data/playstate-local`.

## Local Default Credentials

From `application-local.properties`:

- User: `local-admin`
- Password: `local-admin`
- Demo user: `demo` / `demo`

## Environment Variables (Default Profile)

The default profile expects PostgreSQL and optional IGDB/demo config:

- `DATABASE_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `SPRING_JPA_HIBERNATE_DDL_AUTO` (defaults to `validate`)
- `PORT` (defaults to `8080`)
- `IGDB_CLIENT_ID` and `IGDB_CLIENT_SECRET` (or Twitch equivalents)
- `SECURITY_USER_NAME`
- `SECURITY_USER_PASSWORD`
- `DEMO_ENABLED`
- `DEMO_USERNAME`
- `DEMO_PASSWORD`

## Build and Test

```bash
./mvnw clean test
./mvnw clean package
```

## Main Routes

- `/` - home
- `/login` and `/register` - authentication
- `/search` - game search and add flow
- `/collection` - collection list
- `/collection/{apiId}` - collection game detail and play log/playthrough actions

## Project Structure

- `src/main/java` - Spring Boot Java source
- `src/main/resources/templates` - Thymeleaf templates
- `src/main/resources/static` - CSS/JS assets
- `src/main/resources/application.properties` - default/prod-style config

