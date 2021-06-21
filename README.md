# Nebula Server

This is the server component of the Nebula Recorder project. The server handles user authentication and provides a REST API for the desktop client to upload recordings.

## Development

The project uses Java 16.

- Clone repository
- Copy `main/resources/app.example.conf` to `main/resources/app.conf` and fill out your settings
- Build frontend with `cd frontend && npm install && npm run build` this will copy the standalone FE in your `resources/public` folder, no template engine needed.
- `gradle build && gradle run`
- Profit

## Documentation

### General concept

To really understand what happens inside of our application, we want to avoid heavy frameworks like Spring and instead follow the good old Linux Philosophy: Do one thing and do it right! In this spirit we're using a few key libraries (pulled in as gradle dependencies) and wire them together to have our own little framework - doing exactly what we want, and only that. 

Find a short paragraph about how some of the core functionality is implemented and how its intended to be used bellow.

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

Controllers are connected to routes in our `Router`. The must extend `BaseController` and should be singletons (with the @Singleton annotation) and have a `Validator` instance to validate incoming request bodies (json encoded). See more about this in the chapter about "Validation".

### Validation

Validation is done via [bean validation](https://beanvalidation.org/) which is the defacto standard in the java world. To validate incoming json data, you should create a `FooRequest` in `domain/requests` with all the necessary validation annotations. A good example could be a `UserSignupRequest` or as `UserLoginRequest`. 

En example flow could look like this:

1. Define your bean
2. Trigger validation in your controller with `var userSignup = (UserSignupRequest) this.validate(ctx, UserSignupRequest.class);`
3. The validate method automatically tries to map the request body to the `UserSignupRequest` bean and trigger the validation on the `Validator` instance (which is automatically available on the `BaseController`)
4. An empty `ApiException` is thrown if the json is invalid or if the given data could not be mapped to the request class
5. A `InvalidDataException` is raised which contains any of the found rule violations (e.g invalid email field)

Instead of `this.validate` you could also use `this.validateQuery` to the same thing except it would try to map your query params to the request class.

### Exception handling

Error handling is done via the `ErrorHandler` which is registered in `App.start` and you can find it under `web/ErrorHandler`. This is the same principle as with the `Router`. The ErrorHandler defines how given Exceptions should be handled and you can extend it as you wish with other custom Exceptions. All exceptions should at least extend from `ApiException` or `SystemException` 

- ApiException: Handles domain logic or data errors like invalid email or file to big.
- SystemException: Handles everything else. If any of your serves catch any `Exception` they should catch it and re-throw wrapped in a SystemExceptions

### ORM

As an ORM we're using ebean including the ebean-migrations. Models are located in `domain/models` and should always extend `BaseModel` which implements an id, created and updated field. An example select looks like this `new QUser().email.equalTo(signup.getEmail()).findOneOrEmpty()` - notice the `QUser` object which is automatically generated by ebean. 

If you create new models or change existing ones you need to trigger `gradle makeMigrations` - you can find the resulting migrations in `resources/dbmigrations` . Open migrations are automatically applied on server startup.

You can find more information in the project documentation: https://ebean.io/

### Authentication

We implement the Javalin `AccessManager` as `JWTAccessManager`. If a route is defined like this `path("api/auth/check", () -> get(authController::check, roles(AUTHENTICATED)));` (notice the `roles`) the AccessManager tries to get a JWT token from the `Authentication` header and validate it. The token contains a `user` claim in form of the users id and we check if said user is still active. In the controller you then have access to the User object `User user = ctx.attribute("user");` since it gets set as attribute on the Javalin context. If anything fails along the way (e.g getting the token, validating the token, getting the user, e.t.c) a `PermissionDeniedException` is raised.