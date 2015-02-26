package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.SeasonDto;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class SeasonTable {

  private List<SeasonDto> seasons;

  public SeasonTable(List<SeasonDto> seasons) {
    this.seasons = seasons;
  }

  @JacksonXmlElementWrapper(localName = "seasons", useWrapping = false)
  @JacksonXmlProperty(localName = "season")
  @JsonProperty("seasons")
  public List<SeasonDto> getSeasons() {
    return seasons;
  }
}
