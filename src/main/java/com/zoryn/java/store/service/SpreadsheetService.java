package com.zoryn.java.store.service;

import com.google.common.collect.ImmutableList;
import com.zoryn.java.store.model.Item;
import com.zoryn.java.store.model.Item.Builder;
import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
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

      ImmutableList.Builder<Item> itemListBuilder = ImmutableList.builder();
      for (int i = 1; i <= sheet.getLastRowNum(); i++) {
        itemListBuilder.add(convertRowToItem(sheet.getRow(i)));
      }
      return itemListBuilder.build();
    } catch (Exception e) {
      System.out.printf("IOException happened during spreadsheet parsing, message: %s%n",
          e.getMessage());
      return ImmutableList.of();
    }
  }

  public Workbook generateSpreadsheet(Collection<Item> items) {
    XSSFWorkbook workbook = new XSSFWorkbook();

    Sheet sheet = workbook.createSheet("Persons");
    sheet.setColumnWidth(0, 6000);
    sheet.setColumnWidth(1, 4000);

    Row header = sheet.createRow(0);

    CellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    XSSFFont font = workbook.createFont();
    font.setFontName("Arial");
    font.setFontHeightInPoints((short) 16);
    font.setBold(true);
    headerStyle.setFont(font);

    Cell headerCell = header.createCell(0);
    headerCell.setCellValue("Name");
    headerCell.setCellStyle(headerStyle);

    headerCell = header.createCell(1);
    headerCell.setCellValue("Age");
    headerCell.setCellStyle(headerStyle);

    CellStyle style = workbook.createCellStyle();
    style.setWrapText(true);

    Row row = sheet.createRow(2);
    Cell cell = row.createCell(0);
    cell.setCellValue("John Smith");
    cell.setCellStyle(style);

    cell = row.createCell(1);
    cell.setCellValue(20);
    cell.setCellStyle(style);

    return workbook;
  }

  private static Item convertRowToItem(Row row) {
    Builder itemBuilder = Builder.newBuilder();
    Cell idCell = row.getCell(0);
    if (idCell != null) {
      itemBuilder.setId((long) (row.getCell(0).getNumericCellValue()));
    }
    itemBuilder.setName(row.getCell(1).getStringCellValue())
        .setCategory(row.getCell(2).getStringCellValue())
        .setDescription(row.getCell(3).getStringCellValue())
        .setRating(row.getCell(4).getNumericCellValue())
        .setPrice(BigDecimal.valueOf(row.getCell(5).getNumericCellValue()));
    return itemBuilder.build();
  }
}
