package io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class Export {
	public static void writeExcelFile(String xlsxPath){
		try {
			FileOutputStream out = new FileOutputStream(new File(xlsxPath));

			out.close();
		}catch (IOException e){
			throw new RuntimeException(e);
		}
	}
}
