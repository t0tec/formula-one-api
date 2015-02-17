package org.formulaone.rest.controller;

/**
 * This class contains the information of an error that occurred when the API tried to perform the
 * operation requested by the client.
 *
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
final class ErrorDTO {

  private final String code;
  private final String message;

  public ErrorDTO(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
