package org.formulaone.rest.controller;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

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
@Api(value = "races", description = "Returns information about the schedule of races and results"
                                    + " in Formula One")
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
   * Finds all race entries.
   *
   * @return The information of all entries.
   */
  @ApiOperation(value = "Returns a list of all races",
      notes = "Finds a list of race entries ordered alphabetically")
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  @ResponseBody
  RaceTable findAll() {
    logger.info("Finding all races entries");

    List<RaceDto> raceEntries = raceReadOnlyService.findAll();
    logger.info("Found {} races entries.", raceEntries.size());

    return new RaceTable(raceEntries);
  }

  /**
   * Finds all race entries paginated.
   *
   * @param page the page you are requesting
   * @param size the size of entries per page
   * @return The information of all entries paginated.
   */
  @ApiOperation(value = "Returns a list of races limited by a query parameter",
      notes =
          "The number of results that are returned can be controlled using a limit query parameter. "
          + "Please use the smallest value that your application needs. If not specified, the default value is 30. "
          + "A page number into the url can also be specified using a page query parameter. "
          + "A page numbers starts at 0. For example: 'api/races?page=1&size=50' returns the second page of race information containing fifty entries per page.")
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
  @ApiOperation(value = "Returns the race schedule of a year",
      notes = "Finds a list of race entries by year ordered alphabetically")
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
  @ApiOperation(value = "Returns the race schedule of the current season",
      notes = "Finds a list of race entries by the current year ordered alphabetically")
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
  @ApiOperation(value = "Returns the race entry of a particular year and round",
      notes = "Finds a of race entry by year and round")
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

  // --------------------------------------RESULTS--------------------------------------------

  /**
   * Finds a race entry and the results by year and round.
   *
   * @param year  The year of the requested entry.
   * @param round The round of the requested entry.
   * @return The information of the requested entry.
   */
  @ApiOperation(value = "Returns the race entry and the results of a particular year and round",
      notes = "Finds a race entry and the results by year and round")
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

  /**
   * Finds the last race entry and the results.
   *
   * @return The information of the requested entry.
   */
  @ApiOperation(value = "Returns the last race entry and the results",
      notes = "Finds the last race entry and the results")
  @RequestMapping(value = "/last/results", method = RequestMethod.GET)
  @ResponseBody
  RaceResource findLastRaceAndResults() {
    RaceDto raceEntry = raceReadOnlyService.findLastRaceAndResults();

    RaceResource resource = new RaceResource(raceEntry);
    resource.add(linkTo(RaceController.class).slash("last").slash("results").withSelfRel());
    logger.info("Found last race {} entry.", raceEntry);

    return resource;
  }

  /**
   * Finds the last race entry and the result with finishing position.
   *
   * @param position The finishing position in a race.
   * @return The information of the requested entry.
   */
  @ApiOperation(value = "Returns the last race entry and the results with a specified finishing position",
      notes = "Finds the last race entry and the results with a specified finishing position. "
              + "So you can find who was the winner of the last race by doing: 'api/races/last/results/1' ")
  @RequestMapping(value = "/last/results/{position}", method = RequestMethod.GET)
  @ResponseBody
  RaceResource findLastRaceAndResultsAndPosition(@PathVariable("position") int position) {
    RaceDto raceEntry = raceReadOnlyService.findLastRaceAndResultsWithPosition(position);

    RaceResource resource = new RaceResource(raceEntry);
    resource.add(linkTo(RaceController.class).slash("last").slash("results")
                     .slash(position).withSelfRel());
    logger.info("Found last race {} entry with position {}", raceEntry, position);

    return resource;
  }

  /**
   * Finds a race entry and a results by year, round and finishing position.
   *
   * @param year     The year of the requested entry.
   * @param round    The round of the requested entry.
   * @param position The finishing position in a race.
   * @return The information of the requested entry.
   */
  @ApiOperation(value = "Returns a race entry and the results with a specified finishing position",
      notes = "Finds a race entry and the results with a specified finishing position. "
              + "So you can find who was the winner of the first race of 2014 by doing: 'api/races/2014/1/results/1' ")
  @RequestMapping(value = "/{year}/{round}/results/{position}", method = RequestMethod.GET)
  @ResponseBody
  RaceResource findRaceAndResultsBySeasonAndRoundAndPosition(@PathVariable("year") int year,
                                                             @PathVariable("round") int round,
                                                             @PathVariable("position") int position) {
    RaceDto raceEntry = raceReadOnlyService.findRaceAndResultsBySeasonYearAndRoundWithPosition(year,
                                                                                               round,
                                                                                               position);

    RaceResource resource = new RaceResource(raceEntry);
    resource.add(linkTo(RaceController.class).slash(raceEntry.getSeason().getYear())
                     .slash(raceEntry.getRound()).slash("results").slash(position).withSelfRel());
    logger.info("Found {} race entry by year {} and round {} with position.", raceEntry, year,
                position);

    return resource;
  }

  /**
   * Finds all race entries and results by year and finishing position.
   *
   * @param year     The year of the requested entry.
   * @param position The finishing position in a race.
   * @return The information of all entries for a year (season).
   */
  @ApiOperation(value = "Returns a list of race entries and the results with a specified finishing position",
      notes = "Finds race entries and the results with a specified finishing position. "
              + "So you can find who were the winners of the races of 2014 by doing: 'api/races/2014/results/1' ")
  @RequestMapping(value = "/{year}/results/{position}", method = RequestMethod.GET)
  @ResponseBody
  RaceTable findBySeasonAndPosition(@PathVariable("year") int year,
                                    @PathVariable("position") int position) {
    List<RaceDto> raceEntries = raceReadOnlyService.findRaceAndResultsBySeasonYearWithPosition(year,
                                                                                               position);
    logger.info("Found {} race entries for year {} with position {}.", raceEntries.size(), year,
                position);

    return new RaceTable(raceEntries);
  }

  // --------------------------------------QUALIFYING-----------------------------------------

  /**
   * Finds a race entry and the qualifying results by year and round.
   *
   * @param year  The year of the requested entry.
   * @param round The round of the requested entry.
   * @return The information of the requested entry.
   */
  @ApiOperation(value = "Returns the race entry and the qualifying results of a particular year and round",
      notes = "Finds a race entry and the qualifying results by year and round")
  @RequestMapping(value = "/{year}/{round}/qualifying", method = RequestMethod.GET)
  @ResponseBody
  RaceResource findRaceAndQualifyingBySeasonAndRound(@PathVariable("year") int year,
                                                     @PathVariable("round") int round) {
    RaceDto raceEntry = raceReadOnlyService.findRaceAndQualifyingBySeasonYearAndRound(year, round);

    RaceResource resource = new RaceResource(raceEntry);
    resource.add(linkTo(RaceController.class).slash(raceEntry.getSeason().getYear())
                     .slash(raceEntry.getRound()).slash("qualifying").withSelfRel());
    logger.info("Found {} race entry by year {} and round {}.", raceEntry, year);

    return resource;
  }

  // --------------------------------------LAP TIMES------------------------------------------

  /**
   * Finds a race entry and the lap times by year and round.
   *
   * @param year  The year of the requested entry.
   * @param round The round of the requested entry.
   * @return The information of the requested entry.
   */
  @ApiOperation(value = "Returns the race entry and the lap times of a particular year and round",
      notes = "Finds a race entry and the lap times by year and round")
  @RequestMapping(value = "/{year}/{round}/laps", method = RequestMethod.GET)
  @ResponseBody
  RaceResource findRaceAndLapTimesBySeasonAndRound(@PathVariable("year") int year,
                                                   @PathVariable("round") int round) {
    RaceDto raceEntry = raceReadOnlyService.findRaceAndLapTimesBySeasonYearAndRound(year, round);

    RaceResource resource = new RaceResource(raceEntry);
    resource.add(linkTo(RaceController.class).slash(raceEntry.getSeason().getYear())
                     .slash(raceEntry.getRound()).slash("laps").withSelfRel());
    logger.info("Found {} race entry by year {} and round {}.", raceEntry, year);

    return resource;
  }

  /**
   * Finds a race entry and the lap times by year and round and the specific laps with lap number.
   *
   * @param year      The year of the requested entry.
   * @param round     The round of the requested entry.
   * @param lapNumber The lap number.
   * @return The information of the requested entry.
   */
  @ApiOperation(value = "Returns the race entry and the lap times of a particular year and round and the specific laps with the lap number accordingly",
      notes = "Finds a race entry and the lap times by year and round, to find the specific laps add the lap number")
  @RequestMapping(value = "/{year}/{round}/laps/{lapNumber}", method = RequestMethod.GET)
  @ResponseBody
  RaceResource findRaceAndLapTimesBySeasonYearAndRoundAndLap(@PathVariable("year") int year,
                                                             @PathVariable("round") int round,
                                                             @PathVariable("lapNumber") int lapNumber) {
    RaceDto raceEntry = raceReadOnlyService.findRaceAndLapTimesBySeasonYearAndRoundAndLap(year,
                                                                                          round,
                                                                                          lapNumber);

    RaceResource resource = new RaceResource(raceEntry);
    resource.add(linkTo(RaceController.class).slash(raceEntry.getSeason().getYear())
                     .slash(raceEntry.getRound()).slash("laps").slash(lapNumber).withSelfRel());
    logger.info(
        "Found {} race entry by year {} and round {} and the specific laps with lap number {}.",
        raceEntry, year, lapNumber);

    return resource;
  }

  /**
   * Finds a race entry and the lap times by year and round and for a specific driverr.
   *
   * @param year                The year of the requested entry.
   * @param round               The round of the requested entry.
   * @param driverReferenceName The driver referenceName.
   * @return The information of the requested entry.
   */
  @ApiOperation(value = "Returns the race entry and the lap times of a particular year and round for a specific driver",
      notes = "Finds a race entry and the lap times by year and round for a specific driver")
  @RequestMapping(value = "/{year}/{round}/drivers/{driverReferenceName}/laps", method = RequestMethod.GET)
  @ResponseBody
  RaceResource findRaceAndLapTimesBySeasonYearAndRoundAndDriver(@PathVariable("year") int year,
                                                                @PathVariable("round") int round,
                                                                @PathVariable("driverReferenceName") String driverReferenceName) {
    RaceDto raceEntry = raceReadOnlyService.findRaceAndLapTimesBySeasonYearAndRoundAndDriver(year,
                                                                                             round,
                                                                                             driverReferenceName);

    RaceResource resource = new RaceResource(raceEntry);
    resource.add(linkTo(RaceController.class).slash(raceEntry.getSeason().getYear())
                     .slash(raceEntry.getRound()).slash("drivers").slash(driverReferenceName)
                     .slash("laps").withSelfRel());
    logger.info("Found {} race entry by year {} and round {} and driver reference name {}.",
                raceEntry, year, driverReferenceName);

    return resource;
  }

  /**
   * Finds a race entry and the lap times by year and round and the specific laps with lap number.
   *
   * @param year                The year of the requested entry.
   * @param round               The round of the requested entry.
   * @param lapNumber           The lap number.
   * @param driverReferenceName The driver referenceName.
   * @return The information of the requested entry.
   */
  @ApiOperation(value = "Returns the race entry and the lap times of a particular year and round and the specific laps with the lap number accordingly for a specific driver",
      notes = "Finds a race entry and the lap times by year and round, to find the specific laps add the lap number, for a specific driver")
  @RequestMapping(value = "/{year}/{round}/drivers/{driverReferenceName}/laps/{lapNumber}", method = RequestMethod.GET)
  @ResponseBody
  RaceResource findRaceAndLapTimesBySeasonYearAndRoundAndLapAndDriver(
      @PathVariable("year") int year,
      @PathVariable("round") int round,
      @PathVariable("lapNumber") int lapNumber,
      @PathVariable("driverReferenceName") String driverReferenceName) {
    RaceDto
        raceEntry =
        raceReadOnlyService.findRaceAndLapTimesBySeasonYearAndRoundAndLapAndDriver(year,
                                                                                   round,
                                                                                   lapNumber,
                                                                                   driverReferenceName);

    RaceResource resource = new RaceResource(raceEntry);
    resource.add(linkTo(RaceController.class).slash(raceEntry.getSeason().getYear())
                     .slash(raceEntry.getRound()).slash("drivers").slash(driverReferenceName)
                     .slash("laps").slash(lapNumber).withSelfRel());
    logger.info(
        "Found {} race entry by year {} and round {} and the specific laps with lap number {} for driver {}.",
        raceEntry, year, lapNumber, driverReferenceName);

    return resource;
  }

  // --------------------------------------PIT STOPS------------------------------------------

  /**
   * Finds a race entry and the pit stops by year and round.
   *
   * @param year  The year of the requested entry.
   * @param round The round of the requested entry.
   * @return The information of the requested entry.
   */
  @ApiOperation(value = "Returns the race entry and the pit stops of a particular year and round",
      notes = "Finds a race entry and the pit stops by year and round")
  @RequestMapping(value = "/{year}/{round}/pitstops", method = RequestMethod.GET)
  @ResponseBody
  RaceResource findRaceAndPitStopsBySeasonAndRound(@PathVariable("year") int year,
                                                   @PathVariable("round") int round) {
    RaceDto raceEntry = raceReadOnlyService.findRaceAndPitStopsBySeasonYearAndRound(year, round);

    RaceResource resource = new RaceResource(raceEntry);
    resource.add(linkTo(RaceController.class).slash(raceEntry.getSeason().getYear())
                     .slash(raceEntry.getRound()).slash("pitstops").withSelfRel());
    logger.info("Found {} race entry by year {} and round {}.", raceEntry, year);

    return resource;
  }

  /**
   * Finds a race entry and the pit stops by year and round.
   *
   * @param year  The year of the requested entry.
   * @param round The round of the requested entry.
   * @param stop  The stop.
   * @return The information of the requested entry.
   */
  @ApiOperation(value = "Returns the race entry and the pit stops of a particular year and round with a specific stop",
      notes = "Finds a race entry and the pit stops by year and round with a specific stop")
  @RequestMapping(value = "/{year}/{round}/pitstops/{stop}", method = RequestMethod.GET)
  @ResponseBody
  RaceResource findRaceAndPitStopsBySeasonYearAndRoundAndStop(@PathVariable("year") int year,
                                                              @PathVariable("round") int round,
                                                              @PathVariable("stop") int stop) {
    RaceDto
        raceEntry =
        raceReadOnlyService.findRaceAndPitStopsBySeasonYearAndRoundAndStop(year, round, stop);

    RaceResource resource = new RaceResource(raceEntry);
    resource.add(linkTo(RaceController.class).slash(raceEntry.getSeason().getYear())
                     .slash(raceEntry.getRound()).slash("pitstops").slash(stop).withSelfRel());
    logger.info("Found {} race entry by year {} and round {} and stop {}.", raceEntry, year, stop);

    return resource;
  }

  /**
   * Finds a race entry and the pit stops by year and round.
   *
   * @param year                The year of the requested entry.
   * @param round               The round of the requested entry.
   * @param driverReferenceName The driver referenceName.
   * @return The information of the requested entry.
   */
  @ApiOperation(value = "Returns the race entry and the pit stops of a particular year and round for a specific driver",
      notes = "Finds a race entry and the pit stops by year and round for a specific driver")
  @RequestMapping(value = "/{year}/{round}/drivers/{driverReferenceName}/pitstops", method = RequestMethod.GET)
  @ResponseBody
  RaceResource findRaceAndPitStopsBySeasonYearAndRoundAndDriver(@PathVariable("year") int year,
                                                                @PathVariable("round") int round,
                                                                @PathVariable("driverReferenceName") String driverReferenceName) {
    RaceDto raceEntry = raceReadOnlyService
        .findRaceAndPitStopsBySeasonYearAndRoundAndDriver(year, round, driverReferenceName);

    RaceResource resource = new RaceResource(raceEntry);
    resource.add(linkTo(RaceController.class).slash(raceEntry.getSeason().getYear())
                     .slash(raceEntry.getRound()).slash("drivers").slash(driverReferenceName)
                     .slash("pitstops").withSelfRel());
    logger.info("Found {} race entry by year {} and round {} and driver {}.", raceEntry, year,
                driverReferenceName);

    return resource;
  }

  /**
   * Finds a race entry and the pit stops by year and round for a specific driver with a specific stop.
   *
   * @param year                The year of the requested entry.
   * @param round               The round of the requested entry.
   * @param stop                The stop.
   * @param driverReferenceName The driver referenceName.
   * @return The information of the requested entry.
   */
  @ApiOperation(value = "Returns the race entry and the pit stops by year and round for a specific driver with a specific stop",
      notes = "Finds a race entry and the pit stops by year and round for a specific driver with a specific stop")
  @RequestMapping(value = "/{year}/{round}/drivers/{driverReferenceName}/pitstops/{stop}", method = RequestMethod.GET)
  @ResponseBody
  RaceResource findRaceAndPitStopsBySeasonYearAndRoundAndStopAndDriver(
      @PathVariable("year") int year,
      @PathVariable("round") int round,
      @PathVariable("stop") int stop,
      @PathVariable("driverReferenceName") String driverReferenceName) {
    RaceDto raceEntry = raceReadOnlyService
        .findRaceAndPitStopsBySeasonYearAndRoundAndStopAndDriver(year, round, stop,
                                                                 driverReferenceName);

    RaceResource resource = new RaceResource(raceEntry);
    resource.add(linkTo(RaceController.class).slash(raceEntry.getSeason().getYear())
                     .slash(raceEntry.getRound()).slash("drivers").slash(driverReferenceName)
                     .slash("pitstops").slash(stop).withSelfRel());
    logger.info("Found {} race entry by year {} and round {} and stop {} and driver {}.", raceEntry,
                year,
                stop, driverReferenceName);

    return resource;
  }

  /**
   * Finds a race entry and the pit stops by year and round for a specific lap.
   *
   * @param year  The year of the requested entry.
   * @param round The round of the requested entry.
   * @param lap   The lap.
   * @return The information of the requested entry.
   */
  @ApiOperation(value = "Returns the race entry and the pit stops of a particular year and round for a specific lap",
      notes = "Finds a race entry and the pit stops by year and round for a specific lap")
  @RequestMapping(value = "/{year}/{round}/laps/{lap}/pitstops", method = RequestMethod.GET)
  @ResponseBody
  RaceResource findRaceAndPitStopsBySeasonYearAndRoundAndLap(@PathVariable("year") int year,
                                                             @PathVariable("round") int round,
                                                             @PathVariable("lap") int lap) {
    RaceDto raceEntry =
        raceReadOnlyService.findRaceAndPitStopsBySeasonYearAndRoundAndLap(year, round, lap);

    RaceResource resource = new RaceResource(raceEntry);
    resource.add(linkTo(RaceController.class).slash(raceEntry.getSeason().getYear())
                     .slash(raceEntry.getRound()).slash("laps").slash(lap).slash("pitstops")
                     .withSelfRel());
    logger.info("Found {} race entry by year {} and round {} and lap {}.", raceEntry, year, lap);

    return resource;
  }

  /**
   * Finds a race entry and the pit stops by year and round for a driver with lap number
   *
   * @param year  The year of the requested entry.
   * @param round The round of the requested entry.
   * @param lap   The lap.
   * @return The information of the requested entry.
   */
  @ApiOperation(value = "Returns the race entry and the pit stops by year and round for a driver with lap number",
      notes = "Finds a race entry and the pit stops by year and round for a driver with lap number")
  @RequestMapping(value = "/{year}/{round}/drivers/{driverReferenceName}/laps/{lap}/pitstops", method = RequestMethod.GET)
  @ResponseBody
  RaceResource findRaceAndPitStopsBySeasonYearAndRoundAndLapAndDriver(
      @PathVariable("year") int year,
      @PathVariable("round") int round,
      @PathVariable("driverReferenceName") String driverReferenceName,
      @PathVariable("lap") int lap) {
    RaceDto raceEntry =
        raceReadOnlyService.findRaceAndPitStopsBySeasonYearAndRoundAndLapAndDriver(year, round, lap,
                                                                                   driverReferenceName);

    RaceResource resource = new RaceResource(raceEntry);
    resource.add(linkTo(RaceController.class).slash(raceEntry.getSeason().getYear())
                     .slash(raceEntry.getRound()).slash("drivers").slash(driverReferenceName)
                     .slash("laps").slash(lap).slash("pitstops")
                     .withSelfRel());
    logger.info("Found {} race entry by year {} and round {} and lap {} and driver {}.", raceEntry,
                year, lap, driverReferenceName);

    return resource;
  }

}
