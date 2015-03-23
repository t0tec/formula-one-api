package org.formulaone.service;

import org.formulaone.core.model.Driver;
import org.formulaone.service.dto.DriverDto;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface DriverReadOnlyService extends GenericService<Driver, DriverDto, Integer> {

  /**
   * Finds a driver entry by using the referenceName given as a method parameter.
   *
   * @param referenceName The referenceName of the wanted entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         referenceName.
   */
  DriverDto findByReferenceName(String referenceName);

  /**
   * Finds all drivers that have driven in a particular season.
   *
   * @param year The year of the wanted entry.
   * @return The information of the requested entry.
   */
  List<DriverDto> findDriversBySeason(int year);

  /**
   * Finds all drivers that have driven in a particular season and particular round.
   *
   * @param year The year of the wanted entry.
   * @param round The round of the wanted entry.
   * @return The information of the requested entry.
   */
  List<DriverDto> findDriversBySeasonAndRound(int year, int round);
}
