package io;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import words.Word;
import words.WordCollection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public abstract class Export {
	public static void writeExcelFile(WordCollection wordCollection, File dst){
		try {
			FileOutputStream out = new FileOutputStream(dst);
			List<Word> words = new LinkedList<>(wordCollection.getWordList().values());
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet();
			sheet.setDefaultColumnWidth(15);

			//Style
			XSSFFont font = workbook.createFont();
			font.setBold(true);
			font.setFontName("Arial");
			font.setFontHeight(12);
			XSSFCellStyle style = workbook.createCellStyle();
			style.setFont(font);

			Row row = sheet.createRow(0);
			row.setRowStyle(style);

			//Topic
			Cell cell = row.createCell(0, CellType.STRING);
			cell.setCellValue(wordCollection.getTopic());

			row = sheet.createRow(1);
			row.setRowStyle(style);

			//en
			cell = row.createCell(0, CellType.STRING);
			cell.setCellValue("en");

			//vi
			cell = row.createCell(1, CellType.STRING);
			cell.setCellValue("vi");

			//seen
			cell = row.createCell(2, CellType.STRING);
			cell.setCellValue("seen");

			//imgPath
			cell = row.createCell(3, CellType.STRING);
			cell.setCellValue("imgPath");

			XSSFFont font1 = workbook.createFont();
			font1.setFontName("Arial");
			font1.setFontHeight(12);
			XSSFCellStyle style1 = workbook.createCellStyle();
			style1.setFont(font1);

			int rowNum = 2;
			for (Word w : words) {
				row = sheet.createRow(rowNum++);
				row.setRowStyle(style1);

				cell = row.createCell(0, CellType.STRING);
				cell.setCellValue(w.getEn());

				cell = row.createCell(1, CellType.STRING);
				cell.setCellValue(w.getVi());

				cell = row.createCell(2, CellType.BOOLEAN);
				cell.setCellValue(w.isSeen());

				cell = row.createCell(3, CellType.STRING);
				cell.setCellValue(w.getImgPath());
			}

			workbook.write(out);
			out.close();
		}catch (IOException e){
			throw new RuntimeException(e);
		}
	}
	/*public static void writeExcelFile(WordCollection wordCollection){
		writeExcelFile(wordCollection, "res/voc");
	}*/
}