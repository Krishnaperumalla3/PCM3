/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc, Version 6.1 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pe.pcm.exception;

import com.pe.pcm.utils.PCMConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static com.pe.pcm.constants.ProfilesConstants.SSO_SSP_SEAS;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public static CommunityManagerServiceException notFound(String message) {
        return new CommunityManagerServiceException(NOT_FOUND.value(), message + " not found.");
    }

    public static CommunityManagerServiceException conflict(String message) {
        return new CommunityManagerServiceException(CONFLICT.value(), message + " already exists.");
    }

    public static CommunityManagerServiceException unknownProtocol() {
        return new CommunityManagerServiceException(NOT_IMPLEMENTED.value(), "protocol not implemented");
    }

    public static CommunityManagerServiceException internalServerError(String message) {
        return new CommunityManagerServiceException(INTERNAL_SERVER_ERROR.value(), message);
    }

    public static CommunityManagerServiceException internalServerError(String message, Exception exception) {
        return new CommunityManagerServiceException(INTERNAL_SERVER_ERROR.value(), message, exception);
    }

    public static CommunityManagerServiceException badRequest(String message) {
        return new CommunityManagerServiceException(BAD_REQUEST.value(), "Please Provide a valid data for " + message);
    }

    public static CommunityManagerServiceException badRequestCustom(String message) {
        return new CommunityManagerServiceException(BAD_REQUEST.value(), message);
    }

    public static CommunityManagerServiceException customError(int statusCode, String message) {
        return new CommunityManagerServiceException(statusCode, message);
    }


    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorMessage> badRequestHandler(Exception e) {
        String errorDescription;
        if (e instanceof MethodArgumentNotValidException) {
            errorDescription = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage();
            LOGGER.error("Bad Request: {}", errorDescription);
        } else {
            errorDescription = "Required request parameters are missing/type mismatching";
            LOGGER.error("Bad Request ", e);
        }
        return ResponseEntity.status(BAD_REQUEST.value()).body(new ErrorMessage(BAD_REQUEST.value(), errorDescription));
    }

    @ExceptionHandler(CommunityManagerServiceException.class)
    public ResponseEntity<ErrorMessage> internalServerErrorHandler(CommunityManagerServiceException e) {
        String message;
        if (hasText(e.getErrorMessage())) {
            message = e.getErrorMessage();
        } else {
            message = e.getMessage();
        }
        LOGGER.error("Exception occurred: {}", e.getErrorMessage());
        LOGGER.debug("Exception occurred", e);
        ErrorMessage msg = new ErrorMessage(e.getStatusCode(), message);
        return ResponseEntity.status(e.getStatusCode()).body(msg);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler({CommunityManagerTokenException.class, RuntimeException.class})
    public ResponseEntity<ErrorMessage> runtimeExceptionHandler(RuntimeException e) {
        LOGGER.error("Runtime Exception: ", e);
        ErrorMessage message = new ErrorMessage(INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR.value()).body(message);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessIsDenied(HttpServletResponse response, AccessDeniedException e) {
        LOGGER.info("Forbidden access: {}", e.getMessage());
        String[] profiles = response.getHeader(PCMConstants.PROFILE_NAMES).split(",");
        int entityStatusCode;
        if (Arrays.asList(profiles).contains(SSO_SSP_SEAS)) {
            entityStatusCode = NOT_ACCEPTABLE.value();
        } else {
            entityStatusCode = FORBIDDEN.value();
        }
        return ResponseEntity.status(entityStatusCode).body(new ErrorMessage(FORBIDDEN.value(), "Forbidden access to this resource"));
    }

}
