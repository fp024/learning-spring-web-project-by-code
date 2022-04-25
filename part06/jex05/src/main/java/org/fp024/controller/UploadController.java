package org.fp024.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.fp024.config.ProjectDataUtils;
import org.fp024.domain.AttachFileDTO;
import org.fp024.util.CommonUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
public class UploadController {

  private static final String UPLOAD_FOLDER =
      ProjectDataUtils.getProperty("multipart.uploadFolder");

  @GetMapping("/uploadForm")
  public void uploadForm() {
    LOGGER.info("Upload Folder: {}", UPLOAD_FOLDER);
    LOGGER.info("upload form");
  }

  @PostMapping("/uploadFormAction")
  public void uploadFormPost(MultipartFile[] uploadFile) {
    // 폴더 만들기
    final File uploadPath = new File(UPLOAD_FOLDER, CommonUtil.getFolder());
    LOGGER.info("upload path: {}", uploadPath);

    if (!uploadPath.exists()) {
      uploadPath.mkdirs();
    }
    // yyyy/MM/dd 폴더 만듬.

    for (MultipartFile multipartFile : uploadFile) {
      LOGGER.info("------------------------------------");
      LOGGER.info("Upload File Name: {}", multipartFile.getOriginalFilename());
      LOGGER.info("Upload File Size: {}", multipartFile.getSize());

      String uploadFileName = multipartFile.getOriginalFilename();

      // IE 는 파일 경로를 가짐.
      uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf(File.separator) + 1);
      LOGGER.info("경로를 제외한 파일명: {}", uploadFileName);

      String uuidFileName = CommonUtil.getUUID() + "_" + uploadFileName;
      LOGGER.info("UUID가 붙은 파일명: {}", uuidFileName);

      File saveTempFile = new File(uploadPath, "tmp_" + uuidFileName);
      File renamedFile = new File(uploadPath, uuidFileName);
      renamedFile.delete();

      try {
        multipartFile.transferTo(saveTempFile);
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
        if (!saveTempFile.renameTo(renamedFile)) {
          throw new IllegalStateException("임시파일 이름 변경 실패");
        }

        if (CommonUtil.checkImageType(renamedFile)) {
          makeThumbnail(renamedFile);
        }
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
  }

  @GetMapping("/uploadAjax")
  public void uploadAjax() {
    LOGGER.info("upload ajax");
  }

  @PostMapping("/uploadAjaxAction")
  @ResponseBody
  public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
    LOGGER.info("update ajax post........");

    List<AttachFileDTO> list = new ArrayList<>();

    // 폴더 만들기
    final String uploadFolderPath = CommonUtil.getFolder();
    final File uploadPath = new File(UPLOAD_FOLDER, uploadFolderPath);
    LOGGER.info("upload path: {}", uploadPath);

    if (!uploadPath.exists()) {
      uploadPath.mkdirs();
    }
    // yyyy/MM/dd 폴더 만듬.

    for (MultipartFile multipartFile : uploadFile) {
      LOGGER.info("------------------------------------");
      LOGGER.info("Upload File Name: {}", multipartFile.getOriginalFilename());
      LOGGER.info("Upload File Size: {}", multipartFile.getSize());

      String originalFilename = multipartFile.getOriginalFilename();

      // IE 는 파일 경로를 가짐.
      final String uploadFileName =
          originalFilename.substring(originalFilename.lastIndexOf(File.separator) + 1);
      LOGGER.info("경로를 제외한 파일명: {}", uploadFileName);

      final String uuid = CommonUtil.getUUID();

      final String uuidFileName = uuid + "_" + uploadFileName;
      LOGGER.info("UUID가 붙은 파일명: {}", uuidFileName);

      File saveTempFile = new File(uploadPath, "tmp_" + uuidFileName);
      File renamedFile = new File(uploadPath, uuidFileName);
      // 테스트를 위해 이미 파일이 있다면 지워주자.
      renamedFile.delete();

      AttachFileDTO attachDTO = new AttachFileDTO();
      attachDTO.setFileName(uploadFileName);
      attachDTO.setUuid(uuid);
      attachDTO.setUploadPath(uploadFolderPath);

      try {
        multipartFile.transferTo(saveTempFile);

        if (!saveTempFile.renameTo(renamedFile)) {
          throw new IllegalStateException("임시파일 이름 변경 실패");
        }

        if (CommonUtil.checkImageType(renamedFile)) {
          attachDTO.setImage(true);
          makeThumbnail(renamedFile);
        }
        attachDTO.setSuccess(true);
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
        return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
      } finally {
        list.add(attachDTO);
      }
    }
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  /**
   * 업로드 파일 조회
   *
   * @param fileName 조회할 파일명
   * @return 파일의 바이트 배열을 담은 응답 객체
   */
  @GetMapping("/display")
  public ResponseEntity<byte[]> getFile(String fileName) {
    LOGGER.info("fileName: {}", fileName);

    File file = new File(UPLOAD_FOLDER + File.separator + fileName);

    LOGGER.info("file: {}", file);

    try {
      HttpHeaders header = new HttpHeaders();
      String contentType =
          Files.probeContentType(file.toPath()); // 파일이 실제로 없어도 그냥 확장자 보고 컨텐트 타입을 판단하는 것 같다.
      LOGGER.info("### Content-Type: {} ###", contentType);
      header.add("Content-Type", contentType);
      return new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * 섬네일 생성
   *
   * @param imageFile 이미지 파일
   */
  private void makeThumbnail(File imageFile) {
    try (FileOutputStream thumbnail =
            new FileOutputStream(new File(imageFile.getParent(), "s_" + imageFile.getName()));
        InputStream inputStream = new FileInputStream(imageFile)) {
      Thumbnailator.createThumbnail(inputStream, thumbnail, 100, 100);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}
