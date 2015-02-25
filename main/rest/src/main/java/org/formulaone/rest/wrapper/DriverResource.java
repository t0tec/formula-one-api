package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.DriverDto;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("DriverTable")
public class DriverResource extends DtoResource<DriverDto> {

  public DriverResource(DriverDto content) {
    super(content);
  }

  @JacksonXmlProperty(localName = "driver")
  @JsonProperty("driver")
  @Override
  public DriverDto getContent() {
    return this.content;
  }
}
