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

/**
 * Class thực hiện việc đọc file, nạp từ vựng
 */
public abstract class ImportVoc {
	/**
	 * Đọc dữ liệu từ file excel
	 * @param xlsxPath đường dẫn đến file excel
	 * @return HashMap chứa các từ vựng
	 */
	public static Map<String, Word> toWordHashMap(String xlsxPath) {
		try {
			FileInputStream fileIn = new FileInputStream(new File(xlsxPath));
			XSSFWorkbook workbook = new XSSFWorkbook(fileIn);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Map<String, Word> wordHashMap = new HashMap<>(sheet.getLastRowNum()-sheet.getFirstRowNum() + 10);
			Iterator<Row> rowIterator = sheet.rowIterator();
			rowIterator.next();
			Row row;
			String en, vi;
			while (rowIterator.hasNext()){
				row = rowIterator.next();
				en = row.getCell(0).getStringCellValue();
				vi = row.getCell(1).getStringCellValue();
				wordHashMap.put(en, new Word(en, vi));
			}
			fileIn.close();
			return wordHashMap;
		} catch (IOException e){
			return new HashMap<>();
		}
	}
}