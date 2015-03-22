package org.formulaone.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

import org.formulaone.core.model.Result;
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
@DatabaseTearDown("classpath:result-no-data.xml")
public class ResultRepositoryTest {

  private static final Long ID = 20779L;
  private static final int SEASON_YEAR = 2011;
  private static final int ROUND = 1;
  private static final int FIRST_POSITION = 1;

  private static final int TOTAL_ENTRIES = 1758;
  private static final int RESULTS_TOTAL_P1 = 77;

  private static final Long NON_EXISTING_ID = -1L;
  ;

  private static final String SORT_BY_ID = "id";
  private static final Sort.Direction DIRECTION_DESC = Sort.Direction.DESC;

  private static final int PAGE_NUMBER = 0;
  private static final int PAGE_SIZE = 30;
  private static final Pageable PAGEABLE = new PageRequest(PAGE_NUMBER, PAGE_SIZE);

  @Autowired
  private ResultRepository resultRepository;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  @Transactional
  @DatabaseSetup({"classpath:status-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml", "classpath:result-data.xml"})
  public void testFindResultById() {
    Result result = resultRepository.findOne(ID);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(ID);
  }

  @Test
  @Transactional
  @DatabaseSetup({"classpath:status-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml", "classpath:result-data.xml"})
  public void testReturnNullResultWithCorrectId() {
    Result result = resultRepository.findOne(NON_EXISTING_ID);

    assertThat(result).isNull();
  }

  @Test
  @Transactional
  @DatabaseSetup({"classpath:status-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml", "classpath:result-no-data.xml"})
  public void testReturnEmptyList() {
    List<Result> resultEntries = (List<Result>) resultRepository.findAll();

    assertThat(resultEntries).isEmpty();
  }

  @Test
  @DatabaseSetup({"classpath:status-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml", "classpath:result-data.xml"})
  public void testReturnList() {
    List<Result> resultEntries = (List<Result>) resultRepository.findAll();

    assertThat(resultEntries).hasSize(TOTAL_ENTRIES);
  }

  @Test
  @DatabaseSetup({"classpath:status-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml", "classpath:result-data.xml"})
  public void testReturnListSorted() {
    Sort sort = new Sort(DIRECTION_DESC, SORT_BY_ID);

    List<Result> sortedResultEntries = (List<Result>) resultRepository.findAll(sort);

    assertThat(sortedResultEntries).hasSize(TOTAL_ENTRIES);
    assertThat(sortedResultEntries.get(sortedResultEntries.size() - 1).getId()).isEqualTo(ID);
  }

  @Test
  @DatabaseSetup({"classpath:status-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml", "classpath:result-data.xml"})
  public void testReturnsPage() {
    Page<Result> resultPage = resultRepository.findAll(PAGEABLE);

    PageAssertion.assertThat(resultPage)
        .hasTotalPages((int) Math.ceil((double) TOTAL_ENTRIES / PAGE_SIZE))
        .hasTotalElements(TOTAL_ENTRIES)
        .hasPageSize(PAGE_SIZE)
        .hasPageNumber(PAGE_NUMBER)
        .hasContentSize(PAGE_SIZE);
  }

  @Test
  @DatabaseSetup({"classpath:status-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml", "classpath:result-data.xml"})
  public void testFindResultsByPositionOrder() {
    List<Result> resultEntries = resultRepository.findResultByPositionOrder(FIRST_POSITION);

    assertThat(resultEntries).isNotNull();
    assertThat(resultEntries).hasSize(RESULTS_TOTAL_P1);
  }

  @Test
  @DatabaseSetup({"classpath:status-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml", "classpath:result-data.xml"})
  public void testFindResultsByPositionOrderAndSeason() {
    List<Result> resultEntries =
        resultRepository.findResultByPositionOrderAndRaceSeasonYear(FIRST_POSITION, SEASON_YEAR);

    assertThat(resultEntries).isNotNull();
    assertThat(resultEntries).isNotEmpty();
  }

  @Test
  @Transactional
  @DatabaseSetup({"classpath:status-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml", "classpath:result-data.xml"})
  public void testFindResultsByPositionOrderAndSeasonAndRound() {
    Result
        result =
        resultRepository
            .findResultByPositionOrderAndRaceSeasonYearAndRaceRound(FIRST_POSITION, SEASON_YEAR,
                                                                    ROUND);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(20779L);
  }
}
