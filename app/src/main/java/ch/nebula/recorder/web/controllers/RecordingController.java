package ch.nebula.recorder.web.controllers;

import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.domain.requests.RecordingGetOneRequest;
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
        recordingService.upload(ctx.attribute("user"), recordingUploadRequest);

        ctx.status(200);
    }

    public void getAll(Context ctx) throws ApiException {
        var recordings = recordingService.getAll(ctx.attribute("user"));

        ctx.result(recordings);
    }

    public void getOne(Context ctx) throws ApiException {
        var recordingGetOneRequest = this.validate(ctx, RecordingGetOneRequest.class);
        var recording = recordingService.getOne(recordingGetOneRequest.getId());

        ctx.result(recording);
    }
}
