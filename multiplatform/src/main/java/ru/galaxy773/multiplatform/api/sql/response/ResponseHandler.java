package ru.galaxy773.multiplatform.api.sql.response;

public interface ResponseHandler<H, R> {

    R handleResponse(H handle) throws Exception;
}
