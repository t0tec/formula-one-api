package org.formulaone.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("result")
public class ResultDto {

  @JsonIgnore
  private Long id;

  @JacksonXmlProperty(isAttribute = true)
  private int driverNumber;

  private Integer gridPosition;

  @JsonIgnore
  private Integer position;

  @JacksonXmlProperty(isAttribute = true)
  private int positionOrder;

  @JacksonXmlProperty(isAttribute = true)
  private String positionText;

  @JacksonXmlProperty(isAttribute = true)
  private double points;

  private int laps;

  private String time;

  private Integer raceTime;

  private FastestLap fastestLap;

  private DriverDto driver;

  private ConstructorDto constructor;

  private StatusDto status;

  public ResultDto() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getDriverNumber() {
    return this.driverNumber;
  }

  public void setDriverNumber(int driverNumber) {
    this.driverNumber = driverNumber;
  }

  public Integer getGridPosition() {
    return this.gridPosition;
  }

  public void setGridPosition(Integer gridPosition) {
    this.gridPosition = gridPosition;
  }

  public Integer getPosition() {
    return this.position;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }

  public int getPositionOrder() {
    return this.positionOrder;
  }

  public void setPositionOrder(int positionOrder) {
    this.positionOrder = positionOrder;
  }

  public String getPositionText() {
    return this.positionText;
  }

  public void setPositionText(String positionText) {
    this.positionText = positionText;
  }

  public double getPoints() {
    return this.points;
  }

  public void setPoints(double points) {
    this.points = points;
  }

  public int getLaps() {
    return this.laps;
  }

  public void setLaps(int laps) {
    this.laps = laps;
  }

  public String getTime() {
    return this.time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public Integer getRaceTime() {
    return this.raceTime;
  }

  public void setRaceTime(Integer raceTime) {
    this.raceTime = raceTime;
  }

  public FastestLap getFastestLap() {
    return this.fastestLap;
  }

  public DriverDto getDriver() {
    return this.driver;
  }

  public void setDriver(DriverDto driver) {
    this.driver = driver;
  }

  public ConstructorDto getConstructor() {
    return this.constructor;
  }

  public void setConstructor(ConstructorDto constructor) {
    this.constructor = constructor;
  }

  public StatusDto getStatus() {
    return this.status;
  }

  public void setStatus(StatusDto status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).toString();
  }
}
