package org.formulaone.rest.controller;

import com.wordnik.swagger.annotations.Api;

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
@Api(value = "status", description = "status")
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
  @RequestMapping(value = "/status/all", method = RequestMethod.GET)
  @ResponseBody
  StatusTable findAll() {
    logger.info("Finding all status entries");

    List<StatusDto> statusEntries = statusReadOnlyService.findAll();
    logger.info("Found {} status entries.", statusEntries.size());

    return new StatusTable(statusEntries);
  }

  /**
   * Finds all status entries sorted by a property.
   *
   * @param sort The parameter to sort the entries on
   * @return The information of all entries sorted by sortOn.
   */
  @RequestMapping(value = "/status/sort", method = RequestMethod.GET)
  @ResponseBody
  StatusTable findAllSortable(@RequestParam(defaultValue = "name") String sort,
                              @RequestParam(defaultValue = "ASC") String direction) {
    Sort sortOn = new Sort(
        Sort.Direction.fromStringOrNull(direction), sort
    );

    List<StatusDto> statusEntries = statusReadOnlyService.findAll(sortOn);
    logger.info("Found {} status entries sort on {} {}.", statusEntries.size(), sort, direction);

    return new StatusTable(statusEntries);
  }

  /**
   * Finds all status entries paginated.
   *
   * @param page the page you are requesting
   * @param size the size of entries per page
   * @return The information of all entries paginated.
   */
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
