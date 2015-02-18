package org.formulaone.service;

import java.io.Serializable;
import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
interface GenericService<T, D, ID extends Serializable> {

  /**
   * Finds all entries that are in the database.
   */
  List<D> findAll();

  /**
   * Finds an entry by using the id given as a method parameter.
   *
   * @param id The id of the wanted entry.
   * @return The information of the requested entry.
   */
  D findById(ID id);
}
