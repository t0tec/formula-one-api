package org.formulaone.service;

import org.formulaone.core.model.DriverStandings;
import org.formulaone.service.dto.DriverStandingsDto;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface DriverStandingsReadOnlyService
    extends GenericService<DriverStandings, DriverStandingsDto, Long> {

  /**
   * Finds all driver standings after a specific race with the year and round method
   * parameters.
   *
   * @param year  The year.
   * @param round The round.
   * @return The information of the requested entry.
   */
  List<DriverStandingsDto> findDriverStandingsBySeasonYearAndRound(int year, int round);

  /**
   * Finds all driver standings at the end of the season with the year as a method parameter If
   * the season has not ended you will get the current standings.
   *
   * @param year The year.
   * @return The information of the requested entry.
   */
  List<DriverStandingsDto> findDriverStandingsBySeasonYear(int year);

  /**
   * Finds the driver standings with the position as a method parameter for all seasons You can
   * get the winners of the driver standings for all seasons by passing 1 as position
   * parameter.
   *
   * @param position The position.
   * @return The information of the requested entry.
   */
  List<DriverStandingsDto> findDriverStandingsBySeasonsAndPosition(int position);


  /**
   * Finds the driver standings for a particular driver for every season he has taken
   * part.
   *
   * @param driverName The driverName.
   * @return The information of the requested entry.
   */
  List<DriverStandingsDto> findDriverStandingsBySeasonsAndDriver(
      String driverName);

}
