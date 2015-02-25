package org.formulaone.repository;

import org.formulaone.core.model.Constructor;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface ConstructorRepository extends ReadOnlyRepository<Constructor, Long> {

  Constructor findByReferenceName(String referenceName);
}
