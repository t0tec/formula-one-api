package org.formulaone.service;

import org.formulaone.core.exception.NotFoundException;
import org.formulaone.core.model.QRace;
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
public class RepositoryRaceService extends RepositoryGenericService<Race, RaceDto, Integer>
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
    List<RaceDto> races = new ArrayList<RaceDto>();

    for (Race race : raceRepository.findAll(QRace.race.season.year.eq(year))) {
      races.add(mapper.map(race, dtoClass));
    }

    return races;
  }

  @Override
  public RaceDto findBySeasonYearAndRound(int year, int round) {
    Race raceEntry = raceRepository.findOne(
        QRace.race.season.year.eq(year).and(QRace.race.round.eq(round)));

    if (raceEntry == null) {
      throw new NotFoundException(Race.class, "season/round", year + "/" + round);
    }

    return mapper.map(raceEntry, dtoClass);
  }

  // --------------------------------------RESULTS--------------------------------------------
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
    List<RaceDto> races = new ArrayList<RaceDto>();

    for (Race race : raceRepository.findRaceAndResultsBySeasonYearWithPosition(year, position)) {
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

  // --------------------------------------QUALIFYING-----------------------------------------
  @Override
  public RaceDto findRaceAndQualifyingBySeasonYearAndRound(int year, int round) {
    Race raceEntry = raceRepository.findRaceAndQualifyingBySeasonYearAndRound(year, round);

    if (raceEntry == null) {
      throw new NotFoundException(Race.class, "season/round", year + "/" + round);
    }

    return mapper.map(raceEntry, dtoClass);
  }

  // --------------------------------------LAP TIMES------------------------------------------
  @Override
  public RaceDto findRaceAndLapTimesBySeasonYearAndRound(int year, int round) {
    Race raceEntry = raceRepository.findRaceAndLapTimesBySeasonYearAndRound(year, round);

    if (raceEntry == null) {
      throw new NotFoundException(Race.class, "season/round", year + "/" + round);
    }

    return mapper.map(raceEntry, dtoClass);
  }

  @Override
  public RaceDto findRaceAndLapTimesBySeasonYearAndRoundAndLap(int year, int round, int lap) {
    Race raceEntry = raceRepository.findRaceAndLapTimesBySeasonYearAndRoundAndLap(year, round, lap);

    if (raceEntry == null) {
      throw new NotFoundException(Race.class, "season/round/lap", year + "/" + round + "/" + lap);
    }

    return mapper.map(raceEntry, dtoClass);
  }

  @Override
  public RaceDto findRaceAndLapTimesBySeasonYearAndRoundAndDriver(int year, int round,
                                                                  String driverName) {
    Race raceEntry = raceRepository.findRaceAndLapTimesBySeasonYearAndRoundAndDriver
        (year, round, driverName);

    if (raceEntry == null) {
      throw new NotFoundException(Race.class, "season/round/driverReferenceName",
                                  year + "/" + round + "/" + driverName);
    }

    return mapper.map(raceEntry, dtoClass);
  }

  @Override
  public RaceDto findRaceAndLapTimesBySeasonYearAndRoundAndLapAndDriver(int year, int round,
                                                                        int lap,
                                                                        String driverName) {
    Race raceEntry = raceRepository.findRaceAndLapTimesBySeasonYearAndRoundAndLapAndDriver
        (year, round, lap, driverName);

    if (raceEntry == null) {
      throw new NotFoundException(Race.class, "season/round/driverReferenceName/lap",
                                  year + "/" + round + "/" + driverName + "/" + lap);
    }

    return mapper.map(raceEntry, dtoClass);
  }

  // --------------------------------------PIT STOPS------------------------------------------

  @Override
  public RaceDto findRaceAndPitStopsBySeasonYearAndRound(int year, int round) {
    Race raceEntry = raceRepository.findRaceAndPitStopsBySeasonYearAndRound(year, round);

    if (raceEntry == null) {
      throw new NotFoundException(Race.class, "season/round", year + "/" + round);
    }

    return mapper.map(raceEntry, dtoClass);
  }

  @Override
  public RaceDto findRaceAndPitStopsBySeasonYearAndRoundAndStop(int year, int round, int stop) {
    Race raceEntry = raceRepository.findRaceAndPitStopsBySeasonYearAndRoundAndStop(year, round,
                                                                                   stop);

    if (raceEntry == null) {
      throw new NotFoundException(Race.class, "season/round/stop", year + "/" + round + "/" + stop);
    }

    return mapper.map(raceEntry, dtoClass);
  }

  @Override
  public RaceDto findRaceAndPitStopsBySeasonYearAndRoundAndDriver(int year, int round,
                                                                  String driverName) {
    Race raceEntry = raceRepository.findRaceAndPitStopsBySeasonYearAndRoundAndDriver(year, round,
                                                                                     driverName);

    if (raceEntry == null) {
      throw new NotFoundException(Race.class, "season/round/driverReferenceName",
                                  year + "/" + round + "/" + driverName);
    }

    return mapper.map(raceEntry, dtoClass);
  }

  @Override
  public RaceDto findRaceAndPitStopsBySeasonYearAndRoundAndStopAndDriver(int year, int round,
                                                                         int stop,
                                                                         String driverName) {
    Race raceEntry = raceRepository.findRaceAndPitStopsBySeasonYearAndRoundAndStopAndDriver(year,
                                                                                            round,
                                                                                            stop,
                                                                                            driverName);

    if (raceEntry == null) {
      throw new NotFoundException(Race.class, "season/round/driverReferenceName/stop",
                                  year + "/" + round + "/" + driverName + "/" + stop);
    }

    return mapper.map(raceEntry, dtoClass);
  }

  @Override
  public RaceDto findRaceAndPitStopsBySeasonYearAndRoundAndLap(int year, int round,
                                                                        int lap) {
    Race raceEntry = raceRepository.findRaceAndPitStopsBySeasonYearAndRoundAndLap(year, round, lap);

    if (raceEntry == null) {
      throw new NotFoundException(Race.class, "season/round/lap", year + "/" + round + "/" + lap);
    }

    return mapper.map(raceEntry, dtoClass);
  }

  @Override
  public RaceDto findRaceAndPitStopsBySeasonYearAndRoundAndLapAndDriver(int year, int round,
                                                                        int lap,
                                                                        String driverName) {
    Race raceEntry = raceRepository.findRaceAndPitStopsBySeasonYearAndRoundAndLapAndDriver(year,
                                                                                           round,
                                                                                           lap,
                                                                                           driverName);

    if (raceEntry == null) {
      throw new NotFoundException(Race.class, "season/round/driverReferenceName/lap",
                                  year + "/" + round + "/" + driverName + "/" + lap);
    }

    return mapper.map(raceEntry, dtoClass);
  }
}
