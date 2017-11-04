package io;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import words.RunningData;
import words.Word;
import words.WordCollection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Class thực hiện việc đọc file, nạp từ vựng
 */
public abstract class Import {
//	private static HashSet<String> suggestion = new HashSet<>();

//	public static HashSet<String> getSuggestion() {
//		return suggestion;
//	}

	/**
	 * Đọc dữ liệu từ file excel
	 * @param xlsxPath đường dẫn đến file excel
	 * @return bộ từ vựng
	 */
	public static WordCollection readExcelFile(String xlsxPath) {
		try {
			FileInputStream fileIn = new FileInputStream(new File(xlsxPath));
			XSSFWorkbook workbook = new XSSFWorkbook(fileIn);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Map<String, Word> wordHashMap = new HashMap<>(sheet.getLastRowNum()-sheet.getFirstRowNum() + 10);
			Iterator<Row> rowIterator = sheet.rowIterator();
			String topic = sheet.getRow(0).getCell(0).getStringCellValue();
			rowIterator.next();
			rowIterator.next();
			Row row;
			String en, vi;
			boolean seen;
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				en = row.getCell(0).getStringCellValue();
				vi = row.getCell(1).getStringCellValue();
				seen = row.getCell(2).getCellTypeEnum() != CellType.BLANK && row.getCell(2).getBooleanCellValue();
				wordHashMap.put(en, new Word(en, vi, topic, seen));
//				suggestion.add(en);
			}
			fileIn.close();
			return new WordCollection(topic, wordHashMap);
		} catch (IOException e){
			throw new RuntimeException(e);
		}

	}
}