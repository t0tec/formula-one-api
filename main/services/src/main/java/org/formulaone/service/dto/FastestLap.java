package org.formulaone.service.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class FastestLap {

  @JacksonXmlProperty(isAttribute = true)
  private Integer lap;

  @JacksonXmlProperty(isAttribute = true)
  private Integer rank;

  private String time;

  private Double averageSpeed;

  public FastestLap() {
  }

  public Integer getLap() {
    return this.lap;
  }

  public void setLap(Integer lap) {
    this.lap = lap;
  }

  public Integer getRank() {
    return this.rank;
  }

  public void setRank(Integer rank) {
    this.rank = rank;
  }

  public String getTime() {
    return this.time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public Double getAverageSpeed() {
    return this.averageSpeed;
  }

  public void setAverageSpeed(Double averageSpeed) {
    this.averageSpeed = averageSpeed;
  }
}
