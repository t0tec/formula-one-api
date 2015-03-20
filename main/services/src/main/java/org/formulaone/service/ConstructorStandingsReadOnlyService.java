package org.formulaone.service;

import org.formulaone.core.model.ConstructorStandings;
import org.formulaone.service.dto.ConstructorStandingsDto;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public interface ConstructorStandingsReadOnlyService
    extends GenericService<ConstructorStandings, ConstructorStandingsDto, Long> {

  /**
   * Finds all constructor standings after a specific race with the year and round method
   * parameters.
   *
   * @param year  The year.
   * @param round The round.
   * @return The information of the requested entry.
   */
  List<ConstructorStandingsDto> findConstructorStandingsBySeasonYearAndRound(int year, int round);

  /**
   * Finds all constructor standings at the end of the season with the year as a method parameter If
   * the season has not ended you will get the current standings.
   *
   * @param year The year.
   * @return The information of the requested entry.
   */
  List<ConstructorStandingsDto> findConstructorStandingsBySeasonYear(int year);

  /**
   * Finds the constructor standings with the position as a method parameter for all seasons You can
   * get the winners of the constructor standings for all seasons by passing 1 as position
   * parameter.
   *
   * @param position The position.
   * @return The information of the requested entry.
   */
  List<ConstructorStandingsDto> findConstructorStandingsBySeasonsAndPosition(int position);


  /**
   * Finds the constructor standings for a particular constructor for every season they have taken
   * part.
   *
   * @param constructorName The constructorName.
   * @return The information of the requested entry.
   */
  List<ConstructorStandingsDto> findConstructorStandingsBySeasonsAndConstructor(
      String constructorName);

}
