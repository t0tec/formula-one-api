package org.formulaone.service;

import org.formulaone.core.model.Race;
import org.formulaone.service.dto.RaceDto;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface RaceReadOnlyService extends GenericService<Race, RaceDto, Integer> {

  /**
   * Finds races entry by using the season year given as a method parameter.
   *
   * @param year The year of the wanted entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season.
   */
  List<RaceDto> findBySeasonYear(int year);

  /**
   * Finds a race entry by using the season year and round given as method parameters.
   *
   * @param year  The year of the wanted entry.
   * @param round The round of the wanted entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season or round.
   */
  RaceDto findBySeasonYearAndRound(int year, int round);

  // --------------------------------------RESULTS-------------------------------------------

  /**
   * Finds a race entry and the results by using the season year and round given as method
   * parameters.
   *
   * @param year  The year of the wanted entry.
   * @param round The round of the wanted entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season or round.
   */
  RaceDto findRaceAndResultsBySeasonYearAndRound(int year, int round);

  /**
   * Finds the last race entry and the results.
   *
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found
   */
  RaceDto findLastRaceAndResults();

  /**
   * Finds the last race entry and the results with finishing position as method parameter.
   *
   * @param position The position of the wanted entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found
   */
  RaceDto findLastRaceAndResultsWithPosition(int position);

  /**
   * Finds a race entry and the results by using the season year and finishing position given as
   * method parameters.
   *
   * @param year     The year of the wanted entry.
   * @param position The position of the wanted entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season or round.
   */
  List<RaceDto> findRaceAndResultsBySeasonYearWithPosition(int year, int position);

  /**
   * Finds a race entry and the results by using the season year, round and finishing position given
   * as method parameters.
   *
   * @param year     The year of the wanted entry.
   * @param round    The round of the wanted entry.
   * @param position The position of the wanted entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season or round.
   */
  RaceDto findRaceAndResultsBySeasonYearAndRoundWithPosition(int year, int round, int position);

  // --------------------------------------QUALIFYING-------------------------------------------

  /**
   * Finds a race entry and the qualifying results by using the season year and round given as
   * method parameters.
   *
   * @param year  The year of the wanted entry.
   * @param round The round of the wanted entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season or round.
   */
  RaceDto findRaceAndQualifyingBySeasonYearAndRound(int year, int round);

  // --------------------------------------LAP TIMES------------------------------------------

  /**
   * Finds a race entry and the lap times by using the season year and round given as method
   * parameters.
   *
   * @param year  The year of the wanted entry.
   * @param round The round of the wanted entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season or round.
   */
  RaceDto findRaceAndLapTimesBySeasonYearAndRound(int year, int round);

  /**
   * Finds a race entry and the lap times by using the season year, round and lap number given as
   * method parameters.
   *
   * @param year  The year of the wanted entry.
   * @param round The round of the wanted entry.
   * @param lap   The lap.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season or round.
   */
  RaceDto findRaceAndLapTimesBySeasonYearAndRoundAndLap(int year, int round, int lap);

  /**
   * Finds a race entry and the lap times by using the season year, round, and driver reference name
   * given as method parameters.
   *
   * @param year       The year of the wanted entry.
   * @param round      The round of the wanted entry.
   * @param driverName The lap.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season or round.
   */
  RaceDto findRaceAndLapTimesBySeasonYearAndRoundAndDriver(int year, int round, String driverName);

  /**
   * Finds a race entry and the lap times by using the season year, round, lap number and driver
   * reference name given as method parameters.
   *
   * @param year       The year of the wanted entry.
   * @param round      The round of the wanted entry.
   * @param lap        The lap.
   * @param driverName The lap.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season or round.
   */
  RaceDto findRaceAndLapTimesBySeasonYearAndRoundAndLapAndDriver(int year, int round, int lap,
                                                                 String driverName);

  // --------------------------------------PIT STOPS------------------------------------------

  /**
   * Finds a race entry and the pit stops by using the season year and round given as method
   * parameters.
   *
   * @param year  The year of the wanted entry.
   * @param round The round of the wanted entry.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season or round.
   */
  RaceDto findRaceAndPitStopsBySeasonYearAndRound(int year, int round);

  /**
   * Finds a race entry and the pit stops by using the season year and round and the stop number
   * given as method parameters.
   *
   * @param year  The year of the wanted entry.
   * @param round The round of the wanted entry.
   * @param stop  The stop.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season or round.
   */
  RaceDto findRaceAndPitStopsBySeasonYearAndRoundAndStop(int year, int round, int stop);

  /**
   * Finds a race entry and the pit stops by using the season year and round and the driverName
   * given as method parameters.
   *
   * @param year       The year of the wanted entry.
   * @param round      The round of the wanted entry.
   * @param driverName The driverName.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season or round.
   */
  RaceDto findRaceAndPitStopsBySeasonYearAndRoundAndDriver(int year, int round, String driverName);

  /**
   * Finds a race entry and the pit stops by using the season year and round and the stop number and
   * the driverName given as method parameters.
   *
   * @param year       The year of the wanted entry.
   * @param round      The round of the wanted entry.
   * @param stop       The stop.
   * @param driverName The driverName.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season or round.
   */
  RaceDto findRaceAndPitStopsBySeasonYearAndRoundAndStopAndDriver(int year, int round, int stop,
                                                                  String driverName);

  /**
   * Finds a race entry and the pit stops by using the season year and round and the lap number
   * given as method parameters.
   *
   * @param year  The year of the wanted entry.
   * @param round The round of the wanted entry.
   * @param lap   The lap.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season or round.
   */
  RaceDto findRaceAndPitStopsBySeasonYearAndRoundAndLap(int year, int round, int lap);

  /**
   * Finds a race entry and the pit stops by using the season year and round and the lap number and
   * the driverName given as method parameters.
   *
   * @param year       The year of the wanted entry.
   * @param round      The round of the wanted entry.
   * @param lap        The lap.
   * @param driverName The driverName.
   * @return The information of the requested entry.
   * @throws org.formulaone.core.exception.NotFoundException if no entry is found with the given
   *                                                         season or round.
   */
  RaceDto findRaceAndPitStopsBySeasonYearAndRoundAndLapAndDriver(int year, int round, int lap,
                                                                 String driverName);

}
