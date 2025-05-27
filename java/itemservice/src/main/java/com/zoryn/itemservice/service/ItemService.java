package com.zoryn.itemservice.service;

import com.google.common.collect.ImmutableList;
import com.zoryn.itemservice.model.Item;
import com.zoryn.itemservice.repository.ItemRepository;
import java.io.File;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ItemService {

  private final ItemRepository itemRepository;
  private final SpreadsheetService spreadsheetService;
  private final FileService fileService;

  @Autowired
  ItemService(ItemRepository itemRepository, SpreadsheetService spreadsheetService, FileService fileService) {
    this.itemRepository = itemRepository;
    this.spreadsheetService = spreadsheetService;
    this.fileService = fileService;
  }

  public ImmutableList<Item> uploadItems(MultipartFile spreadsheetMultipartFile) {
    File spreadsheetFile = fileService.convertMultipartFileIntoFile(spreadsheetMultipartFile);
    ImmutableList<Item> parsedItems = spreadsheetService.parseSpreadsheet(spreadsheetFile);
    return ImmutableList.copyOf(itemRepository.saveAll(parsedItems));
  }

  public void downloadItems() {
    List<Item> itemList = itemRepository.findAll();
    Workbook generatedSpreadsheet = spreadsheetService.generateSpreadsheet(itemList);
    fileService.saveSpreadsheetToFile(generatedSpreadsheet);
  }
}
