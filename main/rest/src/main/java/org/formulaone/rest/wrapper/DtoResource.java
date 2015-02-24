package org.formulaone.rest.wrapper;

import org.springframework.hateoas.ResourceSupport;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class DtoResource<T> extends ResourceSupport {

  protected T content;

  public DtoResource(T content) {
    super();
    this.content = content;
  }

  public T getContent() {
    return content;
  }
}
