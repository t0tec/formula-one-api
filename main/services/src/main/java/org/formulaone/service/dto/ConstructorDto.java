package org.formulaone.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.formulaone.core.model.ConstrainConstants;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("constructor")
public class ConstructorDto {

  @JsonIgnore
  private Integer id;

  @JacksonXmlProperty(isAttribute = true)
  @NotEmpty
  private String referenceName;

  private String name;

  private String nationality;

  @JacksonXmlProperty(isAttribute = true)
  @Size(max = ConstrainConstants.MAX_LENGTH_URL)
  private String url;

  public ConstructorDto() {
  }

  private ConstructorDto(Builder builder) {
    this.id = builder.id;
    this.referenceName = builder.referenceName;
    this.name = builder.name;
  }

  public static Builder getBuilder() {
    return new Builder();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getReferenceName() {
    return this.referenceName;
  }

  public void setReferenceName(String referenceName) {
    this.referenceName = referenceName;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNationality() {
    return this.nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("referenceName", this.referenceName)
        .append("name", this.name).append("nationality", this.nationality)
        .append("url", this.url).toString();
  }

  /**
   * The builder pattern makes it easier to create objects
   */
  public static class Builder {

    private Integer id;
    private String referenceName;
    private String name;

    public Builder() {
    }

    public Builder id(Integer id) {
      this.id = id;
      return this;
    }

    public Builder referenceName(String referenceName) {
      this.referenceName = referenceName;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public ConstructorDto build() {
      ConstructorDto build = new ConstructorDto(this);

      return build;
    }
  }
}
