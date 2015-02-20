package org.formulaone.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.formulaone.core.model.Circuit;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("circuit")
public class CircuitDto {

  @JsonIgnore
  private Long id;

  @JacksonXmlProperty(isAttribute = true)
  @NotEmpty
  private String referenceName;

  private String name;

  private Location location;

  private Double length;

  private Integer turns;

  @JacksonXmlProperty(isAttribute = true)
  @Size(max = Circuit.MAX_LENGTH_URL)
  private String url;

  public CircuitDto() {
  }

  private CircuitDto(Builder builder) {
    this.id = builder.id;
    this.referenceName = builder.referenceName;
    this.name = builder.name;
  }

  public static Builder getBuilder() {
    return new Builder();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public Location getLocation() {
    return this.location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Double getLength() {
    return this.length;
  }

  public void setLength(Double length) {
    this.length = length;
  }

  public Integer getTurns() {
    return this.turns;
  }

  public void setTurns(Integer turns) {
    this.turns = turns;
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
        .append("name", this.name).append("location", location).append("length", this.length)
        .append("turns", this.turns).append("url", this.url)
        .toString();
  }

  /**
   * The builder pattern makes it easier to create objects
   */
  public static class Builder {

    private Long id;
    private String referenceName;
    private String name;

    public Builder() {
    }

    public Builder id(Long id) {
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


    public CircuitDto build() {
      CircuitDto build = new CircuitDto(this);

      return build;
    }
  }
}
