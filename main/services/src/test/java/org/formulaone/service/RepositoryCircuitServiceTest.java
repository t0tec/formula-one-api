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

  private CircuitRepository circuitRepository;

  private RepositoryCircuitService circuitService;

  @Before
  public void setUp() {
    circuitRepository = mock(CircuitRepository.class);
    circuitService = new RepositoryCircuitService(circuitRepository);
  }

  public class FindAll {

    public class WhenNoCircuitEntryAreFound {

      @Test
      public void shouldReturnEmptyList() {
        given(circuitRepository.findAll()).willReturn(new ArrayList<Circuit>());

        List<CircuitDto> circuitEntries = circuitService.findAll();

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

          given(circuitRepository.findAll()).willReturn(Arrays.asList(found));

          List<CircuitDto> circuitEntries = circuitService.findAll();

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
        circuitRepository.findOne(NON_EXISTING_ID);
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

        given(circuitRepository.findOne(ID)).willReturn(found);

        CircuitDto returned = circuitService.findById(ID);

        CircuitDtoAssert.assertThatCircuitEntry(returned)
            .hasId(ID)
            .hasReferenceName(REFERENCE_NAME)
            .hasName(NAME);
      }
    }
  }

  public class FindByName {

    public class WhenCircuitEntryIsNotFound {

      @Test
      public void shouldThrowExceptionWithCorrectId() {
        thrown.expect(CircuitNotFoundException.class);
        circuitRepository.findByName(NAME);
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

        given(circuitRepository.findByName(NAME)).willReturn(found);

        CircuitDto returned = circuitService.findByName(NAME);

        CircuitDtoAssert.assertThatCircuitEntry(returned)
            .hasId(ID)
            .hasReferenceName(REFERENCE_NAME)
            .hasName(NAME);
      }
    }
  }
}
