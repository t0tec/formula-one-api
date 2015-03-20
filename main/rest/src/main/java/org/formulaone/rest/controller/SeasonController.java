package org.formulaone.rest.controller;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import org.formulaone.rest.wrapper.SeasonPage;
import org.formulaone.rest.wrapper.SeasonResource;
import org.formulaone.rest.wrapper.SeasonTable;
import org.formulaone.service.SeasonReadOnlyService;
import org.formulaone.service.dto.SeasonDto;
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
@Api(value = "seasons", description = "Returns the list of seasons in Formula One"
    , position = 5)
@RestController
@RequestMapping("/api/seasons")
public class SeasonController {

  private static final Logger logger = LoggerFactory.getLogger(SeasonController.class);

  private final SeasonReadOnlyService seasonReadOnlyService;

  @Autowired
  SeasonController(SeasonReadOnlyService seasonReadOnlyService) {
    this.seasonReadOnlyService = seasonReadOnlyService;
  }

  /**
   * Finds all seasons entries.
   *
   * @return The information of all entries.
   */
  @ApiOperation(value = "Returns a list of all seasons",
      notes = "Finds a list of seasons entries ordered by year ascending")
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  @ResponseBody
  SeasonTable findAll() {
    logger.info("Finding all season entries");
    Sort sortOn = new Sort(
        Sort.Direction.ASC, "year"
    );

    List<SeasonDto> seasonEntries = seasonReadOnlyService.findAll(sortOn);
    logger.info("Found {} season entries.", seasonEntries.size());

    return new SeasonTable(seasonEntries);
  }

  /**
   * Finds all season entries paginated.
   *
   * @param page the page you are requesting
   * @param size the size of entries per page
   * @return The information of all entries paginated.
   */
  @ApiOperation(value = "Returns a list of seasons limited by a query parameter",
      notes =
          "The number of results that are returned can be controlled using a limit query parameter. "
          + "Please use the smallest value that your application needs. If not specified, the default value is 30. "
          + "A page number into the url can also be specified using a page query parameter. "
          + "A page numbers starts at 0. For example: 'api/seasons?page=1&size=50' returns the second page of season information containing fifty entries per page.")
  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  SeasonPage findAllPageable(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "30") int size) {
    Sort sortOn = new Sort(
        Sort.Direction.ASC, "year"
    );

    Pageable pageable = new PageRequest(
        page, size, sortOn
    );

    Page<SeasonDto> pageResult = seasonReadOnlyService.findAll(pageable);
    logger.info("Found {} season entries paged by {} {}.", pageResult.getNumberOfElements(), page,
                size);

    return new SeasonPage(pageResult, "page", "size");
  }

  @ApiOperation(value = "Returns a seasons entry",
      notes = "Finds a single seasons entry by the year")
  @RequestMapping(value = "/{year}", method = RequestMethod.GET)
  @ResponseBody
  SeasonResource findByYear(@PathVariable("year") int year) {
    logger.info("Finding season entry by using year: {}", year);

    SeasonDto seasonEntry = seasonReadOnlyService.findByYear(year);
    logger.info("Found season entry: {}", seasonEntry);

    SeasonResource resource = new SeasonResource(seasonEntry);
    resource.add(linkTo(SeasonController.class).slash(seasonEntry.getYear()).withSelfRel());

    return resource;
  }
}
