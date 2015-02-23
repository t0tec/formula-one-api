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

import static org.hamcrest.Matchers.hasSize;
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
public class ITFindAllCircuitsPageableTest {

  private static final String REFERENCE_NAME = "albert_park";
  private static final String NAME = "Albert Park Grand Prix Circuit";

  private static final int pageNumber = 0;
  private static final int pageSize = 30;
  private static final int totalEntries = 72;

  private static final int linkSize = 4;

  @Autowired
  private WebApplicationContext webAppContext;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
  }

  @Test
  public void findAllShouldReturnResponseStatusOk() throws Exception {
    mockMvc.perform(get("/api/circuits"))
        .andExpect(status().isOk());
  }

  @Test
  @DatabaseSetup("classpath:circuit-no-data.xml")
  public void findAllPageableNoCircuitEntriesFoundShouldReturnEmptyListAsJson() throws Exception {
    mockMvc.perform(get("/api/circuits"))
        .andExpect(content().contentType(WebTestConstants.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$CircuitTable.circuits", hasSize(0)));
  }

  @Test
  @DatabaseSetup("classpath:circuit-data.xml")
  public void findAllPageableAsJson()
      throws Exception {
    mockMvc.perform(get("/api/circuits"))
        .andExpect(content().contentType(WebTestConstants.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$CircuitTable.number", is(pageNumber)))
        .andExpect(jsonPath("$CircuitTable.totalElements", is(totalEntries)))
        .andExpect(jsonPath("$CircuitTable.totalPages", is((int)Math.ceil((double) totalEntries / pageSize))))
        .andExpect(jsonPath("$CircuitTable.size", is(pageSize)))
        .andExpect(jsonPath("$CircuitTable.links", hasSize(linkSize)))
        .andExpect(jsonPath("$CircuitTable.circuits", hasSize(pageSize)))
        .andExpect(jsonPath("$CircuitTable.circuits[0].referenceName", is(REFERENCE_NAME)))
        .andExpect(jsonPath("$CircuitTable.circuits[0].name", is(NAME)));

  }
}
