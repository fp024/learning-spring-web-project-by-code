package org.fp024.task;

import static org.fp024.util.CommonUtil.getFolderYesterday;
import static org.fp024.util.CommonUtil.unixPathToCurrentSystemPath;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.fp024.domain.BoardAttachVO;
import org.fp024.domain.FileType;
import org.fp024.mapper.BoardAttachMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@PropertySource("classpath:project-data.properties")
public class FileCheckTask {
  private final String uploadFolder;

  private final BoardAttachMapper attachMapper;

  @Autowired
  public FileCheckTask(
      @Value("${multipart.uploadFolder}") String uploadFolder, BoardAttachMapper attachMapper) {
    this.uploadFolder = uploadFolder;
    this.attachMapper = attachMapper;
  }

  @Scheduled(cron = "0 0 2 * * *")
  public void checkFiles() {
    LOGGER.warn("File Check Task run..........");
    LOGGER.warn(
        "현재 시간: {}",
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    // DB의 전일 첨부파일 등록 목록
    List<BoardAttachVO> fileList = attachMapper.getOldFiles();

    // DB 파일과 디렉토리 파일의 확인 준비
    List<Path> fileListPaths =
        new java.util.ArrayList<>(
            fileList.stream()
                .map(
                    vo ->
                        Paths.get(
                            uploadFolder,
                            unixPathToCurrentSystemPath(vo.getUploadPath()),
                            vo.getUuid() + "_" + vo.getFileName()))
                .toList());

    // 썸네일 파일을 가지는 이미지 파일의 경우..
    fileList.stream()
        .filter(vo -> vo.getFileType() == FileType.IMAGE)
        .map(
            vo ->
                Paths.get(
                    uploadFolder,
                    unixPathToCurrentSystemPath(vo.getUploadPath()),
                    "s_" + vo.getUuid() + "_" + vo.getFileName()))
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
      LOGGER.warn(file.getAbsolutePath());
      if (file.delete()) {
        LOGGER.warn("삭제 성공 파일: {}", file.getAbsolutePath());
      } else {
        LOGGER.error("삭제 실패 파일: {}", file.getAbsolutePath());
      }
    }
  }
}