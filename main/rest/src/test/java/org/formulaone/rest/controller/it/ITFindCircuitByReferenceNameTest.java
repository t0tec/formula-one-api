package org.formulaone.rest.controller.it;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import org.formulaone.repository.config.Profiles;
import org.formulaone.rest.controller.WebTestConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(Profiles.INTEGRATION_TEST)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@DbUnitConfiguration(dataSetLoader = ColumnSensingReplacementDataSetLoader.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
                         DirtiesContextTestExecutionListener.class,
                         TransactionalTestExecutionListener.class,
                         DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class ITFindCircuitByReferenceNameTest {

  private static final Long ID = 1L;
  private static final String REFERENCE_NAME = "albert_park";
  private static final String NAME = "Albert Park Grand Prix Circuit";

  private static final String ERROR_MESSAGE_KEY_CIRCUIT_ENTRY_NOT_FOUND
      = "No circuit entry was found by using referenceName: " + REFERENCE_NAME;

  @Autowired
  private WebApplicationContext webAppContext;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
  }

  @Test
  @DatabaseSetup("classpath:circuit-no-data.xml")
  public void findByReferenceNameEntryNotFound() throws Exception {
    mockMvc.perform(get("/api/circuits/{$referenceName}", REFERENCE_NAME))
        .andExpect(status().isNotFound());
  }

  @Test
  @DatabaseSetup("classpath:circuit-no-data.xml")
  public void findByReferenceNameEntryNotFoundAsErrorMessageJson() throws Exception {
    mockMvc.perform(get("/api/circuits/{$referenceName}", REFERENCE_NAME))
        .andExpect(content().contentType(WebTestConstants.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$error.code", is(WebTestConstants.ERROR_CODE_ENTRY_NOT_FOUND)))
        .andExpect(jsonPath("$error.message", is(ERROR_MESSAGE_KEY_CIRCUIT_ENTRY_NOT_FOUND)));

  }

  @Test
  @DatabaseSetup("classpath:circuit-data.xml")
  public void findByReferenceNameEntryFoundAsJson()
      throws Exception {
    mockMvc.perform(get("/api/circuits/{$referenceName}", REFERENCE_NAME))
        .andExpect(status().isOk())
        .andExpect(content().contentType(WebTestConstants.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$circuit.referenceName", is(REFERENCE_NAME)))
        .andExpect(jsonPath("$circuit.name", is(NAME)));
  }
}
