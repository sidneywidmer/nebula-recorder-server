package ch.nebula.recorder.web.controllers;

import ch.nebula.recorder.core.RecordingType;
import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.InvalidDataException;
import ch.nebula.recorder.domain.requests.RecordingGetOneRequest;
import ch.nebula.recorder.domain.requests.RecordingUploadRequest;
import ch.nebula.recorder.domain.services.RecordingService;
import io.javalin.http.Context;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class RecordingController extends BaseController {
    private final RecordingService recordingService;

    @Inject
    protected RecordingController(Validator validator, RecordingService recordingService) {
        super(validator);
        this.recordingService = recordingService;
    }

    /**
     * This controller method is little more verbose since it's the only one not using
     * a JSON body but a normal form-multipart POST request.
     */
    public void upload(Context ctx) throws ApiException {
        var recordingUploadRequest = new RecordingUploadRequest();
        try {
            recordingUploadRequest.setRecording(ctx.uploadedFile("recording"));
            recordingUploadRequest.setName(ctx.formParam("name"));
            recordingUploadRequest.setType(RecordingType.valueOf(ctx.formParam("type")));
            recordingUploadRequest.setDescription(ctx.formParam("description"));
        } catch (IllegalArgumentException e) {
            throw new ApiException();
        }

        Set<ConstraintViolation<Object>> violations = this.validator.validate(recordingUploadRequest);

        if (!violations.isEmpty()) {
            throw new InvalidDataException(violations);
        }

        recordingService.upload(ctx.attribute("user"), recordingUploadRequest);

        ctx.status(200);
    }

    public void getAll(Context ctx) throws ApiException {
        var recordings = recordingService.getAll(ctx.attribute("user"));

        ctx.result(recordings);
    }

    public void getOne(Context ctx) throws ApiException {
        var recordingGetOneRequest = this.validateJson(ctx, RecordingGetOneRequest.class);
        var recording = recordingService.getOne(recordingGetOneRequest.getId());

        ctx.result(recording);
    }

    /**
     * Tries to map the given ctx.body to our clazz and if this succeeds we trigger
     * bean validation. If all goes well a clazz instance is returned.
     */
    protected <T> T validateUpload(Context ctx, Class<T> clazz) throws ApiException {
        return validateData(ctx.body(), clazz);
    }
}
