package org.formulaone.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("qualifying")
public class QualifyingDto {

  @JsonIgnore
  private Long id;

  @JacksonXmlProperty(isAttribute = true)
  private int driverNumber;

  @JacksonXmlProperty(isAttribute = true)
  private Integer position;

  private DriverDto driver;

  private ConstructorDto constructor;

  private String q1;

  private String q2;

  private String q3;

  public QualifyingDto() {
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getDriverNumber() {
    return this.driverNumber;
  }

  public void setDriverNumber(int driverNumber) {
    this.driverNumber = driverNumber;
  }

  public Integer getPosition() {
    return this.position;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }

  public DriverDto getDriver() {
    return this.driver;
  }

  public void setDriver(DriverDto driver) {
    this.driver = driver;
  }

  public ConstructorDto getConstructor() {
    return this.constructor;
  }

  public void setConstructor(ConstructorDto constructor) {
    this.constructor = constructor;
  }

  public String getQ1() {
    return this.q1;
  }

  public void setQ1(String q1) {
    this.q1 = q1;
  }

  public String getQ2() {
    return this.q2;
  }

  public void setQ2(String q2) {
    this.q2 = q2;
  }

  public String getQ3() {
    return this.q3;
  }

  public void setQ3(String q3) {
    this.q3 = q3;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).toString();
  }
}
