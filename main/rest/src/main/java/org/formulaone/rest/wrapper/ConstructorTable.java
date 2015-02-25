package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.ConstructorDto;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class ConstructorTable {

  private List<ConstructorDto> constructors;

  public ConstructorTable(List<ConstructorDto> constructors) {
    this.constructors = constructors;
  }

  @JacksonXmlElementWrapper(localName = "constructors", useWrapping = false)
  @JacksonXmlProperty(localName = "constructor")
  @JsonProperty("constructors")
  public List<ConstructorDto> getConstructors() {
    return constructors;
  }
}
