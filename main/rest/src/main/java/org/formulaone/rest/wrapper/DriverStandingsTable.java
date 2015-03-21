package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.DriverStandingsDto;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class DriverStandingsTable {

  private List<DriverStandingsDto> driverStandings;

  public DriverStandingsTable(List<DriverStandingsDto> driverStandings) {
    this.driverStandings = driverStandings;
  }

  @JacksonXmlElementWrapper(localName = "driverStanding", useWrapping = false)
  @JacksonXmlProperty(localName = "driverStandings")
  @JsonProperty("standings")
  public List<DriverStandingsDto> getDriverStandingsDto() {
    return driverStandings;
  }
}
