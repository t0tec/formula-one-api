package org.formulaone.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

  @JacksonXmlProperty(isAttribute = true)
  private int duration;

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

  public int getDuration() {
    return this.duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).toString();
  }
}
