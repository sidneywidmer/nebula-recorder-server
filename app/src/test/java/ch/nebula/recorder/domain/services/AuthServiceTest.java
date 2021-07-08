package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.BaseTest;
import ch.nebula.recorder.core.Generator;
import ch.nebula.recorder.core.Hasher;
import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.PermissionDeniedException;
import ch.nebula.recorder.domain.requests.LoginRequest;
import ch.nebula.recorder.domain.requests.UserActivateRequest;
import ch.nebula.recorder.domain.requests.UserSignupRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Base64;

public class AuthServiceTest extends BaseTest {
    private final AuthService authService;
    private final UserService userService;

    public AuthServiceTest() {
        var config = ConfigFactory.load("app");
        var verifier = JWT.require(Algorithm.HMAC256(config.getString("auth.jwt-secret"))).build();
        userService = new UserService(new Hasher(), new Generator());

        authService = new AuthService(verifier, userService, config);
    }

    @Test
    public void givenLoginRequest_generateTokenHappyPath() throws ApiException {
        var signupRequest = new UserSignupRequest();
        signupRequest.setEmail("foo@bar.ch");
        signupRequest.setPassword("hunter123");
        var user = userService.create(signupRequest);

        var userActivateRequest = new UserActivateRequest();
        userActivateRequest.setEmail(Base64.getEncoder().encodeToString(user.getEmail().getBytes()));
        userActivateRequest.setActivationCode(Base64.getEncoder().encodeToString(user.getActivationCode().getBytes()));
        userService.activate(userActivateRequest);

        var loginRequest = new LoginRequest();
        loginRequest.setEmail("foo@bar.ch");
        loginRequest.setPassword("hunter123");

        assert ((authService.generateToken(loginRequest) != null));
    }

    @Test
    public void givenLoginRequest_generateTokenThrowsUserIsEmpty() throws ApiException {
        var loginRequest = new LoginRequest();
        loginRequest.setEmail("foo@bar.ch");
        loginRequest.setPassword("hunter123");

        Assertions.assertThrows(PermissionDeniedException.class, () -> authService.generateToken(loginRequest));
    }

    @Test
    public void givenLoginRequest_generateTokenThrowsUserNotActive() throws ApiException {
        var signupRequest = new UserSignupRequest();
        signupRequest.setEmail("foo@bar.ch");
        signupRequest.setPassword("hunter123");
        var user = userService.create(signupRequest);

        var loginRequest = new LoginRequest();
        loginRequest.setEmail("foo@bar.ch");
        loginRequest.setPassword("hunter123");

        Assertions.assertThrows(PermissionDeniedException.class, () -> authService.generateToken(loginRequest));
    }

    @Test
    public void givenHeader_getJWTFromHeaderHappyPath() throws ApiException {
        var signupRequest = new UserSignupRequest();
        signupRequest.setEmail("foo@bar.ch");
        signupRequest.setPassword("hunter123");
        var user = userService.create(signupRequest);

        var userActivateRequest = new UserActivateRequest();
        userActivateRequest.setEmail(Base64.getEncoder().encodeToString(user.getEmail().getBytes()));
        userActivateRequest.setActivationCode(Base64.getEncoder().encodeToString(user.getActivationCode().getBytes()));
        userService.activate(userActivateRequest);

        var loginRequest = new LoginRequest();
        loginRequest.setEmail("foo@bar.ch");
        loginRequest.setPassword("hunter123");

        var generatedToken = authService.generateToken(loginRequest);


        var header = "Bearer " + generatedToken;
        var decodedToken = authService.getJWTFromHeader(header);

        assert ((generatedToken.equals(decodedToken.getToken())));
    }

    @Test
    public void givenHeader_getJWTFromHeaderThrowsNoToken() throws ApiException {
        var signupRequest = new UserSignupRequest();
        signupRequest.setEmail("foo@bar.ch");
        signupRequest.setPassword("hunter123");
        var user = userService.create(signupRequest);

        var userActivateRequest = new UserActivateRequest();
        userActivateRequest.setEmail(Base64.getEncoder().encodeToString(user.getEmail().getBytes()));
        userActivateRequest.setActivationCode(Base64.getEncoder().encodeToString(user.getActivationCode().getBytes()));
        userService.activate(userActivateRequest);

        var generatedToken = "";

        var header = "Bearer " + generatedToken;
        Assertions.assertThrows(PermissionDeniedException.class, () -> authService.getJWTFromHeader(header));
    }

    @Test
    public void givenHeader_getJWTFromHeaderThrowsNoBearer() throws ApiException {
        var signupRequest = new UserSignupRequest();
        signupRequest.setEmail("foo@bar.ch");
        signupRequest.setPassword("hunter123");
        var user = userService.create(signupRequest);

        var userActivateRequest = new UserActivateRequest();
        userActivateRequest.setEmail(Base64.getEncoder().encodeToString(user.getEmail().getBytes()));
        userActivateRequest.setActivationCode(Base64.getEncoder().encodeToString(user.getActivationCode().getBytes()));
        userService.activate(userActivateRequest);

        var loginRequest = new LoginRequest();
        loginRequest.setEmail("foo@bar.ch");
        loginRequest.setPassword("hunter123");

        var generatedToken = authService.generateToken(loginRequest);

        var header = "NoBearer " + generatedToken;
        Assertions.assertThrows(PermissionDeniedException.class, () -> authService.getJWTFromHeader(header));
    }

    @Test
    public void givenHeader_getJWTFromHeaderThrowsVerificationException() {
        var header = "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        Assertions.assertThrows(PermissionDeniedException.class, () -> authService.getJWTFromHeader(header));
    }

    @Test
    public void givenHeader_getJWTFromHeaderThrowsTokenIsNull() {
        Assertions.assertThrows(PermissionDeniedException.class, () -> authService.getJWTFromHeader(null));
    }
}
