package org.formulaone.repository;

import org.formulaone.core.model.Constructor;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface ConstructorRepository extends ReadOnlyRepository<Constructor,Integer>,
                                               QueryDslPredicateExecutor<Constructor>,
                                               JpaSpecificationExecutor<Constructor> {

  @Query(value = "select co from Constructor co "
                 + "join co.results re "
                 + "join re.race ra "
                 + "where ra.season.year = :year "
                 + "group by co.id "
                 + "order by co.name")
  List<Constructor> findConstructorsBySeason(@Param("year") int year);

  @Query(value = "select co from Constructor co "
                 + "join co.results re "
                 + "join re.race ra "
                 + "where ra.season.year = :year and ra.round = :round "
                 + "group by co.id "
                 + "order by co.name")
  List<Constructor> findConstructorsBySeasonAndRound(@Param("year") int year,
                                                     @Param("round") int round);
}
