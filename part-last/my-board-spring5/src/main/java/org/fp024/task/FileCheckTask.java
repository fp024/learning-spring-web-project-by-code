package org.fp024.task;

import static org.fp024.util.CommonUtil.getFolderYesterday;
import static org.fp024.util.CommonUtil.getFolderYesterdayUnixPath;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.BoardAttachVO;
import org.fp024.domain.FileType;
import org.fp024.repository.jpa.BoardAttachRepository;
import org.fp024.util.ProjectDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** 업로드 파일 정리 테스크 */
@Slf4j
@Component
public class FileCheckTask {
  private final String uploadFolder;

  private final BoardAttachRepository attachRepository;

  @Autowired
  public FileCheckTask(BoardAttachRepository attachRepository) {
    this(attachRepository, ProjectDataUtil.getProperty("multipart.uploadFolder"));
  }

  public FileCheckTask(BoardAttachRepository attachRepository, String uploadFolder) {
    this.attachRepository = attachRepository;
    this.uploadFolder = uploadFolder;
  }

  @Scheduled(cron = "0 0 2 * * *")
  public void checkFiles() {
    LOGGER.warn("File Check Task run..........");
    LOGGER.warn(
        "현재 시간: {}",
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    // DB의 전일 첨부파일 등록 목록
    List<BoardAttachVO> fileList = getOldFiles();

    // DB 파일과 디렉토리 파일의 확인 준비
    List<Path> fileListPaths =
        new java.util.ArrayList<>(
            fileList.stream()
                .map(
                    vo ->
                        Paths.get(
                            uploadFolder,
                            vo.getUploadPath(),
                            vo.getUuid() + "_" + vo.getFileName()))
                .toList());

    // 썸네일 파일을 가지는 이미지 파일의 경우..
    fileList.stream()
        .filter(vo -> vo.getFileType() == FileType.IMAGE)
        .map(
            vo ->
                Paths.get(
                    uploadFolder, vo.getUploadPath(), "s_" + vo.getUuid() + "_" + vo.getFileName()))
        .forEach(fileListPaths::add);

    LOGGER.warn("==================================");

    // 어제 디렉토리
    File targetDir = Paths.get(uploadFolder, getFolderYesterday()).toFile();

    // DB 목록에 없는 파일이 있으면 삭제 파일로 간주
    File[] removeFiles = targetDir.listFiles(file -> !fileListPaths.contains(file.toPath()));

    if (removeFiles == null) {
      return;
    }

    LOGGER.warn("----------------------------------");
    for (File file : removeFiles) {
      if (file.delete()) {
        LOGGER.warn("삭제 성공 파일: {}", file.getAbsolutePath());
      } else {
        LOGGER.error("삭제 실패 파일: {}", file.getAbsolutePath());
      }
    }
  }

  /**
   * 어제 추가된 첨부파일 목록 조회
   *
   * <p>ex05프로젝트에서는 Oracle 함수로 날짜 계산해서 어제 디렉토리 경로를 알아냈는데, 여기서는 웹 서버에서 확인해서 전달하자!
   *
   * @return 어제 추가된 첨부파일 목록
   */
  List<BoardAttachVO> getOldFiles() {
    return attachRepository.findByUploadPath(getFolderYesterdayUnixPath());

    //    return attachRepository.findAll(
    //        (root, query, cb) ->
    //            cb.equal(root.get(BoardAttachVO_.uploadPath), getFolderYesterdayUnixPath()));
  }
}
