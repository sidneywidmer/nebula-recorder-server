package recorder.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import io.javalin.http.Context;
import recorder.core.exceptions.ApiException;
import recorder.core.exceptions.InvalidDataException;

import javax.validation.Validator;

public abstract class BaseController {

    protected final Validator validator;

    @Inject
    protected BaseController(Validator validator) {
        this.validator = validator;
    }


    protected Object validate(Context ctx, Class clazz) throws ApiException {
        var objectMapper = new ObjectMapper();
        Object instance = null;

        try {
            instance = objectMapper.readValue(ctx.body(), clazz);
        } catch (JsonProcessingException e) {
            throw new ApiException();
        }
        var violations = this.validator.validate(instance);

        if (!violations.isEmpty()) {
            throw new InvalidDataException(violations);
        }

        return instance;
    }
}
