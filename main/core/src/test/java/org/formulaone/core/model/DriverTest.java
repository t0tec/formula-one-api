package org.formulaone.core.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class DriverTest {

  private static final String REFERENCE_NAME = "hamilton";
  private static final String FORENAME = "Lewis";
  private static final String SURNAME = "Hamilton";

  @Test
  public void build() {
    Driver built = Driver.getBuilder().referenceName(REFERENCE_NAME).forename(FORENAME)
        .surname(SURNAME).build();

    assertEquals(REFERENCE_NAME, built.getReferenceName());
    assertEquals(FORENAME, built.getForename());
    assertEquals(SURNAME, built.getSurname());
    assertNull(built.getId());
  }

}
