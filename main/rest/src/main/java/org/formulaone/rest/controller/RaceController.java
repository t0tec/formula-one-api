package org.formulaone.rest.controller;

import org.formulaone.rest.wrapper.RacePage;
import org.formulaone.rest.wrapper.RaceResource;
import org.formulaone.rest.wrapper.RaceTable;
import org.formulaone.service.RaceReadOnlyService;
import org.formulaone.service.dto.RaceDto;
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

import java.util.Calendar;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@RestController
@RequestMapping("/api/races")
public class RaceController {

  private static final Logger logger = LoggerFactory.getLogger(RaceController.class);

  private final RaceReadOnlyService raceReadOnlyService;

  @Autowired
  RaceController(RaceReadOnlyService raceReadOnlyService) {
    this.raceReadOnlyService = raceReadOnlyService;
  }

  /**
   * Finds a single race entry by id.
   *
   * @param id The id of the requested entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found by using the given
   *                                                         id.
   */
  @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
  @ResponseBody
  RaceResource findById(@PathVariable("id") Long id) {
    logger.info("Finding race entry by using id: {}", id);

    RaceDto raceEntry = raceReadOnlyService.findById(id);
    logger.info("Found race entry: {}", raceEntry);

    RaceResource resource = new RaceResource(raceEntry);
    resource.add(linkTo(RaceController.class).slash(raceEntry.getId()).withSelfRel());

    return resource;
  }

  /**
   * Finds all race entries.
   *
   * @return The information of all entries.
   */
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  @ResponseBody
  RaceTable findAll() {
    logger.info("Finding all races entries");

    List<RaceDto> raceEntries = raceReadOnlyService.findAll();
    logger.info("Found {} races entries.", raceEntries.size());

    return new RaceTable(raceEntries);
  }

  /**
   * Finds all race entries sorted by a property.
   *
   * @param sort The parameter to sort the entries on
   * @return The information of all entries sorted by sortOn.
   */
  @RequestMapping(value = "/sort", method = RequestMethod.GET)
  @ResponseBody
  RaceTable findAllSortable(@RequestParam(defaultValue = "name") String sort,
                            @RequestParam(defaultValue = "ASC") String direction) {
    Sort sortOn = new Sort(
        Sort.Direction.fromStringOrNull(direction), sort
    );

    List<RaceDto> raceEntries = raceReadOnlyService.findAll(sortOn);
    logger.info("Found {} race entries sort on {} {}.", raceEntries.size(), sort,
                direction);

    return new RaceTable(raceEntries);
  }

  /**
   * Finds all race entries paginated.
   *
   * @param page the page you are requesting
   * @param size the size of entries per page
   * @return The information of all entries paginated.
   */
  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  RacePage findAllPageable(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "30") int size) {
    Pageable pageable = new PageRequest(
        page, size
    );

    Page<RaceDto> pageResult = raceReadOnlyService.findAll(pageable);
    logger.info("Found {} race entries paged by {} {}.", pageResult.getNumberOfElements(), page,
                size);

    return new RacePage(pageResult, "page", "size");
  }

  /**
   * Finds all race entries by year.
   *
   * @param year The year of the requested entry.
   * @return The information of all entries for a year (season).
   */
  @RequestMapping(value = "/{year}", method = RequestMethod.GET)
  @ResponseBody
  RaceTable findBySeason(@PathVariable("year") int year) {
    List<RaceDto> raceEntries = raceReadOnlyService.findBySeasonYear(year);
    logger.info("Found {} race entries for year {}.", raceEntries.size(), year);

    return new RaceTable(raceEntries);
  }

  /**
   * Finds all race entries by current year.
   *
   * @return The information of all entries for a year (season).
   */
  @RequestMapping(value = "/current", method = RequestMethod.GET)
  @ResponseBody
  RaceTable findByCurrentSeason() {
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    List<RaceDto> raceEntries = raceReadOnlyService.findBySeasonYear(currentYear);
    logger.info("Found {} race entries for current({}) year.", raceEntries.size(), currentYear);

    return new RaceTable(raceEntries);
  }

  /**
   * Finds a race entry by year and round.
   *
   * @param year  The year of the requested entry.
   * @param round The round of the requested entry.
   * @return The information of the requested entry.
   */
  @RequestMapping(value = "/{year}/{round}", method = RequestMethod.GET)
  @ResponseBody
  RaceResource findBySeasonAndRound(@PathVariable("year") int year,
                                    @PathVariable("round") int round) {
    RaceDto raceEntry = raceReadOnlyService.findBySeasonYearAndRound(year, round);

    RaceResource resource = new RaceResource(raceEntry);
    resource.add(linkTo(RaceController.class).slash(raceEntry.getSeason().getYear())
                     .slash(raceEntry.getRound()).withSelfRel());
    logger.info("Found {} race entry by year {} and round {}.", raceEntry, year);

    return resource;
  }

  /**
   * Finds a race entry and the results by year and round.
   *
   * @param year  The year of the requested entry.
   * @param round The round of the requested entry.
   * @return The information of the requested entry.
   */
  @RequestMapping(value = "/{year}/{round}/results", method = RequestMethod.GET)
  @ResponseBody
  RaceResource findRaceAndResultsBySeasonAndRound(@PathVariable("year") int year,
                                                  @PathVariable("round") int round) {
    RaceDto raceEntry = raceReadOnlyService.findRaceAndResultsBySeasonYearAndRound(year, round);

    RaceResource resource = new RaceResource(raceEntry);
    resource.add(linkTo(RaceController.class).slash(raceEntry.getSeason().getYear())
                     .slash(raceEntry.getRound()).slash("results").withSelfRel());
    logger.info("Found {} race entry by year {} and round {}.", raceEntry, year);

    return resource;
  }

}
