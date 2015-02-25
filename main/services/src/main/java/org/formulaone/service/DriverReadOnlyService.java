package org.formulaone.service;

import org.formulaone.core.model.Driver;
import org.formulaone.service.dto.DriverDto;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface DriverReadOnlyService extends GenericService<Driver, DriverDto, Long> {

  /**
   * Finds a driver entry by using the referenceName given as a method parameter.
   *
   * @param referenceName The referenceName of the wanted entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         referenceName.
   */
  DriverDto findByReferenceName(String referenceName);
}
