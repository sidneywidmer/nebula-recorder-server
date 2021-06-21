package ch.nebula.recorder.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import io.javalin.http.Context;
import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.InvalidDataException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Set;

public abstract class BaseController {
    protected final Validator validator;

    @Inject
    protected BaseController(Validator validator) {
        this.validator = validator;
    }

    /**
     * Tries to map the given ctx.body to our clazz and if this succeeds we trigger
     * bean validation. If all goes well a clazz instance is returned.
     */
    protected <T> T validateJson(Context ctx, Class<T> clazz) throws ApiException {
        return validateData(ctx.body(), clazz);
    }

    /**
     * Same as validate, but instead of trying to get the json from the ctx body
     * we convert all GET parameters to json first. Since javalin supports
     * query param lists we need to prepare our data before using it -
     * in our scenario lists are never used or expected.
     */
    protected <T> T validateQuery(Context ctx, Class<T> clazz) throws ApiException {
        var data = new HashMap<String, String>();
        ctx.queryParamMap().forEach((key, values) -> data.put(key, values.get(0)));

        try {
            var json = new ObjectMapper().writeValueAsString(data);
            return validateData(json, clazz);
        } catch (JsonProcessingException e) {
            throw new ApiException();
        }
    }

    protected <T> T validateData(String data, Class<T> clazz) throws ApiException {
        var objectMapper = new ObjectMapper();
        T instance = null;

        try {
            instance = objectMapper.readValue(data, clazz);
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
