package org.formulaone.service;

import org.formulaone.core.exception.NotFoundException;
import org.formulaone.core.model.Race;
import org.formulaone.repository.RaceRepository;
import org.formulaone.service.dto.RaceDto;
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
public class RepositoryRaceService extends RepositoryGenericService<Race, RaceDto, Long>
    implements RaceReadOnlyService {

  private RaceRepository raceRepository;

  public RepositoryRaceService() {
    super();
  }

  @Autowired
  public RepositoryRaceService(RaceRepository raceRepository) {
    super(raceRepository);
    this.raceRepository = raceRepository;
  }

  @Override
  public List<RaceDto> findBySeasonYear(int year) {
    List<Race> raceEntries = raceRepository.findBySeasonYear(year);

    List<RaceDto> races = new ArrayList<RaceDto>();
    for (Race race : raceEntries) {
      races.add(mapper.map(race, dtoClass));
    }

    return races;
  }

  @Override
  public RaceDto findBySeasonYearAndRound(int year, int round) {
    Race raceEntry = raceRepository.findBySeasonYearAndRound(year, round);

    if (raceEntry == null) {
      throw new NotFoundException(Race.class, "season/round", year + "/" + round);
    }

    return mapper.map(raceEntry, dtoClass);
  }

  @Override
  public RaceDto findRaceAndResultsBySeasonYearAndRound(int year, int round) {
    Race raceEntry = raceRepository.findRaceAndResultsBySeasonYearAndRound(year, round);

    if (raceEntry == null) {
      throw new NotFoundException(Race.class, "season/round", year + "/" + round);
    }

    return mapper.map(raceEntry, dtoClass);
  }
}
