package org.formulaone.repository;

import org.formulaone.core.model.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface StatusRepository extends ReadOnlyRepository<Status, Long> {

  @Query(value =
      "select new org.formulaone.core.model.Status(st.id, st.status, count(st.id)) from Status st "
      + "join st.results re "
      + "join re.race ra "
      + "where ra.season.year = :year "
      + "group by st.id")
  List<Status> findAllBySeasonYear(@Param("year") int year);

  @Query(value =
      "select new org.formulaone.core.model.Status(st.id, st.status, count(st.id)) from Status st "
      + "join st.results re "
      + "join re.race ra "
      + "where ra.season.year = :year and ra.round = :round "
      + "group by st.id")
  List<Status> findAllBySeasonYearAndRound(@Param("year") int year, @Param("round") int round);
}
