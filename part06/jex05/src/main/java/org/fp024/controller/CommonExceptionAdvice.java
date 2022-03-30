package org.fp024.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CommonExceptionAdvice {
	@ExceptionHandler(Exception.class)
	public String except(Exception ex, Model model) {
		LOGGER.error("Exception ..... {}", ex.getMessage());
		model.addAttribute("exception", ex);
		LOGGER.error(model.toString());
		return "error_page";
	}

	/**
	 * 6.6.2 404 에러페이지 설정
	 * 
	 * 기본적으로 DispatcherServlet이 요청에 대한 핸들러를 찾을 수없는 경우 404 응답이 전송됩니다. (예외를 발생시키지 않음)
	 * 그러나 속성 "throwExceptionIfNoHandlerFound"가 true로 설정되면이 예외가 발생하고
	 * 구성된 HandlerExceptionResolver로 처리 할 수 있습니다. 
	 * 
	 * 없는 페이지 테스트
	 * ==> http://localhost:8080/jex01/aaa
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handle404(NoHandlerFoundException ex) {
		return "custom404";
	}

}