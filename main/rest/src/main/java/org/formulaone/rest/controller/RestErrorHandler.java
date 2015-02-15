package org.formulaone.rest.controller;

import org.formulaone.core.exception.CircuitNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

/**
 * This class handles the exceptions thrown by our REST API.
 *
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@ControllerAdvice
final class RestErrorHandler {

  private static final Logger logger = LoggerFactory.getLogger(RestErrorHandler.class);

  private static final String ERROR_CODE_CIRCUIT_ENTRY_NOT_FOUND = "error.circuit.entry.not.found";

  private final MessageSource messageSource;

  @Autowired
  RestErrorHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /**
   * Processes an error that occurs when the requested circuit entry is not found.
   *
   * @param ex            The exception that was thrown when the circuit entry was not found.
   * @param currentLocale The current locale.
   * @return An error object that contains the error code and message.
   */
  @ExceptionHandler(CircuitNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  ErrorDTO handleCircuitEntryNotFound(CircuitNotFoundException ex, Locale currentLocale) {
    logger.error("Circuit entry was not found by using id: {}", ex.getMessage());

    MessageSourceResolvable errorMessageRequest = createSingleErrorMessageRequest(
        ERROR_CODE_CIRCUIT_ENTRY_NOT_FOUND,
        ex.getMessage()
    );

    String errorMessage = messageSource.getMessage(errorMessageRequest, currentLocale);
    return new ErrorDTO(HttpStatus.NOT_FOUND.name(), errorMessage);
  }

  private DefaultMessageSourceResolvable createSingleErrorMessageRequest(String errorMessageCode,
                                                                         Object... params) {
    return new DefaultMessageSourceResolvable(new String[]{errorMessageCode}, params);
  }


  private String getValidationErrorMessage(FieldError fieldError, Locale currentLocale) {
    String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

    //If the message was not found, return the most accurate field error code instead.
    //You can remove this check if you prefer to get the default error message.
    if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
      String[] fieldErrorCodes = fieldError.getCodes();
      localizedErrorMessage = fieldErrorCodes[0];
    }

    return localizedErrorMessage;
  }
}
