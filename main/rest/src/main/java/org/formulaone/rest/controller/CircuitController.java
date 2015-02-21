package org.formulaone.rest.controller;

import org.formulaone.rest.wrapper.CircuitTable;
import org.formulaone.service.CircuitReadOnlyService;
import org.formulaone.service.dto.CircuitDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
   * Finds a single circuit entry by id.
   *
   * @param id The id of the requested circuit entry.
   * @return The information of the requested circuit entry.
   * @throws org.formulaone.core.exception.CircuitNotFoundException if no circuit entry is found by
   *                                                                using the given id.
   */
  @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
  @ResponseBody
  CircuitDto findById(@PathVariable("id") Long id) {
    logger.info("Finding circuit entry by using id: {}", id);

    CircuitDto circuitEntry = circuitReadOnlyService.findById(id);
    logger.info("Found circuit entry: {}", circuitEntry);

    return circuitEntry;
  }

  /**
   * Finds a single circuit entry by reference name.
   *
   * @param referenceName The referenceName of the requested circuit entry.
   * @return The information of the requested circuit entry.
   * @throws org.formulaone.core.exception.CircuitNotFoundException if no circuit entry is found by
   *                                                                using the given referenceName.
   */
  @RequestMapping(value = "/referenceName/{referenceName}", method = RequestMethod.GET)
  @ResponseBody
  CircuitDto findByReferenceName(@PathVariable("referenceName") String referenceName) {
    logger.info("Finding circuit entry by using referenceName: {}", referenceName);

    CircuitDto circuitEntry = circuitReadOnlyService.findByReferenceName(referenceName);
    logger.info("Found circuit entry: {}", circuitEntry);

    return circuitEntry;
  }

  /**
   * Finds all circuit entries.
   *
   * @return The information of all circuit entries.
   */
  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  CircuitTable findAll() {
    logger.info("Finding all circuit entries");

    List<CircuitDto> circuitEntries = circuitReadOnlyService.findAll();
    logger.info("Found {} circuit entries.", circuitEntries.size());

    CircuitTable circuitTable = new CircuitTable();
    circuitTable.setList(circuitEntries);

    return circuitTable;
  }

  /**
   * Finds all circuit entries sorted by a property.
   *
   * @param sort The parameter to sort the entries on
   * @return The information of all circuit entries sorted by sortOn.
   */
  @RequestMapping(value = "/sort", method = RequestMethod.GET)
  @ResponseBody
  CircuitTable findAllSortable(@RequestParam(defaultValue = "name") String sort,
                               @RequestParam(defaultValue = "ASC") String direction) {
    Sort sortening = new Sort(
        Sort.Direction.fromStringOrNull(direction), sort
    );

    List<CircuitDto> circuitEntries = circuitReadOnlyService.findAll(sortening);

    CircuitTable circuitTable = new CircuitTable();
    circuitTable.setList(circuitEntries);

    return circuitTable;
  }

  /**
   * Finds all circuit entries paginated.
   *
   * @param page the page you are requesting
   * @param size the size of entries per page
   * @return The information of all circuit entries paginated.
   */
  @RequestMapping(value = "/pages", method = RequestMethod.GET)
  @ResponseBody
  Page<CircuitDto> findAllPageable(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "30") int size) {
    Pageable pageable = new PageRequest(
        page, size
    );

    Page<CircuitDto> pageResult = circuitReadOnlyService.findAll(pageable);

    return pageResult;
  }
}
