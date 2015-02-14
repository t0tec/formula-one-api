package org.formulaone.rest;

import org.formulaone.repository.CircuitRepository;
import org.formulaone.service.CircuitReadOnlyService;
import org.formulaone.service.RepositoryCircuitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class Main {

  private CircuitReadOnlyService service;

  private CircuitRepository repository;

  private RepositoryCircuitService repoCircuitService;

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    Main main = new Main();
    main.temp();
  }

  private void temp() {
    // TODO: hide implementation
    // Hide with excluding the repositories dependency in maven???
    repoCircuitService = new RepositoryCircuitService(repository);

    service.findByName("Albert Park Grand Prix Circuit"); // this is ok
  }
}
