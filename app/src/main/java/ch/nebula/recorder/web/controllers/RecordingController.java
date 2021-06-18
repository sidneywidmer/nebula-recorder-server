package ch.nebula.recorder.web.controllers;

import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.domain.requests.RecordingGetAllRequest;
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

    public String getAll(Context ctx) throws ApiException {
        var recordingGetAllRequest = this.validate(ctx, RecordingGetAllRequest.class);
        recordingService.getAll(recordingGetAllRequest.getUserId());

        ctx.status(200);
        return null;
    }

    public String getOne(Context ctx) throws ApiException {
        var recordingGetOneRequest = this.validate(ctx, RecordingGetOneRequest.class);
        recordingService.getOne(recordingGetOneRequest.getName());

        ctx.status(200);
        return null;
    }
}
