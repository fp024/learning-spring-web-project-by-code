package org.fp024.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletRegistration;

/** web.xml 대체 */
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[] {RootConfig.class, SecurityConfig.class};
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] {ServletConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/"};
  }

  // Spring Security 설정보다 맨 먼저 실행되게 SecurityInitializer 클레스에 선언해서 여기는 주석으로 둔다.
  /*
  @Override
  protected Filter[] getServletFilters() {
    CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
    encodingFilter.setEncoding(StandardCharsets.UTF_8.name());
    encodingFilter.setForceEncoding(true);
    return new Filter[] {encodingFilter};
  }
  */

  /** 처리할 수 있는 핸들러를 찾을 수 없을 때, 404를 예외로 처리하는 사용자 정의 설정 서블릿 3.0 이상에서 설정 가능. */
  @Override
  protected void customizeRegistration(ServletRegistration.Dynamic registration) {
    registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
  }
}
