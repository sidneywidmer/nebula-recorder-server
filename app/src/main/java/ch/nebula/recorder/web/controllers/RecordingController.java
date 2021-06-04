package ch.nebula.recorder.web.controllers;

import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.domain.requests.RecordingUploadRequest;
import ch.nebula.recorder.domain.services.RecordingService;
import io.javalin.http.Context;

import javax.inject.Inject;
import javax.validation.Validator;

public class RecordingController extends BaseController {
    private final RecordingService recordingService;

    @Inject
    protected RecordingController(Validator validator, RecordingService recordingService) {
        super(validator);
        this.recordingService = recordingService;
    }

    public void upload(Context ctx) throws ApiException {
        var recordingUploadRequest = this.validate(ctx, RecordingUploadRequest.class);
        recordingService.upload(recordingUploadRequest);

        ctx.status(200);
    }
}
