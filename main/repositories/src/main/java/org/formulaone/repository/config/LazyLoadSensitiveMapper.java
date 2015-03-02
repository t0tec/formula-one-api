package org.formulaone.repository.config;

import org.dozer.CustomFieldMapper;
import org.dozer.classmap.ClassMap;
import org.dozer.fieldmap.FieldMap;
import org.hibernate.collection.internal.AbstractPersistentCollection;

public class LazyLoadSensitiveMapper implements CustomFieldMapper {

  @Override
  public boolean mapField(Object source, Object destination, Object sourceFieldValue,
                          ClassMap classMap, FieldMap fieldMap) {
    // If field is initialized, Dozer will continue mapping

    // Check if field is derived from Persistent Collection
    if (!(sourceFieldValue instanceof AbstractPersistentCollection)) {
      // Allow Dozer to map as normal
      return false;
    }

    // Check if field is already initialized
    if (((AbstractPersistentCollection) sourceFieldValue).wasInitialized()) {
      // Allow Dozer to map as normal
      return false;
    }

    return true;
  }
}
