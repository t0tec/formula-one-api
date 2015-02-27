package org.formulaone.service.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.formulaone.core.model.ConstrainConstants;

import javax.validation.constraints.Size;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("season")
public class SeasonDto {

  @JacksonXmlText
  private int year;

  @JacksonXmlProperty(isAttribute = true)
  @Size(max = ConstrainConstants.MAX_LENGTH_URL)
  private String url;

  public SeasonDto() {
  }

  public int getYear() {
    return this.year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("year", this.year).append("url", this.url).toString();
  }
}
