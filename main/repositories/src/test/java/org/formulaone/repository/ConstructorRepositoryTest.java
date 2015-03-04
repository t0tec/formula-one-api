package org.formulaone.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

import org.formulaone.core.model.Constructor;
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
@DatabaseTearDown("classpath:constructor-no-data.xml")
public class ConstructorRepositoryTest {

  private static final Long ID = 1L;
  private static final String REFERENCE_NAME = "mclaren";
  private static final String NAME = "McLaren";

  private static final int SEASON_YEAR = 2009;
  private static final int ROUND = 1;

  private static final int TOTAL_ENTRIES = 206;
  private static final int SEASON_TOTAL_CONSTRUCTORS = 10;
  private static final int ROUND_TOTAL_CONSTRUCTORS = 10;


  private static final Long NON_EXISTING_ID = -1L;
  private static final String WRONG_REFERENCE_NAME = "unknown";

  private static final String SORT_BY_ID = "id";
  private static final Sort.Direction DIRECTION_DESC = Sort.Direction.DESC;

  private static final int PAGE_NUMBER = 0;
  private static final int PAGE_SIZE = 30;
  private static final Pageable PAGEABLE = new PageRequest(PAGE_NUMBER, PAGE_SIZE);

  @Autowired
  private ConstructorRepository constructorRepository;

  @Test
  @DatabaseSetup("classpath:constructor-data.xml")
  public void testFindConstructorById() {
    Constructor constructor = constructorRepository.findOne(ID);

    assertThat(constructor).isNotNull();
    assertThat(constructor.getId()).isEqualTo(ID);
    assertThat(constructor.getReferenceName()).isEqualTo(REFERENCE_NAME);
    assertThat(constructor.getName()).isEqualTo(NAME);
  }

  @Test
  @DatabaseSetup("classpath:constructor-data.xml")
  public void testReturnNullConstructorWithCorrectId() {
    Constructor constructor = constructorRepository.findOne(NON_EXISTING_ID);

    assertThat(constructor).isNull();
  }

  @Test
  @DatabaseSetup("classpath:constructor-no-data.xml")
  public void testReturnEmptyList() {
    List<Constructor> constructorEntries = (List<Constructor>) constructorRepository.findAll();

    assertThat(constructorEntries).isEmpty();
  }

  @Test
  @DatabaseSetup("classpath:constructor-data.xml")
  public void testReturnList() {
    List<Constructor> constructorEntries = (List<Constructor>) constructorRepository.findAll();

    assertThat(constructorEntries).hasSize(TOTAL_ENTRIES);
  }

  @Test
  @DatabaseSetup("classpath:constructor-data.xml")
  public void testReturnListSorted() {
    Sort sort = new Sort(DIRECTION_DESC, SORT_BY_ID);

    List<Constructor> sortedConstructorEntries =
        (List<Constructor>) constructorRepository.findAll(sort);

    assertThat(sortedConstructorEntries).hasSize(TOTAL_ENTRIES);
    assertThat(sortedConstructorEntries.get(sortedConstructorEntries.size() - 1).getId())
        .isEqualTo(ID);
  }

  @Test
  @DatabaseSetup("classpath:constructor-data.xml")
  public void testReturnsConstructorPage() {
    Page<Constructor> constructorPage = constructorRepository.findAll(PAGEABLE);

    PageAssertion.assertThat(constructorPage)
        .hasTotalPages((int) Math.ceil((double) TOTAL_ENTRIES / PAGE_SIZE))
        .hasTotalElements(TOTAL_ENTRIES)
        .hasPageSize(PAGE_SIZE)
        .hasPageNumber(PAGE_NUMBER)
        .hasContentSize(PAGE_SIZE);
  }

  @Test
  @DatabaseSetup("classpath:constructor-data.xml")
  public void testFindConstructorByReferenceName() {
    Constructor constructor = constructorRepository.findByReferenceName(REFERENCE_NAME);

    assertThat(constructor).isNotNull();
    assertThat(constructor.getId()).isEqualTo(ID);
    assertThat(constructor.getReferenceName()).isEqualTo(REFERENCE_NAME);
    assertThat(constructor.getName()).isEqualTo(NAME);
  }

  @Test
  @DatabaseSetup("classpath:constructor-data.xml")
  public void testReturnNullConstructorWithWrongReferenceName() {
    Constructor constructor = constructorRepository.findByReferenceName(WRONG_REFERENCE_NAME);

    assertThat(constructor).isNull();
  }

  @Test
  @Transactional
  @DatabaseSetup({"classpath:status-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml", "classpath:result-data.xml"})
  public void testReturnConstructorListBySeason() {
    List<Constructor>
        constructorEntries =
        constructorRepository.findConstructorsBySeason(SEASON_YEAR);

    assertThat(constructorEntries).hasSize(SEASON_TOTAL_CONSTRUCTORS);
  }

  @Test
  @Transactional
  @DatabaseSetup({"classpath:status-data.xml",
                  "classpath:driver-data.xml", "classpath:constructor-data.xml",
                  "classpath:season-data.xml", "classpath:circuit-data.xml",
                  "classpath:race-data.xml", "classpath:result-data.xml"})
  public void testReturnConstructorListBySeasonAndRound() {
    List<Constructor> constructorEntries = constructorRepository.findConstructorsBySeasonAndRound(
        SEASON_YEAR, ROUND);

    assertThat(constructorEntries).hasSize(ROUND_TOTAL_CONSTRUCTORS);
  }
}
