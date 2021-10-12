package com.microsip.articulos.response;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
public class GenericResponse {

    Integer responseStatus;
    String responseError;
    Object result;

    /**
     *
     * @param result
     * @return
     */
    public static GenericResponse getOkResponse(Object result) {
        GenericResponse response = new GenericResponse();
        response.setResponseStatus(200);
        response.setResponseError("");
        response.setResult(result);
        return response;
    }

    /**
     *
     * @param code
     * @param message
     * @return
     */
    public static GenericResponse getErrorResponse(Integer code, String message) {
        GenericResponse response = new GenericResponse();
        response.setResponseStatus(code);
        response.setResponseError(message);
        return response;
    }

    /**
     * Representacion del Objeto en formato JSON.
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

