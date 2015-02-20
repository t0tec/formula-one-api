package org.formulaone.rest.error;

import org.formulaone.core.exception.CircuitNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * This class handles the exceptions thrown by our REST API.
 *
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@ControllerAdvice
public final class RestErrorHandler {

  private static final Logger logger = LoggerFactory.getLogger(RestErrorHandler.class);

  private static final String ERROR_CODE_BAD_URL = "error.bad.url";
  private static final String ERROR_CODE_TYPE_MISMATCH = "error.type.mismatch";
  private static final String ERROR_CODE_CIRCUIT_ENTRY_NOT_FOUND = "error.circuit.entry.not.found";

  private final MessageSource messageSource;

  @Autowired
  public RestErrorHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /**
   * Processes an error that occurs when a no handler is found to process the request
   *
   * @param req           HttpServletRequest provides request information for HTTP servlets
   * @param ex            The exception that was thrown when the circuit entry was not found.
   * @param currentLocale The current locale.
   * @return An error object that contains the error code, status, message and url.
   */
  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  ErrorDto noHandlerFound(HttpServletRequest req, NoHandlerFoundException ex,
                          Locale currentLocale) {
    logger.error("URL does not exist");

    MessageSourceResolvable errorMessageRequest = createSingleErrorMessageRequest(
        ERROR_CODE_BAD_URL
    );

    String errorMessage = messageSource.getMessage(errorMessageRequest, currentLocale);

    String errorURL = req.getRequestURL().toString();

    return new ErrorDto(HttpStatus.BAD_REQUEST, errorMessage, errorURL);
  }

  /**
   * Processes an error that occurs when a TypeMismatchException occurs.
   *
   * @param req           HttpServletRequest provides request information for HTTP servlets
   * @param ex            The exception that was thrown when the circuit entry was not found.
   * @param currentLocale The current locale.
   * @return An error object that contains the error code, status, message and url.
   */
  @ExceptionHandler(TypeMismatchException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  ErrorDto handlerTypeMisMatch(HttpServletRequest req, TypeMismatchException ex,
                          Locale currentLocale) {
    logger.error("Path variable is of wrong type");

    MessageSourceResolvable errorMessageRequest = createSingleErrorMessageRequest(
        ERROR_CODE_TYPE_MISMATCH
    );

    String errorMessage = messageSource.getMessage(errorMessageRequest, currentLocale);

    String errorURL = req.getRequestURL().toString();

    return new ErrorDto(HttpStatus.BAD_REQUEST, errorMessage, errorURL);
  }

  /**
   * Processes an error that occurs when the requested circuit entry is not found.
   *
   * @param req           HttpServletRequest provides request information for HTTP servlets
   * @param ex            The exception that was thrown when the circuit entry was not found.
   * @param currentLocale The current locale.
   * @return An error object that contains the error code, status, message and url.
   */
  @ExceptionHandler(CircuitNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  ErrorDto handleCircuitEntryNotFound(HttpServletRequest req, CircuitNotFoundException ex,
                                      Locale currentLocale) {
    logger.error("Circuit entry was not found by using id: {}", ex.getValue());

    MessageSourceResolvable errorMessageRequest = createSingleErrorMessageRequest(
        ERROR_CODE_CIRCUIT_ENTRY_NOT_FOUND,
        ex.getPropertyName(), ex.getValue()
    );

    String errorMessage = messageSource.getMessage(errorMessageRequest, currentLocale);

    String errorURL = req.getRequestURL().toString();

    return new ErrorDto(HttpStatus.NOT_FOUND, errorMessage, errorURL);
  }

  private DefaultMessageSourceResolvable createSingleErrorMessageRequest(String errorMessageCode,
                                                                         Object... params) {
    return new DefaultMessageSourceResolvable(new String[]{errorMessageCode}, params);
  }
}