package org.formulaone.core.model;

import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@Entity
@Table(name = "driver")
public final class Driver implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "reference_name", nullable = false, unique = true)
  private String referenceName;

  @Column(name = "number")
  private Integer number;

  @Column(name = "code", columnDefinition = "char(3)")
  private String code;

  @Column(name = "forename", nullable = false)
  private String forename;

  @Column(name = "surname", nullable = false)
  private String surname;

  @Temporal(TemporalType.DATE)
  @Column(name = "birthdate")
  private Date birthdate;

  @Column(name = "nationality")
  private String nationality;

  @Type(type = "org.formulaone.core.model.persistence.GenderUserType")
  @Column(name = "gender")
  private Gender gender;

  @Column(name = "url", length = ConstrainConstants.MAX_LENGTH_URL)
  private String url;

  @OneToMany(mappedBy = "driver")
  private Set<LapTime> lapTimes = new HashSet<LapTime>();

  @OneToMany(mappedBy = "driver")
  private Set<PitStop> pitStops = new HashSet<PitStop>();

  @OneToMany(mappedBy = "driver")
  private Set<Qualifying> qualifyings = new HashSet<Qualifying>();

  @OneToMany(mappedBy = "driver")
  private Set<Result> results = new HashSet<Result>();

  @OneToMany(mappedBy = "driver")
  private Set<DriverStandings> driverStandings = new HashSet<DriverStandings>();

  public Driver() {
  }

  public Driver(String referenceName, Integer number, String code, String forename, String surname,
                Date birthdate, String nationality, Gender gender, String url) {
    this.referenceName = referenceName;
    this.number = number;
    this.code = code;
    this.forename = forename;
    this.surname = surname;
    this.birthdate = birthdate;
    this.nationality = nationality;
    this.gender = gender;
    this.url = url;
  }

  private Driver(Builder builder) {
    this.id = builder.id;
    this.referenceName = builder.referenceName;
    this.forename = builder.forename;
    this.surname = builder.surname;
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

  public Integer getNumber() {
    return this.number;
  }

  public String getCode() {
    return this.code;
  }

  public String getForename() {
    return this.forename;
  }

  public String getSurname() {
    return this.surname;
  }

  public Date getBirthdate() {
    return this.birthdate;
  }

  public String getNationality() {
    return this.nationality;
  }

  public Gender getGender() {
    return this.gender;
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

    Driver that = (Driver) o;

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
    private String forename;
    private String surname;

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

    public Builder forename(String forename) {
      this.forename = forename;
      return this;
    }

    public Builder surname(String surname) {
      this.surname = surname;
      return this;
    }

    public Driver build() {
      Driver build = new Driver(this);

      return build;
    }
  }
}
