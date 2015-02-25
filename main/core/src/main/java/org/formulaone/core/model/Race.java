package org.formulaone.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@Entity
@Table(name = "race")
public final class Race implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @OneToOne
  @JoinColumn(name = "year", nullable = false)
  private Season season;

  @OneToOne
  @JoinColumn(name = "circuit_id", referencedColumnName = "id", nullable = false)
  private Circuit circuit;

  @Column(name = "round")
  private int round;

  @Column(name = "name")
  private String name;

  @Temporal(TemporalType.DATE)
  @Column(name = "date")
  private Date date;

  @Temporal(TemporalType.TIME)
  @Column(name = "start_time")
  private Date startTime;

  @Column(name = "url", length = 500)
  private String url;

  @OneToMany(mappedBy = "race")
  private Set<LapTime> lapTimes = new HashSet<LapTime>();

  @OneToMany(mappedBy = "race")
  private Set<PitStop> pitStops = new HashSet<PitStop>();

  @OneToMany(mappedBy = "race")
  private Set<Qualifying> qualifyings = new HashSet<Qualifying>();

  @OneToMany(mappedBy = "race")
  private Set<Result> results = new HashSet<Result>();

  @OneToMany(mappedBy = "race")
  private Set<DriverStandings> driverStandings = new HashSet<DriverStandings>();

  @OneToMany(mappedBy = "race")
  private Set<ConstructorStandings> constructorStandings = new HashSet<ConstructorStandings>();

  public Race() {
  }

  public Race(Season season, Circuit circuit, int round, String name, Date date,
              Date startTime, String url) {
    this.season = season;
    this.circuit = circuit;
    this.round = round;
    this.name = name;
    this.date = date;
    this.startTime = startTime;
    this.url = url;
  }

  public Long getId() {
    return this.id;
  }

  public Season getSeason() {
    return this.season;
  }

  public Circuit getCircuit() {
    return this.circuit;
  }

  public int getRound() {
    return this.round;
  }

  public String getName() {
    return this.name;
  }

  public Date getDate() {
    return this.date;
  }

  public Date getStartTime() {
    return this.startTime;
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

    Race that = (Race) o;

    if (this.round != that.round) {
      return false;
    }
    if (!this.circuit.equals(that.circuit)) {
      return false;
    }
    if (!this.season.equals(that.season)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = this.season.hashCode();
    result = 31 * result + this.circuit.hashCode();
    result = 31 * result + (int) this.round;
    return result;
  }
}
