package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.CircuitDto;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("CircuitTable")
public class CircuitResource extends DtoResource<CircuitDto> {

  public CircuitResource(CircuitDto content) {
    super(content);
  }

  @JacksonXmlProperty(localName = "circuit")
  @JsonProperty("circuit")
  @Override
  public CircuitDto getContent() {
    return this.content;
  }
}
