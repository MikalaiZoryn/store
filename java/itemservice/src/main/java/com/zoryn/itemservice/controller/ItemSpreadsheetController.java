package com.zoryn.itemservice.controller;

import com.google.common.collect.ImmutableList;
import com.zoryn.itemservice.model.Item;
import com.zoryn.itemservice.service.ItemService;
import java.io.File;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/items/spreadsheet")
public class ItemSpreadsheetController {

  private final ItemService itemService;

  @Autowired
  ItemSpreadsheetController(ItemService itemService) {
    this.itemService = itemService;
  }

  @GetMapping
  ResponseEntity<File> getItemsSpreadsheet() throws IOException {
    itemService.downloadItems();
    return ResponseEntity.ok().build();
  }

  @PostMapping
  ImmutableList<Item> uploadItemsSpreadsheet(@RequestParam("file") MultipartFile multipartFile) {
    return itemService.uploadItems(multipartFile);
  }
}
