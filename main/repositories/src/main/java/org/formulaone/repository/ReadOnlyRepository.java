package org.formulaone.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@NoRepositoryBean
public interface ReadOnlyRepository<T, ID extends Serializable> extends Repository<T, ID> {

  /**
   * Returns an entity by its id.
   *
   * @param id
   * @return the entity with the given id or null if none found
   */
  T findOne(ID id);

  /**
   * Returns all instances of the type.
   *
   * @return all entities
   */
  Iterable<T> findAll();

  /**
   * Returns all entities sorted by the given options.
   *
   * @param sort
   * @return all entities sorted by the given options
   */
  Iterable<T> findAll(Sort sort);

  /**
   * Returns a {@link Page} of entities meeting the paging restriction provided in the
   * {@code Pageable} object.
   *
   * @return a page of entities
   */
  Page<T> findAll(Pageable pageable);
}
