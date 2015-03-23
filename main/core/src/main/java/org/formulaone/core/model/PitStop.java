package org.formulaone.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@Entity
@Table(name = "pitStops")
public final class PitStop implements Serializable {

  @Id
  @ManyToOne
  @JoinColumn(name = "raceId", referencedColumnName = "raceId", nullable = false)
  private Race race;

  @Id
  @ManyToOne
  @JoinColumn(name = "driverId", referencedColumnName = "driverId", nullable = false)
  private Driver driver;

  @Id
  @Column(name = "stop")
  private int stop;

  @Column(name = "lap")
  private int lap;

  @Temporal(TemporalType.TIME)
  @Column(name = "time")
  private Date time;

  @Column(name = "milliseconds")
  private Integer milliseconds;

  @Column(name = "duration")
  private String duration;

  public PitStop() {
  }

  public PitStop(Race race, Driver driver, int stop, int lap, Date time, Integer milliseconds, String duration) {
    this.race = race;
    this.driver = driver;
    this.stop = stop;
    this.lap = lap;
    this.time = time;
    this.milliseconds = milliseconds;
    this.duration = duration;
  }

  public Race getRace() {
    return this.race;
  }

  public Driver getDriver() {
    return this.driver;
  }

  public int getStop() {
    return this.stop;
  }

  public int getLap() {
    return this.lap;
  }

  public Date getTime() {
    return this.time;
  }

  public Integer getMilliseconds() {
    return this.milliseconds;
  }

  public String getDuration() {
    return this.duration;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    PitStop that = (PitStop) o;

    if (this.stop != that.stop) {
      return false;
    }
    if (!this.driver.equals(that.driver)) {
      return false;
    }
    if (!this.race.equals(that.race)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = this.race.hashCode();
    result = 31 * result + this.driver.hashCode();
    result = 31 * result + this.stop;
    return result;
  }
}
