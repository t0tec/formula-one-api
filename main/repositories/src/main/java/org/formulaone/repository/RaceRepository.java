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
public interface RaceRepository extends ReadOnlyRepository<Race, Integer>,
                                        QueryDslPredicateExecutor<Race> {

  // --------------------------------------RESULTS--------------------------------------------
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

  // --------------------------------------QUALIFYING-----------------------------------------
  @Query(value = "select ra from Race ra "
                 + "left join fetch ra.qualifyings qu "
                 + "where ra.season.year = :year and ra.round = :round")
  Race findRaceAndQualifyingBySeasonYearAndRound(@Param("year") int year,
                                                 @Param("round") int round);

  // --------------------------------------LAP TIMES------------------------------------------
  @Query(value = "select ra from Race ra "
                 + "left join fetch ra.lapTimes la "
                 + "where ra.season.year = :year and ra.round = :round")
  Race findRaceAndLapTimesBySeasonYearAndRound(@Param("year") int year, @Param("round") int round);

  @Query(value = "select ra from Race ra "
                 + "left join fetch ra.lapTimes la "
                 + "where ra.season.year = :year and ra.round = :round and la.lap = :lap")
  Race findRaceAndLapTimesBySeasonYearAndRoundAndLap(@Param("year") int year,
                                                     @Param("round") int round,
                                                     @Param("lap") int lap);

  @Query(value = "select ra from Race ra "
                 + "left join fetch ra.lapTimes la "
                 + "where ra.season.year = :year and ra.round = :round "
                 + "and la.driver.referenceName = :referenceName")
  Race findRaceAndLapTimesBySeasonYearAndRoundAndDriver(@Param("year") int year,
                                                        @Param("round") int round,
                                                        @Param("referenceName") String referenceName);

  @Query(value = "select ra from Race ra "
                 + "left join fetch ra.lapTimes la "
                 + "where ra.season.year = :year and ra.round = :round and la.lap = :lap "
                 + "and la.driver.referenceName = :referenceName")
  Race findRaceAndLapTimesBySeasonYearAndRoundAndLapAndDriver(@Param("year") int year,
                                                              @Param("round") int round,
                                                              @Param("lap") int lap,
                                                              @Param("referenceName") String referenceName);

  // --------------------------------------PIT STOPS------------------------------------------
  @Query(value = "select ra from Race ra "
                 + "left join fetch ra.pitStops pi "
                 + "where ra.season.year = :year and ra.round = :round")
  Race findRaceAndPitStopsBySeasonYearAndRound(@Param("year") int year, @Param("round") int round);

  @Query(value = "select ra from Race ra "
                 + "left join fetch ra.pitStops pi "
                 + "where ra.season.year = :year and ra.round = :round "
                 + "and pi.stop = :stop")
  Race findRaceAndPitStopsBySeasonYearAndRoundAndStop(@Param("year") int year,
                                                      @Param("round") int round,
                                                      @Param("stop") int stop);

  @Query(value = "select ra from Race ra "
                 + "left join fetch ra.pitStops pi "
                 + "where ra.season.year = :year and ra.round = :round "
                 + "and pi.driver.referenceName = :referenceName")
  Race findRaceAndPitStopsBySeasonYearAndRoundAndDriver(@Param("year") int year,
                                                        @Param("round") int round,
                                                        @Param("referenceName") String referenceName);

  @Query(value = "select ra from Race ra "
                 + "left join fetch ra.pitStops pi "
                 + "where ra.season.year = :year and ra.round = :round "
                 + "and pi.stop = :stop and pi.driver.referenceName = :referenceName")
  Race findRaceAndPitStopsBySeasonYearAndRoundAndStopAndDriver(@Param("year") int year,
                                                               @Param("round") int round,
                                                               @Param("stop") int stop,
                                                               @Param("referenceName") String referenceName);

  @Query(value = "select ra from Race ra "
                 + "left join fetch ra.pitStops pi "
                 + "where ra.season.year = :year and ra.round = :round "
                 + "and pi.lap = :lap")
  Race findRaceAndPitStopsBySeasonYearAndRoundAndLap(@Param("year") int year,
                                                     @Param("round") int round,
                                                     @Param("lap") int lap);

  @Query(value = "select ra from Race ra "
                 + "left join fetch ra.pitStops pi "
                 + "where ra.season.year = :year and ra.round = :round "
                 + "and pi.lap = :lap and pi.driver.referenceName = :referenceName")
  Race findRaceAndPitStopsBySeasonYearAndRoundAndLapAndDriver(@Param("year") int year,
                                                              @Param("round") int round,
                                                              @Param("lap") int lap,
                                                              @Param("referenceName") String referenceName);
}
