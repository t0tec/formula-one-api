package org.formulaone.repository.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@Configuration
@ComponentScan("org.formulaone.repository")
@Import({PersistenceJpaConfig.class})
public class ExampleApplicationContext {

  private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";

  /**
   * These static classes are required because it makes it possible to use different properties
   * files for every Spring profile.
   *
   * See: <a href="http://stackoverflow.com/a/14167357/313554">This StackOverflow answer</a> for
   * more details.
   */
  @Profile(Profiles.APPLICATION)
  @Configuration
  @PropertySource("classpath:application.properties")
  static class ApplicationProperties {

  }

  @Profile(Profiles.INTEGRATION_TEST)
  @Configuration
  @PropertySource("classpath:integration-test.properties")
  static class IntegrationTestProperties {

  }

  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
    messageSource.setUseCodeAsDefaultMessage(true);
    return messageSource;
  }

  @Bean
  public PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}
