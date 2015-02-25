package org.formulaone.core.exception;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class NotFoundException extends RuntimeException {

  private final Class entityClass;
  private final String propertyName;
  private final Object value;

  public NotFoundException(Class entityClass, String propertyName, Object value) {
    super();
    this.entityClass = entityClass;
    this.propertyName = propertyName;
    this.value = value;
  }

  public Class getEntityClass() {
    return this.entityClass;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public Object getValue() {
    return value;
  }
}
