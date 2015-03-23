package org.formulaone.repository;

import org.formulaone.core.model.ConstructorStandings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface ConstructorStandingsRepository
    extends ReadOnlyRepository<ConstructorStandings, Integer>,
            QueryDslPredicateExecutor<ConstructorStandings> {

  @Query(value = "select cost from ConstructorStandings cost "
                 + "where cost.race.season.year = :year and cost.race.round = "
                 + "(select max(ra.round) from Race ra where ra.season.year = cost.race.season.year "
                 + "and ra.results is not empty) "
                 + "order by cost.position")
  List<ConstructorStandings> findConstructorStandingsBySeason(@Param("year") int year);

  @Query(value = "select cost from ConstructorStandings cost "
                 + "where cost.position = :position and cost.race.round = "
                 + "(select max(ra.round) from Race ra where ra.season.year = cost.race.season.year) "
                 + "order by cost.race.season.year")
  List<ConstructorStandings> findConstructorStandingsBySeasonsAndPosition(@Param("position") int position);

  @Query(value = "select cost from ConstructorStandings cost "
                 + "where cost.race.round = "
                 + "(select max(ra.round) from Race ra where ra.season.year = cost.race.season.year) "
                 + "and cost.constructor.referenceName = :constructorName "
                 + "order by cost.race.season.year")
  List<ConstructorStandings> findConstructorStandingsBySeasonsAndConstructor(
      @Param("constructorName") String constructorName);
}
