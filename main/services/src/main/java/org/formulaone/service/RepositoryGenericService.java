package org.formulaone.service;

import org.dozer.DozerBeanMapper;
import org.formulaone.repository.ReadOnlyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
class RepositoryGenericService<T, D, ID extends Serializable> implements GenericService<T, D, ID> {

  @Autowired
  private ReadOnlyRepository<T, ID> repository;

  @Autowired
  protected DozerBeanMapper mapper;

  protected Class<T> entityClass;

  protected Class<D> dtoClass;

  @SuppressWarnings("unchecked")
  public RepositoryGenericService() {
    ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
    this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    this.dtoClass = (Class<D>) genericSuperclass.getActualTypeArguments()[1];
  }

  public D findById(ID id) {
    return mapper.map(repository.findOne(id), dtoClass);
  }

  public List<D> findAll() {
    List<D> result = new ArrayList<D>();
    for (T t : repository.findAll()) {
      result.add(mapper.map(t, dtoClass));
    }
    return result;
  }
}
