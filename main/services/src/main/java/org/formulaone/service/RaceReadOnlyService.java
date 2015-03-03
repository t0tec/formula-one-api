package org.formulaone.service;

import org.formulaone.core.model.Race;
import org.formulaone.service.dto.RaceDto;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface RaceReadOnlyService extends GenericService<Race, RaceDto, Long> {

  /**
   * Finds races entry by using the season year given as a method parameter.
   *
   * @param year The year of the wanted entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season.
   */
  List<RaceDto> findBySeasonYear(int year);

  /**
   * Finds a race entry by using the season year and round given as a method parameter.
   *
   * @param year  The year of the wanted entry.
   * @param round The round of the wanted entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season or round.
   */
  RaceDto findBySeasonYearAndRound(int year, int round);

  /**
   * Finds a race entry and the results by using the season year and round given as a method
   * parameter.
   *
   * @param year  The year of the wanted entry.
   * @param round The round of the wanted entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season or round.
   */
  RaceDto findRaceAndResultsBySeasonYearAndRound(int year, int round);

  /**
   * Finds the last race entry and the results
   * parameter.
   *
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found
   */
  RaceDto findLastRaceAndResults();
}
