# Gamevaultr

![Gamevaultr logo](docs/media/0gvlogo.png)

![Gamevaultr wordmark on dark](docs/media/gvlogo.png)

**Gamevaultr** is a personal game vault: build your collection, then track playthroughs, sessions, ratings, and notes in one place.

## Live app

The site is fully usable at **[gamevaultr.com](https://gamevaultr.com)**.

Demo login:

```
Username: demo
Password: demopassword
```

## What you can do

- Search IGDB and add games to your collection
- Track ownership, play status, and per-game progress
- Manage playthrough history and your current run
- Log play sessions with notes and reflections
- Record ratings, reviews, and personal notes
- Measure playtime with a built-in session timer

---

## Product preview

### Home — your collection at a glance

![Home](docs/media/Home.png)

![Home — alternate view](docs/media/HomeScreenshot.png)

### Search and add — build your library quickly

![Search and add](docs/media/search-add.gif)

### Collection — organize and manage your games

![Collection](docs/media/collection.gif)

### Game page — pick up where you left off

![Game page](docs/media/game.png)

### Intentions — define how you want to play

![Intentions](docs/media/intentions.png)

### Session logging — record each play session

![Log a play session](docs/media/log-play.png)

![Play history — detailed view](docs/media/log-play-history.png)

![Play history](docs/media/play-history.png)

### Session timer — track playtime in real time

![Session timer](docs/media/timer.png)

### Review and reflect — capture your experience

![Reflect](docs/media/reflect.png)

---

## Tech stack

| Layer | Technology |
| --- | --- |
| Backend | Spring Boot 4 (MVC) |
| Language | Java 21 |
| Build | Maven (`mvn` / `./mvnw`) |
| Data | Spring Data JPA + Hibernate |
| Database | PostgreSQL (default), H2 (local profile) |
| Security | Spring Security |
| UI | Thymeleaf + static assets |
| External API | IGDB (Twitch credentials) |
| Caching | Caffeine (service-level cache) |
