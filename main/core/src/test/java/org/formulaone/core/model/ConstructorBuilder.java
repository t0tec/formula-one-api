package org.formulaone.core.model;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class ConstructorBuilder {

  private Integer id;
  private String referenceName = "NOT_IMPORTANT";
  private String name = "NOT_IMPORTANT";

  public ConstructorBuilder() {
  }

  public ConstructorBuilder id(Integer id) {
    this.id = id;
    return this;
  }

  public ConstructorBuilder referenceName(String referenceName) {
    this.referenceName = referenceName;
    return this;
  }

  public ConstructorBuilder name(String name) {
    this.name = name;
    return this;
  }

  public Constructor build() {
    Constructor build = Constructor.getBuilder()
        .referenceName(referenceName)
        .name(name)
        .build();

    ReflectionTestUtils.setField(build, "id", id);

    return build;
  }
}
