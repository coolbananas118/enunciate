package org.codehaus.enunciate.modules.rest;

import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.context.ServletContextAware;
import org.codehaus.enunciate.rest.annotations.VerbType;

import javax.servlet.ServletContext;

/**
 * Single-instance factory for an instance of org.springframework.web.multipart.commons.CommonsMultipartResolver if Commons Fileupload isn't on the classpath,
 * then no resolver will be returned by this factory.
 *
 * @author Ryan Heaton
 */
public class CommonsMultipartResolverFactory implements MultipartResolverFactory, ServletContextAware {

  private final MultipartResolver resolver;

  public CommonsMultipartResolverFactory() {
    MultipartResolver resolver;
    try {
      resolver = (MultipartResolver) Class.forName("org.springframework.web.multipart.commons.CommonsMultipartResolver").newInstance();
    }
    catch (Throwable e) {
      resolver = null;
    }

    this.resolver = resolver;
  }

  public void setServletContext(ServletContext servletContext) {
    if (resolver != null) {
      ((ServletContextAware) resolver).setServletContext(servletContext);
    }
  }

  public MultipartResolver getMultipartResolver(String nounContext, String noun, VerbType verb) {
    return resolver;
  }
}
