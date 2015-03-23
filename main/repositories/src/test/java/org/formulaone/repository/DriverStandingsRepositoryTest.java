package org.formulaone.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.mysema.query.types.Predicate;

import org.formulaone.core.model.DriverStandings;
import org.formulaone.core.model.QDriverStandings;
import org.formulaone.repository.config.ExampleApplicationContext;
import org.formulaone.repository.config.Profiles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(Profiles.INTEGRATION_TEST)
@ContextConfiguration(classes = {ExampleApplicationContext.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
                         DirtiesContextTestExecutionListener.class,
                         TransactionalTestExecutionListener.class,
                         DbUnitTestExecutionListener.class})
@DatabaseTearDown("classpath:driver_standings-no-data.xml")
public class DriverStandingsRepositoryTest {

  private static final Integer ID = 64384;
  private static final String DRIVER_REFERENCE_NAME = "hamilton";

  private static final int SEASON_YEAR = 2011;
  private static final int ROUND = 1;

  private static final int TOTAL_ENTRIES = 1829;


  private static final Integer NON_EXISTING_ID = -1;

  private static final String SORT_BY_ID = "id";
  private static final Sort.Direction DIRECTION_DESC = Sort.Direction.DESC;

  private static final int PAGE_NUMBER = 0;
  private static final int PAGE_SIZE = 30;
  private static final Pageable PAGEABLE = new PageRequest(PAGE_NUMBER, PAGE_SIZE);

  @Autowired
  private DriverStandingsRepository driverStandingsRepository;

  @Test
  @DatabaseSetup({"classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:driver-data.xml",
                  "classpath:race-data.xml", "classpath:driver_standings-data.xml"})
  public void testFindById() {
    DriverStandings driverStandings = driverStandingsRepository.findOne(ID);

    assertThat(driverStandings).isNotNull();
    assertThat(driverStandings.getId()).isEqualTo(ID);
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:driver-data.xml",
                  "classpath:race-data.xml", "classpath:driver_standings-data.xml"})
  public void testReturnNullWithCorrectId() {
    DriverStandings driverStandings = driverStandingsRepository.findOne(NON_EXISTING_ID);

    assertThat(driverStandings).isNull();
  }

  @Test
  @DatabaseSetup("classpath:driver_standings-no-data.xml")
  public void testReturnEmptyList() {
    List<DriverStandings> driverEntries =
        (List<DriverStandings>) driverStandingsRepository.findAll();

    assertThat(driverEntries).isEmpty();
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:driver-data.xml",
                  "classpath:race-data.xml", "classpath:driver_standings-data.xml"})
  public void testReturnList() {
    List<DriverStandings> driverEntries =
        (List<DriverStandings>) driverStandingsRepository.findAll();

    assertThat(driverEntries).hasSize(TOTAL_ENTRIES);
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:driver-data.xml",
                  "classpath:race-data.xml", "classpath:driver_standings-data.xml"})
  public void testReturnListSorted() {
    Sort sort = new Sort(DIRECTION_DESC, SORT_BY_ID);

    List<DriverStandings> sortedDriverEntries =
        (List<DriverStandings>) driverStandingsRepository.findAll(sort);

    assertThat(sortedDriverEntries).hasSize(TOTAL_ENTRIES);
    assertThat(sortedDriverEntries.get(sortedDriverEntries.size() - 1).getId())
        .isEqualTo(ID);
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:driver-data.xml",
                  "classpath:race-data.xml", "classpath:driver_standings-data.xml"})
  public void testReturnsPage() {
    Page<DriverStandings> driverStandingsPage = driverStandingsRepository.findAll(PAGEABLE);

    PageAssertion.assertThat(driverStandingsPage)
        .hasTotalPages((int) Math.ceil((double) TOTAL_ENTRIES / PAGE_SIZE))
        .hasTotalElements(TOTAL_ENTRIES)
        .hasPageSize(PAGE_SIZE)
        .hasPageNumber(PAGE_NUMBER)
        .hasContentSize(PAGE_SIZE);
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:driver-data.xml",
                  "classpath:race-data.xml", "classpath:driver_standings-data.xml"})
  public void testReturnDriverStandingsBySeasonYearAndRound() {
    List<DriverStandings> driverStandingsEntries = (List<DriverStandings>) driverStandingsRepository
            .findAll(findDriverStandingsBySeasonYearAndRound(SEASON_YEAR, ROUND));

    assertThat(driverStandingsEntries).hasSize(20);
  }

  public static Predicate findDriverStandingsBySeasonYearAndRound(final int year,
                                                                  final int round) {
    return QDriverStandings.driverStandings.race.season.year.eq(year)
        .and(QDriverStandings.driverStandings.race.round.eq(round));
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:driver-data.xml",
                  "classpath:race-data.xml", "classpath:driver_standings-data.xml"})
  public void testReturnDriverStandingsBySeasonsAndPosition() {
    List<DriverStandings> driverStandingsEntries = driverStandingsRepository
        .findDriverStandingsBySeasonsAndPosition(1);

    assertThat(driverStandingsEntries).hasSize(4);
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:driver-data.xml",
                  "classpath:race-data.xml", "classpath:driver_standings-data.xml"})
  public void testReturnDriverStandingsBySeasonYear() {
    List<DriverStandings> driverStandingsEntries = driverStandingsRepository
        .findDriverStandingsBySeason(SEASON_YEAR);

    assertThat(driverStandingsEntries).hasSize(28);
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:driver-data.xml",
                  "classpath:race-data.xml", "classpath:driver_standings-data.xml"})
  public void testReturnDriverStandingsByDriver() {
    List<DriverStandings> driverStandingsEntries = driverStandingsRepository
            .findDriverStandingsBySeasonsAndDriver(DRIVER_REFERENCE_NAME);

    assertThat(driverStandingsEntries).hasSize(4);
  }
}
