package org.formulaone.service;

import com.mysema.query.types.Predicate;

import org.formulaone.core.exception.NotFoundException;
import org.formulaone.core.model.Driver;
import org.formulaone.core.model.QDriver;
import org.formulaone.repository.DriverRepository;
import org.formulaone.service.dto.DriverDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@Transactional(readOnly = true)
@Service
public class RepositoryDriverService extends RepositoryGenericService<Driver, DriverDto, Long>
    implements DriverReadOnlyService {

  private DriverRepository driverRepository;

  public RepositoryDriverService() {
    super();
  }

  @Autowired
  public RepositoryDriverService(DriverRepository driverRepository) {
    super(driverRepository);
    this.driverRepository = driverRepository;
  }

  @Override
  public DriverDto findByReferenceName(String referenceName) {
    Driver driverEntry = driverRepository.findOne(referenceNameEquals(referenceName));

    if (driverEntry == null) {
      throw new NotFoundException(Driver.class, "referenceName", referenceName);
    }

    return mapper.map(driverEntry, dtoClass);
  }

  private static Predicate referenceNameEquals(final String referenceName) {
    QDriver driver = QDriver.driver;
    return driver.referenceName.eq(referenceName);
  }

  @Override
  public List<DriverDto> findDriversBySeason(int year) {
    List<DriverDto> result = new ArrayList<DriverDto>();

    for (Driver driver : driverRepository.findDriversBySeason(year)) {
      result.add(mapper.map(driver, dtoClass));
    }

    return result;
  }

  @Override
  public List<DriverDto> findDriversBySeasonAndRound(int year, int round) {
    List<DriverDto> result = new ArrayList<DriverDto>();

    for (Driver driver : driverRepository.findDriversBySeasonAndRound(year, round)) {
      result.add(mapper.map(driver, dtoClass));
    }

    return result;
  }
}
