package recorder.domain.services;

import recorder.core.Hasher;
import recorder.core.exceptions.ApiException;
import recorder.core.exceptions.InvalidDataException;
import recorder.core.exceptions.SystemException;
import recorder.domain.models.User;
import recorder.domain.models.query.QUser;
import recorder.domain.requests.UserSignupRequest;

import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

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
        if (existing.isPresent()) throw new InvalidDataException(Map.of("_", "User already exists"));

        var newUser = new User(signup.getEmail(), this.hashPassword(signup.getPassword()));
        newUser.save();

        return newUser;
    }

    private String hashPassword(String password) throws SystemException {
        String hashedPw = null;

        try {
            hashedPw = this.hasher.make(password);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new SystemException(e);
        }

        return hashedPw;
    }
}
