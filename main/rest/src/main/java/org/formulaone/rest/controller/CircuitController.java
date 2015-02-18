package org.formulaone.rest.controller;

import org.formulaone.service.CircuitReadOnlyService;
import org.formulaone.service.dto.CircuitDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@RestController
@RequestMapping("/api/circuits")
public class CircuitController {

  private static final Logger logger = LoggerFactory.getLogger(CircuitController.class);

  private final CircuitReadOnlyService circuitReadOnlyService;

  @Autowired
  CircuitController(CircuitReadOnlyService circuitReadOnlyService) {
    this.circuitReadOnlyService = circuitReadOnlyService;
  }

  /**
   * Finds all circuit entries.
   *
   * @return The information of all circuit entries.
   */
  @RequestMapping(method = RequestMethod.GET, produces = {
      MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ResponseBody
  MyList findAll() {
    logger.info("Finding all circuit entries");

    List<CircuitDto> circuitEntries = circuitReadOnlyService.findAll();

    logger.info("Found {} circuit entries.", circuitEntries.size());

    MyList list = new MyList();
    list.setList(circuitEntries);

    return list;
  }

  /**
   * Finds a single circuit entry.
   *
   * @param id The id of the requested circuit entry.
   * @return The information of the requested circuit entry.
   * @throws org.formulaone.core.exception.CircuitNotFoundException if no circuit entry is found by
   *                                                                using the given id.
   */
  @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {
      MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ResponseBody
  CircuitDto findById(@PathVariable("id") Long id) {
    logger.info("Finding circuit entry by using id: {}", id);

    CircuitDto circuitEntry = circuitReadOnlyService.findById(id);
    logger.info("Found circuit entry: {}", circuitEntry);

    return circuitEntry;
  }
}
