package org.formulaone.repository;

import org.formulaone.core.model.Result;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface ResultRepository extends ReadOnlyRepository<Result, Integer> {

  List<Result> findResultByPositionOrder(int positionOrder);

  List<Result> findResultByPositionOrderAndRaceSeasonYear(int positionOrder, int year);

  Result findResultByPositionOrderAndRaceSeasonYearAndRaceRound(int positionOrder, int year,
                                                                int round);
}
