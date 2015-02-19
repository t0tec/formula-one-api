package org.formulaone.rest.controller;

import com.fasterxml.jackson.annotation.JsonRootName;

import org.springframework.http.HttpStatus;

/**
 * This class contains the information of an error that occurred when the API tried to perform the
 * operation requested by the client.
 *
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("error")
final class ErrorDto {

  private final HttpStatus httpStatus;
  private final String code;
  private final int status;
  private final String message;

  public ErrorDto(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.code = httpStatus.name();
    this.status = httpStatus.value();
    this.message = message;
  }

  public int getStatus() {
    return this.status;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
