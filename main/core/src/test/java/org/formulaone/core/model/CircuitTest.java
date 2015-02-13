package org.formulaone.core.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class CircuitTest {

  private static final String REFERENCE_NAME = "albert_park";
  private static final String NAME = "Albert Park Grand Prix Circuit";

  @Test
  public void build() {
    Circuit built = Circuit.getBuilder().referenceName(REFERENCE_NAME).name(NAME).build();

    assertEquals(REFERENCE_NAME, built.getReferenceName());
    assertEquals(NAME, built.getName());
    assertNull(built.getId());

  }

}
