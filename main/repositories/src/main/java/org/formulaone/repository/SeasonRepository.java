package org.formulaone.repository;

import org.formulaone.core.model.Season;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface SeasonRepository extends ReadOnlyRepository<Season, Long>,
                                          QueryDslPredicateExecutor<Season> {

}
