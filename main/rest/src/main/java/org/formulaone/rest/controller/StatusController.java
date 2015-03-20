package org.formulaone.rest.controller;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import org.formulaone.rest.wrapper.StatusPage;
import org.formulaone.rest.wrapper.StatusResource;
import org.formulaone.rest.wrapper.StatusTable;
import org.formulaone.service.StatusReadOnlyService;
import org.formulaone.service.dto.StatusDto;
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
@Api(value = "status", description = "Returns a list of all finishing status codes"
    , position = 6)
@RestController
@RequestMapping("/api")
public class StatusController {

  private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

  private final StatusReadOnlyService statusReadOnlyService;

  @Autowired
  StatusController(StatusReadOnlyService statusReadOnlyService) {
    this.statusReadOnlyService = statusReadOnlyService;
  }

  /**
   * Finds a single status entry by id.
   *
   * @param id The id of the requested entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found by using the given
   *                                                         id.
   */
  @ApiOperation(value = "Returns a status entry",
      notes = "Finds a single status entry by a unique id")
  @RequestMapping(value = "/status/{id}", method = RequestMethod.GET)
  @ResponseBody
  StatusResource findById(@PathVariable("id") Long id) {
    logger.info("Finding status entry by using id: {}", id);

    StatusDto statusEntry = statusReadOnlyService.findById(id);
    logger.info("Found status entry: {}", statusEntry);

    StatusResource resource = new StatusResource(statusEntry);
    resource.add(linkTo(StatusController.class).slash("status")
                     .slash(statusEntry.getId()).withSelfRel());

    return resource;
  }

  /**
   * Finds all status entries.
   *
   * @return The information of all entries.
   */
  @ApiOperation(value = "Returns a list of all status codes and the total count per code",
      notes = "Finds a list of status entries ordered by the unique id")
  @RequestMapping(value = "/status/all", method = RequestMethod.GET)
  @ResponseBody
  StatusTable findAll() {
    logger.info("Finding all status entries");

    List<StatusDto> statusEntries = statusReadOnlyService.findAll();
    logger.info("Found {} status entries.", statusEntries.size());

    return new StatusTable(statusEntries);
  }

  /**
   * Finds all status entries paginated.
   *
   * @param page the page you are requesting
   * @param size the size of entries per page
   * @return The information of all entries paginated.
   */
  @ApiOperation(value = "Returns a list of status codes and the total count per code limited by a query parameter",
      notes =
          "The number of results that are returned can be controlled using a limit query parameter. "
          + "Please use the smallest value that your application needs. If not specified, the default value is 30. "
          + "A page number into the url can also be specified using a page query parameter. "
          + "A page numbers starts at 0. For example: 'api/status?page=1&size=50' returns the second page of status code information containing fifty entries per page.")
  @RequestMapping(value = "/status", method = RequestMethod.GET)
  @ResponseBody
  StatusPage findAllPageable(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "30") int size) {
    Pageable pageable = new PageRequest(
        page, size
    );

    Page<StatusDto> pageResult = statusReadOnlyService.findAll(pageable);
    logger.info("Found {} status entries paged by {} {}.", pageResult.getNumberOfElements(), page,
                size);

    return new StatusPage(pageResult, "page", "size");
  }

  /**
   * Finds all status entries by season year.
   *
   * @return The information of all entries.
   */
  @ApiOperation(value = "Returns a list of status codes and the total count per code in a particular season",
      notes = "Finds a list of status codes in a particular season ")
  @RequestMapping(value = "/{year}/status", method = RequestMethod.GET)
  @ResponseBody
  StatusTable findAllBySeasonYear(@PathVariable("year") int year) {
    logger.info("Finding all status entries by season year: {}", year);

    List<StatusDto> statusEntries = statusReadOnlyService.findAllBySeasonYear(year);
    logger.info("Found {} status entries.", statusEntries.size());

    return new StatusTable(statusEntries);
  }

  /**
   * Finds all status entries by season year and round.
   *
   * @return The information of all entries.
   */
  @ApiOperation(value = "Returns a list of status codes and the total count per code in a particular season and round",
      notes = "Finds a list of status codes in a particular season and round")
  @RequestMapping(value = "/{year}/{round}/status", method = RequestMethod.GET)
  @ResponseBody
  StatusTable findAllBySeasonYearAndRound(@PathVariable("year") int year,
                                          @PathVariable("round") int round) {
    logger.info("Finding all status entries by season year and round: {}, {}", year, round);

    List<StatusDto> statusEntries = statusReadOnlyService.findAllBySeasonYearAndRound(year, round);
    logger.info("Found {} status entries.", statusEntries.size());

    return new StatusTable(statusEntries);
  }
}
