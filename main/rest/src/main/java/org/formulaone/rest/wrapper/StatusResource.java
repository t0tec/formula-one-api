package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.StatusDto;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("StatusTable")
public class StatusResource extends DtoResource<StatusDto> {

  public StatusResource(StatusDto content) {
    super(content);
  }

  @JacksonXmlProperty(localName = "status")
  @JsonProperty("status")
  @Override
  public StatusDto getContent() {
    return this.content;
  }
}
