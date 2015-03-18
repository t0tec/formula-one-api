package org.formulaone.repository;

import org.formulaone.core.model.Driver;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface DriverRepository extends ReadOnlyRepository<Driver, Long>,
                                          QueryDslPredicateExecutor<Driver> {

  @Query(value = "select dr from Driver dr "
                 + "join dr.results re "
                 + "join re.race ra "
                 + "where ra.season.year = :year "
                 + "group by dr.id "
                 + "order by dr.surname")
  List<Driver> findDriversBySeason(@Param("year") int year);

  @Query(value = "select dr from Driver dr "
                 + "join dr.results re "
                 + "join re.race ra "
                 + "where ra.season.year = :year and ra.round = :round "
                 + "group by dr.id "
                 + "order by dr.surname")
  List<Driver> findDriversBySeasonAndRound(@Param("year") int year, @Param("round") int round);
}
