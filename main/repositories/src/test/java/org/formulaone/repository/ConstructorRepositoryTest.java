package org.formulaone.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

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
public class ConstructorRepositoryTest {

  private static final Long ID = 1L;
  private static final String REFERENCE_NAME = "mclaren";
  private static final String NAME = "McLaren";

  private static final int totalEntries = 206;

  private static final Long NON_EXISTING_ID = -1L;
  private static final String WRONG_REFERENCE_NAME = "unknown";

  private static final String sortById = "id";
  private static final Sort.Direction directionDesc = Sort.Direction.DESC;

  private static final int pageNumber = 0;
  private static final int pageSize = 30;
  private static final Pageable pageable = new PageRequest(pageNumber, pageSize);

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

    assertThat(constructorEntries).hasSize(totalEntries);
  }

  @Test
  @DatabaseSetup("classpath:constructor-data.xml")
  public void testReturnListSorted() {

    Sort sort = new Sort(directionDesc, sortById);

    List<Constructor> sortedConstructorEntries =
        (List<Constructor>) constructorRepository.findAll(sort);

    assertThat(sortedConstructorEntries).hasSize(totalEntries);
    assertThat(sortedConstructorEntries.get(sortedConstructorEntries.size() - 1).getId())
        .isEqualTo(ID);
  }

  @Test
  @DatabaseSetup("classpath:constructor-data.xml")
  public void testReturnsConstructorPage() {
    Page<Constructor> constructorPage = constructorRepository.findAll(pageable);

    PageAssertion.assertThat(constructorPage)
        .hasTotalPages((int) Math.ceil((double) totalEntries / pageSize))
        .hasTotalElements(totalEntries)
        .hasPageSize(pageSize)
        .hasPageNumber(pageNumber)
        .hasContentSize(pageSize);
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
}
