package org.formulaone.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
  private long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", unique = true)
  private StatusType statusType;

  public Status() {
  }

  public Status(StatusType statusType) {
    this.statusType = statusType;
  }

  public long getId() {
    return this.id;
  }

  public StatusType getStatusType() {
    return this.statusType;
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

    if (this.statusType != status.statusType) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return this.statusType.hashCode();
  }
}

