# Dop

This application is an authorization server with minimal configuration, and is built on the foundation of [Spring Authorization Server](https://github.com/spring-projects/spring-authorization-server)

## Getting Started

1. Start the [compost.yml](https://github.com/vanlinh619/Dop/blob/main/Dop/compose.yml) using Docker to launch the PostgreSQL database and Redis.

```bash
docker compose -f compose.yml up -d
```

2. Make sure Java 21 is installed, then run the project.

3. Use Postman to test the project's APIs: [Dop](https://www.postman.com/planetary-desert-10407/dop/collection/e0rpwvt/dop)
