package org.formulaone.service;

import org.dozer.DozerBeanMapper;
import org.formulaone.core.exception.NotFoundException;
import org.formulaone.repository.ReadOnlyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

  @SuppressWarnings("unchecked")
  @Autowired
  public RepositoryGenericService(ReadOnlyRepository<T, ID> repository) {
    this.repository = repository;
    ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
    this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    this.dtoClass = (Class<D>) genericSuperclass.getActualTypeArguments()[1];
  }

  @Override
  public D findById(ID id) {
    T entry = repository.findOne(id);

    if (entry == null) {
      throw new NotFoundException(this.entityClass, "id", id);
    }

    return mapper.map(entry, dtoClass);
  }

  @Override
  public List<D> findAll() {
    List<D> result = new ArrayList<D>();

    for (T t : repository.findAll()) {
      result.add(mapper.map(t, dtoClass));
    }

    return result;
  }

  @Override
  public List<D> findAll(Sort sort) {
    List<D> result = new ArrayList<D>();

    for (T t : repository.findAll(sort)) {
      result.add(mapper.map(t, dtoClass));
    }

    return result;
  }

  @Override
  public Page<D> findAll(Pageable pageable) {
    List<D> result = new ArrayList<D>();
    Page<T> pageSource = repository.findAll(pageable);

    for (T t : pageSource.getContent()) {
      result.add(mapper.map(t, dtoClass));
    }

    Page<D> pageTarget = new PageImpl<D>(result, pageable, pageSource.getTotalElements());

    return pageTarget;
  }

}
