package org.formulaone.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import org.formulaone.core.model.QSeason;
import org.formulaone.core.model.Season;
import org.formulaone.repository.config.ExampleApplicationContext;
import org.formulaone.repository.config.Profiles;
import org.junit.Ignore;
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
public class SeasonRepositoryTest {

  private static final int YEAR = 1950;
  private static final String URL = "http://en.wikipedia.org/wiki/1950_Formula_One_season";

  private static final int TOTAL_ENTRIES = 65;

  private static final int NON_EXISTING_YEAR = 1949;

  private static final String SORT_BY_ID = "year";
  private static final Sort.Direction DIRECTION_DESC = Sort.Direction.DESC;

  private static final int PAGE_NUMBER = 0;
  private static final int PAGE_SIZE = 30;
  private static final Pageable PAGEABLE = new PageRequest(PAGE_NUMBER, PAGE_SIZE);

  @Autowired
  private SeasonRepository seasonRepository;

  @Test
  @Ignore("Year is PK value")
  @DatabaseSetup("classpath:season-data.xml")
  public void testFindSeasonById() {
    Season season = seasonRepository.findOne(Long.valueOf(YEAR));

    assertThat(season).isNotNull();
    assertThat(season.getYear()).isEqualTo(YEAR);
    assertThat(season.getUrl()).isEqualTo(URL);
  }

  @Test
  @Ignore("Year is PK value")
  @DatabaseSetup("classpath:season-data.xml")
  public void testReturnNullSeasonWithCorrectId() {
    Season season = seasonRepository.findOne(Long.valueOf(NON_EXISTING_YEAR));

    assertThat(season).isNull();
  }

  @Test
  @DatabaseSetup("classpath:season-no-data.xml")
  public void testReturnEmptyList() {
    List<Season> seasonEntries = (List<Season>) seasonRepository.findAll();

    assertThat(seasonEntries).isEmpty();
  }

  @Test
  @DatabaseSetup("classpath:season-data.xml")
  public void testReturnList() {
    List<Season> seasonEntries = (List<Season>) seasonRepository.findAll();

    assertThat(seasonEntries).hasSize(TOTAL_ENTRIES);
  }

  @Test
  @DatabaseSetup("classpath:season-data.xml")
  public void testReturnListSorted() {
    Sort sort = new Sort(DIRECTION_DESC, SORT_BY_ID);

    List<Season> sortedSeasonEntries = (List<Season>) seasonRepository.findAll(sort);

    assertThat(sortedSeasonEntries).hasSize(TOTAL_ENTRIES);
    assertThat(sortedSeasonEntries.get(sortedSeasonEntries.size() - 1).getYear()).isEqualTo(YEAR);
    assertThat(sortedSeasonEntries.get(sortedSeasonEntries.size() - 1).getUrl()).isEqualTo(URL);
  }

  @Test
  @DatabaseSetup("classpath:season-data.xml")
  public void testReturnsPage() {
    Page<Season> seasonPage = seasonRepository.findAll(PAGEABLE);

    PageAssertion.assertThat(seasonPage)
        .hasTotalPages((int) Math.ceil((double) TOTAL_ENTRIES / PAGE_SIZE))
        .hasTotalElements(TOTAL_ENTRIES)
        .hasPageSize(PAGE_SIZE)
        .hasPageNumber(PAGE_NUMBER)
        .hasContentSize(PAGE_SIZE);
  }

  @Test
  @DatabaseSetup("classpath:season-data.xml")
  public void testFindSeasonByYear() {
    Season season = seasonRepository.findOne(QSeason.season.year.eq(YEAR));

    assertThat(season).isNotNull();
    assertThat(season.getYear()).isEqualTo(YEAR);
    assertThat(season.getUrl()).isEqualTo(URL);
  }

  @Test
  @DatabaseSetup("classpath:season-data.xml")
  public void testReturnNullSeasonWithWrongYear() {
    Season season = seasonRepository.findOne(QSeason.season.year.eq(NON_EXISTING_YEAR));

    assertThat(season).isNull();
  }
}
