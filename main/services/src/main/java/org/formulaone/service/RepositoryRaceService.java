package org.formulaone.service;

import org.formulaone.core.exception.NotFoundException;
import org.formulaone.core.model.Race;
import org.formulaone.core.model.Season;
import org.formulaone.repository.RaceRepository;
import org.formulaone.service.dto.RaceDto;
import org.formulaone.service.dto.SeasonDto;
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
  public RepositoryRaceService(RaceRepository constructorRepository) {
    super(constructorRepository);
    this.raceRepository = constructorRepository;
  }

  @Override
  public List<RaceDto> findBySeason(SeasonDto season) {
    // TODO: need to pass (int) year instead
    Season temp = new Season(season.getYear(), season.getUrl());

    List<Race> raceEntries = raceRepository.findBySeason(temp);

    List<RaceDto> races = new ArrayList<RaceDto>();
    for (Race race : raceEntries) {
      races.add(mapper.map(race, dtoClass));
    }

    return races;
  }

  @Override
  public RaceDto findBySeasonAndRound(SeasonDto season, int round) {
    // TODO: need to pass (int) year instead
    Season temp = new Season(season.getYear(), season.getUrl());
    Race raceEntry = raceRepository.findBySeasonAndRound(temp, round);

    if (raceEntry == null) {
      throw new NotFoundException(Race.class, "season", season);
    }

    return mapper.map(raceEntry, dtoClass);
  }
}
