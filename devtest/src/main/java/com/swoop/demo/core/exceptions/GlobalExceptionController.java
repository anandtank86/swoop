/*
 * Copyright (c) 2017, WestJet (Swoop-ULCC) Inc
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.swoop.demo.core.exceptions;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 *
 * @author zo061e
 */
@PropertySource("classpath:application.properties")
@ControllerAdvice
public class GlobalExceptionController {

    static Logger log = LogManager.getLogger();

    @Autowired
    Environment env;

    /**
     *
     */
    public class ErrorResponse {

        private String code;
        private String message;
        private String details;

        /**
         *
         * @return
         */
        public String getCode() {
            return code;
        }

        /**
         *
         * @param code
         */
        public void setCode(String code) {
            this.code = code;
        }

        /**
         *
         * @return
         */
        public String getMessage() {
            return message;
        }

        /**
         *
         * @param message
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         *
         * @return
         */
        public String getDetails() {
            return details;
        }

        /**
         *
         * @param details
         */
        public void setDetails(String details) {
            this.details = details;
        }

        @Override
        public String toString() {
            return "ErrorResponse{" + "code=" + code + ", message=" + message + ", details=" + details + '}';
        }
    }

    /**
     * Exception class to handle all application exceptions HTTP Status will
     * come from the thrown exception classes
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(DEMOException.class)
    @ResponseBody
    public Object cshExceptionHandler(HttpServletResponse response, DEMOException e) {
        // Set the HTTP status
        HttpStatus responseStatus = resolveAnnotatedResponseStatus(e);
        response.setStatus(responseStatus.value());

        // Set the error details
        ErrorResponse error = new ErrorResponse();
        error.setCode(e.getCode());
        error.setMessage(e.getMessage());
        error.setDetails(e.getError());

        log.error(error.toString());

        return createJSONResponse(error);
    }

    /**
     * Exception class to handle calls to invalid REST end points
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public Object noHandlerFoundExceptionHandler(HttpServletResponse response, NoHandlerFoundException e) {
        // Set the HTTP status
        response.setStatus(HttpStatus.NOT_IMPLEMENTED.value());

        // Set the error details
        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.NOT_IMPLEMENTED.toString());
        error.setMessage("This method is not implemented by the server");

        log.error(error.toString());

        return createJSONResponse(error);
    }

    /**
     * Exception class to handle calls to invalid REST methods
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Object methodNotSupportedExceptionHandler(HttpServletResponse response, HttpRequestMethodNotSupportedException e) {
        // Set the HTTP status
        response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());

        // Set the error details
        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.METHOD_NOT_ALLOWED.toString());
        error.setMessage("This method is not supported by the server");

        log.error(error.toString());

        return createJSONResponse(error);
    }

    /**
     * Exception class to handle all uncaught exceptions
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object unhandledExceptionHandler(HttpServletResponse response, Exception e) {
        // Set the HTTP status
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        // Set the error details
        ErrorResponse error = new ErrorResponse();

        if (e instanceof HttpMessageConversionException
                || e instanceof MethodArgumentTypeMismatchException) {
            //if there is exception thrown by Spring while converting JSON to JAVA type
            response.setStatus(org.springframework.http.HttpStatus.BAD_REQUEST.value());
            error.setCode(String.valueOf(org.springframework.http.HttpStatus.BAD_REQUEST.value()));
            error.setMessage("Http Message Conversion Exception");
            error.setDetails("Could not convert http message to target type. " + e.getMessage());
        } else if (e instanceof MissingServletRequestParameterException) {
            //if there is exception thrown by Spring while converting JSON to JAVA type
            response.setStatus(org.springframework.http.HttpStatus.BAD_REQUEST.value());
            error.setCode(MessageConstants.MANDATORY_PARAMETER_ERROR);
            error.setMessage("Missing params in request");
            error.setDetails( e.getMessage());
        } else {
            log.error("General exception received with stacktrace - " + getStacktrace(e.getStackTrace()));
            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            error.setMessage("Internal server error");
            error.setDetails(e.getMessage());
        }

        log.error(error.toString());

        return createJSONResponse(error);
    }

    /**
     * Get the HTTP response code from the custom exception classes
     *
     * @param exception
     * @return
     */
    HttpStatus resolveAnnotatedResponseStatus(Exception exception) {
        ResponseStatus annotation = findMergedAnnotation(exception.getClass(), ResponseStatus.class);
        if (annotation != null) {
            return annotation.value();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * Create JSON response envelop
     *
     * @param obj
     * @return
     */
    private Object createJSONResponse(Object obj) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("error", obj);
        return jsonObj;
    }

    private static String getStacktrace(StackTraceElement[] stackTraceElements) {

        StringBuilder strBuf = new StringBuilder();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            strBuf.append(stackTraceElement.toString()).append("\n");

        }
        return strBuf.toString();
    }

}
