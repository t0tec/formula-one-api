package org.formulaone.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
interface GenericService<T, D, ID extends Serializable> {

  /**
   * Finds an entry by using the id given as a method parameter.
   *
   * @param id The id of the wanted entry.
   * @return The information of the requested entry.
   */
  D findById(ID id);

  /**
   * Finds all entries that are in the database.
   */
  List<D> findAll();

  /**
   * Finds all entries that are in the database sorted by parameter
   * @param sort On which parameter you want to sort
   */
  List<D> findAll(Sort sort);

  /**
   * Finds all entries that are in the database with pagination
   * @param pageable Paginate the query results by using Pageable
   */
  Page<D> findAll(Pageable pageable);

}
