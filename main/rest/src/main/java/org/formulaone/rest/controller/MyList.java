package org.formulaone.rest.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import org.formulaone.service.dto.CircuitDto;

import java.util.List;

class MyList {

  @JacksonXmlElementWrapper(localName = "circuit", useWrapping = false)
  @JsonProperty("circuit")
  private List<CircuitDto> list;

  public List<CircuitDto> getList() {
    return list;
  }

  public void setList(List<CircuitDto> list) {
    this.list = list;
  }
}
