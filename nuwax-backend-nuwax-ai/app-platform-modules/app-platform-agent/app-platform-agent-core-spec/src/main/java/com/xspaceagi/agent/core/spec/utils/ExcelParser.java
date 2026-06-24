package com.xspaceagi.agent.core.spec.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelParser {

    public static byte[] convertJsonToExcel(String jsonString) {
        if (!JSON.isValid(jsonString)) {
            throw new IllegalArgumentException("Invalid JSON string");
        }
        Workbook workbook = new XSSFWorkbook();
        if (JSON.isValidObject(jsonString)) {
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            for (String key : jsonObject.keySet()) {
                Object object = jsonObject.get(key);
                if (object instanceof JSONArray) {
                    JSONArray jsonArray = jsonObject.getJSONArray(key);
                    Sheet sheet = workbook.createSheet(key);
                    arrayToSheet(jsonArray, sheet);
                }

            }
        } else if (JSON.isValidArray(jsonString)) {
            JSONArray jsonArray = JSONArray.parseArray(jsonString);
            Sheet sheet = workbook.createSheet("sheet1");
            arrayToSheet(jsonArray, sheet);
        }

        // Write the output to a file
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error("Error writing Excel file", e);
            return null;
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return outputStream.toByteArray();
    }

    private static void arrayToSheet(JSONArray jsonArray, Sheet sheet) {
        for (int i = 0; i < jsonArray.size(); i++) {
            if (jsonArray.get(i) instanceof JSONArray) {
                JSONArray rowArray = jsonArray.getJSONArray(i);
                Row row = sheet.createRow(i);
                for (int j = 0; j < rowArray.size(); j++) {
                    Object cellValue = rowArray.get(j);
                    row.createCell(j).setCellValue(cellValue.toString());
                }
            }
        }
    }


    public static List<Map<String, Object>> convertExcelToJson(String url) throws IOException {
        List<Map<String, Object>> allSheetsData = new ArrayList<>();
        byte[] bytes = UrlFile.downLoad(url);
        InputStream fis = new ByteArrayInputStream(bytes);
        Workbook workbook = new XSSFWorkbook(fis);
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            List<List<Object>> sheetData = new ArrayList<>();
            for (int j = 0; j <= sheet.getLastRowNum(); j++) { // 从0开始，包含表头
                Row row = sheet.getRow(j);
                List<Object> rowData = new ArrayList<>();
                boolean nullRow = true;
                if (row != null) {
                    for (int k = 0; k < row.getLastCellNum(); k++) {
                        Cell cell = row.getCell(k);
                        Object cellValue = getCellValue(evaluator, cell);
                        if (cellValue != null && StringUtils.isNotBlank(cellValue.toString())) {
                            nullRow = false;
                        }
                        if (cellValue == null || StringUtils.isBlank(cellValue.toString())) {
                            cellValue = merged(evaluator, sheet, j, k);
                        }
                        rowData.add(cellValue);
                    }
                }
                if (!nullRow) {
                    sheetData.add(rowData);
                }
            }

            Map<String, Object> sheetMap = new HashMap<>();
            sheetMap.put("sheetName", sheet.getSheetName());
            sheetMap.put("sheetIndex", i + 1);
            sheetMap.put("data", sheetData);
            allSheetsData.add(sheetMap);
        }

        workbook.close();
        fis.close();
        return allSheetsData;
    }

    private static Object merged(FormulaEvaluator evaluator, Sheet sheet, int rowIndex, int colIndex) {
        // 检查当前单元格是否在合并单元格中
        int mergedRegionsCount = sheet.getNumMergedRegions();
        for (int i = 0; i < mergedRegionsCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            if (range.isInRange(rowIndex, colIndex)) {
                for (int j = range.getFirstRow(); j <= range.getLastRow(); j++) {
                    for (int k = range.getFirstColumn(); k <= range.getLastColumn(); k++) {
                        Object v = getCellValue(evaluator, sheet.getRow(j).getCell(k));
                        if (v != null && StringUtils.isNotBlank(v.toString())) {
                            return v;
                        }
                    }
                }
            }
        }

        return "";
    }

    public static Object getCellValue(FormulaEvaluator evaluator, Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return evaluator.evaluate(cell).formatAsString();
            default:
                return cell.toString();
        }
    }
}