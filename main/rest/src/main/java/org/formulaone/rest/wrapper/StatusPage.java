package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.StatusDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("StatusTable")
public class StatusPage extends PageResource<StatusDto> {

  public StatusPage(Page<StatusDto> page, String pageParam, String sizeParam) {
    super(page, pageParam, sizeParam);
  }

  @JacksonXmlElementWrapper(localName = "status", useWrapping = false)
  @JacksonXmlProperty(localName = "status")
  @JsonProperty("status")
  @Override
  public List<StatusDto> getContent() {
    return page.getContent();
  }
}
