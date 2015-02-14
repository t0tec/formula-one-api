package org.formulaone.service;

import org.formulaone.core.dto.CircuitDto;
import org.formulaone.core.exception.CircuitNotFoundException;
import org.formulaone.core.model.Circuit;
import org.formulaone.repository.CircuitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Service
final class RepositoryCircuitService implements CircuitReadOnlyService {

  private static final Logger logger = LoggerFactory.getLogger(RepositoryCircuitService.class);

  private CircuitRepository repository;

  @Autowired
  public RepositoryCircuitService(CircuitRepository repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  @Override
  public List<CircuitDto> findAll() {
    List<CircuitDto> circuitEntries = new ArrayList<CircuitDto>();

    logger.info("Finding all circuit entries.");

    for (Circuit circuit : repository.findAll()) {
      circuitEntries.add(convertIntoDto(circuit));
    }

    logger.info("Found {} circuit entries", circuitEntries.size());

    return circuitEntries;
  }

  @Transactional(readOnly = true)
  @Override
  public CircuitDto findById(Long id) {
    logger.info("Finding circuit entry by using id: {}", id);

    Circuit circuitEntry = repository.findOne(id);

    if (circuitEntry == null) {
      throw new CircuitNotFoundException("Did not found circuit with id " + id);
    }

    logger.info("Found circuit entry: {}", circuitEntry);

    return convertIntoDto(circuitEntry);
  }

  @Override
  public CircuitDto findByName(String name) {
    logger.info("Finding circuit entry by using name: {}", name);

    Circuit circuitEntry = repository.findByName(name);

    if (circuitEntry == null) {
      throw new CircuitNotFoundException("Did not found circuit with name " + name);
    }

    logger.info("Found circuit entry: {}", circuitEntry);

    return convertIntoDto(circuitEntry);
  }

  private CircuitDto convertIntoDto(Circuit entity) {
    CircuitDto dto = new CircuitDto();

    dto.setId(entity.getId());
    dto.setReferenceName(entity.getReferenceName());
    dto.setName(entity.getName());
    dto.setCountry(entity.getCountry());
    dto.setLocation(entity.getLocation());
    dto.setLatitude(entity.getLatitude());
    dto.setLongitude(entity.getLongitude());
    dto.setLength(entity.getLength());
    dto.setTurns(entity.getTurns());
    dto.setUrl(entity.getUrl());

    return dto;
  }
}
