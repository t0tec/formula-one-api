package org.formulaone.service;

import com.mysema.query.types.Predicate;

import org.formulaone.core.exception.NotFoundException;
import org.formulaone.core.model.Constructor;
import org.formulaone.core.model.QConstructor;
import org.formulaone.repository.ConstructorRepository;
import org.formulaone.service.dto.ConstructorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@Transactional(readOnly = true)
@Service
public class RepositoryConstructorService
    extends RepositoryGenericService<Constructor, ConstructorDto, Long>
    implements ConstructorReadOnlyService {

  private ConstructorRepository constructorRepository;

  public RepositoryConstructorService() {
    super();
  }

  @Autowired
  public RepositoryConstructorService(ConstructorRepository constructorRepository) {
    super(constructorRepository);
    this.constructorRepository = constructorRepository;
  }

  @Override
  public ConstructorDto findByReferenceName(String referenceName) {
    Constructor constructorEntry =
        constructorRepository.findOne(referenceNameEquals(referenceName));

    if (constructorEntry == null) {
      throw new NotFoundException(Constructor.class, "referenceName", referenceName);
    }

    return mapper.map(constructorEntry, dtoClass);
  }

  private static Predicate referenceNameEquals(final String referenceName) {
    QConstructor constructor = QConstructor.constructor;
    return constructor.referenceName.eq(referenceName);
  }

  @Override
  public List<ConstructorDto> findConstructorsBySeason(int year) {
    List<ConstructorDto> result = new ArrayList<ConstructorDto>();

    for (Constructor constructor : constructorRepository.findConstructorsBySeason(year)) {
      result.add(mapper.map(constructor, dtoClass));
    }

    return result;
  }

  @Override
  public List<ConstructorDto> findConstructorsBySeasonAndRound(int year, int round) {
    List<ConstructorDto> result = new ArrayList<ConstructorDto>();

    for (Constructor constructor : constructorRepository.findConstructorsBySeasonAndRound(year,
                                                                                          round)) {
      result.add(mapper.map(constructor, dtoClass));
    }

    return result;
  }
}
