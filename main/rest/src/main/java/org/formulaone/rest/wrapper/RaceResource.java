package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.RaceDto;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("RaceTable")
public class RaceResource extends DtoResource<RaceDto> {

  public RaceResource(RaceDto content) {
    super(content);
  }

  @JacksonXmlProperty(localName = "race")
  @JsonProperty("race")
  @Override
  public RaceDto getContent() {
    return this.content;
  }
}
