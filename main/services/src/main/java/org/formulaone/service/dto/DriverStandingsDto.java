package org.formulaone.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.Mapping;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("driverStandings")
public class DriverStandingsDto {

  @JsonIgnore
  private Integer id;

  @Mapping("race.season.year")
  private Integer year;

  @Mapping("race.round")
  private Integer round;

  private DriverDto driver;

  @JacksonXmlProperty(isAttribute = true)
  private int position;

  @JacksonXmlProperty(isAttribute = true)
  private String positionText;

  @JacksonXmlProperty(isAttribute = true)
  private float points;

  @JacksonXmlProperty(isAttribute = true)
  private int wins;

  public DriverStandingsDto() {
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getYear() {
    return this.year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getRound() {
    return this.round;
  }

  public void setRound(Integer round) {
    this.round = round;
  }

  public DriverDto getDriver() {
    return this.driver;
  }

  public void setDriver(DriverDto driver) {
    this.driver = driver;
  }

  public int getPosition() {
    return this.position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public String getPositionText() {
    return this.positionText;
  }

  public void setPositionText(String positionText) {
    this.positionText = positionText;
  }

  public float getPoints() {
    return this.points;
  }

  public void setPoints(float points) {
    this.points = points;
  }

  public int getWins() {
    return this.wins;
  }

  public void setWins(int wins) {
    this.wins = wins;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).toString();
  }
}
