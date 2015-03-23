package org.formulaone.service;

import org.formulaone.core.model.Status;
import org.formulaone.service.dto.StatusDto;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface StatusReadOnlyService extends GenericService<Status, StatusDto, Integer> {

  List<StatusDto> findAllBySeasonYear(int year);

  List<StatusDto> findAllBySeasonYearAndRound(int year, int round);
}
