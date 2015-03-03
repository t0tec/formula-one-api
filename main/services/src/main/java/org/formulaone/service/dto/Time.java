package org.formulaone.service.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class Time {

  @JacksonXmlText
  private String time;

  @JacksonXmlProperty(isAttribute = true)
  private Integer millis;

  public Time() {
  }

  public String getTime() {
    return this.time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public Integer getMillis() {
    return this.millis;
  }

  public void setMillis(Integer millis) {
    this.millis = millis;
  }
}
