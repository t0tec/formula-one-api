package org.formulaone.repository;

import org.formulaone.core.model.Season;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface SeasonRepository extends ReadOnlyRepository<Season, Long> {

  Season findByYear(int year);
}
