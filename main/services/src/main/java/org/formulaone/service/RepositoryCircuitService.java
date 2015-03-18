package org.formulaone.service;

import com.mysema.query.types.Predicate;

import org.formulaone.core.exception.NotFoundException;
import org.formulaone.core.model.Circuit;
import org.formulaone.core.model.QCircuit;
import org.formulaone.repository.CircuitRepository;
import org.formulaone.service.dto.CircuitDto;
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
public class RepositoryCircuitService extends RepositoryGenericService<Circuit, CircuitDto, Long>
    implements CircuitReadOnlyService {

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
  public CircuitDto findByReferenceName(String referenceName) {
    Circuit circuitEntry = circuitRepository.findOne(referenceNameEquals(referenceName));

    if (circuitEntry == null) {
      throw new NotFoundException(Circuit.class, "referenceName", referenceName);
    }

    return mapper.map(circuitEntry, dtoClass);
  }

  private static Predicate referenceNameEquals(final String referenceName) {
    QCircuit circuit = QCircuit.circuit;
    return circuit.referenceName.eq(referenceName);
  }
}
