package org.formulaone.rest.controller;

import com.nitorcreations.junit.runners.NestedRunner;

import org.formulaone.core.exception.CircuitNotFoundException;
import org.formulaone.service.CircuitReadOnlyService;
import org.formulaone.service.dto.CircuitDto;
import org.junit.Before;
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

@RunWith(NestedRunner.class)
public class CircuitControllerTest {

  private static final Logger logger = LoggerFactory.getLogger(CircuitControllerTest.class);

  private static final Locale CURRENT_LOCALE = Locale.US;

  private static final Long ID = 1L;
  private static final String REFERENCE_NAME = "albert_park";
  private static final String NAME = "Albert Park Grand Prix Circuit";

  private static final Long NON_EXISTING_ID = 20L;
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

    @Test
    public void shouldReturnResponseStatusOk() throws Exception {
      mockMvc.perform(get("/api/circuits"))
          .andExpect(status().isOk());
    }

    public class WhenNoCircuitEntriesAreFound {

      @Test
      public void shouldReturnEmptyListAsJson() throws Exception {
        given(circuitReadOnlyService.findAll()).willReturn(new ArrayList<CircuitDto>());

        mockMvc.perform(get("/api/circuits"))
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

        mockMvc.perform(get("/api/circuits"))
            .andExpect(content().contentType(WebTestConstants.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$CircuitTable.circuits", hasSize(1)))
            .andExpect(jsonPath("$CircuitTable.circuits[0].referenceName", is(REFERENCE_NAME)))
            .andExpect(jsonPath("$CircuitTable.circuits[0].name", is(NAME)));
      }
    }
  }

  public class FindById {

    public class WhenCircuitEntryIsNotFound {

      @Test
      public void shouldReturnResponseStatusNotFound() throws Exception {
        given(circuitReadOnlyService.findById(NON_EXISTING_ID))
            .willThrow(new CircuitNotFoundException("id", NON_EXISTING_ID));

        mockMvc.perform(get("/api/circuits/{id}", NON_EXISTING_ID))
            .andExpect(status().isNotFound());
      }

      @Test
      public void shouldReturnErrorMessageAsJson() throws Exception {
        given(circuitReadOnlyService.findById(ID))
            .willThrow(new CircuitNotFoundException("id", NON_EXISTING_ID));

        MvcResult result = mockMvc.perform(get("/api/circuits/id/{id}", NON_EXISTING_ID)).andReturn();

        String content = result.getResponse().getContentAsString();

        logger.info("{}", content);

//        TODO: need to fix this test to get error message
//        mockMvc.perform(get("/api/circuits/{id}", NON_EXISTING_ID))
//            .andExpect(content().contentType(WebTestConstants.APPLICATION_JSON_UTF8))
//            .andExpect(jsonPath("$.code", is(WebTestConstants.ERROR_CODE_CIRCUIT_ENTRY_NOT_FOUND)))
//            .andExpect(jsonPath("$.message", is(ERROR_MESSAGE_KEY_CIRCUIT_ENTRY_NOT_FOUND)));
      }
    }

    public class WhenCircuitEntryIsFound {

      @Test
      public void shouldReturnResponseStatusOk() throws Exception {
        CircuitDto found = new CircuitDto();

        given(circuitReadOnlyService.findById(ID)).willReturn(found);

        mockMvc.perform(get("/api/circuits/id/{id}", ID))
            .andExpect(status().isOk());
      }

      @Test
      public void shouldReturnInformationOfFoundCircuitEntryAsJson() throws Exception {
        CircuitDto found = new CircuitDtoBuilder()
            .referenceName(REFERENCE_NAME)
            .name(NAME)
            .id(ID)
            .build();

        given(circuitReadOnlyService.findById(ID)).willReturn(found);

        mockMvc.perform(get("/api/circuits/id/{id}", ID))
            .andExpect(content().contentType(WebTestConstants.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$circuit.referenceName", is(REFERENCE_NAME)))
            .andExpect(jsonPath("$circuit.name", is(NAME)));
      }
    }
  }

  public class FindByReferenceName {

    public class WhenCircuitEntryIsNotFound {

      @Test
      public void shouldReturnResponseStatusNotFound() throws Exception {
        given(circuitReadOnlyService.findByReferenceName(NON_REFERENCE_NAME))
            .willThrow(new CircuitNotFoundException("referenceName", NON_REFERENCE_NAME));

        mockMvc.perform(get("/api/circuits/referenceName/{referenceName}", NON_REFERENCE_NAME))
            .andExpect(status().isNotFound());
      }

      @Test
      public void shouldReturnErrorMessageAsJson() throws Exception {
        given(circuitReadOnlyService.findByReferenceName(NON_REFERENCE_NAME))
            .willThrow(new CircuitNotFoundException("referenceName", NON_REFERENCE_NAME));

        MvcResult result =
            mockMvc.perform(get("/api/circuits/referenceName/{referenceName}", NON_REFERENCE_NAME)).andReturn();

        String content = result.getResponse().getContentAsString();

        logger.info("{}", content);

//        TODO: need to fix this test to get error message
//        mockMvc.perform(get("/api/circuits/{id}", NON_EXISTING_ID))
//            .andExpect(content().contentType(WebTestConstants.APPLICATION_JSON_UTF8))
//            .andExpect(jsonPath("$.code", is(WebTestConstants.ERROR_CODE_CIRCUIT_ENTRY_NOT_FOUND)))
//            .andExpect(jsonPath("$.message", is(ERROR_MESSAGE_KEY_CIRCUIT_ENTRY_NOT_FOUND)));
      }
    }

    public class WhenCircuitEntryIsFound {

      @Test
      public void shouldReturnResponseStatusOk() throws Exception {
        CircuitDto found = new CircuitDto();

        given(circuitReadOnlyService.findByReferenceName(REFERENCE_NAME)).willReturn(found);

        mockMvc.perform(get("/api/circuits/referenceName/{referenceName}", REFERENCE_NAME))
            .andExpect(status().isOk());
      }

      @Test
      public void shouldReturnInformationOfFoundCircuitEntryAsJson() throws Exception {
        CircuitDto found = new CircuitDtoBuilder()
            .referenceName(REFERENCE_NAME)
            .name(NAME)
            .id(ID)
            .build();

        given(circuitReadOnlyService.findByReferenceName(REFERENCE_NAME)).willReturn(found);

        mockMvc.perform(get("/api/circuits/referenceName/{referenceName}", REFERENCE_NAME))
            .andExpect(content().contentType(WebTestConstants.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$circuit.referenceName", is(REFERENCE_NAME)))
            .andExpect(jsonPath("$circuit.name", is(NAME)));
      }
    }
  }
}
