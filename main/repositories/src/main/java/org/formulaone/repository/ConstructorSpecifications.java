package org.formulaone.repository;

import org.formulaone.core.model.Constructor;
import org.formulaone.core.model.Race;
import org.formulaone.core.model.Result;
import org.formulaone.core.model.Season;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ConstructorSpecifications {

  public static Specification<Constructor> constructorsBySeasonYear(final int year) {

    return new Specification<Constructor>() {

      @Override
      public Predicate toPredicate(Root<Constructor> root, CriteriaQuery<?> query,
                                   CriteriaBuilder cb) {

        final Join<Constructor, Result> results = root.join("results");
        final Join<Result, Race> races = results.join("race");

        Path<Season> season = races.<Season>get("season");

        query.groupBy(root.get("id"));
        query.orderBy(cb.asc(root.get("name")));

        return cb.equal(season.<Integer>get("year"), year);
      }
    };
  }
}
