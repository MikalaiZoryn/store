package com.zoryn.itemservice.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.poi.ss.usermodel.Workbook;
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

  public void saveSpreadsheetToFile(Workbook spreadsheet) {
    try {
      File currDir = new File(".");
      String path = currDir.getAbsolutePath();
      String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

      FileOutputStream outputStream = new FileOutputStream(fileLocation);
      spreadsheet.write(outputStream);
      spreadsheet.close();
    } catch (Exception e) {
      System.out.println("exception: " + e.getMessage());
    }
  }
}
