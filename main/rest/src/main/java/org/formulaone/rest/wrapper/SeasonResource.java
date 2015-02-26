package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.SeasonDto;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("SeasonTable")
public class SeasonResource extends DtoResource<SeasonDto> {

  public SeasonResource(SeasonDto content) {
    super(content);
  }

  @JacksonXmlProperty(localName = "season")
  @JsonProperty("season")
  @Override
  public SeasonDto getContent() {
    return this.content;
  }
}
