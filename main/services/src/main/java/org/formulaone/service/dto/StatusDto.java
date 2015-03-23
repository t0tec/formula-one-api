package org.formulaone.service.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("status")
public class StatusDto {

  @JacksonXmlProperty(isAttribute = true)
  private Integer id;

  @JacksonXmlText
  private String status;

  @JacksonXmlProperty(isAttribute = true)
  private Integer count;

  public StatusDto() {
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Integer getCount() {
    return this.count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("id", this.id).append("status", this.status)
        .append("count", this.count).toString();
  }
}
