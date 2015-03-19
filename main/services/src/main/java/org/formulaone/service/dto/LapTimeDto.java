package org.formulaone.service.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.Mapping;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("lapTime")
public class LapTimeDto {

  @Mapping("driver.referenceName")
  private String driver;

  private int lap;

  private int position;

  private int time;

  public LapTimeDto() {
  }

  public String getDriver() {
    return this.driver;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public int getLap() {
    return this.lap;
  }

  public void setLap(int lap) {
    this.lap = lap;
  }

  public int getPosition() {
    return this.position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public int getTime() {
    return this.time;
  }

  public void setTime(int time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).toString();
  }
}
