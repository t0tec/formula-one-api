package org.formulaone.service;

import org.formulaone.core.model.Constructor;
import org.formulaone.service.dto.ConstructorDto;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface ConstructorReadOnlyService
    extends GenericService<Constructor, ConstructorDto, Long> {

  /**
   * Finds a constructor entry by using the referenceName given as a method parameter.
   *
   * @param referenceName The referenceName of the wanted entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         referenceName.
   */
  ConstructorDto findByReferenceName(String referenceName);
}
