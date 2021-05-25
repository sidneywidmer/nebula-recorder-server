package ch.nebula.recorder.domain.services;

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

    @Inject
    public UserService(Hasher hasher) {
        this.hasher = hasher;
    }

    /**
     * Try to create a new user by also validating if the given email is unique.
     */
    public User create(UserSignupRequest signup) throws SystemException, ApiException {
        var existing = new QUser().email.equalTo(signup.getEmail()).findOneOrEmpty();
        if (existing.isPresent()) {
            throw new InvalidDataException(Map.of("_", "User already exists"));
        }

        var newUser = new User(signup.getEmail(), this.hashPassword(signup.getPassword()));
        newUser.save();

        return newUser;
    }

    /**
     * Update whenActivated on user if activationCode is correct.
     */
    public void activate(UserActivateRequest activate) throws SystemException, ApiException {
        //query anpassen, so dass der user nur aktiviert wird, wenn er auch noch nicht aktiviert war
        //email.equalTo
        //activateWhenIsNUll
        //findOneOrEmpty
        Optional<User> optionalUser = new QUser().email.equalTo(activate.getEmail()).findOneOrEmpty();
        if (optionalUser.isEmpty()) {
            throw new InvalidDataException(Map.of("_", "User doesnt exist"));
        }

        User user = optionalUser.get();
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
