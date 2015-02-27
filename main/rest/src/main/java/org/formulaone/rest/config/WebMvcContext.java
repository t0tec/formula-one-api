package org.formulaone.rest.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.datatype.jdk7.Jdk7Module;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author t0tec (t0tec.olmec@gmail.com)
 * @version $Id$
 * @since 1.0
 */
@Configuration
@EnableWebMvc
class WebMvcContext extends WebMvcConfigurerAdapter {

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.defaultContentType(MediaType.APPLICATION_JSON);
    configurer.favorPathExtension(true);
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.add(jackson2HttpMessageConverter());
    converters.add(jackson2XmlHttpMessageConverter());
  }

  private MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
    ObjectMapper objectMapper = new ObjectMapper();

    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
    objectMapper.registerModule(new Jdk7Module());

    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setObjectMapper(objectMapper);

    return converter;
  }

  private MappingJackson2XmlHttpMessageConverter jackson2XmlHttpMessageConverter() {
    JacksonXmlModule module = new JacksonXmlModule();

    module.setDefaultUseWrapper(false);
    XmlMapper xmlMapper = new XmlMapper(module);
    xmlMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//    xmlMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
    xmlMapper.registerModule(module);

    MappingJackson2XmlHttpMessageConverter converter = new MappingJackson2XmlHttpMessageConverter();
    converter.setObjectMapper(xmlMapper);

    return converter;
  }

}
