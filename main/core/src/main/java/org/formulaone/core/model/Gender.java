package org.formulaone.core.model;

import org.formulaone.core.model.persistence.PersistentGenderEnum;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public enum Gender implements PersistentGenderEnum {
  MALE('M'),
  FEMALE('F');

  private final Character id;

  Gender(Character id) {
    this.id = id;
  }

  @Override
  public Character getId() {
    return id;
  }
}
