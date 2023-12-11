package com.zoryn.java.store.controller;

import com.google.common.collect.ImmutableList;
import com.zoryn.java.store.model.Item;
import com.zoryn.java.store.service.ItemService;
import jakarta.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ItemSpreadsheetController {

  private final ItemService itemService;

  @Autowired
  ItemSpreadsheetController(ItemService itemService) {
    this.itemService = itemService;
  }

  @GetMapping("/items/spreadsheet")
  ResponseEntity<File> getItemsSpreadsheet() throws IOException {
    return null;
  }

  @PostMapping("/items/spreadsheet")
  ImmutableList<Item> uploadItemsSpreadsheet(@RequestParam("file") MultipartFile multipartFile) {
    return itemService.uploadItems(multipartFile);
  }
}
