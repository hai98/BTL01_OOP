package io;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
	 * @param fileExcel file excel
	 * @return bộ từ vựng
	 */
	public static WordCollection readExcelFile(File fileExcel) {
		try {
			System.out.println(fileExcel.getName());
			FileInputStream fileIn = new FileInputStream(fileExcel);
			XSSFWorkbook workbook = new XSSFWorkbook(fileIn);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Map<String, Word> wordHashMap = new HashMap<>(sheet.getLastRowNum()-sheet.getFirstRowNum() + 10);
			String topic = sheet.getRow(0).getCell(0).getStringCellValue().trim();
			int learned = 0;
			Row row;
			String en, vi, imgPath;
			boolean seen;
			for (int i=2; i<=sheet.getLastRowNum(); ++i) {
				if ((row = sheet.getRow(i)) == null) continue;
				if(row.getCell(0) == null) continue;
				en = row.getCell(0).getStringCellValue().trim();
				vi = row.getCell(1).getStringCellValue().trim();
				seen = (row.getCell(2) != null) && (row.getCell(2).getCellTypeEnum() != CellType.BLANK && row.getCell(2).getBooleanCellValue());
				if (seen) ++learned;
				imgPath = (row.getCell(3) == null)? null : row.getCell(3).getStringCellValue();
				Word t = new Word(en, vi, topic, seen);
				t.setImgPath(imgPath);
				wordHashMap.put(en, t);
//				suggestion.add(en);
			}
			fileIn.close();
			WordCollection t = new WordCollection(topic, wordHashMap);
			t.setFileName(fileExcel.getName());
			t.setLearned(learned);
			return t;
		} catch (Exception e){
			throw new RuntimeException("error while reading excel files", e);
		}

	}
}