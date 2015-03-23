package org.formulaone.rest.controller;

import com.nitorcreations.junit.runners.NestedRunner;

import org.formulaone.core.exception.NotFoundException;
import org.formulaone.core.model.Circuit;
import org.formulaone.service.CircuitReadOnlyService;
import org.formulaone.service.dto.CircuitDto;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@RunWith(NestedRunner.class)
public class CircuitControllerTest {

  private static final Logger logger = LoggerFactory.getLogger(CircuitControllerTest.class);

  private static final Locale CURRENT_LOCALE = Locale.US;

  private static final Integer ID = 1;
  private static final String REFERENCE_NAME = "albert_park";
  private static final String NAME = "Albert Park Grand Prix Circuit";

  private static final Integer NON_EXISTING_ID = -1;
  private static final String NON_REFERENCE_NAME = "NOT_IMPORTANT";

  private static final String ERROR_MESSAGE_KEY_CIRCUIT_ENTRY_NOT_FOUND
      = "No circuit entry was found by using id: " + NON_EXISTING_ID;

  private MockMvc mockMvc;

  private CircuitReadOnlyService circuitReadOnlyService;

  private StaticMessageSource messageSource;

  @Before
  public void setUp() {
    circuitReadOnlyService = mock(CircuitReadOnlyService.class);

    messageSource = new StaticMessageSource();
    messageSource.setUseCodeAsDefaultMessage(true);

    mockMvc = MockMvcBuilders.standaloneSetup(new CircuitController(circuitReadOnlyService))
        .setHandlerExceptionResolvers(WebTestConfig.restErrorHandler(messageSource))
        .setLocaleResolver(WebTestConfig.fixedLocaleResolver(CURRENT_LOCALE))
        .setMessageConverters(WebTestConfig.jacksonDateTimeConverter())
        .setValidator(WebTestConfig.validator())
        .build();
  }


  public class FindAll {

    public class WhenNoCircuitEntriesAreFound {

      @Test
      public void shouldReturnEmptyListAsJson() throws Exception {
        given(circuitReadOnlyService.findAll()).willReturn(new ArrayList<CircuitDto>());

        mockMvc.perform(get("/api/circuits/all"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(WebTestConstants.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$CircuitTable.circuits", hasSize(0)));
      }
    }

    public class WhenOneCircuitEntryIsFound {

      @Test
      public void shouldReturnOneCircuitEntryAsJson() throws Exception {
        CircuitDto found = new CircuitDtoBuilder()
            .referenceName(REFERENCE_NAME)
            .name(NAME)
            .id(ID)
            .build();

        given(circuitReadOnlyService.findAll()).willReturn(Arrays.asList(found));

        mockMvc.perform(get("/api/circuits/all"))
            .andExpect(content().contentType(WebTestConstants.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$CircuitTable.circuits", hasSize(1)))
            .andExpect(jsonPath("$CircuitTable.circuits[0].referenceName", is(REFERENCE_NAME)))
            .andExpect(jsonPath("$CircuitTable.circuits[0].name", is(NAME)));
      }
    }
  }

  public class FindById {

    public class WhenCircuitEntryIsNotFound {

      @Ignore
      @Test
      public void shouldReturnErrorMessageAsJson() throws Exception {
        given(circuitReadOnlyService.findById(ID))
            .willThrow(new NotFoundException(Circuit.class, "id", NON_EXISTING_ID));

        MvcResult
            result =
            mockMvc.perform(get("/api/circuits/id/{id}", NON_EXISTING_ID)).andReturn();

        String content = result.getResponse().getContentAsString();

        logger.info("{}", content);

//        TODO: need to fix this test to get error message
        mockMvc.perform(get("/api/circuits/{id}", NON_EXISTING_ID))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(WebTestConstants.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$error.code", is(WebTestConstants.ERROR_CODE_ENTRY_NOT_FOUND)))
            .andExpect(jsonPath("$error.message", is(ERROR_MESSAGE_KEY_CIRCUIT_ENTRY_NOT_FOUND)));
      }
    }

    public class WhenCircuitEntryIsFound {

      @Test
      public void shouldReturnInformationOfFoundCircuitEntryAsJson() throws Exception {
        CircuitDto found = new CircuitDtoBuilder()
            .referenceName(REFERENCE_NAME)
            .name(NAME)
            .id(ID)
            .build();

        given(circuitReadOnlyService.findById(ID)).willReturn(found);

        mockMvc.perform(get("/api/circuits/id/{id}", ID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(WebTestConstants.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$CircuitTable.circuit.referenceName", is(REFERENCE_NAME)))
            .andExpect(jsonPath("$CircuitTable.circuit.name", is(NAME)));
      }
    }
  }

  public class FindByReferenceName {

    public class WhenCircuitEntryIsNotFound {

      @Ignore
      @Test
      public void shouldReturnErrorMessageAsJson() throws Exception {
        given(circuitReadOnlyService.findByReferenceName(NON_REFERENCE_NAME))
            .willThrow(new NotFoundException(Circuit.class, "referenceName", NON_REFERENCE_NAME));

        MvcResult result =
            mockMvc.perform(get("/api/circuits/{referenceName}", NON_REFERENCE_NAME)).andReturn();

        String content = result.getResponse().getContentAsString();

        logger.info("{}", content);

//        TODO: need to fix this test to get error message
        mockMvc.perform(get("/api/circuits/{id}", NON_EXISTING_ID))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(WebTestConstants.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$error.code", is(WebTestConstants.ERROR_CODE_ENTRY_NOT_FOUND)))
            .andExpect(jsonPath("$error.message", is(ERROR_MESSAGE_KEY_CIRCUIT_ENTRY_NOT_FOUND)));
      }
    }

    public class WhenCircuitEntryIsFound {

      @Test
      public void shouldReturnInformationOfFoundCircuitEntryAsJson() throws Exception {
        CircuitDto found = new CircuitDtoBuilder()
            .referenceName(REFERENCE_NAME)
            .name(NAME)
            .id(ID)
            .build();

        given(circuitReadOnlyService.findByReferenceName(REFERENCE_NAME)).willReturn(found);

        mockMvc.perform(get("/api/circuits/{referenceName}", REFERENCE_NAME))
            .andExpect(status().isOk())
            .andExpect(content().contentType(WebTestConstants.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$CircuitTable.circuit.referenceName", is(REFERENCE_NAME)))
            .andExpect(jsonPath("$CircuitTable.circuit.name", is(NAME)));
      }
    }
  }
}
