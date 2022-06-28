package org.fp024.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import lombok.extern.slf4j.Slf4j;
import org.fp024.util.ProjectDataUtil;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/** web.xml 대체 */
@Slf4j
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[] {RootConfig.class};
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
    MultipartConfigElement multipartConfig =
        new MultipartConfigElement(
            ProjectDataUtil.getProperty("multipart.uploadTempFolder"),
            ProjectDataUtil.getLongProperty("multipart.maxFileSize"),
            ProjectDataUtil.getLongProperty("multipart.maxRequestSize"),
            ProjectDataUtil.getIntegerProperty("multipart.fileSizeThreshold"));

    LOGGER.info("### multipart config의 location 값: {}", multipartConfig.getLocation());
    LOGGER.info("### multipart config의 maxFileSize 값: {}", multipartConfig.getMaxFileSize());
    LOGGER.info("### multipart config의 maxRequestSize 값: {}", multipartConfig.getMaxRequestSize());
    LOGGER.info(
        "### multipart config의 fileSizeThreshold 값: {}", multipartConfig.getFileSizeThreshold());
    registration.setMultipartConfig(multipartConfig);
  }
}
