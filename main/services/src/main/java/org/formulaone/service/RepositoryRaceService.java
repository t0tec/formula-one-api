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

  @Override
  public RaceDto findLastRaceAndResults() {
    Race raceEntry = raceRepository.findLastRaceAndResults();

    if (raceEntry == null) {
      throw new NotFoundException(Race.class, "UNKNOWN", 0);
    }

    return mapper.map(raceEntry, dtoClass);
  }

  @Override
  public RaceDto findLastRaceAndResultsWithPosition(int position) {
    Race raceEntry = raceRepository.findLastRaceAndResultsWithPosition(position);

    if (raceEntry == null) {
      throw new NotFoundException(Race.class, "position", position);
    }

    return mapper.map(raceEntry, dtoClass);
  }

  @Override
  public List<RaceDto> findRaceAndResultsBySeasonYearWithPosition(int year, int position) {
    List<Race> raceEntries =
        raceRepository.findRaceAndResultsBySeasonYearWithPosition(year, position);

    List<RaceDto> races = new ArrayList<RaceDto>();
    for (Race race : raceEntries) {
      races.add(mapper.map(race, dtoClass));
    }

    return races;
  }

  @Override
  public RaceDto findRaceAndResultsBySeasonYearAndRoundWithPosition(int year, int round,
                                                                    int position) {
    Race raceEntry = raceRepository.findRaceAndResultsBySeasonYearAndRoundWithPosition(year, round,
                                                                                       position);

    if (raceEntry == null) {
      throw new NotFoundException(Race.class, "season/round/position",
                                  year + "/" + round + "/" + position);
    }

    return mapper.map(raceEntry, dtoClass);
  }
}
