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
@RequestMapping("/api/constructors")
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
  @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
  @ResponseBody
  ConstructorResource findById(@PathVariable("id") Long id) {
    logger.info("Finding constructor entry by using id: {}", id);

    ConstructorDto constructorEntry = constructorReadOnlyService.findById(id);
    logger.info("Found constructor entry: {}", constructorEntry);

    ConstructorResource resource = new ConstructorResource(constructorEntry);
    resource.add(linkTo(ConstructorController.class).slash(constructorEntry.getId()).withSelfRel());

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
  @RequestMapping(value = "/{referenceName}", method = RequestMethod.GET)
  @ResponseBody
  ConstructorResource findByReferenceName(@PathVariable("referenceName") String referenceName) {
    logger.info("Finding constructor entry by using referenceName: {}", referenceName);

    ConstructorDto constructorEntry = constructorReadOnlyService.findByReferenceName(referenceName);
    logger.info("Found constructor entry: {}", constructorEntry);

    ConstructorResource resource = new ConstructorResource(constructorEntry);
    resource.add(
        linkTo(ConstructorController.class).slash(constructorEntry.getReferenceName())
            .withSelfRel());

    return resource;
  }

  /**
   * Finds all constructor entries.
   *
   * @return The information of all entries.
   */
  @RequestMapping(value = "/all", method = RequestMethod.GET)
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
  @RequestMapping(value = "/sort", method = RequestMethod.GET)
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
  @RequestMapping(method = RequestMethod.GET)
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
}
