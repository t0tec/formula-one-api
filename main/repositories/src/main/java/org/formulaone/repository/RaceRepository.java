package org.formulaone.repository;

import org.formulaone.core.model.Race;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface RaceRepository extends ReadOnlyRepository<Race, Long>,
                                        QueryDslPredicateExecutor<Race> {

  @Query(value = "select ra from Race ra "
                 + "left join fetch ra.results re "
                 + "where ra.season.year = :year and ra.round = :round")
  Race findRaceAndResultsBySeasonYearAndRound(@Param("year") int year, @Param("round") int round);

  @Query(value = "select ra from Race ra "
                 + "left join fetch ra.results re "
                 + "where ra.id = (select max(ra.id) from Race ra "
                 + "where ra.results is not empty)")
  Race findLastRaceAndResults();

  @Query(value = "select ra from Race ra "
                 + "left join fetch ra.results re "
                 + "where ra.id = (select max(ra.id) from Race ra "
                 + "where ra.results is not empty) and re.positionOrder = :position")
  Race findLastRaceAndResultsWithPosition(@Param("position") int position);

  @Query(value = "select ra from Race ra "
                 + "left join fetch ra.results re "
                 + "where ra.season.year = :year "
                 + "and re.positionOrder = :position")
  List<Race> findRaceAndResultsBySeasonYearWithPosition(@Param("year") int year,
                                                        @Param("position") int position);

  @Query(value = "select ra from Race ra "
                 + "left join fetch ra.results re "
                 + "where ra.season.year = :year and ra.round = :round "
                 + "and re.positionOrder = :position")
  Race findRaceAndResultsBySeasonYearAndRoundWithPosition(@Param("year") int year,
                                                          @Param("round") int round,
                                                          @Param("position") int position);
}
