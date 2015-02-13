package org.formulaone.rest;

import org.formulaone.repository.CircuitRepository;
import org.formulaone.service.RepositoryCircuitService;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class Main {
  private RepositoryCircuitService service;

  private CircuitRepository repository;

  private void temp() {
    repository.findByName("name"); // TODO: repository should not be accessible in rest module

  }
}
