package org.formulaone.core.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class ConstructorTest {

  private static final String REFERENCE_NAME = "mclaren";
  private static final String NAME = "McLaren";

  @Test
  public void build() {
    Constructor built = Constructor.getBuilder().referenceName(REFERENCE_NAME).name(NAME).build();

    assertEquals(REFERENCE_NAME, built.getReferenceName());
    assertEquals(NAME, built.getName());
    assertNull(built.getId());
  }

}
