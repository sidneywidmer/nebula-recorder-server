package recorder.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import io.javalin.http.Context;
import recorder.core.exceptions.ApiException;
import recorder.core.exceptions.InvalidDataException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public abstract class BaseController {

    protected final Validator validator;

    @Inject
    protected BaseController(Validator validator) {
        this.validator = validator;
    }


    protected <T> T validate(Context ctx, Class<T> clazz) throws ApiException {
        var objectMapper = new ObjectMapper();
        T instance = null;

        try {
            instance = objectMapper.readValue(ctx.body(), clazz);
        } catch (JsonProcessingException e) {
            throw new ApiException();
        }

        Set<ConstraintViolation<Object>> violations = this.validator.validate(instance);

        if (!violations.isEmpty()) {
            throw new InvalidDataException(violations);
        }


        return instance;
    }
}
