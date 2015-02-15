package org.formulaone.rest.controller;

import org.springframework.http.MediaType;

import java.nio.charset.Charset;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
final class WebTestConstants {

  static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
                                                               MediaType.APPLICATION_JSON
                                                                   .getSubtype(),
                                                               Charset.forName("utf8")
  );

  static final String ERROR_CODE_CIRCUIT_ENTRY_NOT_FOUND = "NOT_FOUND";
  static final String ERROR_CODE_VALIDATION_FAILED = "BAD_REQUEST";

  /**
   * Prevents instantiation.
   */
  private WebTestConstants() {
  }
}
