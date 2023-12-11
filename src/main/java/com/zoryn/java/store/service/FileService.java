package com.zoryn.java.store.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

  public File convertMultipartFileIntoFile(MultipartFile multipartFile) {
    File spreadsheetFile = new File("src/main/resources/targetFile.tmp");
    try {
      try (OutputStream os = new FileOutputStream(spreadsheetFile)) {
        os.write(multipartFile.getBytes());
      }
    } catch (IOException exception) {
      throw new IllegalArgumentException("Exception happened during file conversion", exception);
    }
    return spreadsheetFile;
  }
}
