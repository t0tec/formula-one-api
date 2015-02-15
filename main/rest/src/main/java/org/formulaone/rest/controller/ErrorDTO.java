package org.formulaone.rest.controller;

//import static PreCondition.notEmpty;
//import static PreCondition.notNull;

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

  ErrorDTO(String code, String message) {
//        notNull(code, "Code cannot be null.");
//        notEmpty(code, "Code cannot be empty.");
//
//        notNull(message, "Message cannot be null.");
//        notEmpty(message, "Message cannot be empty");

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
