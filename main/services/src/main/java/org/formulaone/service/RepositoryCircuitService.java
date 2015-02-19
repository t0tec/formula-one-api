package org.formulaone.service;

import org.formulaone.core.exception.CircuitNotFoundException;
import org.formulaone.core.model.Circuit;
import org.formulaone.repository.CircuitRepository;
import org.formulaone.service.dto.CircuitDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@Service
public class RepositoryCircuitService extends RepositoryGenericService<Circuit, CircuitDto, Long>
    implements CircuitReadOnlyService {

  private static final Logger logger = LoggerFactory.getLogger(RepositoryCircuitService.class);

  private CircuitRepository circuitRepository;

  public RepositoryCircuitService() {
    super();
  }

  @Autowired
  public RepositoryCircuitService(CircuitRepository circuitRepository) {
    super(circuitRepository);
    this.circuitRepository = circuitRepository;
  }

  @Override
  public CircuitDto findById(Long id) {
    logger.info("Finding circuit entry by using id: {}", id);

    Circuit circuitEntry = repository.findOne(id);

    if (circuitEntry == null) {
      throw new CircuitNotFoundException("id", id);
    }

    logger.info("Found circuit entry: {}", circuitEntry);

    return mapper.map(circuitEntry, dtoClass);
  }

  @Override
  public CircuitDto findByReferenceName(String referenceName) {
    logger.info("Finding circuit entry by using referenceName: {}", referenceName);

    Circuit circuitEntry = circuitRepository.findByReferenceName(referenceName);

    if (circuitEntry == null) {
      throw new CircuitNotFoundException("referenceName", referenceName);
    }

    logger.info("Found circuit entry: {}", circuitEntry);

    return mapper.map(circuitEntry, dtoClass);
  }
}
