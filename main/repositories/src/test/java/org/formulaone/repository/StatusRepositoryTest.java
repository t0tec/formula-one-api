package org.formulaone.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

import org.formulaone.core.model.Status;
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
@DatabaseTearDown("classpath:status-no-data.xml")
public class StatusRepositoryTest {

  private static final Integer ID = 1;
  private static final String STATUS = "Finished";

  private static final int TOTAL_ENTRIES = 54;
  private static final int FINISHED_COUNT = 890;

  private static final Integer NON_EXISTING_ID = -1;

  private static final String SORT_BY_ID = "id";
  private static final Sort.Direction DIRECTION_DESC = Sort.Direction.DESC;

  private static final int PAGE_NUMBER = 0;
  private static final int PAGE_SIZE = 30;
  private static final Pageable PAGEABLE = new PageRequest(PAGE_NUMBER, PAGE_SIZE);

  @Autowired
  private StatusRepository statusRepository;

  @Test
  @DatabaseSetup({"classpath:status-data.xml", "classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:race-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:result-data.xml"})
  public void testFindStatusById() {
    Status status = statusRepository.findOne(ID);

    assertThat(status).isNotNull();
    assertThat(status.getStatus()).isEqualTo(STATUS);
    assertThat(status.getCount()).isEqualTo(FINISHED_COUNT);
  }

  @Test
  @DatabaseSetup({"classpath:status-data.xml", "classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:race-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:result-data.xml"})
  public void testReturnNullStatusWithCorrectId() {
    Status status = statusRepository.findOne(NON_EXISTING_ID);
    assertThat(status).isNull();
  }

  @Test
  @DatabaseSetup({"classpath:status-no-data.xml", "classpath:season-no-data.xml",
                  "classpath:circuit-no-data.xml", "classpath:race-no-data.xml",
                  "classpath:driver-no-data.xml", "classpath:constructor-no-data.xml",
                  "classpath:result-no-data.xml"})
  public void testReturnEmptyList() {
    Iterable<Status> statusEntries = statusRepository.findAll();

    assertThat(statusEntries).isEmpty();
  }

  @Test
  @DatabaseSetup({"classpath:status-data.xml", "classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:race-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:result-data.xml"})
  public void testReturnList() {
    Iterable<Status> statusEntries = statusRepository.findAll();

    assertThat(statusEntries).hasSize(TOTAL_ENTRIES);
  }

  @Test
  @DatabaseSetup({"classpath:status-data.xml", "classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:race-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:result-data.xml"})
  public void testReturnListSorted() {
    Sort sort = new Sort(DIRECTION_DESC, SORT_BY_ID);

    List<Status> statusEntries = (List<Status>) statusRepository.findAll(sort);

    assertThat(statusEntries).hasSize(TOTAL_ENTRIES);
    assertThat(statusEntries.get(statusEntries.size() - 1).getId()).isEqualTo(ID);
    assertThat(statusEntries.get(statusEntries.size() - 1).getStatus()).isEqualTo(STATUS);
    assertThat(statusEntries.get(statusEntries.size() - 1).getCount()).isEqualTo(FINISHED_COUNT);
  }

  @Test
  @DatabaseSetup({"classpath:status-data.xml", "classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:race-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:result-data.xml"})
  public void testReturnsPage() {
    Page<Status> statusPage = statusRepository.findAll(PAGEABLE);

    PageAssertion.assertThat(statusPage)
        .hasTotalPages((int) Math.ceil((double) TOTAL_ENTRIES / PAGE_SIZE))
        .hasTotalElements(TOTAL_ENTRIES)
        .hasPageSize(PAGE_SIZE)
        .hasPageNumber(PAGE_NUMBER)
        .hasContentSize(PAGE_SIZE);
  }

  @Test
  @DatabaseSetup({"classpath:status-data.xml", "classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:race-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:result-data.xml"})
  public void testReturnListBySeasonYear() {
    List<Status> statusEntries = statusRepository.findAllBySeasonYear(2011);
    assertThat(statusEntries).hasSize(31);
  }

  @Test
  @DatabaseSetup({"classpath:status-data.xml", "classpath:season-data.xml",
                  "classpath:circuit-data.xml", "classpath:race-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:result-data.xml"})
  public void testReturnListBySeasonYearAndRound() {
    List<Status> statusEntries = statusRepository.findAllBySeasonYearAndRound(2011, 1);
    assertThat(statusEntries).hasSize(9);
  }
}
