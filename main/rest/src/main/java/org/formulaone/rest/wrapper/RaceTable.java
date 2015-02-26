package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.RaceDto;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class RaceTable {

  private List<RaceDto> races;

  public RaceTable(List<RaceDto> races) {
    this.races = races;
  }

  @JacksonXmlElementWrapper(localName = "races", useWrapping = false)
  @JacksonXmlProperty(localName = "race")
  @JsonProperty("races")
  public List<RaceDto> getRaces() {
    return races;
  }
}
