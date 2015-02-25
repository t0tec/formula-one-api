package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.ConstructorDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("ConstructorTable")
public class ConstructorPage extends PageResource<ConstructorDto> {

  public ConstructorPage(Page<ConstructorDto> page, String pageParam, String sizeParam) {
    super(page, pageParam, sizeParam);
  }

  @JacksonXmlElementWrapper(localName = "circuits", useWrapping = false)
  @JacksonXmlProperty(localName = "constructor")
  @JsonProperty("constructors")
  @Override
  public List<ConstructorDto> getContent() {
    return page.getContent();
  }
}
