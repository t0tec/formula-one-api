package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.ConstructorDto;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("ConstructorTable")
public class ConstructorResource extends DtoResource<ConstructorDto> {

  public ConstructorResource(ConstructorDto content) {
    super(content);
  }

  @JacksonXmlProperty(localName = "constructor")
  @JsonProperty("constructor")
  @Override
  public ConstructorDto getContent() {
    return this.content;
  }
}
