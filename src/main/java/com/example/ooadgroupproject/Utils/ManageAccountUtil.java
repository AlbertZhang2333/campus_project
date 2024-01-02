package com.example.ooadgroupproject.Utils;

import com.example.ooadgroupproject.entity.Account;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManageAccountUtil {
    private static final Logger logger = Logger.getLogger(ManageAccountUtil.class);

    //调用该方法后，返回的新增account，将存储在accountList中
    //成功存储将返回null,否则返回Result.fail

    public static ArrayList<Account> batchAddAccount(String localFilePath) throws IOException {
        ArrayList<Account>accountList=new ArrayList<>();
        try {
            accountList = new ArrayList<>();
            if (localFilePath == null) {
                return null;
            }
            File localFile = new File(localFilePath);
            FileInputStream fis = new FileInputStream(localFile);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            Row headerRow = sheet.getRow(0);
            String[] header = new String[headerRow.getLastCellNum()];
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                header[i] = cell.getStringCellValue();
            }

            Map<String, Integer> infoPos = new HashMap<>();
            if (header.length != 4) {
                return null;
            } else {
                for (int i = 0; i < 4; i++) {
                    String temp = header[i];
                    switch (temp) {
                        case "username":
                        case "userMail":
                        case "password":
                        case "identity":
                            infoPos.put(temp, i);
                            break;
                        default:
                            logger.error("Excel file format error");
                            return null;
                    }
                }
            }

            if (infoPos.size() != 4) {
                logger.error("Excel file format error");
                return null;
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                row.getCell(infoPos.get("username")).setCellType(CellType.STRING);
                String username = row.getCell(infoPos.get("username")).getStringCellValue();
                row.getCell(infoPos.get("userMail")).setCellType(CellType.STRING);
                String userMail = row.getCell(infoPos.get("userMail")).getStringCellValue();
                row.getCell(infoPos.get("password")).setCellType(CellType.STRING);
                String password = row.getCell(infoPos.get("password")).getStringCellValue();
                int identity = (int) row.getCell(infoPos.get("identity")).getNumericCellValue();
                Account account = new Account(0,username, userMail, password, identity);
                accountList.add(account);
            }

            workbook.close();
            fis.close();

            return accountList;
    }catch (Exception e){
            logger.error("Excel file format error");
            return null;
        }
    }
    public static ArrayList<String> batchSetAccountToBlacklist(String localFilePath) throws IOException {
        ArrayList<String> userMailList =new ArrayList<>();
        try {
            userMailList = new ArrayList<>();
            if (localFilePath == null) {
                return null;
            }
            File localFile = new File(localFilePath);
            FileInputStream fis = new FileInputStream(localFile);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            Row headerRow = sheet.getRow(0);
            String[] header = new String[headerRow.getLastCellNum()];
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                header[i] = cell.getStringCellValue();
            }
            Map<String, Integer> infoPos = new HashMap<>();
            if(header.length!=1){
                logger.error("Excel file format error");
                return null;
            }
            if(header[0].equals("userMail")){
                logger.error("Excel file format error");
                return null;
            }
            for(int i=1;i<=sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                row.getCell(infoPos.get("userMail")).setCellType(CellType.STRING);
                String userMail = row.getCell(infoPos.get("userMail")).getStringCellValue();
                userMailList.add(userMail);
            }
            workbook.close();
            fis.close();

            return userMailList;
        }catch (Exception e){
            logger.error("Excel file format error");
            return null;
        }
    }
}

