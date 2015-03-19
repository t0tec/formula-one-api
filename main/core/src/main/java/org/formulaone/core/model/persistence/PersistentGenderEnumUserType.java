package org.formulaone.core.model.persistence;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public abstract class PersistentGenderEnumUserType<T extends PersistentGenderEnum>
    implements UserType {

  @Override
  public abstract Class<T> returnedClass();

  @Override
  public int[] sqlTypes() {
    return new int[]{Types.CHAR};
  }

  @Override
  public Object assemble(Serializable cached, Object owner)
      throws HibernateException {
    return cached;
  }

  @Override
  public Object deepCopy(Object value) throws HibernateException {
    return value;
  }

  @Override
  public Serializable disassemble(Object value) throws HibernateException {
    return (Serializable) value;
  }

  @Override
  public boolean equals(Object x, Object y) throws HibernateException {
    return x == y;
  }

  @Override
  public int hashCode(Object x) throws HibernateException {
    return x == null ? 0 : x.hashCode();
  }

  @Override
  public boolean isMutable() {
    return false;
  }

  @Override
  public Object nullSafeGet(ResultSet resultSet, String[] strings,
                            SessionImplementor sessionImplementor, Object o)
      throws HibernateException, SQLException {
    Character id = resultSet.getString(strings[0]).charAt(0);
    if (resultSet.wasNull()) {
      return null;
    }
    for (PersistentGenderEnum value : returnedClass().getEnumConstants()) {
      if (id.equals(value.getId())) {
        return value;
      }
    }
    throw new IllegalStateException("Unknown " + returnedClass().getSimpleName() + " id");
  }

  @Override
  public void nullSafeSet(PreparedStatement preparedStatement, Object value, int i,
                          SessionImplementor sessionImplementor)
      throws HibernateException, SQLException {
    if (value == null) {
      preparedStatement.setNull(i, Types.CHAR);
    } else {
      preparedStatement.setString(i, String.valueOf(((PersistentGenderEnum) value).getId()));
    }
  }

  @Override
  public Object replace(Object original, Object target, Object owner)
      throws HibernateException {
    return original;
  }
}
