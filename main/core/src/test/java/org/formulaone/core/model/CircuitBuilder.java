package org.formulaone.core.model;

import org.springframework.test.util.ReflectionTestUtils;

public class CircuitBuilder {

  private Long id;
  private String referenceName = "NOT_IMPORTANT";
  private String name = "NOT_IMPORTANT";

  public CircuitBuilder() {
  }

  public CircuitBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public CircuitBuilder referenceName(String referenceName) {
    this.referenceName = referenceName;
    return this;
  }

  public CircuitBuilder name(String name) {
    this.name = name;
    return this;
  }

  public Circuit build() {
    Circuit build = Circuit.getBuilder()
        .referenceName(referenceName)
        .name(name)
        .build();

    ReflectionTestUtils.setField(build, "id", id);

    return build;
  }
}
