package org.formulaone.rest.controller;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import org.formulaone.rest.wrapper.ConstructorPage;
import org.formulaone.rest.wrapper.ConstructorResource;
import org.formulaone.rest.wrapper.ConstructorTable;
import org.formulaone.service.ConstructorReadOnlyService;
import org.formulaone.service.dto.ConstructorDto;
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
@Api(value = "constructors", description = "Returns information about the constructors"
                                           + " that are active or have taken part in Formula One"
    , position = 2)
@RestController
@RequestMapping("/api")
public class ConstructorController {

  private static final Logger logger = LoggerFactory.getLogger(ConstructorController.class);

  private final ConstructorReadOnlyService constructorReadOnlyService;

  @Autowired
  ConstructorController(ConstructorReadOnlyService constructorReadOnlyService) {
    this.constructorReadOnlyService = constructorReadOnlyService;
  }

  /**
   * Finds a single constructor entry by reference name.
   *
   * @param referenceName The referenceName of the requested entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found by using the given
   *                                                         referenceName.
   */
  @ApiOperation(value = "Returns a constructor entry",
      notes = "Finds a single constructor entry by a unique reference name")
  @RequestMapping(value = "/constructors/{referenceName}", method = RequestMethod.GET)
  @ResponseBody
  ConstructorResource findByReferenceName(
      @ApiParam(value = "Unique reference name of a constructor", required = true)
      @PathVariable("referenceName") String referenceName) {
    logger.info("Finding constructor entry by using referenceName: {}", referenceName);

    ConstructorDto constructorEntry = constructorReadOnlyService.findByReferenceName(referenceName);
    logger.info("Found constructor entry: {}", constructorEntry);

    ConstructorResource resource = new ConstructorResource(constructorEntry);
    resource.add(linkTo(ConstructorController.class).slash("constructors")
                     .slash(constructorEntry.getReferenceName()).withSelfRel());

    return resource;
  }

  /**
   * Finds all constructor entries.
   *
   * @return The information of all entries.
   */
  @ApiOperation(value = "Returns a list of all constructors",
      notes = "Finds a list of constructor entries ordered alphabetically")
  @RequestMapping(value = "/constructors/all", method = RequestMethod.GET)
  @ResponseBody
  ConstructorTable findAll() {
    logger.info("Finding all constructor entries");

    List<ConstructorDto> constructorEntries = constructorReadOnlyService.findAll();
    logger.info("Found {} constructor entries.", constructorEntries.size());

    return new ConstructorTable(constructorEntries);
  }

  /**
   * Finds all constructor entries paginated.
   *
   * @param page the page you are requesting
   * @param size the size of entries per page
   * @return The information of all entries paginated.
   */
  @ApiOperation(value = "Returns a list of constructors limited by a query parameter",
      notes =
          "The number of results that are returned can be controlled using a limit query parameter. "
          + "Please use the smallest value that your application needs. If not specified, the default value is 30. "
          + "A page number into the url can also be specified using a page query parameter. "
          + "A page numbers starts at 0. For example: 'api/constructors?page=1&size=50' returns the second page of constructor information containing fifty entries per page.")
  @RequestMapping(value = "/constructors", method = RequestMethod.GET)
  @ResponseBody
  ConstructorPage findAllPageable(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "30") int size) {
    Pageable pageable = new PageRequest(
        page, size
    );

    Page<ConstructorDto> pageResult = constructorReadOnlyService.findAll(pageable);
    logger.info("Found {} constructor entries paged by {} {}.", pageResult.getNumberOfElements(),
                page,
                size);

    return new ConstructorPage(pageResult, "page", "size");
  }

  /**
   * Finds all constructors entries who have taken part in a particular season
   *
   * @return The information of all entries.
   */
  @ApiOperation(value = "Returns a list of constructors entries who have taken part in a particular season",
      notes = "Finds a list of constructor entries who have taken part in a particular season ordered alphabetically")
  @RequestMapping(value = "/{year}/constructors", method = RequestMethod.GET)
  @ResponseBody
  ConstructorTable findConstructorsBySeason(
      @ApiParam(value = "The year of a particular season", required = true)
      @PathVariable("year") int year) {
    logger.info("Finding all constructors entries in a particular season({})", year);

    List<ConstructorDto>
        constructorEntries =
        constructorReadOnlyService.findConstructorsBySeason(year);
    logger.info("Found {} constructors entries.", constructorEntries.size());

    return new ConstructorTable(constructorEntries);
  }

  /**
   * Finds all constructors entries who have taken part in a particular season and round
   *
   * @return The information of all entries.
   */
  @ApiOperation(value = "Returns a list of constructors entries who have taken part in a particular season and round",
      notes = "Finds a list of constructor entries who have taken part in a particular season and round ordered alphabetically")
  @RequestMapping(value = "/{year}/{round}/constructors", method = RequestMethod.GET)
  @ResponseBody
  ConstructorTable findConstructorsBySeason(@ApiParam(value = "The year of a particular season", required = true) @PathVariable("year") int year,
                                            @ApiParam(value = "The round of a particular season", required = true) @PathVariable("round") int round) {
    logger.info("Finding all constructors entries in a particular year({}) and round({})", year,
                round);

    List<ConstructorDto> constructorEntries =
        constructorReadOnlyService.findConstructorsBySeasonAndRound(year, round);
    logger.info("Found {} constructors entries.", constructorEntries.size());

    return new ConstructorTable(constructorEntries);
  }
}
