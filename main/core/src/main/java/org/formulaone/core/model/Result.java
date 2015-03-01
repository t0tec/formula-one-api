package org.formulaone.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@Entity
@Table(name = "result")
public final class Result implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "number")
  private int driverNumber;

  @Column(name = "grid")
  private Integer gridPosition;

  @Column(name = "position")
  private Integer position;

  @Column(name = "position_text")
  private String positionText;

  @Column(name = "position_order")
  private int positionOrder;

  @Column(name = "points")
  private double points;

  @Column(name = "laps")
  private int laps;

  @Column(name = "rank")
  private int rank;

  @Column(name = "time")
  private String time;

  @Column(name = "milliseconds")
  private int raceTime;

  // TODO: remove fastestLap and fastestLapTime and introduce LapTime to reference it?
  @Column(name = "fastest_lap")
  private int fastestLap;

  @Column(name = "fastest_lap_time")
  private String fastestLapTime;

  @Column(name = "average_speed", columnDefinition = "decimal(5,2)")
  private double averageSpeed;

  @ManyToOne
  @JoinColumn(name = "race_id", referencedColumnName = "id", nullable = false)
  private Race race;

  @ManyToOne
  @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
  private Driver driver;

  @ManyToOne
  @JoinColumn(name = "constructor_id", referencedColumnName = "id", nullable = false)
  private Constructor constructor;

  @ManyToOne
  @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
  private Status status;

  public Result() {
  }

  public Result(int driverNumber, Integer gridPosition, Integer position,
                String positionText, int positionOrder, double points, int laps, int rank,
                String time, int raceTime, int fastestLap, String fastestLapTime,
                double averageSpeed, Race race, Driver driver,
                Constructor constructor, Status status) {
    this.driverNumber = driverNumber;
    this.gridPosition = gridPosition;
    this.position = position;
    this.positionText = positionText;
    this.positionOrder = positionOrder;
    this.points = points;
    this.laps = laps;
    this.rank = rank;
    this.time = time;
    this.raceTime = raceTime;
    this.fastestLap = fastestLap;
    this.fastestLapTime = fastestLapTime;
    this.averageSpeed = averageSpeed;
    this.race = race;
    this.driver = driver;
    this.constructor = constructor;
    this.status = status;
  }

  public Long getId() {
    return this.id;
  }

  public int getDriverNumber() {
    return this.driverNumber;
  }

  public Integer getGridPosition() {
    return this.gridPosition;
  }

  public Integer getPosition() {
    return this.position;
  }

  public String getPositionText() {
    return this.positionText;
  }

  public int getPositionOrder() {
    return this.positionOrder;
  }

  public double getPoints() {
    return this.points;
  }

  public int getLaps() {
    return this.laps;
  }

  public int getRank() {
    return this.rank;
  }

  public String getTime() {
    return this.time;
  }

  public int getRaceTime() {
    return this.raceTime;
  }

  public int getFastestLap() {
    return this.fastestLap;
  }

  public String getFastestLapTime() {
    return this.fastestLapTime;
  }

  public double getAverageSpeed() {
    return this.averageSpeed;
  }

  public Status getStatus() {
    return this.status;
  }

  public Race getRace() {
    return this.race;
  }

  public Driver getDriver() {
    return this.driver;
  }

  public Constructor getConstructor() {
    return this.constructor;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    Result result = (Result) o;

    if (!this.driver.equals(result.driver)) {
      return false;
    }
    if (!this.race.equals(result.race)) {
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
