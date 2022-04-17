package org.fp024.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@PropertySource("classpath:project-data.properties")
public class UploadController {
  @GetMapping("/uploadForm")
  public void uploadForm() {
    LOGGER.info("Upload Folder: {}", uploadFolder);
    LOGGER.info("upload form");
  }

  @Value("${uploadFolder}")
  private String uploadFolder;

  @PostMapping("/uploadFormAction")
  public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
    for (MultipartFile multipartFile : uploadFile) {
      LOGGER.info("------------------------------------");
      LOGGER.info("Upload File Name: {}", multipartFile.getOriginalFilename());
      LOGGER.info("Upload File Size: {}", multipartFile.getSize());
    }
  }
}
