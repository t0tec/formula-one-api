package org.formulaone.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import org.formulaone.core.model.Circuit;
import org.formulaone.repository.config.ApplicationContext;
import org.formulaone.repository.config.Profiles;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(classes = {ApplicationContext.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
                         DirtiesContextTestExecutionListener.class,
                         TransactionalTestExecutionListener.class,
                         DbUnitTestExecutionListener.class})
public class CircuitRepositoryTest {

  private static final Long ID = 1L;
  private static final String REFERENCE_NAME = "albert_park";
  private static final String NAME = "Albert Park Grand Prix Circuit";

  private static final Long NON_EXISTING_ID = 20L;

  @Autowired
  private CircuitRepository circuitRepository;

  @Test
  @DatabaseSetup("classpath:circuit-no-data.xml")
  public void testReturnEmptyList() {
    List<Circuit> circuitEntries = (List<Circuit>) circuitRepository.findAll();

    assertThat(circuitEntries).isEmpty();
  }

  @Test
  @DatabaseSetup("classpath:circuit-data.xml")
  public void testReturnListWithSizeOne() {
    List<Circuit> circuitEntries = (List<Circuit>) circuitRepository.findAll();

    assertThat(circuitEntries).hasSize(1);
  }

  @Test
  @DatabaseSetup("classpath:circuit-data.xml")
  public void testFindCircuitById() {
    Circuit circuit = circuitRepository.findOne(ID);

    Assert.assertNotNull(circuit);
    Assert.assertEquals(REFERENCE_NAME, circuit.getReferenceName());
    Assert.assertEquals(NAME, circuit.getName());
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  @DatabaseSetup("classpath:circuit-no-data.xml")
  public void testReturnNullCircuitWithCorrectId() {
    Circuit circuit = circuitRepository.findOne(ID);

    Assert.assertNull(circuit);
  }

  @Test
  @DatabaseSetup("classpath:circuit-data.xml")
  public void testFindCircuitByName() {
    Circuit circuit = circuitRepository.findByName(NAME);

    Assert.assertNotNull(circuit);
    Assert.assertEquals(REFERENCE_NAME, circuit.getReferenceName());
    Assert.assertEquals(NAME, circuit.getName());
  }

}
