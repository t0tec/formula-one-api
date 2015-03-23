package org.formulaone.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@Entity
@Table(name = "lapTimes")
public final class LapTime implements Serializable {

  @Id
  @ManyToOne
  @JoinColumn(name = "raceId", referencedColumnName = "raceId", nullable = false)
  private Race race;

  @Id
  @ManyToOne
  @JoinColumn(name = "driverId", referencedColumnName = "driverId", nullable = false)
  private Driver driver;

  @Id
  @Column(name = "lap")
  private int lap;

  @Column(name = "position")
  private int position;

  @Column(name = "milliseconds")
  private Integer milliseconds;

  @Column(name = "time")
  private String time;

  public LapTime() {
  }

  public LapTime(Race race, Driver driver, int lap, int position, Integer milliseconds, String time) {
    this.race = race;
    this.driver = driver;
    this.lap = lap;
    this.position = position;
    this.milliseconds = milliseconds;
    this.time = time;
  }

  public Race getRace() {
    return this.race;
  }

  public Driver getDriver() {
    return this.driver;
  }

  public int getLap() {
    return this.lap;
  }

  public int getPosition() {
    return this.position;
  }

  public Integer getMilliseconds() {
    return this.milliseconds;
  }

  public String getTime() {
    return this.time;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    LapTime that = (LapTime) o;

    if (this.lap != that.lap) {
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
    result = 31 * result + this.lap;
    return result;
  }
}
