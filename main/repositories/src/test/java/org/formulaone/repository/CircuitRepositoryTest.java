package org.formulaone.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import org.formulaone.core.model.Circuit;
import org.formulaone.repository.config.ExampleApplicationContext;
import org.formulaone.repository.config.Profiles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(Profiles.INTEGRATION_TEST)
@ContextConfiguration(classes = {ExampleApplicationContext.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
                         DirtiesContextTestExecutionListener.class,
                         TransactionalTestExecutionListener.class,
                         DbUnitTestExecutionListener.class})
public class CircuitRepositoryTest {

  private static final Long ID = 1L;
  private static final String REFERENCE_NAME = "albert_park";
  private static final String NAME = "Albert Park Grand Prix Circuit";

  private static final int totalEntries = 72;

  private static final Long NON_EXISTING_ID = -1L;
  private static final String WRONG_REFERENCE_NAME = "park_avenue";

  private static final String sortById = "id";
  private static final Sort.Direction directionDesc = Sort.Direction.DESC;

  private static final int pageNumber = 0;
  private static final int pageSize = 30;
  private static final Pageable pageable = new PageRequest(pageNumber, pageSize);

  @Autowired
  private CircuitRepository circuitRepository;

  @Test
  @DatabaseSetup("classpath:circuit-data.xml")
  public void testFindCircuitById() {
    Circuit circuit = circuitRepository.findOne(ID);

    assertThat(circuit).isNotNull();
    assertThat(circuit.getId()).isEqualTo(ID);
    assertThat(circuit.getReferenceName()).isEqualTo(REFERENCE_NAME);
    assertThat(circuit.getName()).isEqualTo(NAME);
  }

  @Test
  @DatabaseSetup("classpath:circuit-data.xml")
  public void testReturnNullCircuitWithCorrectId() {
    Circuit circuit = circuitRepository.findOne(NON_EXISTING_ID);

    assertThat(circuit).isNull();
  }

  @Test
  @DatabaseSetup("classpath:circuit-no-data.xml")
  public void testReturnEmptyList() {
    List<Circuit> circuitEntries = (List<Circuit>) circuitRepository.findAll();

    assertThat(circuitEntries).isEmpty();
  }

  @Test
  @DatabaseSetup("classpath:circuit-data.xml")
  public void testReturnList() {
    List<Circuit> circuitEntries = (List<Circuit>) circuitRepository.findAll();

    assertThat(circuitEntries).hasSize(totalEntries);
  }

  @Test
  @DatabaseSetup("classpath:circuit-data.xml")
  public void testReturnListSorted() {

    Sort sort = new Sort(directionDesc, sortById);

    List<Circuit> sortedCircuitEntries = (List<Circuit>) circuitRepository.findAll(sort);

    assertThat(sortedCircuitEntries).hasSize(totalEntries);
    assertThat(sortedCircuitEntries.get(sortedCircuitEntries.size() - 1).getId()).isEqualTo(ID);
  }

  @Test
  @DatabaseSetup("classpath:circuit-data.xml")
  public void testReturnsCircuitPage() {
    Page<Circuit> circuitPage = circuitRepository.findAll(pageable);

    PageAssertion.assertThat(circuitPage)
        .hasTotalPages((int) Math.ceil((double) totalEntries / pageSize))
        .hasTotalElements(totalEntries)
        .hasPageSize(pageSize)
        .hasPageNumber(pageNumber)
        .hasContentSize(pageSize);
  }

  @Test
  @DatabaseSetup("classpath:circuit-data.xml")
  public void testFindCircuitByReferenceName() {
    Circuit circuit = circuitRepository.findByReferenceName(REFERENCE_NAME);

    assertThat(circuit).isNotNull();
    assertThat(circuit.getId()).isEqualTo(ID);
    assertThat(circuit.getReferenceName()).isEqualTo(REFERENCE_NAME);
    assertThat(circuit.getName()).isEqualTo(NAME);
  }

  @Test
  @DatabaseSetup("classpath:circuit-data.xml")
  public void testReturnNullCircuitWithWrongReferenceName() {
    Circuit circuit = circuitRepository.findByReferenceName(WRONG_REFERENCE_NAME);

    assertThat(circuit).isNull();
  }
}
