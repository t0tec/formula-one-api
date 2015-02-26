package org.formulaone.rest.controller;

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
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  @ResponseBody
  SeasonTable findAll() {
    logger.info("Finding all season entries");

    List<SeasonDto> seasonEntries = seasonReadOnlyService.findAll();
    logger.info("Found {} season entries.", seasonEntries.size());

    return new SeasonTable(seasonEntries);
  }

  /**
   * Finds all season entries sorted by a property.
   *
   * @param sort The parameter to sort the entries on
   * @return The information of all entries sorted by sortOn.
   */
  @RequestMapping(value = "/sort", method = RequestMethod.GET)
  @ResponseBody
  SeasonTable findAllSortable(@RequestParam(defaultValue = "name") String sort,
                                   @RequestParam(defaultValue = "ASC") String direction) {
    Sort sortOn = new Sort(
        Sort.Direction.fromStringOrNull(direction), sort
    );

    List<SeasonDto> seasonEntries = seasonReadOnlyService.findAll(sortOn);
    logger.info("Found {} season entries sort on {} {}.", seasonEntries.size(), sort,
                direction);

    return new SeasonTable(seasonEntries);
  }

  /**
   * Finds all season entries paginated.
   *
   * @param page the page you are requesting
   * @param size the size of entries per page
   * @return The information of all entries paginated.
   */
  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  SeasonPage findAllPageable(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "30") int size) {
    Pageable pageable = new PageRequest(
        page, size
    );

    Page<SeasonDto> pageResult = seasonReadOnlyService.findAll(pageable);
    logger.info("Found {} season entries paged by {} {}.", pageResult.getNumberOfElements(), page,
                size);

    return new SeasonPage(pageResult, "page", "size");
  }

  @RequestMapping(value = "/{year}", method = RequestMethod.GET)
  @ResponseBody
  SeasonResource findByReferenceName(@PathVariable("year") int year) {
    logger.info("Finding season entry by using year: {}", year);

    SeasonDto seasonEntry = seasonReadOnlyService.findByYear(year);
    logger.info("Found season entry: {}", seasonEntry);

    SeasonResource resource = new SeasonResource(seasonEntry);
    resource.add(linkTo(SeasonController.class).slash(seasonEntry.getYear()).withSelfRel());

    return resource;
  }
}
