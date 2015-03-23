package org.formulaone.service;

import com.nitorcreations.junit.runners.NestedRunner;

import org.dozer.DozerBeanMapper;
import org.formulaone.core.exception.NotFoundException;
import org.formulaone.core.model.Circuit;
import org.formulaone.core.model.QCircuit;
import org.formulaone.repository.CircuitRepository;
import org.formulaone.service.dto.CircuitDto;
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

  private static final Integer ID = 1;
  private static final String REFERENCE_NAME = "albert_park";
  private static final String NAME = "Albert Park Grand Prix Circuit";

  private static final Integer NON_EXISTING_ID = -1;

  private CircuitRepository circuitRepository;

  private RepositoryCircuitService circuitService;

  @Before
  public void setUp() {
    circuitRepository = mock(CircuitRepository.class);
    circuitService = new RepositoryCircuitService(circuitRepository);
    circuitService.mapper = new DozerBeanMapper();
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
        thrown.expect(NotFoundException.class);
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

  public class FindByReferenceName {

    public class WhenCircuitEntryIsNotFound {

      @Test
      public void shouldThrowExceptionWithCorrectId() {
        thrown.expect(NotFoundException.class);
        circuitRepository.findOne(QCircuit.circuit.referenceName.eq("unknown"));
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

        given(circuitRepository.findOne(QCircuit.circuit.referenceName.eq(REFERENCE_NAME)))
            .willReturn(found);

        CircuitDto returned = circuitService.findByReferenceName(REFERENCE_NAME);

        CircuitDtoAssert.assertThatCircuitEntry(returned)
            .hasId(ID)
            .hasReferenceName(REFERENCE_NAME)
            .hasName(NAME);
      }
    }
  }
}
