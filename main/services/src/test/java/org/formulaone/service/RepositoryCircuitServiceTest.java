package org.formulaone.service;

import com.nitorcreations.junit.runners.NestedRunner;

import org.formulaone.core.dto.CircuitDto;
import org.formulaone.core.exception.CircuitNotFoundException;
import org.formulaone.core.model.Circuit;
import org.formulaone.repository.CircuitRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@RunWith(NestedRunner.class)
public class RepositoryCircuitServiceTest {

  private static final Long ID = 1L;
  private static final String REFERENCE_NAME = "albert_park";
  private static final String NAME = "Albert Park Grand Prix Circuit";

  private static final Long NON_EXISTING_ID = 20L;

  private CircuitRepository repository;

  private RepositoryCircuitService service;

  @Before
  public void setUp() {
    repository = mock(CircuitRepository.class);
    service = new RepositoryCircuitService(repository);
  }

  public class FindAll {

    public class WhenNoCircuitEntryAreFound {

      @Test
      public void shouldReturnEmptyList() {
        given(repository.findAll()).willReturn(new ArrayList<Circuit>());

        List<CircuitDto> circuitEntries = service.findAll();

        assertThat(circuitEntries).isEmpty();
      }

      public class WhenOneCircuitEntryIsFound {

        @Test
        public void shouldReturnInformationOfFoundCircuitEntry() {
          Circuit found = new CircuitBuilder()
              .id(ID)
              .referenceName(REFERENCE_NAME)
              .name(NAME)
              .build();

          given(repository.findAll()).willReturn(Arrays.asList(found));

          List<CircuitDto> circuitEntries = service.findAll();

          assertThat(circuitEntries).hasSize(1);
          CircuitDto circuitEntry = circuitEntries.iterator().next();

          CircuitDtoAssert.assertThatCircuitEntry(circuitEntry)
              .hasId(ID)
              .hasReferenceName(REFERENCE_NAME)
              .hasName(NAME);
        }
      }
    }

  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  public class FindOne {

    public class WhenCircuitEntryIsNotFound {

      @Test
      public void shouldThrowExceptionWithCorrectId() {
        thrown.expect(CircuitNotFoundException.class);
        repository.findOne(NON_EXISTING_ID);
      }
    }

    public class WhenCircuitEntryIsFound {

      @Test
      public void shouldReturnInformationOfFoundCircuitEntry() {
        Circuit found = new CircuitBuilder()
            .id(ID)
            .referenceName(REFERENCE_NAME)
            .name(NAME)
            .build();

        given(repository.findOne(ID)).willReturn(found);

        CircuitDto returned = service.findById(ID);

        CircuitDtoAssert.assertThatCircuitEntry(returned)
            .hasId(ID)
            .hasReferenceName(REFERENCE_NAME)
            .hasName(NAME);
      }
    }
  }
}
