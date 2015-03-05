package org.formulaone.rest.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
public class WebAppConfig implements WebApplicationInitializer {

  private static final String CHARACTER_ENCODING_FILTER_ENCODING = "UTF-8";
  private static final String CHARACTER_ENCODING_FILTER_NAME = "characterEncoding";
  private static final String CHARACTER_ENCODING_FILTER_URL_PATTERN = "/*";

  private static final String CORS_FILTER_NAME = "corsFilter";
  private static final String CORS_FILTER_URL_PATTERN = "/*";

  private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
  private static final String DISPATCHER_SERVLET_MAPPING = "/";

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    XmlWebApplicationContext rootContext = new XmlWebApplicationContext();
    rootContext.setConfigLocations("classpath:/applicationContext.xml");

    configureDispatcherServlet(servletContext, rootContext);
    configureCORSFilter(servletContext);
    EnumSet<DispatcherType>
        dispatcherTypes =
        EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
    configureCharacterEncodingFilter(servletContext, dispatcherTypes);
    servletContext.addListener(new ContextLoaderListener(rootContext));
  }

  private void configureDispatcherServlet(ServletContext servletContext,
                                          WebApplicationContext rootContext) {
    DispatcherServlet dispatcherServlet = new DispatcherServlet(rootContext);
    // if setThrowExceptionIfNoHandlerFound set to true,
    // you can't use configureDefaultServletHandling
    // as it will consume 4xx errors before it gets to the DispatcherServlet
    dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);

    ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
        DISPATCHER_SERVLET_NAME,
        dispatcherServlet
    );

    dispatcher.setAsyncSupported(true);
    dispatcher.setLoadOnStartup(1);
    dispatcher.addMapping(DISPATCHER_SERVLET_MAPPING);
  }

  private void configureCharacterEncodingFilter(ServletContext servletContext,
                                                EnumSet<DispatcherType> dispatcherTypes) {
    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    characterEncodingFilter.setEncoding(CHARACTER_ENCODING_FILTER_ENCODING);
    characterEncodingFilter.setForceEncoding(true);
    FilterRegistration.Dynamic
        characterEncoding =
        servletContext.addFilter(CHARACTER_ENCODING_FILTER_NAME, characterEncodingFilter);
    characterEncoding
        .addMappingForUrlPatterns(dispatcherTypes, true, CHARACTER_ENCODING_FILTER_URL_PATTERN);
  }

  private void configureCORSFilter(ServletContext servletContext) {
    FilterRegistration.Dynamic corsFilter =
        servletContext.addFilter(CORS_FILTER_NAME, SimpleCORSFilter.class);
    corsFilter.addMappingForUrlPatterns(null, false, CORS_FILTER_URL_PATTERN);
  }
}
