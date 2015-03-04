package org.formulaone.core.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@Entity
@Table(name = "status")
public final class Status implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private Long id;

  @Column(name = "status")
  private String status;

  @Transient
  private Integer count;

  @OneToMany(mappedBy = "status")
  private List<Result> results;

  public Status() {
  }

  public Status(Long id, String status, Long count) {
    this.id = id;
    this.status = status;
    this.count = count != null ? count.intValue() : null;
  }

  public Long getId() {
    return this.id;
  }

  public String getStatus() {
    return this.status;
  }

  public Integer getCount() {
    return this.count;
  }

  public List<Result> getResults() {
    return this.results;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    Status status = (Status) o;

    if (this.status != status.status) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return this.status.hashCode();
  }
}
