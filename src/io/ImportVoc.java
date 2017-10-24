package io;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import words.Word;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class ImportVoc {
	private ImportVoc(){}
	public static Map<String, Word> toWordHashMap(String xlsxPath) throws IOException{
		FileInputStream fileIn = new FileInputStream(new File(xlsxPath));
		XSSFWorkbook workbook = new XSSFWorkbook(fileIn);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Map<String, Word> wordHashMap = new HashMap<>(sheet.getLastRowNum()-sheet.getFirstRowNum() + 10);
		Iterator<Row> rowIterator = sheet.rowIterator();
		Row row = rowIterator.next();
		String en, vi;
		while (rowIterator.hasNext()){
			row = rowIterator.next();
			en = row.getCell(0).getStringCellValue();
			vi = row.getCell(1).getStringCellValue();
			wordHashMap.put(en, new Word(en, vi));
		}
		fileIn.close();
		return wordHashMap;
	}
}