package org.formulaone.service;

import org.formulaone.core.exception.NotFoundException;
import org.formulaone.core.model.Status;
import org.formulaone.repository.StatusRepository;
import org.formulaone.service.dto.StatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@Transactional(readOnly = true)
@Service
public class RepositoryStatusService extends RepositoryGenericService<Status, StatusDto, Integer>
    implements StatusReadOnlyService {

  private StatusRepository statusRepository;

  public RepositoryStatusService() {
    super();
  }

  @Autowired
  public RepositoryStatusService(StatusRepository statusRepository) {
    super(statusRepository);
    this.statusRepository = statusRepository;
  }

  // TODO: Override needed because of query in ReadOnlyRepository not including the count.
  @Override
  public StatusDto findById(Integer id) {
    Status entry = statusRepository.findOne(id);

    if (entry == null) {
      throw new NotFoundException(entityClass, "id", id);
    }

    return mapper.map(entry, dtoClass);
  }

  @Override
  public List<StatusDto> findAllBySeasonYear(int year) {
    List<StatusDto> result = new ArrayList<StatusDto>();

    for (Status status : statusRepository.findAllBySeasonYear(year)) {
      result.add(mapper.map(status, dtoClass));
    }

    return result;
  }

  @Override
  public List<StatusDto> findAllBySeasonYearAndRound(int year, int round) {
    List<StatusDto> result = new ArrayList<StatusDto>();

    for (Status status : statusRepository.findAllBySeasonYearAndRound(year, round)) {
      result.add(mapper.map(status, dtoClass));
    }

    return result;
  }
}
