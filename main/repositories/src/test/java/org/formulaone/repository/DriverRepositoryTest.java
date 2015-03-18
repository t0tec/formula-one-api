package org.formulaone.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

import org.formulaone.core.model.Driver;
import org.formulaone.core.model.QDriver;
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
@DatabaseTearDown("classpath:driver-no-data.xml")
public class DriverRepositoryTest {

  private static final Long ID = 1L;
  private static final String REFERENCE_NAME = "hamilton";
  private static final String FORENAME = "Lewis";
  private static final String SURNAME = "Hamilton";

  private static final int SEASON_YEAR = 2009;
  private static final int ROUND = 1;

  private static final int TOTAL_ENTRIES = 828;
  private static final int SEASON_TOTAL_DRIVERS = 25;
  private static final int ROUND_TOTAL_DRIVERS = 20;

  private static final Long NON_EXISTING_ID = -1L;
  private static final String WRONG_REFERENCE_NAME = "unknown";

  private static final String SORT_BY_ID = "id";
  private static final Sort.Direction DIRECTION_DESC = Sort.Direction.DESC;

  private static final int PAGE_NUMBER = 0;
  private static final int PAGE_SIZE = 30;
  private static final Pageable PAGEABLE = new PageRequest(PAGE_NUMBER, PAGE_SIZE);

  @Autowired
  private DriverRepository driverRepository;

  @Test
  @DatabaseSetup("classpath:driver-data.xml")
  public void testFindById() {
    Driver driver = driverRepository.findOne(ID);

    assertThat(driver).isNotNull();
    assertThat(driver.getId()).isEqualTo(ID);
    assertThat(driver.getReferenceName()).isEqualTo(REFERENCE_NAME);
    assertThat(driver.getForename()).isEqualTo(FORENAME);
    assertThat(driver.getSurname()).isEqualTo(SURNAME);
  }

  @Test
  @DatabaseSetup("classpath:driver-data.xml")
  public void testReturnNullWithCorrectId() {
    Driver driver = driverRepository.findOne(NON_EXISTING_ID);

    assertThat(driver).isNull();
  }

  @Test
  @DatabaseSetup("classpath:driver-no-data.xml")
  public void testReturnEmptyList() {
    List<Driver> driverEntries = (List<Driver>) driverRepository.findAll();

    assertThat(driverEntries).isEmpty();
  }

  @Test
  @DatabaseSetup("classpath:driver-data.xml")
  public void testReturnList() {
    List<Driver> driverEntries = (List<Driver>) driverRepository.findAll();

    assertThat(driverEntries).hasSize(TOTAL_ENTRIES);
  }

  @Test
  @DatabaseSetup("classpath:driver-data.xml")
  public void testReturnListSorted() {
    Sort sort = new Sort(DIRECTION_DESC, SORT_BY_ID);

    List<Driver> sortedDriverEntries = (List<Driver>) driverRepository.findAll(sort);

    assertThat(sortedDriverEntries).hasSize(TOTAL_ENTRIES);
    assertThat(sortedDriverEntries.get(sortedDriverEntries.size() - 1).getId()).isEqualTo(ID);
  }

  @Test
  @DatabaseSetup("classpath:driver-data.xml")
  public void testReturnPage() {
    Page<Driver> driverPage = driverRepository.findAll(PAGEABLE);

    PageAssertion.assertThat(driverPage)
        .hasTotalPages((int) Math.ceil((double) TOTAL_ENTRIES / PAGE_SIZE))
        .hasTotalElements(TOTAL_ENTRIES)
        .hasPageSize(PAGE_SIZE)
        .hasPageNumber(PAGE_NUMBER)
        .hasContentSize(PAGE_SIZE);
  }

  @Test
  @DatabaseSetup("classpath:driver-data.xml")
  public void testFindByReferenceName() {
    Driver driver = driverRepository.findOne(QDriver.driver.referenceName.eq(REFERENCE_NAME));

    assertThat(driver).isNotNull();
    assertThat(driver.getId()).isEqualTo(ID);
    assertThat(driver.getReferenceName()).isEqualTo(REFERENCE_NAME);
    assertThat(driver.getForename()).isEqualTo(FORENAME);
    assertThat(driver.getSurname()).isEqualTo(SURNAME);
  }

  @Test
  @DatabaseSetup("classpath:driver-data.xml")
  public void testReturnNullWithWrongReferenceName() {
    Driver driver = driverRepository.findOne(QDriver.driver.referenceName.eq(WRONG_REFERENCE_NAME));

    assertThat(driver).isNull();
  }

  @Test
  @Transactional
  @DatabaseSetup({"classpath:status-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml", "classpath:result-data.xml"})
  public void testReturnDriverListBySeason() {
    List<Driver> driverEntries = driverRepository.findDriversBySeason(SEASON_YEAR);

    assertThat(driverEntries).hasSize(SEASON_TOTAL_DRIVERS);
  }

  @Test
  @Transactional
  @DatabaseSetup({"classpath:status-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml", "classpath:result-data.xml"})
  public void testReturnDriverListBySeasonAndRound() {
    List<Driver> driverEntries = driverRepository.findDriversBySeasonAndRound(SEASON_YEAR, ROUND);

    assertThat(driverEntries).hasSize(ROUND_TOTAL_DRIVERS);
  }
}
