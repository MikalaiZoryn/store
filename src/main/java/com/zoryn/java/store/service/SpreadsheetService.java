package com.zoryn.java.store.service;

import com.google.common.collect.ImmutableList;
import com.zoryn.java.store.model.Item;
import com.zoryn.java.store.model.Item.Builder;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
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

  enum Column {
    ID,
    NAME,
    CATEGORY,
    DESCRIPTION,
    RATING,
    PRICE
  }

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

  public Workbook generateSpreadsheet(List<Item> items) {
    XSSFWorkbook workbook = new XSSFWorkbook();

    Sheet sheet = workbook.createSheet("Items");
    setColumnsWidth(sheet);
    createHeader(workbook, sheet);
    createItemRows(items, workbook, sheet);

    return workbook;
  }

  private static void createItemRows(List<Item> items, XSSFWorkbook workbook, Sheet sheet) {
    CellStyle style = workbook.createCellStyle();
    style.setWrapText(true);

    for (int i = 0; i < items.size(); i++) {
      createItemRow(items.get(i), sheet, style, i);
    }
  }

  private static void createItemRow(Item item, Sheet sheet, CellStyle style, int i) {
    Row row = sheet.createRow(i + 1);
    Cell cell = row.createCell(0);
    cell.setCellValue(item.getId());
    cell.setCellStyle(style);

    cell = row.createCell(1);
    cell.setCellValue(item.getName());
    cell.setCellStyle(style);

    cell = row.createCell(2);
    cell.setCellValue(item.getCategory());
    cell.setCellStyle(style);

    cell = row.createCell(3);
    cell.setCellValue(item.getDescription());
    cell.setCellStyle(style);

    cell = row.createCell(4);
    cell.setCellValue(item.getRating());
    cell.setCellStyle(style);

    cell = row.createCell(5);
    cell.setCellValue(item.getPrice().toPlainString());
    cell.setCellStyle(style);
  }

  private static void setColumnsWidth(Sheet sheet) {
    for (Column column : Column.values()) {
      sheet.setColumnWidth(column.ordinal(), 4000);
    }
  }

  private static void createHeader(XSSFWorkbook workbook, Sheet sheet) {
    Row header = sheet.createRow(0);
    CellStyle headerStyle = createHeaderStyle(workbook);

    for (Column column : Column.values()) {
      Cell headerCell = header.createCell(column.ordinal());
      headerCell.setCellValue(column.name());
      headerCell.setCellStyle(headerStyle);
    }
  }

  private static CellStyle createHeaderStyle(XSSFWorkbook workbook) {
    CellStyle headerStyle = workbook.createCellStyle();
    XSSFFont font = workbook.createFont();
    font.setFontName("Arial");
    font.setFontHeightInPoints((short) 16);
    font.setBold(true);
    headerStyle.setFont(font);
    return headerStyle;
  }

  private static Item convertRowToItem(Row row) {
    Builder itemBuilder = Builder.newBuilder();
    Cell idCell = row.getCell(Column.ID.ordinal());
    if (idCell != null) {
      itemBuilder.setId((long) (idCell.getNumericCellValue()));
    }
    itemBuilder.setName(row.getCell(Column.NAME.ordinal()).getStringCellValue())
        .setCategory(row.getCell(Column.CATEGORY.ordinal()).getStringCellValue())
        .setDescription(row.getCell(Column.DESCRIPTION.ordinal()).getStringCellValue())
        .setRating(row.getCell(Column.RATING.ordinal()).getNumericCellValue())
        .setPrice(BigDecimal.valueOf(row.getCell(Column.PRICE.ordinal()).getNumericCellValue()));
    return itemBuilder.build();
  }
}
