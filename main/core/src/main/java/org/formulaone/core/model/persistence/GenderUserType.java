package org.formulaone.core.model.persistence;

import org.formulaone.core.model.Gender;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class GenderUserType extends PersistentGenderEnumUserType<Gender> {

  @Override
  public Class<Gender> returnedClass() {
    return Gender.class;
  }

}
