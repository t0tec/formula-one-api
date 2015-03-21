package org.formulaone.rest.controller;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import org.formulaone.rest.wrapper.DriverStandingsTable;
import org.formulaone.service.DriverStandingsReadOnlyService;
import org.formulaone.service.dto.DriverStandingsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@Api(value = "driver standings", description = "Returns information about the driver championship standings in Formula One", position = 8)
@RestController
@RequestMapping("/api")
public class DriverStandingsController {

  private static final Logger logger =
      LoggerFactory.getLogger(DriverStandingsController.class);

  private final DriverStandingsReadOnlyService driverStandingsReadOnlyService;

  @Autowired
  DriverStandingsController(DriverStandingsReadOnlyService driverStandingsReadOnlyService) {
    this.driverStandingsReadOnlyService = driverStandingsReadOnlyService;
  }

  /**
   * Finds all driver standings entries after a specific race for a particular season and round
   *
   * @return The information of all entries.
   */
  @ApiOperation(value = "Returns a list of driver standings after a specific race for a particular season and round",
      notes = "Finds all driver standings after a specific race for a particular season and round", position = 1)
  @RequestMapping(value = "/{season}/{round}/driverStandings", method = RequestMethod.GET)
  @ResponseBody
  DriverStandingsTable findDriverStandingsBySeason(@PathVariable("season") int year,
                                                   @PathVariable("round") int round) {
    logger.info("Finding all driver standings entries in a particular season({}) and round({})",
                year,
                round);

    List<DriverStandingsDto> driverStandingsEntries =
        driverStandingsReadOnlyService.findDriverStandingsBySeasonYearAndRound(year, round);
    logger.info("Found {} driver standings entries.", driverStandingsEntries.size());

    return new DriverStandingsTable((driverStandingsEntries));
  }

  /**
   * Finds all driver standings at the end of the season with the year as a method parameter. If the
   * season has not ended you will get the current standings.
   *
   * @return The information of all entries.
   */
  @ApiOperation(value = "Returns the driver standings at the end of a particular season",
      notes = "Finds all the driver standings at the end of a particular season. "
              + "If the season has not ended you will get the current standings.", position = 2)
  @RequestMapping(value = "/{season}/driverStandings", method = RequestMethod.GET)
  @ResponseBody
  DriverStandingsTable findDriverStandingsBySeasonYear(@PathVariable("season") int year) {
    logger.info("Finding all driver standings entries in a particular season({})", year);

    List<DriverStandingsDto> driverStandingsEntries =
        driverStandingsReadOnlyService.findDriverStandingsBySeasonYear(year);
    logger.info("Found {} driver standings entries.", driverStandingsEntries.size());

    return new DriverStandingsTable((driverStandingsEntries));
  }

  /**
   * Finds the driver standings with the position as a method parameter for all seasons You can get
   * the winners of the driver standings for all seasons by passing 1 as position parameter.
   *
   * @return The information of all entries.
   */
  @ApiOperation(value = "Returns the standings of the current driver championship",
      notes = "Finds the standings of the current driver championship. "
              + "If the season has not ended you will get the current standings."
      , position = 2)
  @RequestMapping(value = "current/driverStandings", method = RequestMethod.GET)
  @ResponseBody
  DriverStandingsTable findCurrentDriverStandings() {
    logger.info("Finds the current driver standings");
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    List<DriverStandingsDto> driverStandingsEntries =
        driverStandingsReadOnlyService.findDriverStandingsBySeasonYear(currentYear);
    logger.info("Found {} driver standings entries.", driverStandingsEntries.size());

    return new DriverStandingsTable((driverStandingsEntries));
  }

  /**
   * Finds the driver standings with the position as a method parameter for all seasons You can get
   * the winners of the driver standings for all seasons by passing 1 as position parameter.
   *
   * @return The information of all entries.
   */
  @ApiOperation(value = "Returns the winners of the driver standings for all seasons if you pass 1 as a parameter",
      notes = "Finds the winners of the driver standings for all seasons if you pass 1 as a parameter"
      , position = 2)
  @RequestMapping(value = "/driverStandings/{position}", method = RequestMethod.GET)
  @ResponseBody
  DriverStandingsTable findDriverStandingsBySeasonsAndPosition(
      @PathVariable("position") int position) {
    logger.info("Finds all driver standings with the position({}) as a method parameter",
                position);

    List<DriverStandingsDto> driverStandingsEntries =
        driverStandingsReadOnlyService.findDriverStandingsBySeasonsAndPosition(position);
    logger.info("Found {} driver standings entries.", driverStandingsEntries.size());

    return new DriverStandingsTable((driverStandingsEntries));
  }


  /**
   * Finds the driver standings for a particular driver for every season they have taken part.
   *
   * @return The information of all entries.
   */
  @ApiOperation(value = "Returns the driver standings for a particular driver for every season he took part in the championship",
      notes = "Finds the driver standings for a particular driver for every season he took part in the championship"
      , position = 2)
  @RequestMapping(value = "/driverStandings/drivers/{driverReferenceName}", method = RequestMethod.GET)
  @ResponseBody
  DriverStandingsTable findDriverStandingsBySeasonsAndDriver(
      @PathVariable("driverReferenceName") String driverReferenceName) {
    logger.info("Finds all driver standings with the driverReferenceName({}) as a method parameter",
                driverReferenceName);

    List<DriverStandingsDto> driverStandingsEntries =
        driverStandingsReadOnlyService.findDriverStandingsBySeasonsAndDriver(
            driverReferenceName);
    logger.info("Found {} driver standings entries.", driverStandingsEntries.size());

    return new DriverStandingsTable((driverStandingsEntries));
  }

}
