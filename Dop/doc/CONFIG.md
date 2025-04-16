# Advanced Configuration
By default, Dop provides available configurations, so you don't need to make any changes to start the project for testing.
However, in a production environment, you’ll need to adjust some configurations to suit your business requirements and ensure security.

## Configure Database in Docker
You need to change the PostgreSQL username and password in the [compose.yml](https://github.com/vanlinh619/Dop/blob/main/Dop/compose.yml) file.
Redis should also be set to private for security purposes.

