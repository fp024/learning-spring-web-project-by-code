package org.fp024.controller;

import java.util.List;

import org.fp024.domain.SampleDTO;
import org.fp024.domain.SampleDTOList;
import org.fp024.domain.TodoDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
	/*
	 * // 도메인 Date 필드에 @DateTimeFormat을 사용하면 @InitBinder 설정은 필요없음.
	 * 
	 * @InitBinder public void initBinder(WebDataBinder binder) { SimpleDateFormat
	 * dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 * binder.registerCustomEditor(java.util.Date.class, new
	 * CustomDateEditor(dateFormat, false)); }
	 */

	/**
	 * http://localhost:8080/ex01/sample/ex03?title=InitBinder%EC%97%B0%EC%8A%B5&dueDate=2021-04-24
	 * ==> TodoDTO(title=InitBinder연습, dueDate=Sat Apr 24 00:00:00 KST 2021)
	 */
	@GetMapping
	public String ex03(TodoDTO todo) {
		LOGGER.info("todo: {}", todo);
		return "ex03";
	}

	/**
	 * Spring MVC의 컨트롤러는 기본적으로 Java Beans 규칙에 맞는 객체는 다시 화면으로 객체를 전달함.
	 * 
	 * http://localhost:8080/ex01/sample/ex04?page=1&name=aaa&age=10 ==> 기본형으로 전달된
	 * page 정보는 출력되지 않지만, SampleDTO에 대한 정보가 JSP에 출력됨.
	 * 
	 * 그러나 기본형 타입 파라미터라도 @ModelAttribute 를 붙여사용하면, 무조건 Model에 담아서 전달하므로, 파라미터로 전달된
	 * 데이터를 다시 화면에서 사용해야할 경우 유용하게 사용됨.
	 * 
	 */
	@GetMapping("/ex04")
	public String ex04(SampleDTO dto, @ModelAttribute("page") int page) {
		LOGGER.info("dto: ", dto);
		LOGGER.info("page: {}", page);

		return "sample/ex04";
	}

	/**
	 * 6.4.2 RedirectAttributes
	 * 
	 * * 일회성으로 데이터를 전달하는 용도로 사용.
	 * 
	 */

	/**
	 * 6.5.1 void 타입
	 * 
	 * InternalResourceViewResolver에 정의된대로 /WEB-INF/views/sample/ex05.jsp 를 찾아감.
	 */
	@GetMapping("/ex05")
	public void ex05() {
		LOGGER.info("/ex05 ..........");
	}

	/**
	 * 6.5.3 객체 타입
	 * 
	 * http://localhost:8080/ex01/sample/ex06
	 * 
	 * ==>
	 * 
	 * { "name": "홍길동", "age": 10 }
	 */
	@GetMapping("/ex06")
	@ResponseBody
	public SampleDTO ex06() {
		LOGGER.info("ex06...........");
		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("홍길동");

		return dto;
	}

	/**
	 * 6.5.4 ResponseEntity 타입
	 */
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07() {
		LOGGER.info("/ex07..............");

		// {"name": "홍길동"}
		String msg = "{\"name\":\"홍길동\"}";

		HttpHeaders header = new HttpHeaders();

		// MediaTyp.APPLICATION_JSON_UTF8 (application/json;charset=UTF-8) 은 Deprecated 됨
		//
		// Chrome 등의 주요 브라우저가 사양을 준수하고 charset=UTF-8 매개변수 없이 UTF-8 특수문자를
		// 올바르게 해석하므로 5.2 버전 부터 APPLICATION_JSON 를 선호한다고 함.
		//
		// Firefox, Chrome기반 edge의 응답해더를 확인했을 대.. Content-Type 에 application/json;charset=UTF-8 로 출력됨.
		
		header.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<>(msg, header, HttpStatus.OK);
	}

	
	/**
	 * 6.5.5 파일 업로드 처리
	 */
	@GetMapping("/exUpload")
	public void exUpload() {
		LOGGER.info("/exUpload....................");
	}
	
	@PostMapping("/exUploadPost")
	public void exUploadPost(List<MultipartFile> files) {
		
		files.forEach(file -> {
			LOGGER.info("----------------------");
			LOGGER.info("name: {}", file.getOriginalFilename());
			LOGGER.info("size: {}", file.getSize());
		});
		
	}
	
	/**
	 * 에러 페이지 접근 테스트
	 * ==> http://localhost:8080/ex01/sample/ex04?name=aaa&age=a
	 */
	
	
}
