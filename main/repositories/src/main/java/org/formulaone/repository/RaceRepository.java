package org.formulaone.repository;

import org.formulaone.core.model.Race;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface RaceRepository extends ReadOnlyRepository<Race, Long> {

  List<Race> findBySeasonYear(int year);

  Race findBySeasonYearAndRound(int year, int round);

  @Query(value =
      "select ra from Race ra "
      + "left join fetch ra.results re "
      + "where ra.season.year = :year and ra.round = :round")
  Race findRaceAndResultsBySeasonYearAndRound(@Param("year") int year, @Param("round") int round);
}
