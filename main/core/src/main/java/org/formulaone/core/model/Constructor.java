package org.formulaone.core.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@Entity
@Table(name = "constructors")
public final class Constructor implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "constructorId")
  private Integer id;

  @Column(name = "constructorRef", nullable = false)
  private String referenceName;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "nationality")
  private String country;

  @Column(name = "url", length = ConstrainConstants.MAX_LENGTH_URL)
  private String url;

  @OneToMany(mappedBy = "constructor")
  private Set<Qualifying> qualifying = new HashSet<Qualifying>();

  @OneToMany(mappedBy = "constructor")
  private Set<ConstructorStandings> constructorStandings = new HashSet<ConstructorStandings>();

  @OneToMany(mappedBy = "constructor")
  private Set<Result> results = new HashSet<Result>();

  public Constructor() {
  }

  public Constructor(String referenceName, String name, String country, String url) {
    this.referenceName = referenceName;
    this.name = name;
    this.country = country;
    this.url = url;
  }

  private Constructor(Builder builder) {
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

  public String getCountry() {
    return this.country;
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

    Constructor that = (Constructor) o;

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


    public Constructor build() {
      Constructor build = new Constructor(this);

      return build;
    }
  }
}
