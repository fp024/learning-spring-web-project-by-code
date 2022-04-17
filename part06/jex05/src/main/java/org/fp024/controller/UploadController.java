package org.fp024.controller;

import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.fp024.config.ProjectDataUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
public class UploadController {

  private static final String uploadFolder = ProjectDataUtils.getProperty("multipart.uploadFolder");

  @GetMapping("/uploadForm")
  public void uploadForm() {
    LOGGER.info("Upload Folder: {}", uploadFolder);
    LOGGER.info("upload form");
  }

  @PostMapping("/uploadFormAction")
  public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
    for (MultipartFile multipartFile : uploadFile) {
      LOGGER.info("------------------------------------");
      LOGGER.info("Upload File Name: {}", multipartFile.getOriginalFilename());
      LOGGER.info("Upload File Size: {}", multipartFile.getSize());

      File saveFile = new File(uploadFolder, "tmp_" + multipartFile.getOriginalFilename());

      try {
        multipartFile.transferTo(saveFile);
        // transferTo()로 처음 생성한 파일은 (메모리 -> 파일저장) 메서드가 끝날때 자동 정리되는 것 같다.
        //
        // StandardServletMultipartResolver 클래스의 cleanupMultipart 메서드 참조바람!!!
        //
        // 파일이 업로드 된 이후 뭔가 다른 처리를 해줘야하는 것을 권고하는 것 같은데..
        // 이 프로젝트는 테스트 동작 확인용이여서, 최초 생성할 때는 tmp_파일명으로 생성후 이후 tmp_를 제거하는 식으로
        // 이름만 바꾸어줬다.
        //
        // web.xml의 <multipart-config> 이하 <location> 설정은...
        // <file-size-threshold> 를 초과했을 때, 임시로 사용할 디스크 경로라고 하는데,
        // 동시에 2MB씩 10개 이상 파일이 업로드 시도 되었을 때에 한해서, 해당 디렉토리에 임시 파일이 생성되는 것을
        // 볼 수 있을 것 같긴하다.
        //
        saveFile.renameTo(new File(uploadFolder, multipartFile.getOriginalFilename()));
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
  }
}
