package org.formulaone.core.model;

import org.hibernate.annotations.Formula;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

  @Formula(value = "(select count(*) from status "
                   + "inner join result on result.status_id = status.id "
                   + "where result.status_id = id "
                   + "group by status.id)")
  private Integer count;

  @OneToMany(mappedBy = "status")
  private List<Result> results;

  public Status() {
  }

  public Status(Long id, String status, Long count) {
    this.id = id;
    this.status = status;
    this.count = count.intValue();
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
