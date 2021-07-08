package ch.nebula.recorder.web.controllers;

import ch.nebula.recorder.core.RecordingType;
import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.InvalidDataException;
import ch.nebula.recorder.domain.models.Recording;
import ch.nebula.recorder.domain.requests.RecordingUploadRequest;
import ch.nebula.recorder.domain.services.RecordingService;
import io.javalin.http.Context;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static java.lang.Long.parseLong;

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

        var recording = recordingService.upload(ctx.attribute("user"), recordingUploadRequest);

        ctx.contentType("application/json").result(recording.toJson());
    }

    public void getAll(Context ctx) throws ApiException {
        var recordings = recordingService.getAll(ctx.attribute("user"));

        var jsonArray = new JSONArray();
        for (Recording recording : recordings) {
            var jsonObject = new JSONObject()
                    .put("id", recording.getId())
                    .put("name", recording.getName())
                    .put("url", recording.getUrl());

            jsonArray.put(jsonObject);
        }

        ctx.contentType("application/json").result(jsonArray.toString(1));
    }

    public void getOne(Context ctx) throws ApiException {
        var id = ctx.pathParam("id");
        var recording = recordingService.getOne(parseLong(id));

        ctx.contentType("application/json").result(recording.toJson());
    }
}
