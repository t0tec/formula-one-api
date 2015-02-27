package org.formulaone.service;

import org.formulaone.core.model.Status;
import org.formulaone.repository.StatusRepository;
import org.formulaone.service.dto.StatusDto;
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
public class RepositoryStatusService extends RepositoryGenericService<Status, StatusDto, Long>
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
}
