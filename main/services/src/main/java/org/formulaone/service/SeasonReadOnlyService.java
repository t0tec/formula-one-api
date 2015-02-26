package org.formulaone.service;

import org.formulaone.core.model.Season;
import org.formulaone.service.dto.SeasonDto;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface SeasonReadOnlyService extends GenericService<Season, SeasonDto, Long> {

  /**
   * Finds a season entry by using the year given as a method parameter.
   *
   * @param year The year of the wanted entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found
   * with the given year.
   */
  SeasonDto findByYear(int year);
}
