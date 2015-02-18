package org.formulaone.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

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

  @NotEmpty
  private String referenceName;

  private String name;

  private String location;

  private String country;

  private double latitude;

  private double longitude;

  private Double length;

  private Integer turns;

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

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public double getLatitude() {
    return this.latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return this.longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
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
        .append("name", this.name).append("country", this.country).append("location", location)
        .append("latitude", this.latitude).append("longitude", this.longitude)
        .append("length", this.length).append("turns", this.turns).append("url", this.url)
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
