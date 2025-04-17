# Advanced Configuration

By default, Dop provides available configurations, so you don't need to make any changes to start the project for testing.

However, in a production environment, you’ll need to adjust some configurations to suit your business requirements and ensure security.

## Configure Database In Docker

You need to change the PostgreSQL `username` and `password` in the [compose.yml](https://github.com/vanlinh619/Dop/blob/main/Dop/compose.yml) file.

Redis should also be set to private for security purposes.

## How To Configure Application Properties

The default environment for Dop is set to 'dev' in the [application.properties](https://github.com/vanlinh619/Dop/blob/main/Dop/src/main/resources/application.properties) file.

Switch to the `local` environment to use alternative properties.

```properties
spring.profiles.active=local
```
You need to create a new file named `application-local.properties` as well. Then, copy all properties from the [application-dev.properties](https://github.com/vanlinh619/Dop/blob/main/Dop/src/main/resources/application-dev.properties) file.

## How To Configuring The Datasource For Dop

In the `application-local.properties` file, you need to change the `username` and `password` for the datasource as mentioned in the [Configure Database In Docker](#how-to-configure-application-properties) section.

```properties
dop.setting.datasource.username=<username>
dop.setting.datasource.password=<password>
```

## How To Change The Username And Password For Admin User

Before starting the project, you need to change the `username` and `password` for the Admin user first. This user is used by default to manage the Admin page and has the highest permissions.

```properties
dop.user.admin.username=<username>
dop.user.admin.password=<password>
```
