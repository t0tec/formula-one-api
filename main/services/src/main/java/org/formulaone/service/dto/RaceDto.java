package org.formulaone.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.formulaone.core.model.ConstrainConstants;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.constraints.Size;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName("race")
public class RaceDto {

  @JsonIgnore
  private Long id;

  private String name;

  private SeasonDto season;

  @JacksonXmlProperty(isAttribute = true)
  private int round;

  private CircuitDto circuit;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "CET")
  private Date date;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss'Z'", timezone = "CET")
  private Date startTime;

  @JacksonXmlProperty(isAttribute = true)
  @Size(max = ConstrainConstants.MAX_LENGTH_URL)
  private String url;

  private Set<ResultDto> results = new LinkedHashSet<ResultDto>();

  public RaceDto() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SeasonDto getSeason() {
    return this.season;
  }

  public void setSeason(SeasonDto season) {
    this.season = season;
  }

  public CircuitDto getCircuit() {
    return this.circuit;
  }

  public void setCircuit(CircuitDto circuit) {
    this.circuit = circuit;
  }

  public int getRound() {
    return this.round;
  }

  public void setRound(int round) {
    this.round = round;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getDate() {
    return this.date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Date getStartTime() {
    return this.startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Set<ResultDto> getResults() {
    return this.results;
  }

  public void setResults(Set<ResultDto> results) {
    this.results = results;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("name", this.name)
        .append("season", this.season).append("round", this.round)
        .append("circuit", this.circuit).append("date", this.date)
        .append("startTime", this.startTime).append("url", this.url).toString();
  }
}
