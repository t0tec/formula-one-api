package org.formulaone.core.exception;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class CircuitNotFoundException extends RuntimeException {

  private final String message;

  public CircuitNotFoundException(String message) {
    super();
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
