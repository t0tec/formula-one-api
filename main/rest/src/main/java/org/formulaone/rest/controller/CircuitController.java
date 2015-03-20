package org.formulaone.rest.controller;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

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
@Api(value = "circuits", description = "Returns information about the circuits"
                                       + " where Formula One races are hosted"
    , position = 1)
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
  @ApiOperation(value = "Returns a circuit entry",
      notes = "Finds a single circuit entry by a unique id", position = 1)
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
  @ApiOperation(value = "Returns a circuit entry",
      notes = "Finds a single circuit entry by a unique reference name", position = 2)
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
  @ApiOperation(value = "Returns a list of all circuits",
      notes = "Finds a list of circuit entries ordered alphabetically", position = 3)
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  @ResponseBody
  CircuitTable findAll() {
    logger.info("Finding all circuit entries");

    List<CircuitDto> circuitEntries = circuitReadOnlyService.findAll();
    logger.info("Found {} circuit entries.", circuitEntries.size());

    return new CircuitTable(circuitEntries);
  }

  /**
   * Finds all circuit entries paginated.
   *
   * @param page the page you are requesting
   * @param size the size of entries per page
   * @return The information of all entries paginated.
   */
  @ApiOperation(value = "Returns a list of circuits limited by a query parameter",
      notes =
          "The number of results that are returned can be controlled using a limit query parameter. "
          + "Please use the smallest value that your application needs. If not specified, the default value is 30. "
          + "A page number into the url can also be specified using a page query parameter. "
          + "A page numbers starts at 0. For example: 'api/circuits?page=1&size=50' returns the second page of circuit information containing fifty entries per page."
      , position = 4)
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
