package org.formulaone.service;

import org.formulaone.core.model.Race;
import org.formulaone.service.dto.RaceDto;
import org.formulaone.service.dto.SeasonDto;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface RaceReadOnlyService extends GenericService<Race, RaceDto, Long> {

  /**
   * Finds races entry by using the season given as a method parameter.
   *
   * @param season The season of the wanted entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season.
   */
  List<RaceDto> findBySeason(SeasonDto season);

  /**
   * Finds race entry by using the season and round given as a method parameter.
   *
   * @param season The season of the wanted entry.
   * @param round The round of the wanted entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season or round.
   */
  RaceDto findBySeasonAndRound(SeasonDto season, int round);
}
