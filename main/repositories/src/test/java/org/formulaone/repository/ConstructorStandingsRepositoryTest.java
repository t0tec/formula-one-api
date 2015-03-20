package org.formulaone.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.mysema.query.types.Predicate;

import org.formulaone.core.model.ConstructorStandings;
import org.formulaone.core.model.QConstructorStandings;
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
@DatabaseTearDown("classpath:constructor_standings-no-data.xml")
public class ConstructorStandingsRepositoryTest {

  private static final Long ID = 24540L;
  private static final String CONSTRUCTOR_REFERENCE_NAME = "mclaren";

  private static final int SEASON_YEAR = 2011;
  private static final int ROUND = 1;

  private static final int TOTAL_ENTRIES = 465;


  private static final Long NON_EXISTING_ID = -1L;

  private static final String SORT_BY_ID = "id";
  private static final Sort.Direction DIRECTION_DESC = Sort.Direction.DESC;

  private static final int PAGE_NUMBER = 0;
  private static final int PAGE_SIZE = 30;
  private static final Pageable PAGEABLE = new PageRequest(PAGE_NUMBER, PAGE_SIZE);

  @Autowired
  private ConstructorStandingsRepository standingsRepositoryRepository;

  @Test
  @DatabaseSetup({"classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:constructor-data.xml",
                  "classpath:race-data.xml", "classpath:constructor_standings-data.xml"})
  public void testFindById() {
    ConstructorStandings constructorStandings = standingsRepositoryRepository.findOne(ID);

    assertThat(constructorStandings).isNotNull();
    assertThat(constructorStandings.getId()).isEqualTo(ID);
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:constructor-data.xml",
                  "classpath:race-data.xml", "classpath:constructor_standings-data.xml"})
  public void testReturnNullWithCorrectId() {
    ConstructorStandings constructorStandings =
        standingsRepositoryRepository.findOne(NON_EXISTING_ID);

    assertThat(constructorStandings).isNull();
  }

  @Test
  @DatabaseSetup("classpath:constructor_standings-no-data.xml")
  public void testReturnEmptyList() {
    List<ConstructorStandings> constructorEntries =
        (List<ConstructorStandings>) standingsRepositoryRepository.findAll();

    assertThat(constructorEntries).isEmpty();
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:constructor-data.xml",
                  "classpath:race-data.xml", "classpath:constructor_standings-data.xml"})
  public void testReturnList() {
    List<ConstructorStandings> constructorEntries =
        (List<ConstructorStandings>) standingsRepositoryRepository.findAll();

    assertThat(constructorEntries).hasSize(TOTAL_ENTRIES);
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:constructor-data.xml",
                  "classpath:race-data.xml", "classpath:constructor_standings-data.xml"})
  public void testReturnListSorted() {
    Sort sort = new Sort(DIRECTION_DESC, SORT_BY_ID);

    List<ConstructorStandings> sortedConstructorEntries =
        (List<ConstructorStandings>) standingsRepositoryRepository.findAll(sort);

    assertThat(sortedConstructorEntries).hasSize(TOTAL_ENTRIES);
    assertThat(sortedConstructorEntries.get(sortedConstructorEntries.size() - 1).getId())
        .isEqualTo(ID);
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:constructor-data.xml",
                  "classpath:race-data.xml", "classpath:constructor_standings-data.xml"})
  public void testReturnsPage() {
    Page<ConstructorStandings> constructorPage = standingsRepositoryRepository.findAll(PAGEABLE);

    PageAssertion.assertThat(constructorPage)
        .hasTotalPages((int) Math.ceil((double) TOTAL_ENTRIES / PAGE_SIZE))
        .hasTotalElements(TOTAL_ENTRIES)
        .hasPageSize(PAGE_SIZE)
        .hasPageNumber(PAGE_NUMBER)
        .hasContentSize(PAGE_SIZE);
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:constructor-data.xml",
                  "classpath:race-data.xml", "classpath:constructor_standings-data.xml"})
  public void testReturnConstructorStandingsBySeasonYearAndRound() {
    List<ConstructorStandings> constructorEntries =
        (List<ConstructorStandings>) standingsRepositoryRepository
            .findAll(findConstructorStandingsBySeasonYearAndRound(SEASON_YEAR, ROUND));

    assertThat(constructorEntries).hasSize(10);
  }

  public static Predicate findConstructorStandingsBySeasonYearAndRound(final int year,
                                                                        final int round) {
    return QConstructorStandings.constructorStandings.race.season.year.eq(year)
        .and(QConstructorStandings.constructorStandings.race.round.eq(round));
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:constructor-data.xml",
                  "classpath:race-data.xml", "classpath:constructor_standings-data.xml"})
  public void testReturnConstructorStandingsBySeasonsAndPosition() {
    List<ConstructorStandings> constructorEntries = standingsRepositoryRepository
        .findConstructorStandingsBySeasonsAndPosition(1);

    assertThat(constructorEntries).hasSize(2);
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:constructor-data.xml",
                  "classpath:race-data.xml", "classpath:constructor_standings-data.xml"})
  public void testReturnConstructorStandingsBySeasonYear() {
    List<ConstructorStandings> constructorEntries = standingsRepositoryRepository
        .findConstructorStandingsBySeason(SEASON_YEAR);

    assertThat(constructorEntries).hasSize(12);
  }

  @Test
  @DatabaseSetup({"classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:constructor-data.xml",
                  "classpath:race-data.xml", "classpath:constructor_standings-data.xml"})
  public void testReturnConstructorStandingsByConstructor() {
    List<ConstructorStandings> constructorEntries = standingsRepositoryRepository
            .findConstructorStandingsBySeasonsAndConstructor(CONSTRUCTOR_REFERENCE_NAME);

    assertThat(constructorEntries).hasSize(2);
  }
}
