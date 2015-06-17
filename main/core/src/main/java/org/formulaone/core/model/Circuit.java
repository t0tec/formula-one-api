package org.formulaone.core.model;

import org.dozer.Mapping;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@Entity
@Table(name = "circuits")
public final class Circuit implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "circuitId")
  private Integer id;

  @Column(name = "circuitRef", nullable = false, unique = true)
  private String referenceName;

  @Column(name = "name", nullable = false)
  private String name;

  @Mapping("location.country")
  @Column(name = "country")
  private String country;

  @Mapping("location.locality")
  @Column(name = "location")
  private String location;

  @Mapping("location.latitude")
  @Column(name = "lat", precision = 8, scale = 6) // columnDefinition = "decimal(8,6)"
  private float latitude;

  @Mapping("location.longitude")
  @Column(name = "lng", precision = 9, scale = 6) //  columnDefinition = "decimal(9,6)"
  private float longitude;

  @Column(name = "url", length = ConstrainConstants.MAX_LENGTH_URL)
  private String url;

  public Circuit() {
  }

  public Circuit(String referenceName, String name, String country, String location,
                 float latitude, float longitude, String url) {
    this.referenceName = referenceName;
    this.name = name;
    this.country = country;
    this.location = location;
    this.latitude = latitude;
    this.longitude = longitude;
    this.url = url;
  }

  private Circuit(Builder builder) {
    this.id = builder.id;
    this.referenceName = builder.referenceName;
    this.name = builder.name;
  }

  public static Builder getBuilder() {
    return new Builder();
  }

  public Integer getId() {
    return this.id;
  }

  public String getReferenceName() {
    return this.referenceName;
  }

  public String getName() {
    return this.name;
  }

  public String getLocation() {
    return this.location;
  }

  public String getCountry() {
    return this.country;
  }

  public double getLatitude() {
    return this.latitude;
  }

  public double getLongitude() {
    return this.longitude;
  }

  public String getUrl() {
    return this.url;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    Circuit that = (Circuit) o;

    if (!this.referenceName.equals(that.referenceName)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return this.referenceName.hashCode();
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


    public Circuit build() {
      Circuit build = new Circuit(this);

      return build;
    }
  }
}
