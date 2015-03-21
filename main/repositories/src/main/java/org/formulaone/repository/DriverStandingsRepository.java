package org.formulaone.repository;

import org.formulaone.core.model.DriverStandings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface DriverStandingsRepository
    extends ReadOnlyRepository<DriverStandings, Long>,
            QueryDslPredicateExecutor<DriverStandings> {

  @Query(value = "select dist from DriverStandings dist "
                 + "where dist.race.season.year = :year and dist.race.round = "
                 + "(select max(ra.round) from Race ra where ra.season.year = dist.race.season.year) "
                 + "order by dist.position")
  List<DriverStandings> findDriverStandingsBySeason(@Param("year") int year);

  @Query(value = "select dist from DriverStandings dist "
                 + "where dist.position = :position and dist.race.round = "
                 + "(select max(ra.round) from Race ra where ra.season.year = dist.race.season.year) "
                 + "order by dist.race.season.year")
  List<DriverStandings> findDriverStandingsBySeasonsAndPosition(
      @Param("position") int position);

  @Query(value = "select dist from DriverStandings dist "
                 + "where dist.race.round = "
                 + "(select max(ra.round) from Race ra where ra.season.year = dist.race.season.year) "
                 + "and dist.driver.referenceName = :driverName "
                 + "order by dist.race.season.year")
  List<DriverStandings> findDriverStandingsBySeasonsAndDriver(
      @Param("driverName") String driverName);
}
