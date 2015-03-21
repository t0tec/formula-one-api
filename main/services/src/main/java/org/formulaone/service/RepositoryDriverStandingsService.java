package org.formulaone.service;

import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;

import org.formulaone.core.model.DriverStandings;
import org.formulaone.core.model.QDriverStandings;
import org.formulaone.repository.DriverStandingsRepository;
import org.formulaone.service.dto.DriverStandingsDto;
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
public class RepositoryDriverStandingsService
    extends RepositoryGenericService<DriverStandings, DriverStandingsDto, Long>
    implements DriverStandingsReadOnlyService {

  private DriverStandingsRepository driverStandingsRepository;

  public RepositoryDriverStandingsService() {
    super();
  }

  @Autowired
  public RepositoryDriverStandingsService(
      DriverStandingsRepository driverStandingsRepository) {
    super(driverStandingsRepository);
    this.driverStandingsRepository = driverStandingsRepository;
  }

  @Override
  public List<DriverStandingsDto> findDriverStandingsBySeasonYearAndRound(int year,
                                                                          int round) {
    List<DriverStandingsDto> result = new ArrayList<DriverStandingsDto>();

    OrderSpecifier<Integer> sortByPosition =
        QDriverStandings.driverStandings.position.asc();

    for (DriverStandings driverStandings : driverStandingsRepository
        .findAll(driverStandingsEqualsSeasonYearAndRound(year, round), sortByPosition)) {
      result.add(mapper.map(driverStandings, dtoClass));
    }

    return result;
  }

  private static Predicate driverStandingsEqualsSeasonYearAndRound(final int year,
                                                                   final int round) {
    return QDriverStandings.driverStandings.race.season.year.eq(year)
        .and(QDriverStandings.driverStandings.race.round.eq(round));
  }

  @Override
  public List<DriverStandingsDto> findDriverStandingsBySeasonYear(int year) {

    List<DriverStandingsDto> result = new ArrayList<DriverStandingsDto>();

    for (DriverStandings driverStandings : driverStandingsRepository
        .findDriverStandingsBySeason(year)) {
      result.add(mapper.map(driverStandings, dtoClass));
    }

    return result;
  }

  @Override
  public List<DriverStandingsDto> findDriverStandingsBySeasonsAndPosition(int position) {
    List<DriverStandingsDto> result = new ArrayList<DriverStandingsDto>();

    for (DriverStandings driverStandings : driverStandingsRepository
        .findDriverStandingsBySeasonsAndPosition(position)) {
      result.add(mapper.map(driverStandings, dtoClass));
    }

    return result;
  }

  @Override
  public List<DriverStandingsDto> findDriverStandingsBySeasonsAndDriver(String driverName) {
    List<DriverStandingsDto> result = new ArrayList<DriverStandingsDto>();

    for (DriverStandings driverStandings : driverStandingsRepository
        .findDriverStandingsBySeasonsAndDriver(driverName)) {
      result.add(mapper.map(driverStandings, dtoClass));
    }

    return result;
  }
}
