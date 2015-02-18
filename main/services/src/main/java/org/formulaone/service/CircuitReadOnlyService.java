package org.formulaone.service;

import org.formulaone.core.model.Circuit;
import org.formulaone.service.dto.CircuitDto;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface CircuitReadOnlyService extends GenericService<Circuit, CircuitDto, Long> {

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
