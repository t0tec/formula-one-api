package org.formulaone.rest.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.formulaone.service.dto.RaceDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("RaceTable")
public class RacePage extends PageResource<RaceDto> {

  public RacePage(Page<RaceDto> page, String pageParam, String sizeParam) {
    super(page, pageParam, sizeParam);
  }

  @JacksonXmlElementWrapper(localName = "races", useWrapping = false)
  @JacksonXmlProperty(localName = "race")
  @JsonProperty("races")
  @Override
  public List<RaceDto> getContent() {
    return page.getContent();
  }
}
