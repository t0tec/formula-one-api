package org.formulaone.repository;

import org.formulaone.core.model.Driver;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface DriverRepository extends ReadOnlyRepository<Driver, Long> {

  Driver findByReferenceName(String referenceName);
}
