package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.DriverDto;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class DriverTable {

  private List<DriverDto> drivers;

  public DriverTable(List<DriverDto> drivers) {
    this.drivers = drivers;
  }

  @JacksonXmlElementWrapper(localName = "drivers", useWrapping = false)
  @JacksonXmlProperty(localName = "driver")
  @JsonProperty("drivers")
  public List<DriverDto> getDrivers() {
    return drivers;
  }
}
