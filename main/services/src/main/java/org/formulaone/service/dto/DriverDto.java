package org.formulaone.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.formulaone.core.model.ConstrainConstants;
import org.formulaone.core.model.Gender;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

import javax.validation.constraints.Size;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@JsonRootName("driver")
public class DriverDto {

  @JsonIgnore
  private Long id;

  @JacksonXmlProperty(isAttribute = true)
  @NotEmpty
  private String referenceName;

  private Integer number;

  @JacksonXmlProperty(isAttribute = true)
  private String code;

  private String forename;

  private String surname;

  private Date birthdate;

  private String nationality;

  private Gender gender;

  @JacksonXmlProperty(isAttribute = true)
  @Size(max = ConstrainConstants.MAX_LENGTH_URL)
  private String url;

  public DriverDto() {
  }

  private DriverDto(Builder builder) {
    this.id = builder.id;
    this.referenceName = builder.referenceName;
    this.forename = builder.forename;
    this.surname = builder.surname;
  }

  public static Builder getBuilder() {
    return new Builder();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getReferenceName() {
    return this.referenceName;
  }

  public void setReferenceName(String referenceName) {
    this.referenceName = referenceName;
  }

  public Integer getNumber() {
    return this.number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getForename() {
    return this.forename;
  }

  public void setForename(String forename) {
    this.forename = forename;
  }

  public String getSurname() {
    return this.surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public Date getBirthdate() {
    return this.birthdate;
  }

  public void setBirthdate(Date birthdate) {
    this.birthdate = birthdate;
  }

  public String getNationality() {
    return this.nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  public Gender getGender() {
    return this.gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("referenceName", this.referenceName)
        .append("number", this.number).append("code", this.code).append("forename", this.forename)
        .append("surname", this.surname).append("birthdate").append(this.birthdate)
        .append("nationality", this.nationality).append("gender", this.gender)
        .append("url", this.url).toString();
  }

  /**
   * The builder pattern makes it easier to create objects
   */
  public static class Builder {

    private Long id;
    private String referenceName;
    private String forename;
    private String surname;

    public Builder() {
    }

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder referenceName(String referenceName) {
      this.referenceName = referenceName;
      return this;
    }

    public Builder forename(String forename) {
      this.forename = forename;
      return this;
    }

    public Builder surname(String surname) {
      this.surname = surname;
      return this;
    }

    public DriverDto build() {
      DriverDto build = new DriverDto(this);

      return build;
    }
  }
}
