package org.formulaone.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

import org.formulaone.core.model.Race;
import org.formulaone.repository.config.ExampleApplicationContext;
import org.formulaone.repository.config.Profiles;
import org.junit.Before;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
@DatabaseTearDown("classpath:race-no-data.xml")
public class RaceRepositoryTest {

  private static final Long ID = 1L;
  private static final String NAME = "Australian Grand Prix";
  private static final int SEASON_YEAR = 2009;
  private static final int ROUND = 1;

  private static final int TOTAL_ENTRIES = 916;
  private static final int SEASON_TOTAL_ENTRIES = 17;
  private static final int RACE_RESULTS_SIZE = 20;

  private static final Long NON_EXISTING_ID = -1L;
  private static final int NON_EXISTING_SEASON_YEAR = 1949;
  private static final int NON_EXISTING_ROUND = -1;

  private static final String SORT_BY_ID = "id";
  private static final Sort.Direction DIRECTION_DESC = Sort.Direction.DESC;

  private static final int PAGE_NUMBER = 0;
  private static final int PAGE_SIZE = 30;
  private static final Pageable PAGEABLE = new PageRequest(PAGE_NUMBER, PAGE_SIZE);

  @Autowired
  private RaceRepository raceRepository;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml"})
  public void testFindRaceById() {
    Race race = raceRepository.findOne(ID);

    assertThat(race).isNotNull();
    assertThat(race.getId()).isEqualTo(ID);
    assertThat(race.getName()).isEqualTo(NAME);
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml"})
  public void testReturnNullRaceWithCorrectId() {
    Race race = raceRepository.findOne(NON_EXISTING_ID);

    assertThat(race).isNull();
  }

  @Test
  @DatabaseSetup("classpath:race-no-data.xml")
  public void testReturnEmptyList() {
    List<Race> raceEntries = new ArrayList<Race>();

    raceEntries = (List<Race>) raceRepository.findAll();

    assertThat(raceEntries).isEmpty();
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml"})
  public void testReturnList() {
    List<Race> raceEntries = (List<Race>) raceRepository.findAll();

    assertThat(raceEntries).hasSize(TOTAL_ENTRIES);
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml"})
  public void testReturnListSorted() {
    Sort sort = new Sort(DIRECTION_DESC, SORT_BY_ID);

    List<Race> sortedRaceEntries = (List<Race>) raceRepository.findAll(sort);

    assertThat(sortedRaceEntries).hasSize(TOTAL_ENTRIES);
    assertThat(sortedRaceEntries.get(sortedRaceEntries.size() - 1).getId()).isEqualTo(ID);
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml"})
  public void testReturnsPage() {
    Page<Race> racePage = raceRepository.findAll(PAGEABLE);

    PageAssertion.assertThat(racePage)
        .hasTotalPages((int) Math.ceil((double) TOTAL_ENTRIES / PAGE_SIZE))
        .hasTotalElements(TOTAL_ENTRIES)
        .hasPageSize(PAGE_SIZE)
        .hasPageNumber(PAGE_NUMBER)
        .hasContentSize(PAGE_SIZE);
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml"})
  public void testFindRacesBySeason() {
    List<Race> raceEntries = raceRepository.findBySeasonYear(SEASON_YEAR);

    assertThat(raceEntries).isNotNull();
    assertThat(raceEntries).hasSize(SEASON_TOTAL_ENTRIES);
  }

  @Test
  @DatabaseSetup("classpath:season-data.xml")
  public void testReturnEmptyListWithNonExistingSeason() {
    List<Race> raceEntries = raceRepository.findBySeasonYear(NON_EXISTING_SEASON_YEAR);

    assertThat(raceEntries).isEmpty();
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml"})
  public void testFindRaceBySeasonAndRound() {
    Race race = raceRepository.findBySeasonYearAndRound(SEASON_YEAR, ROUND);

    assertThat(race).isNotNull();
    assertThat(race.getId()).isEqualTo(ID);
    assertThat(race.getName()).isEqualTo(NAME);
  }

  @Test
  @DatabaseSetup({"classpath:race-no-data.xml"})
  public void testReturnNullRaceWithWrongSeasonAndRound() {
    Race
        race =
        raceRepository.findBySeasonYearAndRound(NON_EXISTING_SEASON_YEAR, NON_EXISTING_ROUND);

    assertThat(race).isNull();
  }

  @Test
  @Transactional
  @DatabaseSetup({"classpath:status-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml", "classpath:result-data.xml"})
  public void testFindRaceResultsBySeasonAndRound() {
    Race race = raceRepository.findRaceAndResultsBySeasonYearAndRound(SEASON_YEAR, ROUND);

    assertThat(race).isNotNull();
    assertThat(race.getId()).isEqualTo(ID);
    assertThat(race.getName()).isEqualTo(NAME);

    assertThat(race.getResults()).hasSize(RACE_RESULTS_SIZE);
  }

  @Test
  @Transactional
  @DatabaseSetup({"classpath:status-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml", "classpath:result-data.xml"})
  public void testFindLastRaceResults() {
    Race race = raceRepository.findLastRaceAndResults();

    assertThat(race).isNotNull();
    assertThat(race.getId()).isEqualTo(360);
    assertThat(race.getName()).isEqualTo("United States Grand Prix");

    assertThat(race.getResults()).hasSize(20);
  }
}
