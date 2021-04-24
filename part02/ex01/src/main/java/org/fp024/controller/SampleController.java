package org.fp024.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.fp024.domain.SampleDTO;
import org.fp024.domain.SampleDTOList;
import org.fp024.domain.TodoDTO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/sample/*")
@Slf4j
public class SampleController {
	@RequestMapping("")
	public void basic() {
		LOGGER.info("basic...................");
	}

	@RequestMapping(value = "/basic", method = { RequestMethod.GET, RequestMethod.POST })
	public void basicGet() {
		LOGGER.info("basic get...................");
	}

	@GetMapping("/basicOnlyGet")
	public void basicGet2() {
		LOGGER.info("basic get only get..................");
	}

	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		LOGGER.info(dto.toString());
		return "ex01";
	}

	/**
	 * 객체형이 아닌 기본 형을 required = false 로 두고 관련 파라미터를 전달하지 않으면...
	 * 
	 * Optional int parameter 'age' is present but cannot be translated into a null
	 * value due to being declared as a primitive type. Consider declaring it as
	 * object wrapper for the corresponding primitive type.
	 * 
	 * 기본타입 int를 null로 변환할 수 없어 오류가 나므로, 기본 타입을 객체 레퍼로 선언하는 것으로 고려하란 메시지의 예외가 발생함.
	 */
	@GetMapping("/ex02")
	public String ex02(@RequestParam(value = "name") String name,
			@RequestParam(value = "age", required = false) int age) {
		LOGGER.info("name: {}", name);
		LOGGER.info("age: {}", age);

		return "ex02";
	}

	/**
	 * 6.3.2 리스트 배열 처리 * 요소를 쉼표로 나눠서 보내도 되고,
	 * http://localhost:8080/ex01/sample/ex02List?ids=111,222,333
	 * 
	 * * 이름을 중복해서 여러번 보내도 된다.
	 * http://localhost:8080/ex01/sample/ex02List?ids=111&ids=222&ids=333
	 */
	@GetMapping("/ex02List")
	public String ex02List(@RequestParam("ids") List<String> ids) {
		LOGGER.info("ids: {}, ids size: {}", ids, ids.size());
		return "ex02List";
	}

	/**
	 * http://localhost:8080/ex01/sample/ex02Array?ids=111,222,333
	 * http://localhost:8080/ex01/sample/ex02Array?ids=111&ids=222&ids=333
	 */
	@GetMapping("/ex02Array")
	public String ex02Array(@RequestParam("ids") String[] ids) {
		LOGGER.info("array ids: {}, array ids length: {}", ids, ids.length);
		return "ex02List";
	}

	/**
	 * 6.3.3 객체 리스트
	 * 
	 * http://localhost:8080/ex01/sample/ex02Bean?list[0].name=aaa&list[0].age=1
	 * 
	 * * 톰켓 9.0에서 아래 오류 발생 java.lang.IllegalArgumentException: Invalid character
	 * found in the request target
	 * [/ex01/sample/ex02Bean?list[0].name=aaa&list[0].age=1]. The valid characters
	 * are defined in RFC 7230 and RFC 3986
	 * 
	 * 톰켓에서 해당 문자를 허용하지 않아서 그렇다는데.. catalina.properties 파일에 아래 내용 추가하면 허용해줄 수 있으나...
	 * tomcat.util.http.parser.HttpParser.requestTargetAllow=[]
	 * 
	 * URI 인코드 해서 보내라함...
	 * 
	 * encodeURI("http://localhost:8080/ex01/sample/ex02Bean?list[0].name=aaa&list[0].age=1&list[1].name=bbb&list[1].age=2&list[4].name=bbb")
	 * 
	 * ==>
	 * "http://localhost:8080/ex01/sample/ex02Bean?list%5B0%5D.name=…&list%5B1%5D.name=bbb&list%5B1%5D.age=2&list%5B4%5D.name=bbb"
	 * 
	 * * (참고) 3번째 요소를 넘기고 4번째 요소의 이름만 입력했을 때, 3번째는 빈 객체로 들어간다. - list dtos:
	 * SampleDTOList(list=[SampleDTO(name=aaa, age=1), SampleDTO(name=bbb, age=2),
	 * SampleDTO(name=null, age=0), SampleDTO(name=null, age=0), SampleDTO(name=bbb,
	 * age=0)])
	 * 
	 * 참조:
	 * https://stackoverflow.com/questions/41053653/tomcat-8-is-not-able-to-handle-get-request-with-in-query-parameters
	 */
	@GetMapping("/ex02Bean")
	public String ex02Bean(SampleDTOList list) {
		LOGGER.info("list dtos: {}", list);
		return "ex02Bean";
	}

	/**
	 * 6.3.4 @InitBinder
	 */
	/* // 도메인 Date 필드에 @DateTimeFormat을 사용하면 @InitBinder 설정은 필요없음.
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, false));
	}
	*/

	/** 
	 *  http://localhost:8080/ex01/sample/ex03?title=InitBinder%EC%97%B0%EC%8A%B5&dueDate=2021-04-24
	 *  ==> TodoDTO(title=InitBinder연습, dueDate=Sat Apr 24 00:00:00 KST 2021)
	 */
	@GetMapping
	public String ex03(TodoDTO todo) {
		LOGGER.info("todo: {}", todo);
		return "ex03";
	}

}
