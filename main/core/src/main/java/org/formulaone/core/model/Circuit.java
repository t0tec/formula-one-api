package org.formulaone.core.model;

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
@Table(name = "circuit")
public final class Circuit implements Serializable {

  public static final int MAX_LENGTH_URL = 500;

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "reference_name", nullable = false, unique = true)
  private String referenceName;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "country")
  private String country;

  @Column(name = "location")
  private String location;

  @Column(name = "lat", precision = 8, scale = 6, columnDefinition = "decimal(8,6)")
  private double latitude;

  @Column(name = "lng", precision = 9, scale = 6, columnDefinition = "decimal(9,6)")
  private double longitude;

  @Column(name = "length", precision = 5, scale = 3, columnDefinition = "decimal(5,3)")
  private double length;

  @Column(name = "turns", columnDefinition = "smallint")
  private int turns;

  @Column(name = "url", length = MAX_LENGTH_URL)
  private String url;

  public Circuit() {
  }

  public Circuit(String referenceName, String name, String country, String location,
                 double latitude,
                 double longitude, double length, int turns, String url) {
    this.referenceName = referenceName;
    this.name = name;
    this.country = country;
    this.location = location;
    this.latitude = latitude;
    this.longitude = longitude;
    this.length = length;
    this.turns = turns;
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

  public Long getId() {
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

  public double getLength() {
    return this.length;
  }

  public int getTurns() {
    return this.turns;
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


    public Circuit build() {
      Circuit build = new Circuit(this);

      return build;
    }
  }
}
