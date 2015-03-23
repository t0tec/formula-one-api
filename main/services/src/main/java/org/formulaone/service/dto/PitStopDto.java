package org.formulaone.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.Mapping;

import java.util.Date;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("pitStop")
public class PitStopDto {

  @JacksonXmlProperty(isAttribute = true)
  @Mapping("driver.referenceName")
  private String driver;

  @JacksonXmlProperty(isAttribute = true)
  private int stop;

  @JacksonXmlProperty(isAttribute = true)
  private int lap;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "CET")
  @JacksonXmlProperty(isAttribute = true)
  private Date time;

  // TODO: PitStop in core module stores milliseconds as Integer as milliseconds,
  // this converts it to a readable time format
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ss.S")
  @JsonIgnore
  private Integer milliseconds;

  @JacksonXmlProperty(isAttribute = true)
  private String duration;

  public PitStopDto() {
  }

  public String getDriver() {
    return this.driver;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public int getStop() {
    return this.stop;
  }

  public void setStop(int stop) {
    this.stop = stop;
  }

  public int getLap() {
    return this.lap;
  }

  public void setLap(int lap) {
    this.lap = lap;
  }

  public Date getTime() {
    return this.time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public Integer getMilliseconds() {
    return this.milliseconds;
  }

  public void setMilliseconds(Integer milliseconds) {
    this.milliseconds = milliseconds;
  }

  public String getDuration() {
    return this.duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).toString();
  }
}
