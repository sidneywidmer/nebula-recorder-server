package recorder.providers;


import com.google.inject.Provider;

import javax.validation.Validation;
import javax.validation.Validator;

public class ValidationProvider implements Provider<Validator> {
    @Override
    public Validator get() {
        var factory = Validation.buildDefaultValidatorFactory();

        return factory.getValidator();
    }
}
