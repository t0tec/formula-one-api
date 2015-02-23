package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.CircuitDto;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class CircuitTable {

  private List<CircuitDto> circuits;

  public CircuitTable(List<CircuitDto> circuits) {
    this.circuits = circuits;
  }

  @JacksonXmlElementWrapper(localName = "circuits", useWrapping = false)
  @JacksonXmlProperty(localName = "circuit")
  @JsonProperty("circuits")
  public List<CircuitDto> getCircuits() {
    return circuits;
  }
}
