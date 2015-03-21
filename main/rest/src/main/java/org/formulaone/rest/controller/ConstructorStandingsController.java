package org.formulaone.rest.controller;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import org.formulaone.rest.wrapper.ConstructorStandingsTable;
import org.formulaone.service.ConstructorStandingsReadOnlyService;
import org.formulaone.service.dto.ConstructorStandingsDto;
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
@Api(value = "constructors standings", description = "Returns information about the constructor championship standings in Formula One", position = 7)
@RestController
@RequestMapping("/api")
public class ConstructorStandingsController {

  private static final Logger logger =
      LoggerFactory.getLogger(ConstructorStandingsController.class);

  private final ConstructorStandingsReadOnlyService constructorReadOnlyService;

  @Autowired
  ConstructorStandingsController(ConstructorStandingsReadOnlyService constructorReadOnlyService) {
    this.constructorReadOnlyService = constructorReadOnlyService;
  }

  /**
   * Finds all constructor standings entries after a specific race for a particular season and
   * round
   *
   * @return The information of all entries.
   */
  @ApiOperation(value = "Returns a list of constructor standings after a specific race for a particular season and round",
      notes = "Finds all constructor standings after a specific race for a particular season and round", position = 1)
  @RequestMapping(value = "/{season}/{round}/constructorStandings", method = RequestMethod.GET)
  @ResponseBody
  ConstructorStandingsTable findConstructorsBySeason(@PathVariable("season") int year,
                                                     @PathVariable("round") int round) {
    logger
        .info("Finding all constructors standings entries in a particular season({}) and round({})",
              year,
              round);

    List<ConstructorStandingsDto> constructorStandingsEntries =
        constructorReadOnlyService.findConstructorStandingsBySeasonYearAndRound(year, round);
    logger.info("Found {} constructor standings entries.", constructorStandingsEntries.size());

    return new ConstructorStandingsTable((constructorStandingsEntries));
  }

  /**
   * Finds all constructor standings at the end of the season with the year as a method parameter.
   * If the season has not ended you will get the current standings.
   *
   * @return The information of all entries.
   */
  @ApiOperation(value = "Returns the constructor standings at the end of a particular season",
      notes = "Finds all the constructor standings at the end of a particular season. "
              + "If the season has not ended you will get the current standings.", position = 2)
  @RequestMapping(value = "/{season}/constructorStandings", method = RequestMethod.GET)
  @ResponseBody
  ConstructorStandingsTable findConstructorStandingsBySeasonYear(@PathVariable("season") int year) {
    logger.info("Finding all constructors standings entries in a particular season({})", year);

    List<ConstructorStandingsDto> constructorStandingsEntries =
        constructorReadOnlyService.findConstructorStandingsBySeasonYear(year);
    logger.info("Found {} constructor standings entries.", constructorStandingsEntries.size());

    return new ConstructorStandingsTable((constructorStandingsEntries));
  }

  /**
   * Finds the constructor standings with the position as a method parameter for all seasons You can
   * get the winners of the constructor standings for all seasons by passing 1 as position
   * parameter.
   *
   * @return The information of all entries.
   */
  @ApiOperation(value = "Returns the standings of the current constructor championship",
      notes = "Finds the standings of the current constructor championship. "
              + "If the season has not ended you will get the current standings."
      , position = 2)
  @RequestMapping(value = "current/constructorStandings", method = RequestMethod.GET)
  @ResponseBody
  ConstructorStandingsTable findCurrentConstructorStandings() {
    logger.info("Finds the current constructor standings");
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    List<ConstructorStandingsDto> constructorStandingsEntries =
        constructorReadOnlyService.findConstructorStandingsBySeasonYear(currentYear);
    logger.info("Found {} constructor standings entries.", constructorStandingsEntries.size());

    return new ConstructorStandingsTable((constructorStandingsEntries));
  }

  /**
   * Finds the constructor standings with the position as a method parameter for all seasons You can
   * get the winners of the constructor standings for all seasons by passing 1 as position
   * parameter.
   *
   * @return The information of all entries.
   */
  @ApiOperation(value = "Returns the winners of the constructor standings for all seasons if you pass 1 as a parameter",
      notes = "Finds the winners of the constructor standings for all seasons if you pass 1 as a parameter"
      , position = 2)
  @RequestMapping(value = "/constructorStandings/{position}", method = RequestMethod.GET)
  @ResponseBody
  ConstructorStandingsTable findConstructorStandingsBySeasonsAndPosition(
      @PathVariable("position") int position) {
    logger.info("Finds all constructor standings with the position({}) as a method parameter",
                position);

    List<ConstructorStandingsDto> constructorStandingsEntries =
        constructorReadOnlyService.findConstructorStandingsBySeasonsAndPosition(position);
    logger.info("Found {} constructor standings entries.", constructorStandingsEntries.size());

    return new ConstructorStandingsTable((constructorStandingsEntries));
  }


  /**
   * Finds the constructor standings for a particular constructor for every season they have taken
   * part.
   *
   * @return The information of all entries.
   */
  @ApiOperation(value = "Returns the constructor standings for a particular constructor for every season they took part in the championship",
      notes = "Finds the constructor standings for a particular constructor for every season they took part in the championship"
      , position = 2)
  @RequestMapping(value = "/constructorStandings/constructors/{constructorReferenceName}", method = RequestMethod.GET)
  @ResponseBody
  ConstructorStandingsTable findConstructorStandingsBySeasonsAndConstructor(
      @PathVariable("constructorReferenceName") String constructorReferenceName) {
    logger.info("Finds all constructor standings with the constructorReferenceName({}) as a method parameter",
        constructorReferenceName);

    List<ConstructorStandingsDto> constructorStandingsEntries =
        constructorReadOnlyService.findConstructorStandingsBySeasonsAndConstructor(
            constructorReferenceName);
    logger.info("Found {} constructor standings entries.", constructorStandingsEntries.size());

    return new ConstructorStandingsTable((constructorStandingsEntries));
  }

}
