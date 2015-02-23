package org.formulaone.rest.controller;

import org.springframework.http.MediaType;

import java.nio.charset.Charset;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public final class WebTestConstants {

  public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
                                                               MediaType.APPLICATION_JSON
                                                                   .getSubtype(),
                                                               Charset.forName("utf8")
  );

  public static final String ERROR_CODE_ENTRY_NOT_FOUND = "NOT_FOUND";
  public static final String ERROR_CODE_VALIDATION_FAILED = "BAD_REQUEST";

  /**
   * Prevents instantiation.
   */
  private WebTestConstants() {
  }
}
