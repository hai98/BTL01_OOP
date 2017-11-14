

import java.io.File;
import java.net.URISyntaxException;
import java.text.Normalizer;
import java.util.Locale;

public class TestMethod {
	public static void main(String[] args) throws URISyntaxException {
		String s = "chủ đề";
		System.out.println(Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", ""));
		File file = new File("res");
		System.out.println(file.toURI().toString());
	}
}
