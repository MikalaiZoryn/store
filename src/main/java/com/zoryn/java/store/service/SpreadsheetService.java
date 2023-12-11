package com.zoryn.java.store.service;

import com.google.common.collect.ImmutableList;
import com.zoryn.java.store.model.Item;
import java.io.File;
import java.math.BigDecimal;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpreadsheetService {

  @Autowired
  SpreadsheetService() {
  }

  public ImmutableList<Item> parseSpreadsheet(File file) {
    try (Workbook workbook = new XSSFWorkbook(file)) {
      Sheet sheet = workbook.getSheetAt(0);

      ImmutableList.Builder<Item> itemBuilder = ImmutableList.builder();
      for (int i = 1; i <= sheet.getLastRowNum(); i++) {
        Row row = sheet.getRow(i);
        Cell idCell = row.getCell(0);
        if (idCell == null || idCell.getCellType() == CellType.BLANK) {
          itemBuilder.add(
              new Item(row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue(),
                  row.getCell(3).getStringCellValue(), row.getCell(4).getNumericCellValue(),
                  BigDecimal.valueOf(row.getCell(5).getNumericCellValue())));
        } else {
          itemBuilder.add(new Item((long) (row.getCell(0).getNumericCellValue()),
              row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue(),
              row.getCell(3).getStringCellValue(), row.getCell(4).getNumericCellValue(),
              BigDecimal.valueOf(row.getCell(5).getNumericCellValue())));
        }

      }
      return itemBuilder.build();
    } catch (Exception e) {
      System.out.printf("IOException happened during spreadsheet parsing, message: %s%n",
          e.getMessage());
      return ImmutableList.of();
    }
  }
}
