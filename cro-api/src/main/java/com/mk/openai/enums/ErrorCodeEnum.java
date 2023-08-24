package com.mk.openai.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    SUCCESS(0, "OK"),
    BATCH_PARTIAL_CONTENT_SUCCESS(206, "Partial Content Success"),
    INVALID_ARGUMENT(400, "Invalid Argument param"),
    UNAUTHENTICATED(401, "Unauthenticated"),
    PERMISSION_DENIED(403, "Permission Denied"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    ALREADY_EXISTS(409, "Already Exists"),
    REQUEST_BODY_TOO_LARGE(413, "Request Body Too Large"),
    PARAMETER_OUT_OF_RANGE(415, "Parameter Out Of Range"),
    RESOURCE_EXHAUSTED(429, "Resource Exhausted"),
    RESULT_SIZE_TOO_LARGE(430, "Result size too large"),
    APP_TIMESTAMP_FAIL(497, "App Timestamp Checked Fail"),
    APP_SIGN_FAIL(497, "App Signature Checked Fail"),
    APP_RESOURCE_FAIL(498, "App Resource Permission Denied"),
    APP_API_FAIL(498, "App Api Permission Denied"),
    CLIENT_ERROR(499, "Client Error"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    SERVICE_TIMEOUT(504, "Service Timeout"),
    UNSUPPORTED_OPERATION(601, "Unsupported Operation for Particular Resource"),
    DATA_SERVICE_ERROR(701, "data service ERROR."),
    DATA_SERVICE_EXEC_ERROR(702, "An error occurred when execute query in data service.");

    private final int code;
    private final String msg;

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
