package org.formulaone.rest.controller;

import org.formulaone.service.dto.CircuitDto;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class CircuitDtoBuilder {

  private Long id;
  private String referenceName = "NOT_IMPORTANT";
  private String name = "NOT_IMPORTANT";

  public CircuitDtoBuilder() {
  }

  public CircuitDtoBuilder id(Long id) {
    this.id = id;
    return this;
  }

  public CircuitDtoBuilder referenceName(String referenceName) {
    this.referenceName = referenceName;
    return this;
  }

  public CircuitDtoBuilder name(String name) {
    this.name = name;
    return this;
  }

  public CircuitDto build() {
    CircuitDto build = CircuitDto.getBuilder()
        .referenceName(referenceName)
        .name(name)
        .build();

    ReflectionTestUtils.setField(build, "id", id);

    return build;
  }
}
