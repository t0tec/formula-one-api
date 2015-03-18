package org.formulaone.service;

import com.mysema.query.types.Predicate;

import org.formulaone.core.exception.NotFoundException;
import org.formulaone.core.model.QSeason;
import org.formulaone.core.model.Season;
import org.formulaone.repository.SeasonRepository;
import org.formulaone.service.dto.SeasonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@Transactional(readOnly = true)
@Service
public class RepositorySeasonService extends RepositoryGenericService<Season, SeasonDto, Long>
    implements SeasonReadOnlyService {

  private SeasonRepository seasonRepository;

  public RepositorySeasonService() {
    super();
  }

  @Autowired
  public RepositorySeasonService(SeasonRepository seasonRepository) {
    super(seasonRepository);
    this.seasonRepository = seasonRepository;
  }

  @Override
  public SeasonDto findById(Long id) {
    throw new UnsupportedOperationException("There is no property id in season");
  }

  @Override
  public SeasonDto findByYear(int year) {
    Season seasonEntry = seasonRepository.findOne(yearEquals(year));

    if (seasonEntry == null) {
      throw new NotFoundException(Season.class, "year", year);
    }

    return mapper.map(seasonEntry, dtoClass);
  }

  private static Predicate yearEquals(final int year) {
    QSeason season = QSeason.season;
    return season.year.eq(year);
  }
}
