package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.ConstructorStandingsDto;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class ConstructorStandingsTable {

  private List<ConstructorStandingsDto> constructorStandings;

  public ConstructorStandingsTable(List<ConstructorStandingsDto> constructorStandings) {
    this.constructorStandings = constructorStandings;
  }

  @JacksonXmlElementWrapper(localName = "constructorStanding", useWrapping = false)
  @JacksonXmlProperty(localName = "constructorStandings")
  @JsonProperty("standings")
  public List<ConstructorStandingsDto> getConstructorStandings() {
    return constructorStandings;
  }
}
