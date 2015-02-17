package org.formulaone.service;

import org.formulaone.service.dto.CircuitDto;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface CircuitReadOnlyService {

  /**
   * Finds all circuit entries that are saved to the database.
   */
  List<CircuitDto> findAll();

  /**
   * Finds a circuit entry by using the id given as a method parameter.
   *
   * @param id The id of the wanted circuit entry.
   * @return The information of the requested circuit entry.
   * @throws org.formulaone.core.exception.CircuitNotFoundException if no circuit entry is found
   *                                                                with the given id.
   */
  CircuitDto findById(Long id);

  /**
   * Finds a circuit entry by using the name given as a method parameter.
   *
   * @param name The name of the wanted circuit entry.
   * @return The information of the requested circuit entry.
   * @throws org.formulaone.core.exception.CircuitNotFoundException if no circuit entry is found
   *                                                                with the given id.
   */
  CircuitDto findByName(String name);
}
