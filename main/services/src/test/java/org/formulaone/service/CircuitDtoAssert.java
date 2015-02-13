package org.formulaone.service;

import org.assertj.core.api.AbstractAssert;
import org.formulaone.core.dto.CircuitDto;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class provides a fluent API that can be used for writing assertions to
 * {@link org.formulaone.core.model.Circuit} objects.
 *
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class CircuitDtoAssert extends AbstractAssert<CircuitDtoAssert, CircuitDto> {

  private CircuitDtoAssert(CircuitDto actual) {
    super(actual, CircuitDtoAssert.class);
  }

  public static CircuitDtoAssert assertThatCircuitEntry(CircuitDto actual) {
    return new CircuitDtoAssert(actual);
  }

  CircuitDtoAssert hasId(Long expectedId) {
    isNotNull();

    Long actualId = actual.getId();
    assertThat(actualId)
        .overridingErrorMessage("Expected id to be <%d> but was <%d>",
                                expectedId,
                                actualId
        )
        .isEqualTo(expectedId);

    return this;
  }

  CircuitDtoAssert hasNoId() {
    isNotNull();

    Long actualId = actual.getId();
    assertThat(actualId)
        .overridingErrorMessage("Expected id to be <null> but was <%d>.", actualId)
        .isNull();

    return this;
  }

  CircuitDtoAssert hasReferenceName(String expectedReferenceName) {
    isNotNull();

    String actualReferenceName = actual.getReferenceName();
    assertThat(actualReferenceName)
        .overridingErrorMessage(String.format(
            "Expected referenceName to be <%s> but was <%s>.",
            expectedReferenceName,
            actualReferenceName
        ))
        .isEqualTo(expectedReferenceName);

    return this;
  }

  CircuitDtoAssert hasNoReferenceName() {
    isNotNull();

    String referenceName = actual.getReferenceName();
    assertThat(referenceName)
        .overridingErrorMessage("Expected referenceName to be <null> but was <%s>", referenceName)
        .isNull();

    return this;
  }

  CircuitDtoAssert hasName(String expectedName) {
    isNotNull();

    String actualName = actual.getName();
    assertThat(actualName)
        .overridingErrorMessage(
            "Expected name to be <%s> but was <%s>.",
            expectedName,
            actualName
        )
        .isEqualTo(actualName);

    return this;
  }

  CircuitDtoAssert hasNoName() {
    isNotNull();

    String name = actual.getName();
    assertThat(name)
        .overridingErrorMessage("Expected referenceName to be <null> but was <%s>", name)
        .isNull();

    return this;
  }

}
