package org.formulaone.repository;

import org.formulaone.core.model.Race;
import org.formulaone.core.model.Season;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface RaceRepository extends ReadOnlyRepository<Race, Long> {

  List<Race> findBySeason(Season season);

  Race findBySeasonAndRound(Season season, int round);
}
