package org.formulaone.service;

import org.formulaone.core.exception.NotFoundException;
import org.formulaone.core.model.Driver;
import org.formulaone.repository.DriverRepository;
import org.formulaone.service.dto.DriverDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RepositoryDriverService extends RepositoryGenericService<Driver, DriverDto, Long>
    implements DriverReadOnlyService {

  private static final Logger logger = LoggerFactory.getLogger(RepositoryDriverService.class);

  private DriverRepository driverRepository;

  public RepositoryDriverService() {
    super();
  }

  @Autowired
  public RepositoryDriverService(DriverRepository constructorRepository) {
    super(constructorRepository);
    this.driverRepository = constructorRepository;
  }

  @Override
  public DriverDto findByReferenceName(String referenceName) {
    logger.info("Finding driver entry by using referenceName: {}", referenceName);

    Driver driverEntry = driverRepository.findByReferenceName(referenceName);

    if (driverEntry == null) {
      throw new NotFoundException(Driver.class, "referenceName", referenceName);
    }

    logger.info("Found driver entry: {}", driverEntry);

    return mapper.map(driverEntry, dtoClass);
  }
}
