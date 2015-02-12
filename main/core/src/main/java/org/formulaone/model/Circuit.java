package org.formulaone.model;

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
public class Circuit implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private long id;

  @Column(name = "reference_name", nullable = false, unique = true)
  private String referenceName;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "location")
  private String location;

  @Column(name = "country")
  private String country;

  @Column(name = "lat", precision = 8, scale = 6, columnDefinition = "decimal(8,6)")
  private double latitude;

  @Column(name = "lng", precision = 9, scale = 6, columnDefinition = "decimal(9,6)")
  private double longitude;

  @Column(name = "length", precision = 5, scale = 3, columnDefinition = "decimal(5,3)")
  private double length;

  @Column(name = "turns", columnDefinition = "smallint")
  private int turns;

  @Column(name = "url", length = 500)
  private String url;

  public Circuit() {
  }

  public Circuit(String referenceName, String name, String location, String country,
                 double latitude,
                 double longitude, double length, int turns, String url) {
    this.referenceName = referenceName;
    this.name = name;
    this.location = location;
    this.country = country;
    this.latitude = latitude;
    this.longitude = longitude;
    this.length = length;
    this.turns = turns;
    this.url = url;
  }

  public long getId() {
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
}
