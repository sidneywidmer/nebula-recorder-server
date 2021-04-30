# Nebula Server

This is the server component of the Nebula Recorder project. The server handles user authentication and provides a REST API for the desktop client to upload recordings.

## Development

The project uses Java 16.

- Clone repository
- Copy `main/resources/app.example.conf` to `main/resources/app.conf` and fill out your settings
- `gradle run`
- Profit

## Documentation

### Configuration

Different config settings should never be checked in to git. They are in the `main/resources/app.conf` file and loaded by the `ConfigProvider`. You can access them by injecting the `Config` class and then access e.g the server port by `config.getInt("server.port")`. 

You can find more information in the project documentation: https://github.com/lightbend/config

### Dependency Injection

We're using Google Guice to orchestrate all the different dependencies. Guice is kicked off in `App.main` . You can setup your own providers in `providers/Bootstrap`. All the bound and registered classes this way can be used throughout the application via the `@Inject` decorator.

You can find more information in the project documentation: https://github.com/google/guice

### Routing

We are using Javalin as a web framework and as such it also handles the whole routing. The router is registered in `App.start` and you can find it under `web/Router`. It contains all the routes of our application.

You can find more information in the project documentation: https://javalin.io/

### Controllers

Controllers are connected to routes in our `Router`. The must extend `BaseController` and have a `Validator` instance to validate incoming request bodies (json encoded). See more about this in the chapter about "Validation".

### Validation

### Exception handling

### ORM



### Domain logic

