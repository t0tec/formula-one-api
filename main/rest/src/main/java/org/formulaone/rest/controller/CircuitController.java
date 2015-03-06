package org.formulaone.rest.controller;

import com.wordnik.swagger.annotations.Api;

import org.formulaone.rest.wrapper.CircuitPage;
import org.formulaone.rest.wrapper.CircuitResource;
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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@Api(value = "circuits", description = "circuits")
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
   * @param id The id of the requested entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found by using the given
   *                                                         id.
   */
  @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
  @ResponseBody
  CircuitResource findById(@PathVariable("id") Long id) {
    logger.info("Finding circuit entry by using id: {}", id);

    CircuitDto circuitEntry = circuitReadOnlyService.findById(id);
    logger.info("Found circuit entry: {}", circuitEntry);

    CircuitResource resource = new CircuitResource(circuitEntry);
    resource.add(linkTo(CircuitController.class).slash(circuitEntry.getId()).withSelfRel());

    return resource;
  }

  /**
   * Finds a single circuit entry by reference name.
   *
   * @param referenceName The referenceName of the requested entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found by using the given
   *                                                         referenceName.
   */
  @RequestMapping(value = "/{referenceName}", method = RequestMethod.GET)
  @ResponseBody
  CircuitResource findByReferenceName(@PathVariable("referenceName") String referenceName) {
    logger.info("Finding circuit entry by using referenceName: {}", referenceName);

    CircuitDto circuitEntry = circuitReadOnlyService.findByReferenceName(referenceName);
    logger.info("Found circuit entry: {}", circuitEntry);

    CircuitResource resource = new CircuitResource(circuitEntry);
    resource
        .add(linkTo(CircuitController.class).slash(circuitEntry.getReferenceName()).withSelfRel());

    return resource;
  }

  /**
   * Finds all circuit entries.
   *
   * @return The information of all entries.
   */
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  @ResponseBody
  CircuitTable findAll() {
    logger.info("Finding all circuit entries");

    List<CircuitDto> circuitEntries = circuitReadOnlyService.findAll();
    logger.info("Found {} circuit entries.", circuitEntries.size());

    return new CircuitTable(circuitEntries);
  }

  /**
   * Finds all circuit entries sorted by a property.
   *
   * @param sort The parameter to sort the entries on
   * @return The information of all entries sorted by sortOn.
   */
  @RequestMapping(value = "/sort", method = RequestMethod.GET)
  @ResponseBody
  CircuitTable findAllSortable(@RequestParam(defaultValue = "name") String sort,
                               @RequestParam(defaultValue = "ASC") String direction) {
    Sort sortOn = new Sort(
        Sort.Direction.fromStringOrNull(direction), sort
    );

    List<CircuitDto> circuitEntries = circuitReadOnlyService.findAll(sortOn);
    logger.info("Found {} circuit entries sort on {} {}.", circuitEntries.size(), sort, direction);

    return new CircuitTable(circuitEntries);
  }

  /**
   * Finds all circuit entries paginated.
   *
   * @param page the page you are requesting
   * @param size the size of entries per page
   * @return The information of all entries paginated.
   */
  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  CircuitPage findAllPageable(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "30") int size) {
    Pageable pageable = new PageRequest(
        page, size
    );

    Page<CircuitDto> pageResult = circuitReadOnlyService.findAll(pageable);
    logger.info("Found {} circuit entries paged by {} {}.", pageResult.getNumberOfElements(), page,
                size);

    return new CircuitPage(pageResult, "page", "size");
  }
}
