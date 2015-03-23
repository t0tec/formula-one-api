package org.formulaone.core.model;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class DriverBuilder {

  private Integer id;
  private String referenceName = "NOT_IMPORTANT";
  private String forename = "NOT_IMPORTANT";
  private String surname = "NOT_IMPORTANT";

  public DriverBuilder() {
  }

  public DriverBuilder id(Integer id) {
    this.id = id;
    return this;
  }

  public DriverBuilder referenceName(String referenceName) {
    this.referenceName = referenceName;
    return this;
  }

  public DriverBuilder forename(String forename) {
    this.forename = forename;
    return this;
  }

  public DriverBuilder surname(String surname) {
    this.surname = surname;
    return this;
  }

  public Driver build() {
    Driver build = Driver.getBuilder()
        .referenceName(referenceName)
        .forename(forename)
        .surname(surname)
        .build();

    ReflectionTestUtils.setField(build, "id", id);

    return build;
  }
}
