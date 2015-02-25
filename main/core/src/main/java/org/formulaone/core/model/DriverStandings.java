package org.formulaone.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
@Table(name = "driver_standings")
public final class DriverStandings implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "race_id", referencedColumnName = "id", nullable = false)
  private Race race;

  @ManyToOne
  @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
  private Driver driver;

  @Column(name = "points", nullable = false)
  private double points;

  @Column(name = "position")
  private int position;

  @Column(name = "position_text")
  private String positionText;

  @Column(name = "wins", nullable = false)
  private int wins;

  public DriverStandings() {
  }

  public DriverStandings(Race race, Driver driver, double points, int position, String positionText,
                         int wins) {
    this.race = race;
    this.driver = driver;
    this.points = points;
    this.position = position;
    this.positionText = positionText;
    this.wins = wins;
  }

  public Long getId() {
    return this.id;
  }

  public Race getRace() {
    return this.race;
  }

  public Driver getDriver() {
    return this.driver;
  }

  public double getPoints() {
    return this.points;
  }

  public int getPosition() {
    return this.position;
  }

  public String getPositionText() {
    return this.positionText;
  }

  public int getWins() {
    return this.wins;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    DriverStandings that = (DriverStandings) o;

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
    return result;
  }
}
