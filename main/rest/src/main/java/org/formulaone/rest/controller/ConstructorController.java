package org.formulaone.rest.controller;

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
@RequestMapping("/api")
public class ConstructorController {

  private static final Logger logger = LoggerFactory.getLogger(ConstructorController.class);

  private final ConstructorReadOnlyService constructorReadOnlyService;

  @Autowired
  ConstructorController(ConstructorReadOnlyService constructorReadOnlyService) {
    this.constructorReadOnlyService = constructorReadOnlyService;
  }

  /**
   * Finds a single constructor entry by id.
   *
   * @param id The id of the requested entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found by using the given
   *                                                         id.
   */
  @RequestMapping(value = "/constructors/id/{id}", method = RequestMethod.GET)
  @ResponseBody
  ConstructorResource findById(@PathVariable("id") Long id) {
    logger.info("Finding constructor entry by using id: {}", id);

    ConstructorDto constructorEntry = constructorReadOnlyService.findById(id);
    logger.info("Found constructor entry: {}", constructorEntry);

    ConstructorResource resource = new ConstructorResource(constructorEntry);
    resource.add(
        linkTo(ConstructorController.class).slash("constructors").slash(constructorEntry.getId())
            .withSelfRel());

    return resource;
  }

  /**
   * Finds a single constructor entry by reference name.
   *
   * @param referenceName The referenceName of the requested entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found by using the given
   *                                                         referenceName.
   */
  @RequestMapping(value = "/constructors/{referenceName}", method = RequestMethod.GET)
  @ResponseBody
  ConstructorResource findByReferenceName(@PathVariable("referenceName") String referenceName) {
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
  @RequestMapping(value = "/constructors/all", method = RequestMethod.GET)
  @ResponseBody
  ConstructorTable findAll() {
    logger.info("Finding all constructor entries");

    List<ConstructorDto> constructorEntries = constructorReadOnlyService.findAll();
    logger.info("Found {} constructor entries.", constructorEntries.size());

    return new ConstructorTable(constructorEntries);
  }

  /**
   * Finds all constructor entries sorted by a property.
   *
   * @param sort The parameter to sort the entries on
   * @return The information of all entries sorted by sortOn.
   */
  @RequestMapping(value = "/constructors/sort", method = RequestMethod.GET)
  @ResponseBody
  ConstructorTable findAllSortable(@RequestParam(defaultValue = "name") String sort,
                                   @RequestParam(defaultValue = "ASC") String direction) {
    Sort sortOn = new Sort(
        Sort.Direction.fromStringOrNull(direction), sort
    );

    List<ConstructorDto> constructorEntries = constructorReadOnlyService.findAll(sortOn);
    logger.info("Found {} constructor entries sort on {} {}.", constructorEntries.size(), sort,
                direction);

    return new ConstructorTable(constructorEntries);
  }

  /**
   * Finds all constructor entries paginated.
   *
   * @param page the page you are requesting
   * @param size the size of entries per page
   * @return The information of all entries paginated.
   */
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
  @RequestMapping(value = "/{season}/constructors", method = RequestMethod.GET)
  @ResponseBody
  ConstructorTable findConstructorsBySeason(@PathVariable("season") int year) {
    logger.info("Finding all constructors entries in a particular season({})", year);

    List<ConstructorDto> driverEntries = constructorReadOnlyService.findConstructorsBySeason(year);
    logger.info("Found {} constructors entries.", driverEntries.size());

    return new ConstructorTable(driverEntries);
  }

  /**
   * Finds all constructors entries who have taken part in a particular season and round
   *
   * @return The information of all entries.
   */
  @RequestMapping(value = "/{season}/{round}/constructors", method = RequestMethod.GET)
  @ResponseBody
  ConstructorTable findConstructorsBySeason(@PathVariable("season") int year,
                                            @PathVariable("round") int round) {
    logger.info("Finding all constructors entries in a particular season({}) and round({})", year,
                round);

    List<ConstructorDto> driverEntries =
        constructorReadOnlyService.findConstructorsBySeasonAndRound(year, round);
    logger.info("Found {} constructors entries.", driverEntries.size());

    return new ConstructorTable(driverEntries);
  }
}
