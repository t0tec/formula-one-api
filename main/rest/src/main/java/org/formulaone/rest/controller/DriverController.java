package org.formulaone.rest.controller;

import com.wordnik.swagger.annotations.Api;

import org.formulaone.rest.wrapper.DriverPage;
import org.formulaone.rest.wrapper.DriverResource;
import org.formulaone.rest.wrapper.DriverTable;
import org.formulaone.service.DriverReadOnlyService;
import org.formulaone.service.dto.DriverDto;
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
@Api(value = "drivers", description = "drivers")
@RestController
@RequestMapping("/api")
public class DriverController {

  private static final Logger logger = LoggerFactory.getLogger(DriverController.class);

  private final DriverReadOnlyService driverReadOnlyService;

  @Autowired
  DriverController(DriverReadOnlyService driverReadOnlyService) {
    this.driverReadOnlyService = driverReadOnlyService;
  }

  /**
   * Finds a single driver entry by id.
   *
   * @param id The id of the requested entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found by using the given
   *                                                         id.
   */
  @RequestMapping(value = "/drivers/id/{id}", method = RequestMethod.GET)
  @ResponseBody
  DriverResource findById(@PathVariable("id") Long id) {
    logger.info("Finding driver entry by using id: {}", id);

    DriverDto driverEntry = driverReadOnlyService.findById(id);
    logger.info("Found driver entry: {}", driverEntry);

    DriverResource resource = new DriverResource(driverEntry);
    resource.add(linkTo(DriverController.class)
                     .slash("drivers").slash(driverEntry.getId()).withSelfRel());

    return resource;
  }

  /**
   * Finds a single driver entry by reference name.
   *
   * @param referenceName The referenceName of the requested entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found by using the given
   *                                                         referenceName.
   */
  @RequestMapping(value = "drivers/{referenceName}", method = RequestMethod.GET)
  @ResponseBody
  DriverResource findByReferenceName(@PathVariable("referenceName") String referenceName) {
    logger.info("Finding driver entry by using referenceName: {}", referenceName);

    DriverDto driverEntry = driverReadOnlyService.findByReferenceName(referenceName);
    logger.info("Found driver entry: {}", driverEntry);

    DriverResource resource = new DriverResource(driverEntry);
    resource.add(linkTo(DriverController.class)
                     .slash("drivers").slash(driverEntry.getReferenceName()).withSelfRel());

    return resource;
  }

  /**
   * Finds all driver entries.
   *
   * @return The information of all entries.
   */
  @RequestMapping(value = "drivers/all", method = RequestMethod.GET)
  @ResponseBody
  DriverTable findAll() {
    logger.info("Finding all driver entries");

    List<DriverDto> driverEntries = driverReadOnlyService.findAll();
    logger.info("Found {} driver entries.", driverEntries.size());

    return new DriverTable(driverEntries);
  }

  /**
   * Finds all driver entries sorted by a property.
   *
   * @param sort The parameter to sort the entries on
   * @return The information of all entries sorted by sortOn.
   */
  @RequestMapping(value = "drivers/sort", method = RequestMethod.GET)
  @ResponseBody
  DriverTable findAllSortable(@RequestParam(defaultValue = "name") String sort,
                              @RequestParam(defaultValue = "ASC") String direction) {
    Sort sortOn = new Sort(
        Sort.Direction.fromStringOrNull(direction), sort
    );

    List<DriverDto> driverEntries = driverReadOnlyService.findAll(sortOn);
    logger.info("Found {} driver entries sort on {} {}.", driverEntries.size(), sort,
                direction);

    return new DriverTable(driverEntries);
  }

  /**
   * Finds all driver entries paginated.
   *
   * @param page the page you are requesting
   * @param size the size of entries per page
   * @return The information of all entries paginated.
   */
  @RequestMapping(value = "/drivers", method = RequestMethod.GET)
  @ResponseBody
  DriverPage findAllPageable(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "30") int size) {
    Pageable pageable = new PageRequest(
        page, size
    );

    Page<DriverDto> pageResult = driverReadOnlyService.findAll(pageable);
    logger.info("Found {} driver entries paged by {} {}.", pageResult.getNumberOfElements(), page,
                size);

    return new DriverPage(pageResult, "page", "size");
  }

  /**
   * Finds all driver entries who have driven in a particular season
   *
   * @return The information of all entries.
   */
  @RequestMapping(value = "/{season}/drivers", method = RequestMethod.GET)
  @ResponseBody
  DriverTable findDriversBySeason(@PathVariable("season") int year) {
    logger.info("Finding all driver entries in a particular season({})", year);

    List<DriverDto> driverEntries = driverReadOnlyService.findDriversBySeason(year);
    logger.info("Found {} driver entries.", driverEntries.size());

    return new DriverTable(driverEntries);
  }

  /**
   * Finds all driver entries who have driven in a particular season and round
   *
   * @return The information of all entries.
   */
  @RequestMapping(value = "/{season}/{round}/drivers", method = RequestMethod.GET)
  @ResponseBody
  DriverTable findDriversBySeason(@PathVariable("season") int year,
                                  @PathVariable("round") int round) {
    logger.info("Finding all driver entries in a particular season({}) and round({})", year, round);

    List<DriverDto> driverEntries = driverReadOnlyService.findDriversBySeasonAndRound(year, round);
    logger.info("Found {} driver entries.", driverEntries.size());

    return new DriverTable(driverEntries);
  }
}
