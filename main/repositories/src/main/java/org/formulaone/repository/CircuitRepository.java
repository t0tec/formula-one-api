package org.formulaone.repository;

import org.formulaone.core.model.Circuit;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface CircuitRepository extends ReadOnlyRepository<Circuit, Long> {

  Circuit findByName(String name);
}
