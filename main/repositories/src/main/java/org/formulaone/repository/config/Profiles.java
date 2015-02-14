package org.formulaone.repository.config;

/**
 * This class defines the Spring profiles used in the project. The idea behind this class is that it
 * helps us to avoid typos when we are using these profiles. At the moment there are two profiles
 * which are described in the following: <ul> <li>The APPLICATION profile is used when we run our
 * application.</li> <li>The INTEGRATION_TEST profile is used when we run the integration
 * tests of our application.</li> </ul>
 *
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class Profiles {

  public static final String APPLICATION = "application";
  public static final String INTEGRATION_TEST = "integration-test";

  /**
   * Prevent instantiation.
   */
  private Profiles() {
  }
}
