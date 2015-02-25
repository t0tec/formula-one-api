package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.DriverDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("DriverTable")
public class DriverPage extends PageResource<DriverDto> {

  public DriverPage(Page<DriverDto> page, String pageParam, String sizeParam) {
    super(page, pageParam, sizeParam);
  }

  @JacksonXmlElementWrapper(localName = "drivers", useWrapping = false)
  @JacksonXmlProperty(localName = "driver")
  @JsonProperty("drivers")
  @Override
  public List<DriverDto> getContent() {
    return page.getContent();
  }
}
