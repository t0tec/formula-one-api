package org.formulaone.core.exception;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class CircuitNotFoundException extends RuntimeException {

  private final String propertyName;
  private final Object value;

  public CircuitNotFoundException(String propertyName, Object value) {
    super();
    this.propertyName = propertyName;
    this.value = value;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public Object getValue() {
    return value;
  }
}
