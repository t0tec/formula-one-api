package org.formulaone.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

  // TODO: LapTime in core module stores time as Integer as milliseconds,
  // this converts it to a readable time format
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "m:ss.S")
  @JsonIgnore
  private Integer milliseconds;

  private String time;

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

  public int getMilliseconds() {
    return this.milliseconds;
  }

  public void setMilliseconds(int milliseconds) {
    this.milliseconds = milliseconds;
  }

  public String getTime() {
    return this.time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).toString();
  }
}
