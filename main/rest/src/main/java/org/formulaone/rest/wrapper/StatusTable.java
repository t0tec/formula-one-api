package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.StatusDto;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class StatusTable {

  private List<StatusDto> status;

  public StatusTable(List<StatusDto> status) {
    this.status = status;
  }

  @JacksonXmlElementWrapper(localName = "status", useWrapping = false)
  @JacksonXmlProperty(localName = "status")
  @JsonProperty("status")
  public List<StatusDto> getStatus() {
    return status;
  }
}
