package org.formulaone.rest.controller;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

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
@Api(value = "drivers", description = "Returns information about the drivers"
                                      + " that are active or have taken part in Formula One"
    , position = 3)
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
   * Finds a single driver entry by reference name.
   *
   * @param referenceName The referenceName of the requested entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found by using the given
   *                                                         referenceName.
   */
  @ApiOperation(value = "Returns a driver entry",
      notes = "Finds a single driver entry by a unique reference name")
  @RequestMapping(value = "drivers/{referenceName}", method = RequestMethod.GET)
  @ResponseBody
  DriverResource findByReferenceName(
      @ApiParam(value = "Unique reference name of a driver", required = true)
      @PathVariable("referenceName") String referenceName) {
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
  @ApiOperation(value = "Returns a list of all drivers",
      notes = "Finds a list of driver entries ordered alphabetically")
  @RequestMapping(value = "drivers/all", method = RequestMethod.GET)
  @ResponseBody
  DriverTable findAll() {
    logger.info("Finding all driver entries");

    List<DriverDto> driverEntries = driverReadOnlyService.findAll();
    logger.info("Found {} driver entries.", driverEntries.size());

    return new DriverTable(driverEntries);
  }

  /**
   * Finds all driver entries paginated.
   *
   * @param page the page you are requesting
   * @param size the size of entries per page
   * @return The information of all entries paginated.
   */
  @ApiOperation(value = "Returns a list of drivers limited by a query parameter",
      notes =
          "The number of results that are returned can be controlled using a limit query parameter. "
          + "Please use the smallest value that your application needs. If not specified, the default value is 30. "
          + "A page number into the url can also be specified using a page query parameter. "
          + "A page numbers starts at 0. For example: 'api/drivers?page=1&size=50' returns the second page of driver information containing fifty entries per page.")
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
  @ApiOperation(value = "Returns a list of drivers entries who have taken part in a particular season",
      notes = "Finds a list of driver entries who have taken part in a particular season ordered alphabetically")
  @RequestMapping(value = "/{year}/drivers", method = RequestMethod.GET)
  @ResponseBody
  DriverTable findDriversBySeason(
      @ApiParam(value = "The year of a particular season", required = true)
      @PathVariable("year") int year) {
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
  @ApiOperation(value = "Returns a list of drivers entries who have taken part in a particular season and round",
      notes = "Finds a list of driver entries who have taken part in a particular season and round ordered alphabetically")
  @RequestMapping(value = "/{year}/{round}/drivers", method = RequestMethod.GET)
  @ResponseBody
  DriverTable findDriversBySeason(
      @ApiParam(value = "The year of a particular season", required = true) @PathVariable("year") int year,
      @ApiParam(value = "The round of a particular season", required = true) @PathVariable("round") int round) {
    logger.info("Finding all driver entries in a particular season({}) and round({})", year, round);

    List<DriverDto> driverEntries = driverReadOnlyService.findDriversBySeasonAndRound(year, round);
    logger.info("Found {} driver entries.", driverEntries.size());

    return new DriverTable(driverEntries);
  }
}
