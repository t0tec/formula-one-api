package org.formulaone.repository;

import org.formulaone.core.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface StatusRepository extends ReadOnlyRepository<Status,Integer> {

  @Query(value =
      "select new org.formulaone.core.model.Status(st.id, st.status, count(st.id)) from Status st "
      + "join st.results re "
      + "join re.race ra "
      + "where st.id = :id "
      + "group by st.id")
  Status findOne(@Param("id") Integer id);

  @Query(value =
      "select new org.formulaone.core.model.Status(st.id, st.status, count(st.id)) from Status st "
      + "join st.results re "
      + "join re.race ra "
      + "group by st.id "
      + "order by st.id")
  Iterable<Status> findAll();

  @Query(value =
      "select new org.formulaone.core.model.Status(st.id, st.status, count(st.id)) from Status st "
      + "join st.results re "
      + "join re.race ra "
      + "group by st.id")
  Iterable<Status> findAll(Sort sort);

  @Query(value =
      "select new org.formulaone.core.model.Status(st.id, st.status, count(st.id)) from Status st "
      + "join st.results re "
      + "join re.race ra "
      + "group by st.id "
      + "order by st.id")
  Page<Status> findAll(Pageable pageable);

  @Query(value =
      "select new org.formulaone.core.model.Status(st.id, st.status, count(st.id)) from Status st "
      + "join st.results re "
      + "join re.race ra "
      + "where ra.season.year = :year "
      + "group by st.id "
      + "order by st.id")
  List<Status> findAllBySeasonYear(@Param("year") int year);

  @Query(value =
      "select new org.formulaone.core.model.Status(st.id, st.status, count(st.id)) from Status st "
      + "join st.results re "
      + "join re.race ra "
      + "where ra.season.year = :year and ra.round = :round "
      + "group by st.id "
      + "order by st.id")
  List<Status> findAllBySeasonYearAndRound(@Param("year") int year, @Param("round") int round);
}
