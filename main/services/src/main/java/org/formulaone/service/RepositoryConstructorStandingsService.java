package org.formulaone.service;

import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;

import org.formulaone.core.model.ConstructorStandings;
import org.formulaone.core.model.QConstructorStandings;
import org.formulaone.repository.ConstructorStandingsRepository;
import org.formulaone.service.dto.ConstructorStandingsDto;
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
public class RepositoryConstructorStandingsService
    extends RepositoryGenericService<ConstructorStandings, ConstructorStandingsDto, Long>
    implements ConstructorStandingsReadOnlyService {

  private ConstructorStandingsRepository constructorStandingsRepository;

  public RepositoryConstructorStandingsService() {
    super();
  }

  @Autowired
  public RepositoryConstructorStandingsService(
      ConstructorStandingsRepository constructorStandingsRepository) {
    super(constructorStandingsRepository);
    this.constructorStandingsRepository = constructorStandingsRepository;
  }

  @Override
  public List<ConstructorStandingsDto> findConstructorStandingsBySeasonYearAndRound(int year,
                                                                                    int round) {
    List<ConstructorStandingsDto> result = new ArrayList<ConstructorStandingsDto>();

    OrderSpecifier<Integer> sortByPosition =
        QConstructorStandings.constructorStandings.position.asc();

    for (ConstructorStandings constructorStandings : constructorStandingsRepository
        .findAll(constructorStandingsEqualsSeasonYearAndRound(year, round), sortByPosition)) {
      result.add(mapper.map(constructorStandings, dtoClass));
    }

    return result;
  }

  private static Predicate constructorStandingsEqualsSeasonYearAndRound(final int year,
                                                                        final int round) {
    return QConstructorStandings.constructorStandings.race.season.year.eq(year)
        .and(QConstructorStandings.constructorStandings.race.round.eq(round));
  }

  @Override
  public List<ConstructorStandingsDto> findConstructorStandingsBySeasonYear(int year) {
    List<ConstructorStandingsDto> result = new ArrayList<ConstructorStandingsDto>();

    for (ConstructorStandings constructorStandings : constructorStandingsRepository
        .findConstructorStandingsBySeason(year)) {
      result.add(mapper.map(constructorStandings, dtoClass));
    }

    return result;
  }

  @Override
  public List<ConstructorStandingsDto> findConstructorStandingsBySeasonsAndPosition(int position) {
    List<ConstructorStandingsDto> result = new ArrayList<ConstructorStandingsDto>();

    for (ConstructorStandings constructorStandings : constructorStandingsRepository
        .findConstructorStandingsBySeasonsAndPosition(position)) {
      result.add(mapper.map(constructorStandings, dtoClass));
    }

    return result;
  }

  @Override
  public List<ConstructorStandingsDto> findConstructorStandingsBySeasonsAndConstructor(
      String constructorName) {
    List<ConstructorStandingsDto> result = new ArrayList<ConstructorStandingsDto>();

    for (ConstructorStandings constructorStandings : constructorStandingsRepository
        .findConstructorStandingsBySeasonsAndConstructor(constructorName)) {
      result.add(mapper.map(constructorStandings, dtoClass));
    }

    return result;
  }
}
