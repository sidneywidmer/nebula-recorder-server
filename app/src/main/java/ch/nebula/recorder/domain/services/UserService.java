package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.core.Generator;
import ch.nebula.recorder.core.Hasher;
import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.InvalidDataException;
import ch.nebula.recorder.core.exceptions.SystemException;
import ch.nebula.recorder.domain.models.User;
import ch.nebula.recorder.domain.models.query.QUser;
import ch.nebula.recorder.domain.requests.LoginRequest;
import ch.nebula.recorder.domain.requests.UserActivateRequest;
import ch.nebula.recorder.domain.requests.UserSignupRequest;

import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

public class UserService {
    private final Hasher hasher;
    private final Generator generator;

    @Inject
    public UserService(Hasher hasher, Generator generator) {
        this.hasher = hasher;
        this.generator = generator;
    }

    /**
     * Try to create a new user by also validating if the given email is unique.
     * If the user is created an activationCode will be generated.
     */
    public User create(UserSignupRequest signup) throws SystemException, ApiException {
        var existing = new QUser().email.equalTo(signup.getEmail()).findOneOrEmpty();
        if (existing.isPresent()) {
            throw new InvalidDataException(Map.of("_", "User already exists"));
        }

        var newUser = new User(signup.getEmail(), this.hashPassword(signup.getPassword()));

        String activationCode = generator.generateActivationCode();
        newUser.setActivationCode(activationCode);
        newUser.save();

        return newUser;
    }

    /**
     * Try to activate a user by also validating if the given user is not yet activated and the activationCode is valid.
     */
    public void activate(UserActivateRequest activate) throws SystemException, ApiException {
        User user = new QUser().email.equalTo(activate.getEmail()).findOne();
        if (user == null) {
            throw new InvalidDataException(Map.of("_", "User doesnt exist"));
        }
        if (user.getWhenActivated() != null) {
            throw new InvalidDataException(Map.of("-", "User already activated"));
        }
        if (!user.getActivationCode().equals(activate.getActivationCode())) {
            throw new InvalidDataException(Map.of("_", "Wrong activation code"));
        }

        user.setWhenActivated(Instant.now());
        user.update();
    }

    public Optional<User> byEmailAndPassword(LoginRequest login) {
        var user = new QUser()
                .email.equalTo(login.getEmail())
                .findOneOrEmpty();

        if (user.isEmpty()) {
            return Optional.empty();
        }

        if (!hasher.check(login.getPassword(), user.get().getPassword())) {
            return Optional.empty();
        }

        return user;
    }

    private String hashPassword(String password) throws SystemException {
        String hashedPw;

        try {
            hashedPw = hasher.make(password);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new SystemException(e);
        }

        return hashedPw;
    }
}
