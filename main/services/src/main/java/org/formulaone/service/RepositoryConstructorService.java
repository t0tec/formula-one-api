package org.formulaone.service;

import org.formulaone.core.exception.NotFoundException;
import org.formulaone.core.model.Constructor;
import org.formulaone.repository.ConstructorRepository;
import org.formulaone.service.dto.ConstructorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    Constructor constructorEntry = constructorRepository.findByReferenceName(referenceName);

    if (constructorEntry == null) {
      throw new NotFoundException(Constructor.class, "referenceName", referenceName);
    }

    return mapper.map(constructorEntry, dtoClass);
  }
}
