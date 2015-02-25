package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.CircuitDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("CircuitTable")
public class CircuitPage extends PageResource<CircuitDto> {

  public CircuitPage(Page<CircuitDto> page, String pageParam, String sizeParam) {
    super(page, pageParam, sizeParam);
  }

  @JacksonXmlElementWrapper(localName = "circuits", useWrapping = false)
  @JacksonXmlProperty(localName = "circuit")
  @JsonProperty("circuits")
  @Override
  public List<CircuitDto> getContent() {
    return page.getContent();
  }
}
